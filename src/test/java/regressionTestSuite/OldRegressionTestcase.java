package regressionTestSuite;

import static org.testng.Assert.ARRAY_MISMATCH_TEMPLATE;
import static org.testng.Assert.assertEquals;

import java.io.Console;
import java.io.IOException;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.testng.internal.collections.Ints;

import junit.framework.Assert;
import pageObjects.AssetPage;
import pageObjects.Calendar;
import pageObjects.ComparePage;
import pageObjects.FavouritePage;
import pageObjects.Header;
import pageObjects.HomePage;
import pageObjects.InvoicePage;
import pageObjects.MyProfilePage;
import pageObjects.Orders;
import pageObjects.PDP;
import pageObjects.PurchaseAnalysis;
import pageObjects.SavedReports;
import pageObjects.SearchResultPage;
import pageObjects.ShippingAddressesPage;
import pageObjects.StandardsCatalog;
import pageObjects.TrackPage;
import resources.base;

public class OldRegressionTestcase extends base {

	public static Logger Log = LogManager.getLogger(base.class.getName());
	public WebDriver driver;

	@SuppressWarnings("null")
	@Test(priority = 0,enabled=false)

	public void MT729() throws IOException, InterruptedException {

		
		//driver = initializeDriver();
		SoftAssert SF = new SoftAssert();
		WebDriverWait wt = new WebDriverWait(driver, 50);
		Header hd = new Header(driver);
		ArrayList<String> ar = new ArrayList<String>();
		String bb;
		String bbb;
		String bb3;
		String bb4;
		String bb5;
		ArrayList<String> arr = new ArrayList<String>();
		ArrayList<String> arr3 = new ArrayList<String>();
		int[] quantlistint = null;
		int[] DesSortedArrAct = null;
		ArrayList<String> quantlist = new ArrayList<String>();
		ArrayList<String> quantlist1 = new ArrayList<String>();
		ArrayList<String> PrtNumLstStr = new ArrayList<String>();
		String quantitytext = null;
		String quantitytext1 = null;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy", Locale.ENGLISH);
		hd.getStanCatalogLink().click();
		SF.assertEquals("Standard Products - MarkITplace", driver.getTitle(),
				"User not able to reach the standard Product Page");
		Log.info("User has reached the Standard Catalog Page");

		Thread.sleep(5000);
		// Add Product to favorite
		int a = driver.findElements(By.xpath("//a[contains(@id,'fav_btn')]")).size();
		if (a > 5) {
			a = 5;
		}
		for (int i = 1; i <= a; i++) {
			driver.findElement(By.xpath("(//a[contains(@id,'fav_btn')])[" + i + "]")).click();
			Thread.sleep(2000);
		}

		hd.getFavrtIcon().click();
		Thread.sleep(5000);
		SF.assertEquals(driver.getTitle(), "Favorites - MarkITplace", "User not able to reach the Favorite Page");
		FavouritePage FP = new FavouritePage(driver);
		SF.assertEquals(FP.getInactiveSelectedBtn().size() > 0, true,
				"Add Selected to Cart button is not appearing disable on favorite Screen when no record is selected");
		SF.assertEquals(FP.getInactiveDelBtn().size() > 0, true,
				"Delete button is not appearing disable on favorite Screen when no record is selected");
		// Add to Cart Check
		int b1 = Integer.parseInt(FP.getShoppingCartIcon().getText());
		Thread.sleep(10000);
		FP.getAdd2Cart().click();
		Thread.sleep(10000);
		wt.until(ExpectedConditions.elementToBeClickable(FP.getCloseCartModel()));
		FP.getCloseCartModel().click();
		Thread.sleep(5000);
		int c1 = Integer.parseInt(FP.getShoppingCartIcon().getText());
		SF.assertEquals(b1 + 1, c1, "Product is not added to cart from the favorite page");
		// Select All check
		FP.getSelectall().click();
		SF.assertEquals(FP.getActiveSelectedBtn().size() > 0, true,
				"Add Selected to Cart button is not appearing enabled on favorite Screen when all the records are selected");
		SF.assertEquals(FP.getActiveDelBtn().size() > 0, true,
				"Delete button is not appearing enabled on favorite Screen when all the records is selected");

		Select se = new Select(FP.getSortBy());
		SF.assertEquals(se.getFirstSelectedOption().getText(), "Date Added",
				"Date Added is not appearing as the Default Selection on Sort By dropdown");
		String pp = FP.getSortBy().getText();
		String finalString = pp.replaceAll("\\s", "");
		SF.assertEquals(finalString.trim(), "DateAddedProductNameMfgPart#Availability",
				"In Sort by DropDown the Options appearing are not as expected");
		// Check Default sorting

		int DateAddedCount = FP.getAddedDateCount().size();
		int counter2 = 1;
		LocalDate[] dates = new LocalDate[FP.getAddedDateCount().size()];
		LocalDate[] dates1 = new LocalDate[FP.getAddedDateCount().size()];
		for (int q3 = 0; q3 < DateAddedCount; q3++) {
			Thread.sleep(2000);
			bb5 = driver.findElement(By.xpath("(//p[@class='added'])[" + counter2 + "]")).getText();
			bb5 = bb5.replace("Added ", "");
			LocalDate date = LocalDate.parse(bb5, formatter);
			dates[q3] = date;
			dates1[q3] = date;
			counter2++;
		}
		Arrays.sort(dates);
		LocalDate[] datesDesc = new LocalDate[FP.getAddedDateCount().size()];
		int count3 = 0;
		for (int i = FP.getAddedDateCount().size() - 1; i >= 0; i--) {
			datesDesc[count3] = dates[i];
			count3++;
		}

		SF.assertEquals(datesDesc, dates1, "The default sorting w.r.t Date added is not working properly");

		// Sorting By Product Name

		int aa = FP.getProductCount().size();
		int r = 1;
		for (int q = 0; q <= aa - 2; q++) {

			bb = driver.findElement(By.xpath("(//a[@class='product-name']/h2)[" + r + "]")).getText();
			ar.add(bb);
			r++;
		}

		String[] arrr = ar.toArray(new String[ar.size()]);
		Arrays.sort(arrr);
		se.selectByVisibleText("Product Name");
		Thread.sleep(5000);

		int aaa = FP.getProductCount().size();
		int rr = 1;
		for (int q = 0; q <= aaa - 2; q++) {

			bbb = driver.findElement(By.xpath("(//a[@class='product-name']/h2)[" + rr + "]")).getText();
			arr.add(bbb);
			rr++;
		}

		String[] arrrUn = ar.toArray(new String[ar.size()]);

		SF.assertNotEquals(arrrUn, arrr, "The Products are not sorted as desired with respect to product name");

		// Sorting By Availability

		int quantcount = FP.getProdQuantityCount().size();
		int n = 1;
		for (int nn = 0; nn <= quantcount - 1; nn++) {

			quantitytext = driver.findElement(By.xpath("(//span[@class='qty'])[" + n + "]")).getText().trim();
			quantitytext = quantitytext.replaceAll("[()]", "");

			quantlist.add(quantitytext);
			n++;
		}

		String[] quantliststr = quantlist.toArray(new String[quantlist.size()]);
		quantlistint = strArrayToIntArray(quantliststr);
		Arrays.sort(quantlistint);

		List<Integer> integersList = Ints.asList(quantlistint);
		Collections.sort(integersList, Collections.reverseOrder());
		Object[] DesSortedArr = integersList.toArray();
		se.selectByVisibleText("Availability");
		Thread.sleep(5000);
		int count1 = 1;
		for (int np = 0; np < quantcount; np++) {

			quantitytext1 = driver.findElement(By.xpath("(//span[@class='qty'])[" + count1 + "]")).getText().trim();
			quantitytext1 = quantitytext1.replaceAll("[()]", "");
			quantlist1.add(quantitytext1);
			count1++;
		}

		String[] quantliststr1 = quantlist1.toArray(new String[quantlist1.size()]);

		DesSortedArrAct = strArrayToIntArray(quantliststr1);
		SF.assertEquals(DesSortedArrAct, DesSortedArr,
				"Products are not correctly sorted with reference to availability");

		// Sorting By MfgPartNumber

		int PartCount = FP.getPartNumCount().size();
		int counter = 1;
		for (int q3 = 0; q3 < PartCount - 1; q3++) {
			Thread.sleep(2000);
			bb3 = driver.findElement(By.xpath("(//span[@class='part-number'])[" + counter + "]")).getText();
			bb3 = bb3.replace("Mfg. Part #: ", "");
			arr3.add(bb3);
			counter++;
		}

		String[] Partnumlstr = arr3.toArray(new String[arr3.size()]);

		Arrays.sort(Partnumlstr);
		se.selectByVisibleText("Mfg Part #");
		int counter1 = 1;
		for (int q3 = 0; q3 < PartCount - 1; q3++) {
			Thread.sleep(2000);
			bb4 = driver.findElement(By.xpath("(//span[@class='part-number'])[" + counter1 + "]")).getText();
			bb4 = bb4.replace("Mfg. Part #: ", "");
			PrtNumLstStr.add(bb4);
			counter1++;
		}
		String[] PrtNumStrarr = PrtNumLstStr.toArray(new String[PrtNumLstStr.size()]);
		SF.assertEquals(PrtNumStrarr, Partnumlstr,
				"The List are not correctly sort with respect to Manufacturer Part Number");

		// Compact view Case Check
		int imagecountact = FP.getImageLinkCount().size();
		FP.getCompactView().click();
		Thread.sleep(2000);
		int imagecountexp = FP.getImageLinkCount().size();
		if (imagecountact > 0) {
			SF.assertEquals(imagecountexp, 0);
		} else {
			;
		}

		// Email

		FP.getEmailIcon().click();
		Thread.sleep(3000);
		FP.getEmailTextBox().sendKeys("bilawal.tsg@gmail.com");
		FP.getEmailSndBtn().click();
		Thread.sleep(1000);
		int countSndTxt = FP.getSendingTextCount().size();
		SF.assertEquals(countSndTxt, 1, "Sending Text is not appearing while sending Email using the email icon");
		Thread.sleep(20000);
		// Delete Check

		FP.getDeleteAllFav().click();
		wt.until(ExpectedConditions.elementToBeClickable(FP.getDeleteDiagYes()));
		Thread.sleep(1000);
		FP.getDeleteDiagYes().click();
		wt.until(ExpectedConditions.elementToBeClickable(FP.getNoRsltTxt()));
		Thread.sleep(8000);
		SF.assertEquals(FP.getNoRsltTxt().getText(), "No Result Found",
				"Products are not getting Deleted From the Favorite Page");

		// Select All Check

		FP.getSelectall().click();
		SF.assertAll();
	}

