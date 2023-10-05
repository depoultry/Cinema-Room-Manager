package cinema;

import java.util.Arrays;
import java.util.Scanner;

public class Cinema {

    static final int LOW_TICKET_PRICE = 8;
    static final int HIGH_TICKET_PRICE = 10;
    static public int ticketsPurchased = 0;
    static public int currentIncome = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of rows:");
        int rowsQ = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int seatsQ = scanner.nextInt();

        String[][] seatingChart = new String[rowsQ + 1][seatsQ + 1];
        buildSeating(seatingChart);

        int totalIncome = rowsQ % 2 == 0 ? (((rowsQ / 2) * seatsQ) * HIGH_TICKET_PRICE) + (((rowsQ / 2) * seatsQ) * LOW_TICKET_PRICE) :
                (((rowsQ / 2) * seatsQ) * HIGH_TICKET_PRICE) + (((rowsQ - (rowsQ / 2)) * seatsQ) * LOW_TICKET_PRICE);
        int totalSeats = rowsQ * seatsQ;

        while (true) {
            int menuChoice = cinemaMenu();
            if (menuChoice == 1) {
                System.out.println("\nCinema:");
                Arrays.stream(seatingChart).forEach(e ->
                        System.out.println(Arrays.toString(e)
                                .replace(",", "")
                                .replace("[", "")
                                .replace("]", "")
                                + " "));
            } else if (menuChoice == 2) {
                pickSeating(seatingChart, rowQuestion(), seatQuestion(), rowsQ, seatsQ);
            } else if (menuChoice == 3) {
                showStatistics(totalSeats, totalIncome);
            } else {
                break;
            }
        }
    }

    public static int cinemaMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("""
                                
                1. Show the seats
                2. Buy a ticket
                3. Statistics
                0. Exit""");

        return scanner.nextInt();
    }

    public static void buildSeating(String[][] seatingChart) {

        for (int i = 0; i < seatingChart.length; i++) {
            for (int j = 0; j < seatingChart[0].length; j++) {
                if (i == 0 && j == 0) {
                    seatingChart[i][j] = " ";
                } else if (i == 0) {
                    seatingChart[i][j] = String.valueOf(j);
                } else if (j == 0) {
                    seatingChart[i][j] = String.valueOf(i);
                } else {
                    seatingChart[i][j] = "S";
                }
            }
        }
    }

    public static int rowQuestion() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nEnter a row number:");
        return scanner.nextInt();
    }

    public static int seatQuestion() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nEnter a seat number in that row:");
        return scanner.nextInt();
    }

    public static void pickSeating(String[][] seatingChart, int rowPicked, int seatPicked, int rowsQ, int seatsQ) {

        if (rowPicked <= 0 || seatPicked <= 0 || rowPicked > rowsQ || seatPicked > seatsQ) {
            System.out.println("\nWrong input!\n");
            pickSeating(seatingChart, rowQuestion(), seatQuestion(), rowsQ, seatsQ);
        } else if (seatingChart[rowPicked][seatPicked].equals("B")) {
            System.out.println("\nThat ticket has already been purchased!\n");
            pickSeating(seatingChart, rowQuestion(), seatQuestion(), rowsQ, seatsQ);
        } else {
            int ticketPrice = rowPicked < (rowsQ / 2) + 1 ? HIGH_TICKET_PRICE : LOW_TICKET_PRICE;
            System.out.println("\nTicket price: $" + ticketPrice);
            seatingChart[rowPicked][seatPicked] = "B";
            ticketsPurchased++;
            currentIncome += ticketPrice;
        }
    }

    public static void showStatistics(double totalSeats, int totalIncome) {
        System.out.printf("""
                
                Number of purchased tickets: %d
                Percentage: %.2f%%
                Current income: $%d
                Total income: $%d
                """, ticketsPurchased, (ticketsPurchased / totalSeats) * 100, currentIncome, totalIncome);
    }
}