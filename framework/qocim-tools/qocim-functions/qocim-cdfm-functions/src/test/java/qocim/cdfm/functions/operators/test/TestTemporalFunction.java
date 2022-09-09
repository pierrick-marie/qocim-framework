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
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import mucontext.datamodel.context.ContextDataModelFacade;
import mucontext.datamodel.context.ContextEntity;
import mucontext.datamodel.context.ContextObservable;
import mucontext.datamodel.context.ContextObservation;
import mucontext.datamodel.context.ContextReport;
import qocim.cdfm.function.ICDFMOperator;
import qocim.cdfm.function.impl.CDFMFunction;
import qocim.cdfm.operator.aggregation.SelectionAggregationOperator;
import qocim.cdfm.operator.aggregation.impl.MaxSelectionAggregator;
import qocim.datamodel.utils.QoCIMLogger;
import qocim.functions.IQoCIMFunctionListener;

public class TestTemporalFunction {

	// # # # # # CONSTANTS # # # # #

	private static final String DEFAULT_ENTITY_URI = "myuri://localhost";
	private static final String DEFAULT_ENTITY_NAME = "entity_name";
	private static final String DEFAULT_OBSERVABLE_URI = "sensor";
	private static final String DEFAULT_OBSERVABLE_NAME = "observable_name";
	private static final int DEFAULT_TIME_TO_WAIT = 1000;
	private static final int DEFAULT_TOTAL_TIME_TO_WAIT = 13000;

	// # # # # # PRIVATE VARIABLES # # # # #

	private static Integer counter_contextObservationId;
	private ContextReport expectedContextReport;
	private static ContextDataModelFacade contextDataFacade;
	private static CDFMFunction function;
	private static IQoCIMFunctionListener resultListener;
	private static ICDFMOperator maxSelectionOperator;
	private static Map<String, String> map_operatorParameters;

	// # # # # # PUBLIC METHODS # # # # #

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		QoCIMLogger.logger.setLevel(Level.OFF);
		contextDataFacade = new ContextDataModelFacade("facade");
		counter_contextObservationId = 0;
		maxSelectionOperator = new MaxSelectionAggregator();
		map_operatorParameters = new HashMap<String, String>();
		map_operatorParameters.put(SelectionAggregationOperator.PARAM_CONTEXT_OBSERVABLE_URI_SELECTION,
				DEFAULT_OBSERVABLE_URI);
		maxSelectionOperator.setParameters(map_operatorParameters);
	}

	@Before
	public void setUp() throws Exception {
		// - - - - - INIT VARIABLES - - - - -
		function = new CDFMFunction();
		ContextEntity expectedContextEntity;
		ContextObservable expectedContextObservable;
		expectedContextReport = contextDataFacade.createContextReport("01 - 60");
		expectedContextEntity = createContextEntity(DEFAULT_ENTITY_NAME, DEFAULT_ENTITY_URI);
		expectedContextObservable = createContextObservable(DEFAULT_OBSERVABLE_NAME, DEFAULT_OBSERVABLE_URI,
				expectedContextEntity);
		createContextObservation(10.0, expectedContextReport, expectedContextObservable);
		resultListener = new ResultListenerTest(expectedContextReport);
		function.setResultListener(resultListener);
		function.setOperator(maxSelectionOperator);
		function.setNbHandledContextReport(Integer.MAX_VALUE);
		function.setTimeToWait(DEFAULT_TOTAL_TIME_TO_WAIT);
	}

	@After
	public void execTest() {
		try {
			Thread.sleep(DEFAULT_TIME_TO_WAIT * 5);
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Ignore
	@Test
	public void testConfigig_ONEobservable_TENobservations() {
		// - - - - - INIT VARIABLES - - - - -
		ContextEntity contextEntity;
		ContextObservable contextObservable;
		final int NB_OBSERVATION = 10;
		ContextReport contextReport;
		final Double contextObservationValue = 1.0;
		// - - - - - CORE OF THE METHOD - - - - -
		for (int index_observation = 0; index_observation < NB_OBSERVATION; index_observation++) {
			contextReport = contextDataFacade.createContextReport("01 - " + index_observation);
			contextEntity = createContextEntity(DEFAULT_ENTITY_NAME, DEFAULT_ENTITY_URI);
			contextObservable = createContextObservable(DEFAULT_OBSERVABLE_NAME, DEFAULT_OBSERVABLE_URI, contextEntity);
			createContextObservation(contextObservationValue, contextReport, contextObservable);
			function.addContextReport(contextReport);
			try {
				Thread.sleep(DEFAULT_TIME_TO_WAIT);
			} catch (final InterruptedException e) {
				e.printStackTrace();
			}
		}
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
