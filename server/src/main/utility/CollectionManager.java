package main.utility;


import main.data.Semester;
import main.data.StudyGroup;
import main.exceptions.CollectionIsEmptyException;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Operates the collection itself.
 */

public class CollectionManager {
    private LinkedList<StudyGroup> groupsCollection;

    private LocalDateTime lastInitTime;

    private LocalDateTime lastSaveTime;

    private CollectionFileManager collectionFileManager;

    public CollectionManager(CollectionFileManager collectionFileManager) {
        this.lastInitTime = null;
        this.lastSaveTime = null;
        this.collectionFileManager = collectionFileManager;

        loadCollection();
    }

    /**
     * @return Last initialization time or null if there wasn't initialization.
     */
    public LocalDateTime getLastInitTime() {
        return lastInitTime;
    }

    /**
     * @return Last save time or null if there wasn't saving.
     */
    public LocalDateTime getLastSaveTime() {
        return lastSaveTime;
    }

    /**
     * @return Name of the collection's type.
     */
    public String collectionType() {
        return groupsCollection.getClass().getName();
    }

    /**
     * @return Size of the collection.
     */
    public int collectionSize() {
        return groupsCollection.size();
    }

    /**
     * @return The first element of the collection or null if collection is empty.
     */

    public StudyGroup getFirst() {return groupsCollection.stream().findFirst().orElse(null);}


    /**
     * @return The last element of the collection or null if collection is empty.
     */

    public StudyGroup getLast() {
        if (groupsCollection.isEmpty()) return null;
        return groupsCollection.getLast();
    }

    /**
     * @param id ID of the group.
     * @return A group by his ID or null if group isn't found.
     */



    public StudyGroup getById(Long id) {
        return groupsCollection.stream().filter(group -> group.getId().equals(id)).findFirst().orElse(null);
    }

    /**
     * @return A group by his index or null if group isn't found.
     */

    public StudyGroup getByIndex(int index) {
        if (index == 0) {
            return groupsCollection.stream().findFirst().orElse(null);
        }
        return groupsCollection.stream().skip(index).findFirst().orElse(null);
    }

    /**
     * @param groupToFind A group who's value will be found.
     * @return A group by his value or null if group isn't found.
     */

    public StudyGroup getByValue(StudyGroup groupToFind) {
        return groupsCollection.stream().filter(group -> group.equals(groupToFind)).findFirst().orElse(null);
    }

    /**
     * @return Sum of all transferred students or 0 if collection is empty.
     */

    public int sumOfTransferredStudents() {
        return groupsCollection.stream()
                .reduce(0, (sum, p) -> sum + p.getTransferredStudents(), Integer::sum);
    }

    /**
     * @return Group, whose semesterEnum field value is the minimum.
     * @throws CollectionIsEmptyException If collection is empty.
     */

    public String minBySemester() throws CollectionIsEmptyException {
        if (groupsCollection.isEmpty()) throw new CollectionIsEmptyException();

        Semester minSemester = groupsCollection.stream().map(group -> group.getSemesterEnum())
                .min(Enum::compareTo).get();
        return groupsCollection.stream().filter(group -> group.getSemesterEnum().equals(minSemester)).findFirst().get().toString();
    }

    /**
     *
     * @return a sorted collection.
     */

    public void sortByNameAscending() {
        Collections.sort(groupsCollection, new Comparator<StudyGroup>() {
            @Override
            public int compare(StudyGroup group1, StudyGroup group2) {
                return group1.getName().compareTo(group2.getName());
            }
        });
    }

    public void addToCollection(StudyGroup group) {
//        group.setId(IDprovider.getInstance().getID());
        groupsCollection.add(group);
    }

    /**
     * Removes a group from collection.
     * @param group A group to remove.
     */

    public void removeFromCollection(StudyGroup group) {
        groupsCollection.remove(group);
    }

    /**
     * Remove group given the index.
     */

    public void removeByIndex(int index) {
        groupsCollection.remove(index);
    }

    /**
     * Group of groups with the same coordinates.
     */

    public Map<String, Long> groupCountingByCoordinates() {
        return groupsCollection.stream()
                .collect(Collectors.groupingBy(StudyGroup::getCoordinatesString, Collectors.counting()));
    }

    /**
     * Clears the collection.
     */

    public void clearCollection() {
        groupsCollection.clear();
    }

    /**
     * Generates next ID. It will be (the bigger one + 1).
     * @return Next ID.
     */

    public Long generateNextId() {
        if (groupsCollection.isEmpty()) return null;
        return groupsCollection.getLast().getId() + 1;
    }

    /**
     * Saves the collection to file.
     */

    public void saveCollection() {
        collectionFileManager.writeCollection(groupsCollection);
        lastSaveTime = LocalDateTime.now();
    }

    /**
     * Loads the collection from file.
     */
    private void loadCollection() {
        groupsCollection = collectionFileManager.readCollection();
        lastInitTime = LocalDateTime.now();
    }

    /**
     * @return Collection content or corresponding string if collection is empty.
     */

    public String showCollection() {
        if (groupsCollection.isEmpty()) return "Коллекция пуста!";
        return groupsCollection.stream().reduce("", (sum, m) -> sum += m + "\n\n", (sum1, sum2) -> sum1 + sum2).trim();
    }

    /**
     * @return Sum of all transferred students or 0 if collection is empty.
     */
    public int getSumOfTransferredStudents() {
        return groupsCollection.stream()
                .reduce(0, (sum, p) -> sum += p.getTransferredStudents(), Integer::sum);
    }
}
