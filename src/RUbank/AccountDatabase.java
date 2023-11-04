package RUbank;
import java.text.DecimalFormat;

/**
 * This class contains a linear data structure using an array to hold a list of
 * accounts with different types. The initial capacity of the array is 4. The capacity will be automatically
 * increased by 4 whenever the array is full.
 * @author Seth Yeh, Vinh Pham
 */

public class AccountDatabase {
    private Account[] accounts; //list of various types of accounts
    private int numAcct; //number of accounts in the array

    /**
     * getAccount() method: Getter for the accounts[] array.
     * @return Accounts[] array
     */
    public Account[] getAccounts(){
        return this.accounts;
    }

    /**
     * find() method:
     * @param a Account objext
     * @return index of elements in numAcct, -1 if not found
     */
    private int find(Account a) {
        for (int i = 0; i < numAcct; i++) {
            if (accounts[i] != null) {
                if(a.getClass() == accounts[i].getClass()){
                    if(accounts[i].equals(a)){
                        return i;
                    }
                }
            }
        }
        return -1;
    } //search for an account in the array

    /**
     * grow() method: increases array size by 4, and numAcct accordingly
     */
    private void grow() {
        int newNumofEvents = numAcct + 4;
        Account[] newAccounts = new Account[newNumofEvents];

        for (int i = 0; i < numAcct; i++) {
            newAccounts[i] = accounts[i];
        }
        accounts = newAccounts;
        numAcct = newNumofEvents;
    } //increase the capacity by 4

    /**
     * isEmpty(): Checks if accounts[] array is empty
     * @return true if is empty, no otherwise
     */
    public boolean isEmpty() {
        boolean check = true;

        if (this.accounts == null) {
            check = true;
        } else {
            for (Account i : this.accounts) {
                if (i != null) {
                    check = false;
                }
            }
        }
        return check;
    }

    /**
     * Contains() method: Checks if a given account is within the accounts[] array by using find()
     * @param account Account to search for
     * @return true if found, false otherwise
     */
    public boolean contains(Account account) {
        return find(account) != -1;
    } //overload if necessary

    /**
     * open() method: Opens an account by adding the object to the accounts[] array, while checking for
     * existing accounts.
     * @param account Account object to add
     * @return true if account is successfully added, false otherwise
     */
    public boolean open(Account account) {
        if (numAcct == 0) {
            grow();
            accounts[0] = account;
            return true;
        }
        else {
            if (!contains(account)) {
                int temp = -1;
                for (int i = 0; i < numAcct; i++) {
                    if (accounts[i] == null) {
                        temp = i;
                        break;
                    }
                }
                if (temp == -1) {
                    grow();
                    int temp2 = -1;
                    for (int i = 0; i < numAcct; i++) {
                        if (accounts[i] == null) {
                            temp2 = i;
                            break;
                        }
                    }
                    accounts[temp2] = account;
                }
                else {
                    accounts[temp] = account;
                }
                return true;
            }
            else {
                return false;
            }
        }
    } //add a new account

    /**
     * close() method: Closes an account by removing it from the accounts[] array by setting it to null, and then
     * rearranging array back in order.
     * @param account Account to be deleted
     * @return true if successfully removed, false otherwise
     */
    public boolean close(Account account) {
        if (!contains(account)) {
            return false;
        } else {
            int index = find(account);
            //System.out.println(index);
            if (index == numAcct - 1) {
                accounts[index] = null;
            } else {
                for(int i = index; i < numAcct-1; i++){
                    accounts[i] = accounts[i+1];
                }
                accounts[numAcct-1] = null;
                //printy();
            }
            return true;
        }
    } //remove the given account

    /**
     * withdraw() method: Withdraws money from an account in the accounts[] array by finding the profile of the same
     * account passed into the method, and subtracting balances.
     * @param account Copy of account that holds the amount to be subtracted
     * @return true if successfully withdrawaled, false otherwise
     */
    public boolean withdraw(Account account) {
        if (isEmpty() || find(account) == -1) {
            return false;
        } else
        if (account.getClass() == Checking.class ||
                account.getClass() == CollegeChecking.class) { // withdraw from checking/college
            if ((accounts[find(account)].balance >= account.balance)) {
                //System.out.println("WITHDRAWING...");
                accounts[find(account)].balance -= account.balance;
                return true;
            }
        }
        else if(account.getClass() == Savings.class){ // withdraw from savings
            if ((accounts[find(account)].balance >= account.balance)) {
                Savings temp = (Savings) accounts[find(account)];
                //System.out.println("WITHDRAWING...");
                temp.balance -= account.balance;
                return true;
            }
        }
        else{
            if ((accounts[find(account)].balance >= account.balance)){ // withdraw from MM
                MoneyMarket temp = (MoneyMarket) accounts[find(account)];
                if(temp.getWithdrawal() > 3){
                    //System.out.println("WITHDRAWING...");
                    temp.setWithdrawal(temp.getWithdrawal()+1);
                    temp.balance -= 10+account.balance;
                }
                else{
                    //System.out.println("WITHDRAWING...");
                    temp.setWithdrawal(temp.getWithdrawal()+1);
                    temp.balance -= account.balance;
                }
                temp.updateStatus();
                return true;
            }
        }
        return false;
    } //false if insufficient fund

