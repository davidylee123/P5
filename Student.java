/**
 * HW5_dyl30_Lee
 * This class represents a student with a name and CaseCash balance in the CaseCashSystem.
 */
public class Student {
    private final String name;
    private int balance;

    /**
     * Constructor to initialize the student's name and balance.
     *
     * @param name    The name of the student.
     * @param balance The initial balance of the student.
     */
    public Student(String name, int balance) {
        this.name = name;
        this.balance = balance;
    }

    /**
     * Returns the name of the student.
     *
     * @return The name of the student.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the current balance of the student.
     *
     * @return The current balance.
     */
    public int getBalance() {
        return this.balance;
    }

    /**
     * Updates the balance of the student.
     *
     * @param newAmount The new balance amount.
     */
    public void updateBalance(int newAmount) {
        this.balance = newAmount;
    }
}


