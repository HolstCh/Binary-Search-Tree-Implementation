/**
 * Node class is a generic Node class for BST with each Node connection set to null by default. This Node class is a
 * helper class designed for storage and traversal of the datamembers "word" and "frequency".
 */
/*
    This class was created with guidance by code used from:
     lectures in week 5: "StudentNode.java"
     The differences in this code are that each Node connection is set to null by default and not with setter methods in
     the constructor. Also, a parent Node is used to create a reference from child to parent Node in insertWordAndNode() in
     class BinaryTree.
 */
public class Node
{
    private Node parent = null; // Node above left child and/or right child
    private Node leftChild = null; // Node below parent on left side
    private Node rightChild = null; // Node below parent on right side
    private int frequency = 0; // amount of times that a word occurs in BST
    private String word = ""; // word from input text file

    /**
     * @param frequency is an integer that represents amount of words occurring in an input text file
     * @param word is a String that represents a word that was parsed from within an input text file
     */
    Node(int frequency, String word)
    {
        this.frequency = frequency;
        this.word = word;
    }

    // getter methods
    public Node getParent(){return parent;}
    public Node getLeftChild(){return leftChild;}
    public Node getRightChild(){return rightChild;}
    public int getFrequency(){return frequency;}
    public String getWord(){return word;}

    // setter methods
    public void setParent(Node parent){this.parent = parent;}
    public void setLeftChild(Node leftChild){this.leftChild = leftChild;}
    public void setRightChild(Node rightChild){this.rightChild = rightChild;}
    public void setFrequency(int frequency){this.frequency = frequency;}
    public void setWord(String word){this.word = word;}
}

