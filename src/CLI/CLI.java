package CLI;

import Editor.TextEditor;
import XMLCommands.*;

import java.util.Scanner;

public class CLI {
    private static final TextEditor editor = new TextEditor();

    public static void run() {
        Scanner scanner = new Scanner(System.in);
        String input;
        boolean isRunning = true;

        while (isRunning) {
            System.out.print("> ");
            input = scanner.nextLine();
            String[] command = input.split("\\s+");

            switch (command[0]) {
                case "open" -> new OpenCommand(command, editor).execute();
                case "close" -> new CloseCommand(editor).execute();
                case "save" -> new SaveCommand(editor).execute();
                case "saveas" -> new SaveAsCommand(editor, command).execute();
                case "help" -> new HelpCommand().execute();
                case "exit" -> isRunning = false;
                case "child" -> new ChildCommand(editor.getXmlEditor(), command).execute();
                case "print" -> new PrintCommand(editor.getXmlEditor()).execute();
                case "select" -> new SelectCommand(editor.getXmlEditor(), command).execute();
                case "set" -> new SetCommand(editor, editor.getXmlEditor(), command).execute();
                case "children" -> new ChildrenCommand(editor.getXmlEditor(), command).execute();
                case "text" -> new TextCommand(editor.getXmlEditor(), command).execute();
                case "newchild" -> new NewChildCommand(editor, editor.getXmlEditor(), command).execute();
                case "delete" -> new DeleteCommand(editor, editor.getXmlEditor(), command).execute();
                case "xpath" -> new XpathCommand(editor.getXmlEditor(), command).execute();
                default -> System.out.println("Invalid command. Type 'help' to see available commands.");
            }
        }
    }
}