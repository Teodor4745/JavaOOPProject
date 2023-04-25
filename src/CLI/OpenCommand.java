package CLI;

import Editor.TextEditor;

class OpenCommand implements Command {
    private String[] command;
    private TextEditor editor;

    public OpenCommand(String[] command,TextEditor editor) {
        this.editor = editor;
        this.command = command;
    }


    @Override
    public void execute() {
        editor.openFile(command);
    }
}