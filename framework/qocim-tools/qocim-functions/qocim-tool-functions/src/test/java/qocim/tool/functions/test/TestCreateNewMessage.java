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

import java.util.Date;

import mucontext.datamodel.context.ContextReport;
import qocim.tool.functions.impl.CreateNewMessage;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestCreateNewMessage {

    // # # # # # CONSTANTS # # # # #

    private final static String TEST_CONTEXTENTITYNAME = "test_contextEntityName";
    private final static String TEST_CONTEXTENTITYURI = "test_contextEntityUri";
    private final static String TEST_CONTEXTOBSERVABLENAME = "test_contextObservableName";
    private final static String TEST_CONTEXTOBSERVABLEURI = "test_contextObservableUri";
    private final static String TEST_CONTEXTOBSERVATIONID = "test_contextObservationId";
    private final static String TEST_CONTEXTOBSERVATIONVALUE = "test_contextObservationValue";
    private final static String TEST_CONTEXTOBSERVATIONUNIT = "test_contextObservationUnit";

    // # # # # # PRIVATE VARIABLES # # # # #

    private static CreateNewMessage createNewMessage;

    private String testedEntityName;
    private String testedEntityUri;
    private String testedObservableName;
    private String testedObservableUri;
    private String testedObservableId;
    private String testedObservationValue;
    private ContextReport contextReport;

    // # # # # # PUBLIC METHODS # # # # #

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
	// - - - - - INITIALIZE THE VARIABLES - - - - -
	createNewMessage = new CreateNewMessage();
    }

    @After
    public final void execTest() {
	// - - - - - CORE OF THE METHOD - - - - -
	assertEquals(TEST_CONTEXTENTITYNAME, testedEntityName);
	assertEquals(TEST_CONTEXTENTITYURI, testedEntityUri);
	assertEquals(TEST_CONTEXTOBSERVABLENAME, testedObservableName);
	assertEquals(TEST_CONTEXTOBSERVABLEURI, testedObservableUri);
	assertEquals(TEST_CONTEXTOBSERVATIONID, testedObservableId);
	assertEquals(TEST_CONTEXTOBSERVATIONVALUE, testedObservationValue);
    }

    @Test
    public void createNewMessage() {
	// - - - - - CORE OF THE METHOD - - - - -
	createNewMessage.setUp(TEST_CONTEXTENTITYNAME, TEST_CONTEXTENTITYURI, TEST_CONTEXTOBSERVABLENAME, TEST_CONTEXTOBSERVABLEURI, TEST_CONTEXTOBSERVATIONID,
		new Date().getTime(), TEST_CONTEXTOBSERVATIONVALUE, TEST_CONTEXTOBSERVATIONUNIT);
	contextReport = (ContextReport) createNewMessage.exec();
	testedEntityName = contextReport.observations.iterator().next().observable.entity.name;
	testedEntityUri = contextReport.observations.iterator().next().observable.entity.uri.toString();
	testedObservableName = contextReport.observations.iterator().next().observable.name;
	testedObservableUri = contextReport.observations.iterator().next().observable.uri.toString();
	testedObservableId = contextReport.observations.iterator().next().id;
	testedObservationValue = (String) contextReport.observations.iterator().next().value;
    }
}
