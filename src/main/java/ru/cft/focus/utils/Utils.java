package ru.cft.focus.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import ru.cft.focus.model.Credentials;
import ru.cft.focus.model.Issue;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class Utils {

    public static String getResourceAsString(final String filePath) {
        try(InputStream inputStream = Utils.class.getResourceAsStream(filePath)){
            return IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Issue jsonToModelIssue(String json){
        ObjectMapper mapper = new ObjectMapper();
        Issue result = null;
        try {
            result = mapper.readValue(json, Issue.class);
        } catch (IOException e){e.printStackTrace();}
        return result;
    }

    public static Credentials jsonToModelCredentials(String json){
        ObjectMapper mapper = new ObjectMapper();
        Credentials result = null;
        try {
            result = mapper.readValue(json, Credentials.class);
        } catch (IOException e){e.printStackTrace();}
        return result;
    }
}
