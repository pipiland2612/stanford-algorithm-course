import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TwoSATTest {

    @Test
    public void testSatisfiableCase() {
        // Case 1: A simple satisfiable 2-SAT scenario
        // x1 OR x2, NOT x1 OR x2
        TwoSAT twoSAT = new TwoSAT(2);
        twoSAT.addClause(0, true, 1, true);  // x1 OR x2
        twoSAT.addClause(0, false, 1, true); // NOT x1 OR x2

        assertTrue(twoSAT.isSatisfiable());
    }

    @Test
    public void testUnsatisfiableCase() {
        // Case 2: A simple unsatisfiable 2-SAT scenario
        // x1 OR x2, NOT x1 OR NOT x2, x1 OR NOT x2, NOT x1 OR x2
        TwoSAT twoSAT = new TwoSAT(2);
        twoSAT.addClause(0, true, 1, true);   // x1 OR x2
        twoSAT.addClause(0, false, 1, false); // NOT x1 OR NOT x2
        twoSAT.addClause(0, true, 1, false);  // x1 OR NOT x2
        twoSAT.addClause(0, false, 1, true);  // NOT x1 OR x2

        assertFalse(twoSAT.isSatisfiable());
    }

    @Test
    public void testLargerSatisfiableCase() {
        // Case 3: A larger satisfiable 2-SAT scenario
        // Clauses: x1 OR x2, NOT x2 OR x3, NOT x1 OR x3
        TwoSAT twoSAT = new TwoSAT(3);
        twoSAT.addClause(0, true, 1, true);   // x1 OR x2
        twoSAT.addClause(1, false, 2, true);  // NOT x2 OR x3
        twoSAT.addClause(0, false, 2, true);  // NOT x1 OR x3

        assertTrue(twoSAT.isSatisfiable());
    }

    @Test
    public void testLargerUnsatisfiableCase() {
        // Case 4: A larger unsatisfiable 2-SAT scenario
        // Clauses: x1 OR x3, NOT x1 OR NOT x3, x2 OR NOT x3, NOT x2 OR x3, x1 OR NOT x2, NOT x1 OR x2
        TwoSAT twoSAT = new TwoSAT(3);
        twoSAT.addClause(0, true, 2, true);   // x1 OR x3
        twoSAT.addClause(0, false, 2, false); // NOT x1 OR NOT x3
        twoSAT.addClause(1, true, 2, false);  // x2 OR NOT x3
        twoSAT.addClause(1, false, 2, true);  // NOT x2 OR x3
        twoSAT.addClause(0, true, 1, false);  // x1 OR NOT x2
        twoSAT.addClause(0, false, 1, true);  // NOT x1 OR x2

        assertFalse(twoSAT.isSatisfiable());
    }
}