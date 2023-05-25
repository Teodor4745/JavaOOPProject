package CLI.XMLCommands;

import CLI.Command;
import Editor.TextEditor;
import Editor.XMLEditor;

/**
 *  When an instance is created and the execute method is executed
 *  the passed in constructor XMLEditor's deleteAttribute method is executed
 *  Since changes are done to the XML file, we also pass a textEditor in the constructor
 *  which will be used for saving the new structure
 */
public class DeleteCommand implements Command {
    private final XMLEditor xmlEditor;
    private final String[] command;
    private final TextEditor textEditor;

    public DeleteCommand(TextEditor textEditor,XMLEditor xmlEditor, String[] command) {
        this.textEditor = textEditor;
        this.xmlEditor = xmlEditor;
        this.command = command;
    }


    @Override
    public String execute() {
        if(xmlEditor == null){
           return "No file has been opened";
        }
        else{
            String result = xmlEditor.deleteAttribute(command);
            this.textEditor.setContent(xmlEditor.getFileContent());
            return result;
        }
    }
}
