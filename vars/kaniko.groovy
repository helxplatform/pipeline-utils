import jenkins.StaticUtils

/*
Builds a kaniko image, given a path to a dockerfile and a list of image tags to use as destinations.
 */
def build(String pathToDockerfile, List<String> destinationsList) {
    echo "Build stage"

    if (StaticUtils.containsIllegalCharacter(pathToDockerfile)) { return }

    String destinationsCmdSnippet = ""
    for (destination in destinationsList) {
        if (StaticUtils.containsIllegalCharacter(destination)) { return }
        destination = StaticUtils.transformSlashToUnderscoreInTag(destination)
        singleDestinationCmdSnippet = " --destination " + destination + " "
        destinationsCmdSnippet += singleDestinationCmdSnippet
    }
    // Using string interpolation is fine for plaintext variables, but never
    // use it for secrets. Those secrets can be unwittingly logged to the 
    // console. See here for more: 
    // https://www.jenkins.io/doc/book/pipeline/jenkinsfile/#injection-via-interpolation
    sh """
        /kaniko/executor --dockerfile $pathToDockerfile \
                        --context . \
                        --verbosity debug \
                        --no-push \
                        $destinationsCmdSnippet \
                        --tarPath image.tar
    """
}

/*
Builds a kaniko image and pushes it to harbor, given a path to a dockerfile and a list of image tags to use as destinations.
 */
def buildAndPush(String pathToDockerfile, List<String> destinationsList) {
    echo "Build stage"

    if (StaticUtils.containsIllegalCharacter(pathToDockerfile)) { return }

    String destinationsCmdSnippet = ""
    for (destination in destinationsList) {
        if (StaticUtils.containsIllegalCharacter(destination)) { return }
        destination = StaticUtils.transformSlashToUnderscoreInTag(destination)
        singleDestinationCmdSnippet = " --destination " + destination + " "
        destinationsCmdSnippet += singleDestinationCmdSnippet
    }
    // Using string interpolation is fine for plaintext variables, but never
    // use it for secrets. Those secrets can be unwittingly logged to the 
    // console. See here for more: 
    // https://www.jenkins.io/doc/book/pipeline/jenkinsfile/#injection-via-interpolation
    sh """
        /kaniko/executor --dockerfile $pathToDockerfile \
                        --context . \
                        --verbosity debug \
                        $destinationsCmdSnippet
    """
}