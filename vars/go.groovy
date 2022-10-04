def ccv(String repoRemoteUrl, String repoName) {
    withEnv(["REPO_REMOTE_URL=${repoRemoteUrl}", "REPO_NAME=${repoName}"]) {
        return sh(returnStdout: true, script: '
            git clone ${REPO_REMOTE_URL}; \
            cd ${REPO_NAME}; \
            /usr/local/go/bin/go install github.com/smlx/ccv@v0.3.2 > /dev/null 2>&1; \
            TAG=$(/go/bin/ccv) > /dev/null 2>&1; \
            if [ -z $(git tag -l $TAG > /dev/null 2>&1)]; then \
                git tag $TAG > /dev/null 2>&1; \
                git remote set origin ${REPO_REMOTE_URL}; \
                git push origin --tags > /dev/null 2>&1; \
            fi \
            echo $TAG \
        ')
    }
}