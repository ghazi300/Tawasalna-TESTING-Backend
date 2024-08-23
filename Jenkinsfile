pipeline {
    agent any

    tools {
        nodejs 'nodeJSInstallationName'
    }

    environment {
        DOCKER_REGISTRY = 'docker.io'
               DOCKER_IMAGE_Back_administration = 'romdhanihana/tawasolna_back:administrationspringimage'
               DOCKER_IMAGE_Back_authentication = 'romdhanihana/tawasolna_back:authenticationspringimage'
               DOCKER_IMAGE_Back_pms = 'romdhanihana/tawasolna_back:pmsspringimage'
               DOCKER_IMAGE_Back_crm = 'romdhanihana/tawasolna_back:crmspringimage'
               DOCKER_IMAGE_Back_social = 'romdhanihana/tawasolna_back:socialspringimage'
               DOCKER_IMAGE_Back_business = 'romdhanihana/tawasolna_back:businessspringimage'
               DOCKER_IMAGE_Back_shared = 'romdhanihana/tawasolna_back:sharedspringimage'
               DOCKER_IMAGE_Back_management_coordination = 'romdhanihana/tawasolna_back:management-coordinationimage'
               DOCKER_IMAGE_Back_resident_support_services = 'romdhanihana/tawasolna_back:resident-support-servicesimage'
               DOCKER_IMAGE_Back_community_engagement = 'romdhanihana/tawasolna_back:community-engagementimage'
               DOCKER_IMAGE_Back_crisis = 'romdhanihana/tawasolna_back:crisisimage'
               DOCKER_IMAGE_Back_facilities_management = 'romdhanihana/tawasolna_back:facilities-managementimage'
               DOCKER_IMAGE_Back_operations = 'romdhanihana/tawasolna_back:operationsimage'
               DOCKER_IMAGE_Back_security_safety = 'romdhanihana/tawasolna_back:security-safetyimage'
           }

    stages {
        stage('Checkout') {
            steps {
                // Checkout the code from the GitHub repository
                checkout([$class: 'GitSCM', branches: [[name: 'develop']], userRemoteConfigs: [[url: 'https://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@github.com/ipactconsult/tawasolna-backend-app.git']]])
            }
        }

        stage('Build') {
            steps {
                // Build the project using Maven
                sh 'mvn clean install' // Adjust this to your actual build command
            }
        }

        /*
        stage('SonarQube Analysis') {
            steps {
                script {
                    sh 'mvn clean verify sonar:sonar ' +
                       '-Dsonar.projectKey=tawasolna ' +
                       '-Dsonar.projectName=tawasolna ' +
                       '-Dsonar.host.url=http://194.146.13.51:9000 ' +
                       '-Dsonar.login=sqp_ba4938ea8cf15f72d29a739ffcf4238fb80a6724'
                }
            }
        }
        */

        stage('Build images using Docker Compose') {
            steps {
                script {
                    // Build the Docker images using Docker Compose
                    sh 'docker-compose build'
                }
            }
        }

        stage('Push images') {
            steps {
                script {
                    withDockerRegistry([credentialsId: 'docker-hub-creds', url: "${DOCKER_REGISTRY}"]) {
                        // Push the Docker images to Docker Hub
                        sh "docker-compose push"
                    }
                }
            }
        }

        stage('Deployment stage') {
            steps {
                dir('ansible') {
                    sh "sudo ansible-playbook -u root k8s.yml -i inventory/host.yml"
                }
            }
        }
    }
}
