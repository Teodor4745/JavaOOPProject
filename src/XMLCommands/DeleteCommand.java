package XMLCommands;

import CLI.Command;
import Editor.TextEditor;
import Editor.XMLEditor;

public class DeleteCommand implements Command {
    private final XMLEditor xmlEditor;
    private final String[] command;
    private final TextEditor textEditor;

    public DeleteCommand(TextEditor textEditor,XMLEditor xmlEditor, String[] command) {
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
            xmlEditor.deleteAttribute(command);
            this.textEditor.setContent(xmlEditor.getFileContent());
        }
    }
}
