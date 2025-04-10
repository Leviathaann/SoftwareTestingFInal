package org.salesForceTesting.tests;

import org.salesForceTesting.data.ContactFormDataProvider;
import org.salesForceTesting.pages.ContactFormPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.salesForceTesting.testUtils.ScreenshotListener;

import java.util.ArrayList;
import java.util.List;

/*
 * TEST CASES:
 *  testContactFormValidation
 *  testValidFormSubmission
 *  testInvalidEmailFormat
 *  testInvalidPhoneNumberFormat
 *  testInvalidEmployeeDropdown
 */
@Listeners(ScreenshotListener.class)
public class ContactFormPageTest extends BaseTestCore {

    private ContactFormPage contactFormPage;

    @BeforeMethod
    public void setupTest() {
        contactFormPage = new ContactFormPage(getDriver());
        contactFormPage.navigateToContactPage();
    }

    /* This is the data driven test for the contact form
       It will go through the CSV file and test each row
       The CSV file is formatted in the follwing way:
       testCaseId, firstName, lastName, jobTitle, email, company, employeesNumber, phone, productInterest, country, state, expectedResult
    */
    @Test(dataProvider = "ContactFormData", dataProviderClass = ContactFormDataProvider.class)
    public void testContactFormValidation(String testCaseId, String firstName, String lastName, String jobTitle, String email, String company,
                                          String employeesNumber, String phone, String productInterest, String country, String state, String expectedResult) {

        // Log the test case being executed
        System.out.println("Executing test case: " + testCaseId);

        // Fill the form with the provided data from the CSV
        contactFormPage.fillOutForm(firstName, lastName, email, company, phone,
                jobTitle, employeesNumber, productInterest, country, state);

        // Submit the form
        contactFormPage.submitForm();

        // This will go through the expected results and check if the form is displayed correctly
        switch (expectedResult) {
            case "SUCCESS":
                Assert.assertTrue(
                        contactFormPage.isSuccessMessageDisplayed(), "Success message should be displayed for test case: " + testCaseId);
                String successText = contactFormPage.getSuccessMessageText();
                Assert.assertNotNull(successText, "Success message text should not be null for test case: " + testCaseId);
                Assert.assertEquals(successText, "Thank you. We'll be in touch soon.", "Success message text is incorrect for test case: " + testCaseId
                );
                break;

            case "FIRST_NAME_ERROR":
                Assert.assertTrue(contactFormPage.isFirstNameErrorDisplayed(),
                        "First name error should be displayed for test case: " + testCaseId);
                String firstNameError = contactFormPage.getFirstNameErrorMessage();
                Assert.assertEquals(firstNameError, "Enter your first name",
                        "First name error message is incorrect for test case: " + testCaseId);
                break;

            case "LAST_NAME_ERROR":
                Assert.assertTrue(contactFormPage.isLastNameErrorDisplayed(),
                        "Last name error should be displayed for test case: " + testCaseId);
                String lastNameError = contactFormPage.getLastNameErrorMessage();
                Assert.assertEquals(lastNameError, "Enter your last name",
                        "Last name error message is incorrect for test case: " + testCaseId);
                break;

            case "JOB_TITLE_ERROR":
                Assert.assertTrue(contactFormPage.isJobTitleErrorDisplayed(),
                        "Job title error should be displayed for test case: " + testCaseId);
                break;

            case "EMAIL_ERROR":
                Assert.assertTrue(contactFormPage.isEmailErrorDisplayed(),
                        "Email error should be displayed for test case: " + testCaseId);
                String emailError = contactFormPage.getEmailErrorMessage();
                Assert.assertTrue(emailError.contains("valid") || emailError.contains("email"),
                        "Email error should mention valid email format for test case: " + testCaseId);
                break;

            case "COMPANY_ERROR":
                Assert.assertTrue(contactFormPage.isCompanyErrorDisplayed(),
                        "Company error should be displayed for test case: " + testCaseId);
                break;

            case "EMPLOYEES_ERROR":
                Assert.assertTrue(contactFormPage.isEmployeesErrorDisplayed(),
                        "Employees number error should be displayed for test case: " + testCaseId);
                break;

            case "PHONE_ERROR":
                Assert.assertTrue(contactFormPage.isPhoneErrorDisplayed(),
                        "Phone error should be displayed for test case: " + testCaseId);
                break;

            case "PRODUCT_INTEREST_ERROR":
                Assert.assertTrue(contactFormPage.isProductInterestErrorDisplayed(),
                        "Product interest error should be displayed for test case: " + testCaseId);
                break;

            case "COUNTRY_ERROR":
                Assert.assertTrue(contactFormPage.isCountryErrorDisplayed(),
                        "Country error should be displayed for test case: " + testCaseId);
                break;

            default:
                Assert.fail("Unknown expected result: " + expectedResult + " for test case: " + testCaseId);
        }
    }

