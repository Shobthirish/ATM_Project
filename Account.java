// Account.java
import java.util.ArrayList;
import java.util.List;

public class Account {
    private String accountNumber;
    private String pin;
    private double balance;
    private List<String> transactions;

    public Account(String accountNumber, String pin, double balance) {
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.balance = balance;
        this.transactions = new ArrayList<>();
        transactions.add("Account created with ₹" + balance);
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public boolean validatePin(String inputPin) {
        return pin.equals(inputPin);
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            transactions.add("Deposited ₹" + amount);
        }
    }

    public boolean withdraw(double amount) {
        if (amount > balance) {
            return false;
        } else if (amount <= 0) {
            return false;
        } else {
            balance -= amount;
            transactions.add("Withdrew ₹" + amount);
            return true;
        }
    }

    public void changePin(String newPin) {
        this.pin = newPin;
        transactions.add("PIN changed");
    }

    public void transfer(Account receiver, double amount) {
        this.balance -= amount;
        receiver.balance += amount;
        this.transactions.add("Transferred ₹" + amount + " to Acc " + receiver.accountNumber);
        receiver.transactions.add("Received ₹" + amount + " from Acc " + this.accountNumber);
    }

    public void showMiniStatement() {
        System.out.println("\u001B[36m--- Mini Statement ---\u001B[0m");
        for (String txn : transactions) {
            System.out.println(txn);
        }
    }
}