	public static int[] strArrayToIntArray(String[] a) {
		int[] b = new int[a.length];
		for (int i = 0; i < a.length; i++) {
			b[i] = Integer.parseInt(a[i]);
		}

		return b;

	}

	@Test(priority = 1,enabled=false)
	public void mt839() throws IOException, InterruptedException {

		// driver = initializeDriver();
		SoftAssert SF = new SoftAssert();
		Header hd = new Header(driver);
		hd.getTrackBtn().click();
		Actions an = new Actions(driver);
		TrackPage tp = new TrackPage(driver);
		an.moveToElement(tp.getReportsBtn()).perform();
		tp.getAssetLnk().click();
		Thread.sleep(5000);
		SF.assertEquals(driver.getTitle(), "Assets Report - MarkITplace",
				"Asset Report is not getting Opened After Click on Asset Report Link on the Track Page");
		tp.getRptDropdown().click();
		tp.getVwAssetDetail().click();
		SF.assertEquals(driver.getTitle(), "Asset Details - MarkITplace",
				"User have not reached the Asset Detail Screen after selecting Asset Detail Option from Asset Report");
		Thread.sleep(3000);
		// boolean iip= tp.getUnRetireBtnCount().size() > 0;
		if (tp.getRetireAssetCount().size() > 0 && tp.getHistoryTextCount().size() > 0) {
			tp.getReAssignBtn().click();
		} else if (tp.getUnRetireBtnCount().size() > 0 && tp.getHistoryTextCount().size() > 0) {
			tp.getUnRetireBtn().click();
		} else {
			tp.getAssignBtn();
		}
		Thread.sleep(2000);
		tp.getAssetBtn().click();
		Thread.sleep(15000);
		tp.getRetireAsset().click();
		Thread.sleep(3000);
		int assetcount = tp.getpostRetireAssetCount().size();
		SF.assertEquals(assetcount, 1, "When clicking on Retire Asset then action is not performing");
		int hiscount = tp.getAssetHistoryItem().size();
		boolean t;
		if (hiscount > 0) {
			t = true;
		} else {
			t = false;
		}
		// int hiscount=tp.getAssetHistoryItem().size();
		SF.assertEquals(true, t, "Asset History is not getting populated");
		SF.assertAll();
	}

