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
 * ケース11
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース11 受講生 勤怠直接編集 正常系")
public class Case11 {

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
	@DisplayName("テスト05 すべての研修日程の勤怠情報を正しく更新し勤怠管理画面に遷移")
	void test05() {
		// TODO ここに追加
		WebElement table = webDriver.findElement(By.xpath("//*[@id=\"main\"]/div/div/form/table"));
		WebElement updateBtn = webDriver.findElement(By.xpath("//*[@id=\"main\"]/div/div/form/div/input"));

		List<WebElement> regHourBtns = table
				.findElements(By.cssSelector(".btn.btn-success.default-button"));

		for (WebElement button : regHourBtns) {
			button.click();
			scrollBy("50");
		}

		getEvidence(new Object() {
		}, "01_regHourInputted");

		updateBtn.click();

		Alert alert = webDriver.switchTo().alert();

		alert.accept();

		getEvidence(new Object() {
		}, "02");

		String url = webDriver.getCurrentUrl();
		assertEquals(url, "http://localhost:8080/lms/attendance/update");

		List<WebElement> rows = webDriver.findElements(By.cssSelector("table#main tbody tr"));

		for (WebElement row : rows) {
			List<WebElement> cells = row.findElements(By.cssSelector("td.w80"));
			String start = cells.get(0).getText(); // 0列目が出勤
			String end = cells.get(1).getText(); // 1列目が退勤

			assertEquals("09:00", start);
			assertEquals("18:00", end);
		}

	}
}
