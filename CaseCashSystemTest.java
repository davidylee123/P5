import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * HW5_dyl30_Lee
 * This class provides a set of test cases for the CaseCashSystem class.
 */
public class CaseCashSystemTest {

    private CaseCashSystem system;

    /**
     * Sets up the testing environment before each test.
     * Initializes a new instance of CaseCashSystem.
     */
    @BeforeEach
    public void setup() {
        system = new CaseCashSystem();
    }

    /**
     * Tests the runSimulation method of CaseCashSystem.
     * This test simulates a sequence of operations including account initialization,
     * sorting by name and balance, and transferring funds.
     * It verifies that the method produces the expected results for each operation.
     */
    @Test
    public void testRunSimulation() {
        List<String> commands = Arrays.asList(
                "INIT, Tammy, 200",
                "INIT, Kim, 300",
                "INIT, Quyen, 400",
                "SORT, name",
                "SORT, balance",
                "TRANSFER, Kim, Tammy, 100",
                "SORT, name",
                "SORT, balance"
        );

        List<String> expectedOutputs = Arrays.asList(
                "true", "true", "true",
                "[Kim, Quyen, Tammy]",
                "[Tammy, Kim, Quyen]",
                "true",
                "[Kim, Quyen, Tammy]",
                "[Kim, Tammy, Quyen]"
        );

        List<String> actualOutputs = system.runSimulation(commands);
        assertEquals(expectedOutputs, actualOutputs, "The outputs of the simulation should match the expected results");
    }

    /**
     * Tests sorting of students by balance with multiple students.
     * Verifies that the sortBalance method correctly sorts students in ascending order of their balance.
     */
    @Test
    public void testSortBalanceWithMultipleStudents() {
        system.init("Alice", 300);
        system.init("Bob", 100);
        system.init("Charlie", 200);

        List<String> sorted = system.sortBalance();
        assertEquals(List.of("Bob", "Charlie", "Alice"), sorted, "Students should be sorted by balance in ascending order");
    }

    /**
     * Tests the sortBalance method with an empty list of students.
     * Verifies that the method returns an empty list when there are no students.
     */
    @Test
    public void testSortBalanceEmptyList() {
        assertTrue(system.sortBalance().isEmpty(), "Sorting an empty list should return an empty list");
    }

    /**
     * Tests sorting of students by balance with a single student.
     * Verifies that the sortBalance method returns a list containing only that student.
     */
    @Test
    public void testSortBalanceSingleStudent() {
        system.init("Alice", 300);

        List<String> sorted = system.sortBalance();
        assertEquals(List.of("Alice"), sorted, "Sorting a single student should return a list with that student");
    }

    /**
     * Tests sorting of students by name with multiple students.
     * Verifies that the sortName method correctly sorts students in alphabetical order.
     */
    @Test
    public void testSortNameWithMultipleStudents() {
        system.init("Charlie", 200);
        system.init("Alice", 300);
        system.init("Bob", 100);

        List<String> sorted = system.sortName();
        assertEquals(List.of("Alice", "Bob", "Charlie"), sorted, "Students should be sorted by name in alphabetical order");
    }

    /**
     * Tests the sortName method with an empty list of students.
     * Verifies that the method returns an empty list when there are no students to sort.
     */
    @Test
    public void testSortNameEmptyList() {
        assertTrue(system.sortName().isEmpty(), "Sorting an empty list should return an empty list");
    }

    /**
     * Tests sorting of students by name with a single student.
     * Verifies that the sortName method returns a list containing only that student.
     */
    @Test
    public void testSortNameSingleStudent() {
        system.init("Bob", 100);

        List<String> sorted = system.sortName();
        assertEquals(List.of("Bob"), sorted, "Sorting a single student should return a list with that student");
    }
}

