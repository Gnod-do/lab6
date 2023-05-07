package main.commands;


import main.data.StudyGroup;
import main.exceptions.CollectionIsEmptyException;
import main.exceptions.GroupNotFoundException;
import main.exceptions.WrongAmountOfElementsException;
import main.utility.CollectionManager;
import main.utility.ResponseOutputer;

/**
 * Command 'remove_at_index'. Removes the element by index.
 */

public class RemoveAtIndexCommand extends  AbstractCommand{

    private CollectionManager collectionManager;

    public RemoveAtIndexCommand(CollectionManager collectionManager) {
        super("remove_at_index <index>","","удалить элемент из коллекции по index");
        this.collectionManager = collectionManager;
    }

    @Override
    public String getDescription() {
        return "удалить элемент из коллекции по index";
    }

    /**
     * Executes the command.
     * @return Command exit status.
     */

    @Override
    public boolean execute(String stringArgument, Object objectArgument) {
        try {
            if (stringArgument.isEmpty() || objectArgument != null) throw new WrongAmountOfElementsException();
            if (collectionManager.collectionSize() == 0) throw new CollectionIsEmptyException();
            int index = Integer.parseInt(stringArgument);
            StudyGroup groupToRemove = collectionManager.getByIndex(index);
            if (groupToRemove == null) throw new GroupNotFoundException();
            collectionManager.removeFromCollection(groupToRemove);
            ResponseOutputer.appendln("группа успешно удалена!");
            return true;
        } catch (WrongAmountOfElementsException exception) {
            ResponseOutputer.appendln("Использование: '" + getName() + "'");
        } catch (CollectionIsEmptyException exception) {
            ResponseOutputer.appenderror("Коллекция пуста!");
        } catch (NumberFormatException exception) {
            ResponseOutputer.appenderror("Index должен быть представлен числом!");
        } catch (GroupNotFoundException exception) {
            ResponseOutputer.appenderror("Солдата с таким ID в коллекции нет!");
        } catch (IndexOutOfBoundsException exception) {
            ResponseOutputer.appenderror("этот индекс не существует в коллекции");
        }
        return false;
    }
}
