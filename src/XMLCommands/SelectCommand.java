package XMLCommands;

import CLI.Command;
import Editor.XMLEditor;

public class SelectCommand implements Command {
    private final XMLEditor xmlEditor;
    private final String[] command;

    public SelectCommand(XMLEditor xmlEditor,String[] command) {
        this.xmlEditor = xmlEditor;
        this.command = command;
    }


    @Override
    public void execute() {
        if(xmlEditor == null){
            System.out.println("No file has been opened");
        }
        else{
            xmlEditor.select(command);
        }
    }
}
