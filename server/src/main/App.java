package main;

import main.commands.*;
import main.utility.CollectionFileManager;
import main.utility.CollectionManager;
import main.utility.CommandManager;
import main.utility.RequestHandler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Main server class. Creates all server instances.
 *
 * Do Van Dong- P3125.
 */

public class App {
    public static final int PORT = 2048;
    public static final int CONNECTION_TIMEOUT = 60 * 1000;
    public static final String ENV_VARIABLE = "LABA";

//    public static Logger logger = LoggerFactory.getLogger("ServerLogger");


    public static void main(String[] args) throws IOException, ClassNotFoundException {

        String filePath = System.getenv("LABA");
        File file = new File(filePath);
        if (file.exists() && file.isFile()){
            if (file.length() == 0) {
                try (FileWriter fileWriter = new FileWriter(file)) {
                    fileWriter.write("[]");
                    fileWriter.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        CollectionFileManager collectionFileManager = new CollectionFileManager(ENV_VARIABLE);
        CollectionManager collectionManager = new CollectionManager(collectionFileManager);
        CommandManager commandManager = new CommandManager(
                new HelpCommand(),
                new InfoCommand(collectionManager),
                new ShowCommand(collectionManager),
                new AddCommand(collectionManager),
                new UpdateCommand(collectionManager),
                new RemoveByIdCommand(collectionManager),
                new ClearCommand(collectionManager),

                new ExitCommand(),
                new ExecuteScriptCommand(),
                new RemoveAtIndexCommand(collectionManager),
                new SortCommand(collectionManager),
                new HistoryCommand(),
                new SumOfTransferredStudentsCommand(collectionManager),
                new MinBySemesterEnumCommand(collectionManager),
                new GroupCountingByCoordinatesCommand(collectionManager)

        );

        //                new SaveCommand(collectionManager),
//        new ServerExitCommand()


//        Thread thread = new Thread(new ConsoleHandle(collectionManager));
//        thread.setDaemon(true);
//        thread.start();
        RequestHandler requestHandler = new RequestHandler(commandManager);
        Server server = new Server(PORT, requestHandler, collectionManager);
        server.run();
    }
}
