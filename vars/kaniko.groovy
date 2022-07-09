def build(List<String> destinationsList) {
    echo "Build stage"

    String destinationsCmdSnippet = ""
    for (destination in destinationsList) {
        if (destination.contains(';') || destination.contains('&')) {
            print "Build push destination string contains either ';' or '&', which is not allowed. Exiting..."
            return
        }
        newDestination = " --destination " + destination + " "
        destinationsCmdSnippet += newDestination
    }
    withEnv(["DESTINATIONS_CMD_SNIPPET=${destinationsCmdSnippet}"]) {
        sh '''
            /kaniko/executor --dockerfile ./Dockerfile \
                            --context . \
                            --verbosity debug \
                            --no-push \
                            $DESTINATIONS_CMD_SNIPPET \
                            --tarPath image.tar
        '''
    }
}