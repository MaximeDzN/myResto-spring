pipeline {
    agent any

    environment {
        AWS_ACCES_KEY_ID     = credentials('AWS_ACCES_KEY_ID')
        AWS_SECRET_ACCESS_KEY = credentials('AWS_SECRET_ACCESS_KEY')
    }


    stages {
    
        stage('Terraform init') {           
            steps {
                sh 'echo ${AWS_ACCES_KEY_ID}'
                sh 'Terraform init -input=false'
            }
        }
    
  }
}