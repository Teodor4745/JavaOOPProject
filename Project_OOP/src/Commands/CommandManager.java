package Commands;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CommandManager {
    private final Map<String, Command> commands;
    boolean isOpen;

    public CommandManager() {
        commands = new HashMap<>();
        commands.put("open", new OpenCommand(""));
        commands.put("close", new CloseCommand());
        commands.put("save", new SaveCommand());
        commands.put("save as", new SaveAsCommand(""));
        commands.put("exit", new ExitCommand());
    }

    public void executeCommand(String commandName, String arg) {
        Command command = commands.get(commandName);
        if (command instanceof OpenCommand || command instanceof SaveAsCommand) {
            ((FileNameCommand) command).setFileName(arg);
        }
        if(command instanceof OpenCommand){
            isOpen = true;
        }
        else if(command instanceof CloseCommand && isOpen){
            isOpen = false;
        }
        if((command instanceof SaveCommand || command instanceof SaveAsCommand || command instanceof CloseCommand) && !isOpen){
            System.out.println("Cannot execute command: " + commandName + "\n Please open file to execute this command");
        }
        command.execute();
    }

    private interface FileNameCommand {
        void setFileName(String fileName);
    }
}