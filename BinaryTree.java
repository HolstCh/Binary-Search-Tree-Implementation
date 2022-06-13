import java.util.ArrayList;
import java.util.InputMismatchException; // for switch statement which prevents incorrect data type entry
import java.util.Scanner;

/**
 * BinaryTree is the central class which creates the Binary Search Tree for traversal and computations. Each option is
 * available in a switch statement after the filename has been entered and data is inserted from "inputArray".
 */
public class BinaryTree
{
    private final String[] inputArray; // "words" array that was sent from class FileInput and is parsed data
    private final String fileName; // name of file that user inputs from keyboard
    private boolean rootCreated = false; // used to check if root Node was initialized
    private Node root = null; // pointer to first Node
    private int wordCount = 0; // counts all Nodes that are currently in tree
    private int uniqueWordCount = 0; // counts each Node that has frequency = 1
    private int highestFrequency = 0; // stores integer for highest frequency Node while traversing
    private int choice = 0; // integer selection for switch statement in displayTree()
    private final ArrayList<String> highFrequencyWords = new ArrayList<String>(); // list used to compare frequencies while traversing

    /**
     * @param inputArray is a String array which is the "words" String array from FileInput class.
     * @param fileName is a String that represents the fileName, the text extension is removed for display.
     * BinaryTree constructor calls insertAllData() which creates the BST, then displayTree() to activate the
     * switch statement and while loop for traversal and computing options.
     */
    BinaryTree(String[] inputArray, String fileName)
    {
        this.inputArray = inputArray; // array sent from FileInput class
        fileName = fileName.replace(".txt", ""); // removes extension from filename for display
        this.fileName = fileName;  // filename input from user without extension
        insertAllData(); // inserts all words from the inputArray that was passed from class FileInput
        displayTree(root); // displays all traversal and computing options in a switch menu
    }

    /**
     * insertAllData() stores each word from "inputArray" into a Node that is placed into the Binary Search Tree. This
     * method passes the root Node in order to traverse with recursion and find a position for a word value and Node instance.
     */
    public void insertAllData()
    {
        for(String inputWord: inputArray)
        {
            insertWordAndNode(inputWord, root);// inserts element into a new Node or increases frequency of existing Node
        }
    }

    /**
     * @return boolean value which represents if the root has been initialized or not
     */
    public boolean hasRootNode()
    {
        return rootCreated; // "rootCreated" is false by default, once root Node is created, it becomes true.
    }

