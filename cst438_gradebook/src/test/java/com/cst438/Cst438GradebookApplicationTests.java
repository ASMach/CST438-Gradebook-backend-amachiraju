package com.cst438;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Cst438GradebookApplicationTests {
	
	final int SLEEP_DURATION = 1000;
	
	final String TEST_USER_EMAIL = "test@csumb.edu";
	final String CHROME_DRIVER_FILE_LOCATION = "";
	final String TEST_COURSE_NAME = "Software Engineering";
	final String TEST_DUE_DATE = "2022-10-10";
	final String TEST_COURSE = "CST438";
	final String URL = "http://localhost:3000/"; // When testing locally
	//final String URL = "https://cst438gradebook-frontend.herokuapp.com/";

	final int TEST_COURSE_ID = 40442;
	
	// TODO: Test an instructor adding a new assignment
	
	@Test
	void createAssignment() {
		
		// browser    property name                 Java Driver Class
        // edge       webdriver.edge.driver         EdgeDriver
        // FireFox    webdriver.firefox.driver      FirefoxDriver
        // IE         webdriver.ie.driver           InternetExplorerDriver
        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_FILE_LOCATION);
        WebDriver driver = new ChromeDriver();
        // Puts an Implicit wait for 10 seconds before throwing exception
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
        /*
		// Clean up any enrollmen ts to make this test repeatable
				 Enrollment x = null;
			        do {
			            x = enrollmentRepository.findByEmailAndCourseId(TEST_USER_EMAIL, TEST_COURSE_ID);
			            if (x != null)
			                enrollmentRepository.delete(x);
			        } while (x != null);
			        
			     // verify that enrollment row has been inserted to database.
		            Enrollment e = enrollmentRepository.findByEmailAndCourseId(TEST_USER_EMAIL, TEST_COURSE_ID);
		            assertNotNull(e, "Course enrollment not found in database.");


		            // clean up database.
		            Enrollment e = enrollmentRepository.findByEmailAndCourseId(TEST_USER_EMAIL, TEST_COURSE_ID);
		            if (e != null)
		                enrollmentRepository.delete(e);
*/
		            
		            driver.quit();
		            
		            driver.get(URL);
		            try {
						Thread.sleep(SLEEP_DURATION);
					} catch (InterruptedException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
		            
		            // Find the first a tag, which is a + button
		            driver.findElement(By.xpath("//a")).click();
		            try {
						Thread.sleep(SLEEP_DURATION);
					} catch (InterruptedException e1) {
						// Auto-generated catch block
						e1.printStackTrace();
					}
		            
		         // select the inputs and enter values
		            driver.findElement(By.xpath("//input[@id='name']")).sendKeys(TEST_COURSE_NAME);
		            driver.findElement(By.xpath("//input[@id='duedate']")).sendKeys(TEST_DUE_DATE);
		            driver.findElement(By.xpath("//input[@id='course']")).sendKeys(TEST_COURSE);
		            driver.findElement(By.xpath("//button[span='Add']")).click();
		            try {
						Thread.sleep(SLEEP_DURATION);
					} catch (InterruptedException e) {
						// Auto-generated catch block
						e.printStackTrace();
					}
		            
		            /*
		            // verify that new course shows in schedule.
		            Course course = courseRepository.findById(TEST_COURSE_ID).get();
		            we = driver.findElement(By.xpath("//div[@data-field='title' and @data-value='" + course.getTitle() + "']"));
		            assertNotNull(we, "Added course does not show in schedule.");
		            */
	}

}
