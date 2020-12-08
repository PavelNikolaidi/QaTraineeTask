import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonHandler {
    /**
     * Reads JSONArray of params from TestCaseStructure.json file.
     * @param pathToFile path to TestCaseStructure.json, String
     * @return JSONArray
     * @throws IOException
     */
    public static JSONObject readTestCaseStructure(String pathToFile) throws IOException {

        String testCaseStructure;
        testCaseStructure = new String(Files.readAllBytes(Paths.get(pathToFile)));
        JSONObject jsonObject = new JSONObject(testCaseStructure);
        return jsonObject;
    }

    /**
     * Reads JSONArray of values from Values.json file.
     * @param pathToFile path to TestCaseStructure.json, String
     * @return JSONArray
     * @throws IOException, JSONException
     */
    public static JSONArray readValues(String pathToFile) throws IOException {
        String Values;
        Values = new String(Files.readAllBytes(Paths.get(pathToFile)));
        JSONArray jsonArray = new JSONObject(Values).getJSONArray("values");
        return jsonArray;
    }

    public static boolean validateTestCaseStructure(String pathToFile) throws IOException {
        String jsonSchemaString = new String(Files.readAllBytes(Paths.get("input_validation/TestcaseStructure-schema.json")));
        JSONObject jsonSchema = new JSONObject(jsonSchemaString);
        String testCasesStructureString = new String(Files.readAllBytes(Paths.get(pathToFile)));
        JSONObject testCaseStructure = new JSONObject(testCasesStructureString);

        Schema schema = SchemaLoader.load(jsonSchema);
        try {
            schema.validate(testCaseStructure);
        } catch (ValidationException e) {
            return false;
        }
        return true;

    }

    public static boolean validateValues(String pathToFile) throws IOException {
        String jsonSchemaString = new String(Files.readAllBytes(Paths.get("input_validation/Values-schema.json")));
        JSONObject jsonSchema = new JSONObject(jsonSchemaString);
        String valuesString = new String(Files.readAllBytes(Paths.get(pathToFile)));
        JSONObject values = new JSONObject(valuesString);

        Schema schema = SchemaLoader.load(jsonSchema);
        try {
            schema.validate(values);
        } catch (ValidationException e) {
            return false;
        }
        return true;
    }

    public static void writeStructureWithValues(JSONObject structureWithValues) {
        System.err.println("Implement Me");
    }

    public static void writeErrorJson() {
        System.err.println("Implement Me");
    }
}
