def build(List<String> destinationsList) {
    destinationsCmdSnippet = ""
    destinationsList.forEach(destination ->
        newDestination = " --destination " + destination + " "
        destinationsCmdSnippet += newDestination
    )
    sh '''
        /kaniko/executor --dockerfile ./Dockerfile \
                        --context . \
                        --verbosity debug \
                        --no-push \
                        ${destinationsCmdSnippet} \
                        --tarPath image.tar
    '''
}