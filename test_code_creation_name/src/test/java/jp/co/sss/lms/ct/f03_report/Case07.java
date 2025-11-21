package jp.co.sss.lms.ct.f03_report;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * 結合テスト レポート機能
 * ケース07
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース07 受講生 レポート新規登録(日報) 正常系")
public class Case07 {

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

		getEvidence(new Object() {}, "01_beforeLogin");

		login.click();

		getEvidence(new Object() {}, "02_afterLogin");

		String screenInfo = webDriver.findElement(By.className("active")).getText();

		assertTrue(screenInfo.contains("コース詳細"));
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 未提出の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {
		// TODO ここに追加
		
		scrollBy("300");
		WebElement record = webDriver.findElement(By.xpath("//table//tr[td//span[text()='未提出']]"));

		record.findElement(By.cssSelector("input[type='submit']")).click();
		
		getEvidence(new Object() {});
		
		String url = webDriver.getCurrentUrl();
		assertEquals(url, "http://localhost:8080/lms/section/detail");
		
		
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「提出する」ボタンを押下しレポート登録画面に遷移")
	void test04() {
		// TODO ここに追加
		WebElement applicationBtn = webDriver
				.findElement(By.xpath("//*[@id=\"sectionDetail\"]/table/tbody/tr[2]/td/form/input[5]"));
		applicationBtn.click();
		
		String url = webDriver.getCurrentUrl();
		assertEquals(url, "http://localhost:8080/lms/report/regist");
		
		getEvidence(new Object() {});
}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を入力して「提出する」ボタンを押下し確認ボタン名が更新される")
	void test05() {
		// TODO ここに追加
		WebElement textForm = webDriver.findElement(By.xpath("//*[@id=\"content_0\"]"));
		WebElement submitBtn = webDriver.findElement(By.xpath("//*[@id=\"main\"]/form/div[2]/fieldset/div/div/button"));

		String text = "test";
		
		textForm.clear();
		textForm.sendKeys(text);
		
		getEvidence(new Object() {}, "01_textInputted");
		
		submitBtn.click();
		
		//以下、セクション詳細画面
		visibilityTimeout(By.xpath("//*[@id=\"sectionDetail\"]/table/tbody/tr[2]/td/form/input[6]"),5);
		
		WebElement dayRepSubmitBtn = webDriver.findElement(By.xpath("//*[@id=\"sectionDetail\"]/table/tbody/tr[2]/td/form/input[6]"));
		
		String reportSubmitBtn = dayRepSubmitBtn.getAttribute("value");
		
		getEvidence(new Object() {}, "02_submitBtnChanged");
		assertEquals("提出済み日報【デモ】を確認する",reportSubmitBtn);
		
	}

}
