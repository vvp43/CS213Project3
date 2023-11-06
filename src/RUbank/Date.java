package RUbank;

import java.util.Calendar;

/**
 * This Date class retrieves information
 * about date which contains year, month and day
 *
 * @author Seth Yeh, Vinh Pham
 */
public class Date implements Comparable<Date> {
    private int year;
    private int month;
    private int day;
    Calendar event = Calendar.getInstance(); // the event date

    /**
     * Constructor with param year, month and day
     *
     * @param year event year
     * @param month event month
     * @param day event day
     */
    public Date(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
        event.set(year, month, day);
    }

    /**
     * getYear() method
     * @return year
     */
    public int getYear() {
        return this.year;
    }

    /**
     * getMonth() method
     * @return month
     */
    public int getMonth() {
        return this.month;
    }

    /**
     * getDay() method
     * @return day
     */
    public int getDay() {
        return this.day;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString(){
        return month+"/"+day+"/"+year;
    }

    /**
     * equals() method: Check if two dates are the same
     * @param input any Object which is relevant to Date
     * @return true if the dates are the same
     */
    @Override
    public boolean equals(Object input){
        Date a = (Date) input;
        int inputDateInt = a.year * 10000 + a.month * 100 + a.day;
        int currentDateInt = this.year * 10000 + this.month * 100 + this.day;
        int diff = currentDateInt - inputDateInt;
        return diff == 0;


    }

    /**
     * compareTo() method: Compare to dates if it is before, same of after the original date
     * @param input the object to be compared.
     * @return 1 if current date is after input date,
     * 0 if current date is the same with output date,
     * -1 if current date is before input date
     */
    @Override
    public int compareTo(Date input) {
        int y = Integer.compare(this.year, input.year);
        if(y != 0){
            return y;
        }
        int m = Integer.compare(this.month, input.month);
        if(m != 0){
            return m;
        }
        return Integer.compare(this.day, input.day);

    }


    /**
     * isLeapYear method(): checks if year is a leap year
     * @param year year to check
     * @return true if it is, false otherwise
     */
    private boolean isLeapYear(int year) {
        boolean is_Leap = false;
        if (year % 4 == 0) {
            if (year % 100 == 0) {
                if (year % 400 == 0) {
                    is_Leap = true;
                }
            } else {
                is_Leap = true;
            }
        }
        return is_Leap;
    }

    /**
     * isValidDate() method : used to check if the day is valid in a month
     * @param date date to check
     * @return boolean true or false
     */
    private boolean isValidDate(Date date) {
        int year = date.getYear();
        int month = date.getMonth();
        int day = date.getDay();
        if (month > 0 && month < 13) {
            switch (month) {
                case 1, 3, 5, 7, 8, 10, 12:
                    return day <= 31 && day > 0;
                case 4, 6, 9, 11:
                    return day <= 30 && day > 0;
                case 2:
                    if (isLeapYear(year)) return day <= 29 && day > 0;
                    else return day <= 28 && day > 0;
                default:
                    return true;
            }
        }
        return false;
    }

    /**
     * isFutureDate method():Checks if date is in the future or not
     * @param input date to compare
     * @return true if inputDate is in the future, false otherwise
     */
    private boolean isFutureDate(Date input) {
        Calendar cmp = Calendar.getInstance();
        int currentYear = input.event.get(Calendar.YEAR);
        int currentMonth = input.event.get(Calendar.MONTH) - 1;
        int currentDay = input.event.get(Calendar.DAY_OF_MONTH);

        Calendar temptemp = Calendar.getInstance();
        temptemp.set(currentYear, currentMonth, currentDay);


        if(temptemp.after(cmp)){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * isWithinSixMonths() method: Checks if date is within 6 months of the current date
     * @param inputDate date to compare
     * @return true if inputDate is within 6 months of the current date, false otherwise
     */
    private boolean isWithinSixMonths(Date inputDate) {
        if (inputDate.isValidDate(inputDate)) {
            if (isFutureDate(inputDate)) {
                Calendar temp = Calendar.getInstance();
                temp.add(Calendar.MONTH, 7);
                return !inputDate.event.after(temp);
            }
        }

        return false;
    }

    /**
     * currentDay() method: check if the date is current Day
     * @param thisEvent Date
     * @return true if date is the current date
     */
    private boolean currentDay(Date thisEvent) {
        Calendar cmp = Calendar.getInstance();
        int currentYear = event.get(Calendar.YEAR);
        int currentMonth = event.get(Calendar.MONTH) - 1;
        int currentDay = event.get(Calendar.DAY_OF_MONTH);


        Calendar temptemp = Calendar.getInstance();
        temptemp.set(currentYear, currentMonth, currentDay);
        //System.out.println(temptemp.get(Calendar.YEAR)+temptemp.get(Calendar.MONTH)+temptemp.get(Calendar.DAY_OF_MONTH));

        return temptemp.get(Calendar.YEAR) == cmp.get(Calendar.YEAR) && temptemp.get(Calendar.MONTH) == cmp.get(Calendar.MONTH)
                && temptemp.get(Calendar.DAY_OF_MONTH) == cmp.get(Calendar.DAY_OF_MONTH);

    }

    /**
     * isUnder16() method: check if age is under 16
     * @param input date of birth
     * @return true if age is under 16
     */
    private boolean isUnder16(Date input) {
        Calendar cmp = Calendar.getInstance();
        int currentYear = input.event.get(Calendar.YEAR);
        int currentMonth = input.event.get(Calendar.MONTH) - 1;
        int currentDay = input.event.get(Calendar.DAY_OF_MONTH);

        Calendar temptemp = Calendar.getInstance();
        temptemp.set(currentYear, currentMonth, currentDay);

        cmp.add(Calendar.YEAR, -16);
//        System.out.println(cmp.get(Calendar.YEAR));
//        System.out.println(cmp.get(Calendar.MONTH));
//        System.out.println(cmp.get(Calendar.DAY_OF_MONTH));


        if(temptemp.after(cmp)){
            return true;
        }
        else{
            return false;
        }

    }

    /**
     * isUnder24() method: check if age is under 24
     * @return true if age is under 24
     */
    public boolean isUnder24() {
        Calendar cmp = Calendar.getInstance();
        int currentYear = event.get(Calendar.YEAR);
        int currentMonth = event.get(Calendar.MONTH) - 1;
        int currentDay = event.get(Calendar.DAY_OF_MONTH);

        Calendar temptemp = Calendar.getInstance();
        temptemp.set(currentYear, currentMonth, currentDay);

        cmp.add(Calendar.YEAR, -24);
//        System.out.println(cmp.get(Calendar.YEAR));
//        System.out.println(temptemp.get(Calendar.MONTH));
//        System.out.println(temptemp.get(Calendar.DAY_OF_MONTH));


        if(temptemp.after(cmp)){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * isValid() method: Checks if an event is valid meaning within 6 months, cannot be in the future, and is an actual date possible.
     * @return true if all conditions above are met, otherwise false
     */
    public String isValid() {
        int y = this.year;
        int m = this.month;
        int d = this.day;
        Date thisEvent = new Date(y, m, d);
        if(isValidDate(thisEvent)) {
            //System.out.println("br");
            if (!currentDay(thisEvent) && !isFutureDate(thisEvent)) {
                //System.out.println("bru");
                if (!isUnder16(thisEvent)) {
                    return "";
                }
                else{
                    return "DOB invalid: "+ m + "/" + d + "/" + y + " under 16.";

                }
            } else {
                return "DOB invalid: " + m + "/" + d + "/" + y + " cannot be today or a future day.";
            }
        }
        else{
            return "DOB invalid: " + m + "/" + d + "/" + y + " not a valid calendar date!";
        }
    }




    /**
     * Testbed main()
     *
     */
    public static void main(String[] args) {
        /**
         Demonstrate test case for isValid()
         isValid() is designed based on three conditions
         1. The date is valid ( )
         2. The date is in the future
         3. The date is within 6 months;
         */

        /**
         * Example Dates (Today's date: 9/30/2023)
         */

        /**
         * Test cases
         */
        Date a = new Date(1999, 10, 20);
        Date b = new Date(2023, 11, 12);
        Date c = new Date(2023, 10, 12);
        Date d = new Date(20057, 11, 32);
        Date g = new Date(2007, 10, 31);
        Date e = new Date(2007, 10, 1);
        Date f = new Date(1987, 1, 15);
        Date aw = new Date(1999, 10, 31);

//
//        System.out.println(e.isValid());
//        System.out.println(g.isValid());
//        System.out.println(f.isValid());
//        System.out.println(aw.isValid());
//        System.out.println(a.isUnder24());
    }
}