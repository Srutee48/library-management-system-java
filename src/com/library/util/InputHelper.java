package com.library.util;
import java.util.Scanner;

public class InputHelper {
    private Scanner scanner;

    public InputHelper(){
        this.scanner = new Scanner(System.in);
    }

    public String getNonEmptyString(String prompt){
        String input;
        while( true ){
            System.out.print(prompt);
            input = scanner.nextLine().trim();
            if( !input.isEmpty()){
                return input;
            }
            System.out.println("Input cannot be empty. Please try again.");
        }
    }

    public int getValidInt(String prompt){
        while(true){
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try{
                return Integer.parseInt(input);
            } catch ( NumberFormatException e){
                System.out.println("Invalid number. please enter digits only ");
            }
        }
    }

    public void close(){
        scanner.close();
    }
}
