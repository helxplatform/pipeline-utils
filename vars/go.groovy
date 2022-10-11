/*
Runs the CCV utility against the master branch and pushes any new tags.
 */
def ccv(String githubCredsPsw, String repoName) {
    environment {
        GITHUB_CREDS_PSW = githubCredsPsw
        REPO_NAME = repoName
    }
    return sh(returnStdout: true, script: '''
        git clone https://${GITHUB_CREDS_PSW}@github.com/helxplatform/${REPO_NAME}.git
        cd ${REPO_NAME}
        git switch master
        /usr/local/go/bin/go install github.com/smlx/ccv@v0.3.2 > /dev/null 2>&1
        TAG=$(/go/bin/ccv)
        if [ -z $(git tag -l $TAG) ]; then
            git tag $TAG
            git push origin --tags
        fi
        echo $TAG
    ''')
}