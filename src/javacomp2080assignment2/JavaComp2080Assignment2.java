package javacomp2080assignment2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

public class JavaComp2080Assignment2 {

    public static Scanner input = new Scanner(System.in);

    private static void loading() throws InterruptedException {
        String[] symbols = new String[]{"-", "\\", "|", "/"};
        int count = 0;
        while (true) {
            System.out.print("\r");
            System.out.print("Loading ");
            Thread.sleep(270);
            System.out.print(symbols[count % symbols.length]);
            Thread.sleep(540);
            count++;
        }
    }

    /**
     * @throws java.io.FileNotFoundException
     */
    private static void word_import() {

    }

    private static void menu() throws FileNotFoundException, IOException {
        dictionary wl = new dictionary();
        ArrayList<String> txtFile = new ArrayList<String>();

        // import
        File currentDir = new File(".");
        File file = new File(GetPath(currentDir, "wordList.txt"));

        if (file.exists() && !file.isDirectory()) {
            System.out.println("File found");
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                int num = 0;
                while ((line = br.readLine()) != null) {
                    num++;
                    if (num == 1) {
                        continue;
                    }
                    wl.add(line, "< Not defined >");
                    System.out.println("Adding new word: " + line);
                }
            }
        } else {
            System.out.println("File not found");
        }
        int menuChoice = -1;
        String word;
        wordInfo foundWord;
        while (menuChoice != 8) {
            try {
                System.out.print(center("DICTIONARY", 50, "") + "\n" + rjust("\n", 50, "-")
                        + "1: Add new word\n"
                        + "2: Add meaning to a word\n"
                        + "3: Get meaning\n"
                        + "4: Delete word\n"
                        + "5: Dictionary list With Meanings\n"
                        + "6: Dictionary list Without Meanings\n"
                        + "7: Spell check a text file\n"
                        + "8: Exit\n"
                        + "Please enter choice(1-8): ");
                menuChoice = Integer.valueOf(input.nextLine());
                System.out.println(ljust("", 50, "-"));
                switch (menuChoice) {
                    case 1:
                        System.out.print("Enter word: ");
                        String wordInput = input.nextLine().toLowerCase();
                        System.out.print("Enter meaning: ");
                        String meaningInput = input.nextLine();
                        System.out.println(wl.add(wordInput, meaningInput) ? ("Added " + wordInput + " successfully.") : ("Failed to add " + wordInput + "."));
                        break;
                    case 2:
                        System.out.print("Enter word: ");
                        word = input.nextLine();
                        foundWord = wl.Search(word);
                        if (foundWord != null) {
                            System.out.println("Word: " + foundWord.word);
                            System.out.print("Enter new meaning: ");
                            String new_meaning = input.nextLine();
                            wl.addMeaning(word, new_meaning);
                        }
                        break;
                    case 3:
                        System.out.println("Enter word: ");
                        word = input.nextLine();
                        foundWord = wl.Search(word);
                        break;
                    case 4:
                        System.out.println("Enter word: ");
                        String wordDelete = input.nextLine();
                        wl.delete(wordDelete);
                        break;
                    case 5:
                        // Print out the current dictionary's saved words with meanings
                        wl.printDictionary();
                        break;
                    case 6:
                        // Print out the current dictionary's saved words
                        wl.printWordList();
                        break;
                    case 7:
                        System.out.println("Enter the name of the txt file you want to import(ex. filename.txt): ");
                        String fileName = input.nextLine();
                        File spellCheckFile = new File(GetPath(currentDir, fileName));
                        Scanner sc = new Scanner(spellCheckFile);
                        while (sc.hasNext()) {
                            // Remove any punctuation and convert to lowercase 
                            txtFile.add(sc.next().replaceAll("[^a-zA-Z ]", "").toLowerCase());
                        }
                        sc.close();
                        for (String item : txtFile) {
                            if (!wl.exists(item)) {
                                System.out.println(item);
                            }
                        }
                        break;
                }
            } catch (Exception e) {
                System.out.println("Invalid input please try again!");
            }
        }
    }

    public static void main(String[] args) throws IOException {

        menu();
    }

    public static String GetPath(File curr, String targetted_file) {
        String file_path = "";
        File[] files = curr.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                GetPath(file, targetted_file);
            } else if (file.isFile() && (file.getName().equals(targetted_file)) && (file.length() > 1)) {
                file_path += file.getPath().replace("\\", "/").substring(2);
                return file_path;
            }
        }
        return ".";
    }

    public static String ljust(String text, int space, String symbol) {
        if (symbol.equals("")) {
            symbol = " ";
        }
        String output = text;
        for (int i = 0; i < (space - text.length()); i++) {
            output += symbol;
        }
        return output;
    }
    // This method is used to set text right-aligned

    public static String rjust(String text, int space, String symbol) {
        if (symbol.equals("")) {
            symbol = " ";
        }
        String output = "";
        for (int i = 0; i < (space - text.length()); i++) {
            output += symbol;
        }
        output += text;
        return output;
    }
    // This method is used to set text center-aligned

    public static String center(String text, int space, String symbol) {
        if (symbol.equals("")) {
            symbol = " ";
        }
        int sides = (space - text.length()) / 2;
        String output = "";
        for (int i = 0; i < sides; i++) {
            output += symbol;
        }

        output += text;

        int rest = space - output.length();
        for (int i = 0; i < rest; i++) {
            output += symbol;
        }
        return output;
    }
}