    /**
     * @param newWord is a String that comes directly from the parsed words in "inputArray".
     * @param currentNode is a Node instance that acts as a temporary reference. The Node starts at the root and then
     * traverses down the left or right side of a parent Node with each recursive call. So, "newWord" to be inserted, is
     * compared with the "word" value from "currentNode" which references each successor Node. The "newWord" is compared and
     * inserted in a Node if it does not exist and a new Node is initialized, or finds a Node that does exist with the same "word"
     * and increases its "frequency" datamember. During each recursive call, a new temporary reference is created from the parent
     * so that "currentNode" becomes the next left child Node or right child Node after the call which depends on the "compare" integer
     * value obtained from compareTo(). Lastly, the base cases are: (1) if the "currentNode's" right child is null, (2)
     * if the "currentNode's" left child is null, (3) if "compare" is 0 where no new Node is initialized for the "newWord" value
     * since a matching "word" exists already.
     */
    /*
        insertWordAndNode() was created with the guidance of the code used from:
        https://www.cs.cornell.edu/courses/cs2112/2018fa/lectures/ which is under the "13.Trees" section. Then, once the
        shortcut is taken, there is an "adding an element to a binary search tree" article and code. Their method was used
        for the the structure of the if statements for controlling flow of a recursive call or inserting a new Node and word;
        however, their method has a boolean return value and uses a generic interface for Node comparisons
        (which are opposite: c < 0 is for left), uses different recursive calls, does not use a second parameter (currentNode)
        for traversing and linking parent/child Nodes: their method only contains a parameter for a data type (T) since generic.
        Also does not use if - else if - else statement structure for (compare == 0) to increase frequency; as well as, an
        else block for the recursive block; however, the else block is inspired by their code. Lastly, their method
        does not contain the if(!hasRootNode()) block for root Node initialization.

        insertWordAndNode() was created with the guidance of the code used from:
        Tutorial session about Binary Search Trees during week 5: "BinarySearchTree.java"
        The recursive calls from addElement() and traversals takes advantage of accessing the left and right datamembers.
        This code uses getter methods to obtain the next temporary reference Node as the access modifier for Node is
        private. Essentially, the recursive calls are based on that code but using different implementation for access.
        This method is different from addElement() and traversals besides the recursive calls.
     */
    public void insertWordAndNode(String newWord, Node currentNode)
    {
        if(!hasRootNode()) // no Nodes exist in BST
        {
            root = new Node(1, newWord); // insert first "word" element and Node instance into BST
            rootCreated = true; // change flag since root Node has been initialized now
        }
        else // else block is used for recursion after first "word" value and Node instance have been inserted
        {
            String referenceWord = currentNode.getWord(); // get the "word" of the current Node for comparison
            int compare = referenceWord.compareTo(newWord); // compares the current Node's "word" to "newWord" using int value

            if(compare < 0) // currentNode's "word" value is lesser than "newWord", so traverse down right side of parent
            {
                if(currentNode.getRightChild() != null) // right child node exists so create a new pointer and do recursive call
                {
                    // when called: "currentNode" = "currentNode.getRightChild()" which creates a new traversed temporary reference
                    insertWordAndNode(newWord, currentNode.getRightChild()); // recursive call for next temp. reference
                }
                else // == null; no right child Node, so can create a Node as right child of parent Node, "currentNode"
                {
                    Node newNode = new Node(1, newWord); // create "newNode" since found position of insertion
                    newNode.setParent(currentNode); // link right child "newNode" to parent "currentNode"
                    currentNode.setRightChild(newNode); // link parent "currentNode" to right child "newNode"
                }
            }
            else if(compare == 0) // currentNode's "word" is the same as "newWord" so increment currentNode's "frequency"
            {
                int theFrequency = currentNode.getFrequency(); // get "frequency" datamember value
                theFrequency++; // increment by 1
                currentNode.setFrequency(theFrequency); // set new "frequency" value for "currentNode"
            }
            else  // (compare > 0) so currentNode's "word" is greater than "newWord", so traverse down left side of parent
            {
                if(currentNode.getLeftChild() != null) // left child node exists so create pointer and do recursive call
                {
                    // when called: "currentNode" = "currentNode.getLeftChild()" which creates a new traversed temp. reference
                    insertWordAndNode(newWord, currentNode.getLeftChild()); // recursive call for next temporary reference
                }
                else // == null; no left child Node, so can create a Node as left child of parent Node, "currentNode"
                {
                    Node newNode = new Node(1, newWord); // create "newNode" since found position of insertion
                    newNode.setParent(currentNode); // link left child "newNode" to parent "currentNode"
                    currentNode.setLeftChild(newNode); // link parent "currentNode" to left child "newNode"
                }
            }
        }
    }

    /**
     * @param currentNode is a Node instance. This method is called from switch (2). The method traverses the BST
     * using in-order traversal. This particular traversal pattern follows left Node, root Node, then right Node.
     */
    /*
       This code is used from:
       Tutorial session about Binary Search Trees during week 5: "BinarySearchTree.java"
       The only difference is this code has the Node type access modifier set as private so getter methods are used
     */
    public void inOrderTraversal(Node currentNode)
    {
        if(currentNode != null)
        {
            inOrderTraversal(currentNode.getLeftChild());
            printWord(currentNode.getWord());
            inOrderTraversal(currentNode.getRightChild());
        }
    }

