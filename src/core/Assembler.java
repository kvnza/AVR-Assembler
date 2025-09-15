package core;

import core.utilities.BinaryConverter;
import core.utilities.StringUtilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A class that assembles some properly-written AVR assembly file into machine code.
 */
public final class Assembler {
    private static final Map<String, String> OPCODES = new HashMap<>();
    private final List<String> fileContent;
    private static final int statementLength = 16;

    static {
        OPCODES.put("ldi", "1110KKKKddddKKKK"); // Load immediate.
        OPCODES.put("mov", "001011rdddddrrrr"); // Move between registers.
        OPCODES.put("and", "001000rdddddrrrr"); // Bitwise AND.
        OPCODES.put("or", "001010rdddddrrrr"); // Bitwise OR.
        OPCODES.put("com", "1001010ddddd0000"); // Inversion (One's Complement).
        OPCODES.put("eor", "001001rdddddrrrr"); // Bitwise XOR.
        OPCODES.put("add", "000011rdddddrrrr"); // Addition.
        OPCODES.put("sub", "000110rdddddrrrr"); // Subtraction.
        OPCODES.put("neg", "1001010ddddd0001"); // Negation (Two's Complement).
    }

    public Assembler(List<String> fileContent) {
        this.fileContent = fileContent;
    }

    /**
     * Assembles the fileContent into machine code based on the AVR ISA.
     *
     * @return The machine code that the fileContent assembles into.
     */
    public List<String> assemble() {
        List<String> assembled = new ArrayList<>();

        for (String line: fileContent) {
            assembled.add(build(line));
        }

        return assembled;
    }

    /**
     * Builds a single AVR assembly statement into machine code.
     *
     * @param statement An AVR assembly statement.
     * @return The machine code that the statement assembles into.
     * @requires statement to be a properly written AVR assembly statement.
     */
    public static String build(String statement) {
        String opcode = getOpcode(statement);
        String destinationReg = getDestinationReg(statement);
        String sourceReg = "";
        String constant = "";
        String[] cleaned = cleanStatement(statement);

        // Checks if the statement contains a source register or a constant and grabs those values.
        if (cleaned.length != 2 && cleaned[2].toCharArray()[0] == 'r') {
            sourceReg = getSourceReg(statement);
        } else if (cleaned.length != 2) {
            constant = getConstant(statement);
        }

        // Makes sure constant is exactly 8-bits.
        while (constant.length() < 8) {
            constant = "0" + constant;
        }

        // Makes sure sourceReg is exactly 5-bits.
        while (sourceReg.length() < 5) {
            sourceReg = "0" + sourceReg;
        }

        // Make sure destinationReg is exactly 4 or 5-bits depending on the operation.
        int destinationRegSize;

        if (opcode.indexOf('K') != -1) {
            destinationRegSize = 4;
        } else {
            destinationRegSize = 5;
        }

        while (destinationReg.length() < destinationRegSize) {
            destinationReg = "0" + destinationReg;
        }

        String built = "";

        if (opcode.indexOf('K') != -1) {
            built = StringUtilities.substituteText(opcode, constant, 'K');
        } else if (opcode.indexOf('r') != -1) {
            built = StringUtilities.substituteText(opcode, sourceReg, 'r');
        } else {
            built = opcode;
        }

        built = StringUtilities.substituteText(built, destinationReg, 'd');

        return built;
    }

    /**
     * Gets the opcode from the given command.
     *
     * @param statement An AVR assembly statement.
     * @return The opcode from the AVR assembly statement, containing placeholder values for source/
     *         destination registers and 8-bit constant values.
     * @requires statement is in the form "OP ..."
     */
    public static String getOpcode(String statement) {
        return OPCODES.get(cleanStatement(statement)[0]);
    }

    /**
     * Gets the source register (in binary) from the given statement.
     *
     * @param statement An AVR assembly statement.
     * @return The binary value for the source register in the AVR assembly statement.
     * @requires statement is in the form "OP DEST SRC".
     * @requires Source register is in form "r**" where "**" is an integer.
     */
    public static String getSourceReg(String statement) {
        String sourceReg = cleanStatement(statement)[2];
        String regValue = sourceReg.substring(1);
        return BinaryConverter.decimalToBinary(Integer.parseInt(regValue));
    }

    /**
     * Gets the destination register (in binary) from the given statement.
     * @param statement An AVR assembly statement.
     * @return The binary value for the destination register in the statement.
     * @requires statement is in the form "OP DEST...".
     * @requires Destination register is form "r**" where "**" is an integer.
     */
    public static String getDestinationReg(String statement) {
        String destReg = cleanStatement(statement)[1];
        String regValue = destReg.substring(1);

        // Makes sure that the correct destination register is given based on whether the operation
        // allows for a 4-bit or a 5-bit register value.
        if (getOpcode(statement).indexOf('K') != -1) {
            return BinaryConverter.decimalToBinary(Integer.parseInt(regValue) - 16);
        } else {
            return BinaryConverter.decimalToBinary(Integer.parseInt(regValue));
        }
    }

    /**
     * Gets the constant value (in binary) from the given statement.
     * @param statement An AVR assembly statement.
     * @return The binary value for the constant value in the statement.
     * @requires statement is in the form "OP DEST CONST".
     * @requires Destination register is in form "0x**" for hex where "**" is a hex number, "0**"
     *           for octal where "**" is an octal number, "**" for decimal where "**" is a
     *           decimal number, or "0b**" for binary where "**" is a binary number.
     */
    public static String getConstant(String statement) {
        String constant = cleanStatement(statement)[2];

        if (constant.startsWith("0x")) {
            return BinaryConverter.hexToBinary(constant.substring(2));
        } else if (constant.startsWith("0b")) {
            return constant.substring(2);
        } else if (constant.startsWith("0")) {
            return BinaryConverter.octalToBinary(constant.substring(1));
        } else {
            return BinaryConverter.decimalToBinary(Integer.parseInt(constant));
        }
    }

    /**
     * Trims and splits the statement so that it can be properly parsed when assembling.
     *
     * @param statement An AVR assembly statement.
     * @return An array of Strings that represent each part of the statement.
     */
    public static String[] cleanStatement(String statement) {
        return statement.trim().split("[,\\s]+");
    }
}
