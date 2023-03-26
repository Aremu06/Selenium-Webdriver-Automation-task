package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.ElementActions;

import java.awt.*;
import java.io.IOException;

public class BoardPage {
    private ElementActions elementAction;


    public BoardPage(WebDriver driver) throws AWTException {
        elementAction = new ElementActions(driver);
    }


    private By moreTool_Label = By.xpath("//div[@class='board-toolbar__items']/div[@tooltip='more tools']");
    private By appsSearch_TextField = By.xpath("//input[@id='library-root-search']");
    private By stickers_Option = By.xpath("//button[contains(@class,'MOJI_ICONS')]");
    private By sticker_Tab = By.xpath("//div[@data-testid='stickers-library-breadcrumbs']/div[1]");
    private By stickerSearch_TextField = By.xpath("//input[@placeholder='Search stickers']");
    private By stickerSuggestion_Label = By.xpath("(//div[@id='stickers-library-content']/div/div/div[2]/div[1]/div)[1]");
    private By whiteCanvas = By.xpath("//*[@id='active_users_layer']");
    private By closeIcon_Button = By.xpath("(//button[@aria-label='Close'])[1]");


    /***
     * Search for Specific sticker then add it to the canvas
     * @param stickerName  sticker name you want to add
     * */
    public BoardPage addSticker (String stickerName) throws InterruptedException {


        elementAction.waitForElementTobeVisible(moreTool_Label);
        elementAction.click(moreTool_Label);
        elementAction.write(appsSearch_TextField,"Stickers");
        elementAction.click(stickers_Option);
        elementAction.click(sticker_Tab);
        elementAction.write(stickerSearch_TextField,stickerName);
        elementAction.waitForElementTobeVisible(stickerSuggestion_Label);
        elementAction.click(stickerSuggestion_Label);
        elementAction.forceWait(5);
        elementAction.dragAndDrop(stickerSuggestion_Label, whiteCanvas);
        elementAction.click(closeIcon_Button);

        return this;
    }


    /***
     * Take Screenshot of specific element and name it as you desire
     * @param  screenShotName screenshot name you want
     * **/
    public void takeScreenshot (String screenShotName ) throws InterruptedException {

        elementAction.waitForElementTobeVisible(moreTool_Label);
        elementAction.forceWait(7);
        elementAction.takeScreenshot(screenShotName,  whiteCanvas);

    }

/****
 * Compare between 2 images then return true if they are same and false if not
 * */
    public boolean compareScreenShots() throws IOException, InterruptedException {

        elementAction.forceWait(30);
        return elementAction.compareImagesUsing("src/test/java/TestData/Images/Screenshot1.png","src/test/java/TestData/Images/screenshot2.png");

    }
}
