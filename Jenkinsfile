pipeline {
    agent any

    environment {
        AUTHOR1 = "Muhammad Faqih Al Fadholi"
        AUTHOR2 = "Ahmad Rifai"
    }

    options {
        disableConcurrentBuilds()
        timeout(time: 10, unit: 'MINUTES')
    }
    stages {
        stage('Preparation') {
            steps {
                echo 'Preparing the environment.......'
                powershell 'java -version'
                powershell '.\\mvnw -version'
            }
        }

        stage('Environment Info') {
            steps {
                script {
                    echo "Author1 : ${AUTHOR1}"
                    echo "Author2 : ${AUTHOR2}"
                    echo "Job Name: ${env.JOB_NAME}"
                    echo "Build Number: ${env.BUILD_NUMBER}"
                    echo "Workspace: ${env.WORKSPACE}"
                    echo "Node Name: ${env.NODE_NAME}"
                }
            }
        }
        stage('Build') {
            steps {
                echo 'Building the project...'
                powershell '.\\mvnw clean package -DskipTests'
            }
        }
        stage('Test') {
            steps {
                echo 'Running tests........'
                powershell '.\\mvnw test'
            }
             post {
                 always {
                     junit '**/target/surefire-reports/*.xml'
                 }
             }
        }
        stage('Build Status') {
            steps {
                script {
                    currentBuild.displayName = "#${env.BUILD_NUMBER} - Calculator Web"
                    echo "Current Build Status: ${currentBuild.currentResult}"
                }
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying the application...'
                sleep(5)
                powershell 'echo "Deploying application... after 5s"'
            }
        }

    }
    post {

        always {
            echo 'Cleaning up...'
            cleanWs()
        }
    }
}