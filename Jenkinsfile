pipeline {
    agent any
    tools {
        maven "mavenHome"
    }

    stages {
        stage('Checkout The Sourcecode') {
            steps {
                echo 'checkout the Source code form the github'
                git 'https://github.com/Abhijeet41/JetFoodRecipe.git'
            }
        }
        stage('compile'){
            steps{
                echo 'Compilation of the source code'
                bat 'mvn compile'
            }
        }
        stage('test'){
            steps{
                 echo 'Testing of the source code'
                 bat 'mvn test'
            }
        }
        stage('QA'){
            steps{
                 echo 'QA of the source code'
                 bat 'mvn pmd:pmd'
            }
        }
        stage('Package'){
            steps{
                 echo 'Package the source code'
                 bat 'mvn package'
                
            }
        }
    }
}