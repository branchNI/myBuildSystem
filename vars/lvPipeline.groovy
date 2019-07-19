#!/usr/bin/env groovy
def PULL_REQUEST = env.CHANGE_ID

//ENTER THE ABOVE INFORMATION

def call(utfPath, lvVersion, lvPath) {

	switch(lvVersion){  //This is to abstract out the different Jenkinsfile conventions of setting version to 14.0 instead of 2014.
	  case "18.0":
		lvVersion="2018"
		break
	  case "19.0":
		lvVersion="2019"
		break
	  case "20.0":
		lvVersion="2020"
		break
	}

	node {
		echo 'Starting Build...'
		
		//TEST
		echo 'job name: '
		echo env.JOB_NAME

		stage ('Pre-Clean'){
		preClean()
		}
	  
		stage('SCM Checkout') {
			echo 'Attempting to get source from repo...'
			timeout(time: 4, unit: 'MINUTES') {
				checkout scm
			}
			
			echo 'Cloning build tools...'
			timeout(time: 5, unit: 'MINUTES') {
				cloneBuildTools()
			}
		}

		stage ('Create Directories'){
          bat 'mkdir TEMPDIR'
		  bat 'mkdir DIFFDIR'
        }
		
		echo 'Running unit tests...'
		
		stage ('Unit Tests') {
			try {
				timeout(time: 60, unit: 'MINUTES') {
					lvUtf(lvVersion, utfPath)
					echo 'Unit tests Succeeded!'
				}
				} catch (err) {
					currentBuild.result = "SUCCESS"
					echo "Unit Tests Failed: ${err}"
				}
		}

		echo 'Running diff...'
		
		// If this change is a pull request, diff the VIs.
		if (env.CHANGE_ID) {
			stage ('Diff VIs'){
				try {
				timeout(time: 60, unit: 'MINUTES') {
					lvDiff(lvVersion)
					echo 'Diff Succeeded!'
				}
				} catch (err) {
					currentBuild.result = "SUCCESS"
					echo "Diff Failed: ${err}"
				}
			}
		}
	}
}

