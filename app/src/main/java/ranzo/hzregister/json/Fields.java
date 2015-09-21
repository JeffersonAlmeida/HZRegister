package ranzo.hzregister.json;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

public class Fields implements Serializable {

    private List<Field> fieldsList;

    public Fields(String json){
        this.fieldsList = FromJson.toFields(json);
    }

    public List<Field> getList(){
        return this.fieldsList;
    }

    private static class FromJson {
        private static final String OBJECT_NAME = "fields";
        private static List<Field> toFields(String jsonString){
            JsonParser jsonParser = new JsonParser();
            JsonObject jsonObject = (JsonObject) jsonParser.parse(jsonString);
            JsonArray jsonArray = jsonObject.getAsJsonArray(OBJECT_NAME);
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Field>>(){}.getType();
            return gson.fromJson(jsonArray, listType);
        }
    }
}
