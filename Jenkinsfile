pipeline {
  agent any
  stages {
    stage('Code Submit') {
      steps {
        git(url: 'https://github.com/jumkid/garage-mgmt.git', branch: 'master', credentialsId: 'chooli-github-pass')
      }
    }

  }
}