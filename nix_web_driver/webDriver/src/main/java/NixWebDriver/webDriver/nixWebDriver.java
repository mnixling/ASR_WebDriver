package NixWebDriver.webDriver;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class nixWebDriver {
	
	public static void main(String[] args) throws Exception {
		Configuration configuration = new Configuration();
		
		configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
		configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
		configuration.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");
		
		LiveSpeechRecognizer recognizer = new LiveSpeechRecognizer(configuration);
		recognizer.startRecognition(true);
		SpeechResult result = recognizer.getResult();
		
		while ((result = recognizer.getResult()) != null) {
			System.out.format("Hypothesis: %s\n", result.getHypothesis());
			String webSite = "http://" + result.getHypothesis() + ".com";
			System.out.print("Accessing website: " + webSite + "\n");
			
			System.setProperty("webdriver.chrome.driver", "C:\\Users\\manua\\Desktop\\nix_web_driver\\webDriver\\src\\chromedriver.exe");
			WebDriver driver = new ChromeDriver();
			driver.get(webSite);
			
			while ((result = recognizer.getResult()) != null) {
				String searchFor = result.getHypothesis();
				WebElement searchElement = driver.findElement(By.name("q"));
				searchElement.sendKeys(searchFor);
				searchElement.submit();
				System.out.print("Searching for: " + searchFor + "\n");
				
				List<WebElement> searchResultLink = driver.findElements(By.tagName("link"));
				while ((result = recognizer.getResult()) != null) {
					String commandWord = result.getHypothesis();
					configuration.setGrammarName("command");
					configuration.setGrammarPath("file:src");
					configuration.setUseGrammar(true);
					
					if (commandWord.matches("open")) {
						for (WebElement link : searchResultLink) {
							String webPageLink = link.getAttribute("href");
							driver.get(webPageLink);
							System.out.println(result.getHypothesis());
						}
					}
					if (commandWord.matches("back")) {
						driver.navigate().back();
						System.out.println(result.getHypothesis());
					}
					if (commandWord.matches("forward")) {
						driver.navigate().forward();
						System.out.println(result.getHypothesis());
					}
					if (commandWord.matches("stop|close|exit")) {
						driver.close();
						System.out.println(result.getHypothesis());
					}
				}
			}
		}
	}
}




















//package NixWebDriver.webDriver;
//
//import edu.cmu.sphinx.api.Configuration;
//import edu.cmu.sphinx.api.LiveSpeechRecognizer;
//import edu.cmu.sphinx.api.SpeechResult;
//import edu.cmu.sphinx.api.StreamSpeechRecognizer;
//
//import java.util.List;
//
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.chrome.ChromeDriver;
//
//public class nixWebDriver {
//	
//	private static final String WebDriver = null;
//	
//	public static void main(String[] args) throws Exception {
//		Configuration configuration = new Configuration();
//		
//		configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
//		configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
//		configuration.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");
//		
//		LiveSpeechRecognizer recognizer = new LiveSpeechRecognizer(configuration);
//		recognizer.startRecognition(true);
//		SpeechResult result = recognizer.getResult();
//		
//		while ((result = recognizer.getResult()) != null) {
//			System.out.format("Hypothesis: %s\n", result.getHypothesis());
//			String webSite = "http://" + result.getHypothesis() + ".com";
//			System.out.print("Accessing website: " + webSite);
//			
//			System.setProperty("webdriver.chrome.driver", "C:\\Users\\manua\\Desktop\\nix_web_driver\\webDriver\\src\\chromedriver.exe");
//			WebDriver driver = new ChromeDriver();
//			driver.get(webSite);
//			
//			while ((result = recognizer.getResult()) != null) {
//				String searchFor = result.getHypothesis();
//				WebElement searchElement = driver.findElement(By.name("q"));
//				searchElement.sendKeys(searchFor);
//				searchElement.submit();
//				WebElement pageLink = driver.findElement(By.tagName("link").id("link"));					
//				while ((result = recognizer.getResult()) != null) {
//					String commandWord = result.getHypothesis();
//					System.out.print(commandWord);
//					configuration.setGrammarName("command");
//					configuration.setGrammarPath("file:src");
//					configuration.setUseGrammar(true);
//					
//					if (commandWord.matches("open")) {
//						pageLink.click();
//					}
//					if (commandWord.matches("go back|back")) {
//						driver.navigate().back();
//					}
//					if (commandWord.matches("go forward|forward")) {
//						driver.navigate().forward();
//					}
//					if (commandWord.matches("stop|close|exit")) {
//						driver.close();
//					}else {
//						System.out.print("No Match");
//					}
//				}
//			}
//		}
//	}
//}
