package core.utilities;

/**
 * Utilities class that provides some extra functionality for strings that is required for the
 * assembler.
 */
public class StringUtilities {
    /**
     * Substitutes a substring into a text, where each character of the substring replaces each
     * target character respectively.
     *
     * @param text A string to be modified.
     * @param sub A substring to substitute into text.
     * @param target A character that will be substituted
     * @requires sub.length() == the number of times that target appears in text.
     * @return The modified String.
     */
    public static String substituteText(String text, String sub, char target) {
        String result = "";
        int subIterator = 0;

        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == target) {
                result += sub.charAt(subIterator);
                subIterator++;
            } else {
                result += text.charAt(i);
            }
        }

        return result;
    }
}
