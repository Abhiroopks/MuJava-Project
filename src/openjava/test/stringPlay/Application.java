// 
// Decompiled by Procyon v0.5.36
// 

package openjava.test.stringPlay;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.io.IOException;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Application
{
    public void go() {
        final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            while (true) {
                System.out.println("Enter an option:\n0: Quit.\n1: Break the string into substrings (words) using spaces as separators.\n2: Count the number of characters, numeric digits, alphabetic characters, and other characters.\n3: Invert the String.\n4: Count the unique occurances of each word using punctuation and spaces as separators.\n5: Convert to uppercase.\n6: Convert to lowercase.");
                final String input = br.readLine();
                if (input.equals("0")) {
                    break;
                }
                if (input.equals("1")) {
                    this.subString(this.askForString(br));
                }
                else if (input.equals("2")) {
                    this.charCount(this.askForString(br));
                }
                else if (input.equals("3")) {
                    this.invert(this.askForString(br));
                }
                else if (input.equals("4")) {
                    this.uniqueOccurrences(this.askForString(br));
                }
                else if (input.equals("5")) {
                    this.toUppercase(this.askForString(br));
                }
                else if (input.equals("6")) {
                    this.toLowercase(this.askForString(br));
                }
                else {
                    System.out.println("Enter option 1-6, or 0 to quit.");
                }
                System.out.println();
            }
            br.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    protected String askForString(final BufferedReader br) {
        System.out.println("Enter a string up to 1000 characters:");
        String result = "";
        try {
            result = br.readLine();
            if (result.length() > 1000) {
                throw new Exception("String must be less than 1000 characters.");
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (Exception e2) {
            e2.printStackTrace();
        }
        return result;
    }
    
    protected void subString(final String val) {
        final StringTools input = new StringTools(val);
        String word = "";
        for (int i = 0; i < input.length(); ++i) {
            if (input.getCharAt(i) == ' ') {
                System.out.println(word);
                word = "";
            }
            else {
                word += input.getCharAt(i);
            }
        }
    }
    
    protected void charCount(final String val) {
        final StringTools input = new StringTools(val);
        int alphabeticCount = 0;
        int numericCount = 0;
        int otherCount = 0;
        for (int i = 0; i < input.length(); ++i) {
            if (String.valueOf(input.getCharAt(i)).matches("\\d")) {
                ++numericCount;
            }
            else if (String.valueOf(input.getCharAt(i)).matches("[a-z]")) {
                ++alphabeticCount;
            }
            else if (String.valueOf(input.getCharAt(i)).matches("\\S")) {
                ++otherCount;
            }
        }
        System.out.println("Alphabetic: " + alphabeticCount);
        System.out.println("Numeric: " + numericCount);
        System.out.println("Other: " + otherCount);
    }
    
    protected void invert(final String val) {
        final StringTools input = new StringTools(val);
        for (int i = input.length(); i >= 0; --i) {
            System.out.print(input.getCharAt(i));
        }
    }
    
    protected void toUppercase(final String val) {
        final StringTools input = new StringTools(val);
        for (int i = 0; i < input.length(); ++i) {
            final char letter = input.getCharAt(i);
            if (String.valueOf(letter).matches("[A-Za-z0-9 ]")) {
                System.out.print(Character.toUpperCase(letter));
            }
        }
    }
    
    protected void uniqueOccurrences(final String val) {
        final LinkedHashMap<String, Integer> seen = new LinkedHashMap<String, Integer>();
        for (final String word : val.split("([.,!?:;'\"-]|\\s)")) {
            if (seen.containsKey(word)) {
                seen.put(word, seen.get(word) + 1);
            }
            else {
                seen.put(word, 1);
            }
        }
        for (final String w : seen.keySet()) {
            System.out.println(seen.get(w) + " " + w);
        }
    }
    
    protected void toLowercase(final String val) {
        final StringTools input = new StringTools(val);
        for (int i = 0; i < input.length(); ++i) {
            final char letter = input.getCharAt(i);
            if (String.valueOf(letter).matches("[A-Z]")) {
                System.out.print((char)(letter + ' '));
            }
            else {
                System.out.print(letter);
            }
        }
    }
}
