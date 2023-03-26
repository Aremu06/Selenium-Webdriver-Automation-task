package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.ElementActions;
import java.awt.*;
import java.util.List;

public class DashboardPage {
    private ElementActions elementAction;

    public DashboardPage(WebDriver driver) throws AWTException {
        elementAction = new ElementActions(driver);
    }

    private By de_Button = By.id("langDe");
    private By profileIcon_Button = By.id("menu-profile");
    private By signOut_Option = By.xpath("//span[text()='Log out']");


    /***
     * Refresh the dashboard to be able to see the newly created board
     * */
    public DashboardPage goToProfilePage() {
        elementAction.click(profileIcon_Button);
        return this;
    }




    public List<WebElement> getElementsWithTagName() {


        elementAction.getElementsUsingTagName("p");

        return null;
    }



    public  void getStream () throws Exception {

        elementAction.getStreamList("p","bezahlen");
    }



    public void changeLanguage (){

        elementAction.click(de_Button);
        try {
            elementAction.forceWait(4);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
