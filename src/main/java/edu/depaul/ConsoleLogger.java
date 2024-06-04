package edu.depaul;

public class ConsoleLogger implements Logger {
    @Override
    public void log(String message) {
        System.out.println("LOG: " + message);
    }

    @Override
    public void error(String message) {
        System.err.println("ERROR: " + message);
    }

    @Override
    public void debug(String message) {
        System.out.println("DEBUG: " + message);
    }
}
