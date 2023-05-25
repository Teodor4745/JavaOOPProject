package CLI.GeneralCommands;

import CLI.Command;
import Editor.TextEditor;

/**
 * When an instance of this class is created (with TextEditor object in constructor) and the execute method is executed
 * it executes the editor saveas method
 */
public class SaveAsCommand implements Command {
    private final String[] filePath;
    private final TextEditor editor;

    public SaveAsCommand(TextEditor editor,String[] command) {
        this.editor = editor;
        this.filePath = command;
    }

    @Override
    public String execute() {
        try {
           return editor.saveFileAs(filePath);
        } catch (Exception e) {
            System.out.println("Error saving file");
        }
        return "";
    }
}
