package DataStructs;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
// Using the Java time imports to keep track of Dates

// Etienne Feyrer und Jakub Schwenkbeck


public class WorkDay {
    /** This class models a Workday, expanding a normal Day in a time format
     * Other than just a date, a workday keeps track of hours worked on this particular day
     * Used to manage Working hours
     */

    // The date of the day in a Java Datetime Format
    public LocalDate Date;

    // The hours worked in a day
    public int Hours;

    // The month this day is in
    public int month;

    // The day value (1-31) of this current day
    public int day;


    /** Getters for Class fields     */
    public LocalDate getDate() {
        return Date;
    }

    public int getHours() {
        return Hours;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }


    /** Constructor for a Workday object
     * A Workday contains a date and the hours worked
     *
     * @param date Date of the day
     * @param hours Hours worked this day
     */

   public WorkDay(LocalDate date, int hours){




       this.Date = date;
       if(hours<0 || hours > 24){ // check if hours in bounds
           this.Hours = 0;
       }else{
           this.Hours = hours;
       }

        // extract day value and save in field
       DateTimeFormatter formatDay = DateTimeFormatter.ofPattern("dd");
       this.day = Integer.parseInt(Date.format(formatDay));

       // extract Month value and save in field
       DateTimeFormatter formatMonth = DateTimeFormatter.ofPattern("MM");
       this.month = Integer.parseInt(Date.format(formatMonth));

       //System.out.println("Day: " +this.day +"Month: " + this.month);





   }


}
