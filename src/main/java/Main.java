
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        boolean validationOfTestCaseStructure = JsonHandler.validateTestCaseStructure("TestcaseStructure.json");
        boolean validationOfValues = JsonHandler.validateValues("Values.json");
        if (!validationOfTestCaseStructure || !validationOfValues) {
            JsonHandler.writeErrorJson();
            return;
        }

        JSONObject testcaseStructure = JsonHandler.readTestCaseStructure("TestcaseStructure.json");
        JSONArray values = JsonHandler.readValues("Values.json");
        JSONObject structureWithValues = combineStructureAndValues(testcaseStructure, values);
        JsonHandler.writeStructureWithValues(structureWithValues);
    }

    public static JSONObject combineStructureAndValues(JSONObject testcaseStructure, JSONArray values) {
        /*for (int i = 0; i < object.length(); i++) {
            JSONObject currentArrayElement = (JSONObject) object.get(i);
            try {
                currentArrayElement.get("values");
            } catch (JSONException e) {

            }

        }*/
        JSONObject structureWithValues = new JSONObject();
        return structureWithValues;
    }
}

