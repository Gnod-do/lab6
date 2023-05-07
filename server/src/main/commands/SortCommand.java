package main.commands;


import main.exceptions.CollectionIsEmptyException;
import main.utility.CollectionManager;
import main.utility.ResponseOutputer;

/**
 * Command 'sort'. Returns a sorted collection.
 */

public class SortCommand extends AbstractCommand{

    private CollectionManager collectionManager;

    public SortCommand(CollectionManager collectionManager) {
        super("sort","","Отображение коллекций, отсортированных по имени");
        this.collectionManager = collectionManager;
    }

    @Override
    public String getDescription() {
        return "Отображение коллекций, отсортированных по имени";
    }

    /**
     * Executes the command.
     * @return Command exit status.
     */

    @Override
    public boolean execute(String stringArgument, Object objectArgument) {
        try {
            if (collectionManager.collectionSize() == 0) throw new CollectionIsEmptyException();
            collectionManager.sortByNameAscending();
            ResponseOutputer.appendln(collectionManager.showCollection());
            return true;
        } catch (CollectionIsEmptyException exception) {
            ResponseOutputer.appendln("Коллекция пуста!");
        }
        return false;
    }
}
