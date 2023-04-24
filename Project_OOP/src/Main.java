
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import Commands.Help;
import Editor.FileEditor;
import Parser.DataExtractor;
import Parser.DataValidator;
import Parser.XMLEditor;
import XML.Element;

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
                        DataExtractor dataExtractor = new DataExtractor();
                        ArrayList<Element> myArrayList = dataExtractor.extract(content);
                        DataValidator dataValidator = new DataValidator();
                        myArrayList = dataValidator.validate(myArrayList);
                        for(int i = 0;i<myArrayList.size();i++){
                            System.out.println(myArrayList.get(i).getTagName());
                            for(Map.Entry<String,String> set : myArrayList.get(i).getAttributes().entrySet()){
                                System.out.println(set.getValue());
                            }
                        }

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
