pipeline {
    agent any
    tools {
        maven 'maven'
        jdk 'jdk-17'
    }

    environment {
        WORKSPACE = "/var/lib/jenkins/workspace/ebankify-deploy"
        dockerImageTag = "ebankify-app:${env.BUILD_NUMBER}" // Match Dockerfile naming
        containerName = "ebankify-container-${env.BUILD_NUMBER}" // Generate unique container name
        DB_CONTAINER = "ebankify-db" // Database container name
        SONAR_PROJECT_KEY = "ebankify-app" // Unique project key for SonarQube
    }

    stages {
        stage('Clone Repo') {
            steps {
                script {
                    try {
                        deleteDir()
                        echo "Cloning Git repository..."
                        checkout([$class: 'GitSCM',
                            branches: [[name: '*/develop']],
                            userRemoteConfigs: [[
                                url: 'https://github.com/elkhailihamza/eBankify_security'
                            ]]])
                        echo "Repository cloned successfully."
                    } catch (Exception e) {
                        error "Failed to clone repository: ${e.getMessage()}"
                    }
                }
            }
        }

        stage('Setup Docker Network') {
            steps {
                script {
                    echo "Creating Docker network if it doesn't exist..."
                    sh """
                        docker network create cicd-network || true
                    """
                }
            }
        }

        stage('Start Database') {
            steps {
                script {
                    sh """
                        # Remove existing container if it exists
                        docker rm -f ${DB_CONTAINER} || true

                        docker run -d --name ${DB_CONTAINER} \
                            --network cicd-network \
                            --network-alias postgres \
                            -e POSTGRES_USER=admin \
                            -e POSTGRES_PASSWORD=admin \
                            -e POSTGRES_DB=main_db \
                            -p 5434:5432 postgres:15

                        sleep 10
                    """
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    echo "Building Docker image: ${dockerImageTag}"
                    // Build the Docker image using the correct context (workspace directory)
                    docker.build("${dockerImageTag}", ".")
                }
            }
        }

        stage('SonarQube Analysis') {  // Add SonarQube scan stage
            steps {
                script {
                    echo "Running SonarQube analysis"
                    withSonarQubeEnv('sonarqube') {
                        sh """
                            mvn clean verify sonar:sonar \
                                -Dsonar.projectKey=${SONAR_PROJECT_KEY} \
                                -Dsonar.java.binaries=target/classes \
                        """
                    }
                }
            }
        }

        stage('Deploy Docker') {
            steps {
                script {
                    echo "Deploying Docker Image: ${dockerImageTag}"

                    // Stop and remove the existing application container if it exists
                    sh """
                        if [ "\$(docker ps -q -f name=${containerName})" ]; then
                            docker stop ${containerName}
                            docker rm ${containerName}
                        fi
                    """

                    // Run the newly built Docker image, linking it to the database container and using the network
                    sh """
                        docker run -d --name ${containerName} \
                            --network cicd-network \
                            -e SPRING_DATASOURCE_URL=jdbc:postgresql://ebankify-db:5432/main_db \
                            -e SPRING_DATASOURCE_USERNAME=admin \
                            -e SPRING_DATASOURCE_PASSWORD=admin \
                            -p 8083:8083 ${dockerImageTag}
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
    }
}