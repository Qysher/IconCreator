package utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ResourceLoader {

    public static InputStream getResource(String path) {
        if(!path.startsWith("/")) path = "/" + path;
        return ResourceLoader.class.getResourceAsStream(path);
    }

    public static String readResourceAsText(String path) {
        StringBuffer text = new StringBuffer();
        try {
            InputStream inputStream = getResource(path);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String currentLine;
            while((currentLine = bufferedReader.readLine()) != null) text.append(currentLine).append("\n");
            bufferedReader.close();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return text.toString();
    }

}
