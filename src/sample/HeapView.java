package sample;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.shape.Line;

public class HeapView extends Pane {
    // keeps track of what node needs to be highlighted
    private int highlightedNodeIndex;

    private final Heap heap;
    private final double nodeRadius;
    private final double levelGap;

    HeapView(Heap heap) {
        this.highlightedNodeIndex = -1;
        this.heap = heap;
        this.nodeRadius = 15.0;
        this.levelGap = 50.0;
    }

    // a custom status message for verbosity
    public void setStatusMessage(String message) {
        Text text = new Text(20, 20, "Status: " + message);
        text.setFill(Color.WHITE);
        text.setStyle("-fx-font-family: Arial;-fx-font-size: 16;");
        getChildren().add(text);
    }

    // resets the heap
    public void resetHeap(){
        while(this.heap.getNodes().size() != 0) {
            this.heap.remove();
        }
        this.highlightedNodeIndex = -1;
    }

    // wrapper function for displayHeapRecursive()
    public void displayHeap() {
        getChildren().clear();
        displayHeapRecursive(0, getWidth() / 2, 1);
    }

    // displayHeap() to be called after this to show changes
    public void highlight(int index) {
        highlightedNodeIndex = index;
    }

    // index - obv
    // width - at which node to be displayed
    // level - of node
    // https://cdn.discordapp.com/attachments/778269958533939251/780153244647161876/hi.png
    private void displayHeapRecursive(int index, double width, int level) {
        if(index < 0 || index >= heap.getNodes().size()) {
            return;
        }

        // calculation of the height as a function of level
        double height = levelGap + (levelGap*level);

        // creates a circle at (height, width)
        Circle circle = new Circle(width, height, nodeRadius);
        // handle if node is highlighted
        circle.setFill(index==highlightedNodeIndex? Color.rgb(255, 255, 0, 1) : Color.WHITE);
        Text text = new Text(width - 4, height + 4, heap.getNodes().get(index).toString());
        getChildren().addAll(circle, text);

        System.out.println("displaying " + heap.getNodes().get(index).toString() + " @ (" + width + ", " + height + ")");

        // calculation of widths of children as a function of width and level
        final double widthLeft = width - (getWidth()/Math.pow(2, level+1));
        final double widthRight = width + (getWidth()/Math.pow(2, level+1));

        // recursive call to print left children at (widthLeft, f(level+1))
        displayHeapRecursive(Heap.getLeftChild(index), widthLeft, level+1);

        // draw line to left child if it exists
        if(!(Heap.getLeftChild(index) < 0 || Heap.getLeftChild(index) >= heap.getNodes().size())) {
            Line line = new Line(width-15, height, widthLeft+15, height+levelGap);
            line.setFill(Color.RED);
            getChildren().add(line);
        }

        // recursive call to print right children at (widthRight, f(level+1))
        displayHeapRecursive(Heap.getRightChild(index), widthRight, level+1);

        // draw line to right child if it exists
        if(!(Heap.getRightChild(index) < 0 || Heap.getRightChild(index) >= heap.getNodes().size())) {
            Line line = new Line(width+15, height, widthRight-15, height+levelGap);
            line.setFill(Color.RED);
            getChildren().add(line);
        }
    }

}
