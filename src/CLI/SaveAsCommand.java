package CLI;

import Editor.TextEditor;

class SaveAsCommand implements Command {
    private final String[] filePath;
    private final TextEditor editor;

    public SaveAsCommand(TextEditor editor,String[] command) {
        this.editor = editor;
        this.filePath = command;
    }

    public void execute() {
        try {
            editor.saveFileAs(filePath);
        } catch (Exception e) {
            System.out.println("Error saving file");
        }
    }
}
