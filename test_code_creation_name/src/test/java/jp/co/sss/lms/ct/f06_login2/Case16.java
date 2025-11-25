package jp.co.sss.lms.ct.f06_login2;

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
 * 結合テスト ログイン機能②
 * ケース16
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース16 受講生 初回ログイン 変更パスワード未入力")
public class Case16 {

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
	@DisplayName("テスト02 DBに初期登録された未ログインの受講生ユーザーでログイン")
	void test02() {
		// TODO ここに追加
		final WebElement loginId = webDriver.findElement(By.name("loginId"));
		final WebElement password = webDriver.findElement(By.name("password"));
		final WebElement login = webDriver.findElement(By.className("btn-primary"));
		String lmsId = "StudentAA02";
		String lmsPass = "StudentAA02";

		loginId.clear();
		loginId.sendKeys(lmsId);
		password.clear();
		password.sendKeys(lmsPass);

		getEvidence(new Object() {
		}, "01_beforeLogin");

		login.click();

		getEvidence(new Object() {
		}, "02_afterLogin");

		String screenInfo = webDriver.findElement(By.tagName("h2")).getText();

		assertTrue(screenInfo.contains("利用規約"));
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 「同意します」チェックボックスにチェックを入れ「次へ」ボタン押下")
	void test03() {
		// TODO ここに追加
		final WebElement checkBox = webDriver.findElement(By.xpath("//*[@id=\"main\"]/div[2]/form/fieldset/div[1]/div/label"));
		final WebElement nextBtn = webDriver.findElement(By.className("btn-primary"));
		
		scrollBy("200");
		
		checkBox.click();
		
		getEvidence(new Object() {
		},"01_checked");
		
		nextBtn.click();
		
		getEvidence(new Object() {
		},"02");
		
		String helpURL = webDriver.getCurrentUrl();
		assertEquals(helpURL, "http://localhost:8080/lms/password/changePassword");
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 パスワードを未入力で「変更」ボタン押下")
	void test04() {
		// TODO ここに追加
		final WebElement newPassForm = webDriver.findElement(By.xpath("//*[@id=\"password\"]"));
		final WebElement passConfirmForm = webDriver.findElement(By.xpath("//*[@id=\"passwordConfirm\"]"));
		final WebElement changeBtn = webDriver.findElement(By.xpath("//*[@id=\"upd-form\"]/div[1]/fieldset/div[4]/div/button[2]"));
		final WebElement changeConfirmBtn = webDriver.findElement(By.xpath("//*[@id=\"upd-btn\"]"));
		
		final String newPass = "Abcdefghijklmnopqrs1";
		final String passConfirm = "Abcdefghijklmnopqrs1";
		
		newPassForm.clear();
		newPassForm.sendKeys(newPass);
		passConfirmForm.clear();
		passConfirmForm.sendKeys(passConfirm);
		
		getEvidence(new Object() {},"01_beforeClickChange");
		
		changeBtn.click();
		
		visibilityTimeout(By.xpath("//*[@id=\"upd-btn\"]"),5);
		
		changeConfirmBtn.click();
		
		getEvidence(new Object() {},"02_changeClicked");
		
		String error = webDriver.findElement(By.xpath("//*[@id=\"upd-form\"]/div[1]/fieldset/div[1]/div/ul/li/span")).getText();

		assertTrue(error.contains("現在のパスワードは必須です。"));
		
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 20文字以上の変更パスワードを入力し「変更」ボタン押下")
	void test05() {
		// TODO ここに追加
		final WebElement currentPassForm = webDriver.findElement(By.xpath("//*[@id=\"currentPassword\"]"));
		final WebElement newPassForm = webDriver.findElement(By.xpath("//*[@id=\"password\"]"));
		final WebElement passConfirmForm = webDriver.findElement(By.xpath("//*[@id=\"passwordConfirm\"]"));
		final WebElement changeBtn = webDriver.findElement(By.xpath("//*[@id=\"upd-form\"]/div[1]/fieldset/div[4]/div/button[2]"));
		final WebElement changeConfirmBtn = webDriver.findElement(By.xpath("//*[@id=\"upd-btn\"]"));
		
		final String currentPass = "StudentAA02";
		final String newPass = "Abcdefghijklmnopqrst1";
		final String passConfirm = "Abcdefghijklmnopqrst1";
		
		currentPassForm.clear();
		currentPassForm.sendKeys(currentPass);
		newPassForm.clear();
		newPassForm.sendKeys(newPass);
		passConfirmForm.clear();
		passConfirmForm.sendKeys(passConfirm);
		
		getEvidence(new Object() {},"01_beforeClickChange");
		
		scrollBy("200");
		
		changeBtn.click();
		
		visibilityTimeout(By.xpath("//*[@id=\"upd-btn\"]"),5);
		
		changeConfirmBtn.click();
		
		getEvidence(new Object() {},"02_changeClicked");
		
		String error = webDriver.findElement(By.xpath("//*[@id=\"upd-form\"]/div[1]/fieldset/div[2]/div/ul/li/span")).getText();

		assertTrue(error.contains("パスワードの長さが最大値(20)を超えています。"));
		
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 ポリシーに合わない変更パスワードを入力し「変更」ボタン押下")
	void test06() {
		// TODO ここに追加 
		final WebElement currentPassForm = webDriver.findElement(By.xpath("//*[@id=\"currentPassword\"]"));
		final WebElement newPassForm = webDriver.findElement(By.xpath("//*[@id=\"password\"]"));
		final WebElement passConfirmForm = webDriver.findElement(By.xpath("//*[@id=\"passwordConfirm\"]"));
		final WebElement changeBtn = webDriver.findElement(By.xpath("//*[@id=\"upd-form\"]/div[1]/fieldset/div[4]/div/button[2]"));
		final WebElement changeConfirmBtn = webDriver.findElement(By.xpath("//*[@id=\"upd-btn\"]"));
		
		final String currentPass = "StudentAA02";
		final String newPass = "abcdefghijklmnopqrst";
		final String passConfirm = "abcdefghijklmnopqrst";
		
		currentPassForm.clear();
		currentPassForm.sendKeys(currentPass);
		newPassForm.clear();
		newPassForm.sendKeys(newPass);
		passConfirmForm.clear();
		passConfirmForm.sendKeys(passConfirm);
		
		getEvidence(new Object() {},"01_beforeClickChange");
		
		scrollBy("200");
		
		changeBtn.click();
		
		visibilityTimeout(By.xpath("//*[@id=\"upd-btn\"]"),5);
		
		changeConfirmBtn.click();
		
		getEvidence(new Object() {},"02_changeClicked");
		
		String error = webDriver.findElement(By.xpath("//*[@id=\"upd-form\"]/div[1]/fieldset/div[2]/div/ul/li/span")).getText();

		assertTrue(error.contains("「パスワード」には半角英数字のみ使用可能です。また、半角英大文字、半角英小文字、数字を含めた8～20文字を入力してください。"));
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 一致しない確認パスワードを入力し「変更」ボタン押下")
	void test07() {
		// TODO ここに追加
		final WebElement currentPassForm = webDriver.findElement(By.xpath("//*[@id=\"currentPassword\"]"));
		final WebElement newPassForm = webDriver.findElement(By.xpath("//*[@id=\"password\"]"));
		final WebElement passConfirmForm = webDriver.findElement(By.xpath("//*[@id=\"passwordConfirm\"]"));
		final WebElement changeBtn = webDriver.findElement(By.xpath("//*[@id=\"upd-form\"]/div[1]/fieldset/div[4]/div/button[2]"));
		final WebElement changeConfirmBtn = webDriver.findElement(By.xpath("//*[@id=\"upd-btn\"]"));
		
		final String currentPass = "StudentAA02";
		final String newPass = "Abcdefghijklmnopqrs1";
		final String passConfirm = "Abcdefghijklmnopqrstu1";
		
		currentPassForm.clear();
		currentPassForm.sendKeys(currentPass);
		newPassForm.clear();
		newPassForm.sendKeys(newPass);
		passConfirmForm.clear();
		passConfirmForm.sendKeys(passConfirm);
		
		getEvidence(new Object() {},"01_beforeClickChange");
		
		scrollBy("200");
		
		changeBtn.click();
		
		visibilityTimeout(By.xpath("//*[@id=\"upd-btn\"]"),5);
		
		changeConfirmBtn.click();
		
		getEvidence(new Object() {},"02_changeClicked");
		
		String error = webDriver.findElement(By.xpath("//*[@id=\"upd-form\"]/div[1]/fieldset/div[2]/div/ul/li/span")).getText();

		assertTrue(error.contains("パスワードと確認パスワードが一致しません。"));
	}

}
