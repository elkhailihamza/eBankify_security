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

                    // Set up a Docker Compose override file (optional) to use dynamic environment variables
                    writeFile(file: 'docker-compose.override.yml', text: """
    version: '3.8'
    services:
      app:
        image: ${dockerImageTag}
        container_name: ${containerName}
        ports:
          - "8083:8083"
        networks:
          - ebankify-network
        environment:
          - SPRING_PROFILES_ACTIVE=prod
        depends_on:
          - db

      db:
        image: postgres:13
        container_name: db
        environment:
          POSTGRES_DB: ebankify_security
          POSTGRES_USER: root
          POSTGRES_PASSWORD: ;(.314Luiv./
        ports:
          - "5432:5432"
        networks:
          - ebankify-network

    networks:
      ebankify-network:
        driver: bridge
                        """)

                        // Stop and remove any existing container using the dynamic container name
                        sh """
                            if [ "\$(docker ps -q -f name=${containerName})" ]; then
                                docker stop ${containerName}
                                docker rm ${containerName}
                            fi
                        """

                        // Start the application and its dependencies (DB) with Docker Compose
                        sh 'docker-compose -f docker-compose.override.yml up -d'
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
            // Clean up containers if needed
            sh 'docker-compose down --volumes --remove-orphans'
        }
    }
}