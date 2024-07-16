package Main;

import java.io.IOException;
import DataStructs.*;
import CSV_IO.*;
// Etienne Feyrer und Jakub Schwenkbeck

public class Main {
    /** The Main Class handles the connection of all other Data and Util classes
     * Contains the psv Main function which starts the program
     */
    // This array models a year with index 1-12 and an empty space at index 0
    static Month[] year = new Month[13];


    // Project Array containing all Projects in the program
    static Project[] projs = new Project[10];

    //  keeping track of the amount of projects created
    static int projectcounter = 0;


    /** Main function assembles all parts of the program and starts the GUI
     *
     * @param args
     * @throws IOException
     */


    public static void main(String[] args) throws IOException {
        createYear(); // create a year

        new CSVReader(); // create a CSV Reader object

        new CSVWriter(); // create a CSV Writer object

        new GUI(); // open the GUI and starting the Window


    }

    /** addDayToArr adds a Day to the year
     *
     * @param day which will be added to the year
     */
    public static void addDayToArr(WorkDay day){
        Main.year[day.month].Days[day.day] = day;
        // making use of the properties saved directly in the Workday class to index the array

    }

    /** adds a Projects to the projects Array
     *
     * @param proj Project to be added
     */
    public static void addProj(Project proj){
        if(projectcounter< 10){
            projs[projectcounter] = proj;
            projectcounter++;
        }
        // nothing
    }

    /** createYear creates a new Year with all the months
     *
     */

public static void createYear(){
        //year[0] = new Month(0);

    year[1] = new Month(31);

    year[2] = new Month(29); // 2024 has 29 day in Feb :)

    year[3] = new Month(31);

    year[4] = new Month(30);

    year[5] = new Month(31);

    year[6] = new Month(30);

    year[7] = new Month(31);

    year[8] = new Month(31);

    year[9] = new Month(30);

    year[10] = new Month(31);

    year[11] = new Month(30);

    year[12] = new Month(31);

}


}
