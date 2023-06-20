package nio.demo.socket.programming.chat.server;


import nio.demo.socket.programming.chat.server.model.Conversation;
import nio.demo.socket.programming.chat.server.model.Device;
import nio.demo.socket.programming.chat.server.utils.SerdeUtils;

import java.io.IOException;
import java.io.StreamCorruptedException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.*;

public class NIOChatServer {
    private static final Map<String, List<Device>> clients= new HashMap<>();

    @SuppressWarnings("unused")
    public static void main(String[] args) throws IOException, ClassNotFoundException {

        // Selector: A multiplexor of SelectableChannel objects.
        Selector selector = Selector.open();

        // ServerSocketChannel: A selectable channel for stream-oriented listening sockets.
        ServerSocketChannel socket = ServerSocketChannel.open();

        InetSocketAddress addr = new InetSocketAddress("localhost", 1111);

        // Binds the channel's socket to a local address and configures the socket to listen for connections
        socket.bind(addr);

        // Adjusts this channel's blocking mode.
        socket.configureBlocking(false);

        int ops = socket.validOps();

        // SelectionKey: A token representing the registration of a SelectableChannel with a Selector.
        SelectionKey selectKy = socket.register(selector, ops);

        // Keep server running
        for(;;) {

            // Selects a set of keys whose corresponding channels are ready for I/O operations
            selector.select();

            // token representing the registration of a SelectableChannel with a Selector
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = keys.iterator();

            while (iterator.hasNext()) {
                SelectionKey myKey = iterator.next();

                // Tests whether this key's channel is ready to accept a new socket connection
                if (myKey.isAcceptable()) {
                    SocketChannel client = socket.accept();

                    // Adjusts this channel's blocking mode to false
                    client.configureBlocking(false);

                    // Operation-set bit for read operations
                    client.register(selector, SelectionKey.OP_READ);

                    log("Connection Accepted: " + client.getLocalAddress());

                    // Tests whether this key's channel is ready for reading
                } else if (myKey.isReadable()) {

                    SocketChannel client = (SocketChannel) myKey.channel();

                    ByteBuffer buffer = ByteBuffer.allocate(256);
                    client.read(buffer);
                    Conversation conversation;
                    try {
                        conversation = (Conversation) SerdeUtils.deserialize(buffer.array());
                    } catch(StreamCorruptedException ignored){
                        continue;
                    }

                    if(conversation.getMsg() == null && conversation.getTo() == null) {
                        //Registration
                        clients.put(conversation.getFrom(), List.of(new Device(client)));
                        System.out.println("Registered: " + conversation.getFrom() + " => " + clients.get(conversation.getFrom()));
                        continue;
                    }

                    System.out.println("Received conversation: " + conversation);
                    SocketChannel receiverChannel = clients.get(conversation.getTo()).get(0).getClient();

                    //forward the conversation
                    byte[] conversationBytes = buffer.array();
                    receiverChannel.write(ByteBuffer.wrap(conversationBytes));
                    System.out.println("Forwarded to: " + conversation.getTo() + " => " + clients.get(conversation.getTo()));
                }
                iterator.remove();
            }
        }
    }

    private static void log(String str) {

        System.out.println(str);
    }
}
