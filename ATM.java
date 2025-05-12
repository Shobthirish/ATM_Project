
// ATM.java
import java.util.*;

public class ATM {
    // Color codes
    static final String RESET = "\u001B[0m";
    static final String RED = "\u001B[31m";
    static final String GREEN = "\u001B[32m";
    static final String YELLOW = "\u001B[33m";
    static final String BLUE = "\u001B[34m";
    static final String CYAN = "\u001B[36m";

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        // Create multiple sample accounts
        Map<String, Account> accounts = new HashMap<>();
        accounts.put("12345678", new Account("12345678", "1234", 10000.0));
        accounts.put("87654321", new Account("87654321", "4321", 5000.0));

        System.out.println(CYAN + "==== Welcome to Java ATM ====" + RESET);

        Account user = login(accounts);
        if (user != null) {
            showMenu(user, accounts);
        } else {
            System.out.println(RED + "Too many failed attempts. Exiting..." + RESET);
        }
    }

    public static Account login(Map<String, Account> accounts) {
        int attempts = 0;
        while (attempts < 3) {
            System.out.print(BLUE + "Enter Account Number: " + RESET);
            String accNumber = sc.nextLine();

            System.out.print(BLUE + "Enter PIN: " + RESET);
            String pin = sc.nextLine();

            Account acc = accounts.get(accNumber);
            if (acc != null && acc.validatePin(pin)) {
                System.out.println(GREEN + "Login Successful!" + RESET);
                return acc;
            } else {
                attempts++;
                System.out.println(RED + "Invalid Account Number or PIN. Attempts left: " + (3 - attempts) + RESET);
            }
        }
        return null;
    }

    public static void showMenu(Account user, Map<String, Account> accounts) {
        int choice;
        do {
            System.out.println(YELLOW + "\n=== ATM Menu ===" + RESET);
            System.out.println("1. Balance Inquiry");
            System.out.println("2. Cash Deposit");
            System.out.println("3. Cash Withdrawal");
            System.out.println("4. PIN Change");
            System.out.println("5. Mini Statement");
            System.out.println("6. Fund Transfer");
            System.out.println("7. Exit");
            System.out.print(CYAN + "Choose an option: " + RESET);
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.println(GREEN + "Your Balance: ₹" + user.getBalance() + RESET);
                    break;
                case 2:
                    System.out.print("Enter amount to deposit: ₹");
                    double depositAmount = sc.nextDouble();
                    user.deposit(depositAmount);
                    System.out.println(GREEN + "₹" + depositAmount + " deposited successfully." + RESET);
                    break;
                case 3:
                    System.out.print("Enter amount to withdraw: ₹");
                    double withdrawAmount = sc.nextDouble();
                    if (user.withdraw(withdrawAmount)) {
                        System.out.println(GREEN + "₹" + withdrawAmount + " withdrawn successfully." + RESET);
                    } else {
                        System.out.println(RED + "Invalid amount or insufficient balance." + RESET);
                    }
                    break;
                case 4:
                    sc.nextLine(); // Consume newline
                    System.out.print("Enter new PIN: ");
                    String newPin = sc.nextLine();
                    user.changePin(newPin);
                    System.out.println(GREEN + "PIN changed successfully." + RESET);
                    break;
                case 5:
                    user.showMiniStatement();
                    break;
                case 6:
                    sc.nextLine(); // Consume newline
                    System.out.print("Enter recipient Account Number: ");
                    String receiverAcc = sc.nextLine();
                    Account receiver = accounts.get(receiverAcc);
                    if (receiver != null && receiver != user) {
                        System.out.print("Enter amount to transfer: ₹");
                        double transferAmt = sc.nextDouble();
                        if (transferAmt > 0 && transferAmt <= user.getBalance()) {
                            user.transfer(receiver, transferAmt);
                            System.out.println(GREEN + "₹" + transferAmt + " transferred successfully to Acc "
                                    + receiverAcc + RESET);
                        } else {
                            System.out.println(RED + "Invalid amount or insufficient balance." + RESET);
                        }
                    } else {
                        System.out.println(RED + "Invalid recipient account." + RESET);
                    }
                    break;
                case 7:
                    System.out.println(BLUE + "Thank you for using Java ATM. Goodbye!" + RESET);
                    break;
                default:
                    System.out.println(RED + "Invalid choice. Please try again." + RESET);
            }
        } while (choice != 7);
    }
}
