import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Theatre {
    //this scanner object is used to get all over the program
    static final Scanner input = new Scanner(System.in);

    //These 3 arrays are used to keep record of the seats
    final int[] row1 = new int[12];
    final int[] row2 = new int[16];
    final int[] row3 = new int[20];
    final int[][] rows = {row1, row2, row3};
    //storing user's selection
    int rowChoice;
    int seatChoice;
    static boolean quit = false;

    ArrayList<Ticket> tickets = new ArrayList<>();
    static Theatre theatre = new Theatre();

    //This is the main method of a program that provides a menu of options to the user for managing a theatre's ticket booking system. It uses a while loop to continuously display the menu until the user chooses to quit the program.
    public static void main(String[] args) {
        System.out.println("-".repeat(49));
        System.out.println("Welcome to the New Theatre");

        //main while loop
        while (!quit) {
            //task 2(menu)
            System.out.println("-".repeat(49));
            System.out.println("Please select an option:");
            System.out.println("1) Buy a ticket");
            System.out.println("2) Print seating area");
            System.out.println("3) Cancel ticket");
            System.out.println("4) List available seats");
            System.out.println("5) Save to file");
            System.out.println("6) Load from file");
            System.out.println("7) Print ticket information and total price");
            System.out.println("8) Sort tickets by price");
            System.out.println("0) Quit");
            System.out.println("-".repeat(49));
            try {
                System.out.print("Enter option: ");
                int menu_choice = input.nextInt();
                switch (menu_choice) {
                    case 0 -> theatre.quit();
                    case 1 -> theatre.buy_ticket();
                    case 2 -> theatre.print_seating_area();
                    case 3 -> theatre.cancel_ticket();
                    case 4 -> theatre.show_available();
                    case 5 -> theatre.save();
                    case 6 -> theatre.load();
                    case 7 -> theatre.show_tickets_info();
                    case 8 -> theatre.sort_tickets();
                    default -> System.out.println("Invalid Input !");
                }
            }catch (InputMismatchException e) {
                System.out.println("Invalid Input !");
                input.next();
            }
        }
    }

    //This is a method that allows a user to buy a ticket by selecting a valid row and seat number, inputting personal details, and creating a ticket object with the corresponding information.
    void buy_ticket() {
        get_valid_rowNo_and_seatNo();

        double price = 0;

        if (rows[rowChoice - 1][seatChoice - 1] == 0) {
            rows[rowChoice - 1][seatChoice - 1] = 1;

            if (rowChoice == 1) {
                price = 10;
            } else if (rowChoice == 2) {
                price = 20;
            } else if (rowChoice == 3) {
                price = 30;
            }

            System.out.print("Enter your name: ");
            input.nextLine();
            String name = input.nextLine();
            System.out.print("Enter your surname: ");
            String surname = input.nextLine();
            System.out.print("Enter your email: ");
            String email = input.nextLine();

            Person person = new Person(name, surname, email);
            Ticket ticket = new Ticket(rowChoice, seatChoice, price, person);
            tickets.add(ticket);
            System.out.println("-".repeat(49)+"\n"+"Your booking is successful !"+"\n"+"-".repeat(49));

        } else {
            System.out.println("Sorry this seat NOT Available");
        }
    }

    // This helper method prompts the user to enter a valid row number and seat number for purchasing or cancelling a ticket
    void get_valid_rowNo_and_seatNo() {
        boolean valid = false;
        do {
            try {
                System.out.print("Enter Row Number : ");
                rowChoice = input.nextInt();
                if (rowChoice < 4 && rowChoice > 0) {
                    do {
                        System.out.print("Enter Seat Number : ");
                        seatChoice = input.nextInt();
                        if((seatChoice < 1 || seatChoice > rows[rowChoice-1].length)) {
                            System.out.println("Invalid Seat Number! Please try again !");
                        } else {
                            valid = true;
                        }
                    } while (!valid);
                } else {
                    System.out.println("Invalid Row Number! Please try again !");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input!");
                input.next();
            }
        } while (!valid);
    }

    //This method prints a visual representation of the seating area
    void print_seating_area() {
        //stage printing
        System.out.println("     ***********\n     *  STAGE  *\n     ***********");
        int spacing = 4;
        for (int[] row:rows) {
            System.out.print(" ".repeat(spacing));
            for (int i = 0; i < row.length; i++) {
                if (i == row.length/2) {
                    System.out.print(" ");
                }
                if (row[i] == 0) {
                    System.out.print("O");
                } else if (row[i] == 1) {
                    System.out.print("X");
                }
            }
            spacing -= 2;
            System.out.println();
        }
    }

    //This method cancels a ticket by removing it from both the rows array and the tickets ArrayList. It first calls a helper method get_valid_rowNo_and_seatNo() to get valid the user's input for the row and seat numbers of the ticket to cancel.
    void cancel_ticket() {
        get_valid_rowNo_and_seatNo();
        if (rows[rowChoice-1][seatChoice-1] == 1) {
            rows[rowChoice-1][seatChoice-1] = 0;

            //this loop used to remove object of cancelled ticket in tickets Array
            for (Ticket ticket:tickets){
                if (ticket.getRow() == rowChoice && ticket.getSeat() == seatChoice) {
                    tickets.remove(ticket);
                    break;
                }
            }
            System.out.println("-".repeat(49));
            System.out.println("Your cancellation is successful !");
        } else {
            System.out.println("-".repeat(49));
            System.out.println("There is no booking for the data you entered !");
        }
    }

    //This method used print all the seat numbers which are available in each row
    void show_available () {
        System.out.println("-".repeat(100));
        availableSeats(12,row1,1);
        availableSeats(16,row2,2);
        availableSeats(20,row3,3);
        System.out.println("-".repeat(100));
    }

    //This method is a helper method of show available method
    void availableSeats(int seats,int[] row, int rowNo){
        System.out.print("Seats available in row "+rowNo+" : ");
        for (int i = 0; i < seats; i++) {
            if (row[i] == 0) {
                if (i == seats-1) {
                    System.out.print((i+1)+".");
                } else {
                    System.out.print((i+1)+", ");
                }
            }
        }
        System.out.println();
    }

    //This method writes data from a 2D array of integers called rows to a text file named "Data.txt". The method uses a nested loop to iterate through each row and column of the array and write the corresponding data to the file. It uses the FileWriter class to write the data to the file, and it writes each value on a separate line using System.lineSeparator().
    void save() {
        try {
            FileWriter dataSave = new FileWriter("Data.txt");
            for (int[] row : rows) {
                for (int data : row) {
                    dataSave.write(Integer.toString(data));
                    dataSave.write(System.lineSeparator());
                }
            }
            dataSave.close();
            System.out.println("-".repeat(49));
            System.out.println("All data saved successfully!");
        } catch (IOException e){
            System.out.println("Error");
        }
    }

    //This method reads data from a text file named "Data.txt" and loads it into a 2D array of integers called rows. The method uses a nested loop to iterate through each row and column of the array and read the corresponding data from the file. It converts the data from a string to an integer using Integer.parseInt() method and sets the corresponding value in the array.
    void load () {
        try {
            File readFile = new File("Data.txt");
            Scanner dataRead = new Scanner(readFile);
            for (int[] row:rows) {
                for (int i=0; i<row.length; i++) {
                    String data = dataRead.next();
                    row[i] = Integer.parseInt(data);
                }
            }
            dataRead.close();
            System.out.println("-".repeat(49));
            System.out.println("All data loaded successfully!");
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
        }
    }

    //This method iterates through an ArrayList of Ticket objects and prints their information using the print() method. It also calculates the total price of all the tickets and prints it at the end.
    void show_tickets_info(){
        double total = 0;
        for (Ticket ticket:tickets) {
            ticket.print();
            total += ticket.getPrice();
        }
        System.out.println("-".repeat(49)+"\n"+"Total is: $ "+total+"\n"+"-".repeat(49));
    }

    //This method sets a boolean variable quit to true, indicating the intention to break loop.
    void quit(){
        quit = true;
    }

    //This method used to sort an ArrayList of Ticket objects by price
    void sort_tickets() {
       ArrayList<Ticket> sortedTickets = (ArrayList<Ticket>) tickets.clone();

        int bottom = sortedTickets.size()-2;
        Ticket temp;
        boolean swapping = true;
        while (swapping) {
            swapping = false;
            for (int i = 0; i <= bottom; i++) {
                if (sortedTickets.get(i).getPrice() > sortedTickets.get(i+1).getPrice()) {
                    temp = sortedTickets.get(i);
                    sortedTickets.set(i, sortedTickets.get(i+1));
                    sortedTickets.set(i+1, temp);
                    swapping = true;
                }
            }
            bottom--;
        }

        for (Ticket ticket:sortedTickets) {
            ticket.print();
        }
    }
}