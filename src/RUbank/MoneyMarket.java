package RUbank;

import java.text.DecimalFormat;

/**
 * This class extends the Savings class; includes one instance variable only, and defines the constants
 * for interest rate and fee.
 * @author Seth Yeh, Vinh Pham
 */

public class MoneyMarket extends Savings{
    //Instance variable
    private int withdrawal;

    //Constants
    final double marketSavingsInterestRate = 0.045;
    final double marketSavingsMonthlyFee= savingsMonthlyFee;

    /**
     * MoneyMarket Constructor
     * @param holder profile containing name and DOB
     * @param balance account balance
     * @param isLoyal Loyalty status
     * @param withdrawal number of withdrawals
     */
    public MoneyMarket(Profile holder, double balance, boolean isLoyal, int withdrawal) {
        super(holder, balance, isLoyal);
        this.isLoyal = true;
        this.withdrawal = withdrawal;
    }

    /**
     * setWithdrawal method: sets withdrawal amount to given input
     * @param input number of withdrawals
     */
    public void setWithdrawal(int input) {
        withdrawal = input;
    }

    /**
     * withdrawal variable getter
     * @return number of withdrawals
     */
    public int getWithdrawal() {
        return withdrawal;
    }

    /**
     * monthlyInterest Override: returns balance with applied MM interest rates
     * @return updated balance
     */
    @Override
    public double monthlyInterest() {
        if(isLoyal){
            return balance*((marketSavingsInterestRate+0.0025)/12);
        }
        else{
            return balance*((marketSavingsInterestRate)/12);
        }

    }
    /**
     * updateStatus() method: updates the Savings account's loyalty status based on balance
     */
    public void updateStatus(){
        if(balance < 2000){
            isLoyal = false;
        }
        else{
            isLoyal = true;
        }
    }

    /**
     * monthlyFee Override: returns the monthly fee of a MM savings account
     * @return if bal >= 2000, then return 0, otherwise return marketSavingsMonthlyFee.
     */
    @Override
    public double monthlyFee() {
        int with = 0;
        if(withdrawal > 3){
            for(int i = 3; i < withdrawal; i++){
                with++;
            }
            with*=10;
        }
        if (balance >= 2000) {
            return with;
        } else {
            return with+marketSavingsMonthlyFee;
        }
    }

    /**
     * applyMonthlyInterestsAndFees() method: applies monthly interests and fees and resets withdrawal to 0.
     */
    public void applyMonthlyInterestsAndFees(){

        balance-=monthlyFee();
        balance+=monthlyInterest();
        withdrawal = 0;
    }

    /**
     * toString Override that includes number of withdrawal
     * @return formatted string containing variables in a MM account
     */
    @Override
    public String toString(){
        DecimalFormat df = new DecimalFormat("#,###.00");
        if(isLoyal){
            return "Money Market::Savings::"+holder.getFname()+" "+holder.getLname()+" "
                    +holder.getDob().toString()+"::Balance $"+df.format(balance)+"::is loyal::withdrawal: "+withdrawal;
        }
        else{
            return "Money Market::Savings::"+holder.getFname()+" "+holder.getLname()+" "
                    +holder.getDob().toString()+"::Balance $"+df.format(balance)+"::withdrawal: "+withdrawal;
        }
    }
    /**
     * equals Override: compares MoneyMarket accounts to each other
     * @param mmAccount Account to compare
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object mmAccount){
        MoneyMarket m = (MoneyMarket) mmAccount;
        return super.equals(m);
    }

}