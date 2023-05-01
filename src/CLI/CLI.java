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
                    new OpenCommand(command, editor).execute();
                    break;
                case "close":
                    new CloseCommand(editor).execute();
                    break;
                case "save":
                    new SaveCommand(editor).execute();
                    break;
                case "saveas":
                    new SaveAsCommand(editor, command).execute();
                    break;
                case "help":
                    new HelpCommand().execute();
                    break;
                case "exit":
                    new ExitCommand().execute();
                    break;
                case "child":
                    new ChildCommand(editor.getXmlEditor(), command).execute();
                    break;
                case "print":
                    new PrintCommand(editor.getXmlEditor()).execute();
                    break;
                case "select":
                    new SelectCommand(editor.getXmlEditor(), command).execute();
                    break;
                case "set":
                    new SetCommand(editor, editor.getXmlEditor(), command).execute();
                    break;
                case "children":
                    new ChildrenCommand(editor.getXmlEditor(), command).execute();
                    break;
                case "text":
                    new TextCommand(editor.getXmlEditor(), command).execute();
                    break;
                case "newchild":
                    new NewChildCommand(editor, editor.getXmlEditor(), command).execute();
                    break;
                case "delete":
                    new DeleteCommand(editor, editor.getXmlEditor(), command).execute();
                    break;
                case "xpath":
                    new XpathCommand(editor.getXmlEditor(), command).execute();
                    break;
                case "xpathhelp":
                    new XpathHelpCommand().execute();
                default:
                    System.out.println("Invalid command. Type 'help' to see available commands.");
                    break;
            }
        }
    }
}