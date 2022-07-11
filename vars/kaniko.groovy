import jenkins.StaticUtils

def build(List<String> destinationsList) {
    echo "Build stage"

    String destinationsCmdSnippet = ""
    for (destination in destinationsList) {
        if (StaticUtils.containsIllegalCharacter(destination)) { return }
        newDestination = " --destination " + destination + " "
        destinationsCmdSnippet += newDestination
    }
    sh """
        /kaniko/executor --dockerfile ./Dockerfile \
                        --context . \
                        --verbosity debug \
                        --no-push \
                        ${destinationsCmdSnippet} \
                        --tarPath image.tar
    """
}