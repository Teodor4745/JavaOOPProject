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
            while (!input.equalsIgnoreCase("stop")) {
                showMenu();
                input = in.readLine();
                if(input.equals("1")) {
                    System.out.println("Wow, you choose 1");
                }
                else if(input.equals("2")) {
                    System.out.println("Wow, you choose 2");
                }
                else if(input.equals("3")) {
                    System.out.println("Wow you choose 3");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showMenu() {
        System.out.println("Enter 1, 2, 3, or \"stop\" to exit");
    }
}
