# 05-skaffold-and-kustomize.md

### Skaffold

JenkinsFile를 확인하면 다음과 같은 부분들이 빌드를 수행하도록 되어 있음을 확인할 수 있다.
```zsh
...
                script{
                    docker.withRegistry("${imgRegistry}","imageRegistry-credential"){   //credential 이름이 jenkins에 등록된 이름과 동일해야 함, jenkins에 docker deploy 권한 필요
                        sh "pwd"
                        sh "chmod u+x ./gradlew"
                        sh "skaffold build -p dev -t ${TAG}"
                    }
                    // mac local 일때만 사용 linux 환경에서는 docker.withRegistry 사용
                    //sh "skaffold build -p dev -t ${TAG}"
                }
...
```

다음과 같이 실행하여 클러스터 상에 빌드된 이미지를 디플로이 하여 실행하거나(run), 이미지를 업로드하거나(build), 디버깅 모드로 실행할 수 있다. 
```zsh
# 디버깅 모드로 실행
skaffold -p dev -t 0.0.19(version)
# 실행
skaffold -p run -t 0.0.19(version)
# 빌드후 이미지 업로드
skaffold -p build -t 0.0.19(version)
```


## Kustomize

kustomize는 어플리케이션의 버전을 이미지에 태깅하여 gitops 상의 kustomization.yaml 파일에 이를 동기화 하는 역할을 한다. CD를 담당하는 argocd와 같은 도구들이 버전 변경을 감지하고 이를 실제 클러스터에 반영해 준다.

```zsh
...
                        git clone ${gitOpsUrl}
                        cd ./gitops-openmsa/${PROJECT_NAME}/rolling-update
                        kustomize edit set image oscka/${PROJECT_NAME}:${TAG}
                        # 로컬외에는 주석 제거한다
                        git config --global user.email "demo@osckorea.com"
                        git config --global user.name "demo"
...
```