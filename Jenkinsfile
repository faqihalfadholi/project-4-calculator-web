pipeline {
    agent any

    environment {
        AUTHOR = "Muhammad Faqih Al Fadholi"
        EMAIL = "faqihalfadholi@gmail.com"
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
                    echo "Author : ${AUTHOR}"
                    echo "Email : ${EMAIL}"
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
            // post {
            //     always {
            //         junit '**/target/surefire-reports/*.xml'
            //     }
            // }
        }
        stage('Build Status') {
            steps {
                script {
                    currentBuild.displayName = "#${env.BUILD_NUMBER} - Custom Build Name"
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