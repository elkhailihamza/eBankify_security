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

        stage ('Deploy docker') {
            echo "Docker Image Tag Name: ${dockerImageTag}",
            sh "docker stop ebankify-deploy || true && docker rm ebankify-deploy || true"
            sh "docker run --name ebankify-deploy -d -p 5000:5000 ebankify-deploy:${env.BUILD_NUMBER}"
        }
    } catch (e) {
        error("Pipeline failed: ${e.message}")
    } finally {
        stage('Cleanup') {
            sh 'docker system prune -f'
        }
    }
}