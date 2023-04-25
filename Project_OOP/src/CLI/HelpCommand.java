package CLI;

import Editor.TextEditor;

public class HelpCommand implements Command {
    private static final String HELP_TEXT = "Available commands:\n" +
            "open <filename> - open a file\n" +
            "close - close the current file\n" +
            "save - save changes to the current file\n" +
            "saveas <filename> - save changes to a new file\n" +
            "help - show this help text\n" +
            "exit - exit the program\n";

    @Override
    public void execute() {
        System.out.println(HELP_TEXT);
    }
}