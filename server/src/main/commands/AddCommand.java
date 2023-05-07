package main.commands;

import main.data.StudyGroup;
import main.exceptions.WrongAmountOfElementsException;
import main.interaction.GroupRaw;
import main.utility.CollectionManager;
import main.utility.ResponseOutputer;

/**
 * Command 'add'. Adds a new element to collection.
 */

public class AddCommand extends AbstractCommand{

    private CollectionManager collectionManager;

    public AddCommand(CollectionManager collectionManager) {
        super("add", "{element}","добавить новый элемент в коллекцию");
        this.collectionManager = collectionManager;
    }

    @Override
    public String getDescription() {
        return "добавить новый элемент в коллекцию";
    }

    /**
     * Executes the command.
     *
     * @return Command exit status.
     */

    @Override
    public boolean execute(String stringArgument, Object objectArgument) {
        try {
            if (!stringArgument.isEmpty() || objectArgument == null) throw new WrongAmountOfElementsException();
            GroupRaw groupRaw = (GroupRaw) objectArgument;
//            ResponseOutputer.appendln("den day rui");
            collectionManager.addToCollection(new StudyGroup(
                    groupRaw.getName(),
                    groupRaw.getCoordinates(),
                    groupRaw.getStudentsCount(),
                    groupRaw.getTransferredStudents(),
                    groupRaw.getFormOfEducation(),
                    groupRaw.getSemester(),
                    groupRaw.getGroupAdmin()
            ));
            ResponseOutputer.appendln("группа успешно добавлена!");
            return true;
        } catch (WrongAmountOfElementsException exception) {
            ResponseOutputer.appendln("Использование: '" + getName() + " " + getUsage() + "'");
        } catch (ClassCastException exception) {
            ResponseOutputer.appenderror("Переданный клиентом объект неверен!");
        }
        return false;
    }

}
