
import java.io.File;
import java.util.Scanner;
import Commands.Help;
import Editor.FileEditor;
import Parser.XMLEditor;

public class Main {

    //Test command line

    public static void main(String args[]){
        System.out.println("Please enter command");
        boolean isRunning = true;
        Scanner scanner = new Scanner(System.in);
        String input;
        String[] commands;
        boolean isOpen = false;
        String content;

        while(isRunning){
            input = scanner.nextLine();
            if(input.equals("exit")){
                isRunning = false;
            }
            else if(input.equals("help")){
                Help.displayHelp();
            }

            commands = input.split(" ");
            FileEditor fileEditor = new FileEditor();
            XMLEditor xmlEditor = new XMLEditor();

            if(commands[0].equals("open")){
                if(isOpen){
                    System.out.println("Please close current file before opening a new one");
                }
                else {
                    if(fileEditor.openFile(commands[1]) == null){
                        isOpen = false;
                    }
                    else {
                        System.out.println("Successfully opened " + commands[1]);
                        isOpen = true;
                        content = fileEditor.openFile(commands[1]);
                        System.out.println(content);
                    }
                }



            }

            else if(commands[0].equals("close")){
                fileEditor.closeFile(isOpen);
                isOpen = false;
            }
            else if(commands[0].equals("save")){
                fileEditor.saveFile();
            }
            else if(commands[0].equals("saveas")){
                if(commands.length == 1){
                    System.out.println("Please specify path to save file to");
                }
                else if(commands.length > 2){
                    System.out.println("Too many arguments in command");
                }
                else {
                    fileEditor.saveFileAs(commands[1]);
                }
            }


        }

    }

}
