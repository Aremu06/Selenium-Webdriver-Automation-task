# Tobi Technical Task

# Project Structure :
- This framework followed Page object Model methodology, Pages package include 3 classes for each screen ( Login Page , Dashboard , Board ) and it has a one Test Page (EndToEndTests) .
- All configuration Data (  Browser Name - Credentials - etc ) exist in TestData package (TestData.java)
- Test Report exist in TestReports package.
- You can run the test by running E2E.xml in the project root path
- You can run the parallel tests by running Parallel.xml in the project root path
- Execution automatically recorder as videos in video folder.
- WebDriver Manager automatically handle downloading the needed WebDriver binary executable files for all browsers on all platforms (chrome-firefox-Safari-Edge- Opera)

# Tech Stack:

- Selenium Webdriver
- Maven
- TestNG
- Extent Report for reporting in HTML5 format
- Automation-remarks for recording all test execution as videos
- ShutterBug for image Comparison 

# Requirements :
- jdk-17
- Eclipse or Intellij IDE


### How To Run 
- Run below command Or right click on EndToEndTests then choose run Or  right click on E2E.xml then choose run 
```aidl
mvn clean test
```

