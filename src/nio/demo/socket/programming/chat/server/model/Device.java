package nio.demo.socket.programming.chat.server.model;

import java.io.IOException;
import java.nio.channels.SocketChannel;

public class Device {
    private String deviceType;

    public SocketChannel getClient() {
        return client;
    }

    public void setClient(SocketChannel client) {
        this.client = client;
        this.deviceType = "pc";
    }

    private SocketChannel client;

    public Device(SocketChannel client, String deviceType) {
        this.client = client;
        this.deviceType = deviceType;
    }

    public Device(SocketChannel client) {
        this.client = client;
        this.deviceType = "pc";
    }

    @Override
    public String toString() {
        try {
            return "Device{" +
                    "deviceType='" + deviceType + '\'' +
                    ", client=" + client.getRemoteAddress() +
                    '}';
        } catch (IOException e) {
            System.out.println("Error while printing toString()");
            return "Device{" +
                    "deviceType='" + deviceType + '\'' +
                    ", client=" + client +
                    '}';
        }
    }
}
