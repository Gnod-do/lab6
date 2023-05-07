package main.commands;

import main.exceptions.CollectionIsEmptyException;
import main.exceptions.WrongAmountOfElementsException;
import main.utility.CollectionManager;
import main.utility.ResponseOutputer;

/**
 * Command 'sum_of_TransferredStudents'. Prints the sum of transferred students of all group.
 */

public class SumOfTransferredStudentsCommand extends AbstractCommand{

    private CollectionManager collectionManager;

    public SumOfTransferredStudentsCommand(CollectionManager collectionManager) {
        super("sum_of_transferred_students", "","вывести сумму значений поля transferred_students для всех элементов коллекции");
        this.collectionManager = collectionManager;
    }
    @Override
    public String getDescription() {
        return "вывести сумму значений поля transferred_students для всех элементов коллекции";
    }

    /**
     * Executes the command.
     * @return Command exit status.
     */

    @Override
    public boolean execute(String stringArgument, Object objectArgument) {
        try {
            if (!stringArgument.isEmpty() || objectArgument != null) throw new WrongAmountOfElementsException();
            int sum_of_transferred_students = collectionManager.getSumOfTransferredStudents();
            if (sum_of_transferred_students == 0) throw new CollectionIsEmptyException();
            ResponseOutputer.appendln("Сумма количества переведенных студентов: " + sum_of_transferred_students);
            return true;
        } catch (WrongAmountOfElementsException exception) {
            ResponseOutputer.appendln("Использование: '" + getName() + " " + getUsage() + "'");
        } catch (CollectionIsEmptyException exception) {
            ResponseOutputer.appenderror("Коллекция пуста!");
        }
        return false;
    }
}
