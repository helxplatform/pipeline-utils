def ccv(String repoRemoteUrl, String repoName) {
    withEnv(["REPO_REMOTE_URL=${repoRemoteUrl}", "REPO_NAME=${repoName}"]) {
        sh '''
        git clone ${REPO_REMOTE_URL}
        cd ${REPO_NAME}
        /usr/local/go/bin/go install github.com/smlx/ccv@v0.3.2
        cat <<EOF > ccv.sh
        #!/bin/bash
        set -x
        if [ -z $(git tag -l $(/go/bin/ccv)) ]; then
            git tag $(/go/bin/ccv)
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