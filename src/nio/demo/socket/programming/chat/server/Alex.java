package nio.demo.socket.programming.chat.server;

import nio.demo.socket.programming.chat.server.model.Conversation;
import nio.demo.socket.programming.chat.server.utils.SerdeUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class Alex {
    public Alex() {
    }

    private static final String CLIENT_NAME = "Alex";

    public static void main(String[] args) throws IOException {

        InetSocketAddress addr = new InetSocketAddress("localhost", 1111);

        //  selectable channel for stream-oriented connecting sockets
        SocketChannel client = SocketChannel.open(addr);
        log("Connected to Chat Server on port 1111...");
        Conversation registration = new Conversation(CLIENT_NAME);
        byte[] registrationBytes = SerdeUtils.serialize(registration);
        ByteBuffer buffer = ByteBuffer.wrap(registrationBytes);
        client.write(buffer);
        buffer.clear();
        log("User Registration completed");
        Runnable reader = new ReaderThread(client);
        Thread readerThread = new Thread(reader);
        readerThread.start();

        Runnable writer = new WriterThread(client);
        Thread writerThread = new Thread(writer);
        writerThread.start();
    }


    public static class WriterThread implements Runnable {

        SocketChannel client;
        public WriterThread(SocketChannel client) {
            this.client = client;
        }

        public void run() {
            Scanner sc = new Scanner(System.in);
            for (; ; ) {
                if(sc.hasNext()) {
                    String entry = sc.nextLine();
                    String to= entry.split(":")[0];
                    String msg = entry.split(":")[1];
                    if (to.equalsIgnoreCase("stop")) break;
                    Conversation com = new Conversation(CLIENT_NAME, to, msg);
                    //System.out.println(com);
                    byte[] registrationBytes;
                    try {
                        registrationBytes = SerdeUtils.serialize(com);
                        ByteBuffer buffer = ByteBuffer.wrap(registrationBytes);
                        client.write(buffer);
                        buffer.clear();
                    } catch (IOException e) {
                        log("Error while serializing the message: " + e.getMessage());
                    }
                }
            }
            try {
                client.close();
            } catch (IOException e) {
                log("Error while closing connection: " + e.getMessage());
            }
        }

    }

    public static class ReaderThread implements Runnable {

        SocketChannel client;
        public ReaderThread(SocketChannel client) {
            this.client = client;
        }

        public void run() {
            for (; ; ) {
                ByteBuffer readBuffer = ByteBuffer.allocate(256);
                Conversation result = null;
                try {
                    client.read(readBuffer);
                    if(readBuffer.array().length != 0) {
                        result = (Conversation) SerdeUtils.deserialize(readBuffer.array());
                        log(result.getFrom() + ": " + result.getMsg());
                    }

                } catch (IOException e) {
                    log("IOException: " + e.getMessage());
                } catch (ClassNotFoundException e) {
                    log("ClassNotFoundException: " + e.getMessage());
                }
            }
        }

    }

    private static void log(String str) {

        System.out.println(str);
    }


}
