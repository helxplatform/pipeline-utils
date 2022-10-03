def ccv(String repoRemoteUrl, String repoName) {
    withEnv(["REPO_REMOTE_URL=${repoRemoteUrl}", "REPO_NAME=${repoName}"]) {
        sh '''
        git clone ${REPO_REMOTE_URL}
        cd ${REPO_NAME}
        /usr/local/go/bin/go install github.com/smlx/ccv@v0.3.2
        TAG=$(/go/bin/ccv)
        if [ -z $(git tag -l $TAG) ]; then
            git tag $TAG
            git remote set origin ${REPO_REMOTE_URL}
            git push origin --tags
        fi
        '''
    }
}