def call() {
	def stepsDir = "${WORKSPACE}\\${BUILD_SYSTEM_REPO}\\steps"
	
	echo 'Archiving artifacts...'
	bat "Python -u \"${stepsDir}\\move_files.py\" \"${LV_BUILD_OUTPUT_DIR}\" \"${WORKSPACE}\\TEMPDIR\""
	archiveArtifacts artifacts: 'TEMPDIR/**'
	echo 'Archival complete!'
}