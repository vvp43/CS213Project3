package RUbank;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

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
    private Button clearAll;

    @FXML
    private TextField balance;

    @FXML
    void initialize(){
        AccountDatabase accountDatabase = new AccountDatabase();
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
        openButton.setOnAction(event -> {
            if(openAccountButton() != null){
                System.out.println("created!");
                accountDatabase.open(openAccountButton());
                accountDatabase.printSorted();
            }
        });

    }
    @FXML
    void onCollegeCheckingButtonClick(ActionEvent event) {

    }
    @FXML
    Account openAccountButton() {
        if(!firstName.getText().isEmpty() && !lastName.getText().isEmpty()){
            if(dateOfBirth.getValue() != null){
                Date a = createDateFromString(dateOfBirth.getValue().toString());
                if(checkingButtonClick.isSelected()){
                    return addCheck(a);
                }
                else if(collegeCheckingButtonClick.isSelected()){
                    return addCollegeCheck(a);
                }
                else if(savingsButtonClick.isSelected()){
                    return addSavings(a);
                }
                else if(moneyMarketButtonClick.isSelected()){
                    return addMM(a);
                }
            } else {

            }
        } else {
            textArea.appendText("Missing data for opening an account\n");
        }
        return null;
    }
    @FXML
    void setCustomerStatusYes(ActionEvent event) {
        loyalCustomerButton.setSelected(true);
        loyalCustomerButton.setDisable(true);
    }

    @FXML
    void clearOptions(ActionEvent event) {
        firstName.setText("");
        lastName.setText("");
        dateOfBirth.setValue(null);
        tgAccountType.selectToggle(null);
        tgLocation.selectToggle(null);
    }
    public static boolean isValidDouble(String input) {
        if (input == null) {
            return false;
        }
        try {
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    private Date createDateFromString(String date) { // need to catch exception wher e  integers cant beparsed
        //Split date string into array contains month, day, year
        String[] dateArr = date.split("-");
        int year = Integer.parseInt(dateArr[0]);
        int month = Integer.parseInt(dateArr[1]);
        int day = Integer.parseInt(dateArr[2]);


        //Create Date object and return
        return new Date(year, month, day);
    }

    private Account addCheck(Date a){
        if(a.isValid().isEmpty()) {
            Profile prof = new Profile(firstName.getText(), lastName.getText(),a);
            if (isValidDouble(balance.getText())) {
                if (Double.parseDouble(balance.getText()) > 0) {
                    return new Checking(prof, Double.parseDouble(balance.getText()));
                } else {
                    textArea.appendText("Initial deposit cannot be 0 or negative.\n");
                }
            } else {
                textArea.appendText("Not a valid amount.\n");
            }
        } else {
            textArea.appendText(a.isValid()+"\n");
        }
        return null;
    }

    private Account addCollegeCheck(Date a){
        Campus campus = null;
        if(newarkButton.isSelected()){
            campus = Campus.NEWARK;
        } else if(newBrunswickButton.isSelected()){
            campus = Campus.NEW_BRUNSWICK;
        } else if(camdenButton.isSelected()){
            campus = Campus.CAMDEN;
        } else {
            System.out.println("Invalid campus!");
            return null;
        }
        if(a.isValid().isEmpty()) {
            Profile prof = new Profile(firstName.getText(), lastName.getText(),a);
            if (isValidDouble(balance.getText())) {
                if (Double.parseDouble(balance.getText()) > 0) {
                    return new CollegeChecking(prof, Double.parseDouble(balance.getText()), campus);
                } else {
                    textArea.appendText("Initial deposit cannot be 0 or negative.\n");
                }
            } else {
                textArea.appendText("Not a valid amount.\n");
            }
        } else {
            textArea.appendText(a.isValid()+"\n");
        }
        return null;
    }
    private Account addSavings(Date a){
        if(a.isValid().isEmpty()) {
            Profile prof = new Profile(firstName.getText(), lastName.getText(),a);
            if (isValidDouble(balance.getText())) {
                if (Double.parseDouble(balance.getText()) > 0) {
                    if(loyalCustomerButton.isSelected()){
                        return new Savings(prof, Double.parseDouble(balance.getText()), true);
                    } else {
                        return new Savings(prof, Double.parseDouble(balance.getText()), false);
                    }
                } else {
                    textArea.appendText("Initial deposit cannot be 0 or negative.\n");
                }
            } else {
                textArea.appendText("Not a valid amount.\n");
            }
        } else {
            textArea.appendText(a.isValid()+"\n");
        }
        return null;
    }
    private Account addMM(Date a){
        if(a.isValid().isEmpty()) {
            Profile prof = new Profile(firstName.getText(), lastName.getText(),a);
            if (isValidDouble(balance.getText())) {
                if (Double.parseDouble(balance.getText()) > 0) {
                    if(Double.parseDouble(balance.getText()) >= 2000) {
                        return new MoneyMarket(prof, Double.parseDouble(balance.getText()), true, 0);
                    } else{
                        textArea.appendText("Minimum of $2000 to open a Money Market account.\n");
                    }
                } else {
                    textArea.appendText("Initial deposit cannot be 0 or negative.\n");
                }
            } else {
                textArea.appendText("Not a valid amount.\n");
            }
        } else {
            textArea.appendText(a.isValid()+"\n");
        }
        return null;
    }


}