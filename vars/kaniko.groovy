def build(List<String> destinationsList) {
    echo "Build stage"

    String destinationsCmdSnippet = ""
    for (destination in destinationsList) {
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