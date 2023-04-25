package XMLCommands;

import CLI.Command;
import Editor.TextEditor;
import Editor.XMLEditor;
import org.w3c.dom.Text;

public class SetCommand implements Command {
    private XMLEditor xmlEditor;
    private String[] command;
    private TextEditor textEditor;

    public SetCommand(TextEditor textEditor,XMLEditor xmlEditor, String[] command) {
        this.textEditor = textEditor;
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
            xmlEditor.set(command);
            this.textEditor.setContent(xmlEditor.getFileContent());
        }
    }
}
