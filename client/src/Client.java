package src;

import main.interaction.Request;
import main.interaction.Response;
import main.utility.Outputer;
import src.utility.UserHandler;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Set;

public class Client {

    private String host;

    private int port;

    private UserHandler userHandler;

    private DatagramChannel datagramChannel;

    private SocketAddress address;

    private ByteBuffer byteBuffer = ByteBuffer.allocate(16384);

    private Selector selector;


    public Client(String host, int port, UserHandler userHandler) {
        this.host = host;
        this.port = port;
        this.userHandler = userHandler;
    }

    public void run(){
        try {
            datagramChannel = DatagramChannel.open();
            address = new InetSocketAddress("localhost", port);
            datagramChannel.connect(address);
            datagramChannel.configureBlocking(false);
            selector = Selector.open();
            datagramChannel.register(selector, SelectionKey.OP_WRITE);
            Request requestToServer = null;
            Response serverResponse = null;
            do {
                requestToServer = serverResponse != null ? userHandler.handle(serverResponse.getResponseCode()) :
                        userHandler.handle(null);
                if (requestToServer.isEmpty()) continue;
                send(requestToServer);
                serverResponse = receive();
                Outputer.println(serverResponse.getResponseBody());
            } while (!requestToServer.getCommandName().equals("exit"));
        } catch (IOException | ClassNotFoundException exception) {
            Outputer.println("Произошла ошибка при работе с сервером!");
            System.exit(0);
        }

    }

    private void makeByteBufferToRequest(Request request) throws IOException {
        byteBuffer.put(serialize(request));
        byteBuffer.flip();
    }

    private byte[] serialize(Request request) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(request);
        byte[] buffer = byteArrayOutputStream.toByteArray();
        objectOutputStream.flush();
        byteArrayOutputStream.flush();
        byteArrayOutputStream.close();
        objectOutputStream.close();
        return buffer;
    }

    private Response deserialize() throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteBuffer.array());
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        Response response = (Response) objectInputStream.readObject();
        byteArrayInputStream.close();
        objectInputStream.close();
        byteBuffer.clear();
        return response;
    }

    private void send(Request request) throws IOException {
        makeByteBufferToRequest(request);
        DatagramChannel channel = null;
        while (channel == null) {
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            for (SelectionKey key : selectionKeys) {
                if (key.isWritable()) {
                    channel = (DatagramChannel) key.channel();
                    channel.write(byteBuffer);
                    channel.register(selector, SelectionKey.OP_READ);
                    break;
                }
            }
        }
        byteBuffer.clear();
    }

    private Response receive() throws IOException, ClassNotFoundException {
        DatagramChannel channel = null;
        while (channel == null) {
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            for (SelectionKey key : selectionKeys) {
                if (key.isReadable()) {
                    channel = (DatagramChannel) key.channel();
                    channel.read(byteBuffer);
                    byteBuffer.flip();
                    channel.register(selector, SelectionKey.OP_WRITE);
                    break;
                }
            }
        }
        return deserialize();
    }
}