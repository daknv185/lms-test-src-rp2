package jp.co.sss.lms.ct.f02_faq;

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
 * 結合テスト よくある質問機能
 * ケース06
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース06 カテゴリ検索 正常系")
public class Case06 {

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
		}, "beforeLogin");

		login.click();

		getEvidence(new Object() {
		}, "afterLogin");

		String screenInfo = webDriver.findElement(By.className("active")).getText();

		assertTrue(screenInfo.contains("コース詳細"));
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「ヘルプ」リンクからヘルプ画面に遷移")
	void test03() {
		// TODO ここに追加
		final WebElement function = webDriver.findElement(By.className("dropdown-toggle"));

		function.click();

		getEvidence(new Object() {
		}, "dropdown-toggle");

		WebElement helpLink = webDriver.findElement(By.cssSelector("a[href='/lms/help']"));

		helpLink.click();

		getEvidence(new Object() {
		}, "Screen-help");

		String helpURL = webDriver.getCurrentUrl();
		assertEquals(helpURL, "http://localhost:8080/lms/help");
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクからよくある質問画面を別タブに開く")
	void test04() throws InterruptedException {
		// TODO ここに追加
		WebElement helpLink = webDriver
				.findElement(By.cssSelector("#main > div:nth-child(4) > div.panel-body > p > a"));

		helpLink.click();

		Thread.sleep(2000);

		Object[] windowHandles = webDriver.getWindowHandles().toArray();
		webDriver.switchTo().window((String) windowHandles[1]);

		getEvidence(new Object() {
		});

		String url = webDriver.getCurrentUrl();
		assertEquals(url, "http://localhost:8080/lms/faq");
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 カテゴリ検索で該当カテゴリの検索結果だけ表示")
	void test05() throws InterruptedException {
		// TODO ここに追加
		final WebElement aboutTraining = webDriver
				.findElement(By.xpath("//*[@id=\"main\"]/div[1]/fieldset/ul[1]/li/a"));

		aboutTraining.click();

		//クリックした2秒後に画面下部へ2000px分(最下部)移動

		Thread.sleep(2000);

		scrollTo("1000");
		
		getEvidence(new Object() {});

		Boolean cancelCheck = webDriver.getPageSource().contains("キャンセル料・途中退校について");
		assertTrue(cancelCheck);

		Boolean applicationCheck = webDriver.getPageSource().contains("研修の申し込みはどのようにすれば良いですか？");
		assertTrue(applicationCheck);

	}

	@Test
	@Order(6)
	@DisplayName("テスト06 検索結果の質問をクリックしその回答を表示")
	void test06() throws InterruptedException {
		// TODO ここに追加
		final WebElement cancellationFee = webDriver
				.findElement(By.xpath("//*[@id=\"question-h[${status.index}]\"]/dt"));
		
		Thread.sleep(1000);
		
		cancellationFee.click();
		
		Thread.sleep(1500);
		
		getEvidence(new Object() {});

		WebElement searchA = webDriver.findElement(By.className("fs18"));

		assertEquals("A. 受講者の退職や解雇等、やむを得ない事情による途中終了に関してなど、事情をお伺いした上で、協議という形を取らせて頂きます。 弊社営業担当までご相談下さい。",
				searchA.getText());

	}

}
