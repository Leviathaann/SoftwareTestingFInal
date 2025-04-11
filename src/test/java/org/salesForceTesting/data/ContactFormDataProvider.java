package org.salesForceTesting.data;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.testng.annotations.DataProvider;

import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class ContactFormDataProvider {
    private final static char DELIMITER = ',';

    @DataProvider(name = "ContactFormData")
    public static Object[][] getSfContactFormData() {
        try {
            System.out.println("Starting to read CSV data...");

            // path to the csv file
            String csvFilePath = "src/test/resources/ContactFormData.csv";

            // read the csv file
            Reader in = new FileReader(csvFilePath);
            System.out.println("Successfully opened CSV file");

            // parse the csv file using an iterator (most stable way I could get it to work)
            Iterable<CSVRecord> fields = CSVFormat.DEFAULT.withDelimiter(DELIMITER).withFirstRecordAsHeader()
                    .withIgnoreHeaderCase().withTrim().parse(in);

            List<Object[]> testData = new ArrayList<>();
            // Go through each row in the csv file and add it to the test data array list
            for (CSVRecord field : fields) {
                try {
                    String testCaseId = field.get("testCaseId");
                    String firstName = field.get("firstName").isEmpty() ? null : field.get("firstName");
                    String lastName = field.get("lastName").isEmpty() ? null : field.get("lastName");
                    String jobTitle = field.get("jobTitle").isEmpty() ? null : field.get("jobTitle");
                    String email = field.get("email").isEmpty() ? null : field.get("email");
                    String company = field.get("company").isEmpty() ? null : field.get("company");
                    String employeesNumber = field.get("employeesNumber").isEmpty() ? null : field.get("employeesNumber");
                    String phone = field.get("phone").isEmpty() ? null : field.get("phone");
                    String productInterest = field.get("productInterest").isEmpty() ? null : field.get("productInterest");
                    String country = field.get("country").isEmpty() ? null : field.get("country");
                    String state = field.get("state").isEmpty() ? null : field.get("state");
                    String expectedResult = field.get("expectedResult");

                    Object[] collectedData = {testCaseId, firstName, lastName, jobTitle, email, company, employeesNumber, phone, productInterest, country, state, expectedResult};
                    testData.add(collectedData);

                } catch (Exception e) {
                    System.out.println("error reading row from the csv file: " + e.getMessage());
                }
            }

            return testData.toArray(new Object[0][]);

        } catch (Exception e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
            // just return a empty array if there is an error
            return new Object[0][0];
        }
    }
}

