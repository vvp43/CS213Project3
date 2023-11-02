package RUbank;
import java.util.Scanner;

/**
 *This is the user interface class that processes the transactions entered on the
 * terminal and performs all Input/Ouput. This class handles all Java exceptions and invalid data
 * @author Seth Yeh, Vinh Pham
 */

public class TransactionManager {
    /**
     * isValidCommand() method checks if user's input is valid
     * @param command user's input
     * @return true if command is valid and false if command is invalid
     */
    public boolean isValidCommand(String command) {
        return command.equals("O") || command.equals("C") || command.equals("D")
                || command.equals("W") || command.equals("P") || command.equals("PI")
                || command.equals("UB") || command.equals("Q");
    }

    /**
     * createDateFromString() method
     * @param date String of date contains month, day, year
     * @return Date object
     */
    private Date createDateFromString(String date) {
        //Split date string into array contains month, day, year
        String[] dateArr = date.split("/");
        int month = Integer.parseInt(dateArr[0]);
        int day = Integer.parseInt(dateArr[1]);
        int year = Integer.parseInt(dateArr[2]);


        //Create Date object and return
        return new Date(year, month, day);
    }

    /**
     * createLoyaltyFromString() method checks if account holder is loyalty from String
     * @param loyalty String of date contains month, day, year
     * @return loyalty boolean
     */
    private boolean createLoyaltyFromString(String loyalty) {
        boolean temp = false;
        if(isStringInteger(loyalty)){
            switch(loyalty){
                case "0":
                    break;
                case "1":
                    temp = true;
                    break;
            }
        }
        return temp;
    }


    /**
     * createLocationFromInt() creates account's location from String
     * @param campus int representative of location
     * @return Location object based on String provided
     */
    private Campus createCampusFromString(String campus) {
        Campus place = null;
        if(isStringInteger(campus)){
            switch (campus) {
                case "0" -> place = Campus.NEW_BRUNSWICK;
                case "1" -> place = Campus.NEWARK;
                case "2" -> place = Campus.CAMDEN;
            }
        }
        return place;
    }

