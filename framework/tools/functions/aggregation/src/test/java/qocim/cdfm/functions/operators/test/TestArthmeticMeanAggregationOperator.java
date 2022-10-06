/**
  * This file is part of the QoCIM middleware.
 *
 * Copyright (C) 2014 IRIT, Télécom SudParis
 *
 * The QoCIM software is free software: you can redistribute it and/or modify
 * It under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * The QoCIM software platform is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * See the GNU Lesser General Public License
 * for more details: http://www.gnu.org/licenses
 *
 * Initial developer(s): Pierrick MARIE
 * Contributor(s):
 */
package qocim.cdfm.functions.operators.test;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import mucontext.datamodel.context.ContextDataModelFacade;
import mucontext.datamodel.context.ContextEntity;
import mucontext.datamodel.context.ContextObservable;
import mucontext.datamodel.context.ContextObservation;
import mucontext.datamodel.context.ContextReport;
import qocim.cdfm.function.impl.CDFMFunction;
import qocim.cdfm.operator.aggregation.impl.MeanAggregator;
import qocim.datamodel.test.criterion.one.TestCriterionOneFactory;
import qocim.datamodel.test.criterion.zero.TestCriterionZeroFactory;
import qocim.datamodel.utils.QoCIMLogger;
import qocim.functions.IQoCIMFunctionListener;

public class TestArthmeticMeanAggregationOperator {

	// # # # # # CONSTANTS # # # # #

	private static final String DEFAULT_ENTITY_URI = "myuri://localhost";
	private static final String DEFAULT_ENTITY_NAME = "entity_name";
	private static final String DEFAULT_OBSERVABLE_URI = "sensor";
	private static final String DEFAULT_OBSERVABLE_NAME = "observable_name";
	private static final double DEFAULT_OBSERVATION_VALUE = 42.0;
	private static final String DEFAULT_SECOND_OBSERVABLE_URI = "my_sensor";

	// # # # # # PRIVATE VARIABLES # # # # #

	private static Integer counter_contextObservationId;
	private static List<ContextReport> list_contextReport;
	private ContextReport expectedContextReport;
	private static ContextDataModelFacade contextDataFacade;
	private static CDFMFunction function;
	private static IQoCIMFunctionListener resultListener;
	private static qocim.cdfm.function.IAgregationOperator arithmeticOperator;