    /**
     * deposit() method: Deposits money into an account through a given account which holds the deposit amount
     * @param account Account that holds deposit number
     */
    public void deposit(Account account) { // handling invalid numbers of deposits should be in transmanager
        if (isEmpty()) {
            System.out.println("Account Database is empty!");
        } else
        if (account.getClass() == Checking.class ||
                account.getClass() == CollegeChecking.class) { // depositing from checking/college
            //System.out.println("DEPOSITING...");
            accounts[find(account)].balance += account.balance;
        }
        else if(account.getClass() == Savings.class){ // depositing from savings
            Savings temp = (Savings) accounts[find(account)];
            //System.out.println("DEPOSITING...");
            temp.balance += account.balance;
        }
        else{
            //printtest();
            MoneyMarket temp = (MoneyMarket) accounts[find(account)]; // depositing from MM
            //System.out.println("DEPOSITING...");
            temp.balance += account.balance;
            temp.updateStatus();
        }
    }

    /**
     * sort() method: Sorts given array by Account type, and then Profile
     * @param sorted array to be sorted
     * @return sorted array
     */
    public Account[] sort(Account[] sorted){
        boolean swap;
        do {
            swap = false;
            for (int i = 0; i < numAcct - 1; i++) {
                if (sorted[i + 1] != null) {
                    if (sorted[i].compareTo(sorted[i + 1]) > 0) { //need to test compaoreTo
                        Account temp = sorted[i];
                        sorted[i] = sorted[i + 1];
                        sorted[i + 1] = temp;
                        swap = true;
                    }
                }
            }
        } while (swap);

        do {
            swap = false;
            for (int i = 0; i < numAcct - 1; i++) {
                if (sorted[i + 1] != null) {
                    if (sorted[i].getClass().getSimpleName().compareToIgnoreCase(sorted[i + 1].getClass().getSimpleName()) > 0) {
                        Account temp = sorted[i];
                        sorted[i] = sorted[i + 1];
                        sorted[i + 1] = temp;
                        swap = true;
                    }
                }
            }
        } while (swap);
        return sorted;
    }

    /**
     * printSorted() method: sorts the current accounts[] array by account type and profile then prints it
     */
    public String printSorted() {
        String output = "*Accounts sorted by account type and profile.\n";
        if(accounts[0] != null) {
            Account[] copy = new Account[numAcct];
            // copy array first
            for (int i = 0; i < numAcct; i++) {
                if (accounts[i] != null) {
                    copy[i] = accounts[i];
                }
            }
            sort(copy);
            for (Account a : copy) {
                if (a != null) {
                    output = output.concat(a.toString()+"\n");
                }
            }
            output = output.concat("*end of list.\n");
            return output;
        }
        return null;
    } //sort by account type and profile

    /**
     * printFeesAndInterests() method: sorts the current accounts[] array by account type and profile, then adds
     * their respective monthly interest and fees at the end of the string, then prints.
     */
    public String printFeesAndInterests() {
        DecimalFormat df = new DecimalFormat("#,###.00");
        String output = "*list of accounts with fee and monthly interest\n";
        if(accounts[0] != null) {
            Account[] copy = new Account[numAcct];
            // copy array first
            for (int i = 0; i < numAcct; i++) {
                if (accounts[i] != null) {
                    copy[i] = accounts[i];
                }
            }
            sort(copy);
            for (Account a : copy) {
                if (a != null) {
                    output = output.concat(a.toString()+"::fee $"+df.format(a.monthlyFee())+"::monthly interest $"+df.format(a.monthlyInterest())+"\n");
                }
            }
            output = output.concat("*end of list.\n");
            return output;
        }
        return null;
    } //calculate interests/fees

    /**
     * printUpdatedBalances() method: applies monthly interests and fees to accounts, then sorts by account type and
     * profile, then prints.
     */
    public String printUpdatedBalances() {

        String output = "*list of accounts with fees and interests applied.\n";
        if(this.accounts == null) {
            output = "Account Database is empty!";
            return output;
        }
        if(accounts[0] != null && numAcct != 0) {
            for (Account a : accounts) {
                if (a != null) {
                    a.applyMonthlyInterestsAndFees();
                }
            }
        }
        Account[] copy = new Account[numAcct];
        // copy array first
        for (int i = 0; i < numAcct; i++) {
            if (accounts[i] != null) {
                copy[i] = accounts[i];
            }
        }
        sort(copy);
        for (Account a : copy) {
            if (a != null) {
                output = output.concat(a.toString());
            }
        }
        output = output.concat("*end of list.\n");
        return output;

    } //apply the interests/fees



    public static void main(String[] args) {
        // test bed
        AccountDatabase test = new AccountDatabase();
        Date temp = new Date(1776, 7, 4);
        Date temp2 = new Date(2005, 7, 4);
        Profile a = new Profile("john", "smith", temp);
        Profile ab = new Profile("john", "ssmith", temp);
        Profile abc = new Profile("jo2hn", "Dsmith", temp);
        Profile abcd = new Profile("jo2hn", "Dsmith", temp2);

        Checking john = new Checking(a, 1200);

        System.out.println(test.open(john));
        System.out.println(test.open(john));



    }
}