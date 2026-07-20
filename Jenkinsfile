pipeline {
    agent any

    
    tools {
        maven 'Maven-3.9'
        jdk 'JDK-21'   // Use the exact JDK name configured in Jenkins
    }

    environment {
        IMAGE_NAME = "mariaselvam21/springboot-demo:latest"
        EC2_HOST = "13.229.78.27"
    }

    stages {

        
        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh '''
                docker stop springboot-app || true
                docker rm springboot-app || true
                docker build -t $IMAGE_NAME .
                '''
            }
        }

        stage('Push Docker Image') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhub',
                        usernameVariable: 'DOCKER_USER',
                        passwordVariable: 'DOCKER_PASS')]) {

                    sh '''
                    echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin
                    docker push $IMAGE_NAME
                    '''
                }
            }
        }

        stage('Deploy to EC2') {
    steps {
        sshagent(['ec2-key']) {
            sh """
            ssh -o StrictHostKeyChecking=no ubuntu@${EC2_HOST} '
                docker pull ${IMAGE_NAME}

                docker stop springboot-app || true

                docker rm springboot-app || true

                docker run -d \
                  --name springboot-app \
                  -p 2000:2000 \
                  ${IMAGE_NAME}
            '
            """
        }
    }
	}
    }

    post {

        success {
            echo "Application deployed successfully."
        }

        failure {
            echo "Pipeline failed."
        }
    }
}