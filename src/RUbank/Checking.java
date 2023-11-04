package RUbank;

import java.text.DecimalFormat;

/**
 * This class extends the Account class without defining any additional instance variable, but defines the
 * constants for interest rate and fee.
 * @author Seth Yeh, Vinh Pham
 */

public class Checking extends Account {
    //constants
    final double checkingMonthlyFee = 12.0;
    final double checkingInterestRate = 0.01;

    /**
     * monthlyInterest() method: overrides Account's method
     * @return monthlyInterest rate of Checking account
     */
    @Override
    public double monthlyInterest() {
        return balance * (checkingInterestRate / 12);
    }

    /**
     * monthlyFee() method: returns monthly fee according to conditions of balance
     * @return 0 if balance >= 1000, or checkingMonthlyFee if not.
     */
    @Override
    public double monthlyFee() {
        if (balance >= 1000) {
            return 0;
        } else {
            return checkingMonthlyFee;
        }
    }

    /**
     * applyMonthlyInterestsAndFees() method: applies monthly interests and fees to balance
     */
    @Override
    public void applyMonthlyInterestsAndFees() {
        balance -= monthlyFee();
        balance += monthlyInterest();
        formatBal();
    }


    /**
     * Checking object Constructor
     * @param holder  Profile of account, containing first and last name, and DOB
     * @param balance balance of account
     */
    public Checking(Profile holder, double balance) {
        super(holder, balance);
    }

}
