pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git branch: 'develop', url: 'https://github.com/ghazi300/Tawasalna-TESTING-Backend.git'
            }
        }

        stage('Set Permissions') {
            steps {
                sh 'chmod +x ./mvnw'
            }
        }

        stage('Compile') {
            steps {
                sh './mvnw clean compile'
            }
        }

        stage('Test') {
            steps {
                sh './mvnw test'
            }
        }

        stage('Package') {
            steps {
                sh './mvnw package'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                script {
                    def scannerHome = tool 'scanner'
                    withSonarQubeEnv {
                        sh "${scannerHome}/bin/sonar-scanner -X"
                    }
                }
            }
        }
}