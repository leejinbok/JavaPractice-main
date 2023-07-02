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
/**
 * modifyPart - Controller to modify product data in the inventory
 */
public class modifyProduct implements Initializable {

    @FXML
    private TextField searchPart;
    @FXML
    private TextField idTxt;
    @FXML
    private TextField nameTxt;
    @FXML
    private TextField invTxt;
    @FXML
    private TextField priceTxt;
    @FXML
    private TextField maxTxt;
    @FXML
    private TextField minTxt;
    @FXML
    private TableView productTableView;
    @FXML
    private TableColumn prodIDCol;
    @FXML
    private TableColumn prodNameCol;
    @FXML
    private TableColumn prodPriceCol;
    @FXML
    private TableColumn prodInvLevelCol;
    @FXML
    private TableView partsTblView;
    @FXML
    private TableColumn partIdCol;
    @FXML
    private TableColumn partNameCol;
    @FXML
    private TableColumn partInvLevelCol;
    @FXML
    private TableColumn partPriceCol;
    Stage stage;
    Parent scene;

    /**
     * @param event - on press of Cancel button, return to main screen
     * @throws IOException
     */
    public void cancelButtonOnAction(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        try {
            scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("mainForm.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /**
     * @param actionEvent - save highlighted data from Parts list to associated part to product on Button click
     */
    public void addButtonOnAction(ActionEvent actionEvent) {
        Part selectedPart = (Part) partsTblView.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR,"ERROR");
            alert.setContentText("Please select a part to associate with product");
            alert.show();
        } else {
            Product.addAssociatedPart((Part) (partsTblView.getSelectionModel().getSelectedItem()));
        }
    }
    /**
     * @param actionEvent - On press of save button, store items in observable list Product
     */
    public void saveButtonOnAction(ActionEvent actionEvent) {
        try {
            int id = Integer.parseInt(idTxt.getText());
            String name = nameTxt.getText();
            int stock = Integer.parseInt(invTxt.getText());
            double price = Double.parseDouble(priceTxt.getText());
            int max = Integer.parseInt(maxTxt.getText());
            int min = Integer.parseInt(minTxt.getText());

            if (min > max) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning!");
                alert.setContentText("Min must be less than or equal to Max");
                alert.showAndWait();
            } else if (stock > max || stock < min) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning!");
                alert.setContentText("Inv must be within range between Min and Max");
                alert.showAndWait();
            } else if (name.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning!");
                alert.setContentText("Name cannot be blank");
                alert.showAndWait();
            }
            int oldID = id;
            Product modProd = new Product(id, name, price, stock, min, max);
            Inventory.addProduct(modProd);
            Inventory.deleteProduct(Inventory.lookupProduct(oldID));

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setContentText("Please enter valid values for all fields");
            alert.showAndWait();

        }
    }
    /**
     * @param product - Receive product information from main screen from selected tableview row
     */
    public void sendProduct(Product product) {
        idTxt.setText(String.valueOf(product.getId()));
        nameTxt.setText(String.valueOf(product.getName()));
        invTxt.setText(String.valueOf(product.getStock()));
        priceTxt.setText(String.valueOf(product.getPrice()));
        maxTxt.setText(String.valueOf(product.getMax()));
        minTxt.setText(String.valueOf(product.getMin()));
    }
    /**
     * @param actionEvent - remove highlighted data from associated parts list on Button click
     */
    public void removePart(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setContentText("Are you sure?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Product.deleteAssociatedPart((Part)(productTableView.getSelectionModel().getSelectedItem()));
        }
    }
    /**
     * @param actionEvent - Search box to look up either part ID or name of part
     */
    public void searchPartOnAction(ActionEvent actionEvent) {
        String search = searchPart.getText();
        ObservableList<Part> searchPartList = Inventory.lookupPart(search);

        if (searchPartList.size() == 0) {
            int partID = Integer.parseInt(search);
            Part item = Inventory.lookupPart(partID);
            if (item != null) {
                searchPartList.add(item);
            }
        }
    }
    /**
     * Initialize two tables - one for parts and one for associated parts to product.
     *
     * @param location
     * The location used to resolve relative paths for the root object, or
     * {@code null} if the location is not known.
     *
     * @param resources
     * The resources used to localize the root object, or {@code null} if
     * the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        partsTblView.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        productTableView.setItems(Product.getAllAssociatedParts());
        prodIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        prodNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        prodInvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        prodPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }


}
