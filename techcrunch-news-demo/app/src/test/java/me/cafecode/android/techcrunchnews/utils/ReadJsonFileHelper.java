package me.cafecode.android.techcrunchnews.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Natthawut Hemathulin on 5/23/2017 AD.
 * Email: natthawut1991@gmail.com
 */

public class ReadJsonFileHelper {

    public <T> T getJsonToMock( String fileName, Class<T> className ){
        String json = getJsonFromResources( fileName );
        Gson gson = new GsonBuilder().create();
        return gson.fromJson( json, className );
    }

    public String getJsonFromResources(String fileName) {
        BufferedReader reader = null;
        String mLineResult = "";
        try {
            reader = new BufferedReader(
                    new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream(fileName), "UTF-8"));

            // do reading, usually loop until end of file reading
            String mLine = null;
            while ((mLine = reader.readLine()) != null) {
                mLineResult += mLine;
            }
        } catch (IOException e) {
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                }
            }
        }
        return mLineResult;
    }

}
