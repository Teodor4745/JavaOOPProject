package Editor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileEditor {

    private File currentFile;
    private String fileContent;

    public FileEditor() {
        currentFile = null;
        fileContent = "";
    }

    public boolean openFile(String filePath) {
        boolean isOpen;
        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);
            StringBuilder stringBuilder = new StringBuilder();
            while (scanner.hasNextLine()) {
                stringBuilder.append(scanner.nextLine());
                stringBuilder.append(System.lineSeparator());
            }
            scanner.close();
            currentFile = file;
            fileContent = stringBuilder.toString();
            System.out.println("Successfully opened " + currentFile.getName());
            isOpen = true;
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filePath);
            isOpen = false;
        }
        return isOpen;
    }

    public void saveFile() {
        if (currentFile == null) {
            System.out.println("No file is currently open");
            return;
        }
        try {
            FileWriter fileWriter = new FileWriter(currentFile);
            fileWriter.write(fileContent);
            fileWriter.close();
            System.out.println("Successfully saved " + currentFile.getName());
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    public void saveFileAs(String filePath) {
        File file = new File(filePath);
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(fileContent);
            fileWriter.close();
            currentFile = file;
            System.out.println("Successfully saved as " + currentFile.getName());
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    public void closeFile(boolean isOpen) {


        if(isOpen){
            System.out.println("Successfully closed file");
            currentFile = null;
            fileContent = "";
        }
        else {
            System.out.println("There isn't an open file");
        }
    }

    public void displayFileContent() {
        if (currentFile == null) {
            System.out.println("No file is currently open");
            return;
        }
        System.out.println(fileContent);
    }


    public void replaceText(String oldText, String newText) {
        fileContent = fileContent.replaceAll(oldText, newText);
    }

}