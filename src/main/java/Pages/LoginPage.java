package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.ElementActions;

import java.awt.*;

public class LoginPage {
    private ElementActions elementAction;


    public LoginPage(WebDriver driver) throws AWTException {
        elementAction = new ElementActions(driver);
    }

    private By email_TextField   = By.id("emailField");
    private By password_TextField = By.id("passwordField");
    private By login_Button       = By.id("loginButton");


    /**
     * Login Using email and password
     * @param email  user email
     * @param pass   user password
     * ***/
    public LoginPage loginWithValidCredentials(String email , String pass) {

        elementAction.clearTextThenWrite(email_TextField,email);
        elementAction.clearTextThenWrite(password_TextField,pass);
        elementAction.click(login_Button);
        return this ;
    }




}
