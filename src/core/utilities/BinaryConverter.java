package core.utilities;

/**
 * A utility class used to convert decimal, hex, and octal values to binary representations.
 */
public final class BinaryConverter {
    // Prevent instantiation.
    private BinaryConverter() {}

    /**
     * Converts a decimal value to unsigned binary.
     *
     * @param num A decimal number.
     * @return The unsigned binary representation of num.
     * @requires num >= 0.
     */
    public static String decimalToBinary(int num) {
        if (num == 0) {
            return "0";
        }

        String binary = "";

        while (num > 0) {
            binary = (num % 2) + binary;
            num /= 2;
        }

        return binary;
    }

    /**
     * Converts a hexadecimal value to unsigned binary.
     *
     * @param hex A hexadecimal number.
     * @return The unsigned binary representation of hex.
     * @requires hex is a valid hexadecimal number.
     */
    public static String hexToBinary(String hex) {
        String binary = "";

        // Convert each hex digit to a 4-bit binary value.
        for (int i = 0; i < hex.length(); i++) {
            char hexDigit = hex.charAt(i);
            int decimalValue = 0;

            if ('A' <= hexDigit && hexDigit <= 'F') {
                decimalValue = hexDigit - 'A' + 10;
            } else if ('a' <= hexDigit && hexDigit <= 'f') {
                decimalValue = hexDigit - 'a' + 10;
            } else if ('0' <= hexDigit && hexDigit <= '9') {
                decimalValue = hexDigit - '0';
            }

            String nibble = decimalToBinary(decimalValue);

            // Makes sure the nibble is made of exactly 4 bits.
            while (nibble.length() < 4) {
                nibble = "0" + nibble;
            }

            binary += nibble;
        }

        // Strip leading 0s.
        return binary.replaceFirst("^0+(?!$)", "");
    }

    /**
     * Converts an octal value to unsigned binary.
     *
     * @param octal An octal number.
     * @return The unsigned binary representation of octal.
     * @requires octal is a valid octal number.
     */
    public static String octalToBinary(String octal) {
        String binary = "";

        // Convert each octal digit to a 3-bit binary value;
        for (int i = 0; i < octal.length(); i++) {
            char octalDigit = octal.charAt(i);
            String tripleBit = decimalToBinary(octalDigit - '0');

            // Make sure each digit represents exactly 3 bits.
            while (tripleBit.length() < 3) {
                tripleBit = "0" + tripleBit;
            }

            binary += tripleBit;
        }

        // Strip leading 0s;
        return binary.replaceFirst("^0+(?!$)", "");
    }
}
