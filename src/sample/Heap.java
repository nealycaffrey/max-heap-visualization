package sample;

import java.util.ArrayList;

public class Heap {
    // max heap implemented through an ArrayList 
    // of type Node
    public ArrayList<Node> nodes;

    public Heap() {
        this.nodes = new ArrayList<>();
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public void add(Node node) {
        if(search(node.getData()) != -1) {
            return;
        }
        nodes.add(node);
        int index = nodes.size() - 1;
        while(index > 0) {
            if(nodes.get(index).getData() > nodes.get(getParent(index)).getData()) {
                swapNodes(index, getParent(index));
                index = getParent(index);
            } else {
                break;
            }
        }
    }

    public void remove(){
        if(nodes.size() != 0) {
            swapNodes(0, nodes.size() - 1);
            nodes.remove(nodes.size() - 1);
            int current = 0;
            while (getLeftChild(current) < nodes.size() && getRightChild(current) < nodes.size()) {
                int leftChild = getLeftChild(current);
                int rightChild = getRightChild(current);
                int greaterChild = nodes.get(leftChild).getData() > nodes.get(rightChild).getData() ? leftChild : rightChild;
                if (nodes.get(greaterChild).getData() > nodes.get(current).getData()) {
                    swapNodes(greaterChild, current);
                    current = greaterChild;
                } else {
                    break;
                }
            }
        }
    }

    // pre order traversal of array representation of heap
    // temp object to persist string while traversing
    private static ArrayList<String> tempStringArray = new ArrayList<>();

    public void preOrder(int index) {
        if(index > nodes.size() - 1) {
            return;
        }
        tempStringArray.add(String.valueOf(nodes.get(index)));
        preOrder(getLeftChild(index));
        preOrder(getRightChild(index));
    }

    public String[] preOrderToString() {
        tempStringArray = new ArrayList<>();
        preOrder(0);
        return tempStringArray.toArray(new String[0]);
    }

    public int search(int data){
        for(int i= 0; i<nodes.size(); i++){
            if(nodes.get(i).getData() == data){
                return i;
            }
        }
        return -1;
    }

    public int getTreeHeight() {
        if(nodes.size() == 0) return 0;
        return (int) (Math.log(nodes.size()) / Math.log(2));
    }

    public void swapNodes(int index1, int index2) {
        Node temp = nodes.get(index1);
        nodes.set(index1, nodes.get(index2));
        nodes.set(index2, temp);
    }

    public static int getParent(int index) {
        return (int) Math.floor((index - 1)/2.0);
    }

    public static int getLeftChild(int index) {
        return (2*index)+1;
    }

    public static int getRightChild(int index) {
        return (2*index)+2;
    }

}