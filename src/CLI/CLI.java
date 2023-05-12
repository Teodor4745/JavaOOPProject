package CLI;

import CLI.GeneralCommands.*;
import Editor.TextEditor;
import CLI.XMLCommands.*;

import java.util.Scanner;

/**
 * This class resembles the Command Line Interface
 * It takes different commands as text input from the user and executes them.
 * The commands are predefined classes. Every time a command is executed, an instance of the specific class is created.
 */
public class CLI {
    private static final TextEditor editor = new TextEditor();

    /**
     * The different commands take different arguments in their constructors.
     * If they must access the file that has been opened they take the TextEditor instance as an argument.
     * If the command must use specific XML logic it also takes the XMLEditor instance as an argument.
     * Also, to get the specific information in the command, the command is also passed.
     */
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