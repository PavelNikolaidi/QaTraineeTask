import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;

public class Tests {
    /*
    Требования к тестируемому приложению:
    1. Генерация корректного файла StructureWithValues.json при предоставлении корректных входных файлов
    2. Генерация файла Error.json при предоставлении некорректных входных файлов
    3. Необходимо заполнить поле value у объектов из массива params из файла TestcaseStructure.json:
         -  при наличии у объекта из из массива params массива со значениями values необходимо выбрать объект из этого
            массива со значением параметра id равным значению поля value соответструющего объекта из Values.json.
            Поле value заполняется значением из поля title выбранного объекта;
         -  При отсутствии объекта со значением параметра id равным значению поля value из Values.json поле оставляется
            пустым
         -  при отсутствии массива values необходимо заполнить поле value значением из поля соответствующего объекта из
            Values.json
     4. Если для объекта с id из из Values.json нет соотвествующего объекта в массиве params в TestcaseStructure.json,
        то значение из этого объета игнорируется
     */

    /**
     * Test case #1: Входные файлы в корректном формате, есть массив values
     * Ожидаемый результат: корректно сформированный файл StructureWithValues.json
     */
    @Test
    public void testCase01() throws IOException {
        int testCaseId = 1;

        copyTestData(testCaseId);
        Main.main(null);

        Assert.assertTrue(isResultJsonAsExpected(testCaseId));
    }

    /**
     * Test case #2: Входные файлы в корректном формате, нет массива values
     * Ожидаемый результат: корректно сформированный файл StructureWithValues.json
     */
    @Test
    public void testCase02() throws IOException {
        int testCaseId = 2;

        copyTestData(testCaseId);
        Main.main(null);

        Assert.assertTrue(isResultJsonAsExpected(testCaseId));
    }

    /**
     * Test case #3: Входные файлы в корректном формате, в массиве params нет объекта с id из Values.json
     * Ожидаемый результат: корректно сформированный файл StructureWithValues.json
     */
    @Test
    public void testCase03() throws IOException {
        int testCaseId = 3;

        copyTestData(testCaseId);
        Main.main(null);

        Assert.assertTrue(isResultJsonAsExpected(testCaseId));
    }

    /**
     * Test case #4: Нет файла TestcaseStructure.json
     * Ожидаемый результат: Сообщение об ошибке:
     * "java.nio.file.NoSuchFileException: TestcaseStructure.json"
     */
    @Test(expectedExceptions = IOException.class)
    public void testCase04() throws IOException {
        int testCaseId = 4;

        Files.deleteIfExists(Paths.get("TestcaseStructure.json"));
        copyTestData(testCaseId);

        Main.main(null);
    }

    /**
     * Test case #5: Нет файла Values.json
     * Ожидаемый результат: Сообщение об ошибке:
     * "java.nio.file.NoSuchFileException: Values.json"
     */
    @Test(expectedExceptions = IOException.class)
    public void testCase05() throws IOException {
        int testCaseId = 5;

        Files.deleteIfExists(Paths.get("Values.json"));
        copyTestData(testCaseId);

        Main.main(null);
    }

    /**
     *  Test case #6: Некорректный синтаксис в файле TestcaseStructure.json
     *  Ожидаемый результат: сформирован файл Error.json
     */
    @Test
    public void testCase06() throws IOException {
        int testCaseId = 6;

        Files.deleteIfExists(Paths.get("Error.json"));
        copyTestData(testCaseId);

        Main.main(null);

        String actualJsonFilePath = "Error.json";
        Assert.assertTrue(isResultJsonAsExpected(actualJsonFilePath, testCaseId));
    }

    /**
     * Test case #7: Нет обязательного поля params в файле TestcaseStructure.json
     * Ожидаемый результат: сформирован файл Error.json
     */
    @Test
    public void testCase07() throws IOException {
        int testCaseId = 7;

        Files.deleteIfExists(Paths.get("Error.json"));
        copyTestData(testCaseId);

        Main.main(null);

        String actualJsonFilePath = "Error.json";
        Assert.assertTrue(isResultJsonAsExpected(actualJsonFilePath, testCaseId));
    }

    /**
     *  Test case #8: Некорректный синтаксис в файле Values.json
     *  Ожидаемый результат: сформирован файл Error.json
     */
    @Test
    public void testCase08() throws IOException {
        int testCaseId = 8;

        Files.deleteIfExists(Paths.get("Error.json"));
        copyTestData(testCaseId);

        Main.main(null);

        String actualJsonFilePath = "Error.json";
        Assert.assertTrue(isResultJsonAsExpected(actualJsonFilePath, testCaseId));
    }
    /**
     * Test case #9: Нет обязательного поля values в файле Values.json
     * Ожидаемый результат: сформирован файл Error.json
     */
    @Test
    public void testCase09() throws IOException {
        int testCaseId = 9;

        Files.deleteIfExists(Paths.get("Error.json"));
        copyTestData(testCaseId);

        Main.main(null);

        String actualJsonFilePath = "Error.json";
        Assert.assertTrue(isResultJsonAsExpected(actualJsonFilePath, testCaseId));
    }

    private void copyTestData(int testCaseId) {
        try {
            Files.deleteIfExists(Paths.get("TestcaseStructure.json"));
            Files.deleteIfExists(Paths.get("Values.json"));
                try {
                    Files.copy(Paths.get(("src/test/resources/TestData/TC" + testCaseId + "/TestcaseStructure.json")),
                            Paths.get("TestcaseStructure.json"));
                } catch (IOException e) {
                    System.err.println("TestcaseStructure.json does not exist.");
                }
                try {
                    Files.copy(Paths.get(("src/test/resources/TestData/TC" + testCaseId + "/Values.json")),
                            Paths.get("Values.json"));
                } catch (IOException e) {
                    System.err.println("Values.json does not exist.");
                }
            } catch (IOException e) {
            System.err.println("Can't copy test data.");
            e.printStackTrace();
        }
    }

    private boolean isResultJsonAsExpected(int testCaseId) {
        String file1 = "";
        String file2 = "";
        try {
            file1 = new String(Files.readAllBytes(Paths.get("StructureWithValues.json")));
        } catch (IOException e) {
            System.err.println("Can't read StructureWithValues.json");
            e.printStackTrace();
        }
        try {
            file2 = new String(Files.readAllBytes(Paths.get("src/test/resources/TestData/TC" + testCaseId + "/ExpectedResult.json")));
        } catch (IOException e) {
            System.err.println("Can't read ExpectedResult.json");
            e.printStackTrace();
        }
        JSONObject actualJson = new JSONObject(file1);
        JSONObject expectedJson = new JSONObject(file2);
        if (actualJson.similar(expectedJson)) {
            return true;
        }
        return false;
    }
    private boolean isResultJsonAsExpected(String actualJsonFilePath, int testCaseId) {
        String file1 = "";
        String file2 = "";
        try {
            file1 = new String(Files.readAllBytes(Paths.get(actualJsonFilePath)));
        } catch (IOException e) {
            System.err.println("Can't read " + actualJsonFilePath + ".");
            e.printStackTrace();
        }
        try {
            file2 = new String(Files.readAllBytes(Paths.get("src/test/resources/TestData/TC" + testCaseId + "/" + actualJsonFilePath)));
        } catch (IOException e) {
            System.err.println("Can't read " + actualJsonFilePath);
            e.printStackTrace();
        }
        JSONObject actualJson = new JSONObject(file1);
        JSONObject expectedJson = new JSONObject(file2);
        if (actualJson.similar(expectedJson)) {
            return true;
        }
        return false;
    }

}
