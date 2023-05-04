package XMLCommands;

import CLI.Command;
import Editor.XMLEditor;

public class ChildCommand implements Command {
    private final XMLEditor xmlEditor;
    private final String[] command;

    public ChildCommand(XMLEditor xmlEditor,String[] command) {
        this.xmlEditor = xmlEditor;
        this.command = command;
    }


    @Override
    public void execute() {
        if(xmlEditor == null){
            System.out.println("No file has been opened");
        }
        else{
            xmlEditor.child(command);
        }
    }
}
