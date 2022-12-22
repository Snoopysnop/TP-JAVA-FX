package fr.istic.hanoi;

public class IllegalMoveException extends Exception {
    public final int fromStack;
    public final int toStack;

    public IllegalMoveException(int fromStack, int toStack) {
        this.fromStack = fromStack;
        this.toStack = toStack;
    }
}
