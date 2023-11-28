import java.util.ArrayList;
import java.util.List;

/**
 * HW5_dyl30_Lee
 * This class represents the system for managing CaseCash among students.
 */
public class CaseCashSystem {
    private final List<Student> students;

    /**
     * Constructs a new CaseCashSystem with an empty list of students.
     */
    public CaseCashSystem() {
        students = new ArrayList<>();
    }

    /**
     * Runs a simulation of the CaseCashSystem based on a list of commands.
     * Commands include initializing accounts, querying balances, depositing, transferring,
     * withdrawing amounts, and sorting students by name or balance.
     *
     * @param commands The list of commands to be executed.
     * @return A list of results from executing the commands.
     */
    public List<String> runSimulation(List<String> commands) {
        // Clear the existing list of students to start the simulation from a clean state
        students.clear();

        List<String> outputs = new ArrayList<>();

        // Iterate through each command in the provided list
        for (String command : commands) {
            // Split the command into parts to analyze its components
            String[] parts = command.split(", ");

            // Use a switch statement to handle different types of commands
            switch (parts[0]) {
                case "INIT" -> outputs.add(String.valueOf(init(parts[1], Integer.parseInt(parts[2]))));
                case "GET" -> outputs.add(String.valueOf(getBalance(parts[1])));
                case "DEPOSIT" -> outputs.add(String.valueOf(deposit(findStudent(parts[1]), Integer.parseInt(parts[2]))));
                case "TRANSFER" -> outputs.add(String.valueOf(transfer(findStudent(parts[1]), findStudent(parts[2]), Integer.parseInt(parts[3]))));
                case "WITHDRAWAL" -> outputs.add(String.valueOf(withdrawal(findStudent(parts[1]), Integer.parseInt(parts[2]))));
                case "SORT" -> {
                    if ("name".equals(parts[1])) {
                        outputs.add(sortName().toString());
                    } else if ("balance".equals(parts[1])) {
                        outputs.add(sortBalance().toString());
                    }
                }
            }
        }

        return outputs;
    }


    /**
     * Helper method to find a student by name.
     *
     * @param name The name of the student to find.
     * @return The Student object if found, null otherwise.
     */
    private Student findStudent(String name) {
        // If the input name matches the student name in the list, return the name of the student
        for (Student student : students) {
            if (student.getName().equals(name)) {
                return student;
            }
        }
        return null;
    }

    /**
     * Initializes a student account.
     *
     * @param name           The name of the student.
     * @param initialBalance The initial balance of the student.
     * @return true if the student is successfully added, false otherwise.
     */
    public boolean init(String name, int initialBalance) {
        // If the initial balance is less than 0 or the name of the student is not found in the list, return false
        if (initialBalance < 0 || findStudent(name) != null) {
            return false;
        }
        students.add(new Student(name, initialBalance));
        return true;
    }

    /**
     * Returns the balance of a student.
     *
     * @param name The name of the student.
     * @return The balance of the student, or -1 if the student does not exist.
     */
    public int getBalance(String name) {
        Student student = findStudent(name);
        return student != null ? student.getBalance() : -1;
    }

    /**
     * Deposits money into a student's account.
     *
     * @param student The student object.
     * @param amount  The amount to deposit.
     * @return true if the deposit is successful, false otherwise.
     */
    public boolean deposit(Student student, int amount) {
        // If the student is not in the list or the deposit amount is less than 0, return false
        if (student == null || amount < 0) {
            return false;
        }
        student.updateBalance(student.getBalance() + amount);
        return true;
    }

    /**
     * Transfers money between two students.
     *
     * @param studentA The sender.
     * @param studentB The receiver.
     * @param amount   The amount to transfer.
     * @return true if the transfer is successful, false otherwise.
     */
    public boolean transfer(Student studentA, Student studentB, int amount) {
        // If the sender or receiver is not in the list, or the transfer amount is less than 0 or
        // the CaseCash amount of the sender is less than the transfer amount, return false
        if (studentA == null || studentB == null || amount < 0 || studentA.getBalance() < amount) {
            return false;
        }
        studentA.updateBalance(studentA.getBalance() - amount);
        studentB.updateBalance(studentB.getBalance() + amount);
        return true;
    }

    /**
     * Withdraws money from a student's account.
     *
     * @param student The student object.
     * @param amount  The amount to withdraw.
     * @return true if the withdrawal is successful, false otherwise.
     */
    public boolean withdrawal(Student student, int amount) {
        // If the student is not in the list or the withdrawal amount is less than 0 or
        // the balance of the student is less than the withdrawal amount, return false
        if (student == null || amount < 0 || student.getBalance() < amount) {
            return false;
        }
        student.updateBalance(student.getBalance() - amount);
        return true;
    }

