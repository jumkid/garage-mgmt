pipeline {
  agent any
  stages {
    stage('Code Submit') {
      steps {
        git(url: 'https://github.com/jumkid/garage-mgmt.git', branch: 'main', credentialsId: 'chooli-github-pass')
        sh 'find . | sed -e "s/[^-][^\\/]*\\// |/g" -e "s/|\\([^ ]\\)/|-\\1/"'
      }
    }

  }
}