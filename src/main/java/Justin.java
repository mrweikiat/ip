import java.util.*;

public class Justin {
    public static void main(String[] args) {
        
        /*
         * This is feature 1
         * Justin is a chatbot that help users plan and organise tasks
         * Justin stands for JUSt a TImetable(New) : JUSTIN
         *
         * Justin is able to create and manage new tasks
         *   1) Mark tasks off as done
         *   2) Set tasks as To Do's - <keyword> <name>
         *   3) Set tasks as Deadline's - keyword  : <keyword> <name> /by <day>
         *   4) Set tasks as Event's - keyword : <keyword> <name> /at <day time>
         *   5) Supports deletion of completed tasks with command delete <int>
         *
         * Justin also supports viewing the entire tasks list with the command list - keyword : <keyword>
         * To end off the session user can input bye to terminate program - keyword : <keyword>
         *
         * @author Goh Wei Kiat aka github : mrweikiat
         * @version CS2103T AY20/21 Semester 2, Individual Project 'IP'
         */

        String logoNew = "     ,--.                    ,--.   ,--.          \n" +
                "     |  | ,--.,--.  ,---.  ,-'  '-. `--' ,--,--,  \n" +
                ",--. |  | |  ||  | (  .-'  '-.  .-' ,--. |      \\ \n" +
                "|  '-'  / '  ''  ' .-'  `)   |  |   |  | |  ||  |  \n" +
                " `-----'   `----'  `----'    `--'   `--' `--''--'  \n";


        Scanner sc = new Scanner(System.in);

        // Starting line for UI
        System.out.println(logoNew);
        printLineBreaker();
        System.out.println("Hello I'm Justin");
        System.out.println("What can I do for you?");
        printLineBreaker();

        // Condition for Duke to stop
        boolean terminate = false;

        // create LinkedList to store information of user inputs
        ArrayList<Task> tasks = new ArrayList<>();

        //Duke will keep repeating until command given "Bye"
        while (!terminate) {

            String text = sc.nextLine();

            try {

                validate(text);

                if (text.equals("bye")) {

                    printLineBreaker();
                    System.out.println("Bye. Hope to see you again soon!");
                    printLineBreaker();
                    terminate = true; // terminates Duke

                } else if (text.equals("list")) {

                    // print all the tasks stored currently in the list
                    printLineBreaker();
                    System.out.println("Here are the tasks in your list:");
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println(i+1 + ". " + tasks.get(i).toString());
                    }
                    printLineBreaker();

                } else if (text.contains("done")) {

                    String num = text.substring(5); // take out the int value of the task to be completed
                    int listNum = Integer.parseInt(num); // changes to int
                    Task hold = tasks.get(listNum-1);
                    hold.markAsDone();

                    // format
                    printLineBreaker();
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println(tasks.get(listNum-1).toString());
                    printLineBreaker();

                } else if (text.contains("deadline")) {

                    // insert code for deadline
                    String newText = text.substring(9); // remove deadline from the string text
                    //System.out.println(newText); // for debugging

                    // set delimiter to take out the description of the deadline
                    String description = newText.substring(0, newText.indexOf("/")-1);
                    //System.out.println(description); // for debugging

                    // set delimiter to take out date of the deadline
                    String date = newText.substring(newText.indexOf("/")+4);
                    //System.out.println(date); // for debugging

                    printLineBreaker();
                    System.out.println("Got it. I've added this task:");

                    for (int i = 0; i < tasks.size(); i++) {
                        if (tasks.get(i).description.equals(description)) { // meaning this is the task we want to change
                            Deadline dl = new Deadline(description, date);
                            if (tasks.get(i).isDone) {
                                dl.markAsDone();
                            }

                            tasks.set(i, dl);// insert into the list

                            System.out.println(" " + dl.toString());
                            System.out.println("Now you have " + tasks.size() + " tasks in the list");
                            printLineBreaker();
                        }
                    }
                } else if (text.contains("todo")) {
                    String description = text.substring(text.indexOf(" ")+1); // take out the item from the text
                    //System.out.println(description); // for debugging

                    for (int i = 0; i < tasks.size(); i++) { // there is an instance of the item in list
                        if (tasks.get(i).description.equals(description)) {
                            Todo td = new Todo(description);
                            // bringing over information from superclass
                            if (tasks.get(i).isDone) {
                                td.markAsDone();
                            }
                            tasks.set(i, td); // insert into list
                            // formatting
                            printLineBreaker();
                            System.out.println("Got it. I've added this task:");
                            System.out.println(" " + td.toString());
                            System.out.println("Now you have " + tasks.size() + " tasks in the list");
                            printLineBreaker();
                        }
                    }
                } else if (text.contains("event")) {

                    String newText = text.substring(text.indexOf(" ")+1); // removing the event to get description
                    System.out.println(newText); // for debugging

                    // set delimiter to obtain the description and the at
                    String description = newText.substring(0, newText.indexOf("/")-1);
                    String date = newText.substring(newText.indexOf("/")+4);

                    //System.out.println(description + " " + date); // for debugging

                    for (int i = 0; i < tasks.size(); i++) {
                        if(tasks.get(i).description.equals(description)) {
                            Event e = new Event(description, date);
                            // bringing over info from superclass
                            if (tasks.get(i).isDone) {
                                e.markAsDone();
                            }

                            tasks.set(i, e); // insert into list

                            //formatting
                            printLineBreaker();
                            System.out.println("Got it. I've added this task:");
                            System.out.println(" " + e.toString());
                            System.out.println("Now you have " + tasks.size() + " tasks in the list");
                            printLineBreaker();
                        }
                    }
                }
                else if (text.contains("delete")) {
                    String num = text.substring(7); // take out the int value of the task to be completed
                    int listNum = Integer.parseInt(num); // changes to int
                    System.out.println(listNum);

                    Task newTask = tasks.remove(listNum-1); // delete the entry of choice

                    //format
                    printLineBreaker();
                    System.out.println("Noted. I've removed this task:");
                    System.out.println(" " + newTask.toString());
                    System.out.println("Now you have " + tasks.size() + " tasks in the list");
                    printLineBreaker();

                }
                // adding of tasks
                else {
                    printLineBreaker();
                    System.out.println("added: " + text);
                    printLineBreaker();
                    // create new instance of task and add to the list
                    Task holder = new Task(text);
                    tasks.add(holder); // position corresponds to item number
                }
            } catch (Exception m) {
                printLineBreaker();
                System.out.println(m.getMessage());
                printLineBreaker();
            }
        }

        sc.close();

    }

    //  ***** level 5 *****
    static void validate(String text) throws JustinException {
        if (text.length() < 5 && text.contains("todo") ) { // case 1
            throw new JustinException("☹ OOPS!!! The description of a todo cannot be empty.");
        }
        else if (text.contains("blah")) { // case 2
            throw new JustinException("☹ OOPS!!! I'm sorry, but I don't know what that means :-(");
        } else if (text.length() < 10 && text.contains("deadline")) { // case 3
            throw new JustinException("☹ OOPS!!! The description of a deadline cannot be empty.");
        } else if (text.length() < 6 && text.contains("event")) { // case 4
            throw new JustinException("☹ OOPS!!! The description of a event cannot be empty.");
        }
    }


    public static void printLineBreaker() {

        for (int i = 0; i < 50; i++) {
            System.out.print("-");
        }
        System.out.println();
    }
}