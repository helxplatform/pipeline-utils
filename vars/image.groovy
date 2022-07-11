import jenkins.StaticUtils

def publish(imageTagsPushAlways = [], imageTagsPushForDevelopBranch = [], imageTagsPushForMasterBranch = []) {
    String tagsToPushAlwaysCmd = ''
    String tagsToPushForDevelopBranchCmd = ''
    String tagsToPushForMasterBranchCmd = ''
    for (tag in imageTagsPushAlways) {
        if (StaticUtils.containsIllegalCharacter(tag)) { return }
        cmd = 'crane push image.tar ' + tag + '; '
        tagsToPushAlwaysCmd += cmd
    }
    for (tag in imageTagsPushForDevelopBranch) {
        if (StaticUtils.containsIllegalCharacter(tag)) { return }
        cmd = 'crane push image.tar ' + tag + '; '
        tagsToPushForDevelopBranchCmd += cmd
    }
    for (tag in imageTagsPushForMasterBranch) {
        if (StaticUtils.containsIllegalCharacter(tag)) { return }
        cmd = 'crane push image.tar ' + tag + '; '
        tagsToPushForMasterBranchCmd += cmd
    }
    sh """
        echo "Publish stage"
        echo "\$DOCKERHUB_CREDS_PSW" | crane auth login -u \$DOCKERHUB_CREDS_USR --password-stdin \$REGISTRY
        ${tagsToPushAlwaysCmd}
        if [ \$BRANCH_NAME == "develop" ]; then
            ${tagsToPushForDevelopBranchCmd}
        elif [ \$BRANCH_NAME == "master" ]; then
            ${tagsToPushForMasterBranchCmd}
            if [ $(git tag -l "\$VERSION") ]; then
                error "ERROR: Tag with version \$VERSION already exists! Exiting."
            else
                # Recover some things we've lost since the build stage:
                git config --global user.email "helx-dev@lists"
                git config --global user.name "rencibuild rencibuild"
                grep url .git/config
                git checkout \$BRANCH_NAME

                # Set the tag
                SHA=$(git log --oneline | head -1 | awk '{print \$1}')
                git tag \$VERSION \$COMMIT_HASH
                git remote set-url origin \$REPO_REMOTE_URL
                git push origin --tags
            fi
        fi
    """
}