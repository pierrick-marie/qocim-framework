package test.qocim.datamodel.precision;

import org.junit.BeforeClass;
import org.junit.Test;
import qocim.datamodel.precision.PrecisionIndicator;
import qocim.datamodel.precision.simple.definitions.SimpleEvaluator;
import qocim.datamodel.precision.PrecisionFacade;
import qocim.datamodel.precision.simple.criteria.PrecisionCriterion;
import qocim.datamodel.precision.simple.definitions.values.MaxPrecisionValue;
import qocim.datamodel.precision.simple.definitions.values.MinPrecisionValue;
import qocim.datamodel.precision.simple.definitions.PrecisionDescription;
import qocim.datamodel.precision.simple.definitions.SimpleDefinition;
import qocim.datamodel.precision.simple.definitions.values.PrecisionValue;
import qocim.information.InformationImpl;
import qocim.information.QInformation;

import static org.junit.Assert.assertEquals;

public class TestFacade {

	private static PrecisionFacade facade;

	@BeforeClass
	public static void beforeClass() {

		facade = new PrecisionFacade();

		System.out.println(" ======= Test PrecisionFacade =======");
	}

	private void testIndicator(final PrecisionIndicator indicator) {
		assertEquals(PrecisionIndicator.NAME, indicator.name);

		assertEquals(1, indicator.qocCriteria().size());

		assertEquals(PrecisionCriterion.NAME, indicator.qocCriteria().get(0).name);
		PrecisionCriterion criterion = (PrecisionCriterion) indicator.qocCriteria().get(0);
		assertEquals(indicator, criterion.container());

		assertEquals(SimpleDefinition.NAME, criterion.qocDefinitions().get(0).name);
		SimpleDefinition definition = (SimpleDefinition) criterion.qocDefinitions().get(0);
		assertEquals(criterion, definition.container());

		assertEquals(PrecisionDescription.NAME, definition.desription().name);
		assertEquals(definition, definition.desription().container());

		assertEquals(MinPrecisionValue.ID, definition.minValue().id());
		assertEquals(definition, definition.minValue().container());

		assertEquals(MaxPrecisionValue.ID, definition.maxValue().id());
		assertEquals(definition, definition.maxValue().container());
	}

	@Test
	public final void testNewIndicator() {
		PrecisionIndicator indicator = (PrecisionIndicator) facade.newQoCIndicator();

		testIndicator(indicator);

		System.out.println(" - newIndicator(): OK");
	}

	@Test
	public final void testQualify() {

		QInformation<Integer> information = new InformationImpl<>("test information", 42);
		facade.qualify(information, SimpleEvaluator.INSTANCE);

		assertEquals(1, information.indicators().size());
		PrecisionIndicator indicator = (PrecisionIndicator) information.indicators().get(0);
		testIndicator(indicator);

		assertEquals(1, indicator.qocValues().size());
		PrecisionValue value = (PrecisionValue) indicator.qocValues().get(0);
		assertEquals(indicator, value.container());
		assertEquals(SimpleDefinition.ID, value.definitionId());

		System.out.println(" - qualify(): OK");
	}
}
