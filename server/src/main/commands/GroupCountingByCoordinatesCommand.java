package main.commands;


import main.exceptions.CollectionIsEmptyException;
import main.utility.CollectionManager;
import main.utility.ResponseOutputer;

/**
 * Command 'group_counting_by_coordinates'. Group the elements of the collection by the value of the field coordinates, display the number of elements in each group.
 */

public class GroupCountingByCoordinatesCommand extends AbstractCommand{
    private CollectionManager collectionManager;

    public GroupCountingByCoordinatesCommand(CollectionManager collectionManager) {
        super("group_counting_by_coordinates","", "Отображает группы, сгруппированные по координатам");
        this.collectionManager = collectionManager;
    }

    @Override
    public String getDescription() {
        return "Отображает группы, сгруппированные по координатам";
    }

    @Override
    public boolean execute(String commandStringArgument, Object commandObjectArgument) {
        try {
            if (collectionManager.collectionSize() == 0) throw new CollectionIsEmptyException();
            collectionManager.groupCountingByCoordinates();
            ResponseOutputer.appendln("сгруппированные учебные группы по координатам");
            return true;
        } catch (CollectionIsEmptyException exception) {
            ResponseOutputer.appenderror("Коллекция пуста!");
        }
        return false;
    }

    /**
     * Executes the command.
     * @return Command exit status.
     */


}
