package test.qocim.datamodel.freshness;

import org.junit.BeforeClass;
import org.junit.Test;
import qocim.datamodel.freshness.FreshnessFacade;
import qocim.datamodel.freshness.FreshnessIndicator;
import qocim.datamodel.freshness.criteria.FreshnessCriterion;
import qocim.datamodel.freshness.definitions.FreshnessDescription;
import qocim.datamodel.freshness.definitions.random.RandomDefinition;
import qocim.datamodel.freshness.definitions.random.RandomEvaluator;
import qocim.datamodel.freshness.definitions.simple.SimpleDefinition;
import qocim.datamodel.freshness.definitions.simple.SimpleEvaluator;
import qocim.datamodel.freshness.definitions.values.FreshnessValue;
import qocim.datamodel.freshness.definitions.values.MaxFreshnessValue;
import qocim.datamodel.freshness.definitions.values.MinFreshnessValue;
import qocim.information.InformationImpl;
import qocim.information.QInformation;
import qocim.model.QoCDefinition;

import static org.junit.Assert.assertEquals;

public class TestFacade {

	private static FreshnessFacade facade;

	@BeforeClass
	public static void beforeClass() {

		facade = new FreshnessFacade();

		System.out.println(" ======= Test FreshnessFacade =======");
	}

	private void testDefinition(final QoCDefinition definition) {
		assertEquals(FreshnessDescription.NAME, definition.desription().name);
		assertEquals(definition, definition.desription().container());

		assertEquals(MinFreshnessValue.ID, definition.minValue().id());
		assertEquals(definition, definition.minValue().container());

		assertEquals(MaxFreshnessValue.ID, definition.maxValue().id());
		assertEquals(definition, definition.maxValue().container());
	}

	private void testIndicator(final FreshnessIndicator indicator) {
		assertEquals(FreshnessIndicator.NAME, indicator.name);

		assertEquals(1, indicator.qocCriteria().size());

		assertEquals(FreshnessCriterion.NAME, indicator.qocCriteria().get(0).name);
		FreshnessCriterion criterion = (FreshnessCriterion) indicator.qocCriteria().get(0);
		assertEquals(indicator, criterion.container());

		assertEquals(2, criterion.qocDefinitions().size());

		SimpleDefinition simpleDefinition = (SimpleDefinition) criterion.getQoCDefinitionById(SimpleDefinition.ID);
		assertEquals(criterion, simpleDefinition.container());
		testDefinition(simpleDefinition);

		RandomDefinition randomDefinition = (RandomDefinition) criterion.getQoCDefinitionById(RandomDefinition.ID);
		assertEquals(criterion, randomDefinition.container());
		testDefinition(randomDefinition);
	}

	@Test
	public final void testNewIndicator() {
		FreshnessIndicator indicator = (FreshnessIndicator) facade.newQoCIndicator();

		testIndicator(indicator);

		System.out.println(" - newIndicator(): OK");
	}

	@Test
	public final void testQualifyRandomEvaluator() {

		QInformation<Integer> information = new InformationImpl<>("test information", 42);
		facade.qualify(information, RandomEvaluator.INSTANCE);

		assertEquals(1, information.indicators().size());
		FreshnessIndicator indicator = (FreshnessIndicator) information.indicators().get(0);
		testIndicator(indicator);

		assertEquals(1, indicator.qocValues().size());
		FreshnessValue value = (FreshnessValue) indicator.qocValues().get(0);
		assertEquals(indicator, value.container());
		assertEquals(RandomDefinition.ID, value.definitionId());

		System.out.println(" - qualifyRandomEvaluator(): OK");
	}

	@Test
	public final void testSimpleRandomEvaluator() {

		QInformation<Integer> information = new InformationImpl<>("test information", 42);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		facade.qualify(information, SimpleEvaluator.INSTANCE);

		assertEquals(1, information.indicators().size());
		FreshnessIndicator indicator = (FreshnessIndicator) information.indicators().get(0);
		testIndicator(indicator);

		assertEquals(1, indicator.qocValues().size());
		FreshnessValue value = (FreshnessValue) indicator.qocValues().get(0);
		assertEquals(indicator, value.container());
		assertEquals(SimpleDefinition.ID, value.definitionId());

		System.out.println(" - qualifySimpleEvaluator(): OK");
	}
}