    /**
     * isStringInteger() checks if the String is an Integer or not
     * @param input String may contain integer
     * @return true if String contains integer
     */
    public boolean isStringInteger(String input) {
        try {
            int a = Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * typeCheckCharacterReturn() checks type of account
     * @param a Account object
     * @return String that represents type of account
     */
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

    /**
     * isValidDouble() check if the String contains double is valid
     * @param input String contains double
     * @return true if double is valid
     */
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


    /**
     * createAccountfromStrings
     * @param accountType String
     * @param fName String
     * @param lName String
     * @param date String
     * @param balance double
     * @param campus String
     * @param isLoyal String
     * @param withdraw String
     * @return Account object
     */
    private Account createAccountFromStrings(String accountType, String fName, String lName, String date, double balance, String campus, String isLoyal, String withdraw) {
        //Create Date object
        Date dateObj = createDateFromString(date);
        Profile holder = new Profile(fName, lName, dateObj);
        if (accountType.equals("C")) {
            return new Checking(holder, balance);
        } else if (accountType.equals("CC")) {
            Campus camp = createCampusFromString(campus);
            return new CollegeChecking(holder, balance, camp);
        } else if (accountType.equals("S")) {
            boolean loyalty = createLoyaltyFromString(isLoyal);
            return new Savings(holder, balance, loyalty);
        } else {
            return new MoneyMarket(holder, balance, true, 0);
        }
        //Create Profile object
    }


    /**
     * Check if the account has checking or college checking
     * @param a Account object
     * @param ad AccountDatabse object
     * @return true if the account is checking or college checking account
     */
    public boolean checkIfCandCCExist(Account a, AccountDatabase ad){
        Account[] list = ad.getAccounts();
        if(list!=null) {
            if (a.getClass() == CollegeChecking.class || a.getClass() == Checking.class) {
                for (Account acc : list) {
                    if (acc != null) {
                        if (acc.holder.equals(a.holder) && acc.getClass() == Checking.class) {
                            System.out.println(a.holder.getFname()+" "+a.holder.getLname()+ " "+a.holder.getDob().toString() +typeCheckCharacterReturn(a) +" is already in the database.");
                            return true;
                        } else if (acc.holder.equals(a.holder) && acc.getClass() == CollegeChecking.class) {
                            System.out.println(a.holder.getFname()+" "+a.holder.getLname()+ " "+a.holder.getDob().toString() +typeCheckCharacterReturn(a) +" is already in the database.");
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }



    /**
     * operationA() method: Helper method used to call the .open() method in AccountDatabase,
     * used to check for any errors in input before opening an account
     * @param a Account to be opened
     * @param ad , short for Account Database
     */
    private void operationO(Account a, AccountDatabase ad) {

        if(a.balance <= 0){
            System.out.println("Initial deposit cannot be 0 or negative.");
            return;
        }
        if(a.getClass() == MoneyMarket.class && a.balance < 2000) {
            System.out.println("Minimum of $2000 to open a Money Market account.");
            return;
        }
        if(!a.holder.getDob().isValid().isEmpty()){
            return;
        }
        if(a.getClass() == CollegeChecking.class && !a.holder.getDob().isUnder24()){
            System.out.println("DOB invalid: "+a.holder.getDob().toString()+" over 24.");
            return;
        }
        if(a.getClass() == CollegeChecking.class && ((CollegeChecking) a).getCampus() == null){
            System.out.println("Invalid campus code.");
            return;
        }
        if(checkIfCandCCExist(a, ad)){
            return;
        }
        if(!ad.open(a)){
            System.out.println(a.holder.getFname()+" "+a.holder.getLname()+
                    " "+a.holder.getDob().toString() +typeCheckCharacterReturn(a) +" is already in the database.");
        } else{
            System.out.println(a.holder.getFname()+" "+a.holder.getLname()+
                    " "+a.holder.getDob().toString() +typeCheckCharacterReturn(a) +" opened.");
            ad.open(a);
        }
    }

    /**
     * updateAccountForOperations() updates accounts before proceeds to oeprations
     * @param a Account object
     * @param ad AccountDatabase object
     */
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

    /**
     * operationR() method: Helper method used to call the .close() method in AccountDatabase,
     * used to check for any errors in input before opening an account
     * @param a Account to be opened
     * @param ad , short for Account Database
     */
    private void operationC(Account a, AccountDatabase ad) {
        //Create Date object
        //Check if any elements of event is invalid and display error message
        if (!a.holder.getDob().isValid().isEmpty()) {
            return;
        }
        updateAccountForOperations(a, ad);
        if(!ad.close(a)){
            System.out.println(a.holder.getFname()+" "+a.holder.getLname()+
                    " "+a.holder.getDob().toString() +typeCheckCharacterReturn(a) +" is not in the " +
                    "database.");
        }
        else{
            ad.close(a);
            System.out.println(a.holder.getFname()+" "+a.holder.getLname()+
                    " "+a.holder.getDob().toString() +typeCheckCharacterReturn(a) +" has been closed.");
        }
    }

    /**
     * operationD(); Helper method used to call the deposit() method in AccountDatabase,
     * @param a Account to be depoisted
     * @param ad AccountDatabse object
     */
    private void operationD(Account a, AccountDatabase ad) {
        updateAccountForOperations(a, ad);
        if(a.balance <= 0){
            System.out.println("Deposit - amount cannot be 0 or negative.");
            return;
        }
        if(!ad.contains(a)){
            System.out.println(a.holder.getFname()+" "+a.holder.getLname()+
                    " "+a.holder.getDob().toString() +typeCheckCharacterReturn(a) +" " +
                    "is not in the database.");
        }
        else{
            ad.deposit(a);
            System.out.println(a.holder.getFname()+" "+a.holder.getLname()+
                    " "+a.holder.getDob().toString() +typeCheckCharacterReturn(a) +" Deposit -" +
                    " balance updated.");
        }
    }

    /**
     * operationW() method: Helper method used to perform the withdrawal operation, and also responsible
     * for catching exceptions where balance < 0 and checking if withdrawal amount > balance.
     * @param a Account that is copied from the terminal
     * @param ad RU Bank database
     */
    private void operationW(Account a, AccountDatabase ad) {
        updateAccountForOperations(a, ad);
        if(a.balance <= 0){
            System.out.println("Withdraw - amount cannot be 0 or negative.");
            return;
        }
        Account[] list = ad.getAccounts();
        if(list != null) {
            for (Account acc : list) {
                if (acc != null) {
                    if(acc.getClass() == a.getClass()){
                        if(acc.equals(a) && (a.balance > acc.balance)){
                            System.out.println(a.holder.getFname()+" "+a.holder.getLname()+
                                    " "+a.holder.getDob().toString() +typeCheckCharacterReturn(a) +" Withdraw - " +
                                    "insufficient fund.");
                            return;
                        }
                    }
                }
            }
        }
        if(list != null) {
            for (Account acc : list) {
                if (acc != null) {
                    if(acc.getClass() == a.getClass()){
                        //System.out.println(acc.balance-(a.balance+10));
                        if(acc.equals(a) && ((acc.balance - (a.balance+10) < 0))){
                            System.out.println(a.holder.getFname()+" "+a.holder.getLname()+ " "+a.holder.getDob().toString() +typeCheckCharacterReturn(a) +" Withdraw - " + "insufficient fund.");
                            return;
                        }
                    }
                }
            }
        }
        if(!ad.contains(a)){
            System.out.println(a.holder.getFname()+" "+a.holder.getLname()+
                    " "+a.holder.getDob().toString() +typeCheckCharacterReturn(a) +" is not in the database.");
        }
        else{
            ad.withdraw(a);
            System.out.println(a.holder.getFname()+" "+a.holder.getLname()+
                    " "+a.holder.getDob().toString() +typeCheckCharacterReturn(a) +" Withdraw -" +
                    " balance updated.");
        }
    }

    /**
     * operationP() method: Helper method used to call printSorted which prints all accounts sorted
     * by Account Type and Profile
     *
     * @param ad ,short for Account Database
     */
    private void operationP(AccountDatabase ad) {
        if(!ad.isEmpty()) {
            ad.printSorted();
        }
        else{
            System.out.println("Account Database is empty!");
        }
    }

    /**
     * operationPE() method: Helper method used to call printFeesAndInterests which prints
     * all accounts with their respective monthly interest and fees;
     *
     * @param ad ,short for Account Database
     */
    private void operationPI(AccountDatabase ad) {
        if(!ad.isEmpty()) {
            ad.printFeesAndInterests();
        }
        else{
            System.out.println("Account Database is empty!");
        }
    }

    /**
     * operationPC() method: Helper method used to call printUpdatedBalances which updates and
     * applies all monthly fees and interest to accounts in database, then prints them sorted
     * by type of account and profile.
     *
     * @param ad ,short for Account Database
     */
    private void operationUB(AccountDatabase ad) {
        if(!ad.isEmpty()){
            ad.printUpdatedBalances();
        }
        else{
            System.out.println("Account Database is empty!");
        }
    }

    /**
     * chooseAccount() method: Based on the given list of strings, choose the correct corresponding
     * account to open and parse inputs into an object to use open an account entered through the
     * terminal. Also responsible for throwing exceptions related to missing data in the string provided
     * like an invalid double etc.
     *
     * @param inputList list of strings where each element represents one part of the parsed command
     * @param accountDatabase AccountDatabase object used to perform various actions
     */
    private void chooseAccount (String[] inputList, AccountDatabase accountDatabase){
        switch (inputList[1]) {
            case "CC" -> {
                if(inputList.length == 7) {
                    if(isValidDouble(inputList[5])) { double bal = Double.parseDouble(inputList[5]);
                        Account add = createAccountFromStrings(inputList[1], inputList[2], inputList[3], inputList[4], bal, inputList[6], "", "");
                        operationO(add, accountDatabase);
                    }
                    else {
                        System.out.println("Not a valid amount.");
                    }
                } else {
                    System.out.println("Missing data for opening an account.");
                }
            } case "S" -> { double bal = Double.parseDouble(inputList[5]);
                if(inputList.length == 7){
                    if(isValidDouble(inputList[5])) {
                        Account add = createAccountFromStrings(inputList[1], inputList[2], inputList[3], inputList[4], bal, "", inputList[6], "");
                        operationO(add, accountDatabase);
                    } else {
                        System.out.println("Not a valid amount.");
                    }
                } else {
                    System.out.println("Missing data for opening an account.");
                }
            } default ->{
                if(inputList.length == 6) { double bal = Double.parseDouble(inputList[5]);
                    if(isValidDouble(inputList[5])){
                        Account add = createAccountFromStrings(inputList[1], inputList[2], inputList[3], inputList[4], bal, "", "", "");
                        operationO(add, accountDatabase);
                    } else{
                        System.out.println("Not a valid amount.");
                    }
                } else{
                    System.out.println("Missing data for opening an account.");
                }
            }
        }
    }

    /**
     * withdrawalCheckandRun(): helper method for operation W to run
     * @param inputList String array of input
     * @param accountDatabase Database of RU Bank
     */
    private void withdrawalCheckandRun(String[] inputList, AccountDatabase accountDatabase){
        if(inputList.length == 6 ){
            if(isValidDouble(inputList[5])) {
                double with = Double.parseDouble(inputList[5]);
                Account withdrawal = createAccountFromStrings(inputList[1], inputList[2], inputList[3], inputList[4], with,
                        "", "", "");
                operationW(withdrawal, accountDatabase);
            } else{
                System.out.println("Not a valid amount.");
            }
        } else{
            System.out.println("Missing data for withdrawal.");
        }
    }

    /**
     * closeCheckAndRun() method: helper method for operation C to run
     * @param inputList String of user input
     * @param accountDatabase RU Bank database
     */
    private void closeCheckAndRun(String[] inputList, AccountDatabase accountDatabase){
        if(inputList.length == 5 ) {
            Account remove = createAccountFromStrings(inputList[1], inputList[2], inputList[3], inputList[4], 0,
                    "", "", "");
            operationC(remove, accountDatabase);
        } else{
            System.out.println("Missing data for closing an account.");
        }
    }

    /**
     * depositCheckAndRun() method: helper method for operation D to run
     * @param inputList String of user input
     * @param accountDatabase RU Bank database
     */
    private void depositCheckAndRun(String[] inputList, AccountDatabase accountDatabase){
        if(inputList.length == 6 ){
            if(isValidDouble(inputList[5])) {
                double with = Double.parseDouble(inputList[5]);
                Account deposit = createAccountFromStrings(inputList[1], inputList[2], inputList[3], inputList[4], with,
                        "", "", "");
                operationD(deposit, accountDatabase);
            } else{
                System.out.println("Not a valid amount.");
            }
        } else{
            System.out.println("Missing data depositing into an account.");
        }
    }

    /**
     * switchHelper() method: helper method helps to define which operation the user would like to proceed
     * @param firstCMD first argument of user input
     * @param inputList the following arguments after firstCMD
     * @param accountDatabase RU Bank Database
     * @return true
     */
    private boolean switchHelper(String firstCMD, String[] inputList, AccountDatabase accountDatabase){
        switch (firstCMD) {
            case "Q" -> {
                System.out.println("Transaction Manager is terminated.");
                return false;
            }
            case "O" -> chooseAccount(inputList, accountDatabase);
            case "C" -> closeCheckAndRun(inputList, accountDatabase);
            case "D" -> depositCheckAndRun(inputList, accountDatabase);
            case "W" -> withdrawalCheckandRun(inputList, accountDatabase);
            case "P" -> operationP(accountDatabase);
            case "PI" -> operationPI(accountDatabase);
            case "PD" -> operationUB(accountDatabase);
            case "UB" -> accountDatabase.printUpdatedBalances();
        }
        return true;
    }

    /**
     * run() method: Driver used to handle parsing inputs from command line into the actual program
     * and performing the various tasks that are available to the user.
     */
    public void run() {
        System.out.println("Transaction Manager is running.\n");
        Scanner scanObj = new Scanner(System.in);
        boolean programRun = true;
        AccountDatabase accountDatabase = new AccountDatabase();
        while (programRun) {
            String command = scanObj.nextLine();
            if (command.equals("")) continue;
            String[] inputList = command.replaceAll("(^\\s+|\\s+$)", "").split("\\s+");//split the whole line into elements of String array
            String firstCMD = inputList[0];
            if (!isValidCommand(firstCMD)) {
                System.out.println("Invalid command!");
            } else {
                programRun = switchHelper (firstCMD, inputList, accountDatabase);
            }
        }
    }
}