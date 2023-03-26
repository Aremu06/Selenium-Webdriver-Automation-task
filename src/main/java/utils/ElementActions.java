package utils;

import com.assertthat.selenium_shutterbug.core.Shutterbug;
import com.github.romankh3.image.comparison.ImageComparison;
import com.github.romankh3.image.comparison.ImageComparisonUtil;
import com.github.romankh3.image.comparison.model.ImageComparisonResult;
import com.github.romankh3.image.comparison.model.ImageComparisonState;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class ElementActions {

    private WebDriver driver;
    FluentWait wait;

    public ElementActions(WebDriver driver) throws AWTException {
        this.driver = driver;
    }

    /**
     * Wait for element to be clickable for 30 seconds
     * @param locator
     */
    private void waitForElementTobeClickable(By locator) {
        try {
            wait = new FluentWait(driver).withTimeout(Duration.ofSeconds(30)).pollingEvery(Duration.ofSeconds(10)).ignoring(NoSuchElementException.class);
            wait.until(ExpectedConditions.elementToBeClickable(locator));
        } catch (NoSuchElementException | TimeoutException e) {
            System.out.println("Failed to find element with locator " + locator.toString());
        }
    }

    /**
     * Wait for the element to be visible inside the page
     * @param locator
     */
    public void waitForElementTobeVisible(By locator) {
        try {
            wait = new FluentWait(driver).withTimeout(Duration.ofSeconds(30)).pollingEvery(Duration.ofSeconds(5)).ignoring(NoSuchElementException.class);
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (NoSuchElementException | TimeoutException e) {
            System.out.println("Failed to find element with locator " + locator.toString());
        }
    }

    /**
     * Perform click Action After waiting the element to be clickable
     * @param locator
     */

    public void click(By locator) {
        waitForElementTobeClickable(locator);
       driver.findElement(locator).click();
        System.out.println("Successfully clicked on element with locator '" + locator.toString() + "'");
    }

    /**
     * Type text inside input field after waiting the element to be visible
     * @param locator
     * @param text
     */
    public void write(By locator, String text) {
        waitUntilElementClickableThenSendkeys(locator,text);
        System.out.println("Successfully wrote text '" + text + "' in element with locator '" + locator.toString() + "'");
    }


   /**
    * Clear the text from the text field then type new text
    * ***/
    public void clearTextThenWrite(By locator , String text){
        waitForElementTobeVisible(locator);
        driver.findElement(locator).clear();
        write(locator , text);
    }




    /**
     *  Wait until the input field to be clickable ,click it then type inside it
     *  used for specific elements inside the project
     * @param element
     * @param data : String
     */
    public void waitUntilElementClickableThenSendkeys(By element, String data) {
        // Wait for Element to be visible.
        try {
            wait = new FluentWait(driver).withTimeout(Duration.ofSeconds(30)).pollingEvery(Duration.ofSeconds(10)).ignoring(NoSuchElementException.class);
            wait.until(ExpectedConditions.elementToBeClickable(element));
            driver.findElement(element).click();
            driver.findElement(element).sendKeys(data);

        } catch (Exception e) {
            System.out.println( "Element not found - sendkeys"+element.toString());
        }

    }


    /**
     * Wait until element is clickable then click it
     * Not used inside the project
     * @param element
     * @deprecated
     */
    public void waitUntilElementClickableThenClick(By element) {

        try {
            wait = new FluentWait(driver).withTimeout(Duration.ofSeconds(30)).pollingEvery(Duration.ofSeconds(10)).ignoring(NoSuchElementException.class);
            wait.until(ExpectedConditions.elementToBeClickable(element));
            driver.findElement(element).click();

        } catch (Exception e) {
            System.out.println( "Element not found - click" );
        }
    }



    /**
     * Press ENTER  from keyboard
     * @param locator
     */
    public void pressEnter (By locator) {

        driver.findElement(locator).sendKeys(Keys.ENTER);

    }

    /**
     * Press RETURN from Keyboard
     * Not used inside the project
     * @param locator
     * @deprecated
     */
    public void pressReturn (By locator) {
        driver.findElement(locator).sendKeys("hossam");
        driver.findElement(locator).sendKeys(Keys.RETURN);

    }


    /**
     * Force the webdriver to wait for specific amount of time
     * as there 're cases where the system(JavaScript ) is too fast and can't do specific
     * @param seconds - integer
     * @throws InterruptedException
     */
    public void forceWait(int seconds) throws InterruptedException {

        Thread.sleep(Long.parseLong(seconds+"000"));
    }



   /**
    * Drag and drop element 1 to element 2
    * */
    public void dragAndDrop(By element1 , By element2) {

        WebElement from = driver.findElement(element1);
        WebElement to = driver.findElement(element2);
        Actions builder = new Actions(driver);
        Action dragAndDrop = builder.clickAndHold(from)
                .moveToElement(to)
                .release(to)
                .build();
        dragAndDrop.perform();

    }


    /**
     * Refresh Page
     * */
    public void refresh() {

        driver.navigate().refresh();
    }


    /**
     * Take screenshot for specific element then give it a name
     * **/
    public void takeScreenshot(String screenshotname,By locator) {

        WebElement element = driver.findElement(locator);
        Shutterbug.shootElement(driver,element).withName(screenshotname).save("src/test/java/TestData/Images/");

    }


    /***
     * Compare 2 images
     * didn't use it for the required test
     * @deprecated
     * **/
    public static void compareImages(String expectedPath, String actualPath){
// load the images to be compared
        BufferedImage expectedImage = ImageComparisonUtil.readImageFromResources(expectedPath);
        BufferedImage actualImage = ImageComparisonUtil.readImageFromResources(actualPath);

// where to save the result (leave null if you want to see the result in the UI)
        File resultDestination = new File( "src/test/java/TestData/Images/Result.png" );

//Create ImageComparison object for it.
        ImageComparison imageComparison = new ImageComparison( expectedImage, actualImage, resultDestination );

//Also can be configured BEFORE comparing next properties:

//Threshold — it’s the max distance between non-equal pixels. By default it’s 5.
        imageComparison.setThreshold(5);
        imageComparison.getThreshold();

//RectangleListWidth — Width of the line that is drawn in the rectangle. By default it’s 1.
        imageComparison.setRectangleLineWidth(1);
        imageComparison.getRectangleLineWidth();

//DifferenceRectangleFilling — Fill the inside the difference rectangles with a transparent fill. By default it’s false and 20.0% opacity.
        imageComparison.setDifferenceRectangleFilling(true, 20.0);
        imageComparison.isFillDifferenceRectangles();
        imageComparison.getPercentOpacityDifferenceRectangles();

//ExcludedRectangleFilling — Fill the inside the excluded rectangles with a transparent fill. By default it’s false and 20.0% opacity.
        imageComparison.setExcludedRectangleFilling(true, 20.0);
        imageComparison.isFillExcludedRectangles();
        imageComparison.getPercentOpacityExcludedRectangles();

//Destination. Before comparing also can be added destination file for result image.
        imageComparison.setDestination(resultDestination);
        imageComparison.getDestination();

//MaximalRectangleCount — It means that would get first x biggest rectangles for drawing.
// by default all the rectangles would be drawn.
        imageComparison.setMaximalRectangleCount(10);
        imageComparison.getMaximalRectangleCount();

//MinimalRectangleSize — The number of the minimal rectangle size. Count as (width x height).
// by default it’s 1.
        imageComparison.setMinimalRectangleSize(1);
        imageComparison.getMinimalRectangleSize();

//Change the level of the pixel tolerance:
        imageComparison.setPixelToleranceLevel(0.2);
        imageComparison.getPixelToleranceLevel();

//After configuring the ImageComparison object, can be executed compare() method:
        ImageComparisonResult imageComparisonResult = imageComparison.compareImages();

//Can be found ComparisonState.
        ImageComparisonState imageComparisonState = imageComparisonResult.getImageComparisonState();

//And Result Image
        BufferedImage resultImage = imageComparisonResult.getResult();

//Image can be saved after comparison, using ImageComparisonUtil.
        if (imageComparisonResult.getDifferencePercent()>0.0)ImageComparisonUtil.saveImage(resultDestination, resultImage);
    }

    /**
     * Compare Images and return true if they are same , false if not
     * Accepted Deviation = .0244932
     * ****/
    public boolean compareImagesUsing(String expectedPath, String actualPath) throws IOException {


    return Shutterbug.shootPage(driver).equalsWithDiff(expectedPath,actualPath,.02449319);
    }

    public void getElementsUsingTagName(String tagName) {

        List<WebElement> listOfElement = driver.findElements(By.tagName(tagName));

        for (WebElement eachLink : listOfElement){
            if (eachLink.getText().length()>0)
                System.out.println("Element Found with "+tagName +": "+eachLink.getText());

    }}


    public List<WebElement> getStreamList( String tagName , String data) throws Exception {
        try {

            List<WebElement> listOfElement = driver.findElements(By.tagName(tagName));
            List<WebElement> streamList = listOfElement.stream()
                    .filter(x -> x.getText().toLowerCase().contains(data))
                    .collect(Collectors.toList());

            for (WebElement eachLink : streamList) {
                    System.out.println("Stream Found : " + eachLink.getText());
            }
            return streamList;
        } catch (Exception e) {
            throw new Exception("Exception occurred while getting Stream of elements : " + e.getMessage());
        }

    }

}




