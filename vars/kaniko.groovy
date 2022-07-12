import jenkins.StaticUtils

def build(List<String> destinationsList) {
    echo "Build stage"

    String destinationsCmdSnippet = ""
    for (destination in destinationsList) {
        if (StaticUtils.containsIllegalCharacter(destination)) { return }
        newDestination = " --destination " + destination + " "
        destinationsCmdSnippet += newDestination
    }
    // Using string interpolation is fine for plaintext variables, but never
    // use it for secrets. Those secrets can be unwittingly logged to the 
    // console. See here for more: 
    // https://www.jenkins.io/doc/book/pipeline/jenkinsfile/#injection-via-interpolation
    sh """
        /kaniko/executor --dockerfile ./Dockerfile \
                        --context . \
                        --verbosity debug \
                        --no-push \
                        $destinationsCmdSnippet \
                        --tarPath image.tar
    """
}