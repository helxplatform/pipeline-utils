def ccv(String repoRemoteUrl, String repoName) {
    withEnv(["REPO_REMOTE_URL=${repoRemoteUrl}", "REPO_NAME=${repoName}"]) {
        return sh(returnStdout: true, script: '''
            git clone ${REPO_REMOTE_URL}
            cd ${REPO_NAME}
            /usr/local/go/bin/go install github.com/smlx/ccv@v0.3.2 > /dev/null
            TAG=$(/go/bin/ccv) > /dev/null
            if [ -z $(git tag -l $TAG)]; then
                git tag $TAG > /dev/null
                git remote set origin ${REPO_REMOTE_URL}
                git push origin --tags > /dev/null
            fi
            echo $TAG
        ''')
    }
}