package src;

import main.exceptions.OpeningClientSocketException;
import main.interaction.Request;
import main.interaction.Response;
import main.interaction.ResponseCode;
import main.utility.Outputer;
import src.utility.UserHandler;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class Client {
    private String host;
    private int port;
    private DatagramChannel clientSocket;

    private UserHandler userHandler;

    public Client(String host, int port, UserHandler userHandler) {
        this.host = host;
        this.port = port;
        this.userHandler = userHandler;
    }

    /**
     * Starts client operation.
     */

    public void run() {
        try {
            openClientSocket();
            sendRequest();
        } catch (OpeningClientSocketException exception) {
            Outputer.printerror("Не удалось установить соединение с сервером!");
        } finally {
            stop();
        }

    }

    /**
     * Finishes client operation.
     */

    private void stop() {
        if (clientSocket == null) return;
        try {
            clientSocket.close();
            Outputer.println("Работа клиента успешно завершена.");
        } catch (IOException exception) {
            Outputer.printerror("Произошла ошибка при завершении работы клиента!");
        }
    }

    /**
     * Open client socket.
     */

    private void openClientSocket() throws OpeningClientSocketException {
        try {
            clientSocket = DatagramChannel.open();
        } catch (IOException exception) {
            Outputer.printerror("Произошла ошибка при открытии канала клиента!");
            throw new OpeningClientSocketException();
        }
    }

    /**
     * Sending requests to server.
     */

    private void sendRequest() {
        Request requestToServer = null;
        Response serverResponse = null;
        while (true){
            try {
                requestToServer = serverResponse != null ? userHandler.handle(serverResponse.getResponseCode()) :
                        userHandler.handle(null);
                if (requestToServer.isEmpty()) continue;
                byte[] requestData = serialize(requestToServer);
                ByteBuffer requestBuffer = ByteBuffer.wrap(requestData);
                InetSocketAddress serverAddress = new InetSocketAddress(host,port);
                clientSocket.send(requestBuffer, serverAddress);
                if (requestToServer.getCommandName().equals("exit")) {
                    break;
                }

                byte[] buffer = new byte[65507];
                ByteBuffer responseBuffer = ByteBuffer.wrap(buffer);
                clientSocket.receive(responseBuffer);
                serverResponse = (Response) deserialize(buffer);
                Outputer.println(serverResponse.getResponseBody());
                if (serverResponse.getResponseCode() == ResponseCode.SERVER_EXIT) {
                    break;
                }
            } catch (ClassNotFoundException | InvalidClassException | NotSerializableException exception) {
                Outputer.printerror("Произошла ошибка при отправке данных на сервер!");
            } catch (IOException exception) {
                Outputer.printerror("Произошла ошибка при попытке отправки данных на сервер!");
            }

        }

    }

    /**
     * Serialize object to byte array.
     */

    private byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(obj);
        objectOutputStream.flush();
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