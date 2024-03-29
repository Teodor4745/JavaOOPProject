package CLI.GeneralCommands;

import CLI.Command;
import Editor.TextEditor;

/**
 * When an instance of this class is created (with TextEditor object in constructor) and the execute method is executed
 * It executes the editor save method
 */
public class SaveCommand implements Command {
    private final TextEditor editor;

    public SaveCommand(TextEditor editor) {
        this.editor = editor;
    }

    @Override
    public String execute() {
        return editor.saveFile();
    }
}
