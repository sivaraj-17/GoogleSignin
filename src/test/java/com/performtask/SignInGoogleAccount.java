package com.performtask;

import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.annotations.Listeners;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@Listeners(com.performtask.Listeners.class)
public class SignInGoogleAccount extends BrowserUtils {

    static String downloadDir = BrowserUtils.downloadDir;

    @BeforeClass
    public void beforeClass() {
        System.out.println("----------------------------Test case started----------------------------");
    }

    @BeforeMethod
    public void initiateBrowser() {
        BrowserUtils.browserLaunch("chrome", "https://consumer-ua9.xtime.com/scheduling/?webKey=x5automp8blockedxx1");
    }

    @Test(testName = "verifySignInGoogleAccountWith2StepVerification")
    public static void verifySignInGoogleAccountWith2StepVerification() throws IOException {
        ResourceUtils uc = new ResourceUtils(0);
        String actualFileName = PageObjectModel.getFileName(downloadDir, uc.user);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(downloadDir + "//" + actualFileName));
        String dataType = bufferedReader.readLine();
        bufferedReader.close();
        PageObjectModel.alignBackUpCode(dataType, downloadDir, actualFileName);
        List<String> allBackUpCodes = PageObjectModel.readAndGetBackUpCode(downloadDir, actualFileName);
        PageObjectModel.loginGoogle(uc.user, uc.passwd);
        int backUpCodeCount = PageObjectModel.sendBackUpCode(allBackUpCodes, actualFileName, downloadDir);
        PageObjectModel.switchWindow(0);
        PageObjectModel.deleteExistingFile(backUpCodeCount, downloadDir, actualFileName);
        PageObjectModel.iframe(ticket, "xtime.iframeHomePage.xp");
        PageObjectModel.myWait(3000);
        Assert.assertTrue(PageObjectModel.xpDisplay(ticket, "xtime.homePage.xp"), "Home page is not display!");
    }

    @AfterMethod
    public void tearDowns() {
        PageObjectModel.myWait(2000);
        BrowserUtils.tearDown();
    }

    @AfterClass
    public void afterClass() {
        System.out.println("----------------------------Test case ended----------------------------");
    }
}
