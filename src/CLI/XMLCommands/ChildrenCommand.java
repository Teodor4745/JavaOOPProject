package CLI.XMLCommands;

import CLI.GeneralCommands.Command;
import Editor.XMLEditor;

/**
 *  When an instance is created and the execute method is executed
 *  the passed in constructor XMLEditor's children method is executed
 */
public class ChildrenCommand implements Command {
    private final XMLEditor xmlEditor;
    private final String[] command;

    public ChildrenCommand(XMLEditor xmlEditor,String[] command) {
        this.xmlEditor = xmlEditor;
        this.command = command;
    }


    @Override
    public void execute() {
        if(xmlEditor == null){
            System.out.println("No file has been opened");
        }
        else{
            xmlEditor.children(command);
        }
    }
}