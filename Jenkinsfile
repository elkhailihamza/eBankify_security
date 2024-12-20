pipeline {
    agent any

    environment {
        WORKSPACE = "/var/lib/jenkins/workspace/ebankify-deploy"
        dockerImageTag = "ebankify-app:${env.BUILD_NUMBER}" // Match Dockerfile naming
        containerName = "ebankify-container-${env.BUILD_NUMBER}" // Generate unique container name
    }

    stages {
        stage('Clone Repo') {
            steps {
                git url: 'https://github.com/elkhailihamza/eBankify_security',
                    credentialsId: 'github-credentials',
                    branch: 'develop'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    // Build the Docker image using the correct context (workspace directory)
                    docker.build("${dockerImageTag}", ".")
                }
            }
        }

        stage('Deploy Docker') {
            steps {
                script {
                    echo "Deploying Docker Image: ${dockerImageTag}"

                    // Stop and remove the existing container if it exists, using the dynamic container name
                    sh """
                        if [ "$(docker ps -q -f name=${containerName})" ]; then
                            docker stop ${containerName}
                            docker rm ${containerName}
                        fi
                    """

                    // Run the newly built Docker image with the dynamic container name
                    sh """
                        docker run -d --name ${containerName} -p 8083:8083 ${dockerImageTag}
                    """
                }
            }
        }
    }

    post {
        failure {
            script {
                error("Pipeline failed")
            }
        }
        always {
            script {
                // Clean up unused Docker resources
                sh 'docker system prune -f'
            }
        }
    }
}