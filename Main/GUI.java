package Main;

// Java Swing imports (work out of the box)
import  javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// imports exception for handling
import java.io.IOException;

// imports to handle time
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

// Java API Datastructures
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

// Our own packages used in this class
import DataStructs.*;
import CSV_IO.*;


// Etienne Feyrer und Jakub Schwenkbeck


/** The GUI Class handles all GUI Elements and their events
 * We tried to separate GUI-creating code and Utility code the best we could
 * We use Java swing, it might not be the most modern way, but has a pretty low threshold for beginners
 * All GUI-creating code is commented,so no real JavaSwing knowledge is needed
 * Good to know: An Action Listener is a callback triggered when the User interacts with a GUI element
 */
public class GUI  {

    // Creating Java swing objects which span over multiple functions or even classes

    //-----------------------------JAVA SWING OBJECTS-------------------------------------------------------------------

    // We use JTextfields as editable input field for user inputs
    public JTextField DateText= new JTextField(20); // User Input for Dates
    public JTextField HourText= new JTextField(20); // User Input for working hours


    public JTextField SetPathReader = new JTextField(); // User Input for Path of the CSV Reader
    public JTextField SetPathWriter = new JTextField(); // User Input for Path of the CSV Writer


    // A JLabel is simply Text in the window
    // Those to labels pop up after a read / write operation was successfully
    public JLabel ReadAccepted = new JLabel("Read Successfully!");
    public  JLabel Written = new JLabel("Written Successfully!");


    // This is the Panel which the ToDoList is displayed in
    public static JPanel taskListPanel = new JPanel();

    // The addButton adds a Task to the current ToDoList
    public  JButton addButton = new JButton("Add Task");


    //creating a custom Font for Headline
    public   Font customFont = new Font("Bebas Neue", Font.BOLD, 18);

    // A combo box lets the user choose from options, here used to choose from Projects to display and edit
    public static JComboBox<String> cb = new JComboBox<String>();

    // A Progressbar displays progress, here used to display the progress made in a project
    public static JProgressBar ProjProg = new JProgressBar();


    //------------------------------------OTHER DATA------------------------------------------------------------------


    // This Hashmap Maps a String to a Project
    // The String is the name of the project which it is the key to
    // This lets us model a combo box with a string array and still have an O(1) access to the project behind the name
    public static HashMap<String, Project> ProjectMap = new HashMap<String, Project>();


    // Initializing an empty ToDoList, later reassigned in runtime
    // The currentList is the List displayed in the ToDoPanel and therefore is updated
    public static ToDoList currentList = new ToDoList(8);

    // Keeping track of the currently selected Month in the context of getTotal (values 1-12 for jan - dec)
    public int SelectedMonth;

    // The choices String array keeps track of the (self created) projects one can choose from
    public String[] choices = new String[10] ;

    // The Project index keeps track of the projects created
    public int projectindex = 0;



    //-------------------------------------------CONSTRUCTOR-----------------------------------------------------

    /** The constructor of this class creates the window and all the gui elements
     * Is called only once in the Main Class
     */
    public GUI(){
        // creating the frame (= window) oor the gui
        JFrame frame = new JFrame("Projekt Etienne und Jakub"); // Setting the name of the window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Setting the exit options as default
        frame.setSize(1000, 600); // size of window



        JPanel panel = new JPanel() { // A JPanel is like a Canvas. panel is the Panel in which everything lies

            // Might be ignored, difficult and fancy way to create the blue outlines in the window
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawLines(g); // Zeichne Linien in der paintComponent Methode
            }

            private void drawLines(Graphics g) {
                // Set color of line
                g.setColor(Color.BLUE);

                // Rectangle upper half
                g.drawLine(250,35,940,35);
                g.drawLine(940,35,940,250);
                g.drawLine(940,250,200 ,250);
                g.drawLine(10,550,10,35);

                // between lines in the upper half

                g.drawLine(440,45,440,240);
                g.drawLine(460,97+45, 920 , 97+45);

                //rectangle lower half
                g.drawLine(440,265,440,540);

                g.drawLine(10,550,940,550);

                g.drawLine(940,550,940,250);


            }
        };

