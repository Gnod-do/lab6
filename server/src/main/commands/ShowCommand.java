package main.commands;


import main.exceptions.WrongAmountOfElementsException;
import main.utility.CollectionManager;
import main.utility.ResponseOutputer;

/**
 * Command 'show'. Shows information about all elements of the collection.
 */

public class ShowCommand extends AbstractCommand{

    private CollectionManager collectionManager;

    public ShowCommand(CollectionManager collectionManager) {
        super("show", "", "вывести все элементы коллекции");
        this.collectionManager = collectionManager;
    }

    @Override
    public String getDescription() {
        return "вывести все элементы коллекции";
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
            ResponseOutputer.appendln(collectionManager.showCollection());
            return true;
        } catch (WrongAmountOfElementsException exception) {
            ResponseOutputer.appendln("Использование: '" + getName() + " " + getUsage() + "'");
        }
        return false;
    }
}
