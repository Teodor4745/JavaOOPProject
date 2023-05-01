package XMLCommands;

import CLI.Command;
import Editor.XMLEditor;

public class XpathCommand implements Command {

    private XMLEditor xmlEditor;
    private String[] command;

    public XpathCommand(XMLEditor xmlEditor,String[] command) {
        this.xmlEditor = xmlEditor;
        this.command = command;
    }


    @Override
    public void execute() {
        if(xmlEditor == null){
            System.out.println("No file has been opened");
            return;
        }
        else{
            xmlEditor.executeXpath(command);
        }
    }
}

