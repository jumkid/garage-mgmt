pipeline {
  agent any
  tools {
    maven '3.9.9'
  }
  stages {
    stage('Build') {
      steps {
        git(url: 'https://github.com/jumkid/garage-mgmt.git', branch: 'main', credentialsId: 'chooli-github-pass')
        sh 'mvn clean complie'
        sh 'find . | sed -e "s/[^-][^\\/]*\\// |/g" -e "s/|\\([^ ]\\)/|-\\1/"'
      }
    }

  }
}
