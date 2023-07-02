package Controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Part;
import model.Product;
import java.io.IOException;
/**
 * The main class creates an inventory management system application with some sample data.
 */
public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("mainForm.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 400);
        stage.setTitle("Inventory Management");
        stage.setScene(scene);
        stage.show();
    }
    /**
     * The main method pre-loads sample test data and starts the inventory management system program.
     * @param args
     */
    public static void main(String[] args) {

        Product product1 = new Product(1, "Brakes", 12.99, 15, 2, 20);
        Product product2 = new Product(2, "Tire", 14.99, 15, 12, 20);
        Product product3 = new Product(3, "Rim", 56.99, 15, 12, 20);
        Part part1 = new InHouse(1, "Brakes",15.00,10,1,15,111);
        Inventory.addProduct(product1);
        Inventory.addProduct(product2);
        Inventory.addProduct(product3);
        Inventory.addPart(part1);
        launch(args);
    }

}