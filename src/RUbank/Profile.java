package RUbank;

/**
 * Define the profile of an account holder as follows.
 * @author Seth Yeh, Vinh Pham
 */

public class Profile implements Comparable<Profile>{
    private String fname;
    private String lname;
    private Date dob;

    /**
     * Profile constructor
     * @param fname First name
     * @param lname Last name
     * @param dob Date of Birth
     */
    public Profile(String fname, String lname, Date dob){
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
    }

    /**
     * First name getter method
     * @return First Name
     */
    public String getFname() {
        return fname;
    }

    /**
     * Last name getter method
     * @return Last Name
     */
    public String getLname() {
        return lname;
    }

    /**
     * Date of birth getter method
     * @return Date of birth
     */
    public Date getDob() {
        return dob;
    }

    @Override
    public boolean equals(Object input){
        Profile cast = (Profile) input;
        return fname.equals(cast.fname) && lname.equals(cast.lname) && dob.equals(cast.dob);
    }
    @Override
    public int compareTo(Profile o) {
        return 0;
    }
}