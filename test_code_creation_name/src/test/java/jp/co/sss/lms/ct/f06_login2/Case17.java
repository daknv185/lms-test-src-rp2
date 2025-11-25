package jp.co.sss.lms.ct.f06_login2;

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
 * 結合テスト ログイン機能②
 * ケース17
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース17 受講生 初回ログイン 正常系")
public class Case17 {

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
	@DisplayName("テスト02 DBに初期登録された未ログインの受講生ユーザーでログイン")
	void test02() {
		// TODO ここに追加
		final WebElement loginId = webDriver.findElement(By.name("loginId"));
		final WebElement password = webDriver.findElement(By.name("password"));
		final WebElement login = webDriver.findElement(By.className("btn-primary"));
		String lmsId = "StudentAA07";
		String lmsPass = "StudentAA07";

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
	@DisplayName("テスト04 変更パスワードを入力し「変更」ボタン押下")
	void test04() {
		// TODO ここに追加
		final WebElement currentPassForm = webDriver.findElement(By.xpath("//*[@id=\"currentPassword\"]"));
		final WebElement newPassForm = webDriver.findElement(By.xpath("//*[@id=\"password\"]"));
		final WebElement passConfirmForm = webDriver.findElement(By.xpath("//*[@id=\"passwordConfirm\"]"));
		final WebElement changeBtn = webDriver.findElement(By.xpath("//*[@id=\"upd-form\"]/div[1]/fieldset/div[4]/div/button[2]"));
		final WebElement changeConfirmBtn = webDriver.findElement(By.xpath("//*[@id=\"upd-btn\"]"));
		
		final String currentPass = "StudentAA07";
		final String newPass = "StudentAA0";
		final String passConfirm = "StudentAA0";
		
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
		
		String url = webDriver.getCurrentUrl();
		assertEquals(url, "http://localhost:8080/lms/course/detail");
	}

}
