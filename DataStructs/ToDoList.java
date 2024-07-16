package DataStructs;

import Main.GUI;

import java.util.HashMap;

// Etienne Feyrer und Jakub Schwenkbeck


public class ToDoList {

    /** This class models a ToDo-List
     *  Main part is a String array 'Texts', where each entry is a Element of the ToDo-List
     *  Used in the GUI Class to ease Workflow management
     */


    // Main core of the class, contains the strings which make up the list
   public String[] Texts;
   // counting the elements to prevent overflow
    public int elemcounter ;

    /* This Hashmap maps String values to Booleans
        The keys are the elements of Texts, mapping to a boolean
        which determines if the element is crossed/checked (checked => true)
     */
    public  HashMap<String, Boolean> StringBinaryMap = new HashMap<>();




    // Constructor with Max length
    public ToDoList(int maxLength){
       Texts = new String[maxLength];
       elemcounter = 0;
    }

    /** get Text return the text of a certain index
     *
     * @param index
     * @return String at index
     */
    public String getText(int index){
        try {

        return Texts[index];
        }catch (IndexOutOfBoundsException e){
            // not in bounds
            return "";
        }

    }

    /** set Text by input msg and boolean determining if it is crossed
     *
     * @param msg The message to set
     * @param crossed boolean, if message is crossed/checked
     */
    public void setText(String msg,Boolean crossed){
            int index = elemcounter % Texts.length; // loop if index is exceeded
            Texts[index] = msg;
            StringBinaryMap.put(msg,crossed); // put in map
       // System.out.println(msg + index);
            elemcounter++;
    }



    /** delete List is static and deletes a given List
     *
     * @param list which will be cleared
     */

    public static void deleteList(ToDoList list){
        list.elemcounter = 0;
        for (int i = 0; i < list.Texts.length; i++) {
            list.Texts[i] = "";
        }
        GUI.updateToDo(GUI.currentList);


    }


}
