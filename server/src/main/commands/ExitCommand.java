package main.commands;

import main.exceptions.WrongAmountOfElementsException;
import main.utility.ResponseOutputer;

/**
 * Command 'exit'. Checks for wrong arguments then do nothing.
 */

public class ExitCommand extends  AbstractCommand{

    public ExitCommand() {super("exit", "", "завершить работу клиента");}

    @Override
    public String getDescription() {
        return "завершить программу (без сохранения в файл)";
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
