package CLI;

import Editor.TextEditor;

class SaveAsCommand implements Command {
    private String[] filePath;
    private TextEditor editor;

    public SaveAsCommand(TextEditor editor,String[] command) {
        this.editor = editor;
        this.filePath = command;
    }

    public void execute() {
        editor.saveFileAs(filePath);
    }
}
