package fr.istic.hanoi;

import java.util.Deque;

public interface HanoiModel {

    /**
     * Return the list of disk sizes from top to bottom of stack
     *
     * @param stackNumber is the stack number, must be between 1 and NUMBER_OF_STACKS
     * @return the queue of disk sizes on the stack
     * @throws IllegalArgumentException if stackNumber not in [1..NUMBER_OF_STACKS]
     */
    Deque<Integer> getStack(int stackNumber);

    boolean isValidMove(int fromStack, int toStack);

    /**
     * Move the top disk of one stack to the top of another stack
     *
     * @param fromStack is the stack to pop the disk from
     * @param toStack   is the stack to push the disk on
     * @throws IllegalMoveException if the disk on fromStack is larger than the
     *                              one on top of toStack
     */
    void move(int fromStack, int toStack) throws IllegalMoveException;

    int NUMBER_OF_STACKS = 3;

}
