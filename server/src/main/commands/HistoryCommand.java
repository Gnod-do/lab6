package main.commands;


import main.exceptions.WrongAmountOfElementsException;
import main.utility.ResponseOutputer;

/**
 * Command 'history'. It's here just for logical structure.
 */

public class HistoryCommand extends AbstractCommand{

    public HistoryCommand() {super("history", "", "вывести историю использованных команд");}


    @Override
    public String getDescription() {
        return "вывести историю использованных команд";
    }

    /**
     * Executes the command.
     *
     * @return Command exit status.
     */
    @Override
    public boolean execute(String stringArgument, Object objectArgument) {
        try {
            if (!stringArgument.isEmpty() || objectArgument != null) throw new WrongAmountOfElementsException();
            return true;
        } catch (WrongAmountOfElementsException exception) {
            ResponseOutputer.appendln("Использование: '" + getName() + " " + getUsage() + "'");
        }
        return false;
    }
}
