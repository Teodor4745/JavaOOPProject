package CLI.GeneralCommands;

import CLI.Command;

/**
 * Prints out help-text
 */
public class HelpCommand implements Command {
    private static final String HELP_TEXT = """
            Available commands:
            open <file>             opens file
            save                    saves the currently open file
            saveas                  saves the currently open file in <file>
            close                   close the current file
            print                   prints xml file content
            select <id> <n>         print attribute by element id and attribute key
            set <id> <key> <value>  print the nth child of an element
            text <id>               print the text of an element
            delete <id> <key>       delete attribute by element id and key
            newchild <id> <name>    add a new child element
            xpath <XPath>           execute XPath
            help                    prints this information
            exit                    exits the program
            """;

    @Override
    public String execute() {
        return HELP_TEXT;
    }
}
