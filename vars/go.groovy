def ccv(String repoRemoteUrl, String repoName) {
    withEnv(["REPO_REMOTE_URL=repoRemoteUrl", "REPO_NAME=repoName"]) {
        sh '''
        git clone ${REPO_REMOTE_URL}
        cd ${REPO_NAME}
        go install github.com/smlx/ccv@v0.3.2
        cat <<EOF > ccv.sh
        #!/bin/bash
        if [ -z $(git tag -l $(ccv)) ]; then
            git tag $(ccv)
            git tag   # testing
            git remote set origin ${REPO_REMOTE_URL}
            # git push --tags
            # git push origin --tags
        fi
        EOF
        chmod +x ccv.sh
        ./ccv.sh
        '''
    }
}