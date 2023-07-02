package Controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class mainForm implements Initializable {

    public TextField partSearch;
    public TextField productSearch;
    Stage stage;
    Parent scene;

    @FXML
    private TableView<Part> partsTblView;
    @FXML
    private TableColumn<Part, Integer> partIdCol;
    @FXML
    private TableColumn<Part, String> partNameCol;
    @FXML
    private TableColumn<Part, Integer> partInvLevelCol;
    @FXML
    private TableColumn<Part, Double> partPriceCol;
    @FXML
    private TableView<Product> productTableView;
    @FXML
    private TableColumn<Product, Integer> prodIDCol;
    @FXML
    private TableColumn<Product, String> prodNameCol;
    @FXML
    private TableColumn<Product, Integer> prodInvLevelCol;
    @FXML
    private TableColumn<Product, Double> prodPriceCol;

    @FXML
    void addPartButtonOnAction(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("addPart.fxml"));
        loader.load();

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setTitle("Add a Part");
        stage.setScene(new Scene(scene));
        stage.show();
    }
    @FXML
    void modifyPartButtonOnAction(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("modifyPart.fxml"));
        loader.load();
        modifyPart modPartController = loader.getController();
        modPartController.sendPart(partsTblView.getSelectionModel().getSelectedItem());

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setTitle("Modify a part");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void deletePartOnAction(ActionEvent actionEvent) {
        Part selectPart = partsTblView.getSelectionModel().getSelectedItem();
        if (selectPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Please select a part");
            alert.show();
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setContentText("Are you sure?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Inventory.deletePart(partsTblView.getSelectionModel().getSelectedItem());
        }
    }
    public void addProductOnAction(ActionEvent actionEvent) throws IOException {

        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        try {
            scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("addProduct.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setTitle("Add a Product");
        stage.setScene(new Scene(scene));
        stage.show();
    }


    public void modifyProductOnAction(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("modifyProduct.fxml"));
        loader.load();
        modifyProduct modPartController = loader.getController();
        modPartController.sendProduct(productTableView.getSelectionModel().getSelectedItem());

        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setTitle("Modify a part");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void deleteProductOnAction(ActionEvent actionEvent) {
        Product selectProduct = productTableView.getSelectionModel().getSelectedItem();
        if (selectProduct == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Please select a product");
            alert.show();
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setContentText("Are you sure?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            ObservableList<Part> assocParts = Product.getAllAssociatedParts();
            if (assocParts.size() > 0) {
                Alert assocItems = new Alert(Alert.AlertType.ERROR, "ERROR");
                assocItems.setContentText("There is an associated part to selected product");
                assocItems.show();
            } else {

                Inventory.deleteProduct(productTableView.getSelectionModel().getSelectedItem());
            }
        }
    }

    public void productSearchOnAction(ActionEvent actionEvent) {
        String name = productSearch.getText();
        ObservableList<Product> searchProduct = Inventory.lookupProduct(name);

        if(searchProduct.size() == 0) {
            int productID = Integer.parseInt(name);
            Product item = Inventory.lookupProduct(productID);
            if (item != null) {
                searchProduct.add(item);
            }
        }
        productTableView.setItems(searchProduct);
        productTableView.getSelectionModel().select(0);
    }

    public void partSearchOnAction(ActionEvent actionEvent) {
        String name = partSearch.getText();
        ObservableList<Part> searchPart = Inventory.lookupPart(name);

        if (searchPart.size() == 0) {
            int partID = Integer.parseInt(name);
            Part partItem = Inventory.lookupPart(partID);
            if (partItem != null) {
                searchPart.add(partItem);
            }
        }

        partsTblView.setItems(searchPart);
        partsTblView.getSelectionModel().select(0);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        partsTblView.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        productTableView.setItems(Inventory.getAllProducts());
        prodIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        prodNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        prodInvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        prodPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        productTableView.getSelectionModel().select(0);
        partsTblView.getSelectionModel().select(0);
    }
    public void exitButtonClicked(ActionEvent actionEvent) {

        System.exit(0);
    }
}