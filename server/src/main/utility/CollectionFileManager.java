package main.utility;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import main.data.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class CollectionFileManager {
    private Gson gson = new Gson();

    private GsonBuilder gsonBuilder = new GsonBuilder();

    private String envVariable;

    public CollectionFileManager(String envVariable) {this.envVariable = envVariable;}

    /**
     * Writes collection to a file.
     * @param studyGroups Collection to write.
     */

    public void writeCollection(Collection<StudyGroup> studyGroups) {


        if (System.getenv().get(envVariable) != null) {
            try (FileWriter collectionFileWriter = new FileWriter(new File(System.getenv().get(envVariable)))) {
                JSONArray studyGroupsJson = new JSONArray();

                for (StudyGroup studyGroup : studyGroups) {
                    JSONObject studyGroupJson = new JSONObject();
                    studyGroupJson.put("name", studyGroup.getName());

                    JSONObject coordinatesJson = new JSONObject();
                    coordinatesJson.put("x", studyGroup.getCoordinates().getX());
                    coordinatesJson.put("y", studyGroup.getCoordinates().getY());
                    studyGroupJson.put("coordinates", coordinatesJson);

                    studyGroupJson.put("studentsCount", studyGroup.getStudentsCount());
                    studyGroupJson.put("transferredStudents", studyGroup.getTransferredStudents());
                    studyGroupJson.put("formOfEducation", studyGroup.getFormOfEducation().toString());
                    studyGroupJson.put("semesterEnum", studyGroup.getSemesterEnum().toString());

                    JSONObject groupAdminJson = new JSONObject();
                    groupAdminJson.put("name", studyGroup.getGroupAdmin().getName());
                    groupAdminJson.put("birthday", studyGroup.getGroupAdmin().getBirthday().toString());
                    groupAdminJson.put("height", studyGroup.getGroupAdmin().getHeight());
                    groupAdminJson.put("weight", studyGroup.getGroupAdmin().getWeight());
                    groupAdminJson.put("passportID", studyGroup.getGroupAdmin().getPassportID());
                    studyGroupJson.put("groupAdmin", groupAdminJson);

                    studyGroupsJson.put(studyGroupJson);
                }
                collectionFileWriter.write(studyGroupsJson.toString());


                ResponseOutputer.appendln("Коллекция успешно сохранена в файл.");
            } catch (IOException exception) {
                ResponseOutputer.appenderror("Загрузочный файл является директорией/не может быть открыт!");
            }
        } else ResponseOutputer.appenderror("Системная переменная с загрузочным файлом не найдена!");
    }

    /**
     * Reads collection from a file.
     * @return Read collection.
     */

    public LinkedList<StudyGroup> readCollection() {
        if (System.getenv().get(envVariable) != null) {
            try (FileInputStream collectionFile = new FileInputStream(System.getenv().get(envVariable));
                 BufferedInputStream bis = new BufferedInputStream(collectionFile)) {

                ByteArrayOutputStream buf = new ByteArrayOutputStream();
                int result = bis.read();
                while(result != -1) {
                    buf.write((byte) result);
                    result = bis.read();
                }
                String jsonString = buf.toString("UTF-8");
                JSONArray jsonArray = new JSONArray(jsonString);
//                JsonReader jsonReader = new JsonReader(new InputStreamReader(collectionFileBuffer));
//                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(collectionFileBuffer, StandardCharsets.UTF_8))
//;
//                StringBuilder stringBuilder = new StringBuilder();
//                String line;
//                while ((line = bufferedReader.readLine()) != null) {
//                    stringBuilder.append(line);
//                }
//
//                JSONTokener jsonTokener = new JSONTokener(stringBuilder.toString());
//                JSONArray jsonArray = new JSONArray(jsonTokener);

                LinkedList<StudyGroup> collection = new LinkedList<>();

                for (int i = 0; i <jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String name = jsonObject.getString("name");
                    JSONObject coordinates = jsonObject.getJSONObject("coordinates");
                    Coordinates coordinatesObj = new Coordinates(coordinates.getInt("x"),coordinates.getLong("y"));
                    Long studentsCount = jsonObject.getLong("studentsCount");

                    int transferredStudents = jsonObject.getInt("transferredStudents");

                    FormOfEducation formOfEducation;
                    try {
                        formOfEducation = FormOfEducation.valueOf(jsonObject.getString("formOfEducation"));
                    } catch (IllegalArgumentException exception){
                        break;
                    }

                    Semester semesterEnum;
                    try {
                        semesterEnum = Semester.valueOf(jsonObject.getString("semesterEnum"));
                    } catch (IllegalArgumentException exception){
                        break;
                    }

                    JSONObject groupAdminObject = jsonObject.getJSONObject("groupAdmin");

                    String birthdayStr = groupAdminObject.getString("birthday");
                    LocalDate birthday;
                    try {
                        birthday = LocalDate.parse(birthdayStr);
                    } catch (DateTimeParseException exception){
                        Outputer.println(i +" элемент не имеет типа данных, соответствующего шаблону yyyy-MM-dd");
                        break;
                    }

                    Person groupAdminObj = new Person(groupAdminObject.getString("name"), birthday, groupAdminObject.getLong("height"),
                            groupAdminObject.getDouble("weight"), groupAdminObject.getString("passportID"));

                    StudyGroup studyGroup = new StudyGroup(name, coordinatesObj, studentsCount, transferredStudents, formOfEducation, semesterEnum, groupAdminObj);
                    if(!(studyGroup.getName().isEmpty() || studyGroup.getCoordinates().getY() == null || studyGroup.getStudentsCount() <= 0 || studyGroup.getTransferredStudents() <= 0 ||
                            studyGroup.getGroupAdmin().getName().isEmpty() || studyGroup.getGroupAdmin().getWeight() <= 0 || studyGroup.getGroupAdmin().getHeight() <= 0 ||
                            studyGroup.getGroupAdmin().getPassportID().length() < 5|| studyGroup.getGroupAdmin().getPassportID().length() > 23 ||
                            studyGroup.getGroupAdmin().getPassportID().isEmpty())){
                        collection.add(studyGroup);
                    }
//                    collection.add(studyGroup);
                }
//                System.out.println(collection);
//                App.logger.info("Коллекция успешна загружена!");
                return collection;
            } catch (FileNotFoundException exception) {
                Outputer.printerror("Загрузочный файл не найден!");
//                App.logger.warn("Загрузочный файл не найден!");
            } catch (NoSuchElementException exception) {
                Outputer.printerror("Загрузочный файл пуст!");
//                App.logger.error("Загрузочный файл пуст!");
            } catch (JsonParseException | NullPointerException exception) {
                Outputer.printerror("В загрузочном файле не обнаружена необходимая коллекция!");
//                App.logger.error("В загрузочном файле не обнаружена корректная коллекция!");
            } catch (IllegalStateException exception) {
                Outputer.printerror("Непредвиденная ошибка!");
//                App.logger.error("Непредвиденная ошибка!");
                System.exit(0);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } else Outputer.printerror("Системная переменная с загрузочным файлом не найдена!");
        return new LinkedList<StudyGroup>();
    }

    @Override
    public String toString() {
        String string = "FileManager (класс для работы с загрузочным файлом)";
        return string;
    }

}
