package RUbank;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * JUnit test for Database close() method
 * @author Seth Yeh
 */

public class AccountDatabaseTest {

    @Test
    public void closeFalseCase() {
        AccountDatabase test = new AccountDatabase();
        Date d = new Date(1776, 7, 4);
        Profile p = new Profile("john", "smith", d);
        Checking john = new Checking(p, 50000);

        test.open(john);
        assertTrue(test.close(john));


    }
    @Test
    public void closeTrueCase() {
        AccountDatabase test = new AccountDatabase();
        Date d = new Date(1776, 7, 4);
        Profile p = new Profile("john", "smith", d);
        Checking john = new Checking(p, 50000);
        assertFalse(test.close(john));
    }
}