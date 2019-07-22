def call(lvProjectPath, lvVersion, lvBitness) {
	def stepsDir = "${WORKSPACE}\\${BUILD_SYSTEM_REPO}\\steps"
	def reportPath = "${WORKSPACE}\\TEMPDIR\\report.xml"
	def projectPath = "${WORKSPACE}\\${lvProjectPath}"
	
	echo 'Running unit tests on project'

	bat "python -u \"${stepsDir}\\labview_utf.py\" \"${projectPath}\" \"${reportPath}\" ${lvVersion} ${lvBitness}"
}

