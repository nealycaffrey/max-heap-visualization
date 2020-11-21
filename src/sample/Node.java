package sample;

public class Node {
    int data;

    public Node(int data){
        this.data = data;
    }

    public int getData(){
        return this.data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public String toString() {
        return data + "";
    }
}