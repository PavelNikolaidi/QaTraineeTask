import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonHandler {

    /**
     * Reads JSONArray of params from TestCaseStructure.json file.
     *
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
     *
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

    /**
     * Validates TestCaseStructure.json file against pre-defined JSON Schema.
     * @param pathToFile
     * @return
     * @throws IOException if TestCaseStructure.json is not available
     */
    public static boolean validateTestCaseStructure(String pathToFile) throws IOException {
        String jsonSchemaString = "";
        try {
            jsonSchemaString = new String(Files.readAllBytes(Paths.get("input_validation/TestcaseStructure-schema.json")));
        } catch (IOException e) {
            System.err.println("Can't read input_validation/TestcaseStructure-schema.json");
            e.printStackTrace();
            throw e;
        }
        JSONObject jsonSchema = new JSONObject(jsonSchemaString);
        String testCasesStructureString = "";
        try {
            testCasesStructureString = new String(Files.readAllBytes(Paths.get(pathToFile)));
        } catch (IOException e) {
            System.err.println("Can't read " + pathToFile);
            e.printStackTrace();
            throw e;
        }
        JSONObject testCaseStructure = new JSONObject();
        try {
            testCaseStructure = new JSONObject(testCasesStructureString);
        } catch (JSONException ignored) {}
        Schema schema = SchemaLoader.load(jsonSchema);
        try {
            schema.validate(testCaseStructure);
        } catch (ValidationException e) {
            return false;
        }
        return true;

    }

    /**
     * Validates Values.json file against pre-defined JSON Schema.
     * @param pathToFile
     * @return
     * @throws IOException if Values.json is not available
     */
    public static boolean validateValues(String pathToFile) throws IOException {
        String jsonSchemaString = "";
        try {
            jsonSchemaString = new String(Files.readAllBytes(Paths.get("input_validation/Values-schema.json")));
        } catch (IOException e) {
            System.err.println("Can't read input_validation/Values-schema.json");
            e.printStackTrace();
            throw e;
        }
        JSONObject jsonSchema = new JSONObject(jsonSchemaString);
        String valuesString = "";
        try {
            valuesString = new String(Files.readAllBytes(Paths.get(pathToFile)));
        } catch (IOException e) {
            System.err.println("Can't read " + pathToFile);
            e.printStackTrace();
            throw e;
        }
        JSONObject values = new JSONObject();
        try {
            values = new JSONObject(valuesString);
        } catch (JSONException ignored) {}
        Schema schema = SchemaLoader.load(jsonSchema);
        try {
            schema.validate(values);
        } catch (ValidationException e) {
            return false;
        }
        return true;
    }

    /**
     * Writes JSONObject structureWithValues to StructureWithValues.json file.
     * @param structureWithValues
     */
    public static void writeStructureWithValues(JSONObject structureWithValues) {

        String structureWithValuesString = structureWithValues.toString(2);
        if (Files.exists(Paths.get("StructureWithValues.json"))) {
            try {
                Files.deleteIfExists(Paths.get("StructureWithValues.json.backup"));
            } catch (IOException e) {
                System.err.println("Can't delete old backup file");
                e.printStackTrace();
            }
            try {
                Files.move(Paths.get("StructureWithValues.json"), Paths.get("StructureWithValues.json.backup"));
            } catch (IOException e) {
                System.err.println("Can't create backup file");
                e.printStackTrace();
            }
        }
        try {
            File outputFile = new File("StructureWithValues.json");
            if (outputFile.createNewFile()) {
                FileWriter writer = new FileWriter("StructureWithValues.json");
                writer.write(structureWithValuesString);
                writer.close();
                System.out.println("File created: " + outputFile.getName());
            } else {
                System.err.println("File already exists.");
            }
        } catch (IOException e) {
            System.err.println("Can't create output file.");
            e.printStackTrace();
        }
    }

    /**
     * Generates and writes Error.json file.
     */
    public static void writeErrorJson() {
        if (Files.exists(Paths.get("Error.json"))) {
            try {
                Files.deleteIfExists(Paths.get("Error.json"));
            } catch (IOException e) {
                System.err.println("Can't delete old Error.json file");
                e.printStackTrace();
            }
        }

        JSONObject error = new JSONObject();
        JSONObject message = new JSONObject();
        message.put("message","Input files are incorrect.");
        error.put("error", message);
        String errorString = error.toString(2);
        try {
            File outputFile = new File("Error.json");
            if (outputFile.createNewFile()) {
                FileWriter writer = new FileWriter("Error.json");
                writer.write(errorString);
                writer.close();
                System.out.println("File created: " + outputFile.getName());
            } else {
                System.err.println("File already exists.");
            }
        } catch (IOException e) {
            System.err.println("Can't create output file.");
            e.printStackTrace();
        }
    }
}
