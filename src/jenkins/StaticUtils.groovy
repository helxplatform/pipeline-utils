package jenkins

class StaticUtils implements Serializable {

    static boolean containsIllegalCharacter(String input) {
        if (input.contains(';') || input.contains('&')) {
            sh "echo 'An input string contains either ';' or '&', which is not allowed. Exiting...'"
            return true
        } else {
            return false
        }
    }

    static transformSlashToDash(String input) {
        return input.replaceAll("/", "-")
    }

}