package CLI;

import Editor.TextEditor;

public class ExitCommand implements Command {
    @Override
    public void execute() {
        System.out.println("Exiting program.");
        System.exit(0);
    }
}
