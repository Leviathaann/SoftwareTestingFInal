package org.salesForceTesting.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.salesForceTesting.tests.BaseTestCore;
import org.testng.Assert;

import java.time.Duration;

/* This class is basically a model for the contact page; it contains:
 * all the locators for the contact form fields (including error messages)
 * all the methods to fill out the contact form
 * all the methods to submit the contact form
 * all the methods to check for error messages
 */

public class ContactFormPage extends BaseTestCore {
    // webdriver instance variables
    private WebDriver driver;
    private WebDriverWait wait;

    // url for Salesforce's contact page
    private String contactPageUrl = "https://www.salesforce.com/form/contact/contactme/";

    // Locators for the contact form fields
    private By firstNameField = By.name("UserFirstName");
    private By lastNameField = By.name("UserLastName");
    private By jobTitleField = By.name("UserTitle");
    private By emailField = By.name("UserEmail");
    private By companyField = By.name("CompanyName");
    private By employeesDropdown = By.name("CompanyEmployees");
    private By phoneField = By.name("UserPhone");
    private By productInterestDropdown = By.name("Lead.Primary_Product_Interest__c");
    private By countryDropdown = By.name("CompanyCountry");
    private By stateField = By.name("CompanyState");
    private By submitButton = By.name("contact me");

    // All locators for the error messages
    // Updated locators for error messages
    private By firstNameError = By.xpath("//input[@name='UserFirstName']/following-sibling::span[@class='error-msg']");
    private By lastNameError = By.xpath("//input[@name='UserLastName']/following-sibling::span[@class='error-msg']");
    private By jobTitleError = By.xpath("//input[@name='UserTitle']/following-sibling::span[@class='error-msg']");
    private By emailError = By.xpath("//input[@name='UserEmail']/following-sibling::span[@class='error-msg']");
    private By companyError = By.xpath("//input[@name='CompanyName']/following-sibling::span[@class='error-msg']");
    private By employeesError = By.xpath("//select[@name='CompanyEmployees']/following-sibling::span[@class='error-msg']");
    private By phoneError = By.xpath("//input[@name='UserPhone']/following-sibling::span[@class='error-msg']");
    private By productInterestError = By.xpath("//select[@name='Lead.Primary_Product_Interest__c']/following-sibling::span[@class='error-msg']");
    private By countryError = By.xpath("//select[@name='CompanyCountry']/following-sibling::span[@class='error-msg']");
    private By stateError = By.xpath("//select[@name='CompanyState']/following-sibling::span[@class='error-msg']");

    // Locator for the success message
    private By successMessage = By.id("thank-you-well-be-in-touch-soon");

    // Constructor
    public ContactFormPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    private void handleAcceptCookies() {
            WebDriverWait cookieTimeWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            By acceptButtonLocator = By.id("onetrust-accept-btn-handler");

            WebElement acceptButton = cookieTimeWait.until(ExpectedConditions.elementToBeClickable(acceptButtonLocator));
            acceptButton.click();

            cookieTimeWait.until(ExpectedConditions.invisibilityOfElementLocated(acceptButtonLocator));
    }


        // go to the contact form page
    public void navigateToContactPage() {
        driver.get(contactPageUrl);
        // will call the handleAcceptCookies if the button is present
        handleAcceptCookies();
    }
        /* Setters for the contact form fields (Actions)
        * These methods check if the input is null or empty before filling in the form.
        * If the input is null, the method will not fill in the field.
        * This is to prevent null pointer exceptions and to ensure that the form is filled out correctly.
     */

    public void setFirstName(String firstName) {
        // Check if the first name is null or empty
        if (firstName != null) {
            WebElement element = driver.findElement(firstNameField);
            element.clear();
            element.sendKeys(firstName);
        }
    }

    public void setLastName(String lastName) {
        // Check if the last name is null or empty
        if (lastName != null) {
            WebElement element = driver.findElement(lastNameField);
            element.clear();
            element.sendKeys(lastName);
        }
    }

