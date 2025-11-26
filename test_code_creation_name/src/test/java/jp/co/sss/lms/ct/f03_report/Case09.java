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
 * ケース09
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース09 受講生 レポート登録 入力チェック")
public class Case09 {

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
	@DisplayName("テスト03 上部メニューの「ようこそ○○さん」リンクからユーザー詳細画面に遷移")
	void test03() {
		// TODO ここに追加
		WebElement profileLink = webDriver.findElement(By.xpath("//*[@id=\"nav-content\"]/ul[2]/li[2]/a"));

		profileLink.click();

		getEvidence(new Object() {
		});

		String url = webDriver.getCurrentUrl();
		assertEquals(url, "http://localhost:8080/lms/user/detail");
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 該当レポートの「修正する」ボタンを押下しレポート登録画面に遷移")
	void test04() {
		// TODO ここに追加
		scrollBy("1000");
		
		WebElement fix = webDriver.findElement(By.xpath("//*[@id=\"main\"]/table[3]/tbody/tr[2]/td[5]/form[2]/input[1]"));
		fix.click();
		
		getEvidence(new Object() {});
		
		String url = webDriver.getCurrentUrl();
		assertEquals(url, "http://localhost:8080/lms/report/regist");
		
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を修正して「提出する」ボタンを押下しエラー表示：学習項目が未入力")
	void test05() {
		// TODO ここに追加
		WebElement contents = webDriver.findElement(By.xpath("//*[@id=\"intFieldName_0\"]"));
		WebElement understanding = webDriver.findElement(By.xpath("//*[@id=\"intFieldValue_0\"]"));
		WebElement attainmentLev = webDriver.findElement(By.xpath("//*[@id=\"content_0\"]"));
		WebElement Impressions = webDriver.findElement(By.xpath("//*[@id=\"content_1\"]"));
		WebElement submitBtn = webDriver.findElement(By.xpath("//*[@id=\"main\"]/form/div[3]/fieldset/div/div/button"));
		
		final String testWord = "test"; 
		
		
		contents.clear();
		
		understanding.click();
		
		WebElement understandingLev1 = webDriver.findElement(By.xpath("//*[@id=\"intFieldValue_0\"]/option[2]"));
		
		understandingLev1.click();
		
		attainmentLev.clear();
		attainmentLev.sendKeys("1");
		
		getEvidence(new Object() {},"01_beforeSubmit");
		
		Impressions.clear();
		Impressions.sendKeys(testWord);
		
		getEvidence(new Object() {},"02_beforeSubmit");
		
		scrollBy("1000");
		
		submitBtn.click();
		
		getEvidence(new Object() {},"03_afterSubmit");
		
		String error = webDriver.findElement(By.className("error")).getText();

		assertEquals(error,"理解度を入力した場合は、学習項目は必須です。");
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：理解度が未入力")
	void test06() {
		// TODO ここに追加
		WebElement contents = webDriver.findElement(By.xpath("//*[@id=\"intFieldName_0\"]"));
		WebElement understanding = webDriver.findElement(By.xpath("//*[@id=\"intFieldValue_0\"]"));
		WebElement attainmentLev = webDriver.findElement(By.xpath("//*[@id=\"content_0\"]"));
		WebElement Impressions = webDriver.findElement(By.xpath("//*[@id=\"content_1\"]"));
		WebElement submitBtn = webDriver.findElement(By.xpath("//*[@id=\"main\"]/form/div[3]/fieldset/div/div/button"));
		
		final String testWord = "test"; 
		
		
		contents.clear();
		contents.sendKeys(testWord);
		
		understanding.click();
		
		WebElement understandingLev1 = webDriver.findElement(By.xpath("//*[@id=\"intFieldValue_0\"]/option[1]"));
		
		understandingLev1.click();
		
		attainmentLev.clear();
		attainmentLev.sendKeys("1");
		
		getEvidence(new Object() {},"01_beforeSubmit");
		
		Impressions.clear();
		Impressions.sendKeys(testWord);
		
		getEvidence(new Object() {},"02_beforeSubmit");
		
		scrollBy("1000");
		
		submitBtn.click();
		
		getEvidence(new Object() {},"03_afterSubmit");
		
		String error = webDriver.findElement(By.className("error")).getText();

		assertEquals(error,"学習項目を入力した場合は、理解度は必須です。");
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度が数値以外")
	void test07() {
		// TODO ここに追加
		WebElement contents = webDriver.findElement(By.xpath("//*[@id=\"intFieldName_0\"]"));
		WebElement understanding = webDriver.findElement(By.xpath("//*[@id=\"intFieldValue_0\"]"));
		WebElement attainmentLev = webDriver.findElement(By.xpath("//*[@id=\"content_0\"]"));
		WebElement Impressions = webDriver.findElement(By.xpath("//*[@id=\"content_1\"]"));
		WebElement submitBtn = webDriver.findElement(By.xpath("//*[@id=\"main\"]/form/div[3]/fieldset/div/div/button"));
		
		final String testWord = "test"; 
		
		
		contents.clear();
		contents.sendKeys(testWord);
		
		understanding.click();
		
		WebElement understandingLev1 = webDriver.findElement(By.xpath("//*[@id=\"intFieldValue_0\"]/option[2]"));
		
		understandingLev1.click();
		
		attainmentLev.clear();
		attainmentLev.sendKeys(testWord);
		
		getEvidence(new Object() {},"01_beforeSubmit");
		
		Impressions.clear();
		Impressions.sendKeys(testWord);
		
		getEvidence(new Object() {},"02_beforeSubmit");
		
		scrollBy("1000");
		
		submitBtn.click();
		
		scrollBy("100");
		
		getEvidence(new Object() {},"03_afterSubmit");
		
		String error = webDriver.findElement(By.className("error")).getText();

		assertEquals(error,"目標の達成度は半角数字で入力してください");
		
		scrollTo("0");
	}

	@Test
	@Order(8)
	@DisplayName("テスト08 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度が範囲外")
	void test08() {
		// TODO ここに追加
		WebElement contents = webDriver.findElement(By.xpath("//*[@id=\"intFieldName_0\"]"));
		WebElement understanding = webDriver.findElement(By.xpath("//*[@id=\"intFieldValue_0\"]"));
		WebElement attainmentLev = webDriver.findElement(By.xpath("//*[@id=\"content_0\"]"));
		WebElement Impressions = webDriver.findElement(By.xpath("//*[@id=\"content_1\"]"));
		WebElement submitBtn = webDriver.findElement(By.xpath("//*[@id=\"main\"]/form/div[3]/fieldset/div/div/button"));
		
		final String testWord = "test"; 
		
		
		contents.clear();
		contents.sendKeys(testWord);
		
		understanding.click();
		
		WebElement understandingLev1 = webDriver.findElement(By.xpath("//*[@id=\"intFieldValue_0\"]/option[2]"));
		
		understandingLev1.click();
		
		attainmentLev.clear();
		attainmentLev.sendKeys("11");
		
		getEvidence(new Object() {},"01_beforeSubmit");
		
		Impressions.clear();
		Impressions.sendKeys(testWord);
		
		getEvidence(new Object() {},"02_beforeSubmit");
		
		scrollBy("1000");
		
		submitBtn.click();
		
		scrollBy("100");
		
		getEvidence(new Object() {},"03_afterSubmit");
		
		String error = webDriver.findElement(By.className("error")).getText();

		assertEquals(error,"目標の達成度は1～10の範囲内で入力してください");
		
		scrollTo("0");
	}

	@Test
	@Order(9)
	@DisplayName("テスト09 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度が未入力")
	void test09() {
		// TODO ここに追加
		WebElement contents = webDriver.findElement(By.xpath("//*[@id=\"intFieldName_0\"]"));
		WebElement understanding = webDriver.findElement(By.xpath("//*[@id=\"intFieldValue_0\"]"));
		WebElement attainmentLev = webDriver.findElement(By.xpath("//*[@id=\"content_0\"]"));
		WebElement Impressions = webDriver.findElement(By.xpath("//*[@id=\"content_1\"]"));
		WebElement submitBtn = webDriver.findElement(By.xpath("//*[@id=\"main\"]/form/div[3]/fieldset/div/div/button"));
		
		final String testWord = "test"; 
		
		
		contents.clear();
		contents.sendKeys(testWord);
		
		understanding.click();
		
		WebElement understandingLev1 = webDriver.findElement(By.xpath("//*[@id=\"intFieldValue_0\"]/option[2]"));
		
		understandingLev1.click();
		
		attainmentLev.clear();
		
		getEvidence(new Object() {},"01_beforeSubmit");
		
		Impressions.clear();
		Impressions.sendKeys(testWord);
		
		getEvidence(new Object() {},"02_beforeSubmit");
		
		scrollBy("1000");
		
		submitBtn.click();
		
		scrollBy("100");
		
		getEvidence(new Object() {},"03_afterSubmit");
		
		String error = webDriver.findElement(By.className("error")).getText();

		assertEquals(error,"目標の達成度は必須です");
		
		scrollTo("0");
		
	}

	@Test
	@Order(10)
	@DisplayName("テスト10 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：所感が未入力")
	void test10() {
		// TODO ここに追加
		WebElement contents = webDriver.findElement(By.xpath("//*[@id=\"intFieldName_0\"]"));
		WebElement understanding = webDriver.findElement(By.xpath("//*[@id=\"intFieldValue_0\"]"));
		WebElement attainmentLev = webDriver.findElement(By.xpath("//*[@id=\"content_0\"]"));
		WebElement Impressions = webDriver.findElement(By.xpath("//*[@id=\"content_1\"]"));
		WebElement submitBtn = webDriver.findElement(By.xpath("//*[@id=\"main\"]/form/div[3]/fieldset/div/div/button"));
		
		final String testWord = "test"; 
		
		
		contents.clear();
		contents.sendKeys(testWord);
		
		understanding.click();
		
		WebElement understandingLev1 = webDriver.findElement(By.xpath("//*[@id=\"intFieldValue_0\"]/option[2]"));
		
		understandingLev1.click();
		
		attainmentLev.clear();
		attainmentLev.sendKeys("1");
		
		getEvidence(new Object() {},"01_beforeSubmit");
		
		Impressions.clear();
		
		getEvidence(new Object() {},"02_beforeSubmit");
		
		scrollBy("1000");
		
		submitBtn.click();
		
		scrollBy("250");
		
		getEvidence(new Object() {},"03_afterSubmit");
		
		String error = webDriver.findElement(By.className("error")).getText();

		assertEquals(error,"所感は必須です");
		
		scrollTo("0");
	}

	@Test
	@Order(11)
	@DisplayName("テスト11 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：所感が2000文字超")
	void test11() {
		// TODO ここに追加
		WebElement contents = webDriver.findElement(By.xpath("//*[@id=\"intFieldName_0\"]"));
		WebElement understanding = webDriver.findElement(By.xpath("//*[@id=\"intFieldValue_0\"]"));
		WebElement attainmentLev = webDriver.findElement(By.xpath("//*[@id=\"content_0\"]"));
		WebElement Impressions = webDriver.findElement(By.xpath("//*[@id=\"content_1\"]"));
		WebElement submitBtn = webDriver.findElement(By.xpath("//*[@id=\"main\"]/form/div[3]/fieldset/div/div/button"));
		
		final String testWord = "test"; 
		
		
		contents.clear();
		contents.sendKeys(testWord);
		
		understanding.click();
		
		WebElement understandingLev1 = webDriver.findElement(By.xpath("//*[@id=\"intFieldValue_0\"]/option[2]"));
		
		understandingLev1.click();
		
		attainmentLev.clear();
		attainmentLev.sendKeys("1");
		
		getEvidence(new Object() {},"01_beforeSubmit");
		
		Impressions.clear();
		for(int i = 0; i <= 91 ; i++) {
			Impressions.sendKeys("あいうえおかきくけこさしすせそたちつてとなに");
		}
		
		getEvidence(new Object() {},"02_beforeSubmit");
		
		scrollBy("1000");
		
		submitBtn.click();
		
		scrollBy("250");
		
		getEvidence(new Object() {},"03_afterSubmit");
		
		String error = webDriver.findElement(By.className("error")).getText();

		assertEquals(error,"所感の長さが最大値(2000)を超えています");
		
		scrollTo("0");
	}

	@Test
	@Order(12)
	@DisplayName("テスト12 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：一週間の振り返りが2000文字超")
	void test12() {
		// TODO ここに追加
		WebElement contents = webDriver.findElement(By.xpath("//*[@id=\"intFieldName_0\"]"));
		WebElement understanding = webDriver.findElement(By.xpath("//*[@id=\"intFieldValue_0\"]"));
		WebElement attainmentLev = webDriver.findElement(By.xpath("//*[@id=\"content_0\"]"));
		WebElement Impressions = webDriver.findElement(By.xpath("//*[@id=\"content_1\"]"));
		WebElement review = webDriver.findElement(By.xpath("//*[@id=\"content_2\"]"));
		WebElement submitBtn = webDriver.findElement(By.xpath("//*[@id=\"main\"]/form/div[3]/fieldset/div/div/button"));
		
		final String testWord = "test"; 
		
		
		contents.clear();
		contents.sendKeys(testWord);
		
		understanding.click();
		
		WebElement understandingLev1 = webDriver.findElement(By.xpath("//*[@id=\"intFieldValue_0\"]/option[2]"));
		
		understandingLev1.click();
		
		attainmentLev.clear();
		attainmentLev.sendKeys("1");
		
		getEvidence(new Object() {},"01_beforeSubmit");
		
		Impressions.clear();
		Impressions.sendKeys(testWord);
		
		review.clear();
		for(int i = 0; i <= 91 ; i++) {
			review.sendKeys("あいうえおかきくけこさしすせそたちつてとなに");
		}
		
		getEvidence(new Object() {},"02_beforeSubmit");
		
		scrollBy("1000");
		
		submitBtn.click();
		
		scrollBy("1000");
		
		getEvidence(new Object() {},"03_afterSubmit");
		
		String error = webDriver.findElement(By.className("error")).getText();

		assertEquals(error,"一週間の振り返りの長さが最大値(2000)を超えています");
	}

}