        // add the panel to our window
        frame.add(panel);


        // function which handles the creation of all elements
        placeComponents(panel);



        frame.setVisible(true); // Window will be visible after everything is built


    }

    /** place Components handles the placement and creation of every JavaSwing element, mostly by other function calls
     *
     * @param panel in which components will be placed
     */
    private void placeComponents(JPanel panel) {


        //FUNCTION CALLS:
        initializeObjects(panel); // initializes interactive elements

        createUserInputGUI(panel); // creates and handles the GUI for Userinput in the upper left corner

        createGetMonthlyGUI(panel); // creates and handles the GUI for the 'get Total of a Month' function

        setCSVActionGUI(panel); //create GUI to interact with the CSV I/O

        setButtons(panel);  // handle complex CSV Buttons and especially their callbacks

        setToDoGUI(panel); // create GUI for ToDoList

        setProjectGUI(panel); // create GUI for Project
    }

    public void initializeObjects(JPanel panel){
        // create an empty Project in the beginning, from which new projects can be created
        ProjectMap.put("No Project Selected",new Project(new ToDoList(1),"No Projects yet") );

        // create the task list panel
        taskListPanel.setBounds(30,320,400,200);
        panel.add(taskListPanel);
        taskListPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // create combo box
        cb.setBounds(700,270,200,25);
        panel.add(cb);
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);


    }

    public void createUserInputGUI(JPanel panel){

        // Labels for userinput field in the top left
        JLabel HeaderLabel = new JLabel("Worktime Management: ");
        HeaderLabel.setFont(customFont);
        HeaderLabel.setBounds(20, 20, 220, 30);
        panel.add(HeaderLabel);



        JLabel DateLabel = new JLabel("Date:(Format dd.MM.yyyy)");
        DateLabel.setBounds(30, 60, 180, 25);
        panel.add(DateLabel);


        JLabel HourLabel = new JLabel("Workhours:");
        HourLabel.setBounds(30, 90, 180, 25);
        panel.add(HourLabel);


        //edit fields for user input

        this.DateText.setBounds(200, 60, 165, 25);
        panel.add(this.DateText);



        this.HourText.setBounds(200, 90, 165, 25);
        panel.add(this.HourText);




    }

    public void createGetMonthlyGUI(JPanel panel){
        // Labels
        JLabel Monthtext = new JLabel("Get hours from Month:");
        Monthtext.setBounds(30,170,180,25);
        Monthtext.setFont(new Font("Dialog", Font.BOLD,14));

        panel.add(Monthtext);

        JLabel TotalMonth = new JLabel("0");
        TotalMonth.setBounds(80,200,180,25);
        panel.add(TotalMonth);


        setCombinebox(panel,TotalMonth); // set the combine box from which the month is selected
    }

    /** creates CombineBox to choose Month for total hours, handles Action listener
     *
     * @param panel in which the box is set
     * @param label which is changed based on the value the selected month gives
     */
    public void setCombinebox(JPanel panel,JLabel label){
        // Strings from which the User can choose, here all Months
        String[] choices = { "January", "February", "March", "April",
                "May", "June" ,"July", "August", "September" ,"October" , "November", "December"};


        final JComboBox<String> cb = new JComboBox<String>(choices); // initialize with String array
        cb.setBounds(210,170,100,25);
        panel.add(cb);

        cb.addActionListener (new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                String selectedValue = cb.getSelectedItem().toString();
                switch (selectedValue) { // switch to get current Month
                    case "January" :
                        SelectedMonth = 1;
                        break;
                    case "February" :
                        SelectedMonth = 2;
                        break;
                    case "March" :
                        SelectedMonth = 3;
                        break;
                    case "April" :
                        SelectedMonth = 4;
                        break;
                    case "May" :
                        SelectedMonth = 5;
                        break;
                    case "June" :
                        SelectedMonth = 6;
                        break;
                    case "July" :
                        SelectedMonth = 7;
                        break;
                    case "August" :
                        SelectedMonth = 8;
                        break;
                    case "September" :
                        SelectedMonth = 9;
                        break;
                    case "October" :
                        SelectedMonth = 10;
                        break;
                    case "November" :
                        SelectedMonth = 11;
                        break;
                    case "December" :
                        SelectedMonth = 12;
                        break;




                }


                label.setText("" + Main.year[SelectedMonth].getTotal()); // set label text to total hours of month
                // making use of class method get Total in Month



            }
        });
    }
    public void setCSVActionGUI(JPanel panel){
        // Labels
        JLabel CSVreadPath = new JLabel("Path :");

        CSVreadPath.setBounds(460, 100, 50, 25);
        panel.add(CSVreadPath);



        // Label which appears when Data is read successfully
        ReadAccepted.setBounds(650,60,150,25);
        ReadAccepted.setForeground(Color.GREEN);
        ReadAccepted.setVisible(false);
        panel.add(ReadAccepted);

        // Label which appears when Data is written successfully
        Written.setBounds(650,160,150,25);
        Written.setForeground(Color.GREEN);
        Written.setVisible(false);
        panel.add(Written);


        JLabel CSVwritePath = new JLabel("Path :");
        CSVwritePath.setBounds(460, 200, 50, 25);
        panel.add(CSVwritePath);

        // editfields for Path of Writer / Reader
        this.SetPathReader.setBounds(530, 100, 400, 25);
        panel.add(this.SetPathReader);

        this.SetPathWriter.setBounds(530, 200, 400, 25);
        panel.add(this.SetPathWriter);

    }


    public void setToDoGUI(JPanel panel){
        // Label
        JLabel HeaderLabel = new JLabel("ToDo and Projects: ");
        HeaderLabel.setFont(customFont);
        HeaderLabel.setBounds(20, 235, 220, 30);
        panel.add(HeaderLabel);


        // input field to enter new Tasks
        JTextField taskInput = new JTextField(20);
        taskInput.setBounds(140,270,290,30);
        panel.add(taskInput);


        // create add button to add new tasks
        addButton.setBounds(30,270,100,30);
        panel.add(addButton);




        // Add Task Button Action Listener
        addButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                // if not an empty string, add to ToDoList
                String taskText = taskInput.getText();
                if (taskText != "" || taskText != " " || taskText != null) {
                    addToDo(currentList,taskText,false);
                }

                taskInput.setText("");
            }
        });



    }

    /** adds a String with a Task as text to the current ToDoList
     *
     * @param currentList List to which task will be added
     * @param taskText Task which will be added
     * @param crossed boolean which determines if task is crossed/checked/done
     */
    public static void addToDo( ToDoList currentList, String taskText,Boolean crossed){
        taskListPanel.removeAll(); // redraw ToDoList after it is updated

        if (!taskText.trim().isEmpty()) {
            currentList.setText(taskText,crossed); // add to current List

                for (int i = 0; i < currentList.Texts.length-1; i++) {

                    if(currentList.getText(i) != null && !Objects.equals(currentList.getText(i), "")) {
                        // redraw the List
                        addTaskItem(currentList.getText(i), taskListPanel);
                    }
                }
                }
        taskListPanel.repaint();
    }

    /** Updates the currentList after its values (may) have changed
     *
     * @param currentList which will be updated
     */
    public static void updateToDo( ToDoList currentList) {
        taskListPanel.removeAll();


        for (int i = 0; i < currentList.Texts.length - 1; i++) {

            if (currentList.getText(i) != null && !Objects.equals(currentList.getText(i), "")) {
                // add all items to the current List
                addTaskItem(currentList.getText(i), taskListPanel);
            }
        }


        taskListPanel.repaint();
    }

    /** This is the heart function of the ToDoList
     *  It adds a Task to the TaskList panel and displays it correctly (e.g. crossed or not)
     * @param taskText Task to add
     * @param taskListPanel Panel which the Task is added to
     */
    private static void addTaskItem(String taskText, JPanel taskListPanel) {
        JPanel taskItemPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Layout

        JCheckBox checkBox = new JCheckBox();
        JLabel taskLabel = new JLabel();

        taskLabel.setFont(new Font("Serif", Font.PLAIN, 14));



        if(currentList.StringBinaryMap.get(taskText)){ //Hashmap keeps track of crossed / not crossed Tasks
            taskLabel.setText("<html><strike>" + taskText + "</strike></html>"); // crossed
            checkBox.setSelected(true);
        }else {
            taskLabel.setText(taskText);
            checkBox.setSelected(false);
        }

        // If box next to Task is checked:
        checkBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkBox.isSelected()) {
                    // cross
                    taskLabel.setText("<html><strike>" + taskText + "</strike></html>");
                    currentList.StringBinaryMap.put(taskText,true); // add new boolean to hashmap
                    updateProgessbar();
                } else {
                    taskLabel.setText(taskText);
                    currentList.StringBinaryMap.put(taskText,false); // add boolean to hasmap
                    updateProgessbar();
                }
            }
        });

        // add and update everything

        taskItemPanel.add(checkBox);
        taskItemPanel.add(taskLabel);

        taskListPanel.add(taskItemPanel);
        taskListPanel.revalidate();
        taskListPanel.repaint();
        updateProgessbar();
    }

    // Create ProjectHGUI
    public void setProjectGUI(JPanel panel){

        // Labels
        JLabel ProjectDescript = new JLabel("Select existing Project:");
        ProjectDescript.setBounds(460,270,200,25);
        ProjectDescript.setFont(new Font("Dialog", Font.BOLD,14));
        panel.add(ProjectDescript);

        // initialize with empty project from which new projects can be created
        updateCombineProject( new Project(new ToDoList(1),"New Project"));

        JLabel TrackProg = new JLabel("Track Progress:");
        TrackProg.setBounds(460,310,140,25);
        panel.add(TrackProg);


        // Progressbar settings
        ProjProg.setBounds(620,310,200,25);

        ProjProg.setValue(0);
        ProjProg.setMaximum(100);
        ProjProg.setMinimum(0);
        ProjProg.setStringPainted(true);

        panel.add(ProjProg);





        // Labels
        JLabel newPrj =  new JLabel("Create new Project:");
        newPrj.setBounds(460,380,200,25);
        newPrj.setFont(new Font("Dialog", Font.BOLD,14));
        panel.add(newPrj);

        JLabel setName = new JLabel("Set Name :");
        setName.setBounds(460,420,75,25);
        panel.add(setName);

        // input for name of new project
        JTextField NameInput = new JTextField(20);
        NameInput.setBounds(550,420,160,25);
        panel.add(NameInput);


        // button which creates new Project
        JButton CreateProject = new JButton("Create from current ToDo");
        CreateProject.setBounds(460,450,250,25);
        panel.add(CreateProject);

        CreateProject.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String s = NameInput.getText();
                NameInput.setText("");
                Project newProj = new Project(currentList,s); // create new Project Object
              Main.addProj(newProj ); // add to global Project list
              updateCombineProject(newProj); // add to CombineBox where user can choose project
              cb.setSelectedItem(newProj.name);
                updateProgessbar();
//
            }
        });
        panel.add(CreateProject);

        // clear current ToDoList
        JButton ClearToDo = new JButton("Clear current ToDoList");
        ClearToDo.setBounds(460,500,250,25);
        panel.add(ClearToDo);

        ClearToDo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                ToDoList.deleteList(currentList); // delete current list
                Project p =    ProjectMap.get(cb.getSelectedItem()); // get selected project
                p.emptyList(); // delete its list
                updateProgessbar();

            }
        });
        panel.add(ClearToDo);



    }

    /** Updates the CombineBox for Projects with a new Project
     *
     * @param P Project to be added
     */
    public void updateCombineProject( Project P){
      choices[projectindex++] = P.name; // add to choices for combinebox
      cb.addItem(P.name);
      ProjectMap.put(P.name,P); // add to hashmap for easy and quick access to project via name


        // if new project is selected
        cb.addActionListener (new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                if(cb.getSelectedItem() != null) {
                    if(cb.getSelectedItem() == "New Project"){ // if our empty Project is selected, create empty list
                        currentList =new ToDoList(8);

                        updateToDo(currentList);
                        updateProgessbar();
                        return;

                    }
                    // else display selected Project
                    String selectedValue = cb.getSelectedItem().toString(); // get selected Project String


                    Project current = ProjectMap.get(selectedValue);// get via Hashmap
                    currentList = current.Tasks; // update the List displayed
                    updateToDo(current.Tasks);
                    updateProgessbar();
                }

            }
        });


    }

    public void setButtons(JPanel panel){
        // button which saves Userinput to intern data structures
        JButton AddButton = new JButton("Save Data");
        AddButton.setBounds(30, 120, 150, 25);
        panel.add(AddButton);

        AddButton.addActionListener(new ActionListener() {

        public void actionPerformed(ActionEvent e) {
            // Many problems could occur by user input, we check for all of them
            try {
                String dateString = DateText.getText(); // get user inputs
                String hours = HourText.getText();

                // validate data
                if (dateString.isEmpty() || hours.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Bitte alle Felder ausfüllen.");
                    return;
                }

                // convert hours from string to integer
                int hoursWorked = Integer.parseInt(hours);

                // get the right date format
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

                try {
                    // convert Date format for intern representation
                    LocalDate date = LocalDate.parse(dateString, formatter);
                    WorkDay day = new WorkDay(date, hoursWorked);
                    Main.addDayToArr(day); // add to intern data structures

                    // show message that everything worked
                    JOptionPane.showMessageDialog(null, "Daten eingetragen:\nDatum: " + day.getDate() + "\nStunden: " + day.getHours());

                } catch (Exception err) {
                    // show error message
                    JOptionPane.showMessageDialog(null, "Fehlerhaftes Datum. \n Das Format lautet : dd.MM.yyyy");
                }

            // catch errors and display message
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Ungültige Stundenangabe. Bitte eine Zahl eingeben.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Fehler: " + ex.getMessage());
            }

        }}        );







        // button to read csv data from entered path

        JButton CSVreadButton = new JButton("Reading CSV Data");
        CSVreadButton.setBounds(460,60,150,25);
        panel.add(CSVreadButton);



        CSVreadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                ReadAccepted.setVisible(false); // the text which indicates if reading was successfull is not visible at first
                String s = SetPathReader.getText(); // get Path from user input
                s = s.replace("\"", "" );           // this handles the unwanted quotes in the path

                CSVReader.SetPath( s);
               try { // try to read
                    CSVReader.ReadCSV(s);
                } catch (IOException ex) {
                   // throw error if not working
                    throw new RuntimeException(ex);
                }

                ReadAccepted.setVisible(true); // if reading was successfully, display label


            }
        });
        panel.add(CSVreadButton);







        // Button which initializes writing int user entered path
        JButton CSVwriteButton = new JButton("Writing CSV Data");
        CSVwriteButton.setBounds(460,160,150,25);
        panel.add(CSVwriteButton);




        CSVwriteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Written.setVisible(false);// label displays if writing worked
                String s = SetPathWriter.getText(); // get Text
                s = s.replace("\"", "" ); // get rid of unwanted chars

                CSVWriter.SetPath(s); // set path to user input

                try {   // try to write from intern data into path entered by user
                    CSVWriter.WriterCSV(Main.year);
                } catch (IOException ex) {
                    // throw error
                    throw new RuntimeException(ex);
                }
                //if worked, display so
                Written.setVisible(true);

            }
        });
        panel.add(CSVwriteButton);


    }


    /** Updates the Progressbar regarding currently selected Project
     *
     */
    public static void updateProgessbar(){
     Project curProj = ProjectMap.get(cb.getSelectedItem()); // get current project via hashmap

        int count = 0; // count all tasks and count done/checked/crosse projects
         int size = curProj.Tasks.StringBinaryMap.values().size();
        // Iteriere durch die Werte der HashMap und zähle die 'true'-Werte
        for (Boolean value : curProj.Tasks.StringBinaryMap.values()) {
            if (value) {
                count++;
            }
        }
        int progress = 0;
        try {
             progress = (int) ( 100* (count/(double)size)) ; // convert into percentage for Progressbar

        }catch (ArithmeticException e) {
        // do nothing
        }

                ProjProg.setValue(progress);// set value




    }


}




