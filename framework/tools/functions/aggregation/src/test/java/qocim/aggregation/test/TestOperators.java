package qocim.aggregation.test;

import criteria.test.TestFacade;
import criteria.test.TestIndicator;
import criteria.test.simple.definitions.SimpleEvaluator;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import qocim.aggregation.operator.arithmetic.EOperator;
import qocim.aggregation.operator.arithmetic.MaxOperator;
import qocim.aggregation.operator.arithmetic.OperatorFactory;
import qocim.aggregation.operator.arithmetic.MinOperator;
import qocim.aggregation.operator.utils.NotValidInformationException;
import qocim.information.InformationImpl;
import qocim.information.QInformation;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestOperators {

	private List<QInformation<?>> informationList;
	private final Integer NB_INFORMATION = 5;

	@BeforeClass
	public static void beforeClass() {
		System.out.println(" ======= Test Aggregation Operators =======");
	}

	@Before
	public final void before() {
		informationList = new LinkedList<>();
		TestFacade facade = new TestFacade();

		for(int i = 0; i <= NB_INFORMATION; i++) {
			QInformation<Integer> information = new InformationImpl<>("Test information", i);
			facade.qualify(information, SimpleEvaluator.INSTANCE);
			informationList.add(information);
		}
	}

	@Test
	public void testMinOperator() {

		MinOperator minOperator = (MinOperator) OperatorFactory.getOperator(EOperator.MIN);

		assertTrue(minOperator instanceof MinOperator);

		try {
			QInformation<?> result = minOperator.applyOperator(informationList);
			assertEquals(0, result.data());
		} catch (NotValidInformationException e) {
			throw new RuntimeException(e);
		}

		System.out.println(" - minOperator: OK");
	}


	@Test
	public void testMaxOperator() {

		MaxOperator maxOperator = (MaxOperator) OperatorFactory.getOperator(EOperator.MAX);

		assertTrue(maxOperator instanceof MaxOperator);

		try {
			QInformation<?> result = maxOperator.applyOperator(informationList);
			assertEquals(NB_INFORMATION, result.data());
		} catch (NotValidInformationException e) {
			throw new RuntimeException(e);
		}

		System.out.println(" - maxOperator: OK");
	}
}
