package org.salesForceTesting.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/* This class is basically a model for the contact page; it contains:
 * all the locators for the contact form fields (including error messages)
 * all the methods to fill out the contact form
 * all the methods to submit the contact form
 * all the methods to check for error messages
 */

public class ContactFormPage {
    // webdriver instance variables
    private WebDriver driver;
    private WebDriverWait wait;

    // url for Salesforce's contact page
    private String contactPageUrl = "https://www.salesforce.com/form/contact/contactme/";

    // Locators for the contact form fields
    private By firstNameField = By.id("UserFirstName-Zsel");
    private By lastNameField = By.id("UserLastName-QTwS");
    private By jobTitleField = By.id("UserTitle-bLge");
    private By emailField = By.id("UserEmail-6neN");
    private By companyField = By.id("CompanyName-re4G");
    private By employeesDropdown = By.id("CompanyEmployees-KlMS");
    private By phoneField = By.id("UserPhone-GULU");
    private By productInterestDropdown = By.id("Lead.Primary_Product_Interest__c-eMcH");
    private By countryDropdown = By.id("CompanyCountry-4rfj");
    private By submitButton = By.xpath("//button[@name='contact me' and @type='submit' and contains(@class, 'submit')]");

    // All locators for the error messages
    private By firstNameError = By.id("UserFirstName-Zsel-errMsg");
    private By lastNameError = By.id("UserLastName-QTwS-errMsg");
    private By jobTitleError = By.id("UserTitle-wZst-errMsg");
    private By emailError = By.id("UserEmail-6neN-errMsg");
    private By companyError = By.id("CompanyName-re4G-errMsg");
    private By employeesError = By.id("CompanyEmployees-KlMS-errMsg");
    private By phoneError = By.id("UserPhone-GULU-errMsg");
    private By productInterestError = By.id("Lead.Primary_Product_Interest__c-eMcH-errMsg");
    private By countryError = By.id("CompanyCountry-4rfj-errMsg");
    private By submitButtonError = By.xpath("//button[@name='contact me' and @type='submit' and contains(@class, 'submit') and @aria-invalid='true']");


    // Locator for the success message
    private By successMessage = By.xpath("");

    // Constructor
    public ContactFormPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // go to the contact form page
    public void navigateToContactFormPage() {
        driver.get(contactPageUrl);
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

    // filling out the form
    public void fillOutForm(String firstName, String lastName, String email, String company, String phone,
                            String jobTitle, String employees, String productInterest, String country) {
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
        setCompany(company);
        setPhone(phone);
        setJobTitle(jobTitle);
        setEmployees(employees);
        setProductInterest(productInterest);
        setCountry(country);
    }

    // Submit the form
    public void submitForm() {
        WebElement element = driver.findElement(submitButton);
        element.click();
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

    public boolean isSubmitButtonErrorDisplayed() {
        try{
            WebElement element = driver.findElement(submitButtonError);
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

    public String getSubmitButtonErrorMessage() {
        try{
            WebElement element = driver.findElement(submitButtonError);
            return element.getText();
        } catch (Exception e) {
            return null;
        }
    }







}


