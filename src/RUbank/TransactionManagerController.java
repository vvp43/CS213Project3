package RUbank;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class TransactionManagerController {

    @FXML
    private GridPane accountTypeChooserGridPane;

    @FXML
    private RadioButton camdenButton;

    @FXML
    private RadioButton checkingButtonClick;

    @FXML
    private RadioButton collegeCheckingButtonClick;

    @FXML
    private DatePicker dateOfBirth;

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private GridPane locationTypeChooserGridPane;

    @FXML
    private CheckBox loyalCustomerButton;

    @FXML
    private RadioButton moneyMarketButtonClick;

    @FXML
    private RadioButton newBrunswickButton;

    @FXML
    private RadioButton newarkButton;

    @FXML
    private Button openButton;

    @FXML
    private RadioButton savingsButtonClick;

    @FXML
    private TextArea textArea;

    @FXML
    private ToggleGroup tgAccountType;

    @FXML
    private ToggleGroup tgLocation;

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
        loyalCustomerButton.setDisable(true);
        tgAccountType.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                String selectedButtonId = ((ToggleButton) newValue).getId();
                loyalCustomerButton.setDisable(!"savingsButtonClick".equals(selectedButtonId));
            }
        });

    }
    @FXML
    void onCollegeCheckingButtonClick(ActionEvent event) {

    }
    @FXML
    void openAccountButton(ActionEvent event) {
        System.out.println(firstName.getText());
        System.out.println(lastName.getText());

        if(checkingButtonClick.isSelected()){
            System.out.println("chjecking  selecetd!");
        }
        else if(collegeCheckingButtonClick.isSelected()){
            if(newarkButton.isSelected()){
                System.out.println("newy selected");
            }
            else if(newBrunswickButton.isSelected()){
                System.out.println("newy brunswy selected");
            }
            else if(camdenButton.isSelected()){
                System.out.println("cammy selected");
            }
            else {
                System.out.println("select a campus!");
            }
        }
        else if(savingsButtonClick.isSelected()){
            System.out.println("savings are selected!");
        }
        else if(moneyMarketButtonClick.isSelected()){
            System.out.println("moneymarket selected!");
        }
        else{
            System.out.println("nothing is selecrted");
        }
    }
    @FXML
    void setCustomerStatusYes(ActionEvent event) {
        loyalCustomerButton.setSelected(true);
        loyalCustomerButton.setDisable(true);
    }


}