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
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * modifyPart - Controller to modify part data in the inventory
 */
public class modifyPart {

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
    private RadioButton modPartInHouse;
    @FXML
    private RadioButton modPartOutsourced;
    @FXML
    private Label machineIDLbl;

    /**
     * @param event - On press of Save button, saves entered part data to inventory
     * @throws IOException
     */
    @FXML
    void modPartSaveButton(ActionEvent event) throws IOException {

        try {

            int id = Integer.parseInt(idTxt.getText());
            String name = nameTxt.getText();
            int stock = Integer.parseInt(invTxt.getText());
            double price = Double.parseDouble(priceTxt.getText());
            int max = Integer.parseInt(maxTxt.getText());
            int min = Integer.parseInt(minTxt.getText());
            int machineId = Integer.parseInt(machineTxt.getText());
            String companyName = null;

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
            } else {

                if (modPartInHouse.isSelected()) {
                    machineIDLbl.setText("Machine ID");
                    machineId = Integer.parseInt(machineTxt.getText());
                    Inventory.addPart(new InHouse(id, name, price, stock, min, max, machineId));
                } else if (modPartOutsourced.isSelected()) {
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
     * @param actionEvent - on press of Cancel button, return to main screen
     */
    public void modPartCancelOnAction(ActionEvent actionEvent) {

        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        try {
            scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("mainForm.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /**
     * @param actionEvent - On click of in-house radio button, set the Machine ID label to "Machine ID"
     */
    public void onClickInHouse(ActionEvent actionEvent) {
        machineIDLbl.setText("Machine ID");
    }

    /**
     * @param actionEvent - On click of Outsourced radio button, set the Machine ID label to "Company Name"
     */
    public void onClickOutsourced(ActionEvent actionEvent) {
        machineIDLbl.setText("Company Name");
    }

    /**
     * @param part - Receive parts information from main screen from selected tableview row
     */
    public void sendPart(Part part) {

        idTxt.setText(String.valueOf(part.getId()));
        nameTxt.setText(String.valueOf(part.getName()));
        invTxt.setText(String.valueOf(part.getStock()));
        priceTxt.setText(String.valueOf(part.getPrice()));
        maxTxt.setText(String.valueOf(part.getMax()));
        minTxt.setText(String.valueOf(part.getMin()));

        if (part instanceof InHouse) {
            modPartInHouse.setSelected(true);
            machineIDLbl.setText("Machine ID");
            machineTxt.setText(String.valueOf(((InHouse) part).getMachineId()));
        } else if (part instanceof Outsourced) {
            modPartOutsourced.setSelected(true);
            machineIDLbl.setText("Company Name");
            machineTxt.setText(((Outsourced) part).getCompanyName());
        }
    }
}
