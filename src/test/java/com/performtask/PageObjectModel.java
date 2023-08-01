package com.performtask;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.testng.Assert;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PageObjectModel extends BrowserUtils {

    public static void myWait(int seconds) {
        try {
            Thread.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void xpSearchProduct(Properties prop, String xpath, String value) {
        driver.findElement(By.xpath(prop.getProperty(xpath))).sendKeys(value, Keys.ENTER);
    }

    public static void xpSend(Properties prop, String xpath, String value) {
        driver.findElement(By.xpath(prop.getProperty(xpath))).sendKeys(value);
    }

    public static List<WebElement> xpSelectiveProduct(Properties prop, String xpath) {
        return driver.findElements(By.xpath(prop.getProperty(xpath)));
    }

    public static void switchWindow(int tabNo) {
        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(tabNo));
    }

    public static void idClick(Properties prop, String id) {
        driver.findElement(By.id(prop.getProperty(id))).click();
    }

    public static void xpClick(Properties prop, String xpath) {
        driver.findElement(By.xpath(prop.getProperty(xpath))).click();
    }

    public static void xpClear(Properties prop, String xpath) {
        driver.findElement(By.xpath(prop.getProperty(xpath))).clear();
    }

    public static String xpGetText(Properties prop, String xpath) {
        return driver.findElement(By.xpath(prop.getProperty(xpath))).getText();
    }

    public static void iframe(Properties prop, String loc) {
        WebElement frameElement = driver.findElement(By.xpath(prop.getProperty(loc)));
        driver.switchTo().frame(frameElement);
    }

    public static boolean xpDisplay(Properties prop, String loc) {
        return driver.findElement(By.xpath(prop.getProperty(loc))).isDisplayed();
    }

    public static void userName(Properties prop, String value) {

        for (Object key : prop.keySet()) {
            System.out.println(key + ": " + prop.getProperty(key.toString()));
        }
    }

    //Google 2 step verification

    public static String getFileName(String downloadPath, String user) {
        String email = user.replaceAll("@gmail.com", "");
        String actualFileName = null;
        File file = new File(downloadPath);
        File[] files = file.listFiles();
        for (File fil : files) {
            if (fil.getName().contains("Backup-codes-" + email))
                actualFileName = fil.getName();
        }
        return actualFileName;
    }

    public static void alignBackUpCode(String value, String downloadPath, String actualFileName) {
        if (value.equals("SAVE YOUR BACKUP CODES")) {
            List<String> list = new ArrayList<>();
            int count = 0;
            String backUpCodes;
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(downloadPath + "//" + actualFileName));
                while ((backUpCodes = bufferedReader.readLine()) != null) {
                    count++;
                    if (count > 3 && count < 9) {
                        String[] split = backUpCodes.split("\t\t ");
                        for (String values : split) {
                            String[] filter1 = values.split("\t\t");
                            for (String filter2 : filter1) {
                                String space = filter2.replaceAll("[A-Za-z ]", "");
                                String[] filter3 = space.split("\\.");
                                for (int i = 0; i < filter3.length; i++) {
                                    if (i % 2 != 0) {
                                        String backUpCode = filter3[i];
                                        list.add(backUpCode);
                                    }
                                }
                            }
                        }
                    }
                }
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(downloadPath + "//" + actualFileName));
                for (String code : list) {
                    bufferedWriter.write(code);
                    bufferedWriter.newLine();
                }
                bufferedWriter.close();
                bufferedReader.close();

            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public static Integer sendBackUpCode(List allBackUpCodes, String actualFileName, String downloadPath) {
        int backUpCodeCount = 0;
        for (int i = 0; i < allBackUpCodes.size(); i++) {
            PageObjectModel.xpClear(ticket, "xtime.enterBackUpCode.xp");
            String digitCode = allBackUpCodes.get(i).toString();
            PageObjectModel.xpClear(ticket, "xtime.enterBackUpCode.xp");
            PageObjectModel.xpSend(ticket, "xtime.enterBackUpCode.xp", digitCode);
            PageObjectModel.xpClick(ticket, "xtime.nextBtn.xp");
            try {
                myWait(2000);
                PageObjectModel.xpDisplay(ticket, "xtime.backUpCodeErrorMsg.xp");
                System.out.println(i + " -8 digit code expire!");
            } catch (Exception e) {
                backUpCodeCount = PageObjectModel.removeBackUpCode(digitCode, actualFileName, downloadPath);
                System.out.println("Logged-In Successfully");
                break;
            }
        }
        return backUpCodeCount;
    }

    public static Integer removeBackUpCode(String code, String actualFileName, String downloadPath) {
        String status;
        List<String> list = new ArrayList<>();
        try {
            myWait(2000);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(downloadPath + "//" + actualFileName));
            while ((status = bufferedReader.readLine()) != null) {
                if (!status.equals(code)) {
                    status.replaceAll("", "");
                    list.add(status);
                } else {
                    list.clear();
                }
            }
            bufferedReader.close();
            BufferedWriter writer = new BufferedWriter(new FileWriter(downloadPath + "//" + actualFileName));
            for (String update : list) {
                writer.write(update);
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list.size();
    }

    public static List<String> readAndGetBackUpCode(String downloadPath, String actualFileName) {
        List<String> listOfBackUpCode = new ArrayList<>();
        String newBackUpCode;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(downloadPath + "//" + actualFileName));
            while ((newBackUpCode = bufferedReader.readLine()) != null) {
                if (!newBackUpCode.equals(""))
                    listOfBackUpCode.add(newBackUpCode);
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listOfBackUpCode;
    }

    public static void deleteExistingFile(int backUpCodeCount, String downloadPath, String actualFileName) {
        if (backUpCodeCount < 1) {
            PageObjectModel.myWait(3000);
            Path path = Paths.get(downloadPath, actualFileName);
            try {
                if (Files.exists(path) == true) {
                    Files.delete(path);
                    if (Files.exists(path) == false)
                        System.out.println("Existing file deleted successfully!");
                }
            } catch (IOException e) {
                System.out.println("File is present!");
                deleteExistingFile(backUpCodeCount, downloadPath, actualFileName);
            }
            PageObjectModel.downloadBackUpCode();
        }
    }

    public static void downloadBackUpCode() {
        ((JavascriptExecutor) driver).executeScript("window.open();");
        PageObjectModel.switchWindow(1);
        driver.navigate().to("https://myaccount.google.com/?hl=en&utm_source=OGB&utm_medium=act");
        Assert.assertTrue(driver.getTitle().equals("Google Account"));
        PageObjectModel.myWait(2000);
        xpClick(ticket, "xtime.security.xp");
        PageObjectModel.xpClick(ticket, "xtime.backUpCode.xp");
        PageObjectModel.xpClick(ticket, "xtime.regenerateCode.xp");
        PageObjectModel.myWait(2000);
        PageObjectModel.xpClick(ticket, "xtime.getNewCode.xp");
        PageObjectModel.myWait(2000);
        driver.navigate().refresh();
        PageObjectModel.myWait(2000);
        PageObjectModel.xpClick(ticket, "xtime.downloadCode.xp");
        System.out.println("8 Digit backup code download successfully!");
        PageObjectModel.myWait(3000);
        driver.close();
        PageObjectModel.switchWindow(0);
    }

    public static void loginGoogle(String email, String password) {
        PageObjectModel.iframe(ticket, "xtime.iframe.xp");
        PageObjectModel.xpClick(ticket, "xtime.signWithGoogleBtn.xp");
        PageObjectModel.myWait(2000);
        PageObjectModel.switchWindow(1);
        PageObjectModel.xpClear(ticket, "xtime.email.xp");
        PageObjectModel.xpSend(ticket, "xtime.email.xp", email);
        PageObjectModel.xpClick(ticket, "xtime.nextBtn.xp");
        PageObjectModel.xpClear(ticket, "xtime.password.xp");
        PageObjectModel.xpSend(ticket, "xtime.password.xp", password);
        System.out.println("password entered successfully");
        PageObjectModel.myWait(2000);
        PageObjectModel.xpClick(ticket, "xtime.nextBtn.xp");
        PageObjectModel.myWait(2000);
        Assert.assertTrue(xpDisplay(ticket, "xtim.verificationPage.xp"), "Unable to view 2step verification page!");
        PageObjectModel.xpClick(ticket, "xtime.tryAnotherWay.xp");
        PageObjectModel.xpClick(ticket, "xtime.bucClick.xp");
    }

    public static void captureScreen(WebDriver driver, String fileName) {
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH-mm");
        String name = dateTime.format(formatter);
        try {
            File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File destFile = new File("./Screenshot/ " + name + "/" + fileName);
            FileUtils.copyFile(source, destFile);
        } catch (IOException e) {

            System.out.println("Failed to capture screenshot: ");
        }
    }
}





