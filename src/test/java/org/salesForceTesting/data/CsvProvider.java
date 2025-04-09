package org.salesForceTesting.data;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.testng.annotations.DataProvider;

import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class CsvProvider {
    private final static char DELIMITER = ',';

    @DataProvider(name = "SalesForceContactForm")
    public static Object[][] getSfContactFormData() {
        try {

            String csvFilePath = "src/test/resources/ContactFormData.csv";
            Reader in = new FileReader(csvFilePath);
            Iterable<CSVRecord> fields = CSVFormat.DEFAULT.withDelimiter(DELIMITER).withFirstRecordAsHeader()
                    .withIgnoreHeaderCase().withTrim().parse(in);

            List<Object[]> testData = new ArrayList<>();


            for (CSVRecord field : fields) {
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
                String expectedResult = field.get("expectedResult");

                Object[] data = {testCaseId, firstName, lastName, jobTitle, email, company, employeesNumber, phone, productInterest, country, expectedResult};
                testData.add(data);
            }

            return testData.toArray(new Object[0][]);

        } catch (Exception e) {
            e.printStackTrace();
            return new Object[1][];
        }
    }
}



