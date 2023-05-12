package Editor;

import java.io.*;
import java.util.Objects;

/**
 * This class provides the following functionality:
 * open a file, get it's content, close the file,
 * the content can then be edited by the classes implementing the XML logic
 * and finally save the content to the same file or somewhere else
 */
public class TextEditor {
    private File file;
    private boolean fileOpened = false;
    private String content = "";

    private XMLEditor xmlEditor;

    /**
     * @param command this parameter is used to extract the filepath from the user command
     * This method opens a file, extracts its content to the String content and then closes it.
     * Then an XMLEditor is created. Its purpose is to validate the content to follow the rules
     * implied by our project logic.The validated content is returned to the String content.
     */
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
            System.out.println("No existing file: " + filename);
            System.out.println("Creating" + filename);
            try {
                File file = new File(filename);
                if(filename.contains("/")){
                    if (!file.getParentFile().exists())
                        file.getParentFile().mkdirs();
                    if (!file.exists())
                        file.createNewFile();
                }
                System.out.println("File: " + file);
                fileOpened = true;
            } catch(Exception e) {
                e.printStackTrace();
            }
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
        } catch (IOException e) {
            System.out.println("Error opening file: " + e.getMessage());
        }

        if(Objects.equals(content, "")){
            System.out.println("XML file is empty");
            return;
        }
        XMLEditor xmlEditor = new XMLEditor();
        xmlEditor.open(content);
        this.xmlEditor = xmlEditor;
        content = xmlEditor.getFileContent();
        System.out.println("File opened: " + filename);
    }

    /**
     * This method is used only for user experience. The file is closed already in the open command after
     * its content has been extracted. The method simply just lets the user think that he closes the file.
     * The fields are then cleared because a new file is expected to be opened.
     */
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

    /**
     * This method saves the modified String content to the file that has been opened.
     */
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

    /**
     * @param command this parameter is used to extract the filepath from the user input.
     * This method saves the String content into a new file, whose path is specified by the user input.
     */
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

    public void setContent(String content) {
        this.content = content;
    }

    public XMLEditor getXmlEditor() {
        return xmlEditor;
    }
}

