package fr.istic.hanoi.test;

import fr.istic.hanoi.HanoiModel;
import fr.istic.hanoi.HanoiModelImpl;
import fr.istic.hanoi.IllegalMoveException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Test class for the model.
 * <p>
 * WARNING
 * <p>
 * If you are using modules (which the case here), you must add these options to the run configuration:
 * <p>
 * --add-exports org.junit.platform.commons/org.junit.platform.commons.util=ALL-UNNAMED
 * --add-exports org.junit.platform.commons/org.junit.platform.commons.logging=ALL-UNNAMED
 */
public class HanoiModelTest {

    private static final int DISK_COUNT = 3;
    private HanoiModel model;

    @BeforeEach
    void setup() {
        assert DISK_COUNT >= 2;

        model = new HanoiModelImpl(DISK_COUNT);
    }

    @Test
    void testInit() {
        assertFalse(model.getStack(1).isEmpty());
        for (int d = 1; d <= DISK_COUNT; d++) {
            assertEquals(d, model.getStack(1).removeFirst());
        }
        for (int stackNumber = 2; stackNumber <= HanoiModel.NUMBER_OF_STACKS; stackNumber++) {
            assertTrue(model.getStack(stackNumber).isEmpty());
        }
    }

    @Test
    @DisplayName("Check is basic legal move")
    void testMove1() throws IllegalMoveException {
        assertTrue(model.isValidMove(1, 2));
        model.move(1, 2);
        assertEquals(2, model.getStack(1).getFirst());
        assertEquals(1, model.getStack(2).getFirst());
    }

    @Test
    void testMoveAll() throws IllegalMoveException {
        model.move(1, 3);
        model.move(1, 2);
        model.move(3, 2);
        model.move(1, 3);
        model.move(2, 1);
        model.move(2, 3);
        model.move(1, 3);

        for (int d = 1; d <= DISK_COUNT; d++) {
            assertEquals(d, model.getStack(3).removeFirst());
        }
    }

    @Test
    @DisplayName("Check that a large disk on a small one is a detected as an illegal move")
    void testBadMove1() throws IllegalMoveException {
        model.move(1, 2);
        assertThrows(IllegalMoveException.class, () -> model.move(1, 2));
    }

    @Test
    @DisplayName("Check that a move from an empty stack is detected as an illegal move")
    void testBadMove2() throws IllegalMoveException {
        model.move(1, 2);
        model.move(1, 3);
        model.move(2, 3);
        model.move(1, 2);
        assertTrue(model.getStack(1).isEmpty());
        assertThrows(IllegalMoveException.class, () -> model.move(1, 2));
    }

}
