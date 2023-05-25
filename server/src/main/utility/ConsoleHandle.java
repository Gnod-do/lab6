package main.utility;

import java.util.Scanner;

public class ConsoleHandle implements Runnable{
    private CollectionManager collectionManager;

    public ConsoleHandle(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()){
            if (scanner.next().trim().equals("save")) {
                Outputer.println("No da su dung save");
                collectionManager.saveCollection();
            }
        }
    }
}
