package CLI.GeneralCommands;

import Editor.TextEditor;

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