	@Test(priority = 2,enabled=false)
	public void MT1535() throws IOException, InterruptedException {
		// driver = initializeDriver();
		SoftAssert SF = new SoftAssert();
		Header hd = new Header(driver);

		TrackPage tp = new TrackPage(driver);
		Thread.sleep(10000);
		hd.getTrackBtn().click();
		Actions an = new Actions(driver);
		an.moveToElement(tp.getReportsBtn()).perform();
		String[] expRptName = { "Assets", "Days to Ship", "Expiration", "Inventory", "Invoices", "Invoice Line",
				"Licenses", "Orders", "Order Line", "Quotes", "Receiving Log", "Shipments", "Standards", "Warranties" };
		int p = 0;
		for (int i = 23; i < 36; i++) {
			String act = driver.findElement(By.xpath("(//a[contains(@href,'/track/reports')])[" + i + "]")).getText()
					.trim();
			SF.assertEquals(act, expRptName[p]);
			p++;
		}
		tp.getAssetLnk().click();
		Thread.sleep(5000);
		// int i = tp.getReportRegexpCount().size();
		// System.out.println(i);
		SF.assertEquals(driver.getTitle(), "Assets Report - MarkITplace", "Asset Report is not opening");
		AssetPage AP = new AssetPage(driver);
		Select se = new Select(AP.getVwDropDown());
		se.selectByVisibleText("All Assets");
		Thread.sleep(10000);
		se = new Select(AP.getVwDropDown());

		String PreNavig = se.getFirstSelectedOption().getText();
		AP.get2ndPagination().click();
		Thread.sleep(3000);
		se = new Select(AP.getVwDropDown());
		String PostNavig = se.getFirstSelectedOption().getText();
		SF.assertEquals(PostNavig, PreNavig,
				"Values of the report view is getting change when we are nevigating thorogh pages in reports");
		AP.getColumn1stDropdown().click();
		Thread.sleep(10000);
		AP.getAPCRow().click();
		AP.getApplyBtn().click();
		Thread.sleep(5000);
		// AP.getRowRecord1st().getText();
		SF.assertEquals("APC", AP.getRowRecord1st().getText(),
				"Records are not sorted as per Column header selected value from the dropdown");
		AP.getSaveBtn().click();
		Random rm = new Random();
		int i = rm.nextInt(1000) + 1;
		AP.getNameTextBox().sendKeys("Asset" + i + "_Automate");
		AP.getSaveDefaultCheck().click();
		AP.getSaveRpt().click();
		Thread.sleep(10000);
		se = new Select(AP.getVwDropDown());

		SF.assertEquals("Asset" + i + "_Automate", se.getFirstSelectedOption().getText(),
				"New Report is not Save with new name");
		an.moveToElement(tp.getReportsBtn()).perform();
		tp.getAssetLnk().click();
		Thread.sleep(5000);
		se = new Select(AP.getVwDropDown());
		SF.assertEquals("Asset" + i + "_Automate", se.getFirstSelectedOption().getText(),
				"New Report is not Appearing as a Default report when openning the Asset report");

		an.moveToElement(tp.getReportsBtn()).perform();
		tp.getDay2ShipRpt().click();
		Thread.sleep(10000);
		SF.assertEquals(driver.getTitle(), "Days To Ship Report - MarkITplace");
		se = new Select(driver.findElement(By.id("reportViewSelect")));
		se.getFirstSelectedOption().getText();

		SF.assertEquals("Days To Ship", se.getFirstSelectedOption().getText(),
				"When accessing Days To Ship Report then Days To Ship view is not selected ");
		AP.get2ndPagination().click();
		Thread.sleep(10000);

		se = new Select(driver.findElement(By.id("reportViewSelect")));
		SF.assertEquals("Days To Ship", se.getFirstSelectedOption().getText(),
				"When accessing Days To Ship Report then Days To Ship view is not selected by default");
		SF.assertAll();
	}

	@Test(priority = 3,enabled=false)
	public void MT1526() throws IOException, InterruptedException {
		 driver = initializeDriver();
		SoftAssert SF = new SoftAssert();
		WebDriverWait wt = new WebDriverWait(driver, 50);
		Header hd = new Header(driver);

		Thread.sleep(10000);
		// TrackPage tp = new TrackPage(driver);
		hd.getTrackBtn().click();
		hd.getOrderBtn().click();
		Orders Or = new Orders(driver);
		SF.assertEquals(driver.getTitle(), "Orders - MarkITplace", "User not able to reach the Order Page");
		Select se = new Select(Or.getDaysDropDown());
		// se.getFirstSelectedOption().getText().trim();
		SF.assertEquals(se.getFirstSelectedOption().getText().trim(), "Past 30 days",
				"Past 30 Days is not selected as Dropdown of Order days filters");

		int counter2 = 1;
		String DateText;
		LocalDate Lastmonth = LocalDate.now().minusDays(30);
		LocalDate[] dates = new LocalDate[Or.getDateRecordCount().size()];
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy", Locale.ENGLISH);
		for (int q3 = 0; q3 < Or.getDateRecordCount().size() - 1; q3++) {
			Thread.sleep(2000);
			DateText = driver.findElement(By.xpath("(//div[@class='title-row']/div[1]/div[2]/p)[" + counter2 + "]"))
					.getText().trim();

			LocalDate date = LocalDate.parse(DateText, formatter);
			dates[q3] = date;
			counter2++;
			SF.assertEquals(true, dates[q3].isAfter(Lastmonth),
					"The values appearing are of before 30 days month although past 30 days are selected as filter");
		}

		se.selectByVisibleText("Past 60 days");
		Thread.sleep(4000);

		int counter3 = 1;
		String DateText1;
		LocalDate Last2month = LocalDate.now().minusDays(60);
		LocalDate[] dates1 = new LocalDate[Or.getDateRecordCount().size()];
		for (int q3 = 0; q3 < Or.getDateRecordCount().size() - 1; q3++) {
			Thread.sleep(2000);
			DateText1 = driver.findElement(By.xpath("(//div[@class='title-row']/div[1]/div[2]/p)[" + counter3 + "]"))
					.getText().trim();

			LocalDate date = LocalDate.parse(DateText1, formatter);
			dates1[q3] = date;
			counter3++;
			SF.assertEquals(true, dates1[q3].isAfter(Last2month),
					"The values appearing are of before 60 days although past 60 days are selected as filter");
		}

		se.selectByVisibleText("Past 90 days");
		Thread.sleep(4000);

		int counter4 = 1;
		String DateText2;
		LocalDate Last3month = LocalDate.now().minusDays(90);
		LocalDate[] dates3 = new LocalDate[Or.getDateRecordCount().size()];
		for (int q3 = 0; q3 < Or.getDateRecordCount().size() - 1; q3++) {
			Thread.sleep(2000);
			DateText2 = driver.findElement(By.xpath("(//div[@class='title-row']/div[1]/div[2]/p)[" + counter4 + "]"))
					.getText().trim();

			LocalDate date = LocalDate.parse(DateText2, formatter);
			dates3[q3] = date;
			counter4++;
			SF.assertEquals(true, dates3[q3].isAfter(Last3month),
					"The values appearing are of before 90 days although past 90 days are selected as filter");
		}

		se.selectByVisibleText("Past 6 months");
		Thread.sleep(4000);
		int counter5 = 1;
		String DateText3;
		LocalDate Last6month = LocalDate.now().minusMonths(6);
		LocalDate[] dates4 = new LocalDate[Or.getDateRecordCount().size()];
		for (int q3 = 0; q3 < Or.getDateRecordCount().size() - 1; q3++) {
			Thread.sleep(2000);
			DateText3 = driver.findElement(By.xpath("(//div[@class='title-row']/div[1]/div[2]/p)[" + counter5 + "]"))
					.getText().trim();

			LocalDate date = LocalDate.parse(DateText3, formatter);
			dates4[q3] = date;
			counter5++;
			SF.assertEquals(true, dates3[q3].isAfter(Last6month),
					"The values appearing are of before 6 months although past 6 months are selected as filter");
		}

		se.selectByVisibleText("2018");
		Thread.sleep(4000);

		int counter2018 = 1;
		String DateText2018;
		LocalDate From2018 = LocalDate.of(2018, 1, 1);
		LocalDate[] dates2018 = new LocalDate[Or.getDateRecordCount().size()];
		for (int q3 = 0; q3 < Or.getDateRecordCount().size() - 1; q3++) {
			Thread.sleep(2000);
			DateText2018 = driver
					.findElement(By.xpath("(//div[@class='title-row']/div[1]/div[2]/p)[" + counter2018 + "]")).getText()
					.trim();

			LocalDate date = LocalDate.parse(DateText2018, formatter);
			dates2018[q3] = date;
			counter2018++;
			SF.assertEquals(true, dates2018[q3].isAfter(From2018),
					"The values appearing are of before 2018 although 2018 is selected in year filter");
		}

		se.selectByVisibleText("2017");
		Thread.sleep(4000);

		int counter2017 = 1;
		String DateText2017;
		LocalDate From2017 = LocalDate.of(2017, 1, 1);
		LocalDate[] dates2017 = new LocalDate[Or.getDateRecordCount().size()];
		for (int q3 = 0; q3 < Or.getDateRecordCount().size() - 1; q3++) {
			Thread.sleep(2000);
			DateText2017 = driver
					.findElement(By.xpath("(//div[@class='title-row']/div[1]/div[2]/p)[" + counter2017 + "]")).getText()
					.trim();

			LocalDate date = LocalDate.parse(DateText2017, formatter);
			dates2017[q3] = date;
			counter2017++;
			SF.assertEquals(true, dates2017[q3].isAfter(From2017),
					"The values appearing are of before 2017 although 2018 is selected in year filter");
		}

		se.selectByVisibleText("2016");
		Thread.sleep(5000);

		int counter2016 = 1;
		String DateText2016;
		LocalDate From2016 = LocalDate.of(2016, 1, 1);
		LocalDate[] dates2016 = new LocalDate[Or.getDateRecordCount().size()];
		for (int q3 = 0; q3 < Or.getDateRecordCount().size() - 1; q3++) {
			Thread.sleep(2000);
			DateText2016 = driver
					.findElement(By.xpath("(//div[@class='title-row']/div[1]/div[2]/p)[" + counter2016 + "]")).getText()
					.trim();

			LocalDate date = LocalDate.parse(DateText2016, formatter);
			dates2016[q3] = date;
			counter2016++;
			SF.assertEquals(true, dates2016[q3].isAfter(From2016),
					"The values appearing are of before 2016  although 2016 is selected in year filter");
		}
		Or.getPlus1st().click();
		Thread.sleep(5000);
		SF.assertEquals(Or.getPlus1st().isDisplayed(), true,
				"Request Return Button is not appearing when expanding the first record of the order page");

		SF.assertEquals(Or.getBuyAgainBtn().isDisplayed(), true,
				"Buy Iem Again Button is not appearing when expanding the first record of the order page");

		Or.getReqReturn1st().click();
		Thread.sleep(2000);
		SF.assertEquals(Or.getReturnTextBox1().isDisplayed(), true,
				"Request Return Form is not openning when clicking on Request Return Button");
		Or.getReturnTextBox1().sendKeys("Test");

		Or.getReqReturnSubmitBtn().click();
		Thread.sleep(7000);
		Or.getRmaCloseBtn1().click();
		Thread.sleep(10000);
		Or.getBuyItemAgain1().click();
		wt.until(ExpectedConditions.elementToBeClickable(Or.getBuyItemClose()));
		Thread.sleep(15000);
		SF.assertEquals(Or.getBuyItemClose().isDisplayed(), true,
				"Add to Cart Module is not displaying when clicking on But Item Again Button");

		Or.getBuyItemClose().click();
		Thread.sleep(20000);
		if (Or.getRowRecordCount().size() > 7) {
			Or.getLoadMoreBtn().click();
			Thread.sleep(10000);
			int b2 = Or.getRowRecordCount().size();
			SF.assertEquals(b2, 15, "All the records are not showing when clicking on Load More Button");
		} else {
			// SF.assertEquals(, 15, "All the records are not showing when clicking on Load
			// More Button");
			SF.assertEquals(Or.getRowRecordCount().size() <= 7, true, "More than 8 records are displaying on the Page");
		}

		// System.out.println("test 1526 End");
		SF.assertAll();
	}

