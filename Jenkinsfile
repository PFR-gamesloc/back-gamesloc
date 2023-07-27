pipeline {
    agent {
        docker {
            image 'maven:3.9.3-eclipse-temurin-20'
            args '-v /root/.m2:/root/.m2 --network jenkins_network'
        }
    }
    stages {
        stage('Build') {
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
        }
        stage('SonarQube analysis') {
            environment {
                scannerHome = tool 'SonarQubeScanner'
            }

            steps {
                withSonarQubeEnv(installationName: 'SonarQube') {
                  sh 'mvn sonar:sonar'
                }
            }
        }
    }
}