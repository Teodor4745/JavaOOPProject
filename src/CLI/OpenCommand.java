package CLI;

import Editor.TextEditor;

class OpenCommand implements Command {
    private final String[] command;
    private final TextEditor editor;

    public OpenCommand(String[] command,TextEditor editor) {
        this.editor = editor;
        this.command = command;
    }


    @Override
    public void execute() {
        try {
            editor.openFile(command);
        } catch (Exception e) {
            System.out.println("Error while reading or validating file");
            new CloseCommand(editor).execute();
        }
    }
}