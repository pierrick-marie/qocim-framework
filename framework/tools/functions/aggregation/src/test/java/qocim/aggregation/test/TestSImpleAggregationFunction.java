package qocim.aggregation.test;

import criteria.test.TestFacade;
import criteria.test.simple.definitions.SimpleEvaluator;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import qocim.aggregation.impl.SimpleAggregationFunction;
import qocim.aggregation.operator.arithmetic.EOperator;
import qocim.aggregation.operator.arithmetic.OperatorFactory;
import qocim.information.InformationImpl;
import qocim.information.QInformation;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class TestSImpleAggregationFunction {

	private ResultListener listener;
	private List<QInformation<?>> informationList;
	private final Integer NB_INFORMATION = 5;

	@BeforeClass
	public static void beforeClass() {
		System.out.println(" ======= Test Aggregation Functions =======");
	}

	@Before
	public final void before() {
		TestFacade facade = new TestFacade();
		listener = new ResultListener();
		informationList = new LinkedList<>();

		for(int i = 0; i <= NB_INFORMATION; i++) {
			QInformation<Integer> information = new InformationImpl<>("Test information", i);
			facade.qualify(information, SimpleEvaluator.INSTANCE);
			informationList.add(information);
		}
	}

	@Test
	public void testSimpleAggregation() {

		SimpleAggregationFunction function = new SimpleAggregationFunction(NB_INFORMATION + 1, OperatorFactory.getOperator(EOperator.MIN), listener);

		for (int i = 0; i < NB_INFORMATION; i++) {
			function.addInformation(informationList.get(i));
			assertNull(listener.getInformation());
		}

		function.addInformation(informationList.get(NB_INFORMATION));
		assertEquals(informationList.get(0), listener.getInformation());

		System.out.println(" - SimpleAggregatorFunction: OK");
	}
}
