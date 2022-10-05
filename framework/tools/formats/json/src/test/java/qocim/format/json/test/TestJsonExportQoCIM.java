package qocim.format.json.test;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import criteria.test.TestFacade;
import criteria.test.TestIndicator;
import criteria.test.simple.definitions.SimpleEvaluator;
import criteria.test.simple.definitions.TestDescription;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import qocim.format.json.JsonQoCIMExport;
import qocim.information.InformationImpl;
import qocim.information.QInformation;
import qocim.model.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestJsonExportQoCIM {

	private static TestFacade facade;
	private TestIndicator testIndicator;
	private QInformation<Integer> information;

	@BeforeClass
	public static void beforeClass() {
		facade = new TestFacade();

		System.out.println(" ======= Test JsonExport =======");
	}

	@Before
	public final void before() {
		testIndicator = (TestIndicator) facade.newQoCIndicator();
		information = new InformationImpl<>("test information", 42);
		facade.qualify(information, SimpleEvaluator.INSTANCE);
	}

	@Test
	public void testExportJsonIndicator() {
		String expectedValue = "{\"" + JsonQoCIMExport.NAME_PROPERTY + "\":\"" + testIndicator.name + "\",\"id\":" + testIndicator.id() + "}";
		assertEquals(expectedValue, JsonQoCIMExport.exportQoCIndicator(testIndicator).toString());

		System.out.println(" - exportQoCIndicator(): OK");
	}

	@Test
	public void testExportJsonQoCValue() {

		QoCValue<Integer> testValue = (QoCValue<Integer>) information.indicators().get(0).qocValues().get(0);

		DateFormat dateFormat = new SimpleDateFormat(QoCValue.DATE_FORMAT);
		String expectedValue = "{\"" + JsonQoCIMExport.NAME_PROPERTY + "\":\"" + testValue.name + "\"," +
			"\"" + QoCValue.ID + "\":" + testValue.id() + "," +
			"\"" + QoCValue.CREATION_DATE + "\":\"" +  dateFormat.format(testValue.creationDate()) + "\"," +
			"\"" + QoCValue.DEFINITION_ID + "\":\"" + testValue.definitionId() + "\"," +
			"\"" + QoCValue.VALUE + "\":\"" + testValue.value() + "\"" +
			"}";
		assertEquals(expectedValue, JsonQoCIMExport.exportQoCValue(testValue).toString());

		System.out.println(" - exportQoCValue(): OK");
	}

	@Test
	public void testExportJsonQoCCriterion() {

		QoCCriterion testCriterion = information.indicators().get(0).qocCriteria().get(0);

		String expectedValue = "{\"" + JsonQoCIMExport.NAME_PROPERTY + "\":\"" + testCriterion.name + "\"," +
			"\"" + QoCCriterion.ID + "\":\"" + testCriterion.id() + "\"}";
		assertEquals(expectedValue, JsonQoCIMExport.exportQoCCriterion(testCriterion).toString());

		System.out.println(" - exportQoCCriterion(): OK");
	}

	@Test
	public void testExportJsonQoCDefinition() {

		QoCDefinition testDefinition = (QoCDefinition) information.indicators().get(0).qocCriteria().get(0).qocDefinitions().get(0);

		String expectedValue = "{\"" + JsonQoCIMExport.NAME_PROPERTY + "\":\"" + testDefinition.name + "\"," +
			"\"" + QoCDefinition.ID + "\":\"" + testDefinition.id() + "\"," +
			"\"" + QoCDefinition.IS_INVARIANT + "\":" + testDefinition.isInvariant() + "," +
			"\"" + QoCDefinition.DIRECTION + "\":\"" + testDefinition.direction() + "\"," +
			"\"" + QoCDefinition.PROVIDER_URI + "\":\"" + testDefinition.providerUri() + "\"," +
			"\"" + QoCDefinition.UNIT + "\":\"" + testDefinition.unit() + "\"," +
			"\"" + QoCDefinition.DESCRIPTION + "\":" + JsonQoCIMExport.exportQoCDescription(new TestDescription()).toString() +
			"}";
		assertEquals(expectedValue, JsonQoCIMExport.exportQoCDefinition(testDefinition).toString());

		System.out.println(" - exportQoCDefinition(): OK");
	}

	@Test
	public void testExportJsonQoCDescription() {

		QoCDescription testDescription = (QoCDescription) information.indicators().get(0).qocCriteria().get(0).qocDefinitions().get(0).desription();

		String expectedValue = "{\"" + JsonQoCIMExport.NAME_PROPERTY + "\":\"" + testDescription.name + "\"," +
			"\"" + QoCDescription.DESCRIPTION + "\":\"" + testDescription.description() + "\"," +
			"\"" + QoCDescription.KEYWORDS + "\":[" + testDescription.keywords().stream().map(s -> "\"" + s + "\"").collect(Collectors.joining(",")) + "]" +
			"}";
		assertEquals(expectedValue, JsonQoCIMExport.exportQoCDescription(testDescription).toString());

		System.out.println(" - exportQoCDescription(): OK");
	}

	@Test
	public void testExportJsonEntireIndicator() {

		JsonObject testJson = JsonQoCIMExport.exportEntireIndicator(information.indicators().get(0));
		JsonElement element;
		JsonArray jsonArray;

		element = testJson.get(JsonQoCIMExport.NAME_PROPERTY);
		assertNotNull(element);

		element = testJson.get(QoCIndicator.ID);
		assertNotNull(element);

		element = testJson.get(JsonQoCIMExport.CRITERIA_PROPERTY);
		assertNotNull(element);
		jsonArray = element.getAsJsonArray();
		assertEquals(1, jsonArray.size());

		element = testJson.get(JsonQoCIMExport.VALUES_PROPERTY);
		assertNotNull(element);
		jsonArray = element.getAsJsonArray();
		assertEquals(1, jsonArray.size());

		element = testJson.get(JsonQoCIMExport.DEFINITIONS_PROPERTY);
		assertNotNull(element);
		jsonArray = element.getAsJsonArray();
		assertEquals(1, jsonArray.size());

		System.out.println(" - exportEntireIndicator(): OK");
	}
}
