package config;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Config {

    public HashMap<Object, Object> values = new HashMap<>();
    public File file;

    public Config(File file) {
        this.file = file;
    }

    public boolean checkFile() {
        try {
            File parentFolder = file.getParentFile();
            if(parentFolder != null)
                if(!parentFolder.exists())
                    parentFolder.mkdirs();

            if(!file.exists())
                if(!file.createNewFile())
                    return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Config load() {
        if(!checkFile()) return this;
        byte[] bytes = ByteFileWriter.readBytesFromFile(file);
        if(bytes.length != 0)
            values = ByteConversion.toMap(bytes);
        return this;
    }

    public Config save() {
        if(!checkFile()) return this;
        ByteFileWriter.writeBytesToFile(file, ByteConversion.toByteArray(values));
        return this;
    }

    public Config registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::save));
        return this;
    }

    public void set(Object key, Object value) {
        values.put(key, value);
    }

    public void remove(Object key) {
        values.remove(key);
    }

    public <T> T get(Object key) {
        return get(key, null);
    }

    public <T> T get(Object key, T default_value) {
        return values.containsKey(key) ? (T) values.get(key) : default_value;
    }

    public <T> T getAs(Object key, Class<T> clazz) {
        return getAs(key, null, clazz);
    }

    public <T> T getAs(Object key, T default_value, Class<T> clazz) {
        if(values.containsKey(key)) {
            try {
                T val = clazz.cast(values.get(key));
                return val;
            } catch (ClassCastException ignored) { }
        }
        return default_value;
    }

    public <T> T get_s(Object key, Class<?> clazz) {
        return get_s(key, null, clazz);
    }

    public <T> T get_s(Object key, T default_value, Class<?> clazz) {
        return valid(key, clazz) ? (T) values.get(key) : default_value;
    }

    public boolean valid(Object key, Class<?> clazz) {
        if(values.containsKey(key)) {
            try {
                clazz.cast(values.get(key));
                return true;
            } catch (ClassCastException ignored) { }
        }
        return false;
    }

    public boolean contains(Object key) {
        return values.containsKey(key);
    }

    public static class ByteConversion {

        public static byte[] toByteArray(Object object) {
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                objectOutputStream.writeObject(object);
                objectOutputStream.close();
                byte[] objectBytes = byteArrayOutputStream.toByteArray();
                byteArrayOutputStream.close();
                return objectBytes;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return new byte[0];
        }

        public static HashMap<Object, Object> toMap(byte[] bytes) {
            try {
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
                ObjectInput objectInput = new ObjectInputStream(byteArrayInputStream);
                Object object = objectInput.readObject();

                objectInput.close();
                byteArrayInputStream.close();

                if(valid(object, HashMap.class)) {
                    return (HashMap<Object, Object>) object;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        public static boolean valid(Object object, Class<?> clazz) {
            try {
                clazz.cast(object);
                return true;
            } catch (ClassCastException ignored) { }
            return false;
        }

    }

    public static class ByteFileWriter {

        public static void writeBytesToFile(File file, byte[] bytes) {
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(bytes);
                fileOutputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public static byte[] readBytesFromFile(File file) {
            try {
                FileInputStream fileInputStream = new FileInputStream(file);

                ArrayList<Byte> bytes = new ArrayList<>();
                int read;
                while((read = fileInputStream.read()) != -1) bytes.add((byte)read);

                byte[] byteArray = new byte[bytes.size()];
                for(int i = 0 ; i < bytes.size() ; i++) byteArray[i] = bytes.get(i);

                bytes.clear();

                fileInputStream.close();
                return byteArray;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return new byte[0];
        }

    }

}