package XMLCommands;

import CLI.Command;
import Editor.TextEditor;
import Editor.XMLEditor;

public class SetCommand implements Command {
    private final XMLEditor xmlEditor;
    private final String[] command;
    private final TextEditor textEditor;

    public SetCommand(TextEditor textEditor,XMLEditor xmlEditor, String[] command) {
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
            xmlEditor.set(command);
            this.textEditor.setContent(xmlEditor.getFileContent());
        }
    }
}
