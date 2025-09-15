package core.io;

import core.Assembler;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class OutputHandler {
    // Prevent initialisation.
    private OutputHandler() {}

    /**
     * Displays a message for the user when the program is initially run.
     */
    public static void initial() {
        System.out.println("Welcome to Jassembler! Type 'help' to view a list of valid commands.");
    }

    /**
     * Prompts the user to write a command.
     */
    public static void prompt() {
        String hostname = "User";
        try {
            hostname = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {}

        System.out.print("\n[" + hostname + "] $~ ");
    }

    /**
     * Outputs a list of valid commands to the user.
     */
    public static void help() {
        System.out.println("['help'] A list of valid commands.\n['assemble *file*'] Converts an" +
                "AVR assembly file to machine code and saves it into the 'assembled/'" +
                "directory.\n['read *file*'] Outputs the assembled file into a readable" +
                "format.\n['exit'] Exit the program.");
    }

    /**
     * Reads an assembled file.
     *
     * @param filename The name of the file (no extension) that the user wants to read.
     */
    public static void read(String filename) {
        List<String> content;
        try {
            content = Files.readAllLines(Paths.get(filename + ".txt"));
        } catch (IOException e) {
            System.out.println("ERROR: Failed to retrieve/read file.");
            return;
        }

        for (String line : content) {
            System.out.println(line);
        }
    }

    /**
     * Displays an error to the user if their command was invalid.
     *
     * @param command The invalid command that the user has entered.
     */
    public static void invalidCommand(String command) {
        System.out.println("ERROR: '" + command + "' is an invalid command!");
    }

    /**
     * Organises for an AVR assembly file to be converted to machine code and saved in a file.
     *
     * @param filename The file which is being assembled.
     * @requires The file is a valid AVR assembly file saved with the '.asm' extension.
     */
    public static void assemble(String filename) {
        List<String> asmStatements;
        Path filePath = Paths.get(filename + ".asm");

        try {
            asmStatements = Files.readAllLines(filePath);
        } catch (IOException e) {
            System.out.println("ERROR: '" + filename + ".asm' is not a valid assembly file.");
            return;
        }

        Assembler assembler = new Assembler(asmStatements);
        List<String> assembled = assembler.assemble();

        if (FileCreator.createAssembledFile(assembled, filename)) {
            System.out.println("Successfully wrote '" + filename + ".txt'!");
        } else {
            System.out.println("ERROR: Failed to create '" + filename + ".txt'!");
        }
    }
}
