package RUbank;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.control.TextArea;

public class TransactionManagerController {

    @FXML
    private GridPane accountTypeChooserGridPane;

    @FXML
    private RadioButton checkingButtonClick;

    @FXML
    private RadioButton collegeCheckingButtonClick;

    @FXML
    private GridPane locationTypeChooserGridPane;

    @FXML
    private RadioButton loyalCustomerButton;

    @FXML
    private RadioButton moneyMarketButtonClick;

    @FXML
    private RadioButton savingsButtonClick;

    @FXML
    private ToggleGroup tgAccountType;

    @FXML
    private ToggleGroup tgLocation;

    @FXML
    private ToggleGroup tgLoyalCustomer;

    @FXML
    private TextArea textArea;
    @FXML
    void initialize(){
        locationTypeChooserGridPane.setDisable(true);
        locationTypeChooserGridPane.setDisable(true);
        tgAccountType.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                String selectedButtonId = ((ToggleButton) newValue).getId();
                locationTypeChooserGridPane.setDisable(!"collegeCheckingButtonClick".equals(selectedButtonId));
            }
        });

        loyalCustomerButton.setDisable(true);
        tgAccountType.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                String selectedButtonId = ((ToggleButton) newValue).getId();
                loyalCustomerButton.setDisable(!"moneyMarketButtonClick".equals(selectedButtonId));
            }
        });

    }

    @FXML
    void onCollegeCheckingButtonClick(ActionEvent event) {

    }


}