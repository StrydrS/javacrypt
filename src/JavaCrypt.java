//program is designed to take a text file and a user password. The file is "encrypted" using psuedorandom numbers generated
//from the user's password. The file is then output to another, separate text file, called "output.txt". 
//"output.txt" can also be input to the program to be decrypted, as long as the user enters the original password used with 
//encryption. 

//imports 

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


// main class
public class JavaCrypt {

    // main function
    public static void main(String[] args) {
        //scanner is used to take user input 
        Scanner scanner = new Scanner(System.in);
        
        // takes user input for encrypt or decrypt options
        String userChoice;
        System.out.println("Welcome to the JavaCrypt, an encryption and decryption program");
        System.out.print("To begin, type \"encrypt\" or \"decrypt\": ");
        userChoice = scanner.nextLine().toLowerCase();

        // if encrypt, call encrypt function
        if (userChoice.equals("encrypt")) {
            encrypt();

        // if decrypt, call decrypt function
        } else if (userChoice.equals("decrypt")) {
            decrypt();

        } else {
            System.out.println("Invalid input. Please type \"encrypt\" or \"decrypt\".");
        }
    }

    //converts password string to a number, returns password seed to be used as seed for pseudo random number generator
    private static int passwordToNum(String password) {
        
        String pass = password.toLowerCase();
        int passSeed = 0;
        int len = pass.length();

        //char array containing common characters
        char[] alphabet = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                ' ', '!', '@', '#', '$', '%', '^', '&', '*', '(', ')', ',', '.', '/', ':', ';', '[', ']', '-', '+', '|', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

        //int array with values for each character
        int[] letterToValue = {10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43,
                44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67};
        
        //for loop iterates length of the password
        for (int i = 0; i < len; i++) {
            //iterates the length of the character array
            for (int j = 0; j < 57; j++) {
                //if the character in the password is equal to the character in the array, add the value of the character to the passSeed
                if (pass.charAt(i) == alphabet[j]) {
                    passSeed += letterToValue[j];
                }
            }
        }
        return passSeed;
    }

    //converts the original file to a ArrayList, with each character of the original file as an index of the list
    //takes input of fileName, which is the name of the file you want to encrypt or decrypt
    private static List<Character> fileToVector(String fileName) {
        List<Character> message = new ArrayList<>();
        String s;
        //tries to open the file, if file is not accessible throws FileNotFoundException
        
        try {

            //opens file at the fileName given by the user
            File newf = new File(fileName);
            Scanner scanner = new Scanner(newf);
            //scanner is used to iterate the length of the file, taking each string and then converting it to a char list
            while(scanner.hasNext()){
                s = scanner.next();
                for(char c: s.toCharArray()) {
                    message.add(c);
                }
            }  
        } catch (FileNotFoundException e) {
            System.out.println("File did not open");
            e.printStackTrace();
        }
        //returns char list with each character from the message 
        return message;
    }

    //converts file to a list of numbers, used if the user selects the "decrypt" option
    private static List<String> fileToNumVector(String fileName) {
        List<String> message = new ArrayList<>();
        String line;

        //tries to read file input by user, if the file isn't found throws IOException
        //if file is found, converts file to a string list filled with numbers representing each character of the original message
        try (BufferedReader file = new BufferedReader(new FileReader(fileName))) {
            while ((line = file.readLine()) != null) {
                message.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //returns string list of all random numbers
        return message;
    }

    //creates a list of integers pseudorandomly, based on the passSeed created in the passToNum() function
    private static List<Integer> randomNums(int passSeed) {
        //seeds the random number series to be used for encryption
        Random random = new Random(passSeed);

        List<Integer> randomNumbers = new ArrayList<>();
        
        //creates a random number for each character input option
        for (int i = 0; i < 57; i++) {
            //adds to an arrayList of random numbers
            randomNumbers.add(random.nextInt());
        }

        //returns list of pseudorandom numbers based on user's password
        return randomNumbers;
    }

    //converts encrypted message back into the original message, as long as the user's password
    //is the same as when they encrypted the message.
    private static void decrypt() {
        Scanner scanner = new Scanner(System.in);
    

        String password;
        String fileName;
        List<Integer> randomNumbers;
        List<String> fileNum;
        int passSeed;
        List<Integer> fileNumInt = new ArrayList<>();

        //takes input of file name
        System.out.println("Type the name of the file you wish to decrypt. For example, \"text.txt\"");
        fileName = scanner.next();

        //takes input of password to decrypt with
        System.out.println("Type the password you want to use to decrypt the file.");
        password = scanner.next();

        //converts password to passSeed
        passSeed = passwordToNum(password);

        // uses passSeed to generate pseudorandom numbers 
        randomNumbers = randomNums(passSeed);

        //converts the encrypted file to an arraylist containing each element
        fileNum = fileToNumVector(fileName);

        //iterates the length of the encrypted array, adds converts each element to an integer
        for (String num : fileNum) {
            fileNumInt.add(Integer.parseInt(num));
        }

        char[] alphabet = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                ' ', '!', '@', '#', '$', '%', '^', '&', '*', '(', ')', ',', '.', '/', ':', ';', '[', ']', '-', '+', '|', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

        StringBuilder answer = new StringBuilder();
        
        //iterates the length of the encrypted file array
        for (int z = 0; z < fileNumInt.size(); z++) {
            //iterates the random numbers generated with the password
            for (int q = 0; q < randomNumbers.size(); q++) {
                //if the numbers are the same, (the user enters the correct password), that character is appended to the end of the message.
                if (randomNumbers.get(q).equals(fileNumInt.get(z))) {
                    answer.append(alphabet[q]);
                }
            }
        }

        System.out.println(answer);
    }

    // encrypt function, takes input of a file and outputs the same file, then encrypted using the password the user enters.
    private static void encrypt() {
        Scanner scanner = new Scanner(System.in);

        String fileName;
        String password;
        int passSeed;
        List<Integer> encrypted = new ArrayList<>();
        List<Character> message;
        List<Integer> randomNumbers;

        char[] alphabet = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                ' ', '!', '@', '#', '$', '%', '^', '&', '*', '(', ')', ',', '.', '/', ':', ';', '[', ']', '-', '+', '|', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

        //takes name of file you want to encrypt as user input
        System.out.println("Type the name of the file you wish to encrypt. For example, \"text.exe\"");
        fileName = scanner.nextLine();

        //takes the password the user wants to use to encrypt their file
        System.out.println("Type the password you want to use to decrypt the file.");
        password = scanner.nextLine();

        //converts password to passSeed
        passSeed = passwordToNum(password);

        //converts the file input to an arraylist, containing each character of the message as an index of the arraylist
        message = fileToVector(fileName);

        //creates an arrayList of random numbers using the random function and seed to be used for pseudorandom number generation
        randomNumbers = randomNums(passSeed);

        //iterates each character in the arraylist
        for (char aMessage : message) {
            //iterates the length of the list of characters available 
            for (int q = 0; q < 57; q++) {
                //if the character at the qth index of the message is equal to the character being iterated,
                //add the random number associated with that number to the encrypted message
                if (aMessage == alphabet[q]) {
                    encrypted.add(randomNumbers.get(q));
                }
            }
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter("./output.txt"))) {
            for (Integer num : encrypted) {
                writer.println(num);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("File has been encrypted. output.txt is your encrypted file. Restart the program and decrypt? y or n");
        char anz = scanner.next().charAt(0);
        if (anz == 'y') {
            decrypt();
        } else if (anz == 'n') {
            System.out.println("Exiting the program.");
        } else {
            System.out.println("Invalid input. Exiting program.");
        }
        scanner.close();
    }
}
