package CLI.XMLCommands;

import CLI.Command;
import Editor.XMLEditor;

/**
 *  When an instance is created and the execute method is executed
 *  the passed in constructor XMLEditor's print method is executed
 */
public class PrintCommand implements Command {
    private final XMLEditor xmlEditor;

    public PrintCommand(XMLEditor xmlEditor) {
        this.xmlEditor = xmlEditor;
    }


    @Override
    public String execute() {
        if(xmlEditor == null){
            return "No file has been opened";
        }
        else{
            return xmlEditor.print();
        }
    }
}
