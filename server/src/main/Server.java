package main;

import main.exceptions.OpeningServerSocketException;
import main.interaction.Request;
import main.interaction.Response;
import main.interaction.ResponseCode;
import main.utility.Outputer;
import main.utility.RequestHandler;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Server {
    private int port;
    private DatagramSocket serverSocket;
    private RequestHandler requestHandler;

    public Server(int port, RequestHandler requestHandler) {
        this.port = port;
        this.requestHandler = requestHandler;
    }

    /**
     * Begins server operation.
     */

    public void run(){
        try {
            openServerSocket();
            boolean processingStatus = true;
            while (processingStatus) {
                processingStatus = processClientRequest();
            }
            stop();
        } catch (OpeningServerSocketException exception) {
            Outputer.printerror("Сервер не может быть запущен!");
        }
    }

    /**
     * Finishes server operation.
     */

    public void stop() {
        if (serverSocket == null) return;
        serverSocket.close();
        Outputer.println("Работа сервера успешно завершена.");
    }

    /**
     * Open server socket.
     */

    private void openServerSocket() throws OpeningServerSocketException {
        try {
            serverSocket = new DatagramSocket(port);
        } catch (IllegalArgumentException exception) {
            Outputer.printerror("Порт '" + port + "' находится за пределами возможных значений!");
            throw new OpeningServerSocketException();
        } catch (IOException exception) {
            Outputer.printerror("Произошла ошибка при попытке использовать порт '" + port + "'!");
            throw new OpeningServerSocketException();
        }
    }

    /**
     * The process of receiving a request from a client.
     */

    private boolean processClientRequest() {
        byte[] buffer = new byte[65507];
        DatagramPacket requestPacket = new DatagramPacket(buffer, buffer.length);
        Request userRequest = null;
        Response responseToUser = null;
        try {
            serverSocket.receive(requestPacket);
            userRequest = (Request) deserialize(requestPacket.getData());
            Outputer.println(userRequest.getCommandName());
            responseToUser = requestHandler.handle(userRequest);
            byte[] responseData = serialize(responseToUser);
            DatagramPacket responsePacket = new DatagramPacket(responseData, responseData.length, requestPacket.getAddress(), requestPacket.getPort());
            serverSocket.send(responsePacket);
            if (responseToUser.getResponseCode() == ResponseCode.SERVER_EXIT) {
                return false;
            }
        } catch (ClassNotFoundException exception) {
            Outputer.printerror("Произошла ошибка при чтении полученных данных!");
        } catch (IOException exception) {
            if (userRequest == null) {
                Outputer.printerror("Непредвиденный разрыв соединения с клиентом!");
            } else {
                Outputer.println("Клиент успешно отключен от сервера!");
            }
        }
        return true;
    }

    /**
     * Serialize object to byte array.
     */

    private byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(obj);
        return byteArrayOutputStream.toByteArray();
    }

    /**
     * Deserialize byte array to object.
     */

    private Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        return objectInputStream.readObject();
    }
}