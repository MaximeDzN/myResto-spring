pipeline {
    agent any

    tools {
		maven 'maven'
		terraform 'terraform'
	}

    environment {
        AWS_ACCES_KEY_ID     = credentials('AWS_ACCES_KEY_ID')
        AWS_SECRET_ACCESS_KEY = credentials('AWS_SECRET_ACCESS_KEY')
    }


    stages {
    
        stage('Terraform init') {           
            steps {
                sh 'terraform -help'
                sh 'echo ${AWS_ACCES_KEY_ID}'
                sh 'terraform init -input=false'
            }
        }
    
  }
}