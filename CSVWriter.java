package CSV_IO;

import DataStructs.*;

import java.io.*;
import java.text.Format;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.StringTokenizer;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
// Etienne Feyrer und Jakub Schwenkbeck

public class CSVWriter {

    public static String pathedit;// = "C:\\Users\\Dell\\IdeaProjects\\Csv for Workhours\\Work hours eddited\\Work hours.csv";
  //  public static String testpath = "C:\\Users\\Dell\\IdeaProjects\\Csv for Workhours\\Work hours.csv";

    /**
     * Sets the path for a new writer call
     * Sets it to the given value when long enough
     * Sets it to default {@code pathedit} when required length has not been reached
     * @param newpath given path
     */
    public static void SetPath(String newpath){
        if(newpath.length() > 10){
            pathedit = newpath;
        }
        else{
            pathedit = pathedit;}
    }


    /**
     *
     * @param Years input is instance of year implemented in Main
     * @throws IOException
     *<p>
     * 1. Starts of by generating new csv file {@code csvFile}in given path
     * <p>
     * 2. A new FileWriter {@code fileWriter} is called on the new file
     * <p>
     * 3. Header is written separately at the beginning
     * <p>
     * 4. Starts Iteration for months in years:
     * <p>
     *      - if empty -> leave
     *  <p>
     *      - if not open new line
     *  <p>
     *      - start second Iteration for days in a month
     * <p>
     *      - fill in with date and hours for each day
     * <p>
     *      - underlines each block of month with a total value
     */
    public static void WriterCSV(Month[] Years) throws IOException {
        File csvFile = new File(pathedit);
        FileWriter fileWriter = new FileWriter(csvFile);

        String header = "Date,working hours,";
        fileWriter.write(header + "\n");

        for (Month month : Years) {
            if (month != null) {
                StringBuilder line = new StringBuilder();
                for (int i = 0; i < month.Days.length; i++) {
                    if (month.Days[i] != null && month.Days[i].Date != null) {
                        line.append(month.Days[i].Date);
                        line.append(",");
                        line.append(month.Days[i].Hours);
                        line.append(",,");
                        line.append("\n");
                    }
                }
                line.append("Total Sum,").append(month.getTotal()).append(",,,");
                line.append("\n");
                fileWriter.write(line.toString());
            }
        }
        fileWriter.close();
    }
}



