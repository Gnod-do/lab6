package main.commands;


import main.exceptions.WrongAmountOfElementsException;
import main.utility.ResponseOutputer;

/**
 * Command 'help'. It's here just for logical structure.
 */

public class HelpCommand extends AbstractCommand{

    public HelpCommand() {super("help", "", "вывести справку по доступным командам");}

    @Override
    public String getDescription() {
        return "вывести справку по доступным командам";
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