    public void setEmail(String email) {
        // Check if the email is null or empty
        if (email != null) {
            WebElement element = driver.findElement(emailField);
            element.clear();
            element.sendKeys(email);
        }
    }

    public void setCompany(String company) {
        // Check if the company is null or empty
        if (company != null) {
            WebElement element = driver.findElement(companyField);
            element.clear();
            element.sendKeys(company);
        }
    }

    public void setPhone(String phone) {
        // Check if the phone number is null or empty
        if (phone != null) {
            WebElement element = driver.findElement(phoneField);
            element.clear();
            element.sendKeys(phone);
        }
    }

    public void setJobTitle(String jobTitle) {
        // Check if the job title is null or empty
        if (jobTitle != null) {
            WebElement element = driver.findElement(jobTitleField);
            element.clear();
            element.sendKeys(jobTitle);
        }
    }

    /* Setters for dropdown menus
        * These methods check if the input is null or empty before selecting an option from the dropdown.
        * If the input is null, the method will not select an option from the dropdown.
        * The dropdowns are selected by visible text.
     */

    public void setEmployees(String employees) {
        // Check if the employees field is null or empty
        if (employees != null) {
            Select EmployeesDropdown = new Select(driver.findElement(employeesDropdown));
            EmployeesDropdown.selectByVisibleText(employees);
        }
    }

    public void setProductInterest(String productInterest) {
        // Check if the product interest field is null or empty
        if (productInterest != null) {
            Select ProductInterestDropdown = new Select(driver.findElement(productInterestDropdown));
            ProductInterestDropdown.selectByVisibleText(productInterest);
        }
    }

    public void setCountry(String country) {
        // Check if the country field is null or empty
        if (country != null) {
            Select CountryDropdown = new Select(driver.findElement(countryDropdown));
            CountryDropdown.selectByVisibleText(country);
        }
    }

    public void selectState(String state) {
        if (state != null && !state.isEmpty()) {
            try {
                // Wait for the state field to be clickable
                WebElement stateElement = wait.until(ExpectedConditions.elementToBeClickable(stateField));
                // Select the state
                Select stateDropdown = new Select(stateElement);
                stateDropdown.selectByVisibleText(state);
                System.out.println("Selected state: " + state);
            } catch (TimeoutException e) {
                System.err.println(
                        "State dropdown did not show to click it: " + e.getMessage());
                Assert.fail("State dropdown failed to load: " + state, e);
            } catch (NoSuchElementException e) {
                System.err.println(
                        "State option did not show up:" + e.getMessage());
                Assert.fail("State option did not show up: " + state, e);
            }
        } else if (state != null) {
            System.out.println("State value is null, skipping to the submit button.");
        }else{
            System.out.println("There was no value for state the field, skipping.");
        }

    }