	@Test(priority = 4,enabled=false)

	public void MT728() throws IOException, NoSuchElementException, InterruptedException {
		// driver = initializeDriver();
		SoftAssert SF = new SoftAssert();
		Header hd = new Header(driver);
		WebDriverWait wt = new WebDriverWait(driver, 100);
		System.out.println("Test MT728 Start");
		// Actions an = new Actions(driver);
		// 26june-hd.getShopIcon();
		hd.getHeadLogo().click();
		Thread.sleep(8000);
		SF.assertEquals(driver.getTitle(), "Shop - MarkITplace", "User not able to reach the shop Page");

		hd.getStanCatalogLink().click();
		Thread.sleep(10000);
		PDP pp = new PDP(driver);

		SF.assertEquals("Standard Products - MarkITplace", driver.getTitle(),
				"User not able to reach the standard Product Page");
		Log.info("User has reached the Standard Catalog Page");
		StandardsCatalog SC = new StandardsCatalog(driver);
		Thread.sleep(10000);
		SC.getProduct2New().click();
		SF.assertEquals(driver.getTitle(), "HP Officejet Pro 8710 All-in-One - Multifunction printer - MarkITplace",
				"User not able to reach Standard Detail Page");
		Log.info("User has reached the PDP page after clicking on the first bundle Product");
		pp = new PDP(driver);
		pp.getfavrtlink().click();
		wt.until(ExpectedConditions.visibilityOf(pp.getCartbtn()));
		Thread.sleep(5000);
		int b3 = Integer.parseInt(pp.getShoppingCartIcon().getText());
		pp.getCartbtn().click();
		// Add to Cart Check

		Thread.sleep(10000);

		wt.until(ExpectedConditions.visibilityOf(pp.getCartClose()));

		pp.getCartClose().click();

		int c3 = Integer.parseInt(pp.getShoppingCartIcon().getText());
		SF.assertEquals(b3 + 1, c3, "Product is not added to cart from the favorite page");

		// Assert.assertEquals(false, pp.getCartClose().isDisplayed());
		Log.info("User has successfully add the Standard Product to the cart");
		wt.until(ExpectedConditions.visibilityOf(pp.getemaillink()));
		pp.getemaillink().click();
		wt.until(ExpectedConditions.visibilityOf(pp.getemailbox()));
		pp.getemailbox().sendKeys("bilawal.tsg@gmail.com");
		SF.assertEquals(true, pp.getemailbox().isDisplayed(), "Email Module is not appearing");
		Log.info("After clicking on the email link the email dialog box has open");
		pp.getSendBtn().click();
		Thread.sleep(1000);
		FavouritePage fp = new FavouritePage(driver);
		int countSndTxt = fp.getSendingTextCount().size();
		SF.assertEquals(countSndTxt, 1, "Sending Text is not appearing while sending Email using the email icon");
		wt.until(ExpectedConditions.invisibilityOf(pp.getemailbox()));
		SF.assertEquals(false, pp.getemailbox().isDisplayed());
		Log.info("User is able to send the email from the standad catalog PDP page");

		SF.assertAll();
	}

	@Test(priority = 5,enabled=false)

