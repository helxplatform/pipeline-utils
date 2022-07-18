package jenkins

class StaticUtils implements Serializable {

    static boolean containsIllegalCharacter(String input) {
        return (input.contains(';') || input.contains('&'))
    }

    static transformSlashToUnderscore(String input) {
        return input.replaceAll('\\/', '_')
    }

}