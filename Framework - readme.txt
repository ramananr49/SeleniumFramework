Building Framework from Scratch:-

part 1 :- Creation of Maven Project
part 2 :- Crating Base & utility function
part 3 :- Organizing Page Object
part 4 :- Adding tests
part 5 :- Data Driving/ Parameterizing the tests
part 6 :- Converting into TestNG Framework
part 7 :- Log4j API for logging inside Framework
part 8 :- TestNG Listeners
part 9 :- Screenshot on Test Failures
part 10 :- Creating excellenet HTML reports for Test Results
part 11 :- jenkins - Continuous Integration


Step 1 :- Create a Maven Project from Eclipse 
	
		File -> New -> Project -> select 'Maven Project' -> select 'Next' @ 'New Maven Project' window -> Select the archetype -> provide 'Group ID' and 'Artifact ID' -> finish
		
		Note :- Group ID - Package name & Artifact ID - Project Name
		
Step 2 :- Go to Maven Repository website and copy the Selenium & TestNG dependency into pom.xml file.
		
Step 3 :- Create a Package called "resource" under "src/main/java" folder. 
		
		Note :- all the common files like globaldata.properties , BaseMain.java, ExtendReport.java , log4j2.xml go into here

Step 4 :- Create a "BaseMain" java class in "src/main/java" folder. (Write utility code like initiliazeBrowser, getScreenshot etc.. here)

Step 5 :- create one properties file in "src/main/java/resource" folder.  (Declare global variable like browser to invoke & URL to hit )

step 6 :- Create one folder at Project level and named as "Library". Place all the browser drivers inside the folder (chromedriver.exe, geckodriver.exe etc..)

step 7 :- Create one method to initiliaze driver based upon browser input in global properties file / Maven Command.

		Note :- Create local driver object in each testcase java class file. because during parallel test execution , it wont interript. Otherwise we may get weird result.

step 8 :- to access the method which is inside in BaseMain class, inherit the method to testcase java class file.

step 9 :- Create a "ObjectRepository" package in "src/main/java" folder. 

Step 10 :- Create one java class file (which is page object model) for each page of website. Eg :- if a website has 10 pages in total, 
		   then we need to have 10 java class file for those pages.
		   
Step 11 :- on Each Page Object Repository java class, create a Local WebDriver object and Constructor to assign base class driver to local driver. To access the methods ,
			we need to give life for local webdriver object by constructor.
			
Step 12 :- Declare all the objects of that page as private and and give access by public method. by doing this we are achieving 'Encapsulation' OOPS concept.

			Eg :-  private By login = By.xpath("//tagname=[@attribute='value'");
			
				   public WebElement login()
				   {
						return driver.findElement(login);
				   }
				   
Step 13 :- To call the web object which is declared in the 'Page Object' java class, first we need to create an object for that java class in testcase java class.
			after creating an object for the page object, we can call an object by mentioning the object of page object.
			
			Eg :- LandingPage LP = new LandingPage(driver);
				  LP.login().Click();
				  
step 14 :- Create a TestNG.xml file for the project. 
			Righ Click on the Project -> mouse over on 'TestNG' option -> Click the 'Convert to TestNG' -> Click the 'Finish' button on 'Generate TestNG.xml' popup window.
			
step 15 :- After creating "testNG.xml" file, we need to integrate the "testNG.xml" file with "pom.xml" file of Maven. To do this, we need to add the following line of dependency code
			under maven-surefire-plugin tag.
			
		<configuration>
          <suiteXmlFiles>
            <suiteXmlFile>testng.xml</suiteXmlFile>
          </suiteXmlFiles>
        </configuration>
		
		Eg:-           <plugin>
                            <groupId>org.apache.maven.plugins</groupId>
                            <artifactId>maven-surefire-plugin</artifactId>
                            <version>3.0.0-M5</version>
                            <configuration>
                              <suiteXmlFiles>
                                <suiteXmlFile>testng.xml</suiteXmlFile>
                              </suiteXmlFiles>
                            </configuration>
                       </plugin>
					   
step 16 :- To initiliaze the logger, we need to copy the below mentioned resource code from 'Log4j maven filtering' website and paste it into the pom.xml file under 'build' tag.

	<resources>
      	<resource>
        	<directory>src/main/java/resources</directory>
        	<filtering>true</filtering>
      	</resource>
    </resources>
	
step 17 :- Copy the "log4j-API" and "log4j-core" dependency from Maven repository into pom.xml file under 'dependency' tag.
	
