package main.commands;

import main.exceptions.WrongAmountOfElementsException;
import main.utility.CollectionManager;
import main.utility.ResponseOutputer;

/**
 * Command 'save'. Saves the collection to a file.
 */

public class SaveCommand extends AbstractCommand{

    private CollectionManager collectionManager;

    public SaveCommand(CollectionManager collectionManager) {
        super("save", "", "сохранить коллекцию в файл");
        this.collectionManager = collectionManager;
    }

    @Override
    public String getDescription() {
        return "сохранить коллекцию в файл";
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
            collectionManager.saveCollection();
            return true;
        } catch (WrongAmountOfElementsException exception) {
            ResponseOutputer.appendln("Использование: '" + getName() + " " + getUsage() + "'");
        }
        return false;
    }
}
