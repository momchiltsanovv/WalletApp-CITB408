package org.utils;

import java.io.*;

public class SerializationUtils {

    public static void serialize(Object object, String fileName) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeObject(object);
        }
    }

    public static <T> T deserialize(String fileName, Class<T> clazz) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            Object obj = in.readObject();
            return clazz.cast(obj);
        }
    }
}
