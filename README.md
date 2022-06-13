# Binary Search Tree
Binary tree created in Java. The following is the assignment document is [Assignment 03.pdf](Assignment 03.pdf)

# Description
"FileInput.java" is my main class and should be executed with no command line arguments. After execution, the filename with text extension should be entered and file
should be in the same folder as the JAVA files. Afterwords, the data will be parsed into an ArrayList for dynamic input. Then, converted into a String array called
"words" to be passed to the BinaryTree class. BinaryTree class will begin inserting each word from the beginning into new Nodes or increase the "frequency" datamember
of an existing Node. After insertion of all words, displayTree() will display all traversal and computing options with a switch statement. The user must input an integer
to select an option from the menu. The user may select each option infinitely which operate on the existing BST. This method is in a while loop until integer "6"
is input from the user to terminate the program. If wrong data type is input or an integer out of range is input, then a message appears and the switch statement loops again.
