def PROJECT_NAME = "api-start"
def gitUrl = "https://github.com/oscka/${PROJECT_NAME}"
def imgRegistry = "https://registry.hub.docker.com"
def gitOpsUrl = "https://github.com/oscka/gitops-openmsa"
def opsBranch = "main"
/////////////////////////////
pipeline {
     environment {
         PATH = "$PATH:/usr/local/bin/"  //maven, skaffold, argocd,jq path
       }
    agent any
    stages {
        stage('Build') {
            steps {
                checkout scm: [
                        $class: "GitSCM",
                        userRemoteConfigs: [[url: "${gitUrl}",
                        credentialsId: "binam33-git" ]],     //credential 이름이 jenkins에 등록된 이름과 동일해야 함
                        branches: [[name: "refs/tags/${TAG}"]]],
                    poll: false  
                script{
                    docker.withRegistry("${imgRegistry}","imageRegistry-credential"){   //credential 이름이 jenkins에 등록된 이름과 동일해야 함, jenkins에 docker deploy 권한 필요
                        sh "pwd"
                        sh "chmod u+x ./gradlew"
                        sh "skaffold build -p dev -t ${TAG}"
                    }
                    // mac local 일때만 사용 linux 환경에서는 docker.withRegistry 사용
                    //sh "skaffold build -p dev -t ${TAG}"
                }
            }
        }
        stage('workspace clear'){
            steps {
                cleanWs()
            }
        }
        stage('GitOps update') {
            steps {
                    print "======kustomization.yaml tag update====="
                    withCredentials([
                        //gitUsernamePassword(credentialsId: 'git-credential', gitToolName: 'Default')
                        gitUsernamePassword(credentialsId: 'binam33-git', gitToolName: 'Default')
                    ]) {
                        sh """
                        git clone ${gitOpsUrl}
                        cd ./gitops-openmsa/${PROJECT_NAME}/rolling-update
                        kustomize edit set image oscka/${PROJECT_NAME}:${TAG}
                        # 로컬외에는 주석 제거한다
                        git config --global user.email "demo@osckorea.com"
                        git config --global user.name "demo"
                        git add .
                        git commit -am 'update image tag ${TAG}'
                        git remote set-url --push origin ${gitOpsUrl}
                        git push origin ${opsBranch}
                        """
                    }
                    print "git push finished !!!"
                }                    
        }
    }
}