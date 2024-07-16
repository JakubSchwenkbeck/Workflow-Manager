package CSV_IO;

import DataStructs.*;
import Main.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.StringTokenizer;

// Etienne Feyrer und Jakub Schwenkbeck


public class CSVReader {
    public static String testpath = "C:\\Users\\Dell\\IdeaProjects\\Csv for Workhours\\Work hours.csv";

    public static void SetPath(String newpath){


        if(newpath.length() > 10){
        testpath = newpath;
        System.out.println(" this is the path" +testpath);
    }
    else{
    testpath = "C:\\Users\\Dell\\IdeaProjects\\Csv for Workhours\\Work hours.csv";}
    System.out.println("This is the path:" + testpath);
    }


    public static LocalDate Format(String entry){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(entry, formatter);
        return date;
    }

    private static boolean shouldSkipLine(String line){
        return line.contains("Datum:,Arbeitszeit in Stunden:,")|| line.contains("Total Sum");
    }


    public static void ReadCSV(String path) throws IOException{
        BufferedReader reader = new BufferedReader(new FileReader(path));
            String line;
            ArrayList<String> firstColumnTokens = new ArrayList<>(); // New ArrayList for second column
            ArrayList<String> secondColumnValues = new ArrayList<>(); // New ArrayList for second column
            while ((line = reader.readLine()) != null) {
                if(shouldSkipLine(line)){
                    continue; //skip unwanted lines
                }
                StringTokenizer tokens = new StringTokenizer(line, ","); //splits the line in several tokens with the delimiter sign:","
                if (tokens.hasMoreTokens()) {
                    String firstToken = tokens.nextToken();
                    firstColumnTokens.add(firstToken);                          // the first token is added to the first column array

                    // Assuming the second token corresponds to the second column
                    if (tokens.hasMoreTokens()) {
                        String secondToken = tokens.nextToken();
                        secondColumnValues.add(secondToken);                    // the second token is added tto the second column
                    }
                }
            }

            int n = 1;
            while (n<firstColumnTokens.size()-1){
                Main.addDayToArr( new WorkDay(Format(firstColumnTokens.get(n)), (int)Double.parseDouble(secondColumnValues.get(n))));
                n= n+1;

            }

    }
}


