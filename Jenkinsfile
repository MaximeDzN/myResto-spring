pipeline {
    agent any

    environment {
        AWS_ACCESS_KEY_ID     = credentials('AWS_ACCESS_KEY_ID')
        AWS_SECRET_ACCESS_KEY = credentials('AWS_SECRET_ACCESS_KEY')
    }


    stages {
    
        stage('Terraform init') {           
            steps {
                sh 'echo ${AWS_ACCESS_KEY_ID}'
                sh 'terraform init -input=false'
            }
        }
    
  }
}