package test.qocim.information;

import javassist.compiler.SyntaxError;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import qocim.information.InformationImpl;
import qocim.model.QoCIndicator;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestInformation {

	private final String INFORMATION_NAME = "tes informations";
	private final Integer INFORMATION_DATA = 42;
	private Date creationDate;

	private InformationImpl<Integer> testInformation;

	@BeforeClass
	public static final void beforeClass() {
		System.out.println(" ======= Test QInformation =======");
	}

	@Before
	public final void before() {
		creationDate = new Date();
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		testInformation = new InformationImpl<>(INFORMATION_NAME, INFORMATION_DATA);
	}

	@Test
	public final void testCompareTo() {
		assertEquals(0, testInformation.compareTo(testInformation));

		// Data not equals
		InformationImpl<Integer> testInformation1 = new InformationImpl<>(INFORMATION_NAME, INFORMATION_DATA + 1);
		assertEquals(-1, testInformation.compareTo(testInformation1));

		// Name not equals
		InformationImpl<Integer> testInformation2 = new InformationImpl<>(INFORMATION_NAME + " test", INFORMATION_DATA);
		assertEquals(-2, testInformation.compareTo(testInformation2));

		// Creation date not equals => wait 1 second
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		InformationImpl<Integer> testInformation3 = new InformationImpl<>(INFORMATION_NAME, INFORMATION_DATA);
		assertEquals(-3, testInformation.compareTo(testInformation3));

		System.out.println(" - CompareTo(): OK");
	}

	@Test
	public final void testName() {

		assertEquals(INFORMATION_NAME, testInformation.name());
		System.out.println(" - name: OK");
	}

	@Test
	public final void testCreationDate() {
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		Date date = new Date();
		assertTrue(creationDate.before(testInformation.creationDate()));
		assertTrue(date.after(testInformation.creationDate()));

		System.out.println(" - creationDate(): OK");
	}

	@Test
	public final void testData() {
		assertEquals(INFORMATION_DATA, testInformation.data());
		System.out.println(" - data(): OK");
	}

	@Test
	public final void testIndicators() {
		assertEquals(0, testInformation.indicators().size());
		testInformation.indicators().add(new QoCIndicator("test indicator", 1));
		assertEquals(1, testInformation.indicators().size());

		testInformation.indicators().clear();
		assertEquals(0, testInformation.indicators().size());

		List<QoCIndicator> indicators = new LinkedList<>();
		indicators.add(new QoCIndicator("test indicator", 1));
		indicators.add(new QoCIndicator("test indicator", 1));
		indicators.add(new QoCIndicator("test indicator", 1));
		testInformation.setIndicators(indicators);
		assertEquals(3, testInformation.indicators().size());
		assertEquals(indicators, testInformation.indicators());

		System.out.println(" - indicators(): OK");
	}
}
