package com.testcase;

import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.annotations.Listeners;

import com.pageobject.PageObjectModel;
import com.pageobject.ReUsable;
import com.utils.BrowserUtils;
import com.utils.ResourceUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@Listeners(com.listeners.Listeners.class)
public class SignInGoogleAccount extends BrowserUtils {

	static String downloadDir = BrowserUtils.downloadDir;

	@BeforeClass
	public void beforeClass() {
		System.out.println(ResourceUtils.GREEN_BACKGROUND
				+ "___________________________Test case started___________________________" + ResourceUtils.ANSI_RESET);
	}

	@BeforeMethod
	public void initiateBrowser() {
		BrowserUtils.browserLaunch("Chrome", "",
				"https://consumer-ua9.xtime.com/scheduling/?webKey=x5automp8blockedxx1");
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
		ReUsable.switchWindow(0);
		PageObjectModel.deleteExistingFile(backUpCodeCount, downloadDir, actualFileName);
		ReUsable.iframe(ticket, "xtime.iframeHomePage.xp");
		ReUsable.myWait(5000);
		Assert.assertTrue(ReUsable.xpDisplay(ticket, "xtime.homePage.xp"), "Home page is not display!");
	}

	@AfterMethod
	public void tearDowns() {
		ReUsable.myWait(2000);
		BrowserUtils.tearDown();
	}

	@AfterClass
	public void afterClass() {
		System.out.println(ResourceUtils.GREEN_BACKGROUND
				+ "___________________________Test case ended___________________________" + ResourceUtils.ANSI_RESET);
	}
}
