package core.io;

import java.io.InputStream;
import java.util.Scanner;

/**
 * Handles the input from the user.
 */
public final class InputHandler {
    private final Scanner scanner;

    public InputHandler(InputStream in) {
        OutputHandler.initial();
        scanner = new Scanner(in);
        inputLoop();
    }

    public void inputLoop() {
        while (true) {
            OutputHandler.prompt();
            String command = getCommand();

            // Check if the user wants to exit.
            if (command.split(" ")[0].equals("exit")) {
                break;
            }

            // Checks command is valid.
            if (!validateCommand(command)) {
                OutputHandler.invalidCommand(command);
                continue;
            }

            executeCommand(command);
        }
    }

    /**
     * Gets the command that the user has entered.
     *
     * @return The command entered by the user.
     */
    public String getCommand() {
        return scanner.nextLine();
    }

    /**
     * Validates the command that the user has entered.
     *
     * @param command The command to be validated.
     * @return True if command is valid, false otherwise.
     */
    public boolean validateCommand(String command) {
        String[] splitCommand = command.split(" ");
        String operation = splitCommand[0];
        String[] validOperations = {"help", "assemble", "read", "exit"};
        boolean valid = false;

        // Check if operation is valid.
        for (String validOperation : validOperations) {
            if (operation.equals(validOperation)) {
                valid = true;
            }
        }

        // Check if the number of args is valid for the operation.
        if (!operation.equals("help") && !operation.equals("exit") && splitCommand.length == 2) {
            valid = true;
        }

        if ((operation.equals("help") || operation.equals("exit")) && splitCommand.length == 1) {
            valid = true;
        }

        return valid;
    }

    /**
     * Executes the current command that the user has entered.
     *
     * @param command The command to be executed.
     */
    public void executeCommand(String command) {
        String[] splitCommand = command.split(" ");
        String operation = splitCommand[0];

        if (operation.equals("help")) {
            OutputHandler.help();
        } else if (operation.equals("assemble")) {
            OutputHandler.assemble(splitCommand[1]);
        } else if (operation.equals("read")) {
            OutputHandler.read(splitCommand[1]);
        }
    }
}
