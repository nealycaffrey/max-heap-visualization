package sample;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.TextField;

import java.util.Arrays;

public class Main extends Application {

    public static void clearBoxes(HBox hBox, VBox vBox) {
        hBox.getChildren().clear();
        vBox.getChildren().clear();
    }

    public static void updateMessages(HBox hBox, VBox vBox, TextField tfKey, Button btInsert, Button btRemove, Button btFind, Button btPreOrder, Heap heap) {
            Label label = new Label("Enter key: ");
            label.setStyle("-fx-text-fill: black;");
            hBox.getChildren().addAll(label, tfKey, btInsert, btRemove, btFind, btPreOrder);
            // Text text1 = new Text("Preorder Representation : " + Arrays.toString(heap.preOrderToString()));
            Text text2 = new Text("Height of the Heap: " + heap.getTreeHeight());
            Text text3 = new Text("Number of Vertices: " + heap.getNodes().size());
            // vBox.getChildren().addAll(text1, text2, text3);
            vBox.getChildren().addAll(text2, text3);
    }

    @Override
    public void start(Stage primaryStage){
        Heap heap = new Heap();

        BorderPane pane = new BorderPane();
        HeapView view = new HeapView(heap);
        pane.setCenter(view);
        pane.setStyle("-fx-background-color: darkgray;");

        TextField tfKey = new TextField();
        tfKey.setPrefColumnCount(5);
        tfKey.setAlignment(Pos.BASELINE_RIGHT);
        Button btInsert = new Button("Insert");
        Button btRemove = new Button("Remove Root");
        Button btFind = new Button("Find element");
        Button btPreOrder = new Button("Preorder");
        HBox hBox = new HBox(5);
        Label label = new Label("Enter key: ");
        label.setStyle("-fx-text-fill: black;");
        hBox.getChildren().addAll(label, tfKey, btInsert, btRemove, btFind, btPreOrder);
        hBox.setAlignment(Pos.CENTER);

        VBox vBox = new VBox(5);
        vBox.setAlignment(Pos.BOTTOM_LEFT);

        pane.setTop(hBox);
        pane.setBottom(vBox);

        btInsert.setOnAction(e -> {
            clearBoxes(hBox, vBox);
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
                updateMessages(hBox, vBox, tfKey, btInsert, btRemove, btFind, btPreOrder, heap);
            } catch (Exception ex){
                view.displayHeap();
                view.setStatusMessage(ex.toString() + ". Try entering an integer this time.");
                clearBoxes(hBox, vBox);
                updateMessages(hBox, vBox, tfKey, btInsert, btRemove, btFind, btPreOrder, heap);
            }
        });

        btRemove.setOnAction(e -> {
            clearBoxes(hBox, vBox);
            view.displayHeap();
            view.setStatusMessage(heap.getNodes().size()==0 ? "No node to remove" : "Removed root");
            heap.remove();
            updateMessages(hBox, vBox, tfKey, btInsert, btRemove, btFind, btPreOrder, heap);
        });

        btFind.setOnAction(e -> {
            clearBoxes(hBox, vBox);
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
            updateMessages(hBox, vBox, tfKey, btInsert, btRemove, btFind, btPreOrder, heap);
        });

        btPreOrder.setOnAction(e -> {
            clearBoxes(hBox, vBox);
            String preorder = Arrays.toString(heap.preOrderToString());
            view.displayHeap();
            view.setStatusMessage("Preorder representation: "+ preorder);
            updateMessages(hBox, vBox, tfKey, btInsert, btRemove, btFind, btPreOrder, heap);
        });

        Scene scene = new Scene(pane, 800, 400);
        primaryStage.setTitle("Max Heap Visualization");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
