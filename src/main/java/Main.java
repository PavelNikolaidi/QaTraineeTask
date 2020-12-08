
import org.json.JSONArray;
import org.json.JSONException;
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
        JSONObject structureWithValues = new JSONObject();
        for (int i = 0; i < testcaseStructure.getJSONArray("params").length(); i++) {
            JSONObject currentArrayElement = testcaseStructure.getJSONArray("params").getJSONObject(i);
            try {
                currentArrayElement.get("values");
            } catch (JSONException jsonException) {
                if (jsonException.getMessage().equalsIgnoreCase("JSONObject[\"values\"] not found.")) {
                    System.out.println("Values array not found");

                    for (int j = 0; j < values.length(); j++) {
                        JSONObject currentValuesArrayElement = values.getJSONObject(j);
                        if (currentValuesArrayElement.getInt("id") == currentArrayElement.getInt("id")) {
                            currentArrayElement.put("value", currentValuesArrayElement.get("value"));
                        }
                    }
                    structureWithValues = structureWithValues.append("params", currentArrayElement);
                    continue;
                }
            }
            System.out.println("Values array found");

            for (int j = 0; j < values.length(); j++) {
                JSONObject currentValuesArrayElement = values.getJSONObject(j);
                if (currentValuesArrayElement.getInt("id") == currentArrayElement.getInt("id")) {
                    currentArrayElement.put("value", currentValuesArrayElement.get("value"));
                }
            }
            structureWithValues = structureWithValues.put("params", currentArrayElement);
        }
        return structureWithValues;
    }
}

