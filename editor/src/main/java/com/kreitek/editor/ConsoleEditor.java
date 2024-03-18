package com.kreitek.editor;

import com.kreitek.editor.commands.CommandFactory;
import com.kreitek.editor.commands.UndoCommand;

import java.util.*;

public class ConsoleEditor implements Editor {
    public static final String TEXT_RESET = "\u001B[0m";
    public static final String TEXT_BLACK = "\u001B[30m";
    public static final String TEXT_RED = "\u001B[31m";
    public static final String TEXT_GREEN = "\u001B[32m";
    public static final String TEXT_YELLOW = "\u001B[33m";
    public static final String TEXT_BLUE = "\u001B[34m";
    public static final String TEXT_PURPLE = "\u001B[35m";
    public static final String TEXT_CYAN = "\u001B[36m";
    public static final String TEXT_WHITE = "\u001B[37m";

    private CommandFactory commandFactory = new CommandFactory();
    private ArrayList<String> documentLines = new ArrayList<String>();
    private CareTaker careTaker = new CareTaker();



    public void run() {
        boolean exit = false;
        while (!exit) {
            String commandLine = waitForNewCommand();
            try {
                if (commandLine.equals("undo")) {
                    Memento memento = careTaker.pop();
                    if (memento != null) {
                        restore(memento);
                        Command undoCommand = new UndoCommand(careTaker);
                        undoCommand.execute(documentLines);
                    } else {
                        printLnToConsole("No hay operaciones para deshacer.");
                    }
                } else {
                    Command command = commandFactory.getCommand(commandLine);
                    command.execute(documentLines);
                    Memento state = getState();
                    careTaker.push(state);

                }
            } catch (BadCommandException e) {
                printErrorToConsole("Bad command");
            } catch (ExitException e) {
                exit = true;
            }

            showDocumentLines(documentLines);
            showHelp();
        }
    }

    private void showDocumentLines(ArrayList<String> textLines) {
        if (textLines.size() > 0) {
            setTextColor(TEXT_YELLOW);
            printLnToConsole("START DOCUMENT ==>");
            for (int index = 0; index < textLines.size(); index++) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("[");
                stringBuilder.append(index);
                stringBuilder.append("] ");
                stringBuilder.append(textLines.get(index));
                printLnToConsole(stringBuilder.toString());
            }
            printLnToConsole("<== END DOCUMENT");
            setTextColor(TEXT_RESET);
        }
    }

    private String waitForNewCommand() {
        printToConsole("Enter a command : ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    private void showHelp() {
        printLnToConsole("To add new line -> a \"your text\"");
        printLnToConsole("To update line  -> u [line number] \"your text\"");
        printLnToConsole("To delete line  -> d [line number]");
        printLnToConsole("To undo last action -> undo");
    }

    private void printErrorToConsole(String message) {
        setTextColor(TEXT_RED);
        printToConsole(message);
        setTextColor(TEXT_RESET);
    }

    private void setTextColor(String color) {
        System.out.println(color);
    }

    private void printLnToConsole(String message) {
        System.out.println(message);
    }

    private void printToConsole(String message) {
        System.out.print(message);
    }


    public void restore(Memento memento) {
        if (memento != null) {
            Map<String, Object> state = memento.getState();
            commandFactory = (CommandFactory) state.get("commandFactory");
            documentLines = (ArrayList<String>) state.get("documentLines");

        }
    }

    public Memento getState() {
        Map<String, Object> state = new HashMap<>();
        state.put("commandFactory", commandFactory);
        state.put("documentLines", new ArrayList<>(documentLines));
        return new Memento(state);
    }


}


