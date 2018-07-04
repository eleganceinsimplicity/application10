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
                sh 'mvn clean package'
            } catch(exc){
                echo 'Something failed. try again'
                throw exc
            }
    }
}