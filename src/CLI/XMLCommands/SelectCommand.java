package CLI.XMLCommands;

import CLI.Command;
import Editor.XMLEditor;

/**
 *  When an instance is created and the execute method is executed
 *  the passed in constructor XMLEditor's select method is executed
 */
public class SelectCommand implements Command {
    private final XMLEditor xmlEditor;
    private final String[] command;

    public SelectCommand(XMLEditor xmlEditor,String[] command) {
        this.xmlEditor = xmlEditor;
        this.command = command;
    }


    @Override
    public String execute() {
        if(xmlEditor == null){
           return "No file has been opened";
        }
        else{
           return xmlEditor.select(command);
        }
    }
}