     // Test case for validation the form submission with valid data.
    @Test
    public void testValidFormSubmission() {
        // A simple log to indicate the test case being executed
        System.out.println("Executing test case: ValidFormSubmission");

        // Fill the form acceptable data according to their types
        contactFormPage.setFirstName("James");
        contactFormPage.setLastName("Smith");
        contactFormPage.setJobTitle("Accountant");
        contactFormPage.setEmail("jamesSmith@example.com");
        contactFormPage.setCompany("BlackRock");
        contactFormPage.setEmployees("201 - 10,000 employees");
        contactFormPage.setPhone("07402182492");
        contactFormPage.setProductInterest("Professional Services");
        contactFormPage.setCountry("United States");
        if (contactFormPage.isStateFieldVisible()) {
            contactFormPage.selectState("Alabama");
        }

        // Submit the form
        contactFormPage.submitForm();

        // Verify the success message
        Assert.assertTrue(
                contactFormPage.isSuccessMessageDisplayed(), "Success message should be displayed for there to be a valid submission!"
        );
        String successText = contactFormPage.getSuccessMessageText();
        Assert.assertEquals(
                successText, "Thank you. We'll be in touch soon.", "Success message text is not right!"
        );


    }

    // Test case for email format validation
    @Test
    public void testInvalidEmailFormat() {

        List<String> invalidEmailFormats = new ArrayList<>();
        invalidEmailFormats.add("email-without-at-or-domain");
        invalidEmailFormats.add("email-without-domain-or-domainName@");
        invalidEmailFormats.add("@withoutuser.com");
        invalidEmailFormats.add("without-domain@domain");
        invalidEmailFormats.add("wihtout-domain-name@.com");
        invalidEmailFormats.add("double..dots@domain.com");
        invalidEmailFormats.add("double-Dots-DomainName@domain..com");

        // This will go through the list of invalid email formats and test each one
        for (String invalidEmail : invalidEmailFormats){
            // go to the contact page
            contactFormPage.navigateToContactPage();


            System.out.println("Testing invalid email format: " + invalidEmail);
            contactFormPage.setFirstName("Jack");
            contactFormPage.setLastName("Ellis");
            contactFormPage.setJobTitle("Software Engineer");
           // Setting the email to an invalid one
            contactFormPage.setEmail(invalidEmail);
            contactFormPage.setCompany("Liberty IT");
            contactFormPage.setEmployees("201 - 10,000 employees");
            contactFormPage.setPhone("07124045248");
            contactFormPage.setProductInterest("Team Productivity");
            if (contactFormPage.isStateFieldVisible()) {
                contactFormPage.selectState("Boston");
            }
            // Submit the form
            contactFormPage.submitForm();

            /* First check if the error message is displayed, then check if the error message is correct
             * The error message should contain the word "valid" or "email" to make sure that we know the email format is the issue
             */
            Assert.assertTrue(contactFormPage.isEmailErrorDisplayed(),
                    "An email error should be displayed showing there is an invalid format entered: " +
                            invalidEmail);


            // Get error message
            String emailError = contactFormPage.getEmailErrorMessage();

            // Check if the error message contains the word "valid" or "email"
            Assert.assertTrue(emailError.contains("valid") || emailError.contains("email"),
                    "The email error message should show an invalid format for: " + invalidEmail);
        }
    }

    // Test case for phone number format validation (will follow a similar style to email format validation)
    @Test
    public void testInvalidPhoneNumberFormat() {
        // List of invalid phone number formats to test
        List<String> invalidPhoneNumbers = new ArrayList<>();
        invalidPhoneNumbers.add("abcdef");
        invalidPhoneNumbers.add("402");
        invalidPhoneNumbers.add("123-456");
        invalidPhoneNumbers.add("123-456-525-2502-5252");
        invalidPhoneNumbers.add("490-abc-1234");
        invalidPhoneNumbers.add("(123)");
        invalidPhoneNumbers.add("[4024124851]");


        // This will go through the list of invalid phoneNumber formats and test each one
        for (String invalidPhone : invalidPhoneNumbers) {
            // go to the contact page
            contactFormPage.navigateToContactPage();

            contactFormPage.setFirstName("Erin");
            contactFormPage.setLastName("keen");
            contactFormPage.setJobTitle("Product Manager");
            contactFormPage.setEmail("ekeen@gmail.com");
            contactFormPage.setCompany("Salesforce");
            contactFormPage.setEmployees("10,001+ employees");
            // Setting the phone number to an invalid one
            contactFormPage.setPhone(invalidPhone);
            contactFormPage.setProductInterest("Net Zero Software");
            contactFormPage.setCountry("Togo");

            // submit form
            contactFormPage.submitForm();

            // Verify phone error is displayed
            Assert.assertTrue(contactFormPage.isPhoneErrorDisplayed(),
                    "Phone error should be displayed for invalid format: " + invalidPhone);

            // Verify error message mentions valid phone or format
            String errorMessage = contactFormPage.getPhoneErrorMessage();
            Assert.assertTrue(
                    errorMessage != null && (errorMessage.contains("phone") || errorMessage.contains("number")
                            || errorMessage.contains("format")), "Phone error message should mention valid format for: " +
                            invalidPhone + " but got message: " + errorMessage);

            System.out.println("Successfully validated error for phone format: " + invalidPhone);
        }
    }

    // Test case for employee dropdown validation

   


}