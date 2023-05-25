package main.utility;


import main.commands.Command;
import main.exceptions.HistoryIsEmptyException;

import java.util.ArrayList;
import java.util.List;

/**
 * Operates the commands.
 */

public class CommandManager {
    private final int COMMAND_HISTORY_SIZE = 11;

    private String[] commandHistory = new String[COMMAND_HISTORY_SIZE];

    private List<Command> commands = new ArrayList<>();

    private Command helpCommand;

    private Command infoCommand;

    private Command showCommand;

    private Command addCommand;

    private Command updateCommand;

    private Command removeByIdCommand;

    private Command clearCommand;

    private Command saveCommand;

    private Command exitCommand;

    private Command executeScriptCommand;

    private Command historyCommand;

    private Command sumOfTransferredStudentsCommand;

    private Command sortCommand;

    private Command removeAtIndexCommand;

    private Command minBySemesterEnumCommand;

    private Command groupCountingByCoordinatesCommand;

    private Command serverExitCommand;

    public CommandManager(Command helpCommand, Command infoCommand, Command showCommand, Command addCommand, Command updateCommand,
                          Command removeByIdCommand, Command clearCommand, Command exitCommand, Command executeScriptCommand,
                          Command removeAtIndexCommand, Command sortCommand, Command historyCommand, Command sumOfTransferredStudentsCommand,
                          Command minBySemesterEnumCommand, Command groupCountingByCoordinatesCommand) {
        this.helpCommand = helpCommand;
        this.infoCommand = infoCommand;
        this.showCommand = showCommand;
        this.addCommand = addCommand;
        this.updateCommand = updateCommand;
        this.removeByIdCommand = removeByIdCommand;
        this.clearCommand = clearCommand;
//        this.saveCommand = saveCommand;
        this.exitCommand = exitCommand;
        this.executeScriptCommand = executeScriptCommand;
        this.removeAtIndexCommand = removeAtIndexCommand;
        this.sortCommand = sortCommand;
        this.historyCommand = historyCommand;
        this.sumOfTransferredStudentsCommand = sumOfTransferredStudentsCommand;
        this.minBySemesterEnumCommand = minBySemesterEnumCommand;
        this.groupCountingByCoordinatesCommand = groupCountingByCoordinatesCommand;
//        this.serverExitCommand = serverExitCommand;

        commands.add(helpCommand);
        commands.add(infoCommand);
        commands.add(showCommand);
        commands.add(addCommand);
        commands.add(updateCommand);
        commands.add(removeByIdCommand);
        commands.add(clearCommand);
//        commands.add(saveCommand);
        commands.add(exitCommand);
        commands.add(executeScriptCommand);
        commands.add(removeAtIndexCommand);
        commands.add(sortCommand);
        commands.add(historyCommand);
        commands.add(sumOfTransferredStudentsCommand);
        commands.add(minBySemesterEnumCommand);
        commands.add(groupCountingByCoordinatesCommand);
//        commands.add(serverExitCommand);
    }

    /**
     * @return List of manager's commands.
     */
    public List<Command> getCommands() {
        return commands;
    }

    /**
     * Adds command to command history.
     *
     * @param commandToStore Command to add.
     */
    public void addToHistory(String commandToStore) {

        for (Command command : commands) {
            if (command.getName().equals(commandToStore)) {
                for (int i = COMMAND_HISTORY_SIZE - 1; i > 0; i--) {
                    commandHistory[i] = commandHistory[i - 1];
                }
                commandHistory[0] = commandToStore;
            }
        }
    }
    /**
     * Prints info about the all commands.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean help(String stringArgument, Object objectArgument) {
        if (helpCommand.execute(stringArgument, objectArgument)) {
            for (Command command : commands) {
                ResponseOutputer.appendtable(command.getName() + " " + command.getUsage(), command.getDescription());
            }
            return true;
        } else return false;
    }

    /**
     * Executes needed command.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean info(String stringArgument, Object objectArgument) {
        return infoCommand.execute(stringArgument, objectArgument);
    }

    /**
     * Executes needed command.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean show(String stringArgument, Object objectArgument) {
        return showCommand.execute(stringArgument, objectArgument);
    }

    /**
     * Executes needed command.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */

    public boolean add(String stringArgument, Object objectArgument) {
        return addCommand.execute(stringArgument, objectArgument);
    }

    /**
     * Executes needed command.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean update(String stringArgument, Object objectArgument) {
        return updateCommand.execute(stringArgument, objectArgument);
    }

    /**
     * Executes needed command.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean removeById(String stringArgument, Object objectArgument) {
        return removeByIdCommand.execute(stringArgument, objectArgument);
    }

    /**
     * Executes needed command.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean clear(String stringArgument, Object objectArgument) {
        return clearCommand.execute(stringArgument, objectArgument);
    }

    /**
     * Executes needed command.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean save(String stringArgument, Object objectArgument) {
        return saveCommand.execute(stringArgument, objectArgument);
    }

    /**
     * Executes needed command.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean exit(String stringArgument, Object objectArgument) {
        return exitCommand.execute(stringArgument, objectArgument);
    }

    /**
     * Executes needed command.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean executeScript(String stringArgument, Object objectArgument) {
        return executeScriptCommand.execute(stringArgument, objectArgument);
    }

    public boolean sumOfTransferredStudents(String stringArgument, Object objectArgument) {return sumOfTransferredStudentsCommand.execute(stringArgument, objectArgument);}

    public boolean sort(String stringArgument, Object objectArgument) {return sortCommand.execute(stringArgument, objectArgument);}

    public boolean removeAtIndex(String stringArgument, Object objectArgument){return removeAtIndexCommand.execute(stringArgument, objectArgument);}

    public boolean minBySemesterEnum(String stringArgument, Object objectArgument) {return minBySemesterEnumCommand.execute(stringArgument, objectArgument);}

    public boolean groupCountingByCoordinates(String stringArgument, Object objectArgument) {return groupCountingByCoordinatesCommand.execute(stringArgument, objectArgument);}

    /**
     * Prints the history of used commands.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean history(String stringArgument, Object objectArgument) {
        if (historyCommand.execute(stringArgument, objectArgument)) {
            try {
                if (commandHistory.length == 0) throw new HistoryIsEmptyException();

                ResponseOutputer.appendln("Последние использованные команды:");
                for (String command : commandHistory) {
                    if (command != null) ResponseOutputer.appendln(" " + command);
                }
                return true;
            } catch (HistoryIsEmptyException exception) {
                ResponseOutputer.appendln("Ни одной команды еще не было использовано!");
            }
        }
        return false;
    }

    /**
     * Executes needed command.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean serverExit(String stringArgument, Object objectArgument) {
        return serverExitCommand.execute(stringArgument, objectArgument);
    }


}