    /**
     * @param currentNode is a Node instance. This method is called from switch (1). The method traverses the BST using in-order
     * traversal. This method also computes the number of Nodes, how many Nodes that have a frequency of 1, and which Node(s)
     * have the highest frequency while traversing. Each datamember used is reset to 0 each time after the switch statement
     * is selected and the while(true) loop runs again.
     */
    /*
        The following code was created with guidance by code from:
        Tutorial session about Binary Search Trees during week 5. It is the previous method above except everything
        else that is different and has comments is my own.
     */
    public void inOrderTraversalCalculations(Node currentNode)
    {
       if(currentNode != null)
       {
           wordCount++; // increment datamember which counts words/nodes in existing BST
           if(currentNode.getFrequency() == 1) // if the "currentNode" has a "frequency" datamember value of 1
           {
               uniqueWordCount++; // increment datamember that counts unique words (Node(s) with frequency = 1)
           }
           int currentFrequency = currentNode.getFrequency(); // get the frequency of the Node to be compared
           if(currentFrequency >= highestFrequency) // compare the current frequency with the highest frequency so far
           {
               highestFrequency = currentFrequency; // set datamember with highest frequency value
               highFrequencyWords.add(currentNode.getWord()); // add the word to the ArrayList if same freq. or higher
               compareExistingWords(currentFrequency); // compare each word in the ArrayList to see which word(s) have highest freq.
           }
           inOrderTraversalCalculations(currentNode.getLeftChild());
           inOrderTraversalCalculations(currentNode.getRightChild());
       }
    }

    /**
     * @param highestFrequency is an integer that represents the highest frequency out of the Nodes that have been
     * traversed so far. The method iterates through each "commonWord" String in "highFrequencyWords" ArrayList, calls search()
     * to look for and return the Node that has the same "word" datamember value. It dynamically removes words that are
     * now irrelevant if "highestFrequency" has been increased or allows for all words that have same "frequency" value
     * to exist in list.
     */
    public void compareExistingWords(int highestFrequency)
    {
        for(String commonWord: highFrequencyWords) // iterate through each commonWord in String ArrayList for high freq. words
        {
            Node comparisonNode = search(commonWord); // returns Node with the same "word" datamember value as "commonWord"
            if(comparisonNode.getFrequency() < highestFrequency) // compare returned Node frequency with highest freq. value
            {
                highFrequencyWords.remove(comparisonNode.getWord()); // remove word from ArrayList since lesser freq. value
            }
        }
    }

    /**
     * @param word is a String that represents Node's "word" datamember, printWord() simply prints the datamember value
     */
    /*
       This code is used from:
       Tutorial session about Binary Search Trees during week 5: "BinarySearchTree.java"
       The only difference is this code has the Node type access modifier set as private so getter methods are used
     */
    private void printWord(String word)
    {
        System.out.print(word + " ");
    }

    /**
     * @param currentNode is a Node instance. This method is called from switch (3). The method traverses the BST using
     * pre-order traversal. This particular traversal pattern follows root Node, left Node, then right Node.
     */
    /*
       This code is used from:
       Tutorial session about Binary Search Trees during week 5: "BinarySearchTree.java"
       The only difference is this code has the Node type access modifier set as private so getter methods are used
     */
    public void preOrderTraversal(Node currentNode)
    {
        if(currentNode != null)
        {
            printWord(currentNode.getWord());
            preOrderTraversal(currentNode.getLeftChild());
            preOrderTraversal(currentNode.getRightChild());
        }
    }

    /**
     * @param currentNode is a Node instance. This method is called from switch (4). The method traverses the BST using
     * post-order traversal. This particular traversal pattern follows left Node, right Node, then root Node.
     */
    /*
       This code is used from:
       Tutorial session about Binary Search Trees during week 5: "BinarySearchTree.java"
     */
    public void postOrderTraversal(Node currentNode)
    {
        if(currentNode != null)
        {
            postOrderTraversal(currentNode.getLeftChild());
            postOrderTraversal(currentNode.getRightChild());
            printWord(currentNode.getWord());
        }
    }

