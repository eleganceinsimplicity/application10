node {
    checkout([
        $class: 'GitSCM',
        branches: [[name: 'master']],
        doGenerateSubmoduleConfigurations: false,
        extensions: [],
        submoduleCfg: [],
        userRemoteConfigs: [[credentialsId: 'GitHub-simplelife1', refspec: '+refs/heads/master:refs/remotes/origin/master', url: 'https://github.com/simplelife1/application10.git']]
        ])




    stage('Build') {
            try {
                sh 'echo "Building.."'
                sh "echo 'Running ${env.BUILD_ID} on ${env.JENKINS_URL}'"
                sh 'java -version'
                sh 'mvn --version'

                def server = Artifactory.newServer url: 'http://artifactory.localhost.com', credentialsId: 'jfrog-artifactory'

                def uploadSpec = """{
                  "files": [
                    {
                      "pattern": "${env.WORKSPACE}/target/*.jar",
                      "target": "example-repo-local/"
                    },
                    {
                      "pattern": "${env.WORKSPACE}/target/*.pom",
                      "target": "example-repo-local/"
                    }
                 ]
                }"""

                def buildInfoUpload = server.upload(uploadSpec)

                def buildInfo = Artifactory.newBuildInfo()
                buildInfo.env.capture = true
                server.upload(buildInfoUpload,buildInfo)


                // Create and set an Artifactory Maven Build instance:
                def rtMaven = Artifactory.newMavenBuild()
                rtMaven.resolver server: server, releaseRepo: 'example-repo-local', snapshotRepo: 'example-repo-local'
                // Optionally include or exclude artifacts to be deployed to Artifactory:
                rtMaven.deployer.artifactDeploymentPatterns.addInclude("*.pom,*.jar").addExclude("*.zip")
                // Set a Maven Tool defined in Jenkins "Manage":
                rtMaven.tool = MAVEN_TOOL
                // Optionally set Maven Ops
                rtMaven.opts = '-Xms1024m -Xmx4096m'
                // Run Maven:
                rtMaven.run pom: 'application10/pom.xml', goals: 'clean install', buildInfo: buildInfo

                server.publishBuildInfo(buildInfo)

            } catch(exc){
                echo 'Something failed. try again'
                throw exc
            }
    }
}