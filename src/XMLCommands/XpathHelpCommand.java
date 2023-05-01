package XMLCommands;

import CLI.Command;

public class XpathHelpCommand implements Command {
    private static final String xPathHelp = "Available commands:\n" +
            "open <filename> - open a file\n" +
            "close - close the current file\n" +
            "save - save changes to the current file\n" +
            "saveas <filename> - save changes to a new file\n" +
            "help - show this help text\n" +
            "exit - exit the program\n";

    @Override
    public void execute() {
        System.out.println(xPathHelp);
    }
}