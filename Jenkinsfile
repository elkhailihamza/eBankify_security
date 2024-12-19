node {
    def WORKSPACE = "/var/lib/jenkins/workspace/ebankify-deploy";
    def dockerImageTag = "ebankify-deploy${env.BUILD_NUMBER}";

    try {
        stage('Clone Repo') {
            git url: 'https://github.com/elkhailihamza/eBankify_security',
                credentialsId: 'github-credentials',
                branch: 'develop'
        }

        stage('Build Docker Image') {
            sh "docker build -t ebankify-deploy:${dockerImageTag} ."
        }

        stage('Deploy Docker') {
            echo "Docker Image Tag Name: ${dockerImageTag}"
            sh "docker stop ebankify-deploy || true && docker rm ebankify-deploy || true"
            // Updated port to match the Dockerfile (port 8083)
            sh "docker run --name ebankify-deploy -d -p 8083:8083 ebankify-deploy:${env.BUILD_NUMBER}"
        }
    } catch (e) {
        error("Pipeline failed: ${e.message}")
    } finally {
        stage('Cleanup') {
            sh 'docker system prune -f'
        }
    }
}