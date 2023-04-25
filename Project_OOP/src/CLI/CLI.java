package CLI;

import Editor.TextEditor;
import XMLCommands.*;

import java.util.Scanner;

public class CLI {
    private static TextEditor editor = new TextEditor();

    public static void run(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input;

        while (true) {
            System.out.print("> ");
            input = scanner.nextLine();
            String[] command = input.split("\\s+");

            switch (command[0]) {
                case "open":
                    new OpenCommand(command,editor).execute();
                    break;
                case "close":
                    new CloseCommand(editor).execute();
                    break;
                case "save":
                    new SaveCommand(editor).execute();
                    break;
                case "saveas":
                    new SaveAsCommand(editor,command).execute();
                    break;
                case "help":
                    new HelpCommand().execute();
                    break;
                case "exit":
                    new ExitCommand().execute();
                    break;
                case "child":
                    new ChildCommand().execute();
                    break;
                case "print":
                    new PrintCommand(editor.getXmlEditor()).execute();
                    break;
                case "select":
                    new SelectCommand(editor.getXmlEditor(),command).execute();
                    break;
                case "set":
                    new SetCommand(editor,editor.getXmlEditor(),command).execute();
                    break;
                case "children":
                    new ChildrenCommand(editor.getXmlEditor(),command).execute();
                    break;
                    default:
                    System.out.println("Invalid command. Type 'help' to see available commands.");
                    break;
            }
        }
    }
}