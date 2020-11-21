package sample;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.shape.Line;

public class HeapView extends Pane {
    private int highlightedNodeIndex;

    private Heap heap;
    private double nodeRadius;
    private double levelGap;

    HeapView(Heap heap) {
        this.highlightedNodeIndex = -1;
        this.heap = heap;
        this.nodeRadius = 15.0;
        this.levelGap = 50.0;
    }

    public void setStatusMessage(String message) {
        getChildren().add(new Text(20, 20, message));
    }

    public void displayHeap() {
        getChildren().clear();
        displayHeapRecursive(0, getWidth() / 2, 1);
    }

    public void highlight(int index) {
        highlightedNodeIndex = index;
    }

    // index - obv
    // width - at which node to be displayde
    // level - of node
    private void displayHeapRecursive(int index, double width, int level) {
        if(index < 0 || index >= heap.getNodes().size()) {
            return;
        }

        double height = levelGap + (levelGap*level);

        Node node = heap.getNodes().get(index);

        Circle circle = new Circle(width, height, nodeRadius);
        circle.setFill(index==highlightedNodeIndex? Color.ORANGE : Color.WHITE);
        Text text = new Text(width - 4, height + 4, heap.getNodes().get(index).toString());
        getChildren().addAll(circle, text);

        System.out.println("displaying " + heap.getNodes().get(index).toString() + " @ (" + width + ", " + height + ")");

        final double widthLeft = width - (getWidth()/Math.pow(2, level+1));
        final double widthRight = width + (getWidth()/Math.pow(2, level+1));

        displayHeapRecursive(Heap.getLeftChild(index), widthLeft, level+1);
        if(!(Heap.getLeftChild(index) < 0 || Heap.getLeftChild(index) >= heap.getNodes().size())) {
            Line line = new Line(width-15, height, widthLeft+15, height+levelGap);
            line.setFill(Color.RED);
            getChildren().add(line);
        }

        displayHeapRecursive(Heap.getRightChild(index), widthRight, level+1);
        if(!(Heap.getRightChild(index) < 0 || Heap.getRightChild(index) >= heap.getNodes().size())) {
            Line line = new Line(width+15, height, widthRight-15, height+levelGap);
            line.setFill(Color.RED);
            getChildren().add(line);
        }
    }

}
