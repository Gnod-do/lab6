package main.commands;


import main.exceptions.WrongAmountOfElementsException;
import main.utility.CollectionManager;
import main.utility.ResponseOutputer;

/**
 * Command 'clear'. Clears the collection.
 */

public class ClearCommand extends AbstractCommand{
    private CollectionManager collectionManager;

    public ClearCommand(CollectionManager collectionManager) {
        super("clear", "","очистить коллекцию");
        this.collectionManager = collectionManager;
    }

    @Override
    public String getDescription() {
        return "очистить коллекцию";
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
            collectionManager.clearCollection();
            ResponseOutputer.appendln("Коллекция очищена!");
            return true;
        } catch (WrongAmountOfElementsException exception) {
            ResponseOutputer.appendln("Использование: '" + getName() + " " + getUsage() + "'");
        }
        return false;
    }
}