step 18 :- create / copy the log4j2.xml file into "src/main/java/resources" folder. create a log4j2.xml by having following code in the xml file.

	<?xml version="1.0" encoding="UTF-8"?>
    <Configuration status="WARN">
        <Properties>
    		<Property name="basePath">./logs</Property>
    	</Properties>
     
      <Appenders>
          <RollingFile name="File" fileName="${basePath}/prints.log" filePattern="${basePath}/prints-%d{yyyy-MM-dd}-%i.log">
         		<PatternLayout pattern="%d{yyyy-MM-dd : HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n"/>
          		<SizeBasedTriggeringPolicy size="500" />
          </RollingFile>
          <Console name="Console" target="SYSTEM_OUT">
          		<PatternLayout pattern="%d{HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n"/>
          </Console>
      </Appenders>
      <Loggers>
        <Root level="trace">
          <AppenderRef ref="File"/>
        </Root>
      </Loggers>
    </Configuration>
	
step 19 :- we need create a object for logger in all the testcase file at global level to log something. 
	
		eg :- public static Logger log = LogManager.getLogger(BaseMain.class.getName());
		
step 20 :- Create a Listeners java class in "src/test/java/" and implements ITestListeners interface.

Step 21 :- Provide the knowledge of Listeners class to "testNG.xml" file by adding below code between suite tag & test tag.

			<listeners>
				<listener class-name = "resources.Listeners"/>
			</listeners>

step 22 :- Implement the unimplemented methods from ITestListeners interface.

step 23 :- Create/Implement the getScreenshot method in BaseMain class and call the method in OnTestFailure method of Listeners java class.

		Note :- Add the "common.io" dependency to the "pom.xml" file under dependency tag.
		
		public String getScreenshotPath(String TestCaseName, WebDriver driver) throws IOException
	    {
	    	TakesScreenshot ts = (TakesScreenshot) driver;
	    	File SourceFile = ts.getScreenshotAs(OutputType.FILE);
	    	String  DestFile = System.getProperty("user.dir")+"\\reports\\"+TestCaseName+".png";
	    	FileUtils.copyFile(SourceFile, new File (DestFile));
	    	return DestFile;
	    }
		
step 24 :- Inherite the BaseMain java class into Listeners java class by adding extends keyword. 

step 25 :- First to get the testcase name dynamically, implement the following code in onTestFailure method of Listeners java class
	
			String failedTestCase = result.getMethod().getMethodName();
			
step 26 :- Secondly to get the life of driver instance, implement the following code in onTestFailure method of Listeners java class

			WebDriver driver = null;
			driver = (WebDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
			
step 27 :- To implement the Extent Reports into framework, copy the dependency of "aventstack extent reports" into "pom.xml" file under 'dependency' tag.

step 28 :- create a "ExtendReport" java class under "src/main/java/resources" folder . and write the following one time code inside the file.

step 29 :- In Listeners java class write following line of code in global level

				ExtentTest test;
				ExtentReports extent = ExtentReportNG.getReportObject();
				ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>();
				
step 30 :- Write the following code in "onTestStart" method of "Listeners" java class

		test = extent.createTest(result.getMethod().getMethodName());
		extentTest.set(test);
		
step 31 :- Write the following code in "onTestSuccess" method of "Listeners" java class

		extentTest.get().log(Status.PASS, "Test Passed");
		
step 32 :- Write the following code in "onTestFailure" method of "Listeners" java class. so that whenever test fails , it will capture and write the expection in report

		extentTest.get().fail(result.getThrowable());
		
step 33 :- If we want to add the screenshot into the report of failure testcase , write the following code in "onTestFailure" method of "Listeners" java class.

		extentTest.get().addScreenCaptureFromPath(getScreenshotPath(failedTestCaseName, driver), failedTestCaseName);
		
		instead of "getScreenshotPath(failedTestCaseName, driver)"
		
		Finally onTestFailure method should have following code:-
		
		public void onTestFailure(ITestResult result) 
		{
		    extentTest.get().fail(result.getThrowable());
		    
		    WebDriver driver = null;
		    try {
		    	driver = (WebDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
		    } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e1) {
		    	// TODO Auto-generated catch block
		    	e1.printStackTrace();
		    }
		    String failedTestCaseName = result.getMethod().getMethodName();
		    try {
		    	extentTest.get().addScreenCaptureFromPath(getScreenshotPath(failedTestCaseName, driver), failedTestCaseName);
		    } catch (IOException e) {
		    	// TODO Auto-generated catch block
		    	e.printStackTrace();
		    }	
		}
		
step 34 :- Write the following code in "onFinish" method of "Listeners" java class

		extent.flush();