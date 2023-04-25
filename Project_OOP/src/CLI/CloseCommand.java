package CLI;

import Editor.TextEditor;

public class CloseCommand implements Command {
    private TextEditor editor;

    public CloseCommand(TextEditor editor) {
        this.editor = editor;
    }

    @Override
    public void execute() {
        editor.closeFile();
    }
}