	// # # # # # PUBLIC METHODS # # # # #

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		QoCIMLogger.logger.setLevel(Level.OFF);
		contextDataFacade = new ContextDataModelFacade("facade");
		list_contextReport = new ArrayList<ContextReport>();
		counter_contextObservationId = 0;
		arithmeticOperator = new MeanAggregator();
	}

	@Before
	public void setUp() throws Exception {
		list_contextReport.clear();
		function = new CDFMFunction();
	}

	@After
	public void execTest() {
		// - - - - - INIT VARIABLES - - - - -
		resultListener = new ResultListenerTest(expectedContextReport);
		function.setResultListener(resultListener);
		function.setOperator(arithmeticOperator);
		function.setNbHandledContextReport(list_contextReport.size());
		for (final ContextReport loop_contextReport : list_contextReport) {
			function.addContextReport(loop_contextReport);
		}
	}

	@Test
	public void ONEcontextReport_TENcomparableObservation_ZEROqocMetaData() {
		// - - - - - INIT VARIABLES - - - - -
		final int NB_OBSERVATION = 10;
		final ContextReport contextReport = contextDataFacade.createContextReport("01 - 0");
		final double[] arrayValues = new double[NB_OBSERVATION];
		Integer indexArrayValue = 0;
		Double contextObservationValue = 1.0;
		final Mean mean = new Mean();
		ContextEntity contextEntity, expectedContextEntity;
		ContextObservable contextObservable, expectedContextObservable;
		// - - - - - CORE OF THE METHOD - - - - -
		contextEntity = createContextEntity(DEFAULT_ENTITY_NAME, DEFAULT_ENTITY_URI);
		contextObservable = createContextObservable(DEFAULT_OBSERVABLE_NAME, DEFAULT_OBSERVABLE_URI, contextEntity);
		for (int index_observation = 0; index_observation < NB_OBSERVATION; index_observation++) {
			createContextObservation(contextObservationValue, contextReport, contextObservable);
			arrayValues[indexArrayValue++] = contextObservationValue++;
		}
		list_contextReport.add(contextReport);
		expectedContextReport = contextDataFacade.createContextReport("01 - 0");
		expectedContextEntity = createContextEntity(DEFAULT_ENTITY_NAME, DEFAULT_ENTITY_URI);
		expectedContextObservable = createContextObservable(DEFAULT_OBSERVABLE_NAME, DEFAULT_OBSERVABLE_URI,
				expectedContextEntity);
		createContextObservation(mean.evaluate(arrayValues), expectedContextReport, expectedContextObservable);
	}

	@Test
	public void ONEcontextReport_ONEcomparableObservation_TENcomparableQoCMetaData() {
		// - - - - - INIT VARIABLES - - - - -
		final int NB_QOC_METADATA = 10;
		final ContextReport contextReport = contextDataFacade.createContextReport("01 - 0");
		final double[] arrayValues = new double[NB_QOC_METADATA];
		Integer indexArrayValue = 0;
		Double qocMetricValue = 1.0;
		final Mean mean = new Mean();
		ContextEntity contextEntity, expectedContextEntity;
		ContextObservable contextObservable, expectedContextObservable;
		ContextObservation<?> contextObservation, expectedContextObservation;
		// - - - - - CORE OF THE METHOD - - - - -
		contextEntity = createContextEntity(DEFAULT_ENTITY_NAME, DEFAULT_ENTITY_URI);
		contextObservable = createContextObservable(DEFAULT_OBSERVABLE_NAME, DEFAULT_OBSERVABLE_URI, contextEntity);
		contextObservation = createContextObservation(DEFAULT_OBSERVATION_VALUE, contextReport, contextObservable);
		for (int index_observation = 0; index_observation < NB_QOC_METADATA; index_observation++) {
			contextObservation.listQocIndicator
					.add(TestCriterionZeroFactory.getInstance().newQoCIndicator("" + indexArrayValue, qocMetricValue));
			arrayValues[indexArrayValue++] = qocMetricValue++;
		}
		list_contextReport.add(contextReport);
		expectedContextReport = contextDataFacade.createContextReport("01 - 0");
		expectedContextEntity = createContextEntity(DEFAULT_ENTITY_NAME, DEFAULT_ENTITY_URI);
		expectedContextObservable = createContextObservable(DEFAULT_OBSERVABLE_NAME, DEFAULT_OBSERVABLE_URI,
				expectedContextEntity);
		expectedContextObservation = createContextObservation(DEFAULT_OBSERVATION_VALUE, expectedContextReport,
				expectedContextObservable);
		expectedContextObservation.listQocIndicator.add(TestCriterionZeroFactory.getInstance()
				.newQoCIndicator("" + indexArrayValue, mean.evaluate(arrayValues)));
	}

	@Test
	public void ONEcontextReport_TWOcomparableObservation_TENcomparableQoCMetaData() {
		// - - - - - INIT VARIABLES - - - - -
		final int NB_QOC_METADATA = 10;
		final ContextReport contextReport = contextDataFacade.createContextReport("01 - 0");
		final double[] arrayValues = new double[NB_QOC_METADATA * 2];
		Integer indexArrayValue = 0;
		Double qocMetricValue = 1.0;
		final Mean mean = new Mean();
		ContextEntity contextEntity, expectedContextEntity;
		ContextObservable contextObservable, expectedContextObservable;
		ContextObservation<?> contextObservation1, contextObservation2, expectedContextObservation;
		// - - - - - CORE OF THE METHOD - - - - -
		contextEntity = createContextEntity(DEFAULT_ENTITY_NAME, DEFAULT_ENTITY_URI);
		contextObservable = createContextObservable(DEFAULT_OBSERVABLE_NAME, DEFAULT_OBSERVABLE_URI, contextEntity);
		contextObservation1 = createContextObservation(DEFAULT_OBSERVATION_VALUE, contextReport, contextObservable);
		contextObservation2 = createContextObservation(DEFAULT_OBSERVATION_VALUE, contextReport, contextObservable);
		for (int index_observation = 0; index_observation < NB_QOC_METADATA; index_observation++) {
			contextObservation1.listQocIndicator
					.add(TestCriterionZeroFactory.getInstance().newQoCIndicator("" + indexArrayValue, qocMetricValue));
			arrayValues[indexArrayValue++] = qocMetricValue++;
			contextObservation2.listQocIndicator
					.add(TestCriterionZeroFactory.getInstance().newQoCIndicator("" + indexArrayValue, qocMetricValue));
			arrayValues[indexArrayValue++] = qocMetricValue++;
		}
		list_contextReport.add(contextReport);
		expectedContextReport = contextDataFacade.createContextReport("01 - 0");
		expectedContextEntity = createContextEntity(DEFAULT_ENTITY_NAME, DEFAULT_ENTITY_URI);
		expectedContextObservable = createContextObservable(DEFAULT_OBSERVABLE_NAME, DEFAULT_OBSERVABLE_URI,
				expectedContextEntity);
		expectedContextObservation = createContextObservation(DEFAULT_OBSERVATION_VALUE, expectedContextReport,
				expectedContextObservable);
		expectedContextObservation.listQocIndicator.add(TestCriterionZeroFactory.getInstance()
				.newQoCIndicator("" + indexArrayValue, mean.evaluate(arrayValues)));
	}

	@Test
	public void ONEcontextReport_TWOcomparableObservation_TENdifferentQoCMetaData() {
		// - - - - - INIT VARIABLES - - - - -
		final int NB_QOC_METADATA = 10;
		final ContextReport contextReport = contextDataFacade.createContextReport("01 - 0");
		final double[] arrayValues1_1 = new double[NB_QOC_METADATA];
		final double[] arrayValues1_2 = new double[NB_QOC_METADATA];
		final double[] arrayValues2_1 = new double[NB_QOC_METADATA];
		final double[] arrayValues2_2 = new double[NB_QOC_METADATA];
		Integer indexArrayValue = 0;
		Double qocMetricValue = 1.0;
		final Mean mean = new Mean();
		ContextEntity contextEntity, expectedContextEntity;
		ContextObservable contextObservable1, contextObservable2, expectedContextObservable1,
				expectedContextObservable2;
		ContextObservation<?> contextObservation1, contextObservation2;
		final ContextObservation<?> expectedContextObservation1, expectedContextObservation2;
		// - - - - - CORE OF THE METHOD - - - - -
		contextEntity = createContextEntity(DEFAULT_ENTITY_NAME, DEFAULT_ENTITY_URI);
		contextObservable1 = createContextObservable(DEFAULT_OBSERVABLE_NAME, DEFAULT_OBSERVABLE_URI, contextEntity);
		contextObservable2 = createContextObservable(DEFAULT_OBSERVABLE_NAME, DEFAULT_SECOND_OBSERVABLE_URI,
				contextEntity);
		contextObservation1 = createContextObservation(DEFAULT_OBSERVATION_VALUE, contextReport, contextObservable1);
		contextObservation2 = createContextObservation(DEFAULT_OBSERVATION_VALUE, contextReport, contextObservable2);
		for (int index_observation = 0; index_observation < NB_QOC_METADATA; index_observation++) {
			contextObservation1.listQocIndicator
					.add(TestCriterionZeroFactory.getInstance().newQoCIndicator("" + indexArrayValue, qocMetricValue));
			arrayValues1_1[indexArrayValue] = qocMetricValue++;
			contextObservation1.listQocIndicator
					.add(TestCriterionOneFactory.getInstance().newQoCIndicator("" + indexArrayValue, qocMetricValue));
			arrayValues1_2[indexArrayValue] = qocMetricValue++;
			contextObservation2.listQocIndicator
					.add(TestCriterionZeroFactory.getInstance().newQoCIndicator("" + indexArrayValue, qocMetricValue));
			arrayValues2_1[indexArrayValue] = qocMetricValue++;
			contextObservation2.listQocIndicator
					.add(TestCriterionOneFactory.getInstance().newQoCIndicator("" + indexArrayValue, qocMetricValue));
			arrayValues2_2[indexArrayValue++] = qocMetricValue++;
		}
		list_contextReport.add(contextReport);
		expectedContextReport = contextDataFacade.createContextReport("01 - 0");
		expectedContextEntity = createContextEntity(DEFAULT_ENTITY_NAME, DEFAULT_ENTITY_URI);
		expectedContextObservable1 = createContextObservable(DEFAULT_OBSERVABLE_NAME, DEFAULT_OBSERVABLE_URI,
				expectedContextEntity);
		expectedContextObservable2 = createContextObservable(DEFAULT_OBSERVABLE_NAME, DEFAULT_SECOND_OBSERVABLE_URI,
				expectedContextEntity);
		expectedContextObservation1 = createContextObservation(DEFAULT_OBSERVATION_VALUE, expectedContextReport,
				expectedContextObservable1);
		expectedContextObservation1.listQocIndicator.add(TestCriterionZeroFactory.getInstance()
				.newQoCIndicator("" + indexArrayValue, mean.evaluate(arrayValues1_1)));
		expectedContextObservation1.listQocIndicator.add(TestCriterionOneFactory.getInstance()
				.newQoCIndicator("" + indexArrayValue, mean.evaluate(arrayValues1_2)));
		expectedContextObservation2 = createContextObservation(DEFAULT_OBSERVATION_VALUE, expectedContextReport,
				expectedContextObservable2);
		expectedContextObservation2.listQocIndicator.add(TestCriterionZeroFactory.getInstance()
				.newQoCIndicator("" + indexArrayValue, mean.evaluate(arrayValues2_1)));
		expectedContextObservation2.listQocIndicator.add(TestCriterionOneFactory.getInstance()
				.newQoCIndicator("" + indexArrayValue, mean.evaluate(arrayValues2_2)));
	}

	@Test
	public void ONEcontextReport_ONEcomparableObservation_TENdifferentQoCMetaData() {
		// - - - - - INIT VARIABLES - - - - -
		final int NB_QOC_METADATA = 10;
		final ContextReport contextReport = contextDataFacade.createContextReport("01 - 0");
		final double[] arrayValues1 = new double[NB_QOC_METADATA];
		final double[] arrayValues2 = new double[NB_QOC_METADATA];
		Integer indexArrayValue = 0;
		Double qocMetricValue = 1.0;
		final Mean mean = new Mean();
		ContextEntity contextEntity, expectedContextEntity;
		ContextObservable contextObservable, expectedContextObservable;
		ContextObservation<?> contextObservation, expectedContextObservation;
		// - - - - - CORE OF THE METHOD - - - - -
		contextEntity = createContextEntity(DEFAULT_ENTITY_NAME, DEFAULT_ENTITY_URI);
		contextObservable = createContextObservable(DEFAULT_OBSERVABLE_NAME, DEFAULT_OBSERVABLE_URI, contextEntity);
		contextObservation = createContextObservation(DEFAULT_OBSERVATION_VALUE, contextReport, contextObservable);
		for (int index_observation = 0; index_observation < NB_QOC_METADATA; index_observation++) {
			contextObservation.listQocIndicator
					.add(TestCriterionZeroFactory.getInstance().newQoCIndicator("" + indexArrayValue, qocMetricValue));
			arrayValues1[indexArrayValue] = qocMetricValue++;
			contextObservation.listQocIndicator
					.add(TestCriterionOneFactory.getInstance().newQoCIndicator("" + indexArrayValue, qocMetricValue));
			arrayValues2[indexArrayValue++] = qocMetricValue++;
		}
		list_contextReport.add(contextReport);
		expectedContextReport = contextDataFacade.createContextReport("01 - 0");
		expectedContextEntity = createContextEntity(DEFAULT_ENTITY_NAME, DEFAULT_ENTITY_URI);
		expectedContextObservable = createContextObservable(DEFAULT_OBSERVABLE_NAME, DEFAULT_OBSERVABLE_URI,
				expectedContextEntity);
		expectedContextObservation = createContextObservation(DEFAULT_OBSERVATION_VALUE, expectedContextReport,
				expectedContextObservable);
		expectedContextObservation.listQocIndicator.add(TestCriterionZeroFactory.getInstance()
				.newQoCIndicator("" + indexArrayValue, mean.evaluate(arrayValues1)));
		expectedContextObservation.listQocIndicator.add(TestCriterionOneFactory.getInstance()
				.newQoCIndicator("" + indexArrayValue, mean.evaluate(arrayValues2)));
	}

	@Test
	public void ONEcontextReport_TENdifferentObservation_ZEROqocMetaData() {
		// - - - - - INIT VARIABLES - - - - -
		final int NB_OBSERVATION = 10;
		final ContextReport contextReport = contextDataFacade.createContextReport("01 - 0");
		final double[] arrayValues = new double[NB_OBSERVATION];
		Integer indexArrayValue = 0;
		Double contextObservationValue = 1.0;
		final Mean mean = new Mean();
		ContextEntity contextEntity, expectedContextEntity;
		ContextObservable contextObservable1, expectedContextObservable1, contextObservable2,
				expectedContextObservable2;
		// - - - - - CORE OF THE METHOD - - - - -
		list_contextReport.add(contextReport);
		contextEntity = createContextEntity(DEFAULT_ENTITY_NAME, DEFAULT_ENTITY_URI);
		contextObservable1 = createContextObservable(DEFAULT_OBSERVABLE_NAME, DEFAULT_OBSERVABLE_URI, contextEntity);
		for (int index_observation = 0; index_observation < NB_OBSERVATION; index_observation++) {
			createContextObservation(contextObservationValue, contextReport, contextObservable1);
			arrayValues[indexArrayValue++] = contextObservationValue++;
		}
		expectedContextReport = contextDataFacade.createContextReport("01 - 0");
		expectedContextEntity = createContextEntity(DEFAULT_ENTITY_NAME, DEFAULT_ENTITY_URI);
		expectedContextObservable1 = createContextObservable(DEFAULT_OBSERVABLE_NAME, DEFAULT_OBSERVABLE_URI,
				expectedContextEntity);
		createContextObservation(mean.evaluate(arrayValues), expectedContextReport, expectedContextObservable1);
		indexArrayValue = 0;
		contextObservable2 = createContextObservable(DEFAULT_OBSERVABLE_NAME, DEFAULT_SECOND_OBSERVABLE_URI,
				contextEntity);
		for (int index_observation = 0; index_observation < NB_OBSERVATION; index_observation++) {
			createContextObservation(contextObservationValue, contextReport, contextObservable2);
			arrayValues[indexArrayValue++] = contextObservationValue++;
		}
		expectedContextObservable2 = createContextObservable(DEFAULT_OBSERVABLE_NAME, DEFAULT_SECOND_OBSERVABLE_URI,
				expectedContextEntity);
		createContextObservation(mean.evaluate(arrayValues), expectedContextReport, expectedContextObservable2);
	}

	@Test
	public void TWOcontextReport_TENcomparableObservation_ZEROqocMetaData() {
		// - - - - - INIT VARIABLES - - - - -
		final int NB_OBSERVATION = 10;
		final ContextReport contextReport1 = contextDataFacade.createContextReport("01 - 0");
		final ContextReport contextReport2 = contextDataFacade.createContextReport("01 - 1");
		final double[] arrayValues = new double[NB_OBSERVATION * 2];
		Integer indexArrayValue = 0;
		Double contextObservationValue = 1.0;
		final Mean mean = new Mean();
		ContextEntity contextEntity1, contextEntity2, expectedContextEntity;
		ContextObservable contextObservable1, contextObservable2, expectedContextObservable;
		// - - - - - CORE OF THE METHOD - - - - -
		contextEntity1 = createContextEntity(DEFAULT_ENTITY_NAME, DEFAULT_ENTITY_URI);
		contextEntity2 = createContextEntity(DEFAULT_ENTITY_NAME, DEFAULT_ENTITY_URI);
		contextObservable1 = createContextObservable(DEFAULT_OBSERVABLE_NAME, DEFAULT_OBSERVABLE_URI, contextEntity1);
		contextObservable2 = createContextObservable(DEFAULT_OBSERVABLE_NAME, DEFAULT_OBSERVABLE_URI, contextEntity2);
		for (int index_observation = 0; index_observation < NB_OBSERVATION; index_observation++) {
			createContextObservation(contextObservationValue, contextReport1, contextObservable1);
			arrayValues[indexArrayValue++] = contextObservationValue++;
			createContextObservation(contextObservationValue, contextReport2, contextObservable2);
			arrayValues[indexArrayValue++] = contextObservationValue++;
		}
		list_contextReport.add(contextReport1);
		list_contextReport.add(contextReport2);
		expectedContextReport = contextDataFacade.createContextReport("01 - 3");
		expectedContextEntity = createContextEntity(DEFAULT_ENTITY_NAME, DEFAULT_ENTITY_URI);
		expectedContextObservable = createContextObservable(DEFAULT_OBSERVABLE_NAME, DEFAULT_OBSERVABLE_URI,
				expectedContextEntity);
		createContextObservation(mean.evaluate(arrayValues), expectedContextReport, expectedContextObservable);
	}

	@Test
	public void TWOcontextReport_ONEcomparableObservation_TENqocMetaData() {
		// - - - - - INIT VARIABLES - - - - -
		final int NB_OBSERVATION = 10;
		final ContextReport contextReport1 = contextDataFacade.createContextReport("01 - 0");
		final ContextReport contextReport2 = contextDataFacade.createContextReport("01 - 1");
		final double[] arrayValues = new double[NB_OBSERVATION * 2];
		Integer indexArrayValue = 0;
		Double qocMetricValue = 1.0;
		final Mean mean = new Mean();
		ContextEntity contextEntity1, contextEntity2, expectedContextEntity;
		ContextObservable contextObservable1, contextObservable2, expectedContextObservable;
		ContextObservation<?> contextObservation1, contextObservation2;
		final ContextObservation<?> expectedContextObservation;
		// - - - - - CORE OF THE METHOD - - - - -
		contextEntity1 = createContextEntity(DEFAULT_ENTITY_NAME, DEFAULT_ENTITY_URI);
		contextEntity2 = createContextEntity(DEFAULT_ENTITY_NAME, DEFAULT_ENTITY_URI);
		contextObservable1 = createContextObservable(DEFAULT_OBSERVABLE_NAME, DEFAULT_OBSERVABLE_URI, contextEntity1);
		contextObservable2 = createContextObservable(DEFAULT_OBSERVABLE_NAME, DEFAULT_OBSERVABLE_URI, contextEntity2);
		contextObservation1 = createContextObservation(DEFAULT_OBSERVATION_VALUE, contextReport1, contextObservable1);
		contextObservation2 = createContextObservation(DEFAULT_OBSERVATION_VALUE, contextReport2, contextObservable2);
		for (int index_observation = 0; index_observation < NB_OBSERVATION; index_observation++) {
			contextObservation1.listQocIndicator
					.add(TestCriterionZeroFactory.getInstance().newQoCIndicator("" + indexArrayValue, qocMetricValue));
			arrayValues[indexArrayValue++] = qocMetricValue++;
			contextObservation2.listQocIndicator
					.add(TestCriterionZeroFactory.getInstance().newQoCIndicator("" + indexArrayValue, qocMetricValue));
			arrayValues[indexArrayValue++] = qocMetricValue++;
		}
		list_contextReport.add(contextReport1);
		list_contextReport.add(contextReport2);
		expectedContextReport = contextDataFacade.createContextReport("01 - 3");
		expectedContextEntity = createContextEntity(DEFAULT_ENTITY_NAME, DEFAULT_ENTITY_URI);
		expectedContextObservable = createContextObservable(DEFAULT_OBSERVABLE_NAME, DEFAULT_OBSERVABLE_URI,
				expectedContextEntity);
		expectedContextObservation = createContextObservation(mean.evaluate(arrayValues), expectedContextReport,
				expectedContextObservable);
		expectedContextObservation.listQocIndicator.add(TestCriterionZeroFactory.getInstance()
				.newQoCIndicator("" + indexArrayValue, mean.evaluate(arrayValues)));
	}

	@Test
	public void TWOcontextReport_TENdifferentObservation_ZEROqocMetaData() {
		// - - - - - INIT VARIABLES - - - - -
		final int NB_OBSERVATION = 10;
		final ContextReport contextReport1 = contextDataFacade.createContextReport("01 - 0");
		final ContextReport contextReport2 = contextDataFacade.createContextReport("01 - 1");
		final double[] arrayValues1 = new double[NB_OBSERVATION * 2];
		final double[] arrayValues2 = new double[NB_OBSERVATION * 2];
		Integer indexArrayValue1 = 0;
		Integer indexArrayValue2 = 0;
		Double contextObservationValue = 1.0;
		final Mean mean = new Mean();
		ContextEntity contextEntity1, contextEntity2, expectedContextEntity;
		final ContextObservable contextObservable1_1, contextObservable1_2, contextObservable2_1, contextObservable2_2;
		final ContextObservable expectedContextObservable1, expectedContextObservable2;
		// - - - - - CORE OF THE METHOD - - - - -
		contextEntity1 = createContextEntity(DEFAULT_ENTITY_NAME, DEFAULT_ENTITY_URI);
		contextEntity2 = createContextEntity(DEFAULT_ENTITY_NAME, DEFAULT_ENTITY_URI);
		contextObservable1_1 = createContextObservable(DEFAULT_OBSERVABLE_NAME, DEFAULT_OBSERVABLE_URI, contextEntity1);
		contextObservable1_2 = createContextObservable(DEFAULT_OBSERVABLE_NAME, DEFAULT_SECOND_OBSERVABLE_URI,
				contextEntity1);
		contextObservable2_1 = createContextObservable(DEFAULT_OBSERVABLE_NAME, DEFAULT_OBSERVABLE_URI, contextEntity2);
		contextObservable2_2 = createContextObservable(DEFAULT_OBSERVABLE_NAME, DEFAULT_SECOND_OBSERVABLE_URI,
				contextEntity2);
		for (int index_observation = 0; index_observation < NB_OBSERVATION; index_observation++) {
			createContextObservation(contextObservationValue, contextReport1, contextObservable1_1);
			arrayValues1[indexArrayValue1] = contextObservationValue++;
			createContextObservation(contextObservationValue, contextReport1, contextObservable1_2);
			arrayValues1[indexArrayValue1++] = contextObservationValue++;
			createContextObservation(contextObservationValue, contextReport2, contextObservable2_1);
			arrayValues2[indexArrayValue2] = contextObservationValue++;
			createContextObservation(contextObservationValue, contextReport2, contextObservable2_2);
			arrayValues2[indexArrayValue2++] = contextObservationValue++;
		}
		list_contextReport.add(contextReport1);
		list_contextReport.add(contextReport2);
		expectedContextReport = contextDataFacade.createContextReport("01 - 3");
		expectedContextEntity = createContextEntity(DEFAULT_ENTITY_NAME, DEFAULT_ENTITY_URI);
		expectedContextObservable1 = createContextObservable(DEFAULT_OBSERVABLE_NAME, DEFAULT_OBSERVABLE_URI,
				expectedContextEntity);
		createContextObservation(mean.evaluate(arrayValues1), expectedContextReport, expectedContextObservable1);
		expectedContextObservable2 = createContextObservable(DEFAULT_OBSERVABLE_NAME, DEFAULT_SECOND_OBSERVABLE_URI,
				expectedContextEntity);
		createContextObservation(mean.evaluate(arrayValues2), expectedContextReport, expectedContextObservable2);
	}

	// # # # # # PRIVATE METHODS # # # # #

	private static ContextEntity createContextEntity(final String _contextEntityName, final String _contextEntityUri) {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		ContextEntity ret_contextEntity = null;
		// - - - - - CORE OF THE METHOD - - - - -
		try {
			ret_contextEntity = contextDataFacade.createContextEntity(_contextEntityName, new URI(_contextEntityUri));
		} catch (final URISyntaxException e) {
			e.printStackTrace();
			return null;
		}
		// - - - - - RETURN STATEMENT - - - - -
		return ret_contextEntity;
	}

	private static ContextObservable createContextObservable(final String _contextObservableName,
			final String _contextObservableUri, final ContextEntity _contextEntity) {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		ContextObservable ret_contextObservable = null;
		// - - - - - CORE OF THE METHOD - - - - -
		try {
			ret_contextObservable = contextDataFacade.createContextObservable(_contextObservableName,
					new URI(_contextObservableUri), _contextEntity);
		} catch (final URISyntaxException e) {
			e.printStackTrace();
			return null;
		}
		// - - - - - RETURN STATEMENT - - - - -
		return ret_contextObservable;
	}

	private static ContextObservation<?> createContextObservation(final Double _contextObservationValue,
			final ContextReport _contextReport, final ContextObservable _contextObservable) {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		final ContextObservation<?> observation = contextDataFacade.createContextObservation(
				"id - " + counter_contextObservationId++, _contextObservationValue, _contextObservable);
		// - - - - - CORE OF THE METHOD - - - - -
		contextDataFacade.addContextObservation(_contextReport, observation);
		contextDataFacade.addContextObservation(_contextObservable, observation);
		// - - - - - RETURN STATEMENT - - - - -
		return observation;
	}

}
