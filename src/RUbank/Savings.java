package RUbank;

import java.text.DecimalFormat;

/**
 * This class extends the Account class; includes one instance variable only, and defines the constants for
 * interest rate and fee.
 * @author Seth Yeh, Vinh pHam
 */

public class Savings extends Account{
    //Instance variable
    protected boolean isLoyal; //loyal customer status

    //Constants
    final double savingsMonthlyFee = 25;
    final double savingsInterestRate = 0.04;

    /**
     * Savings Constructor
     * @param holder profile, name, DOB
     * @param balance balance amount
     * @param isLoyal loyalty status
     */
    public Savings(Profile holder, double balance, boolean isLoyal) {
        super(holder, balance);
        this.isLoyal = isLoyal;
    }



    /**
     * setIsLoyal() method: sets loyalty status to given input
     * @param loyalty boolean containing true or false.
     */
    public void setIsLoyal(boolean loyalty){
        isLoyal = loyalty;
    }

    /**
     * isLoyal getter
     * @return isLoyal variable
     */
    public boolean getIsLoyal(){
        return isLoyal;
    }

    /**
     * monthlyInterest() Override: Overrides superclass monthly interest and fees by applying savings specific
     * interest rates and returning them instead.
     * @return balance with rates applied.
     */
    @Override
    public double monthlyInterest() {
        DecimalFormat df = new DecimalFormat("#0.00");
        if(isLoyal){
            return balance*((savingsInterestRate+0.0025)/12);
        }
        else{
            return balance*((savingsInterestRate)/12);
        }

    }

    /**
     * monthlyFee Override: calculates savings specific monthly fees to balance and returns them
     * @return 0 if balance >= 500, or savingsMonthlyFee otherwise
     */
    @Override
    public double monthlyFee() {
        if (balance >= 500) {
            return 0;
        } else {
            return savingsMonthlyFee;
        }
    }

    /**
     * applyMonthlyInterestsAndFees() method: Applies monthly fees and interest to balance.
     */
    public void applyMonthlyInterestsAndFees(){
        balance-=monthlyFee();
        balance+=monthlyInterest();
    }

    /**
     * toString Override that includes loyalty status.
     * @return formatted string containing variables in a Savings account
     */
    @Override
    public String toString(){
        DecimalFormat df = new DecimalFormat("#,###.00");
        if(isLoyal){
            return "Savings::"+holder.getFname()+" "+holder.getLname()+" "
                    +holder.getDob().toString()+"::Balance $"+df.format(balance)+"::is loyal";
        }
        else{
            return "Savings::"+holder.getFname()+" "+holder.getLname()+" "
                    +holder.getDob().toString()+"::Balance $"+df.format(balance);
        }
    }

    /**
     * equals Override: compares loyalty status along with its superclass equals.
     * @param savingAccount Account to compare
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object savingAccount){
        Savings s = (Savings) savingAccount;
        //System.out.println("CHECKING SHIZ IN HERE");
        return super.equals(s);
    }


}