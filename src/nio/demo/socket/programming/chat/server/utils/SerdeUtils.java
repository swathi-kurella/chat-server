package nio.demo.socket.programming.chat.server.utils;

import java.io.*;

public class SerdeUtils {
    public static byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = new ObjectOutputStream(bos);
        out.writeObject(obj);
        byte[] b = bos.toByteArray();
        out.close();
        bos.close();
        return b;
    }
    public static Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        ObjectInput in = new ObjectInputStream(bis);
        return in.readObject();
    }
}
