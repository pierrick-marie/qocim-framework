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
package qocim.tool.functions.test;

import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import mucontext.datamodel.context.ContextDataModelFacade;
import mucontext.datamodel.context.ContextEntity;
import mucontext.datamodel.context.ContextObservable;
import mucontext.datamodel.context.ContextObservation;
import mucontext.datamodel.context.ContextReport;
import qocim.tool.functions.impl.ContextReportComparable;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestContextReportComparable {

    // # # # # # CONSTANTS # # # # #

    private static final String DEFAULT_ENTITY_URI = "myuri://localhost";
    private static final String DEFAULT_ENTITY_NAME = "name";
    private final static String DEFAULT_OBSERVABLE_URI = "sensor";
    private static final String DEFAULT_OBSERVABLE_NAME = "42";
    private static final int DEFAULT_OBSERVATION_VALUE = 42;

    // # # # # # PRIVATE VARIABLES # # # # #

    private static Integer counter_contextReport;
    private static Integer counter_contextObservationId;
    private static List<ContextObservation<?>> list_contextObservation1;
    private static List<ContextObservation<?>> list_contextObservation2;
    private static ContextReport contextReport1;
    private static ContextReport contextReport2;
    private static ContextReportComparable contextReportComparable;
    private static ContextDataModelFacade facade;

    private Boolean contextReportAreComparable;

    // # # # # # PUBLIC METHODS # # # # #

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
	// - - - - - INITIALIZE THE VARIABLES - - - - -
	counter_contextReport = 0;
	counter_contextObservationId = 0;
	facade = new ContextDataModelFacade("facade");
	contextReportComparable = new ContextReportComparable();
	list_contextObservation1 = new ArrayList<ContextObservation<?>>();
	list_contextObservation2 = new ArrayList<ContextObservation<?>>();
    }

    @Before
    public void setUp() throws Exception {
	// - - - - - CORE OF THE METHOD - - - - -
	contextReport1 = facade.createContextReport("01 - " + counter_contextReport);
	counter_contextReport++;
	contextReport2 = facade.createContextReport("02 - " + counter_contextReport);
	counter_contextReport++;
	list_contextObservation1.clear();
	list_contextObservation2.clear();
    }

    @After
    public final void execTest() {
	// - - - - - CORE OF THE METHOD - - - - -
	contextReportComparable.setUp(contextReport1, contextReport2);
	assertEquals(contextReportAreComparable, contextReportComparable.exec());
    }

    @Test
    public void compareEmptyContextReport() {
	// - - - - - CORE OF THE METHOD - - - - -
	contextReportAreComparable = true;
    }

    @Test
    public void compareOneSameContextReport() {
	// - - - - - INITIALIZE THE VARIABLES - - - - -
	contextReportAreComparable = true;
	// - - - - - CORE OF THE METHOD - - - - -
	createObservation(DEFAULT_ENTITY_URI, DEFAULT_OBSERVABLE_URI, DEFAULT_ENTITY_NAME, DEFAULT_OBSERVABLE_NAME, DEFAULT_OBSERVATION_VALUE, contextReport1);
	createObservation(DEFAULT_ENTITY_URI, DEFAULT_OBSERVABLE_URI, DEFAULT_ENTITY_NAME, DEFAULT_OBSERVABLE_NAME, DEFAULT_OBSERVATION_VALUE, contextReport2);
    }

    @Test
    public void compareTwoSameContextReport() {
	// - - - - - INITIALIZE THE VARIABLES - - - - -
	contextReportAreComparable = true;
	// - - - - - CORE OF THE METHOD - - - - -
	createObservation(DEFAULT_ENTITY_URI, DEFAULT_OBSERVABLE_URI, DEFAULT_ENTITY_NAME, DEFAULT_OBSERVABLE_NAME, DEFAULT_OBSERVATION_VALUE, contextReport1);
	createObservation(DEFAULT_ENTITY_URI, DEFAULT_OBSERVABLE_URI, DEFAULT_ENTITY_NAME, DEFAULT_OBSERVABLE_NAME, DEFAULT_OBSERVATION_VALUE + 2,
		contextReport1);
	createObservation(DEFAULT_ENTITY_URI, DEFAULT_OBSERVABLE_URI, DEFAULT_ENTITY_NAME, DEFAULT_OBSERVABLE_NAME, DEFAULT_OBSERVATION_VALUE, contextReport2);
	createObservation(DEFAULT_ENTITY_URI, DEFAULT_OBSERVABLE_URI, DEFAULT_ENTITY_NAME, DEFAULT_OBSERVABLE_NAME, DEFAULT_OBSERVATION_VALUE + 2,
		contextReport2);
    }

    @Test
    public void compareOneDifferentContextReport() {
	// - - - - - INITIALIZE THE VARIABLES - - - - -
	contextReportAreComparable = false;
	// - - - - - CORE OF THE METHOD - - - - -
	createObservation(DEFAULT_ENTITY_URI + "/report-1", DEFAULT_OBSERVABLE_URI, DEFAULT_ENTITY_NAME, DEFAULT_OBSERVABLE_NAME, DEFAULT_OBSERVATION_VALUE,
		contextReport1);
	createObservation(DEFAULT_ENTITY_URI + "/report-2", DEFAULT_OBSERVABLE_URI, DEFAULT_ENTITY_NAME, DEFAULT_OBSERVABLE_NAME, DEFAULT_OBSERVATION_VALUE,
		contextReport2);
    }

    @Test
    public void compareDifferentNumberContextReport() {
	// - - - - - INITIALIZE THE VARIABLES - - - - -
	contextReportAreComparable = false;
	// - - - - - CORE OF THE METHOD - - - - -
	createObservation(DEFAULT_ENTITY_URI, DEFAULT_OBSERVABLE_URI, DEFAULT_ENTITY_NAME, DEFAULT_OBSERVABLE_NAME, DEFAULT_OBSERVATION_VALUE, contextReport1);
    }

    // # # # # # PRIVATE METHODS # # # # #

    private static void createObservation(final String _entityUri, final String _observableUri, final String _entityName, final String _observableName,
	    final Integer _observationValue, final ContextReport _contextReport) {
	// - - - - - CORE OF THE METHOD - - - - -
	try {
	    final ContextEntity entity = facade.createContextEntity(_entityName, new URI(_entityUri));
	    final ContextObservable observable = facade.createContextObservable(_observableName, new URI(_observableUri), entity);
	    final ContextObservation<?> observation = facade.createContextObservation("id - " + counter_contextObservationId++, _observationValue, observable);
	    facade.addContextObservation(_contextReport, observation);
	} catch (final URISyntaxException e) {
	    e.printStackTrace();
	}
    }
}