	public void MT730() throws IOException, InterruptedException {
		// driver = initializeDriver();
		WebDriverWait wt = new WebDriverWait(driver, 50);

		SoftAssert SF = new SoftAssert();
		PDP pp = new PDP(driver);
		pp.getheadlogo().click();
		SF.assertEquals("Shop - MarkITplace", driver.getTitle(), "user not able to reach the shop page");
		Header hd = new Header(driver);
		hd.getsearchbox().sendKeys("printers" + Keys.ENTER);
		Log.info("user is searching printer using the search textbox");
		SF.assertEquals("Printers - MarkITplace", driver.getTitle(),
				"user not able to search the printers using the search box");

		Thread.sleep(20000);
		SearchResultPage SRP = new SearchResultPage(driver);
		SRP.getProductCart4().click();
		SF.assertEquals(driver.getTitle(), "HP Color LaserJet Pro M452dn - Printer - MarkITplace",
				"PDP page is not openning");
		SRP.getprod4fav().click();
		Thread.sleep(30000);
		String abd1 = SRP.getprod4fav().getText();
		SF.assertTrue(abd1.equalsIgnoreCase("Added To Favorites"));
		Log.info("Product has successfully added to Favorites");
		pp.getemaillink().click();
		wt.until(ExpectedConditions.visibilityOf(pp.getemailbox()));
		pp.getemailbox().sendKeys("bilawal.tsg@gmail.com");
		SF.assertEquals(true, pp.getemailbox().isDisplayed(), "Email Module is not appearing");
		Log.info("After clicking on the email link the email dialog box has open");
		pp.getSendBtn().click();
		FavouritePage fp = new FavouritePage(driver);
		int countSndTxt = fp.getSendingTextCount().size();
		SF.assertEquals(countSndTxt, 1, "Sending Text is not appearing while sending Email using the email icon");

		wt.until(ExpectedConditions.invisibilityOf(pp.getemailbox()));
		Assert.assertEquals(false, pp.getemailbox().isDisplayed());
		SF.assertAll();
	}

	@Test(priority = 6,enabled=false)

	public void MT731() throws IOException, InterruptedException {
		 //driver = initializeDriver();
		SoftAssert SF = new SoftAssert();
		Header hd = new Header(driver);

		hd.getHeadLogo().click();
		Thread.sleep(5000);
		SF.assertEquals("Shop - MarkITplace", driver.getTitle(), "user not able to reach the shop page");

		Actions an = new Actions(driver);
		WebDriverWait wt = new WebDriverWait(driver, 50);
		SearchResultPage srp = new SearchResultPage(driver);
		HomePage hp = new HomePage(driver);
		String Exp = "Product Compare - MarkITplace";
		String act = null;
		Thread.sleep(10000);
		hp.getsearchbox().sendKeys("printers" + Keys.ENTER);
		Log.info("user is searching printer using the search textbox");
		Thread.sleep(20000);

		SF.assertEquals("Printers - MarkITplace", driver.getTitle(),
				"user not able to search the printers using the search box");

		srp.getCompare1().click();
		srp.getCompare2().click();
		srp.getCompareBtn().click();
		Thread.sleep(15000);
		act = driver.getTitle();
		SF.assertEquals(Exp, act, "User has not reached the compare Page");
		Log.info("User Have Successfully reached the Compare page");
		ComparePage Cp = new ComparePage(driver);
		Cp.getEmailLink().click();
		Cp.getEmailTxtbox().sendKeys("bilawal.tsg@gmail.com");
		Cp.getSendbtn().click();
		SF.assertEquals(true, Cp.getSendingbtn().isDisplayed(),
				"User not able to Send the email from the Product Compare Page");

		FavouritePage fp = new FavouritePage(driver);
		int countSndTxt = fp.getSendingTextCount().size();
		SF.assertEquals(countSndTxt, 1, "Sending Text is not appearing while sending Email using the email icon");

		an.moveToElement(Cp.getfavLink()).click().build().perform();

		SearchResultPage SRP = new SearchResultPage(driver);
		String abd1 = SRP.getprod4fav1().getText();
		SF.assertTrue(abd1.equalsIgnoreCase("Added To Favorites"));
		Log.info("Product has successfully added to Favorites");

		wt.until(ExpectedConditions.elementToBeClickable(Cp.getAdd2Cart()));
		PDP pp = new PDP(driver);
		Thread.sleep(5000);
		int b4 = Integer.parseInt(pp.getShoppingCartIcon().getText());

		Cp.getAdd2Cart().click();

		wt.until(ExpectedConditions.visibilityOf(Cp.getCloselink()));
		Cp.getCloselink().click();
		int c4 = Integer.parseInt(pp.getShoppingCartIcon().getText());
		SF.assertEquals(b4 + 1, c4, "Product is not added to cart from the favorite page");

		wt.until(ExpectedConditions.visibilityOf(Cp.getremovelink()));
		//Cp.getremovelink().click();
		an.moveToElement(Cp.getremovelink()).perform();
		Cp.getremovelink().click();
		
		Thread.sleep(20000);
		SF.assertEquals(driver.getTitle(), "Printers - MarkITplace",
				"User is not able to remove the product from the compare Page");
		
		// SF.assertEquals(Cp.getCompare1stImageCount().size() > 0 ,false ,"Product is
		// not getting deleted when clicking on the remove button with the product");
		// SF.assertEquals(actual, expected);
		SF.assertAll();
	}

	@Test(priority = 7,enabled=false)
	public void MT837() throws IOException, InterruptedException {
		// driver = initializeDriver();
		SoftAssert SF = new SoftAssert();
		WebDriverWait wt = new WebDriverWait(driver, 50);
		Header hd = new Header(driver);
		hd.getHeadLogo().click();
		SF.assertEquals(driver.getTitle(), "Shop - MarkITplace", "User have not reached the Shop Page");
		wt.until(ExpectedConditions.elementToBeClickable(hd.getStanCatalogLink()));
		hd.getStanCatalogLink().click();
		SF.assertEquals(driver.getTitle(), "Standard Products - MarkITplace",
				"User have not reached the Standard Catalog Page from SHop Page");
		StandardsCatalog SC = new StandardsCatalog(driver);
		wt.until(ExpectedConditions.elementToBeClickable(SC.getListView()));
		SC.getListView().click();
		Thread.sleep(10000);
		SF.assertEquals(false, SC.getListViewDspCount().size() > 0,
				"Products are not appearing as List after clicking on List view button");
		wt.until(ExpectedConditions.elementToBeClickable(SC.getGridView()));
		SC.getGridView().click();
		Thread.sleep(10000);
		SF.assertEquals(true, SC.getListViewDspCount().size() > 0,
				"Products are not appearing as Grid after clicking on Grid view button");
		wt.until(ExpectedConditions.elementToBeClickable(SC.getQtybox()));
		SC.getQtybox().sendKeys("2");

		wt.until(ExpectedConditions.elementToBeClickable(SC.getfavrtBtn()));
		SC.getfavrtBtn().click();
		wt.until(ExpectedConditions.elementToBeClickable(SC.gettablet()));
		SC.gettablet().click();
		Thread.sleep(8000);
		SF.assertEquals(driver.getTitle(), "Standard Products - MarkITplace",
				"User have not reached the tablet Page after clicking on Tablet category from Standard Catalog Page");
	}

