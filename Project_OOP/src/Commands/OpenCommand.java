package Commands;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class OpenCommand implements Command {
    private final String fileName;

    public OpenCommand(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void execute() {
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);
            StringBuilder stringBuilder = new StringBuilder();
            while (scanner.hasNextLine()) {
                stringBuilder.append(scanner.nextLine());
                stringBuilder.append(System.lineSeparator());
            }
            scanner.close();
            String fileContent = stringBuilder.toString();

        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);
        }

    }
}