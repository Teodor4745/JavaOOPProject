package Editor;
import Parser.DataExtractor;

import java.io.*;

public class TextEditor {
    private File file;
    private boolean fileOpened = false;
    private String content = "";

    private XMLEditor xmlEditor;

    public void openFile(String[] command) {
        if (fileOpened) {
            System.out.println("File already opened. Close the current file to open a new one.");
            return;
        }

        if (command.length < 2) {
            System.out.println("Please specify a file to open.");
            return;
        }

        String filename = command[1];
        file = new File(filename);

        if (!file.exists()) {
            System.out.println("File not found: " + filename);
            return;
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                content += line + "\n";
            }
            reader.close();
            fileOpened = true;
            System.out.println("File opened: " + filename);
        } catch (IOException e) {
            System.out.println("Error opening file: " + e.getMessage());
        }

        if(content == ""){
            System.out.println("XML file is empty");
            return;
        }
        XMLEditor xmlEditor = new XMLEditor();
        xmlEditor.open(content);
        this.xmlEditor = xmlEditor;
        content = xmlEditor.getFileContent();
    }

    public void closeFile() {
        if (!fileOpened) {
            System.out.println("No file opened. Open a file to start editing.");
            return;
        }
        content = "";
        xmlEditor = null;
        fileOpened = false;
        System.out.println("File closed.");
    }

    public void saveFile() {
        if (!fileOpened) {
            System.out.println("No file opened. Open a file to start editing.");
            return;
        }

        try {
            FileWriter writer = new FileWriter(file);
            writer.write(content);
            writer.close();
            System.out.println("File saved: " + file.getName());
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    public void saveFileAs(String[] command) {
        if (!fileOpened) {
            System.out.println("No file opened. Open a file to start editing.");
            return;
        }

        if (command.length < 2) {
            System.out.println("Please specify a filename to save as.");
            return;
        }

        String filename = command[1];
        File newFile = new File(filename);

        try {
            FileWriter writer = new FileWriter(newFile);
            writer.write(content);
            writer.close();
            System.out.println("File saved as: " + newFile.getName());
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isFileOpened() {
        return fileOpened;
    }

    public XMLEditor getXmlEditor() {
        return xmlEditor;
    }
}