    /**
     * Sorts the names of students in the list.
     *
     * @return A list of student names, sorted alphabetically.
     */
    public List<String> sortName() {
        List<Student> sortedStudents = new ArrayList<>(students);

        mergeSortByName(sortedStudents, 0, sortedStudents.size() - 1);

        // Extract and collect the sorted names from the sorted student list
        List<String> sortedNames = new ArrayList<>();
        for (Student student : sortedStudents) {
            sortedNames.add(student.getName());
        }
        return sortedNames;
    }

    /**
     * Applies the merge sort algorithm to a list of students, based on their names.
     *
     * @param list The list of students to be sorted.
     * @param left The starting index of the list segment to be sorted.
     * @param right The ending index of the list segment to be sorted.
     */
    private void mergeSortByName(List<Student> list, int left, int right) {
        // Only proceed if there are at least two elements to sort
        if (left < right) {
            int middle = (left + right) / 2;

            // Recursively sort the first and second halves
            mergeSortByName(list, left, middle);
            mergeSortByName(list, middle + 1, right);

            merge(list, left, middle, right);
        }
    }

    /**
     * Merges two sorted sublists into a single sorted list.
     *
     * @param list The list containing the sublists to be merged.
     * @param left The starting index of the first sublist.
     * @param middle The ending index of the first sublist and one less than the starting index of the second sublist.
     * @param right The ending index of the second sublist.
     */
    private void merge(List<Student> list, int left, int middle, int right) {
        // Temporary list to store the merged result
        List<Student> merged = new ArrayList<>();

        int i = left, j = middle + 1;

        // Merge the two halves into the merged list
        while (i <= middle && j <= right) {
            if (list.get(i).getName().compareTo(list.get(j).getName()) <= 0) {
                merged.add(list.get(i++));
            } else {
                merged.add(list.get(j++));
            }
        }

        // Add any remaining elements from the first half
        while (i <= middle) {
            merged.add(list.get(i++));
        }

        // Add any remaining elements from the second half
        while (j <= right) {
            merged.add(list.get(j++));
        }

        // Copy the merged elements back into the original list
        for (i = left; i <= right; i++) {
            list.set(i, merged.get(i - left));
        }
    }


    /**
     * Sorts students by their balance using quick sort and returns their names in sorted order.
     *
     * @return A list of student names sorted by their balance.
     */
    public List<String> sortBalance() {
        List<Student> sortedStudents = new ArrayList<>(students);

        // Apply quick sort to the copied list
        quickSortByBalance(sortedStudents, 0, sortedStudents.size() - 1);

        // Extract the names from the sorted list of students
        List<String> sortedNames = new ArrayList<>();
        for (Student student : sortedStudents) {
            sortedNames.add(student.getName());
        }
        return sortedNames;
    }

    /**
     * Applies the quick sort algorithm to a list of students, based on their balance.
     *
     * @param list The list of students to be sorted.
     * @param low The starting index for the sorting process.
     * @param high The ending index for the sorting process.
     */
    private void quickSortByBalance(List<Student> list, int low, int high) {
        // Check if there are elements to be sorted
        if (low < high) {
            // Partition the list and get the partition index
            int p = partition(list, low, high);

            // Recursively apply quick sort to the two partitions
            quickSortByBalance(list, low, p - 1);
            quickSortByBalance(list, p + 1, high);
        }
    }

    /**
     * Partitions the list around a pivot element and rearranges it so that all elements
     * with values less than the pivot come before the pivot, while all elements with
     * values greater than the pivot come after it.
     *
     * @param list The list of students to be partitioned.
     * @param low The starting index of the partitioning process.
     * @param high The ending index of the partitioning process.
     * @return The partition index after rearrangement.
     */
    private int partition(List<Student> list, int low, int high) {
        // Choose the last element as pivot
        int pivot = list.get(high).getBalance();

        // Initialize the index of the smaller element
        int i = (low - 1);

        // Traverse through all elements
        for (int j = low; j < high; j++) {
            // If current element is smaller than the pivot, swap it with the element at index i
            if (list.get(j).getBalance() < pivot) {
                i++;
                Student temp = list.get(i);
                list.set(i, list.get(j));
                list.set(j, temp);
            }
        }

        // Swap the pivot element with the element at index i+1
        Student temp = list.get(i + 1);
        list.set(i + 1, list.get(high));
        list.set(high, temp);

        return i + 1;
    }
}


