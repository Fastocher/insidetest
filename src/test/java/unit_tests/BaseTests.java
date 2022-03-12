package unit_tests;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public class BaseTests {

    @BeforeTest
    public void beforeTest(){
        System.out.println("Before test method run");
    }

    @AfterTest
    public void afterTest(){
        System.out.println("After test method run");
    }

}
