package Commands;

public class ExitCommand implements Command {
    @Override
    public void execute() {
        // Implement exit logic here
        System.out.println("Exiting program");
        System.exit(0);
    }
}