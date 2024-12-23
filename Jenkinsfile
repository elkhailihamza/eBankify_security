pipeline {
    agent any

    environment {
        // Set your environment variables for database and Keycloak URLs if needed
        SPRING_DATASOURCE_URL = 'jdbc:postgresql://localhost:5432/ebankify_security'
        SPRING_DATASOURCE_USERNAME = 'root'
        SPRING_DATASOURCE_PASSWORD = ';(.314Luiv./'
        SONAR_PROJECT_KEY = "ebankify-app"
    }

    stages {
        stage('Clone Repository') {
            steps {
                // Clone the source repository
                git 'https://github.com/elkhailihamza/eBankify_security.git'
            }
        }

        stage('Build & Test with Maven') {
            steps {
                // Run Maven build and tests
                script {
                    sh 'mvn clean install -DskipTests=true'  // Skip tests if they are not required, or remove -DskipTests
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                // Build the Docker image for the Spring Boot app
                script {
                    docker.build('ebankify-security', '-f Dockerfile .')
                }
            }
        }

        stage('Deploy to Docker Compose') {
            steps {
                script {
                    // Start the application using Docker Compose
                    sh 'docker-compose -f docker-compose.yml up -d'
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

        stage('Cleanup') {
            steps {
                // Clean up after deployment
                sh 'docker-compose down'
            }
        }
    }

    post {
        success {
            echo 'Deployment successful!'
        }
        failure {
            echo 'Deployment failed!'
        }
    }
}