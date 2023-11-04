package RUbank;

import java.text.DecimalFormat;

/**
 * This class extends the Checking class; includes one instance variable only, and defines the
 * constants for interest rate and fee.
 * @author Seth Yeh, Vinh Pham
 */

public class CollegeChecking extends Checking {
    //Instance variable
    private Campus campus; //campus code

    //Constants
    final double collegeCheckingFee = 12.0;
    final double collegeCheckingInterestRate = 0.01;

    public CollegeChecking(Profile holder, double balance, Campus campus) {
        super(holder, balance);
        this.campus = campus;
    }

    /**
     * Campus getter method
     */
    public Campus getCampus () {
        return this.campus;
    }

    /**
     * Campus setter method
     * @param a name of Campus
     */
    public void setCampus (Campus a) {
        campus = a;
    }
    /**
     * monthlyFee() method: returns monthly fee according to conditions of balance
     * @return 0 always since CollegeChecking accounts have 0 monthly fee.
     */
    @Override
    public double monthlyFee() {
        return 0;
    }

    /**
     * applyMonthlyInterestsAndFees() method: applies monthly interest and fees to balance.
     */
    public void applyMonthlyInterestsAndFees(){
        balance-=monthlyFee();
        balance+=monthlyInterest();
        formatBal();
    }

    /**
     * equals() Override: Compares two CollegeChecking accounts and sees if equal
     * @param collegeCheckingAccount Account to compare
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object collegeCheckingAccount){
        CollegeChecking c = (CollegeChecking) collegeCheckingAccount;
        //System.out.println("CHECKING SHIZ IN HERE");
        return super.equals(c) && campus.equals(c.getCampus());
    }

    /**
     * toString() Override: Formats the variables in Account into string format
     * @return formatted String
     */
    @Override
    public String toString(){
        DecimalFormat df = new DecimalFormat("#,###.00");
        return "College Checking::"+holder.getFname()+" "+holder.getLname()+" "
                +holder.getDob().toString()+"::Balance $"+df.format(balance)+"::"+campus;
    }

    public static void main(String[] args) {
        Date a = new Date (1776, 7, 7);
        Profile john = new Profile ("John", "johnson", a);
        CollegeChecking cc = new CollegeChecking(john, 5000, Campus.NEW_BRUNSWICK);
        System.out.println(cc.getCampus());
        System.out.println(cc.toString());
        System.out.println(cc.monthlyFee());
        System.out.println(cc.monthlyInterest());
        cc.applyMonthlyInterestsAndFees();

    }
}