package nio.demo.socket.programming.chat.bot;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class NIOChatClient2 {

    public static void main(String[] args) throws IOException {

        InetSocketAddress addr = new InetSocketAddress("localhost", 1111);

        //  selectable channel for stream-oriented connecting sockets
        SocketChannel client = SocketChannel.open(addr);

        log("Connecting to Server on port 1111...");
        log("Type your message...");

        for (;;) {

            Scanner sc= new Scanner(System.in);
            System.out.print("You: ");
            String msg = sc.nextLine();
            if(msg.equalsIgnoreCase("stop")) break;

            byte[] msgBytes = msg.getBytes();
            ByteBuffer buffer = ByteBuffer.wrap(msgBytes);
            client.write(buffer);

            buffer.clear();

            buffer = ByteBuffer.allocate(256);
            client.read(buffer);
            String result = new String(buffer.array()).trim();

            log("Server: " + result);
            buffer.clear();

            if(result.equalsIgnoreCase("stop")) break;
        }

        // close(): Closes this channel.
        client.close();
    }

    private static void log(String str) {

        System.out.println(str);
    }
}
