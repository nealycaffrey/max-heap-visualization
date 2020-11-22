package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.TextField;

import java.util.Arrays;

public class Main extends Application {

    // clears the layout panes
    public static void clearBoxes(HBox hBox, VBox vBox) {
        hBox.getChildren().clear();
        vBox.getChildren().clear();
    }

    // updates messages which runs on every button action handler call
    public static void updateMessages(HBox hBox, VBox vBox, TextField tfKey, Button btInsert, Button btRemove, Button btFind, Button btPreOrder, Button btReset, Heap heap) {
            Label label = new Label("Enter key: ");
            label.setStyle("-fx-text-fill: white;-fx-font-size: 16;");
            hBox.getChildren().addAll(label, tfKey, btInsert, btRemove, btFind, btPreOrder, btReset);
            Text text2 = new Text("Height of the Heap: " + heap.getTreeHeight());
            text2.setFill(Color.WHITE);
            text2.setStyle("-fx-font-size: 14;");
            Text text3 = new Text("Number of Vertices: " + heap.getNodes().size());
            text3.setFill(Color.WHITE);
            text3.setStyle("-fx-font-size: 14;");
            vBox.setPadding(new Insets(20));
            vBox.getChildren().addAll(text2, text3);
    }

    @Override
    public void start(Stage primaryStage){
        // init Heap and HeapView for operations
        Heap heap = new Heap();
        HeapView view = new HeapView(heap);

        BorderPane pane = new BorderPane();
        pane.setCenter(view);
        pane.setStyle("-fx-background-color: #AB5263;");

        // init pane contents
        TextField tfKey = new TextField();
        tfKey.setPrefColumnCount(6);
        tfKey.setAlignment(Pos.BASELINE_RIGHT);
        Button btInsert = new Button("Insert");
        Button btRemove = new Button("Remove Root");
        Button btFind = new Button("Find element");
        Button btPreOrder = new Button("Preorder");
        Button btReset = new Button("Reset");
        HBox hBox = new HBox(6);
        Label label = new Label("Enter key: ");
        label.setStyle("-fx-text-fill: white;-fx-font-size: 16;");
        hBox.getChildren().addAll(label, tfKey, btInsert, btRemove, btFind, btPreOrder, btReset);
        hBox.setAlignment(Pos.CENTER);

        VBox vBox = new VBox(5);
        vBox.setAlignment(Pos.BOTTOM_LEFT);

        pane.setPadding(new Insets(10));
        pane.setTop(hBox);
        pane.setBottom(vBox);

        // insert button handler
        btInsert.setOnAction(e -> {
            clearBoxes(hBox, vBox);
            // checks for duplicate values and adds the key from input accordingly
            try {
                int key = Integer.parseInt(tfKey.getText());
                int foundIndex;
                if((foundIndex = heap.search(key)) != -1) {
                    view.highlight(foundIndex);
                    view.displayHeap();
                    view.highlight(-1);
                    view.setStatusMessage(key + " already exists");
                } else {
                    heap.add(new Node(key)); // Insert a new key
                    view.displayHeap();
                    view.setStatusMessage("Inserted " + key);
                }
                updateMessages(hBox, vBox, tfKey, btInsert, btRemove, btFind, btPreOrder, btReset, heap);
            } catch (Exception ex){
                // exception handled for invalid input
                view.displayHeap();
                view.setStatusMessage("Try entering an integer next time");
                clearBoxes(hBox, vBox);
                updateMessages(hBox, vBox, tfKey, btInsert, btRemove, btFind, btPreOrder, btReset, heap);
            }
        });

        // remove button handler
        btRemove.setOnAction(e -> {
            clearBoxes(hBox, vBox);
            //removes the root of the heap
            heap.remove();
            view.displayHeap();
            view.setStatusMessage(heap.getNodes().size()==0 ? "No node to remove" : "Removed root");
            updateMessages(hBox, vBox, tfKey, btInsert, btRemove, btFind, btPreOrder, btReset, heap);
        });

        // reset button handler
        btReset.setOnAction(e -> {
            clearBoxes(hBox, vBox);
            // resets the heap
            view.resetHeap();
            view.displayHeap();
            view.setStatusMessage("Reset the Heap");
            updateMessages(hBox, vBox, tfKey, btInsert, btRemove, btFind, btPreOrder, btReset, heap);
        });

        // find button handler
        btFind.setOnAction(e -> {
            clearBoxes(hBox, vBox);
            //finds the key from input and highlights the node containing the key
            view.displayHeap();
            try {
                int key = Integer.parseInt(tfKey.getText());
            int foundIndex;
            if((foundIndex = heap.search(key)) != -1) {
                view.highlight(foundIndex);
                view.displayHeap();
                view.highlight(-1);
                view.setStatusMessage(key + " found");
            } else {
                view.setStatusMessage(key + " not found");
            }
                updateMessages(hBox, vBox, tfKey, btInsert, btRemove, btFind, btPreOrder, btReset, heap);
            } catch (Exception ex){
                view.displayHeap();
                view.setStatusMessage("Try entering an integer next time");
                clearBoxes(hBox, vBox);
                updateMessages(hBox, vBox, tfKey, btInsert, btRemove, btFind, btPreOrder, btReset, heap);
            }
        });

        // insert button handler
        btPreOrder.setOnAction(e -> {
            clearBoxes(hBox, vBox);
            // sets the status to preorder string
            String preorder = Arrays.toString(heap.preOrderToString());
            view.displayHeap();
            view.setStatusMessage("Preorder representation: "+ preorder);
            updateMessages(hBox, vBox, tfKey, btInsert, btRemove, btFind, btPreOrder, btReset, heap);
        });

        Scene scene = new Scene(pane, 850, 600);
        primaryStage.setTitle("Max Heap Visualization");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        // max heap visualization
        launch(args);
    }
}
