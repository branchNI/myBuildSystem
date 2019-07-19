def call(lvVersion) {
	def diffDir = "${WORKSPACE}\\DIFFDIR"
	def stepsDir = "${WORKSPACE}\\${BUILD_SYSTEM_REPO}\\steps"
	def prNum = env.CHANGE_ID
	def repo = getComponentParts()['repo']

	echo 'Running LabVIEW diff build between origin/master and this commit'

	bat "python -u \"${stepsDir}\\labview_diff.py\" \"${WORKSPACE}\" \"${diffDir}\" ${lvVersion} --target=origin/master"
    
	bat "python -u \"${stepsDir}\\github_commenter.py\" --token=\"${ACCESS_TOKEN}\" --pic-dir=\"${diffDir}\" --pull-req=\"${env.CHANGE_ID}\" --info=\"${ORG_NAME}/${repo}/${env.CHANGE_ID}\" --pic-repo=\"${ORG_NAME}/${PIC_REPO}\""
}
