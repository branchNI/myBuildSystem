def call(lvVersion, lvBitness) {
	def diffDir = "${WORKSPACE}\\DIFFDIR"
	def stepsDir = "${WORKSPACE}\\${BUILD_SYSTEM_REPO}\\steps"
	def operationsDir = "${WORKSPACE}\\${BUILD_SYSTEM_REPO}\\lv\\operations"
	def prNum = env.CHANGE_ID
	def repo = getComponentParts()['repo']

	echo 'Running LabVIEW diff build between origin/master and this commit'

	bat "python -u \"${stepsDir}\\labview_diff.py\" \"${operationsDir}\\\\\" \"${diffDir}\\\\\" ${lvVersion} ${lvBitness} --target=origin/master"
    
	bat "python -u \"${stepsDir}\\github_commenter.py\" --token=\"${ACCESS_TOKEN}\" --pic-dir=\"${diffDir}\" --pull-req=\"${env.CHANGE_ID}\" --info=\"${ORG_NAME}/${repo}/${env.CHANGE_ID}\" --pic-repo=\"${ORG_NAME}/${PIC_REPO}\""
}
