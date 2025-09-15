import assembler.Assembler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String fileName = "test";
        List<String> asmStatements = new ArrayList<>();

        try {
            asmStatements = Files.readAllLines(Paths.get(fileName + ".asm"));
        } catch (IOException ex) {
            System.out.println("ERROR: IO error occurred when trying to read <" + fileName + ".asm>");
        }

        Assembler assembler = new Assembler(asmStatements);
        System.out.println(assembler.assemble());
    }
}
