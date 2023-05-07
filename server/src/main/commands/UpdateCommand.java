package main.commands;

import main.data.*;
import main.exceptions.CollectionIsEmptyException;
import main.exceptions.GroupNotFoundException;
import main.exceptions.WrongAmountOfElementsException;
import main.interaction.GroupRaw;
import main.utility.CollectionManager;
import main.utility.ResponseOutputer;

/**
 * Command 'update'. Updates the information about selected marine.
 */


public class UpdateCommand extends  AbstractCommand{

    private CollectionManager collectionManager;

    public UpdateCommand(CollectionManager collectionManager) {
        super("update", "<ID> {element}", "обновить значение элемента коллекции по ID");
        this.collectionManager = collectionManager;
    }

    @Override
    public String getDescription() {
        return "обновить значение элемента коллекции по ID";
    }

    /**
     * Executes the command.
     *
     * @return Command exit status.
     */
    @Override
    public boolean execute(String stringArgument, Object objectArgument) {
        try {
            if (stringArgument.isEmpty() || objectArgument == null) throw new WrongAmountOfElementsException();
            if (collectionManager.collectionSize() == 0) throw new CollectionIsEmptyException();

            Long id = Long.parseLong(stringArgument);
            if (id <= 0) throw new NumberFormatException();
            StudyGroup oldGroup = collectionManager.getById(id);
            if (oldGroup == null) throw new GroupNotFoundException();

            GroupRaw groupRaw = (GroupRaw) objectArgument;
            String name = groupRaw.getName() == null ? oldGroup.getName() : groupRaw.getName();
            Coordinates coordinates = groupRaw.getCoordinates() == null ? oldGroup.getCoordinates() : groupRaw.getCoordinates();
            Long studentsCount = groupRaw.getStudentsCount() < 0 ? oldGroup.getStudentsCount() : groupRaw.getStudentsCount();
            int transferredStudents = groupRaw.getTransferredStudents() < 0 ? oldGroup.getTransferredStudents() : groupRaw.getTransferredStudents();
            FormOfEducation formOfEducation = groupRaw.getFormOfEducation() == null ? oldGroup.getFormOfEducation() : groupRaw.getFormOfEducation();
            Semester semester = groupRaw.getSemester() == null ? oldGroup.getSemesterEnum() : groupRaw.getSemester();
            Person groupAdmin = groupRaw.getGroupAdmin() == null ? oldGroup.getGroupAdmin() : groupRaw.getGroupAdmin();

            collectionManager.removeFromCollection(oldGroup);
            collectionManager.addToCollection(new StudyGroup(
                    name,
                    coordinates,
                    studentsCount,
                    transferredStudents,
                    formOfEducation,
                    semester,
                    groupAdmin
            ));
            ResponseOutputer.appenderror("группа успешно изменена!");
            return true;
        } catch (WrongAmountOfElementsException exception) {
            ResponseOutputer.appendln("Использование: '" + getName() + " " + getUsage() + "'");
        } catch (CollectionIsEmptyException exception) {
            ResponseOutputer.appenderror("Коллекция пуста!");
        } catch (NumberFormatException exception) {
            ResponseOutputer.appenderror("ID должен быть представлен положительным числом!");
        } catch (GroupNotFoundException exception) {
            ResponseOutputer.appenderror("группа с таким ID в коллекции нет!");
        } catch (ClassCastException exception) {
            ResponseOutputer.appenderror("Переданный клиентом объект неверен!");
        }
        return false;
    }

}
