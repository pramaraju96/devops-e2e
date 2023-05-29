def artifactname = "artifact_devops_${env.BUILD_NUMBER}.jar"
def repoName = "repository_devops"
def pipelineName = "pipeline_devops"
def semanticVersion = "${env.BUILD_NUMBER}.0.0"
def packageName = "package_devops_${env.BUILD_NUMBER}"
def version = "${env.BUILD_NUMBER}.0"
def changeRequestId = "defaultChangeRequestId"

pipeline {
  agent any
  tools {
       maven 'Maven'
   }
   environment {
	 SCANNER_HOME = tool 'sonarScanner'
	}  
  stages {
       stage('Build') {
           steps {
              sh 'mvn -B -DskipTests clean compile'
           }
       }

       stage('Test') {
           steps {
               sh 'mvn test'
	           sleep(5);
           }
          post {
             always {
                junit "**/target/surefire-reports/*.xml"
             }
           }
       }
       
       stage('Pre-Prod') {
		steps {
		    sleep(5);
                sonarSummaries()
                
                snDevOpsArtifact(artifactsPayload: """{"artifacts": [{"name": "${artifactname}", "version": "1.${env.BUILD_NUMBER}","semanticVersion": "1.${env.BUILD_NUMBER}.0","repositoryName": "${repoName}"}],"branchName":"main"}""")
                snDevOpsPackage(name: "${packageName}", artifactsPayload: """{"artifacts":[{"name": "${artifactname}", "version": "1.${env.BUILD_NUMBER}","semanticVersion": "1.${env.BUILD_NUMBER}.0","repositoryName": "${repoName}"}], "branchName":"main"}""")
            }       
       }

        stage('Upload to JFrog') {
        steps {
            sleep(5);
                sonarSummaries()
                
                def server = Artifactory.server 'ramadevopsjfrog'
                def uploadSpec = """{
                   "files": [{
                     "pattern": "${env.WORKSPACE}/target/artifact-1.3.jar",
                      "target": "default-docker-virtual/"
                   }]
                }"""

             def buildInfo = server.upload(uploadSpec) 
             server.publishBuildInfo buildInfo

            }       
        }
             
 }
 
}

def sonarSummaries() {
    withSonarQubeEnv('ramasonarcloud'){
        if(fileExists("sonar-project.properties")) {
            sh '${SCANNER_HOME}/bin/sonar-scanner'
        } else {
            sh '${SCANNER_HOME}/bin/sonar-scanner -Dproject.settings=${SCANNER_HOME}/conf/qa-sonar-scanner.properties'
        }
    }
}
