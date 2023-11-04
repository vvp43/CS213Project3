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
    private Button closeButton;
    @FXML
    private Button LoadAccountButton;
    @FXML
    private Button printFeesAndInterestsButton;
    @FXML
    private Button printSortedButton;
    @FXML
    private Button printUpdatedButton;


    @FXML
    void initialize(){
        AccountDatabase accountDatabase = new AccountDatabase();
        loyalCustomerButton.setDisable(true);
        toggleCampus();
        toggleMM();
        toggleSavings();
        openAcc(accountDatabase);
        closeAcc(accountDatabase);

        printSortedButton.setOnAction(event -> {
            textArea.appendText(accountDatabase.printSorted());
        });

        printFeesAndInterestsButton.setOnAction(event -> {
            textArea.appendText(accountDatabase.printFeesAndInterests());
        });

        printUpdatedButton.setOnAction(event -> {
            textArea.appendText(accountDatabase.printSorted());
        });

    }

    private void toggleCampus(){
        locationTypeChooserGridPane.setDisable(true);
        locationTypeChooserGridPane.setDisable(true);
        tgAccountType.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                String selectedButtonId = ((ToggleButton) newValue).getId();
                locationTypeChooserGridPane.setDisable(!"collegeCheckingButtonClick".equals(selectedButtonId));
            }
        });
    }
    private void toggleMM(){
        tgAccountType.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                String selectedButtonId = ((ToggleButton) newValue).getId();
                loyalCustomerButton.setDisable(!"moneyMarketButtonClick".equals(selectedButtonId));
            }
        });
    }
    private void toggleSavings(){
        tgAccountType.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                String selectedButtonId = ((ToggleButton) newValue).getId();
                loyalCustomerButton.setDisable(!"savingsButtonClick".equals(selectedButtonId));
            }
        });
    }
    private void openAcc(AccountDatabase accountDatabase){
        openButton.setOnAction(event -> {
            if(openAccountButton() != null){
                Account a = openAccountButton();
                if(accountDatabase.contains(a) || checkIfCandCCExist(a, accountDatabase)){
                    textArea.appendText(a.holder.getFname()+" "+a.holder.getLname()+ " "+a.holder.getDob().toString() +typeCheckCharacterReturn(a) +" is already in the database.\n");
                }
                else{
                    accountDatabase.open(a);
                    //textArea.appendText(accountDatabase.printSorted()+"\n");
                    textArea.appendText(a.holder.getFname()+" "+a.holder.getLname()+ " "+a.holder.getDob().toString() +typeCheckCharacterReturn(a) +" opened.\n");

                }
            }
        });
    }
    private void closeAcc(AccountDatabase accountDatabase) {
        closeButton.setOnAction(event -> {
            if(closeAccount() != null){
                Account a = closeAccount();
                updateAccountForOperations(a, accountDatabase);
                if(accountDatabase.close(a)){
                    accountDatabase.close(a);
                    textArea.appendText(a.holder.getFname()+" "+a.holder.getLname()+ " "+a.holder.getDob().toString() +typeCheckCharacterReturn(a) +" has been closed.\n");
                    accountDatabase.printSorted();

                }
                else{
                    textArea.appendText(a.holder.getFname()+" "+a.holder.getLname()+ " "+a.holder.getDob().toString() +typeCheckCharacterReturn(a) +" is not in the database.\n");
                }
            }
        });
    }

    @FXML
    void onCollegeCheckingButtonClick(ActionEvent event) {

    }

    @FXML
    Account openAccountButton() {
        if(!firstName.getText().isEmpty() && !lastName.getText().isEmpty() && dateOfBirth.getValue() != null
            && dateOfBirth.getValue() != null){
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
                } else {
                    textArea.appendText("Missing data for opening an account.\n");
                }
        } else {
            textArea.appendText("Missing data for opening an account.\n");
        }
        return null;
    }
    @FXML
    Account closeAccount() {
        if(!firstName.getText().isEmpty() && !lastName.getText().isEmpty() && dateOfBirth.getValue() != null
                && dateOfBirth.getValue() != null){
            Date a = createDateFromString(dateOfBirth.getValue().toString());
                if(a.isValid().isEmpty()) {
                    Profile prof = new Profile(firstName.getText(), lastName.getText(), a);
                    if (checkingButtonClick.isSelected()) {
                        return new Checking(prof, 0);
                    } else if (collegeCheckingButtonClick.isSelected()) {
                        return new CollegeChecking(prof, 0, null);
                    } else if (savingsButtonClick.isSelected()) {
                        return new Savings(prof, 0, true);
                    } else if (moneyMarketButtonClick.isSelected()) {
                        return new MoneyMarket(prof, 0,true, 0);
                    } else {
                        textArea.appendText("Missing data for opening an account.\n");
                    }
                } else {
                    textArea.appendText(a.isValid() + "\n");
                }
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
    private Account closeCC(Profile b){
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
            textArea.appendText("Invalid campus!\n");
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





    public String typeCheckCharacterReturn(Account a){
        if(a.getClass() == Checking.class){
            return "(C)";
        }
        else if(a.getClass() == CollegeChecking.class){
            return "(CC)";
        }
        else if(a.getClass() == Savings.class){
            return "(S)";
        }
        else{
            return "(MM)";
        }
    }
    public boolean checkIfCandCCExist(Account a, AccountDatabase ad){
        Account[] list = ad.getAccounts();
        if(list!=null) {
            if (a.getClass() == CollegeChecking.class || a.getClass() == Checking.class) {
                for (Account acc : list) {
                    if (acc != null) {
                        if (acc.holder.equals(a.holder) && acc.getClass() == Checking.class) {
                            return true;
                        } else if (acc.holder.equals(a.holder) && acc.getClass() == CollegeChecking.class) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private void updateAccountForOperations(Account a, AccountDatabase ad){
        Account[] list = ad.getAccounts();
        if(list != null) {
            if (a.getClass() == CollegeChecking.class) {
                for (Account acc : list) {
                    if (acc != null) {
                        if (acc.getClass() == CollegeChecking.class && acc.holder.equals(a.holder)) {
                            ((CollegeChecking) a).setCampus(((CollegeChecking) acc).getCampus());
                        }
                    }
                }
            }
            else if(a.getClass() == Savings.class){
                for (Account acc : list) {
                    if (acc != null) {
                        if (acc.getClass() == Savings.class && acc.holder.equals(a.holder)) {
                            ((Savings) a).setIsLoyal(((Savings) acc).getIsLoyal());
                        }
                    }
                }
            }
            else if(a.getClass() == MoneyMarket.class){
                for (Account acc : list) {
                    if (acc != null) {
                        if (acc.getClass() == MoneyMarket.class && acc.holder.equals(a.holder)) {
                            ((MoneyMarket) a).setIsLoyal(((MoneyMarket) acc).getIsLoyal());
                            ((MoneyMarket) a).setWithdrawal(((MoneyMarket) acc).getWithdrawal());
                        }
                    }
                }
            }
        }
    }

}