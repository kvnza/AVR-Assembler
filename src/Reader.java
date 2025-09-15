package reader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Reader {
    private final String fileName;

    public Reader(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Converts this instance's file content to a list of strings.
     *
     * @return A list of strings representing each line of the file.
     * @throws IOException If fileName.asm does not exist.
     */
    public List<String> read() throws IOException {
        return Files.readAllLines(Paths.get(fileName + ".asm"));
    }
}
