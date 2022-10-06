package qocim.format.json.test;

import criteria.test.TestFacade;
import criteria.test.TestIndicator;
import criteria.test.simple.definitions.SimpleDefinition;
import criteria.test.simple.definitions.SimpleEvaluator;
import criteria.test.simple.definitions.TestDescription;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import qocim.format.json.JsonQoCIMExport;
import qocim.format.json.JsonQoCIMImport;
import qocim.information.InformationImpl;
import qocim.information.QInformation;
import qocim.model.*;

import static java.lang.Integer.parseInt;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestJsonImportQoCIM {

	private static TestFacade facade;
	private TestIndicator testIndicator;
	private QInformation<Integer> information;

	@BeforeClass
	public static void beforeClass() {
		facade = new TestFacade();

		System.out.println(" ======= Test JsonImport =======");
	}

	@Before
	public final void before() {
		testIndicator = (TestIndicator) facade.newQoCIndicator();
		information = new InformationImpl<>("test information", 42);
		facade.qualify(information, SimpleEvaluator.INSTANCE);
	}

	@Test
	public void testImportJsonQoCIndicator() {
		QoCIndicator jsonIndicator = JsonQoCIMImport.importQoCIndicator(JsonQoCIMExport.exportQoCIndicator(testIndicator));

		assertEquals(testIndicator.name, jsonIndicator.name);
		assertEquals(testIndicator.id(), jsonIndicator.id());

		System.out.println(" - importQoCIndicator(): OK");
	}


	@Test
	public void testImportJsonWholeQoCValue() {
		QoCValue<Integer> testValue = (QoCValue<Integer>) information.indicators().get(0).qocValues().get(0);
		QoCValue<String> jsonValue = (QoCValue<String>) JsonQoCIMImport.importQoCValue(JsonQoCIMExport.exportWholeQoCValue(testValue));


		assertEquals(testValue.name, jsonValue.name);
		assertEquals(testValue.id(), jsonValue.id());
		assertEquals(testValue.value().toString(), jsonValue.value());
		assertEquals(testValue.definitionId(), jsonValue.definitionId());
		assertEquals(testValue.creationDate().toString(), jsonValue.creationDate().toString());

		System.out.println(" - importQoCValue(): OK");
	}

	@Test
	public void testImportJsonQoCCriterion() {
		QoCCriterion testCriterion = information.indicators().get(0).qocCriteria().get(0);
		QoCCriterion jsonCriterion = JsonQoCIMImport.importQoCCriterion(JsonQoCIMExport.exportQoCCriterion(testCriterion));

		assertEquals(testCriterion.name, jsonCriterion.name);
		assertEquals(testCriterion.id(), jsonCriterion.id());

		System.out.println(" - importQoCCriterion(): OK");
	}

	@Test
	public void testImportJsonQoCDescription() {
		QoCDescription testDescription = new TestDescription();
		QoCDescription jsonDescription = JsonQoCIMImport.importQoCDescription(JsonQoCIMExport.exportQoCDescription(testDescription));

		assertEquals(testDescription.name, jsonDescription.name);
		assertEquals(testDescription.description(), jsonDescription.description());
		assertEquals(testDescription.keywords(), jsonDescription.keywords());

		System.out.println(" - importQoCDescription(): OK");
	}

	@Test
	public void testImportJsonWholeQoCDefinition() {
		QoCDefinition testDefinition = new SimpleDefinition();
		QoCDefinition jsonDefinition = JsonQoCIMImport.importQoCDefinition(JsonQoCIMExport.exportWholeQoCDefinition(testDefinition));

		assertEquals(testDefinition.name, jsonDefinition.name);
		assertEquals(testDefinition.id(), jsonDefinition.id());
		assertEquals(testDefinition.isInvariant(), jsonDefinition.isInvariant());
		assertEquals(testDefinition.isDefault(), jsonDefinition.isDefault());
		assertEquals(testDefinition.unit(), jsonDefinition.unit());
		assertEquals(testDefinition.providerUri(), jsonDefinition.providerUri());
		assertEquals(testDefinition.direction(), jsonDefinition.direction());

		assertEquals(testDefinition.minValue(), jsonDefinition.minValue());
		assertEquals(testDefinition.maxValue(), jsonDefinition.maxValue());
		assertEquals(testDefinition.desription(), jsonDefinition.desription());

		System.out.println(" - importQoCDefinition(): OK");
	}
}
