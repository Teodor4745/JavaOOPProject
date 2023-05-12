package CLI.XMLCommands;

import CLI.GeneralCommands.Command;
import Editor.XMLEditor;

/**
 *  When an instance is created and the execute method is executed
 *  the passed in constructor XMLEditor's executeXpath method is executed
 */
public class XpathCommand implements Command {

    private final XMLEditor xmlEditor;
    private final String[] command;

    public XpathCommand(XMLEditor xmlEditor,String[] command) {
        this.xmlEditor = xmlEditor;
        this.command = command;
    }


    @Override
    public void execute() {
        if(xmlEditor == null){
            System.out.println("No file has been opened");
        }
        else{
            try{
                xmlEditor.executeXpath(command);
            } catch (Exception e) {
                System.out.println("Invalid Xpath syntax");
            }

        }
    }
}

