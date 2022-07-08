def publish(config) {
    imageTagsPushAlwaysCmd = ""
    imageTagsPushForDevelopBranchCmd = ""
    imageTagsPushForMasterBranchCmd = ""
    for (tag in config.imageTagsPushAlways) {
        cmd = "crane push image.tar " + tag + "; "
        imageTagsPushAlwaysCmd += cmd
    }
    for (tag in config.imageTagsPushForDevelopBranch) {
        cmd = "crane push image.tar " + tag + "; "
        imageTagsPushForDevelopBranchCmd += cmd
    }
    for (tag in config.imageTagsPushForMasterBranch) {
        cmd = "crane push image.tar " + tag + "; "
        imageTagsPushForMasterBranchCmd += cmd
    }
    sh """
        echo "Publish stage"
        echo "${config.registryPsw}" | crane auth login -u ${config.registryUser} --password-stdin ${config.registry}
        ${imageTagsPushAlwaysCmd}
        if [ ${config.branchName} == "develop" ]; then
            ${imageTagsPushForDevelopBranchCmd}
        elif [ ${config.branchName} == "master" ]; then
            ${imageTagsPushForMasterBranchCmd}
            if [ $(git tag -l "${config.version}") ]; then
                error "ERROR: Tag with version ${config.version} already exists! Exiting."
            else
                # Recover some things we've lost since the build stage:
                git config --global user.email "helx-dev@lists"
                git config --global user.name "rencibuild rencibuild"
                grep url .git/config
                git checkout ${config.branchName}

                # Set the tag
                SHA=$(git log --oneline | head -1 | awk '{print $1}')
                git tag ${config.version} ${config.sha}
                git remote set-url origin ${repoRemoteUrl}
                git push origin --tags
            fi
        fi
    """
}