	@Test(priority = 8,enabled=false)
	public void MT840() throws IOException, InterruptedException {
		// driver = initializeDriver();
		SoftAssert SF = new SoftAssert();

		WebDriverWait wt = new WebDriverWait(driver, 50);

		Header hd = new Header(driver);
		hd.getTrackBtn().click();
		Log.info("User has clicked on the Track Nevigation Icon");
		TrackPage tp = new TrackPage(driver);
		wt.until(ExpectedConditions.elementToBeClickable(tp.getBuyItAgain()));

		tp.getBuyItAgain().click();
		Log.info("User has clicked on the Buy Item Again Button");
		// Thread.sleep(10000);

		wt.until(ExpectedConditions.elementToBeClickable(tp.getCloseBtn()));
		SF.assertEquals(tp.getCloseBtn().isDisplayed(), true,
				"Buy Item Again Model has not Opened after clicking on the Buy Item Again Button");
		wt.until(ExpectedConditions.elementToBeClickable(tp.getCloseBtn()));

		tp.getCloseBtn().click();
		Log.info("User has clicked on the close icon on the Buy Item Again Modal");

		wt.until(ExpectedConditions.elementToBeClickable(tp.getCircle1()));
		tp.getCircle1().click();
		Thread.sleep(1000);
		Log.info("User has clicked on the Calendar Circle");
		String a = driver.getTitle();
		String b = "Calendar Monthly View - MarkITplace";

		SF.assertEquals(a, b);

		driver.navigate().back();
		// Assert.assertEquals(b, a);

		wt.until(ExpectedConditions.elementToBeClickable(tp.getLink1()));
		tp.getLink1().click();
		Log.info("User has clicked on the first Link from the calendar Section");

		Thread.sleep(5000);
		String ux = driver.getTitle();

		SF.assertEquals("Calendar Detail View - MarkITplace", ux);
		// Set<String> abc=driver.getWindowHandles();
		// Iterator<String> it=abc.iterator();
		// System.out.println(a);
		// while(it.hasNext())
		// {
		// driver.switchTo().window(it.next());
		// String ux = driver.getTitle();
		// Assert.assertEquals("Calendar Detail View - MarkITplace", ux);
		// System.out.println(ux);
		// }

		SF.assertAll();
	}

	@Test(priority = 9)
	public void MT1519() throws IOException, InterruptedException {
		 driver = initializeDriver();
		SoftAssert SF = new SoftAssert();

		WebDriverWait wt = new WebDriverWait(driver, 50);
		MyProfilePage mpp = new MyProfilePage(driver);
		mpp.getAccountDropdown().click();
		Log.info("User has Mouse hover on My Account Icon in the header");
		mpp.getMyProfileLinkLink().click();
		Log.info("User has clicked on the My Profile page from My Account dropdown");
		Thread.sleep(8000);
		SF.assertEquals(driver.getTitle(), "My Profile - MarkITplace", "User is not able to reach the My Profile Page");
		mpp.getEditProfBtn().click();
		Thread.sleep(8000);
		Log.info("User has clicked Edit Profile button");
		wt.until(ExpectedConditions.elementToBeClickable(mpp.getHomePageTextbox()));
		SF.assertEquals(driver.getTitle(), "My Profile Edit - MarkITplace",
				"User is not able to reach the edit profile Page");
		Select sc = new Select(mpp.getHomePageTextbox());

		String act1 = sc.getFirstSelectedOption().getText();
		String exp1 = "Shop Home Page";
		SF.assertEquals(act1, exp1, "Shop Home Page is not selected as a default option");

		sc.selectByIndex(2);

		// String act= sc.getFirstSelectedOption().getText();

		// String exp = "Standard Catalog";

		// SF.assertEquals(act, exp);

		mpp.getSaveBtn().click();
		Thread.sleep(10000);

		SF.assertEquals("My home page: Standard Catalog", mpp.getinfo().getText());

		// System.out.println(mpp.getinfo().getText());

		Header hd = new Header(driver);
		hd.getLogo().click();
		String act2 = driver.getTitle();
		String exp2 = "Standard Products - MarkITplace";

		SF.assertEquals(act2, exp2, "User have not reached the Standard Product Page");

		mpp.getAccountDropdown().click();
		Log.info("User has Mouse hover on My Account Icon in the header 2nd time");
		mpp.getMyProfileLinkLink().click();
		Log.info("User has clicked on the My Profile page from My Account dropdown 2nd time");
		mpp.getEditProfBtn().click();
		Thread.sleep(5000);
		Log.info("User has clicked Edit Profile button 2nd time");
		wt.until(ExpectedConditions.elementToBeClickable(mpp.getHomePageTextbox()));
		Select sc1 = new Select(mpp.getHomePageTextbox());
		sc1.selectByIndex(0);
		Log.info("User has selected the Home Page as Shop Page again");
		mpp.getSaveBtn().click();
		Log.info("User has Again clicked the SAve Button");
		Thread.sleep(15000);
		SF.assertAll();
	}

	@Test(priority = 10,enabled=false)
	public void MT1520() throws IOException, InterruptedException {
		SoftAssert SF = new SoftAssert();
		WebDriverWait wt = new WebDriverWait(driver, 50);
		Header hd = new Header(driver);
		hd.getShopIcon().click();
		Log.info("User has Clicked on the SHop Icon from the Header");
		HomePage hp = new HomePage(driver);
		wt.until(ExpectedConditions.elementToBeClickable(hp.getRecentlyViewedLink()));
		hp.getRecentlyViewedLink().click();
		Log.info("User has Clicked on the recently viewed Link on the Home Page");
		String exp1 = "Recently Viewed Products - MarkITplace";
		String ac1 = driver.getTitle();
		SF.assertEquals(ac1, exp1);
		Log.info(
				"User has reached the Recently viewed screen after clicking on the recently view link from the home page");
		SF.assertAll();

	}

	@Test(priority = 11,enabled=false)
	public void MT1521() throws IOException, InterruptedException {

		//driver = initializeDriver();
		SoftAssert SF = new SoftAssert();
		try {
			Random rand = new Random();
			int val = rand.nextInt(999);
			WebDriverWait wt = new WebDriverWait(driver, 50);
			MyProfilePage mpp = new MyProfilePage(driver);
			mpp.getAccountDropdown().click();
			Log.info("User has Mouse hover on My Account Icon in the header");
			mpp.getShippingAddressLink().click();
			SF.assertEquals(driver.getTitle(), "Shipping Address - MarkITplace",
					"User not able to reach the Shipping Address Page");
			ShippingAddressesPage SAP = new ShippingAddressesPage(driver);
			SAP.getAdd_NewAddressBtn().click();

			wt.until(ExpectedConditions.elementToBeClickable(SAP.getLocationTextbox()));
			SAP.getLocationTextbox().sendKeys("Test");
			SAP.getAddress().sendKeys("355 N Maple DR");
			SAP.getCity().sendKeys("Baverly Hills");
			Select Se = new Select(SAP.getstate());
			Se.selectByVisibleText("CA");
			SAP.getzip().sendKeys("54321-5" + val);
			SAP.getPhone().sendKeys(val + "2587" + val);
			SAP.getSaveBtn().click();
			Thread.sleep(5000);
			// wt.until(ExpectedConditions.elementToBeClickable(SAP.getSuccessDialog()));

			SF.assertEquals(SAP.getSuccessDialog().isDisplayed(), true,
					"User not able to successfull add the new address in the list");

			SAP.getCloseBtn().click();
		}

		
		
		catch (Exception e) {
			SF.assertEquals(false, true,
					"The Test case MT1521 is failed due to we got the exception in between the test run which can be identify by the Logs");
			Log.error(e.getMessage());
		}
		
		SF.assertAll();

	}

