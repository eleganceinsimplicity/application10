node {
    checkout([
        $class: 'GitSCM',
        branches: [[name: 'master']],
        doGenerateSubmoduleConfigurations: false,
        extensions: [],
        submoduleCfg: [],
        userRemoteConfigs: [[credentialsId: 'GitHub-simplelife1', refspec: '+refs/heads/master:refs/remotes/origin/master', url: 'https://github.com/simplelife1/application10.git']]
        ])

    // Create and set an Artifactory Maven Build instance:
     def rtMaven = Artifactory.newMavenBuild()

     // Set a Maven Tool defined in Jenkins "Manage":
     rtMaven.tool = "MAVEN3"


    stage('Build') {
            try {
                sh 'echo "Building.."'
                sh "echo 'Running ${env.BUILD_ID} on ${env.JENKINS_URL}'"
                sh 'java -version'
                sh 'mvn --version'

                def server = Artifactory.newServer url: 'http://artifactory.localhost.com/artifactory', credentialsId: 'jfrog-artifactory'

                def downloadSpec = """{
                 "files": [
                  {
                      "pattern": "**/*",
                      "target": "target/"
                    }
                 ]
                }"""

                def uploadSpec = """{
                  "files": [
                      {
                        "pattern": "${env.WORKSPACE}/*.pom",
                        "target": "example-repo-local/"
                      },
                    {
                      "pattern": "${env.WORKSPACE}/target/*.jar",
                      "target": "example-repo-local/"
                    }

                 ]
                }"""

                def buildInfoUploadDownloadSpec = server.download(downloadSpec)
                def buildInfoUploadSpec = server.upload(uploadSpec)
                buildInfoUploadDownloadSpec.append(buildInfoUploadSpec)
                server.publishBuildInfo(buildInfoUploadDownloadSpec)



                rtMaven.resolver server: server, releaseRepo: 'example-repo-local', snapshotRepo: 'example-repo-local'
                // Optionally include or exclude artifacts to be deployed to Artifactory:
                rtMaven.deployer.artifactDeploymentPatterns.addInclude("*.pom,*.jar")

                // Optionally set Maven Ops
                rtMaven.opts = '-Xms1024m -Xmx4096m'
                // Run Maven:
                def buildInfoData = rtMaven.run pom: 'pom.xml', goals: 'clean package deploy'

                server.publishBuildInfo(buildInfoData)


            } catch(exc){
                echo 'Something failed. try again'
                throw exc
            }
    }
}