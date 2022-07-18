package jenkins

class StaticUtils implements Serializable {

    static boolean containsIllegalCharacter(String input) {
        return (input.contains(';') || input.contains('&'))
    }

    static String transformSlashToUnderscoreInTag(String input) {
        int indexOfColon = input.indexOf(':')
        if (indexOfColon != -1) {
            String[] substrings = input.split(':')
            int numberOfSubstrings = substrings.length
            substrings[numberOfSubstrings - 1] = substrings.last().replaceAll('\\/', '_')
            return String.join(":", substrings)
        } else {
            return input
        }
    }

}