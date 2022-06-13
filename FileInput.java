import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * FileInput class receives input data from the filename entered from the user's keyboard and parses the data so that
 * each word can be stored separately in the "tempArray" array during processing and added to "inputList" ArrayList.
 * After, "inputList" is converted to "words" array and is sent to BinaryTree class to be inserted into the BST.
 */
public class FileInput
{
    private static ArrayList<String> inputList = new ArrayList<String>(); // input file storage ArrayList: used for dynamic input
    private static String[] tempArray; // temporary array that is used to parse data for "inputList".
    private static String[] words; // stores each word individually after parsing and is sent to class BinaryTree

    /**
     * @param args is a String array but is not used as there are no command line arguments being entered.
     */
    public static void main(String[] args)
    {
        System.out.println("Please enter the input filename:");
        Scanner getFileName = new Scanner(System.in); // initialize object to read the user's input
        String fileName = getFileName.nextLine(); // create line for user to enter the filename

        ParseInputFile(fileName); // method for reading and parsing the input file data
        convertToStringArray(); // converts "inputList" to "words" array
        BinaryTree binaryTreeStructure = new BinaryTree(words, fileName); // send BinaryTree all tempArray seperated and the fileName
    }

    /**
     * @param fileName is a String which represents the filename the user entered. ParseInputFile class parses data
     * by delimiting each word in tempArray by using whitespaces. Whitespaces are the default delimiter of the Scanner
     * class and include spaces between words. Hyphens are also delimited through the use of split() and separate multiple
     * words containing hyphens.
     */
    public static void ParseInputFile(String fileName)
    {
        Scanner readFile = null; // allow for closing Scanner object in finally block
        try
        {
            File file = new File(fileName); // create new file
            readFile = new Scanner(file); // create object to read file
            while(readFile.hasNext()) // iterate until next whitespace which includes delimiting spaces between words (default)
            {
                String inputWord = readFile.next(); // String for an input word between each whitespace
                if(inputWord.contains("-")) // word(s) containing at least one hyphen
                {
                    String[] inputArray = inputWord.split("-"); // separate word(s) with hyphens between them and put into array
                    for(String word: inputArray) // iterate through all word(s) that are now seperated
                    {
                        if(!(word.length() == 0)) // in case there are spaces added as a String to "inputArray"
                        {
                            // remove punctuation, convert the word to a lowercase String, and store into first element in "tempArray"
                            tempArray = word.replaceAll("[^0-9a-zA-Z]", "").toLowerCase().split("\\s+");
                            tempArray[0] = tempArray[0].trim(); // trim leading and trailing spaces
                            inputList.add(tempArray[0]); // add trimmed word to "inputList"
                        }
                    }
                }
                else // word that does not contain any hyphens
                {
                    // remove punctuation, convert the word to a lowercase String, and store into first element in "tempArray"
                    tempArray = inputWord.replaceAll("[^0-9a-zA-Z]", "").toLowerCase().split("\\s+");
                    tempArray[0] = tempArray[0].trim(); // trim leading and trailing spaces of the one word
                    inputList.add(tempArray[0]); // add trimmed word to "inputList"
                }
            }
            for(String word: inputList) // check "inputList" elements from console after file is read
            {
                System.out.println(word);
            }
            System.out.println();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if(readFile != null) // if Scanner object read the file
            {
                readFile.close(); // close Scanner object
            }
        }
    }

    /**
     * convertToStringArray simply converts "inputList" to the String array, "words".
     */
    public static void convertToStringArray()
    {
        words = inputList.toArray(new String[0]); // convert ArrayList to String[]
    }
}

