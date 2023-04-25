package XMLCommands;

import CLI.Command;
import Editor.TextEditor;
import Editor.XMLEditor;

public class PrintCommand implements Command {
    private XMLEditor xmlEditor;

    public PrintCommand(XMLEditor xmlEditor) {
        this.xmlEditor = xmlEditor;
    }


    @Override
    public void execute() {
        if(xmlEditor == null){
            System.out.println("No file has been opened");
            return;
        }
        else{
            xmlEditor.print();
        }
    }
}