    /**
     * @param node is a Node instance.
     * @return an integer to represent furthest path down a tree on either side of BST.
     * The method is recursive and traverses down the furthest path on the left side and the right side. Then, compares
     * to see which side has the largest height. This method is called from switch (1).
     */
    /*
       This code is used from:
       Assignment 3 document on page 4.
     */
    public int maxDepth(Node node)
    {
        if(node == null)
        {
            return 0;
        }
        else
        {
            int leftDepth = maxDepth(node.getLeftChild()); // traverse with node = left child
            int rightDepth = maxDepth(node.getRightChild()); // traverse with node = right child

            if(leftDepth > rightDepth) // after all recursive calls, compare to see largest integer which is greatest height
                return(leftDepth + 1); // must count root Node
            else
                return(rightDepth + 1); // must count root Node
        }
    }

    /**
     * displayHighFrequencyWords() is called from switch (1) and iterates through "highFrequencyWords" ArrayList and calls
     * search() for the Node with the same "word" datamember value. Then, prints the "word" and its corresponding "frequency"
     * datamember value.
     */
    public void displayHighFrequencyWords()
    {
        System.out.println("The word(s) which occur(s) most often and the number of times that it/they occur(s) is:");
        for(String frequencyWord: highFrequencyWords) // iterate through ArrayList for high frequency words
        {
            Node foundNode = search(frequencyWord); // return Node from search() if the search word is found
            System.out.println(foundNode.getWord() + " = " + foundNode.getFrequency() + " times"); // print word and freq. datamember values
        }
    }

    /**
     * @param searchWord is a String that is possibly a datamember for a Node instance and is being searched for.
     * @return the Node that is found in the overloaded method or null if no match is found.
     * search() works by first calling the overloaded method with the word being searched for and the root Node.
     * Then, the current Node keeps traversing within the while loop until a Node with the same "word" datamember value
     * is found or until the currentNode becomes null. In the latter case, search() returns null.
     */
    /*
        This method was created with guidance by code used from:
        Tutorial session week 5: "BinarySearchTree.java" and lecture week 5: "Binary Search Trees - Live Coding"
        The structure of using two methods (usually for recursion) was taken from tutorial. However, this case uses
        overloaded methods. The method is similar to search() in lecture. However, instead of recursion,
        I have implemented an iterative version for the comparisons involving Strings instead of Integers.
     */
    public Node search(String searchWord) {return search(searchWord, root);} // overloaded method call to next method
    public Node search(String searchWord, Node currentNode)
    {
        while(currentNode != null) // if currentNode becomes null then no match will be found
        {
            int compare = searchWord.compareTo(currentNode.getWord()); // compare "searchWord" and "word" datamember of currentNode
            if(compare == 0) // match is found so return Node containing the same "word" value
            {
                return currentNode; // found Node that matches the word that was passed in first method
            }
            else if(compare > 0) // traverse down right side of parent Node if not null
            {
                currentNode = currentNode.getRightChild(); // move pointer to refer to next right child Node
            }
            else // (compare < 0) // traverse down left side of parent Node if not null
            {
                currentNode = currentNode.getLeftChild(); // move pointer to refer to Next left child Node
            }
        }
        return null; // returns null if no match is found
    }

