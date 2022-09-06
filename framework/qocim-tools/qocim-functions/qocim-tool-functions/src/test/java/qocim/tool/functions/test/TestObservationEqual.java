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

import mucontext.datamodel.context.ContextDataModelFacade;
import mucontext.datamodel.context.ContextEntity;
import mucontext.datamodel.context.ContextObservable;
import mucontext.datamodel.context.ContextObservation;
import qocim.tool.functions.impl.ObservationEqual;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestObservationEqual {

    // # # # # # CONSTANTS # # # # #

    private static final String DEFAULT_OBSERVATIONNAME = "42";
    private static final String DEFAULT_URI = "myuri://localhost";
    private static final String DEFAULT_NAME = "name";
    private static final int DEFAULT_OBSERVATIONVALUE = 42;

    // # # # # # PRIVATE VARIABLES # # # # #

    private static ObservationEqual observationEqual;

    private ContextObservation<?> contextObservation1;
    private ContextObservation<?> contextObservation2;
    private Boolean isObservationEqual;

    // # # # # # PUBLIC METHODS # # # # #

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
	// - - - - - INITIALIZE THE VARIABLES - - - - -
	observationEqual = new ObservationEqual();
    }

    @Before
    public void setUp() throws Exception {
	// - - - - - CORE OF THE METHOD - - - - -
	contextObservation1 = createObservation(DEFAULT_OBSERVATIONVALUE);
	contextObservation2 = createObservation(DEFAULT_OBSERVATIONVALUE);
    }

    @After
    public final void execTest() {
	// - - - - - CORE OF THE METHOD - - - - -
	observationEqual.setUp(contextObservation1, contextObservation2);
	assertEquals(isObservationEqual, observationEqual.exec());
    }

    @Test
    public final void compareDifferentObservation() {
	// - - - - - CORE OF THE METHOD - - - - -
	contextObservation2 = createObservation(DEFAULT_OBSERVATIONVALUE + 42);
	isObservationEqual = false;
    }

    @Test
    public final void compareEqualObservation() {
	// - - - - - CORE OF THE METHOD - - - - -
	isObservationEqual = true;
    }

    @Test
    public final void compareSameObservation() {
	// - - - - - CORE OF THE METHOD - - - - -
	contextObservation1 = contextObservation2;
	isObservationEqual = true;
    }

    // # # # # # PRIVATE METHODS # # # # #

    private static ContextObservation<?> createObservation(final Integer _observableValue) {
	// - - - - - INITIALIZE THE VARIABLES - - - - -
	ContextEntity entity = null;
	ContextObservable observable = null;
	ContextObservation<?> observation = null;
	final ContextDataModelFacade facade = new ContextDataModelFacade("facade");
	// - - - - - CORE OF THE METHOD - - - - -// - - - - - CORE OF THE METHOD
	// - - - - -
	try {
	    entity = facade.createContextEntity(DEFAULT_NAME, new URI(DEFAULT_URI));
	    observable = facade.createContextObservable(DEFAULT_NAME, new URI(DEFAULT_URI), entity);
	    observation = facade.createContextObservation(DEFAULT_OBSERVATIONNAME, _observableValue, observable);
	} catch (final URISyntaxException e) {
	    e.printStackTrace();
	}
	// - - - - - RETURN STATEMENT - - - - -
	return observation;
    }
}
