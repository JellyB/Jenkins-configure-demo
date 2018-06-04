node('jnlp-slave') {
	def mvnHome = tool 'maven3'
	def svnUrl = "http://192.168.6.5/svn/wdypt-yhzx/01_代码开发库/trunk/wdcloud-admin/wdcloud-ptxtgl"
	def appName = "ptxtgl"
	def appPath = "/wdcloud/app/jetty_ptxtgl/webapps/"
	def nodeIp = "192.168.6.186"
	def tag = "20171108"
	def profile = "test"

	env.sonarHome= tool name: 'sonar-scanner', type: 'hudson.plugins.sonar.SonarRunnerInstallation'
	stage('Checkout Code') {
       checkout([$class: 'SubversionSCM', additionalCredentials: [], excludedCommitMessages: '', excludedRegions: '', excludedRevprop: '', excludedUsers: '', filterChangelog: false, ignoreDirPropChanges: false, includedRegions: '', locations: [[credentialsId: 'biguodong', depthOption: 'infinity', ignoreExternalsOption: true, local: '.', remote: svnUrl]], workspaceUpdater: [$class: 'UpdateUpdater']])
   }
   stage('Package') {
       sh "'${mvnHome}/bin/mvn' -Dmaven.test.failure.ignore -Dmaven.test.skip=true clean package -P ${profile}"
   }
   stage('Check Code'){
     withSonarQubeEnv('SonarQube') {
  	   sh """
	  ${sonarHome}/bin/sonar-scanner \
	  -Dsonar.projectName=${appName} \
	  -Dsonar.projectVersion=${tag} \
	  -Dsonar.projectKey=${appName} \
	  -Dsonar.sources=src/main/java \
	  -Dsonar.java.binaries=target/classes \
	  -Dsonar.language=java \
	  -Dsonar.sourceEncoding=UTF-8
	  """
		}
      }

  	stage('Backup'){
	sshagent(['jenkinsDeploy']){
   		sh """
   			ssh root@${nodeIp} "cd ${appPath} && tar -zcf  ${appName}`date +%Y%m%d`.tar.gz ${appName}"
   			ssh root@${nodeIp} "cd ${appPath} && mv ${appName}`date +%Y%m%d`.tar.gz /wdcloud/backup"
   		"""
		}
	}

	stage('Deployment'){
		sshagent(['jenkinsDeploy']){
       		sh """
       		    ssh root@${nodeIp} "rm -rf ${appPath}${appName}"
       		    scp -o StrictHostKeyChecking=no -r target/ptxtgl root@${nodeIp}:${appPath}
       		    ssh root@${nodeIp} "chown -R jetty:jetty ${appPath}"
       		    ssh root@${nodeIp} "service jetty_ptxtgl restart 2&>1 >/dev/null"
       		"""
			}
	}

}
