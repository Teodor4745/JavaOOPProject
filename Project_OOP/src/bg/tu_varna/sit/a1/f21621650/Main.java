package bg.tu_varna.sit.a1.f21621650;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    //Test command line

    public static void main(String[] args) {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String input = "";
        try {
            while (!input.equalsIgnoreCase("exit")) {
                input = in.readLine();
                if(input.equals("open")) {
                    System.out.println("Successfully opened file");
                }
                else if(input.equals("close")) {
                    System.out.println("successfully closed file");
                }
                else if(input.equals("save")) {
                    System.out.println("Successfully saved file");
                }
                else if(input.equals("save as")){
                    System.out.println("Successfully saved file as file");
                }
                else if(input.equals("help")){
                    System.out.println("The following commands are supported: " + "\n" +
                            "open          <file> opens <file> " + "\n" +
                            "close         closes currently opened file " + "\n" +
                            "save          saves the currently open file " + "\n" +
                            "saveas <file>   saves the currently open file in <file> " + "\n" +
                            "help          prints this information " + "\n" +
                            "exit          exists the program ");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
