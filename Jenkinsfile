pipeline {
    agent any

    environment {
        WORKSPACE = "/var/lib/jenkins/workspace/ebankify-deploy"
        dockerImageTag = "ebankify-deploy${env.BUILD_NUMBER}"
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
                    // Using the Docker plugin to build the image
                    docker.build("ebankify-deploy:${dockerImageTag}")
                }
            }
        }

        stage('Deploy Docker') {
            steps {
                script {
                    echo "Docker Image Tag Name: ${dockerImageTag}"
                    
                    // Use Docker plugin to stop and remove the container if it exists
                    def container = docker.image("ebankify-deploy:${dockerImageTag}")
                    container.remove() // Removes the existing container if it exists

                    // Run the new Docker container with the specified tag
                    container.run('-d -p 8083:8083')
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
                // Clean up unused Docker resources after the pipeline
                sh 'docker system prune -f'
            }
        }
    }
}