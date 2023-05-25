package CLI.GeneralCommands;

import CLI.Command;
import Editor.TextEditor;

/**
 * When an instance of this class is created (with TextEditor object in constructor) and the execute method is executed
 * it executes the editor open method
 */
public class OpenCommand implements Command {
    private final String[] command;
    private final TextEditor editor;

    public OpenCommand(String[] command,TextEditor editor) {
        this.editor = editor;
        this.command = command;
    }


    @Override
    public String execute() {
        try {
            return editor.openFile(command);
        } catch (Exception e) {
            editor.setFileOpened(false);
            return "Error while reading or validating file";
        }
    }
}