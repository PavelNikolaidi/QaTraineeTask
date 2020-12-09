import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Combinator {

    /**
     * Applies values from JSONArray on testCaseStructure JSONObject.
     * @param testCaseStructure
     * @param values
     * @return resulting JSONObject structureWithValues
     */
    public static JSONObject combineStructureAndValues(JSONObject testCaseStructure, JSONArray values) {
        JSONObject structureWithValues = new JSONObject();
        for (int i = 0; i < testCaseStructure.getJSONArray("params").length(); i++) {
            JSONObject currentArrayElement = testCaseStructure.getJSONArray("params").getJSONObject(i);
            try {
                currentArrayElement.get("values");
            } catch (JSONException jsonException) {
                if (jsonException.getMessage().equalsIgnoreCase("JSONObject[\"values\"] not found.")) {
                    //When "values" array not found, setting value from Values.json.
                    for (int j = 0; j < values.length(); j++) {
                        JSONObject currentValuesArrayElement = values.getJSONObject(j);
                        if (currentValuesArrayElement.getInt("id") == currentArrayElement.getInt("id")) {
                            currentArrayElement.put("value", currentValuesArrayElement.get("value"));
                            break;
                        }
                    }
                    structureWithValues = structureWithValues.append("params", currentArrayElement);
                    continue;
                }
            }
            /* When "values" array found, selecting value with "id" = "value" from Values.json. If there's no
            corresponding value, leaving empty.*/
            for (int j = 0; j < values.length(); j++) {
                JSONObject currentValuesArrayElement = values.getJSONObject(j);
                if (currentArrayElement.getInt("id") == currentValuesArrayElement.getInt("id")) {
                    for (int k = 0; k < currentArrayElement.getJSONArray("values").length(); k++) {
                        if (currentArrayElement.getJSONArray("values").getJSONObject(k).getInt("id") == currentValuesArrayElement.getInt("value")) {
                            currentArrayElement.put("value", currentArrayElement.getJSONArray("values").getJSONObject(k).get("title"));
                            break;
                        }
                    }
                    structureWithValues = structureWithValues.append("params", currentArrayElement);
                    break;
                }
            }

        }
        return structureWithValues;
    }
}
