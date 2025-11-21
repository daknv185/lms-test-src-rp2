package jp.co.sss.lms.ct.f03_report;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
 * ケース08
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース08 受講生 レポート修正(週報) 正常系")
public class Case08 {

	/** 前処理 */
	@BeforeAll
	static void before() {
		createDriver();
	}

//	/** 後処理 */
//	@AfterAll
//	static void after() {
//		closeDriver();
//	}

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
	@DisplayName("テスト03 提出済の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {
		// TODO ここに追加
		
		WebElement record = webDriver.findElement(By.xpath("//*[@id=\"main\"]/div/div[2]/div[2]/table/tbody/tr[2]/td[5]/form/input[3]"));
		
		record.click();
		
		getEvidence(new Object() {});
		
		String url = webDriver.getCurrentUrl();
		assertEquals(url, "http://localhost:8080/lms/section/detail");
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「確認する」ボタンを押下しレポート登録画面に遷移")
	void test04() {
		// TODO ここに追加
		WebElement applicationBtn = webDriver
				.findElement(By.xpath("//*[@id=\"sectionDetail\"]/table[2]/tbody/tr[3]/td/form/input[6]"));
		
		scrollBy("200");
		
		applicationBtn.click();
		
		String url = webDriver.getCurrentUrl();
		assertEquals(url, "http://localhost:8080/lms/report/regist");
		
		getEvidence(new Object() {});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を修正して「提出する」ボタンを押下しセクション詳細画面に遷移")
	void test05() {
		// TODO ここに追加
		WebElement textForm = webDriver.findElement(By.xpath("//*[@id=\"content_1\"]"));

		String text = "This is test";
		
		textForm.clear();
		textForm.sendKeys(text);
		
		getEvidence(new Object() {}, "01_textInputted");
		
		scrollBy("200");
		
		WebElement submitBtn = webDriver.findElement(By.xpath("//*[@id=\"main\"]/form/div[3]/fieldset/div/div/button"));
		
		submitBtn.click();
		
		//以下、セクション詳細画面
		visibilityTimeout(By.xpath("//*[@id=\"sectionDetail\"]/table/tbody/tr[2]/td/form/input[6]"),5);
		
		WebElement dayRepSubmitBtn = webDriver.findElement(By.xpath("//*[@id=\"sectionDetail\"]/table[2]/tbody/tr[3]/td/form/input[6]"));
		
		String reportSubmitBtn = dayRepSubmitBtn.getAttribute("value");
		
		getEvidence(new Object() {});
		assertEquals("提出済み週報【デモ】を確認する",reportSubmitBtn);
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 上部メニューの「ようこそ○○さん」リンクからユーザー詳細画面に遷移")
	void test06() {
		// TODO ここに追加
		WebElement profileLink = webDriver.findElement(By.xpath("//*[@id=\"nav-content\"]/ul[2]/li[2]/a"));
		
		profileLink.click();
		
		getEvidence(new Object() {});
		
		String url = webDriver.getCurrentUrl();
		assertEquals(url, "http://localhost:8080/lms/user/detail");
		
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 該当レポートの「詳細」ボタンを押下しレポート詳細画面で修正内容が反映される")
	void test07() {
		// TODO ここに追加
		String inputtedText = "This is test";
		WebElement profileLink = webDriver.findElement(By.xpath("//*[@id=\"main\"]/table[3]/tbody/tr[9]/td[5]/form[1]/input[1]"));
	
		scrollBy("1000");
		
		profileLink.click();
		
		getEvidence(new Object() {});
		
		//レポート詳細画面へ遷移できているかの確認
		String url = webDriver.getCurrentUrl();
		assertEquals(url, "http://localhost:8080/lms/report/detail");
		
		//入力内容が反映されているかの確認
		WebElement thoughts = webDriver.findElement(By.xpath("//*[@id=\"main\"]/div[2]/table/tbody/tr[2]/td"));
		String actualText = thoughts.getText();
		
		assertEquals(inputtedText,actualText);
	}

}
