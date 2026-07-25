pipeline {
    agent any

    tools {
        maven 'Maven-3.9'
        jdk 'JDK-21'
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

       stage('SonarQube Analysis') {
    steps {
        withSonarQubeEnv('SonarQube') {
            sh '''
            mvn clean verify \
            org.sonarsource.scanner.maven:sonar-maven-plugin:4.0.0.4121:sonar \
            -Dsonar.projectKey=JenkinsLearning
            '''
        }
    }
}
        stage('Quality Gate') {
            steps {
                timeout(time: 5, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
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
                withCredentials([usernamePassword(
                    credentialsId: 'dockerhub',
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_PASS'
                )]) {

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
                    sh '''
                    ssh -o StrictHostKeyChecking=no ubuntu@13.229.78.27 <<EOF
                    docker pull mariaselvam21/springboot-demo:latest
                    docker stop springboot-app || true
                    docker rm springboot-app || true
                    docker run -d --name springboot-app -p 2000:2000 mariaselvam21/springboot-demo:latest
                    EOF
                    '''
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