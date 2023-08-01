package randomproject;

import com.performtask.BrowserUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.util.List;

public class GetAllCommands extends BrowserUtils {

    @Test
    public void getAllCommands(){
        BrowserUtils.browserLaunch("chrome","https://peter.sh/experiments/chromium-command-line-switches/");
        List<WebElement> elements = driver.findElements(By.xpath("//table[@class='overview-table']/tbody/tr/td[1]"));
        for (WebElement web:elements) {
            String text = web.getText();
            String s = text.replaceAll(" ", "");
            String s1 = s.replaceAll("⊗", "");
            if(!s1.contains("⊗"))
            System.out.println("options.addArguments("+'"'+s1+'"'+");");
        }
    }
}
