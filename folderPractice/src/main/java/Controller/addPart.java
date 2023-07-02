package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
/**
 * addPart - a controller for adding parts to the inventory.
 */
public class addPart {
    Stage stage;
    Parent scene;
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
    private TextField machineTxt;
    @FXML
    private TextField minTxt;
    @FXML
    private RadioButton addPartOutsourced;
    @FXML
    private RadioButton addPartInHouse;
    @FXML
    private Label machineIDLbl;

    /**
     * a simple function to return new auto-generated part ID.
     *
     * @return newID starting at ID 101.
     */
    public static int newPartId() {
        int newID = 100;
        for (int i = 0; i < Inventory.getAllParts().size(); i++) {
            newID++;
        }
        return newID;
    }

    /**
     * On press of save button, store items in observable list Part - sorted as either in-house or outsource
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void addPartSaveButton(ActionEvent event) throws IOException {
        try {
            int id = newPartId();
            String name = nameTxt.getText();
            int stock = Integer.parseInt(invTxt.getText());
            double price = Double.parseDouble(priceTxt.getText());
            int max = Integer.parseInt(maxTxt.getText());
            int min = Integer.parseInt(minTxt.getText());
            int machineId;
            String companyName = null;

            //conditional to address that max has to be greater than min
            if (min > max) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning!");
                alert.setContentText("Min must be less than or equal to Max");
                alert.showAndWait();

                //conditional to address that stock has to be within ranges of min and max
            } else if (stock > max || stock < min) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning!");
                alert.setContentText("Inv must be within range between Min and Max");
                alert.showAndWait();

                //conditional to address that name field cannot be empty
            } else if (name.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning!");
                alert.setContentText("Name cannot be blank");
                alert.showAndWait();

                //conditional branch to set Machine ID text to display correct text
            } else {

                if (addPartInHouse.isSelected()) {
                    machineIDLbl.setText("Machine ID");
                    machineId = Integer.parseInt(machineTxt.getText());
                    Inventory.addPart(new InHouse(id, name, price, stock, min, max, machineId));
                } else if (addPartOutsourced.isSelected()) {
                    machineIDLbl.setText("Company Name");
                    companyName = machineTxt.getText();
                    Inventory.addPart(new Outsourced(id, name, price, stock, min, max, companyName));
                }
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setContentText("Please enter valid values for all fields");
            alert.showAndWait();
        }
    }

    /**
     * On press of Cancel button, return to main screen
     *
     * @param actionEvent
     */
    public void addPartCancelOnAction(ActionEvent actionEvent) {

        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        try {
            scene = FXMLLoader.load(getClass().getResource("mainForm.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * On click of in-house radio button, set the Machine ID label to "Machine ID"
     *
     * @param actionEvent
     */
    public void onClickInHouse(ActionEvent actionEvent) {
        machineIDLbl.setText("Machine ID");
    }

    /**
     * On click of Outsourced radio button, set the Machine ID label to "Company Name"
     *
     * @param actionEvent
     */
    public void onClickOutsourced(ActionEvent actionEvent) {
        machineIDLbl.setText("Company Name");
    }
}
