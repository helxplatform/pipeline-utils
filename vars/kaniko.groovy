import jenkins.StaticUtils

def build(List<String> destinationsList) {
    echo "Build stage"

    String destinationsCmdSnippet = ""
    for (destination in destinationsList) {
        if (StaticUtils.containsIllegalCharacter(destination)) { return }
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