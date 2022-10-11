import jenkins.StaticUtils

/*
Pushes image tags that have already been built.
 */
def publish2(imageTagsToPushAlways = [], imageTagsToPushForDevelopBranch = [], imageTagsToPushForMasterBranch = []) {
    String tagsToPushAlwaysCmd = ''
    String tagsToPushForDevelopBranchCmd = ''
    String tagsToPushForMasterBranchCmd = ''
    for (tag in imageTagsToPushAlways) {
        if (StaticUtils.containsIllegalCharacter(tag)) { return }
        tag = StaticUtils.transformSlashToUnderscoreInTag(tag)
        cmd = 'crane push image.tar ' + tag + ';'
        tagsToPushAlwaysCmd += cmd
    }
    for (tag in imageTagsToPushForDevelopBranch) {
        if (StaticUtils.containsIllegalCharacter(tag)) { return }
        tag = StaticUtils.transformSlashToUnderscoreInTag(tag)
        cmd = 'crane push image.tar ' + tag + ';'
        tagsToPushForDevelopBranchCmd += cmd
    }
    for (tag in imageTagsToPushForMasterBranch) {
        if (StaticUtils.containsIllegalCharacter(tag)) { return }
        tag = StaticUtils.transformSlashToUnderscoreInTag(tag)
        cmd = 'crane push image.tar ' + tag + ';'
        tagsToPushForMasterBranchCmd += cmd
    }
    tagsToPushAlwaysCmd = tagsToPushAlwaysCmd.substring(0, tagsToPushAlwaysCmd.length() - 1)
    tagsToPushForDevelopBranchCmd = tagsToPushForDevelopBranchCmd.substring(0, tagsToPushForDevelopBranchCmd.length() - 1)
    tagsToPushForMasterBranchCmd = tagsToPushForMasterBranchCmd.substring(0, tagsToPushForMasterBranchCmd.length() - 1)
    // Using string interpolation is fine for plaintext variables, but never
    // use it for secrets. Those secrets can be unwittingly logged to the 
    // console. See here for more: 
    // https://www.jenkins.io/doc/book/pipeline/jenkinsfile/#injection-via-interpolation
    // Also, make sure to escape the $ on any new shell environment variable used here.
    sh """
        echo "Publish stage"
        echo "\$DOCKERHUB_CREDS_PSW" | crane auth login -u \$DOCKERHUB_CREDS_USR --password-stdin \$REGISTRY
        $tagsToPushAlwaysCmd
        if [ \$BRANCH_NAME == "develop" ]; then
            $tagsToPushForDevelopBranchCmd
        elif [ \$BRANCH_NAME == "master" ]; then
            $tagsToPushForMasterBranchCmd
        fi
    """
}

def publish(imageTagsToPushAlways = [], imageTagsToPushForDevelopBranch = [], imageTagsToPushForMasterBranch = []) {
    String tagsToPushAlwaysCmd = ''
    String tagsToPushForDevelopBranchCmd = ''
    String tagsToPushForMasterBranchCmd = ''
    for (tag in imageTagsToPushAlways) {
        if (StaticUtils.containsIllegalCharacter(tag)) { return }
        tag = StaticUtils.transformSlashToUnderscoreInTag(tag)
        cmd = 'crane push image.tar ' + tag + '; '
        tagsToPushAlwaysCmd += cmd
    }
    for (tag in imageTagsToPushForDevelopBranch) {
        if (StaticUtils.containsIllegalCharacter(tag)) { return }
        tag = StaticUtils.transformSlashToUnderscoreInTag(tag)
        cmd = 'crane push image.tar ' + tag + '; '
        tagsToPushForDevelopBranchCmd += cmd
    }
    for (tag in imageTagsToPushForMasterBranch) {
        if (StaticUtils.containsIllegalCharacter(tag)) { return }
        tag = StaticUtils.transformSlashToUnderscoreInTag(tag)
        cmd = 'crane push image.tar ' + tag + '; '
        tagsToPushForMasterBranchCmd += cmd
    }
    // Using string interpolation is fine for plaintext variables, but never
    // use it for secrets. Those secrets can be unwittingly logged to the 
    // console. See here for more: 
    // https://www.jenkins.io/doc/book/pipeline/jenkinsfile/#injection-via-interpolation
    // Also, make sure to escape the $ on any new shell environment variable used here.
    sh """
        echo "Publish stage"
        echo "\$DOCKERHUB_CREDS_PSW" | crane auth login -u \$DOCKERHUB_CREDS_USR --password-stdin \$REGISTRY
        $tagsToPushAlwaysCmd
        if [ \$BRANCH_NAME == "develop" ]; then
            $tagsToPushForDevelopBranchCmd
        elif [ \$BRANCH_NAME == "master" ]; then
            $tagsToPushForMasterBranchCmd
            if [ \$(git tag -l "\$VERSION") ]; then
                error "ERROR: Tag with version \$VERSION already exists! Exiting."
            else
                # Recover some things we've lost since the build stage:
                git config --global user.email "helx-dev@lists"
                git config --global user.name "rencibuild rencibuild"
                grep url .git/config
                git checkout \$BRANCH_NAME

                # Set the tag
                SHA=\$(git log --oneline | head -1 | awk '{print \$1}')
                git tag \$VERSION \$COMMIT_HASH
                git remote set-url origin \$REPO_REMOTE_URL
                git push origin --tags
            fi
        fi
    """
}