	@Test(priority = 12,enabled=false)

	public void MT1522() throws IOException, InterruptedException {
		// driver = initializeDriver();
		// Actions an = new Actions(driver);
		SoftAssert SF = new SoftAssert();
		WebDriverWait wt = new WebDriverWait(driver, 100);
		Header hd = new Header(driver);
		hd.getTrackBtn().click();
		TrackPage tp = new TrackPage(driver);
		SF.assertEquals(driver.getTitle(), "Track Dashboard - MarkITplace");
		tp.getQuickFinder().sendKeys("10359164" + Keys.ENTER);
		Thread.sleep(30000);

		SF.assertEquals(true, tp.getSearchResultCount().size() > 0,
				"Search is not returning any data on the track Page");
		// wt.until(ExpectedConditions.visibilityOf(tp.getSearchResult()));
		// SF.assertEquals(true, tp.getSearchResult().isDisplayed(), "Search is not
		// returning any data on the track Page");
		wt.until(ExpectedConditions.elementToBeClickable(tp.getViewOrder()));
		tp.getViewOrder().click();
		SF.assertEquals(driver.getTitle(), "Order Details - MarkITplace", "order details Page is not being Accessed");
		Orders od = new Orders(driver);
		wt.until(ExpectedConditions.elementToBeClickable(od.getDownloads()));
		od.getDownloads().click();
		Thread.sleep(10000);
		wt.until(ExpectedConditions.elementToBeClickable(od.getemail()));
		od.getemail().click();
		wt.until(ExpectedConditions.visibilityOf(od.getemailtxtbox()));
		od.getemailtxtbox().sendKeys("bilawal.tsg@gmail.com");
		wt.until(ExpectedConditions.elementToBeClickable(od.getSndBtn()));
		od.getSndBtn().click();

		Thread.sleep(10000);

		SF.assertEquals(od.getemailDialog().isDisplayed(), false, "User not able to send email from the Order Page");
		wt.until(ExpectedConditions.elementToBeClickable(od.getRequestReturnBtn()));
		od.getRequestReturnBtn().click();

		wt.until(ExpectedConditions.elementToBeClickable(od.getReturnTextbox()));

		od.getReturnTextbox().sendKeys("Test");
		od.getSubmitReturn().click();

		wt.until(ExpectedConditions.invisibilityOf(od.getSubmitReturn()));
		// Thread.sleep(10000);
		Thread.sleep(5000);
		SF.assertEquals(od.getReturnConfirmPopup().isDisplayed(), true,
				"User not able to submit request return from the order Page");
		wt.until(ExpectedConditions.visibilityOf(od.getReturnConfirmClose()));
		od.getReturnConfirmClose().click();
		Thread.sleep(5000);
		wt.until(ExpectedConditions.elementToBeClickable(od.getBuyItemAgainBtn()));
		od.getBuyItemAgainBtn().click();
		Thread.sleep(5000);

		wt.until(ExpectedConditions.elementToBeClickable(od.getBuyItemAgainPopupClose()));

		SF.assertEquals(od.getBuyItemAgainPopup().isDisplayed(), true,
				"Buy Item Again Popup is not openning on the order Page");
		od.getBuyItemAgainPopupClose().click();
		Thread.sleep(5000);
		wt.until(ExpectedConditions.elementToBeClickable(od.getViewInvoiceBtn()));
		od.getViewInvoiceBtn().click();

		SF.assertEquals(driver.getTitle(), "Invoices - MarkITplace",
				"Invoice Page is not Reached from Order Page when clicking on View Invoice Button");
		InvoicePage IP = new InvoicePage(driver);
		Thread.sleep(5000);
		wt.until(ExpectedConditions.elementToBeClickable(IP.getdownload()));
		IP.getdownload().click();
		Thread.sleep(10000);
		wt.until(ExpectedConditions.elementToBeClickable(IP.getEmailIcon()));
		IP.getEmailIcon().click();
		Thread.sleep(5000);
		wt.until(ExpectedConditions.elementToBeClickable(IP.getEmailTextbox()));
		IP.getEmailTextbox().sendKeys("bilawal.tsg@gmail.com");

		IP.getEmailSend().click();

		wt.until(ExpectedConditions.invisibilityOf(IP.getEmailPopupInvoice()));
		SF.assertEquals(IP.getEmailPopupInvoice().isDisplayed(), false);

		SF.assertAll();
	}

	@Test(priority = 13,enabled=false)
	public void MT1525() throws IOException, InterruptedException {

		 //driver = initializeDriver();

		SoftAssert SF = new SoftAssert();
		Thread.sleep(10000);

		WebDriverWait wt = new WebDriverWait(driver, 100);

		Header hd = new Header(driver);
		hd.getTrackBtn().click();
		TrackPage tp = new TrackPage(driver);
		Thread.sleep(20000);
		wt.until(ExpectedConditions.elementToBeClickable(tp.getQuickFinder()));
		tp.getQuickFinder().sendKeys("18" + Keys.ENTER);
		Thread.sleep(15000);
		SF.assertEquals(tp.getSearchResultCount().size() > 0, true,
				"Search is not returning any data on the track Page");
		// SF.assertEquals(true, tp.getSearchResult().isDisplayed(), "Search is not
		// returning any data on the track Page");
		tp.getOrdersTab().click();
		//wt.until(ExpectedConditions.elementToBeClickable(tp.getExpandIcon()));
		//tp.getExpandIcon().click();
		wt.until(ExpectedConditions.elementToBeClickable(tp.getReqOrderNum()));
		tp.getReqOrderNum().click();
		
		Thread.sleep(3000);
		SF.assertEquals(true, tp.getRequestReturnTrack().isDisplayed(),
				"Order Row is not getting expanded on Track Page");
		wt.until(ExpectedConditions.elementToBeClickable(tp.getRequestReturnTrack()));
		Thread.sleep(5000);
		tp.getRequestReturnTrack().click();
		Thread.sleep(5000);
		SF.assertEquals(true, tp.getReqReturnPopup().isDisplayed(),
				"Request return Popup is not appearing on the Track Page");
		wt.until(ExpectedConditions.elementToBeClickable(tp.getTrackRequestTextBox1()));
		Thread.sleep(5000);
		tp.getTrackRequestTextBox1().sendKeys("Test");
		tp.getTrackRequestSubmitBtn().click();
		Thread.sleep(5000);
		SF.assertEquals(true, tp.getTrackReturnConfirmdialog());
		wt.until(ExpectedConditions.elementToBeClickable(tp.getTrackReturnConfirmclose()));
		tp.getTrackReturnConfirmclose().click();
		Thread.sleep(5000);
		wt.until(ExpectedConditions.elementToBeClickable(tp.getTrackBuyItemAgain()));
		tp.getTrackBuyItemAgain().click();
		Thread.sleep(5000);
		SF.assertEquals(true, tp.getBuyItemAgainPopup().isDisplayed(), "Buy Item Again Popup is not appearing");
		wt.until(ExpectedConditions.elementToBeClickable(tp.getBuyItemAgainPopupClose()));
		tp.getBuyItemAgainPopupClose().click();
		Thread.sleep(5000);
		/*28june
		int a5 = tp.getRecordExpandIcon().size();
		wt.until(ExpectedConditions.elementToBeClickable(tp.getLoadMoreBtn()));
		tp.getLoadMoreBtn().click();
		Thread.sleep(5000);
		int b5 = tp.getRecordExpandIcon().size();
		SF.assertNotEquals(a5, b5, "Clicking on Load More button is not populating the compelete data");
		28june*/
		wt.until(ExpectedConditions.elementToBeClickable(tp.getQuotesBtn()));
		tp.getQuotesBtn().click();
		Thread.sleep(5000);
		SF.assertEquals(true, tp.getSearchText().isDisplayed(), "Results are not displaying on quotes tab");
		wt.until(ExpectedConditions.elementToBeClickable(tp.getQuotesExpand()));
		tp.getQuotesExpand().click();
		Thread.sleep(5000);
		wt.until(ExpectedConditions.elementToBeClickable(tp.getCreateOrderBtn()));
		tp.getCreateOrderBtn().click();
		Thread.sleep(5000);
		wt.until(ExpectedConditions.elementToBeClickable(tp.getCreateOrderClose()));
		tp.getCreateOrderClose().click();
		Thread.sleep(5000);
		wt.until(ExpectedConditions.elementToBeClickable(tp.getAssetBtn1()));
		tp.getAssetBtn1().click();
		SF.assertEquals(true, tp.getSearchText().isDisplayed(), "Results are not displaying on Assets tab");
	}

