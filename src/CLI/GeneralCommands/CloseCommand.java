package CLI.GeneralCommands;

import Editor.TextEditor;

/**
 * When an instance of this class is created (with TextEditor object in constructor) and the execute method is executed
 * it executes the editor close method
 */
public class CloseCommand implements Command {
    private final TextEditor editor;

    public CloseCommand(TextEditor editor) {
        this.editor = editor;
    }

    @Override
    public void execute() {
        editor.closeFile();
    }
}