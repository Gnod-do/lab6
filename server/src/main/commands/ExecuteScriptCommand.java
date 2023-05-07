package main.commands;

import main.exceptions.WrongAmountOfElementsException;
import main.utility.ResponseOutputer;

/**
 * Command 'execute_script'. Executes scripts from a file. Ectually only checks argument and prints messages.
 */

public class ExecuteScriptCommand extends AbstractCommand {
    public ExecuteScriptCommand() {super("execute_script", "<file_name>", "исполнить скрипт из указанного файла");}

    @Override
    public String getDescription() {
        return "исполнить скрипт из указанного файла";
    }

    /**
     * Executes the command, but partially.
     *
     * @return Command exit status.
     */

    @Override
    public boolean execute(String stringArgument, Object objectArgument) {
        try {
            if (stringArgument.isEmpty() || objectArgument != null) throw new WrongAmountOfElementsException();
            return true;
        } catch (WrongAmountOfElementsException exception) {
            ResponseOutputer.appendln("Использование: '" + getName() + " " + getUsage() + "'");
        }
        return false;
    }
}
