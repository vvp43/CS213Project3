package RUbank;

import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.*;

/**
 * Junit test for Date class' isValid() method
 */
public class DateTest {

    @Test
    public void checkRealDate(){
        Date a = new Date(2011, 2, 29); // check if isValid recognizes real dates
        assertFalse(a.isValid().isEmpty());
    }
    @Test
    public void checkFutureDate(){
        Date b = new Date(2000000, 2, 29); // check if isValid recognizes future dates
        assertFalse(b.isValid().isEmpty());
    }
    @Test
    public void checkCurrDay(){
        Calendar temp = Calendar.getInstance();
        int currentYear = temp.get(Calendar.YEAR);
        int currentMonth = temp.get(Calendar.MONTH)+1;
        int currentDay = temp.get(Calendar.DAY_OF_MONTH);
        Date c = new Date(currentYear, currentMonth, currentDay); // check if isValid recognizes current dates
        assertFalse(c.isValid().isEmpty());
    }
    @Test
    public void checkFaultyDate(){
        Date e = new Date(1, 19, 35); // check if isValid recognizes faulty months days etc
        assertFalse(e.isValid().isEmpty());
    }

    @Test
    public void checkifUnder16(){
        Calendar temp = Calendar.getInstance();
        int currentYear = temp.get(Calendar.YEAR);
        int currentMonth = temp.get(Calendar.MONTH)+1;
        int currentDay = temp.get(Calendar.DAY_OF_MONTH);
        Date c = new Date(currentYear-16, currentMonth, currentDay+1); // check if isValid recognizes current dates
        assertFalse(c.isValid().isEmpty());
    }

    @Test
    public void beforeToday16YearsAgo(){
        Calendar temp = Calendar.getInstance();
        int currentYear = temp.get(Calendar.YEAR);
        int currentMonth = temp.get(Calendar.MONTH)+1;
        int currentDay = temp.get(Calendar.DAY_OF_MONTH);
        Date f = new Date (currentYear-16, currentMonth, currentDay-1); // check if isValid recognizes day before today, but 16 years in the past
        assertTrue(f.isValid().isEmpty());
    }

    @Test
    public void dayBefore16YearsAgo(){
        Calendar temp = Calendar.getInstance();
        int currentYear = temp.get(Calendar.YEAR);
        int currentMonth = temp.get(Calendar.MONTH)+1;
        int currentDay = temp.get(Calendar.DAY_OF_MONTH);
        Date g = new Date (currentYear-16, currentMonth, currentDay); // check if isValid recognizes day before but same month
        assertTrue(g.isValid().isEmpty());
    }

}