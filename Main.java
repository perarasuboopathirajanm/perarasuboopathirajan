package atmproject;
import java.util.Scanner;
import java.util.InputMismatchException;


public class Main {

    @SuppressWarnings("resource")
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ATMDAO dao = new ATMDAO();

        try {
            System.out.println("===== ATM MACHINE =====");
            System.out.print("Enter PIN: ");
            int pin = sc.nextInt();

            ATM acc = dao.getAccount(pin);
            if (acc == null) {
                System.out.println("Invalid PIN!");
                return;
            }

            System.out.println("\nWelcome, " + acc.getName());

            boolean exit = false;
            while (!exit) {
                System.out.println("\n===== MENU =====");
                System.out.println("1. Check Balance");
                System.out.println("2. Withdraw");
                System.out.println("3. Change PIN");
                System.out.println("4. Exit");
                System.out.print("Choose an option: ");

                int choice;
                try {
                    choice = sc.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input! Please enter a number.");
                    sc.next(); // clear invalid input
                    continue;
                }

                switch (choice) {
                    case 1:
                        System.out.println("Current Balance: " + acc.getBalance());
                        break;

                    case 2:
                        System.out.print("Enter amount to withdraw: ");
                        double amount;
                        try {
                            amount = sc.nextDouble();
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input! Please enter a number.");
                            sc.next();
                            break;
                        }
                        if (amount > acc.getBalance()) {
                            System.out.println("Insufficient Balance!");
                        } else {
                            double newBalance = acc.getBalance() - amount;
                            if (dao.updateBalance(pin, newBalance)) {
                                acc.setBalance(newBalance);
                                System.out.println("Withdrawal Successful! New Balance: " + newBalance);
                            } else {
                                System.out.println("Error updating balance.");
                            }
                        }
                        break;

                    case 3:
                        System.out.print("Enter new PIN: ");
                        int newPin;
                        try {
                            newPin = sc.nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input! Please enter numbers only.");
                            sc.next();
                            break;
                        }
                        if (dao.changePin(pin, newPin)) {
                            pin = newPin; // update session PIN
                            acc = dao.getAccount(pin);
                            System.out.println("PIN changed successfully!");
                        } else {
                            System.out.println("Failed to change PIN.");
                        }
                        break;

                    case 4:
                        exit = true;
                        System.out.println("Thank you for using ATM!");
                        break;

                    default:
                        System.out.println("Invalid choice! Try again.");
                }
            }

        } catch (InputMismatchException e) {
            System.out.println("Invalid input! Program terminated.");
        }
    }
}