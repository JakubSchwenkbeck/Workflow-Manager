package DataStructs;
import Main.*;

// Etienne Feyrer und Jakub Schwenkbeck

public class Month {
    /** This class models a Month in a year.
    * A Month consists of an array of WorkDays
    * A Month implements two constructors and a getTotal Method
     * Used to manage Working hours
     */

    // Array of WorkDays modelling an extended version of a Java Month
    public WorkDay[] Days ;


    // Constructors
    public Month(int days){
        this.Days =  new WorkDay[days+1];
    }
    public Month(WorkDay[] list){
        Days = list;
    }


    /** This function counts up the total Working hours of each Workday in the Month object
     * @return Integer with total hours worked in a month
     */

    public int getTotal(){
        int count = 0;
        for (int i = 1; i <= Days.length -1 ; i++) {
            if(this.Days[i] != null){
            count += this.Days[i].Hours;

            }

        }
        return count;
    }






}
