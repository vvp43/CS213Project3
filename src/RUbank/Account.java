package RUbank;
import java.text.DecimalFormat;

/**
 * Abstract class that is the general type of the other account types; each account has a
 * profile that uniquely identifies the account holder.
 * @author Seth Yeh, Vinh Pham
 */
public abstract class Account implements Comparable<Account>{
    protected Profile holder;
    protected double balance;

    public abstract double monthlyInterest();
    public abstract double monthlyFee();

    /**
     * Constructor for the Account Class
     *
     * @param holder profile of account holder
     * @param balance balance of account holder
     */
    public Account(Profile holder, double balance){
        this.holder = holder;
        this.balance = balance;
    }

    /**
     * applyMonthlyInterestsAndFees() method: Method used to apply monthly interests and fees to the balance of
     * an Account object.
     */
    public void applyMonthlyInterestsAndFees(){
        balance+=monthlyInterest();
        balance-=monthlyFee();
    }

    /**
     * toString() Override: Prints account into a string
     *
     * @return returns string formatted version of the account class
     */
    @Override
    public String toString(){
        DecimalFormat df = new DecimalFormat("#,##0.00");
        return "Checking::"+holder.getFname()+" "+holder.getLname()+" "
                +holder.getDob().toString()+"::Balance $"+df.format(balance);
    }

    /**
     * equals() Override: Compares profiles of accounts and returns true or false.
     *
     * @param account Account to compare
     * @return true if profile is equal, false otherwise
     */
    @Override
    public boolean equals(Object account){
        Account a = (Account) account;
//        System.out.println("first cmp: "+holder.getFname()+" "+holder.getLname()+" Date: "+holder.getDob().toString());
//        System.out.println("second  cmp: "+a.holder.getFname()+" "+a.holder.getLname()+" Date: "+a.holder.getDob().toString());

        return holder.getFname().equalsIgnoreCase(a.holder.getFname()) &&
                holder.getLname().equalsIgnoreCase(a.holder.getLname()) &&
                holder.getDob().equals(a.holder.getDob());
    }

    /**
     * compareTo() method: compares two account objects in order of profile.
     *
     * @param a the object to be compared.
     * @return 0 if equal, 1 or greater if greater lexicographically, -1 or more otherwise
     */
    @Override
    public int compareTo(Account a){
        if(a.getClass() == Checking.class){
            int ab = Checking.class.getSimpleName().compareToIgnoreCase(a.getClass().getSimpleName());
            if(ab != 0){
                return ab;
            }
            int temp = holder.getLname().compareToIgnoreCase(a.holder.getLname());
            if(temp != 0){
                return temp; // return if last name is alphabetically first
            }
            int temp2 = holder.getFname().compareToIgnoreCase(a.holder.getFname());
            if(temp2 != 0){
                return temp2;
            }
            return holder.getDob().compareTo(a.holder.getDob());
        }
        else if(a.getClass() == CollegeChecking.class){
            int ab = CollegeChecking.class.getSimpleName().compareToIgnoreCase(a.getClass().getSimpleName());
            if(ab != 0){
                return ab;
            }
            int temp = holder.getLname().compareToIgnoreCase(a.holder.getLname());
            if(temp != 0){
                return temp; // return if last name is alphabetically first
            }
            int temp2 = holder.getFname().compareToIgnoreCase(a.holder.getFname());
            if(temp2 != 0){
                return temp2;
            }
            return holder.getDob().compareTo(a.holder.getDob());
        }
        else if(a.getClass() == Savings.class){
            int ab = Savings.class.getSimpleName().compareToIgnoreCase(a.getClass().getSimpleName());
            if(ab != 0){
                return ab;
            }
            int temp = holder.getLname().compareToIgnoreCase(a.holder.getLname());
            if(temp != 0){
                return temp; // return if last name is alphabetically first
            }
            int temp2 = holder.getFname().compareToIgnoreCase(a.holder.getFname());
            if(temp2 != 0){
                return temp2;
            }
            return holder.getDob().compareTo(a.holder.getDob());
        }
        else{
            int ab = MoneyMarket.class.getSimpleName().compareToIgnoreCase(a.getClass().getSimpleName());
            if(ab != 0){
                return ab;
            }
            int temp = holder.getLname().compareToIgnoreCase(a.holder.getLname());
            if(temp != 0){
                return temp; // return if last name is alphabetically first
            }
            int temp2 = holder.getFname().compareToIgnoreCase(a.holder.getFname());
            if(temp2 != 0){
                return temp2;
            }
            return holder.getDob().compareTo(a.holder.getDob());
        }
    }
}