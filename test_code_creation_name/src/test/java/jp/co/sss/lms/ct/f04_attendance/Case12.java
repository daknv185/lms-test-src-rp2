package jp.co.sss.lms.ct.f04_attendance;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebElement;

/**
 * 結合テスト 勤怠管理機能
 * ケース12
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース12 受講生 勤怠直接編集 入力チェック")
public class Case12 {

	/** 前処理 */
	@BeforeAll
	static void before() {
		createDriver();
	}

	/** 後処理 */
	@AfterAll
	static void after() {
		closeDriver();
	}

	@Test
	@Order(1)
	@DisplayName("テスト01 トップページURLでアクセス")
	void test01() {
		// TODO ここに追加
		goTo("http://localhost:8080/lms/");
		getEvidence(new Object() {
		});

		String url = webDriver.getCurrentUrl();
		assertEquals(url, "http://localhost:8080/lms/");
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {
		// TODO ここに追加
		final WebElement loginId = webDriver.findElement(By.name("loginId"));
		final WebElement password = webDriver.findElement(By.name("password"));
		final WebElement login = webDriver.findElement(By.className("btn-primary"));
		String lmsId = "StudentAA01";
		String lmsPass = "StudentAA0";

		loginId.clear();
		loginId.sendKeys(lmsId);
		password.clear();
		password.sendKeys(lmsPass);

		getEvidence(new Object() {
		}, "01_beforeLogin");

		login.click();

		getEvidence(new Object() {
		}, "02_afterLogin");

		String screenInfo = webDriver.findElement(By.className("active")).getText();

		assertTrue(screenInfo.contains("コース詳細"));
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「勤怠」リンクから勤怠管理画面に遷移")
	void test03() {
		// TODO ここに追加
		visibilityTimeout(By.xpath("//*[@id=\"nav-content\"]/ul[1]/li[3]/a"), 5);
		WebElement attendance = webDriver.findElement(By.xpath("//*[@id=\"nav-content\"]/ul[1]/li[3]/a"));

		attendance.click();

		try {
			Alert alert = webDriver.switchTo().alert();
			alert.accept();
		} catch (NoAlertPresentException ignore) {

		}

		getEvidence(new Object() {
		});

		String url = webDriver.getCurrentUrl();
		assertEquals(url, "http://localhost:8080/lms/attendance/detail");

	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「勤怠情報を直接編集する」リンクから勤怠情報直接変更画面に遷移")
	void test04() {
		// TODO ここに追加
		visibilityTimeout(By.xpath("//*[@id=\"main\"]/div[1]/p/a"), 5);
		WebElement editAttendanceLink = webDriver.findElement(By.xpath("//*[@id=\"main\"]/div[1]/p/a"));

		editAttendanceLink.click();

		getEvidence(new Object() {
		});

		String url = webDriver.getCurrentUrl();
		assertEquals(url, "http://localhost:8080/lms/attendance/update");
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 不適切な内容で修正してエラー表示：出退勤の（時）と（分）のいずれかが空白")
	void test05() {
		// TODO ここに追加
		WebElement startHourForm = webDriver.findElement(By.xpath("//*[@id=\"startHour0\"]"));
		WebElement startMinuteForm = webDriver.findElement(By.xpath("//*[@id=\"startMinute0\"]"));
		WebElement endHourForm = webDriver.findElement(By.xpath("//*[@id=\"endHour0\"]"));
		WebElement endMinuteForm = webDriver.findElement(By.xpath("//*[@id=\"endMinute0\"]"));
		WebElement updateBtn = webDriver.findElement(By.xpath("//*[@id=\"main\"]/div/div/form/div/input"));
		
		startHourForm.click();
		WebElement startNineHour = webDriver.findElement(By.xpath("//*[@id=\"startHour0\"]/option[11]"));
		startNineHour.click();
		
		startMinuteForm.click();
		WebElement startMinuteBlank = webDriver.findElement(By.xpath("//*[@id=\"startMinute0\"]/option[1]"));
		startMinuteBlank.click();
		
		endHourForm.click();
		WebElement endHourBlank = webDriver.findElement(By.xpath("//*[@id=\"endHour0\"]/option[1]"));
		endHourBlank.click();
		
		endMinuteForm.click();
		WebElement endZeroMinute = webDriver.findElement(By.xpath("//*[@id=\"endMinute0\"]/option[2]"));
		endZeroMinute.click();
		
		getEvidence(new Object() {},"01_inputted");
		
		scrollBy("1000");
		
		updateBtn.click();
		
		
		
		Alert alert = webDriver.switchTo().alert();
		alert.accept();
		
		
		getEvidence(new Object() {},"02_submitted");
		
		List<WebElement> error = webDriver.findElements(By.className("error"));
		String errorStart = error.get(0).getText();
		String errorEnd = error.get(1).getText();
		
		assertEquals("* 出勤時間が正しく入力されていません。",errorStart);
		assertEquals("* 退勤時間が正しく入力されていません。",errorEnd);
		}

	@Test
	@Order(6)
	@DisplayName("テスト06 不適切な内容で修正してエラー表示：出勤が空白で退勤に入力あり")
	void test06() {
		// TODO ここに追加
		WebElement startHourForm = webDriver.findElement(By.xpath("//*[@id=\"startHour0\"]"));
		WebElement startMinuteForm = webDriver.findElement(By.xpath("//*[@id=\"startMinute0\"]"));
		WebElement endHourForm = webDriver.findElement(By.xpath("//*[@id=\"endHour0\"]"));
		WebElement endMinuteForm = webDriver.findElement(By.xpath("//*[@id=\"endMinute0\"]"));
		WebElement updateBtn = webDriver.findElement(By.xpath("//*[@id=\"main\"]/div/div/form/div/input"));
		
		startHourForm.click();
		WebElement startHourBlank = webDriver.findElement(By.xpath("//*[@id=\"startHour0\"]/option[1]"));
		startHourBlank.click();
		
		startMinuteForm.click();
		WebElement startMinuteBlank = webDriver.findElement(By.xpath("//*[@id=\"startMinute0\"]/option[1]"));
		startMinuteBlank.click();
		
		endHourForm.click();
		WebElement endHourTen= webDriver.findElement(By.xpath("//*[@id=\"endHour0\"]/option[12]"));
		endHourTen.click();
		
		endMinuteForm.click();
		WebElement endZeroMinute = webDriver.findElement(By.xpath("//*[@id=\"endMinute0\"]/option[2]"));
		endZeroMinute.click();
		
		getEvidence(new Object() {},"01_inputted");
		
		scrollBy("1000");
		
		updateBtn.click();
		
		
		Alert alert = webDriver.switchTo().alert();
		alert.accept();
		
		getEvidence(new Object() {},"02_submitted");
		
		WebElement error = webDriver.findElement(By.className("error"));
		
		String errorStart = error.getText();
		
		assertEquals("* 出勤情報がないため退勤情報を入力出来ません。",errorStart);
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 不適切な内容で修正してエラー表示：出勤が退勤よりも遅い時間")
	void test07() {
		// TODO ここに追加
		WebElement startHourForm = webDriver.findElement(By.xpath("//*[@id=\"startHour0\"]"));
		WebElement startMinuteForm = webDriver.findElement(By.xpath("//*[@id=\"startMinute0\"]"));
		WebElement endHourForm = webDriver.findElement(By.xpath("//*[@id=\"endHour0\"]"));
		WebElement endMinuteForm = webDriver.findElement(By.xpath("//*[@id=\"endMinute0\"]"));
		WebElement updateBtn = webDriver.findElement(By.xpath("//*[@id=\"main\"]/div/div/form/div/input"));
		
		startHourForm.click();
		WebElement startHourTen = webDriver.findElement(By.xpath("//*[@id=\"startHour0\"]/option[12]"));
		startHourTen.click();
		
		startMinuteForm.click();
		WebElement startMinuteZero = webDriver.findElement(By.xpath("//*[@id=\"startMinute0\"]/option[2]"));
		startMinuteZero.click();
		
		endHourForm.click();
		WebElement endHourNine= webDriver.findElement(By.xpath("//*[@id=\"endHour0\"]/option[11]"));
		endHourNine.click();
		
		endMinuteForm.click();
		WebElement endMinuteZero = webDriver.findElement(By.xpath("//*[@id=\"endMinute0\"]/option[2]"));
		endMinuteZero.click();
		
		getEvidence(new Object() {},"01_inputted");
		
		scrollBy("1000");
		
		updateBtn.click();
		
		
		Alert alert = webDriver.switchTo().alert();
		alert.accept();
		
		getEvidence(new Object() {},"02_submitted");
		
		WebElement error = webDriver.findElement(By.className("error"));
		
		String errorText = error.getText();
		
		assertEquals("* 退勤時刻は出勤時刻より後でなければいけません。",errorText);
	}

	@Test
	@Order(8)
	@DisplayName("テスト08 不適切な内容で修正してエラー表示：出退勤時間を超える中抜け時間")
	void test08() {
		// TODO ここに追加
		WebElement startHourForm = webDriver.findElement(By.xpath("//*[@id=\"startHour0\"]"));
		WebElement startMinuteForm = webDriver.findElement(By.xpath("//*[@id=\"startMinute0\"]"));
		WebElement endHourForm = webDriver.findElement(By.xpath("//*[@id=\"endHour0\"]"));
		WebElement endMinuteForm = webDriver.findElement(By.xpath("//*[@id=\"endMinute0\"]"));
		WebElement blankTimeForm = webDriver.findElement(By.xpath("//*[@id=\"main\"]/div/div/form/table/tbody/tr[1]/td[10]/select"));
		WebElement updateBtn = webDriver.findElement(By.xpath("//*[@id=\"main\"]/div/div/form/div/input"));
		
		startHourForm.click();
		
		WebElement startHourNine = webDriver.findElement(By.xpath("//*[@id=\"startHour0\"]/option[11]"));
		startHourNine.click();
		
		startMinuteForm.click();
		
		WebElement startMinuteZero = webDriver.findElement(By.xpath("//*[@id=\"startMinute0\"]/option[2]"));
		startMinuteZero.click();
		
		endHourForm.click();
		
		WebElement endHourTen = webDriver.findElement(By.xpath("//*[@id=\"endHour0\"]/option[12]"));
		endHourTen.click();
		
		endMinuteForm.click();
		
		WebElement endMinuteZero = webDriver.findElement(By.xpath("//*[@id=\"endMinute0\"]/option[2]"));
		endMinuteZero.click();
		
		blankTimeForm.click();
		
		WebElement blankHourFive = webDriver.findElement(By.xpath("//*[@id=\"main\"]/div/div/form/table/tbody/tr[1]/td[10]/select/option[21]"));
		blankHourFive.click();
		
		getEvidence(new Object() {},"01_inputted");
		
		scrollBy("1000");
		
		updateBtn.click();
		
		
		Alert alert = webDriver.switchTo().alert();
		alert.accept();
		
		getEvidence(new Object() {},"02_submitted");
		
		WebElement error = webDriver.findElement(By.className("error"));
		
		String errorText = error.getText();
		
		assertEquals("* 中抜け時間が勤務時間を超えています。",errorText);
	}

	@Test
	@Order(9)
	@DisplayName("テスト09 不適切な内容で修正してエラー表示：備考が100文字超")
	void test09() {
		// TODO ここに追加
		WebElement startHourForm = webDriver.findElement(By.xpath("//*[@id=\"startHour0\"]"));
		WebElement startMinuteForm = webDriver.findElement(By.xpath("//*[@id=\"startMinute0\"]"));
		WebElement endHourForm = webDriver.findElement(By.xpath("//*[@id=\"endHour0\"]"));
		WebElement endMinuteForm = webDriver.findElement(By.xpath("//*[@id=\"endMinute0\"]"));
		WebElement blankTimeForm = webDriver.findElement(By.xpath("//*[@id=\"main\"]/div/div/form/table/tbody/tr[1]/td[10]/select"));
		WebElement noteForm = webDriver.findElement(By.xpath("//*[@id=\"main\"]/div/div/form/table/tbody/tr[1]/td[12]/input"));
		WebElement updateBtn = webDriver.findElement(By.xpath("//*[@id=\"main\"]/div/div/form/div/input"));
		String testWord = "あいうえおか";
		
		startHourForm.click();
		
		WebElement startHourNine = webDriver.findElement(By.xpath("//*[@id=\"startHour0\"]/option[11]"));
		startHourNine.click();
		
		startMinuteForm.click();
		
		WebElement startMinuteZero = webDriver.findElement(By.xpath("//*[@id=\"startMinute0\"]/option[2]"));
		startMinuteZero.click();
		
		endHourForm.click();
		
		WebElement endHourTen = webDriver.findElement(By.xpath("//*[@id=\"endHour0\"]/option[12]"));
		endHourTen.click();
		
		endMinuteForm.click();
		
		WebElement endMinuteZero = webDriver.findElement(By.xpath("//*[@id=\"endMinute0\"]/option[2]"));
		endMinuteZero.click();
		
		blankTimeForm.click();
		
		WebElement blankHourBlank = webDriver.findElement(By.xpath("//*[@id=\"main\"]/div/div/form/table/tbody/tr[1]/td[10]/select/option[1]"));
		blankHourBlank.click();
		
		noteForm.clear();
		
		for(int i = 0;i < 17; i++) {
			noteForm.sendKeys(testWord);
		}
		
		getEvidence(new Object() {},"01_inputted");
		
		scrollBy("1000");
		
		updateBtn.click();
		
		
		Alert alert = webDriver.switchTo().alert();
		alert.accept();
		
		getEvidence(new Object() {},"02_submitted");
		
		WebElement error = webDriver.findElement(By.className("error"));
		
		String errorText = error.getText();
		
		assertEquals("* 備考の長さが最大値(100)を超えています。",errorText);
	}

}