    /**
     * @param theRoot is a Node instance that is the root Node. displayTree() is composed of a while loop that runs
     * the switch statement continuously until the program is ended. The switch is composed of all traversal and
     * computing options as desired by the assignment 3 document. By entering an integer (1-6) from the keyboard, the user
     * may select an option. To terminate the program, the user must select integer '6'. The options are displayed below.
     * If the user enters a wrong integer, the loop just starts over with a message in the try block. If the user enters
     * a different data type, the loop over as well but prints a message in the catch block.
     */
    public void displayTree(Node theRoot)
    {
        Scanner reader = null; // allows for closing reading object when selection '6' is chosen
        while(true) // iterates infinitely until system.exit(1) terminates the program or selection '6'
        {
            System.out.println("Please enter one of the integers corresponding to one of the following options:");
            System.out.println("(1) In-order traversal word count, unique word count, highest frequency word(s) and max depth calculations");
            System.out.println("(2) In-order traversal");
            System.out.println("(3) Pre-order traversal");
            System.out.println("(4) Post-order traversal");
            System.out.println("(5) Search for a specific word contained within a Node");
            System.out.println("(6) Exit program");

            try // try-catch block controls error from user input
            {
                reader = new Scanner(System.in); // Scanner object for reading user integer input
                choice = reader.nextInt(); // create a line for user to enter integer input
                if(choice < 0 || choice > 6) // input is integer that is not 1 - 6 inclusive, so prints message and loops again.
                {
                    System.err.println("Sorry, that integer is not from 1 to 6 inclusive. Please enter again.");
                    displayTree(root); // call once more
                }
            }
            catch(InputMismatchException notInteger) // input is not an integer, so prints message and loops again
            {
                System.err.println("Sorry, that is the wrong input as it is not an integer. Please enter an integer from 1 - 6.");
                displayTree(root); // call once more
            }

            switch(choice) // user input is "choice" integer which jumps to each case that corresponds to the integer
            {
                case 1: // computations using in-order traversal
                    inOrderTraversalCalculations(theRoot); // modified in-order traversal method for computations
                    System.out.println("Total number of words in " + fileName + " = " + wordCount); // display word count
                    wordCount = 0; // reset counter for next traversal
                    System.out.println("Number of unique words in " + fileName + " = " + uniqueWordCount); // display unique word count
                    uniqueWordCount = 0; // reset counter for next traversal
                    displayHighFrequencyWords(); // display highest "frequency" word(s) and their "frequency"
                    highFrequencyWords.clear(); // clear words to be computed again next traversal
                    highestFrequency = 0; // reset counter for next traversal
                    int max = maxDepth(theRoot); // get max depth
                    System.out.println("The maximum height of the tree = " + max); // display max depth
                    System.out.println();
                    System.out.println();
                    break;
                case 2: // in-order traversal output
                    System.out.print("In-order output: ");
                    inOrderTraversal(theRoot); // call method to display the output and passes root Node
                    System.out.println();
                    System.out.println();
                    break;
                case 3: // pre-order traversal output
                    System.out.print("Pre-order output: ");
                    preOrderTraversal(theRoot); // call method to display the output and passes root Node
                    System.out.println();
                    System.out.println();
                    break;
                case 4: // in-order traversal output
                    System.out.print("Post-order output: ");
                    postOrderTraversal(theRoot); // call method to display the output and passes root Node
                    System.out.println();
                    System.out.println();
                    break;
                case 5: // binary search method
                    System.out.println("Please enter the word you are looking for in " + fileName + ":"); // display search info
                    Scanner getSearchWord = new Scanner(System.in); // initialize object to read the user's input
                    String searchWord = getSearchWord.nextLine(); // create line for user to enter the search word
                    Node result = search(searchWord); // call search to get Node with the "word" inputted by keyboard or null
                    if(result == null) // no match with user input
                        System.out.println("Word is not found!");
                    else // found match with user input
                    {
                        System.out.println("Found! It appears " + result.getFrequency() + " times in " + fileName); // display info
                    }
                    // normally would close Scanner object here, but it was causing errors
                    System.out.println();
                    break;
                case 6:
                    if(reader != null) // if object has read user input
                    {
                        reader.close(); // close user input reading object after use
                    }
                    System.exit(1); // terminate program so user must input filename with extension to use program again.
            }
        }
    }
}
