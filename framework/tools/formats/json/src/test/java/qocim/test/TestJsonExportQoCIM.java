package qocim.test;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import qocim.datamodel.precision.PrecisionFacade;
import qocim.datamodel.precision.PrecisionIndicator;
import qocim.datamodel.precision.simple.definitions.SimpleEvaluator;
import qocim.format.json.JsonQoCIMExport;
import qocim.information.InformationImpl;
import qocim.information.QInformation;
import qocim.model.QoCCriterion;
import qocim.model.QoCDefinition;
import qocim.model.QoCDescription;
import qocim.model.QoCValue;

import static org.junit.Assert.assertEquals;

public class TestJsonExportQoCIM {

	private static PrecisionFacade facade;
	private PrecisionIndicator precision;
	private QInformation<Integer> information;

	@BeforeClass
	public static void beforeClass() {
		facade = new PrecisionFacade();

		System.out.println(" ======= Test JsonExport =======");
	}

	@Before
	public final void before() {
		precision = (PrecisionIndicator) facade.newQoCIndicator();
		information = new InformationImpl<>("test information", 42);
		facade.qualify(information, SimpleEvaluator.INSTANCE);
	}

	@Test
	public void testJsonIndicator() {
		String expectedValue = "{\"name\":\"" + precision.name + "\",\"id\":" + precision.id() + "}";
		assertEquals(expectedValue, JsonQoCIMExport.exportIndicator(precision).toString());

		System.out.println(" - exportQoCIndicator(): OK");
	}

	@Test
	public void testJsonQoCValue() {

		QoCValue<Integer> testValue = (QoCValue<Integer>) information.indicators().get(0).qocValues().get(0);

		String expectedValue = "{\"name\":\"" + testValue.name + "\"," +
			"\"id\":" + testValue.id() + "," +
			"\"creation date\":\"" + testValue.creationDate() + "\"," +
			"\"definition id\":\"" + testValue.definitionId() + "\"," +
			"\"indicator name\":\"" + testValue.container().name + "\"," +
			"\"value\":\"" + testValue.value() + "\"" +
			"}";
		assertEquals(expectedValue, JsonQoCIMExport.exportQoCValue(testValue).toString());

		System.out.println(" - exportQoCValue(): OK");
	}

	@Test
	public void testJsonQoCCriterion() {

		QoCCriterion testCriterion = information.indicators().get(0).qocCriteria().get(0);

		String expectedValue = "{\"name\":\"" + testCriterion.name + "\"," +
			"\"id\":\"" + testCriterion.id() + "\"}";
		assertEquals(expectedValue, JsonQoCIMExport.exportQoCCriterion(testCriterion).toString());

		System.out.println(" - exportQoCCriterion(): OK");
	}

	@Test
	public void testJsonQoCDefinition() {

		QoCDefinition testDefinition = (QoCDefinition) information.indicators().get(0).qocCriteria().get(0).qocDefinitions().get(0);

		String expectedValue = "{\"name\":\"" + testDefinition.name + "\"," +
			"\"id\":\"" + testDefinition.id() + "\"," +
			"\"is invariant\":" + testDefinition.isInvariant() + "," +
			"\"direction\":\"" + testDefinition.direction() + "\"," +
			"\"provider uri\":\"" + testDefinition.providerUri() + "\"," +
			"\"unit\":\"" + testDefinition.unit() + "\"" +
			"}";
		assertEquals(expectedValue, JsonQoCIMExport.exportQoCDefinition(testDefinition).toString());

		System.out.println(" - exportQoCDefinition(): OK");
	}

	@Test
	public void testJsonQoCDescription() {

		QoCDescription testDescription = (QoCDescription) information.indicators().get(0).qocCriteria().get(0).qocDefinitions().get(0).desription();

		String expectedValue = "{\"informal description\":\"" + testDescription.description() + "\"," +
			"\"keywords\":\"" + testDescription.keywords() + "\"" +
			"}";
		assertEquals(expectedValue, JsonQoCIMExport.exportQoCDescription(testDescription).toString());

		System.out.println(" - exportQoCDescription(): OK");
	}
}