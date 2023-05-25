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
    public String openFile(String[] command) {
        if (fileOpened) {
            return "File already opened. Close the current file to open a new one.";
        }

        if (command.length < 2) {
            return "Please specify a file to open.";
        }

        String filename = command[1];
        file = new File(filename);

        if (!file.exists()) {
            try {
                File file = new File(filename);
                if(filename.contains("/")){
                    if (!file.getParentFile().exists())
                        file.getParentFile().mkdirs();
                    if (!file.exists())
                        file.createNewFile();
                }
                fileOpened = true;
                return "No existing file with that name \n creating new file : " + filename;
            } catch(Exception e) {
                return "No existing file with that name \n Error creating file with that name";
            }
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            content = "";
            while ((line = reader.readLine()) != null) {
                content += line + "\n";
            }
            reader.close();
            fileOpened = true;
        } catch (IOException e) {
            return "Error opening file: " + e.getMessage();
        }

        if(Objects.equals(content, "")){
            return "XML file is empty";
        }
        XMLEditor xmlEditor = new XMLEditor();
        xmlEditor.open(content);
        this.xmlEditor = xmlEditor;
        content = xmlEditor.getFileContent();
        return "File opened: " + filename;
    }

    /**
     * This method is used only for user experience. The file is closed already in the open command after
     * its content has been extracted. The method simply just lets the user think that he closes the file.
     * The fields are then cleared because a new file is expected to be opened.
     */
    public String closeFile() {
        if (!fileOpened) {
            return "No file opened. Open a file to start editing.";
        }
        content = "";
        xmlEditor = null;
        fileOpened = false;
        return "File closed.";
    }

    /**
     * This method saves the modified String content to the file that has been opened.
     */
    public String saveFile() {
        if (!fileOpened) {
            return "No file opened. Open a file to start editing.";
        }

        try {
            FileWriter writer = new FileWriter(file);
            writer.write(content);
            writer.close();
            return "File saved: " + file.getName();
        } catch (IOException e) {
           return "Error saving file: " + e.getMessage();
        }
    }

    /**
     * @param command this parameter is used to extract the filepath from the user input.
     * This method saves the String content into a new file, whose path is specified by the user input.
     */
    public String saveFileAs(String[] command) {
        if (!fileOpened) {
            return "No file opened. Open a file to start editing.";
        }

        if (command.length < 2) {
            return "Please specify a filename to save as.";
        }

        String filename = command[1];
        File newFile = new File(filename);

        try {
            FileWriter writer = new FileWriter(newFile);
            writer.write(content);
            writer.close();
            return "File saved as: " + newFile.getName();
        } catch (IOException e) {
            return "Error saving file: " + e.getMessage();
        }
    }

    public void setContent(String content) {
        this.content = content;
    }

    public XMLEditor getXmlEditor() {
        return xmlEditor;
    }

    public void setFileOpened(boolean fileOpened) {
        this.fileOpened = fileOpened;
    }
}

