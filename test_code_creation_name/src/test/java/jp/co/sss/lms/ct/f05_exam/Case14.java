package jp.co.sss.lms.ct.f05_exam;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * 結合テスト 試験実施機能
 * ケース14
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース14 受講生 試験の実施 結果50点")
public class Case14 {

	/** テスト07およびテスト08 試験実施日時 */
	static Date date;

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
	@DisplayName("テスト03 「試験有」の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {
		// TODO ここに追加
		WebElement record = webDriver.findElement(By.xpath("//table//tr[td//span[text()='試験有']]"));

		record.findElement(By.cssSelector("input[type='submit']")).click();

		getEvidence(new Object() {
		});

		String url = webDriver.getCurrentUrl();
		assertEquals(url, "http://localhost:8080/lms/section/detail");
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「本日の試験」エリアの「詳細」ボタンを押下し試験開始画面に遷移")
	void test04() {
		// TODO ここに追加
		WebElement detailBtn = webDriver
				.findElement(By.xpath("//*[@id=\"sectionDetail\"]/table[1]/tbody/tr[2]/td[2]/form/input[1]"));

		detailBtn.click();

		getEvidence(new Object() {
		});

		String url = webDriver.getCurrentUrl();
		assertEquals(url, "http://localhost:8080/lms/exam/start");
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 「試験を開始する」ボタンを押下し試験問題画面に遷移")
	void test05() {
		// TODO ここに追加
		WebElement startTestBtn = webDriver
				.findElement(By.xpath("//*[@id=\"main\"]/div/form/input[4]"));

		startTestBtn.click();

		getEvidence(new Object() {
		});

		String url = webDriver.getCurrentUrl();
		assertEquals(url, "http://localhost:8080/lms/exam/question");
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 正答と誤答が半々で「確認画面へ進む」ボタンを押下し試験回答確認画面に遷移")
	void test06() throws InterruptedException {
		// TODO ここに追加
		WebElement gonnaConfirmationBtn = webDriver
				.findElement(By.xpath("//*[@id=\"examQuestionForm\"]/div[13]/fieldset/input"));
		//第１問
		WebElement answer1 = webDriver.findElement(By.xpath("//*[@id=\"answer-0-2\"]"));
		answer1.click();
		scrollBy("400");
		//第２問
		WebElement answer2 = webDriver.findElement(By.xpath("//*[@id=\"answer-1-2\"]"));
		answer2.click();
		scrollBy("400");
		//第３問
		WebElement answer3 = webDriver.findElement(By.xpath("//*[@id=\"answer-2-0\"]"));
		answer3.click();
		scrollBy("400");
		//第４問
		WebElement answer4 = webDriver.findElement(By.xpath("//*[@id=\"answer-3-0\"]"));
		answer4.click();
		scrollBy("400");
		//第５問
		WebElement answer5 = webDriver.findElement(By.xpath("//*[@id=\"answer-4-1\"]"));
		answer5.click();
		scrollBy("400");
		//第６問
		WebElement answer6 = webDriver.findElement(By.xpath("//*[@id=\"answer-5-1\"]"));
		answer6.click();
		scrollBy("400");
		//第7問
		WebElement answer7 = webDriver.findElement(By.xpath("//*[@id=\"answer-6-0\"]"));
		answer7.click();
		scrollBy("350");
		//第8問
		WebElement answer8 = webDriver.findElement(By.xpath("//*[@id=\"answer-7-0\"]"));
		answer8.click();
		scrollBy("350");
		//第9問
		WebElement answer9 = webDriver.findElement(By.xpath("//*[@id=\"answer-8-0\"]"));
		answer9.click();
		scrollBy("350");
		//第10問
		WebElement answer10 = webDriver.findElement(By.xpath("//*[@id=\"answer-9-0\"]"));
		answer10.click();
		scrollBy("350");
		//第11門
		WebElement answer11 = webDriver.findElement(By.xpath("//*[@id=\"answer-10-0\"]"));
		answer11.click();
		scrollBy("350");
		//第12問
		WebElement answer12 = webDriver.findElement(By.xpath("//*[@id=\"answer-11-0\"]"));
		answer12.click();
		scrollBy("1000");

		gonnaConfirmationBtn.click();

		getEvidence(new Object() {
		});

		String url = webDriver.getCurrentUrl();
		assertEquals(url, "http://localhost:8080/lms/exam/answerCheck");
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 「回答を送信する」ボタンを押下し試験結果画面に遷移")
	void test07() throws InterruptedException {
		// TODO ここに追加
		scrollBy("5000");

		WebElement sendAnswerBtn = webDriver
				.findElement(By.xpath("//*[@id=\"sendButton\"]"));

		Thread.sleep(3500);

		sendAnswerBtn.click();

		Alert alert = webDriver.switchTo().alert();
		alert.accept();

		getEvidence(new Object() {
		});

		String url = webDriver.getCurrentUrl();
		assertEquals(url, "http://localhost:8080/lms/exam/result");
	}

	@Test
	@Order(8)
	@DisplayName("テスト08 「戻る」ボタンを押下し試験開始画面に遷移後当該試験の結果が反映される")
	void test08() {
		// TODO ここに追加
		scrollBy("5000");

		WebElement backBtn = webDriver
				.findElement(By.xpath("//*[@id=\"examBeing\"]/div[13]/fieldset/form/input[1]"));

		backBtn.click();

		getEvidence(new Object() {
		});

		String url = webDriver.getCurrentUrl();
		assertEquals(url, "http://localhost:8080/lms/exam/start");
	}

}
