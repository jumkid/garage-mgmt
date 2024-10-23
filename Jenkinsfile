pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        git(url: 'https://github.com/jumkid/garage-mgmt.git', branch: 'main', credentialsId: 'chooli-github-pass')
        sh 'mvn clean compile'
        sh 'find . | sed -e "s/[^-][^\\/]*\\// |/g" -e "s/|\\([^ ]\\)/|-\\1/"'
      }
    }

  }
  tools {
    maven '3.9.9'
  }
}