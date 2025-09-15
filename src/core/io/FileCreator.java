package core.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Utility class for writing assembled files to the 'assembled/' directory.
 */
public class FileCreator {
    // Prevent initialisation.
    private FileCreator() {}

    /**
     * Writes a file to a given path that contains assembled machine code.
     *
     * @param assembled Machine code to be contained in this new file.
     * @param filename The file that has been assembled.
     * @return True if function was successfully executed, false otherwise.
     */
    public static boolean createAssembledFile(List<String> assembled, String filename) {
        Path filePath = Paths.get(filename + ".txt");

        try {
            Files.write(filePath, assembled);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
