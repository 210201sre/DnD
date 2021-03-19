pipeline{
    agent any
    environment{
        //can declare different environment variables to be used specifically within the pipeline
        DOCKER_IMAGE_NAME = 'eilonwy/dungeons-and-dragons'
        MAVEN_IMAGE_NAME = 'log-aggregation-demo:0.0.1-SNAPSHOT'
    }

    stages{
        stage('Build'){
            steps{
                sh 'chmod +x mvnw && ./mvnw spring-boot:build-image'
                sh 'docker tag $MAVEN_IMAGE_NAME $DOCKER_IMAGE_NAME'
                script{
                    app = docker.image(DOCKER_IMAGE_NAME)
                }
            }
        }

        stage('Sonar Quality Analysis'){
            withSonarQubeEnv(credentialsId: 'sonar-token', installationNaame: 'sonarcloud'){
                sh 'mvn -B verify org.sonarsource.scanner.maven:sonar-maven-plugin:sonar'
            }
        }

        stage('Wait for Quality Gate'){
            timeout(time: 30, unit: 'MINUTES'){
                def qg = waitForQualityGate abortPipeline: true
            }
        }

        stage('Push Docker Image'){
            when{
                // Only execute the docker push state if we are on the master branch
                branch 'master'
            }
            steps{
                script{
                    docker.withRegistry('https://registry.hub.docker.com', 'docker-jenkins-token'){
                        app.push('latest') // These are the image tags
                        app.push('${env.BUILD_NUMBER}')
                        app.push('env.GIT_COMMIT')
                    }
                }
            }
        }
    }

    post{
        always{
            // can use the previousl create quality gate variable
            // can include the results as part of a fiscord send instruction
        }
    }
}