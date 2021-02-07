package justin;

import java.io.File;
import java.util.Scanner;


/**
 * justin.Justin is a chatbot that help users plan and organise tasks
 * justin.Justin stands for JUSt a TImetable(New) : JUSTIN
 *
 * justin.Justin is able to create and manage new tasks
 *   1) Mark tasks off as done
 *   2) Set tasks as To Do's - [keyword] [name]
 *   3) Set tasks as justin.Deadline's - keyword  : [keyword] [name] /by [day]
 *   4) Set tasks as justin.Event's - keyword : [keyword] [name] /at [day time]
 *   5) Supports deletion of completed tasks with command delete [int]
 *
 *
 * @author Goh Wei Kiat aka github : mrweikiat
 * @version CS2103T AY20/21 Semester 2, Individual Project 'IP'
 */

public class Justin {

    private Storage storage;
    private TaskList tasks;
    private Ui ui;


    /**
     * This is method creates a class Justin that will take in user inputs to execute commands
     *
     * @param filePath This is the path of the file to be read and created
     */

    public Justin(String filePath) {

        ui = new Ui();
        this.storage = new Storage(filePath);
        tasks = storage.loadFile(filePath);

    }


    /**
     * This is the run method that will generate the UI messages at the start
     * user will give inputs and Justin will execute the commands given
     *
     */

    public void run() {

        Scanner sc = new Scanner(System.in);

        // Starting line for UI
        ui.showWelcomeMessage();
        ui.showHelpMessage();

        // Condition for Duke to stop
        boolean terminate = false;

        //Duke will keep repeating until command given "Bye"
        while (!terminate) {

            String text = sc.nextLine();

            Parser pr = new Parser(text);

            try {
                String command = pr.checkCommand();

                switch (command) {

                    case "BYE":

                        storage.saveFile(tasks, storage.getFilePath());
                        terminate = true; // terminates Duke
                        ui.showEndMessage();
                        break;

                    case "LIST":

                        ui.showListMessage();
                        ui.printList(tasks);
                        break;

                    case "DONE":

                        String num = text.substring(5); // take out the int value of the task to be completed
                        int listNum = Integer.parseInt(num); // changes to int
                        Task hold = tasks.getList().get(listNum - 1);
                        hold.markAsDone();
                        ui.showDoneMessage(tasks, listNum);
                        break;

                    case "DEADLINE":

                        String newText = text.substring(9); // remove deadline from the string text
                        // set delimiter to take out the description of the deadline
                        String description = newText.substring(0, newText.indexOf("/") - 1);
                        // set delimiter to take out date of the deadline
                        String date = newText.substring(newText.indexOf("/") + 4);

                        ui.printLine();
                        tasks.addDeadline(description, date);
                        ui.printLine();

                        break;

                    case "TODO":

                        String descriptionToDo = text.substring(text.indexOf(" ") + 1); // take out the item from the text
                        tasks.addToDo(descriptionToDo);

                        break;

                    case "EVENT":

                        String eventText = text.substring(text.indexOf(" ") + 1); // removing the event to get description
                        // set delimiter to obtain the description and the at
                        String descriptionEvent = eventText.substring(0, eventText.indexOf("/") - 1);
                        String dateEvent = eventText.substring(eventText.indexOf("/") + 4);
                        // splitting the date and time respectively
                        tasks.addEvent(descriptionEvent, dateEvent);

                        break;

                    case "DELETE":

                        String numDelete = text.substring(7); // take out the int value of the task to be completed
                        tasks.delete(numDelete);

                        break;

                    case "FIND": // for level 9

                        String findText = text.substring(text.indexOf(" ")+1); // key for searching
                        ui.printFoundTask(tasks.find(findText));

                        break;

                    default:

                        ui.printLine();
                        System.out.println("added: " + text);
                        ui.printLine();
                        // create new instance of task and add to the list
                        Task holder = new Task(text);
                        tasks.getList().add(holder); // position corresponds to item number

                }
            } catch (JustinException m) {
                ui.printErrorMessage(m.getMessage());
            }


        }
        sc.close();
    }


    /**
     * Main method inputs the file path to justin.txt to retrieve any list data stored within
     *
     * @param args unused
     */

    public static void main(String[]args){

            String userDir = System.getProperty("user.dir");
            String filePath = userDir + File.separator + "data" + File.separator + "justin.txt";
            new Justin(filePath).run();

        }
}