    // filling out the form
    public void fillOutForm(String firstName, String lastName, String email, String company, String phone, String jobTitle, String employees, String productInterest, String country, String state) {
        setFirstName(firstName);
        setLastName(lastName);
        setJobTitle(jobTitle);
        setEmail(email);
        setCompany(company);
        setEmployees(employees);
        setPhone(phone);
        setProductInterest(productInterest);
        setCountry(country);

        if (state != null && !state.isEmpty()) {
            try {
                wait.until(ExpectedConditions.presenceOfElementLocated(stateField));
                selectState(state);
            } catch (TimeoutException e) {
                System.err.println(
                        "State field " + stateField + " did not show up after setting the country. " +  "Cant set state: " + state);
                if (country != null && (country.equals("United States") || country.equals("Canada") || country.equals("Australia"))) { 
                    Assert.fail("The State field is required for the United States, Canada and Australia" + e);
                }
            }
        } else {
            System.out.println("There was no state provided or it was not required, skipping this selection");
        }
    }

    
    // This method basically clicks the submit button.
    // If the button is not clickable, the method will try to click the button again.
    public void submitForm() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(submitButton)).click();
        } catch (Exception e) {
            System.err.println("Could not click submit button: " + e.getMessage());
            driver.findElement(submitButton).click();
        }
    }

    /* Check for error messages
     * applies ot all fillable fields
     * These methods check if the error message is displayed for the corresponding field.
     * If the error message is displayed, the method will return true.
     */

    public boolean isFirstNameErrorDisplayed() {
        try{
            WebElement element = driver.findElement(firstNameError);
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isLastNameErrorDisplayed() {
        try{
            WebElement element = driver.findElement(lastNameError);
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isJobTitleErrorDisplayed() {
        try{
            WebElement element = driver.findElement(jobTitleError);
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }


    public boolean isEmailErrorDisplayed() {
        try{
            WebElement element = driver.findElement(emailError);
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isCompanyErrorDisplayed() {
        try{
            WebElement element = driver.findElement(companyError);
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isEmployeesErrorDisplayed() {
        try{
            WebElement element = driver.findElement(employeesError);
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isPhoneErrorDisplayed() {
        try{
            WebElement element = driver.findElement(phoneError);
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isProductInterestErrorDisplayed() {
        try{
            WebElement element = driver.findElement(productInterestError);
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isCountryErrorDisplayed() {
        try{
            WebElement element = driver.findElement(countryError);
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isStateErrorDisplayed() {
        try{
            WebElement element = driver.findElement(stateError);
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // Get error messages (applies to all fillable fields)
    public String getFirstNameErrorMessage() {
        try{
            WebElement element = driver.findElement(firstNameError);
            return element.getText();
        } catch (Exception e) {
            return null;
        }
    }

    public String getLastNameErrorMessage() {
        try{
            WebElement element = driver.findElement(lastNameError);
            return element.getText();
        } catch (Exception e) {
            return null;
        }
    }

    public String getJobTitleErrorMessage() {
        try{
            WebElement element = driver.findElement(jobTitleError);
            return element.getText();
        } catch (Exception e) {
            return null;
        }
    }

    public String getEmailErrorMessage() {
        try{
            WebElement element = driver.findElement(emailError);
            return element.getText();
        } catch (Exception e) {
            return null;
        }
    }

    public boolean isStateFieldVisible() {
        try {
            return driver.findElement(stateField).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getCompanyErrorMessage() {
        try{
            WebElement element = driver.findElement(companyError);
            return element.getText();
        } catch (Exception e) {
            return null;
        }
    }

    public String getEmployeesErrorMessage() {
        try{
            WebElement element = driver.findElement(employeesError);
            return element.getText();
        } catch (Exception e) {
            return null;
        }
    }
    public String getPhoneErrorMessage() {
        try{
            WebElement element = driver.findElement(phoneError);
            return element.getText();
        } catch (Exception e) {
            return null;
        }
    }

    public String getProductInterestErrorMessage() {
        try{
            WebElement element = driver.findElement(productInterestError);
            return element.getText();
        } catch (Exception e) {
            return null;
        }
    }

    public String getCountryErrorMessage() {
        try{
            WebElement element = driver.findElement(countryError);
            return element.getText();
        } catch (Exception e) {
            return null;
        }
    }

    public boolean isSuccessMessageDisplayed() {
        try {
            // Wait for the success message element to be visible
            wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage));
            return true; // Element is visible
        } catch (TimeoutException | NoSuchElementException e) {
            // Element not found or not visible within the timeout period
            return false;
        }
    }

    public String getSuccessMessageText() {
        try {
            // Wait for the element to be visible and get its text
            WebElement successElement = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(successMessage)
            );
            // Get text from the inner span for cleaner text
            WebElement innerSpan = successElement.findElement(By.tagName("span"));
            return innerSpan.getText().trim();
        } catch (TimeoutException | NoSuchElementException e) {
            // Element not found or not visible within the timeout period
            return null;
        }
    }






}


