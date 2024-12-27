pipeline {
    agent any

    environment {
        AUTHOR1 = "Muhammad Faqih Al Fadholi"
        AUTHOR2 = "Ahmad Rifai"
        DOCKER_IMAGE = 'faqihfadholi/calculator-web'
        DOCKER_TAG      = 'latest'
        CONTAINER_NAME = 'calculator-container'
        PORT_MAPPING = '8089:8080'
        VOLUME_DATA = 'calculator-data'
    }

    options {
        disableConcurrentBuilds()
        timeout(time: 10, unit: 'MINUTES')
    }
    stages {
        stage('Preparation') {
            steps {
                echo 'Preparing the environment'
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
                echo 'Building the project'
                powershell '.\\mvnw clean package -DskipTests'
            }
        }
        stage('Test') {
            steps {
                echo 'Running tests'
                powershell '.\\mvnw test'
            }
             post {
                 always {
                     junit '/target/surefire-reports/*.xml'
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
        stage('Build Docker Image') {
            steps {
                script {
                    powershell """
                        if (docker images -q ${DOCKER_IMAGE}:${DOCKER_TAG}) {
                            docker rmi ${DOCKER_IMAGE}:${DOCKER_TAG} -f
                        }
                    """
                    docker.build("${DOCKER_IMAGE}:${DOCKER_TAG}", "-f Dockerfile .")
                }
            }
        }

        stage('Build and Running Container') {
            steps {
                script {
                   powershell """
                    docker stop ${CONTAINER_NAME} 
                    docker rm ${CONTAINER_NAME} 
                     """
                    def runArgs = "-d -p ${PORT_MAPPING} --name ${CONTAINER_NAME} -v ${VOLUME_DATA}:/app/data"
                    docker.image("${DOCKER_IMAGE}:${DOCKER_TAG}").run(runArgs)
                }
            }
        }
    }
    post {
        always {
            echo 'Cleaning up'
            cleanWs()
        }
    }
}