	@Test(priority = 14,enabled=false)
	public void MT1527() throws InterruptedException, IOException {

		// driver = initializeDriver();
		SoftAssert SF = new SoftAssert();

		Thread.sleep(5000);
		Header hd = new Header(driver);
		hd.getTrackBtn().click();
		Thread.sleep(5000);
		Log.info("User has clicked on the Track Button");
		TrackPage tp = new TrackPage(driver);
		tp.get_Purchase_Analysis_BtnBtn().click();
		Thread.sleep(5000);
		Log.info("User has clicked on the Purchase Analysis Icon");
		SF.assertEquals(driver.getTitle(), "Purchase Analysis - MarkITplace", "Purchase Analysis page is not opened");
		PurchaseAnalysis PA = new PurchaseAnalysis(driver);
		Thread.sleep(8000);
		PA.getJan2018Cricle().click();
		Thread.sleep(5000);
		SF.assertEquals(driver.getTitle(), "Order Line Report - MarkITplace",
				"User not able to reach the order line report");
		SF.assertEquals(driver.findElement(By.xpath("//td[@colspan='14']")).getText(), "Records not found");
		driver.navigate().back();
		Thread.sleep(8000);
		Select se = new Select(PA.getSavingDropdown());
		se.selectByVisibleText("Previous Quarter");
		Thread.sleep(10000);
		SF.assertEquals(se.getFirstSelectedOption().getText().trim(), "Previous Quarter");// actionBuilder.moveToElement(PA.getJan2018Circle1());
		PA.getJan2018Cricle().click();
		Thread.sleep(8000);
		SF.assertEquals(driver.getTitle(), "Order Line Report - MarkITplace");
		SF.assertEquals(driver.findElement(By.xpath("//td[@colspan='14']")).getText(), "Records not found");
		driver.navigate().back();
		Thread.sleep(8000);
		PA.getEmail().click();
		Thread.sleep(5000);
		PA.getEAddressField().sendKeys("bilawal.tsg@gmail.com");
		PA.getSendBtn().click();
		Thread.sleep(20000);
		SF.assertEquals(PA.getEAddressField().isDisplayed(), false,
				"Email is successfully send from the Purchase Analysis Page");
		PA.getdownload().click();
		SF.assertAll();
	}

	@Test(priority = 15,enabled=false)
	public void MT1528() throws InterruptedException, IOException {
		SoftAssert SF = new SoftAssert();
		// driver = initializeDriver();
		Thread.sleep(5000);
		Header hd = new Header(driver);
		hd.getTrackBtn().click();
		Thread.sleep(5000);
		Log.info("User has clicked on the Track Button");
		SF.assertEquals(driver.getTitle().trim(), "Track Dashboard - MarkITplace",
				"User not able to reach the track Page");
		hd.getCalendar().click();
		Thread.sleep(10000);
		Calendar CR = new Calendar(driver);
		SF.assertEquals(driver.getTitle().trim(), "Calendar Monthly View - MarkITplace",
				"User not able to reach the calendar Page");
		// SF.assertEquals(CR.getMonthViewBtn1().isSelected(), true, "Monthly Tab is not
		// selected in the Calendar's Page");
		Select se = new Select(CR.getCalendarDropdown());
		se.selectByVisibleText("2016");
		Thread.sleep(5000);
		se = new Select(CR.getCalendarDropdown());
		SF.assertEquals(se.getFirstSelectedOption().getText(), "2016",
				"User not able to change the value from the year drop down");
		CR.getSubscribeBtn().click();
		Thread.sleep(5000);
		// CR.getContBtnDisCount()>0;
		SF.assertEquals(CR.getContBtnDisCount().size() > 0, true, "Continue button is not appearing disabled first");
		CR.getExpirationDates().click();
		Thread.sleep(2000);
		SF.assertEquals(CR.getContBtnEnbCount().size() > 0, true,
				"Continue button is not appearing enabled after clicking on Expiry date");
		CR.getContBtn().click();
		CR.getCopyURLBtn().click();
		SF.assertEquals(CR.getLinkCopied().getText(), "Link was copied");
		CR.getSubCloseBtn().click();
		Thread.sleep(5000);
		CR.getfebExpand().click();
		Thread.sleep(5000);
		CR.getReportLInk().click();
		Thread.sleep(10000);
		SF.assertEquals(driver.getTitle(), "Expiration Report - MarkITplace",
				"When clicking on report link from calendar, the user not able to reach the report");
		SF.assertAll();
	}

	@Test(priority = 16,enabled=false)
	public void MT1534() throws InterruptedException {
		SoftAssert SF = new SoftAssert();
		Thread.sleep(5000);
		Header hd = new Header(driver);
		hd.getTrackBtn().click();
		TrackPage tp = new TrackPage(driver);
		SF.assertEquals(driver.getTitle(), "Track Dashboard - MarkITplace", "User not able to reach the Track Page");
		tp.getSavedReports().click();
		Thread.sleep(5000);
		SF.assertEquals(driver.getTitle(), "Saved Reports - MarkITplace",
				"User not able to reach the saved Report Page");
		SavedReports SR = new SavedReports(driver);
		SR.getDownloadBtn().click();
		Thread.sleep(5000);
		SR.getScheduleBtn().click();
		Thread.sleep(5000);
		SF.assertEquals(SR.getSchedulePopup().isDisplayed(), true, "Schedule Popup is not Opened");
		SR.getScheduleCancel().click();
		SR.getAllOrdersLink().click();
		Thread.sleep(5000);
		SF.assertEquals(driver.getTitle(), "Orders Report - MarkITplace",
				"All order Report is not Openning when clicking on All order Line from Saved Report Page");
		SF.assertAll();
	}
}