/*
Runs the CCV utility against the master branch and pushes any new tags.
Note: Requires REPO_REMOTE_URL and REPO_NAME to be environment variables
 */
def ccv() {
    return sh(returnStdout: true, script: '''
        mkdir temp
        cd temp
        git clone https://${GITHUB_CREDS_PSW}@github.com/helxplatform/${REPO_NAME}.git > /dev/null
        cd ${REPO_NAME}
        git switch master > /dev/null
        /usr/local/go/bin/go install github.com/smlx/ccv@v0.3.2 > /dev/null 2>&1
        TAG=$(/go/bin/ccv)
        if [ -z $(git tag -l $TAG) ]; then
            git tag $TAG > /dev/null
            git push origin --tags > /dev/null
        fi
        echo -n $TAG
    ''')
}