def call(lvProjectPath, targetName, buildSpecName, lvVersion, lvBitness) {
	def stepsDir = "${WORKSPACE}\\${BUILD_SYSTEM_REPO}\\steps"
	def projectPath = "${WORKSPACE}\\${lvProjectPath}"
		
	echo 'Running LabVIEW build spec on project'

	bat "python -u \"${stepsDir}\\labview_build.py\" \"${projectPath}\" \"${targetName}\" \"${buildSpecName}\" ${lvVersion} ${lvBitness}"
}
