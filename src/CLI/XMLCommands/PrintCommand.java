package CLI.XMLCommands;

import CLI.GeneralCommands.Command;
import Editor.XMLEditor;

public class PrintCommand implements Command {
    private final XMLEditor xmlEditor;

    public PrintCommand(XMLEditor xmlEditor) {
        this.xmlEditor = xmlEditor;
    }


    @Override
    public void execute() {
        if(xmlEditor == null){
            System.out.println("No file has been opened");
        }
        else{
            xmlEditor.print();
        }
    }
}
