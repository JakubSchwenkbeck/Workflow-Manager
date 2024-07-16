package DataStructs;
import Main.*;

import javax.swing.*;
import java.awt.*;
// Etienne Feyrer und Jakub Schwenkbeck

public class Project {
    /** A Project contains of a ToDo-List and a Name
     * Used to ease Project management and workflow
     */

    // fields
  public  ToDoList Tasks;
  public   String name;

  // basic constructor

    public Project(ToDoList Tasks,String name){
        this.Tasks = Tasks;
        this.name = name;
    }


    // UTILS


    /** empty List empties the list of this Project object
     *  emptying/clearing the list means setting it to a plain list with empty Strings
     */

    public void emptyList(){
        for (int i = 0; i < this.Tasks.Texts.length-1; i++) {
            this.Tasks.Texts[i] = "";
        }
    }
    // track progress



}
