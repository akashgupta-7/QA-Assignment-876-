package sample.test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.testng.annotations.BeforeClass;

import io.restassured.RestAssured;

public class TestBase {

	//private static final Logger LOG = Logger.getLogger(TestRest.class);
	Properties pro = new Properties();
	
	@BeforeClass(alwaysRun = true, description = "Prepare test environment")
	public void getData() throws IOException{
		FileInputStream fis = new FileInputStream("C:\\Users\\Akash Anand\\Workspace\\API\\src\\test\\java\\files\\prop.properties");
		pro.load(fis);	
		RestAssured.baseURI = pro.getProperty("HOST1");
}
}
