import jenkins.StaticUtils

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
        fi
    """
}