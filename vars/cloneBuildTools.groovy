def call(){
   echo 'Cloning build tools to workspace.'
   
   def branch = env."library.vs-build-tools.version"
   buildToolsDir = cloneRepo("https://github.com/${ORG_NAME}/${BUILD_SYSTEM_REPO}", branch)
   return buildToolsDir
}
