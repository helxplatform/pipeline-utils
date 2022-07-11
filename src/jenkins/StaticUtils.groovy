package jenkins

class StaticUtils implements Serializable {

    static boolean containsIllegalCharacter(String input) {
    if (input.contains(';') || input.contains('&')) {
        print "Build push destination string contains either ';' or '&', which is not allowed. Exiting without publishing..."
        return true
    } else {
        return false
    }
}

}