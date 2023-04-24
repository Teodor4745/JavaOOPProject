package Commands;

public class SaveAsCommand implements Command {
    private final String fileName;

    public SaveAsCommand(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void execute() {
        // Implement save as logic here
        System.out.println("Saving file as: " + fileName);
    }
}
