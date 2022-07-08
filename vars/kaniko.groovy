def build(List<String> destinationsList) {
    echo "Build stage"
    echo "${destinationsList}"

    destinationsCmdSnippet = ""
    for (destination in destinationsList) {
        newDestination = " --destination " + destination + " "
        destinationsCmdSnippet += newDestination
    }
    echo "${destinationsCmdSnippet}"
    sh """
        /kaniko/executor --dockerfile ./Dockerfile \
                        --context . \
                        --verbosity debug \
                        --no-push \
                        ${destinationsCmdSnippet} \
                        --tarPath image.tar
    """
}