def ccv(String repoRemoteUrl, String repoName) {
    withEnv(["REPO_REMOTE_URL=${repoRemoteUrl}", "REPO_NAME=${repoName}"]) {
        return sh(returnStdout: true, script: '''
            git clone ${REPO_REMOTE_URL} > /dev/null
            cd ${REPO_NAME}
            git switch master > /dev/null
            /usr/local/go/bin/go install github.com/smlx/ccv@v0.3.2 > /dev/null 2>&1
            TAG=$(/go/bin/ccv)
            if [ -z $(git tag -l $TAG) ]; then
                git tag $TAG > /dev/null
                git remote add origin ${REPO_REMOTE_URL}
                git push origin --tags > /dev/null
            fi
            echo $TAG
        ''')
    }
}