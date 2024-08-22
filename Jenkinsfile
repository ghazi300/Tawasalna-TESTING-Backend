pipeline {
    agent any

   tools {
        nodejs 'nodeJSInstallationName'
    }

      environment {
        DOCKER_IMAGE_Back_administration = 'brahim98/tawasolna_back:administrationspringimage'
        DOCKER_IMAGE_Back_authentication = 'brahim98/tawasolna_back:authenticationspringimage'
        DOCKER_IMAGE_Back_pms = 'brahim98/tawasolna_back:pmsspringimage'
        DOCKER_IMAGE_Back_crm = 'brahim98/tawasolna_back:crmspringimage'
        DOCKER_IMAGE_Back_social = 'brahim98/tawasolna_back:socialspringimage'
        DOCKER_IMAGE_Back_business = 'brahim98/tawasolna_back:businessspringimage'

    }

    stages {



     stage('Checkout') {
                steps {
                    // Checkout the code from the GitHub repository
                    checkout([$class: 'GitSCM', branches: [[name: 'staging']], userRemoteConfigs: [[url: 'https://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@github.com/ipactconsult/tawasolna-backend-app.git']]])

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




         stage('Build image spring') {
                steps {
                    script {
                // Build the Docker image for the Spring Boot apps
                sh "docker build -t $DOCKER_IMAGE_Back_administration -f Dockerfile-admininistration ."
                sh "docker build -t $DOCKER_IMAGE_Back_authentication -f Dockerfile-authentication ."
                sh "docker build -t $DOCKER_IMAGE_Back_pms -f Dockerfile-pms ."
                sh "docker build -t $DOCKER_IMAGE_Back_crm -f Dockerfile-crm ."
                sh "docker build -t $DOCKER_IMAGE_Back_social -f Dockerfile-social ."
                sh "docker build -t $DOCKER_IMAGE_Back_business -f Dockerfile-business ."
            }
                }
            }

            stage('Push image spring') {
                steps {
                    script {
                        withDockerRegistry([credentialsId: 'docker-hub-creds',url: ""]) {
                            // Push the Docker image to Docker Hub

                        sh "docker push $DOCKER_IMAGE_Back_administration"
                        sh "docker push $DOCKER_IMAGE_Back_authentication"
                        sh "docker push $DOCKER_IMAGE_Back_pms"
                        sh "docker push $DOCKER_IMAGE_Back_crm"
                        sh "docker push $DOCKER_IMAGE_Back_social"
                        sh "docker push $DOCKER_IMAGE_Back_business"
                        }
                    }
                }}








         stage('Deployment stage ') {
    steps {
    dir('ansible') {

        sh "sudo ansible-playbook -u root k8s.yml -i inventory/host.yml"
    }

}

}


    }
}
