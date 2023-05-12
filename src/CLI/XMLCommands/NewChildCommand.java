package CLI.XMLCommands;

import CLI.GeneralCommands.Command;
import Editor.TextEditor;
import Editor.XMLEditor;

/**
 *  When an instance is created and the execute method is executed
 *  the passed in constructor XMLEditor's newChild method is executed
 *  Since changes are done to the XML file, we also pass a textEditor in the constructor
 *  which will be used for saving the new structure
 */
public class NewChildCommand implements Command {
    private final XMLEditor xmlEditor;
    private final String[] command;
    private final TextEditor textEditor;

    public NewChildCommand(TextEditor textEditor,XMLEditor xmlEditor, String[] command) {
        this.textEditor = textEditor;
        this.xmlEditor = xmlEditor;
        this.command = command;
    }


    @Override
    public void execute() {
        if(xmlEditor == null){
            System.out.println("No file has been opened");
        }
        else{
            xmlEditor.newChild(command);
            this.textEditor.setContent(xmlEditor.getFileContent());
        }
    }
}