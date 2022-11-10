package qocim.aggregation.test;

import criteria.test.TestFacade;
import criteria.test.simple.definitions.SimpleEvaluator;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import qocim.aggregation.impl.AggregationFunctionTimer;
import qocim.aggregation.impl.SimpleAggregationFunction;
import qocim.aggregation.operator.arithmetic.EOperator;
import qocim.aggregation.operator.arithmetic.OperatorFactory;
import qocim.information.InformationImpl;
import qocim.information.QInformation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class TestTimerAggregationFunction {

	private ResultListener listener;
	private Integer informationData = 0;
	private final Integer NB_MILLI_SECOND = 1000;
	private TestFacade facade;

	@BeforeClass
	public static void beforeClass() {
		System.out.println(" ======= Test Aggregation Functions =======");
	}

	@Before
	public final void before() {
		facade = new TestFacade();
		listener = new ResultListener();
	}

	private QInformation<?> getNewInformation() {
		QInformation<Integer> information = new InformationImpl<>("Test information", informationData++);
		facade.qualify(information, SimpleEvaluator.INSTANCE);

		return information;
	}

	@Test
	public void testAggregationTimer() {

		AggregationFunctionTimer function = new AggregationFunctionTimer(NB_MILLI_SECOND, OperatorFactory.getOperator(EOperator.MAX), listener);
		function.start();

		int nbInformation = 5;
		for( int i = 0; i <= nbInformation; i++ ) {
			function.addInformation(getNewInformation());
			assertNull(listener.getInformation());
		}

		try {
			Thread.sleep(NB_MILLI_SECOND + 1);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}

		assertEquals(nbInformation, listener.getInformation().data());

		System.out.println(" - AggregatorFunctionTimer: OK");
	}
}
