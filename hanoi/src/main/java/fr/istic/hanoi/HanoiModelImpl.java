package fr.istic.hanoi;

import java.util.ArrayDeque;
import java.util.Deque;

public class HanoiModelImpl implements HanoiModel {

    private final Deque<Integer>[] stacks = new Deque[NUMBER_OF_STACKS];

    public HanoiModelImpl(int numberOfDisks) {
        for (int stackNumber = 0; stackNumber < stacks.length; stackNumber++) {
            stacks[stackNumber] = new ArrayDeque<>();
        }
        for (int disk = 0; disk < numberOfDisks; disk++) {
            stacks[0].addLast(disk + 1);
        }
    }

    public boolean isValidMove(int fromStack, int toStack) {
        return !stacks[fromStack - 1].isEmpty()
                && ((stacks[toStack - 1].isEmpty()) ||
                (stacks[fromStack - 1].getFirst() < stacks[toStack - 1].getFirst()));
    }

    @Override
    public Deque<Integer> getStack(int stackNumber) {
        checkIndex(stackNumber);
        return stacks[stackNumber - 1];
    }

    @Override
    public void move(int fromStack, int toStack) throws IllegalMoveException {
        if (!isValidMove(fromStack, toStack)) {
            throw new IllegalMoveException(fromStack, toStack);
        }
        stacks[toStack - 1].addFirst(stacks[fromStack - 1].removeFirst());
    }

    private void checkIndex(int stackNumber) {
        if ((stackNumber < 1) || (stackNumber > NUMBER_OF_STACKS)) {
            throw new IllegalArgumentException();
        }
    }
}
