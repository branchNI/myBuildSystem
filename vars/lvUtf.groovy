def call(lvVersion, utfPath) {
	def stepsDir = "${WORKSPACE}\\jenkinsbuildsystem\\steps"
	def reportPath = "${WORKSPACE}\\TEMPDIR\\report.xml"
	def utfProjectPath = "${WORKSPACE}\\${utfPath}"
	
	echo 'Running unit tests on \"${utfProjectPath}\"'

	bat "python -u \"${stepsDir}\\labview_utf.py\" \"${utfProjectPath}\" \"${reportPath}\" ${lvVersion}"
}

