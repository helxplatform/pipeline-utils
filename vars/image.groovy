def publish(config) {
    String tagsPushAlwaysCmd = ""
    String tagsPushForDevelopBranchCmd = ""
    String tagsPushForMasterBranchCmd = ""
    for (tag in config.imageTagsPushAlways) {
        cmd = "crane push image.tar " + tag + "; "
        tagsPushAlwaysCmd += cmd
    }
    for (tag in config.imageTagsPushForDevelopBranch) {
        cmd = "crane push image.tar " + tag + "; "
        tagsPushForDevelopBranchCmd += cmd
    }
    for (tag in config.imageTagsPushForMasterBranch) {
        cmd = "crane push image.tar " + tag + "; "
        tagsPushForMasterBranchCmd += cmd
    }
    sh """
        echo "${tagsPushAlwaysCmd}"
        echo "${tagsPushForDevelopBranchCmd}"
        echo "${tagsPushForMasterBranchCmd}"
    """
    // sh """
    //     echo "Publish stage"
    //     echo "${config.registryPsw}" | crane auth login -u ${config.registryUser} --password-stdin ${config.registry}
    //     ${tagsPushAlwaysCmd}
    //     if [ ${config.branchName} == "develop" ]; then
    //         ${tagsPushForDevelopBranchCmd}
    //     elif [ ${config.branchName} == "master" ]; then
    //         ${tagsPushForMasterBranchCmd}
    //         if [ $(git tag -l "${config.version}") ]; then
    //             error "ERROR: Tag with version ${config.version} already exists! Exiting."
    //         else
    //             # Recover some things we've lost since the build stage:
    //             git config --global user.email "helx-dev@lists"
    //             git config --global user.name "rencibuild rencibuild"
    //             grep url .git/config
    //             git checkout ${config.branchName}

    //             # Set the tag
    //             SHA=$(git log --oneline | head -1 | awk '{print $1}')
    //             git tag ${config.version} ${config.commitHash}
    //             git remote set-url origin ${repoRemoteUrl}
    //             git push origin --tags
    //         fi
    //     fi
    // """
}