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
import qocim.tool.functions.impl.ObservationComparable;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestObservationComparable {

    // # # # # # CONSTANTS # # # # #

    private ContextObservation<?> contextObservation1;
    private ContextObservation<?> contextObservation2;
    private Boolean isObservationComparable;

    // # # # # # PRIVATE VARIABLES # # # # #

    private static ObservationComparable observationComparable;

    private static final String DEFAULT_OBSERVATIONNAME = "42";
    private static final String DEFAULT_URI = "myuri://localhost";
    private static final String DEFAULT_NAME = "name";
    private static final int DEFAULT_OBSERVATIONVALUE = 42;

    // # # # # # PUBLIC METHODS # # # # #

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
	// - - - - - INITIALIZE THE VARIABLES - - - - -
	observationComparable = new ObservationComparable();
    }

    @Before
    public void setUp() throws Exception {
	// - - - - - CORE OF THE METHOD - - - - -
	contextObservation1 = createObservation(DEFAULT_URI + "/entity", DEFAULT_URI + "/observable", DEFAULT_NAME + "_entity", DEFAULT_NAME + "_observable");
	contextObservation2 = createObservation(DEFAULT_URI + "/entity", DEFAULT_URI + "/observable", DEFAULT_NAME + "_entity", DEFAULT_NAME + "_observable");
    }

    @After
    public final void execTest() {
	// - - - - - CORE OF THE METHOD - - - - -
	observationComparable.setUp(contextObservation1, contextObservation2);
	assertEquals(isObservationComparable, observationComparable.exec());
    }

    @Test
    public final void compareComparableObservation() {
	// - - - - - CORE OF THE METHOD - - - - -
	isObservationComparable = true;
    }

    @Test
    public final void compareDifferentObservableName() {
	// - - - - - CORE OF THE METHOD - - - - -
	contextObservation1 = createObservation(DEFAULT_URI + "/entity", DEFAULT_URI + "/observable", DEFAULT_NAME + "_entity", DEFAULT_NAME + "_observable_01");
	isObservationComparable = false;
    }

    @Test
    public final void compareDifferentObservableUri() {
	// - - - - - CORE OF THE METHOD - - - - -
	contextObservation1 = createObservation(DEFAULT_URI + "/entity", DEFAULT_URI + "/observable/01", DEFAULT_NAME + "_entity", DEFAULT_NAME + "_observable");
	isObservationComparable = false;
    }

    @Test
    public final void compareDifferentObservationEntityName() {
	// - - - - - CORE OF THE METHOD - - - - -
	contextObservation1 = createObservation(DEFAULT_URI + "/entity", DEFAULT_URI + "/observable", DEFAULT_NAME + "_entity_01", DEFAULT_NAME + "_observable");
	isObservationComparable = false;
    }

    @Test
    public final void compareDifferentObservationUri() {
	// - - - - - CORE OF THE METHOD - - - - -
	contextObservation1 = createObservation(DEFAULT_URI + "/entity/01", DEFAULT_URI + "/observable", DEFAULT_NAME + "_entity", DEFAULT_NAME + "_observable");
	isObservationComparable = false;
    }

    @Test
    public final void compareSameObservation() {
	// - - - - - CORE OF THE METHOD - - - - -
	/**
	 * Comparing same objects
	 */
	contextObservation1 = contextObservation2;
	isObservationComparable = true;
    }

    // # # # # # PRIVATE METHODS # # # # #

    private static ContextObservation<?> createObservation(final String _entityUri, final String _observableUri, final String _entityName,
	    final String _observableName) {
	// - - - - - INITIALIZE THE VARIABLES - - - - -
	ContextEntity entity = null;
	ContextObservable observable = null;
	ContextObservation<?> observation = null;
	final ContextDataModelFacade facade = new ContextDataModelFacade("facade");
	// - - - - - CORE OF THE METHOD - - - - -
	try {
	    entity = facade.createContextEntity(_entityName, new URI(_entityUri));
	    observable = facade.createContextObservable(_observableName, new URI(_observableUri), entity);
	    observation = facade.createContextObservation(DEFAULT_OBSERVATIONNAME, DEFAULT_OBSERVATIONVALUE, observable);
	} catch (final URISyntaxException e) {
	    e.printStackTrace();
	}
	// - - - - - RETURN STATEMENT - - - - -
	return observation;
    }
}
