def publish(config) {
    tagsPushAlwaysCmd = ""
    tagsPushForDevelopBranchCmd = ""
    tagsPushForMasterBranchCmd = ""
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
        echo ${config.branchName}
        echo ${tagsPushForMasterBranchCmd}
    """
    sh """
        echo "Publish stage"
        echo "${config.registryPsw}" | crane auth login -u ${config.registryUser} --password-stdin ${config.registry}
        ${tagsPushAlwaysCmd}
    """
}