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

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import mucontext.datamodel.context.ContextDataModelFacade;
import mucontext.datamodel.context.ContextEntity;
import mucontext.datamodel.context.ContextObservable;
import mucontext.datamodel.context.ContextObservation;
import qocim.datamodel.QoCIndicator;
import qocim.datamodel.test.criterion.one.TestCriterionOneFactory;
import qocim.datamodel.test.criterion.three.TestCriterionThreeFactory;
import qocim.datamodel.test.criterion.zero.TestCriterionZeroFactory;
import qocim.tool.functions.impl.MessageComparable;

public class TestMessageComparable {

	// # # # # # CONSTANTS # # # # #

	private static final String DEFAULT_OBSERVATIONNAME = "42";
	private static final String DEFAULT_URI = "myuri://localhost";
	private static final String DEFAULT_NAME = "name";
	private static final int DEFAULT_OBSERVATIONVALUE = 42;
	private static final double DEFAULT_QOCVALUE = 42.0;
	private static final String DEFAULT_QOCID = "14";

	// # # # # # PRIVATE VARIABLES # # # # #

	private static MessageComparable messageComparable;

	private ContextObservation<?> contextObservation1;
	private ContextObservation<?> contextObservation2;
	private Boolean isMessageComparable;

	// # # # # # PUBLIC METHODS # # # # #

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		messageComparable = new MessageComparable();
	}

	@Before
	public void setUp() throws Exception {
		// - - - - - CORE OF THE METHOD - - - - -
		contextObservation1 = createObservation(DEFAULT_URI + "/entity", DEFAULT_URI + "/observable",
				DEFAULT_NAME + "_entity", DEFAULT_NAME + "_observable");
		contextObservation2 = createObservation(DEFAULT_URI + "/entity", DEFAULT_URI + "/observable",
				DEFAULT_NAME + "_entity", DEFAULT_NAME + "_observable");
	}

	@After
	public final void execTest() {
		// - - - - - CORE OF THE METHOD - - - - -
		messageComparable.setUp(contextObservation1, contextObservation2);
		assertEquals(isMessageComparable, messageComparable.exec());
	}

	@Test
	public void compareDifferentListQoCIndicators() {
		// - - - - - CORE OF THE METHOD - - - - -
		contextObservation1.list_qoCIndicator
				.add(TestCriterionZeroFactory.getInstance().newQoCIndicator(DEFAULT_QOCID, DEFAULT_QOCVALUE));
		contextObservation1.list_qoCIndicator
				.add(TestCriterionOneFactory.getInstance().newQoCIndicator(DEFAULT_QOCID, DEFAULT_QOCVALUE));
		contextObservation1.list_qoCIndicator
				.add(TestCriterionThreeFactory.getInstance().newQoCIndicator(DEFAULT_QOCID, DEFAULT_QOCVALUE));
		contextObservation2.list_qoCIndicator
				.add(TestCriterionZeroFactory.getInstance().newQoCIndicator(DEFAULT_QOCID, DEFAULT_QOCVALUE));
		contextObservation2.list_qoCIndicator
				.add(TestCriterionOneFactory.getInstance().newQoCIndicator(DEFAULT_QOCID, DEFAULT_QOCVALUE));
		isMessageComparable = false;
	}

	@Test
	public void compareDifferentQoCIndicator() {
		// - - - - - CORE OF THE METHOD - - - - -
		contextObservation1.list_qoCIndicator
				.add(TestCriterionZeroFactory.getInstance().newQoCIndicator(DEFAULT_QOCID, DEFAULT_QOCVALUE));
		contextObservation2.list_qoCIndicator
				.add(TestCriterionOneFactory.getInstance().newQoCIndicator(DEFAULT_QOCID, DEFAULT_QOCVALUE));
		isMessageComparable = false;
	}

	@Test
	public final void compareDifferentQoCMetaData() {
		// - - - - - CORE OF THE METHOD - - - - -
		contextObservation1.list_qoCIndicator
				.add(TestCriterionZeroFactory.getInstance().newQoCIndicator(DEFAULT_QOCID, DEFAULT_QOCVALUE));
		contextObservation2.list_qoCIndicator
				.add(TestCriterionOneFactory.getInstance().newQoCIndicator(DEFAULT_QOCID, DEFAULT_QOCVALUE));
		isMessageComparable = false;
	}

	@Test
	public final void compareSameObject() {
		// - - - - - CORE OF THE METHOD - - - - -
		contextObservation1.list_qoCIndicator
				.add(TestCriterionZeroFactory.getInstance().newQoCIndicator(DEFAULT_QOCID, DEFAULT_QOCVALUE));
		contextObservation2.list_qoCIndicator
				.add(TestCriterionZeroFactory.getInstance().newQoCIndicator(DEFAULT_QOCID, DEFAULT_QOCVALUE));
		isMessageComparable = true;
	}

	@Test
	public final void compareWithComparableQoCIndicator() {
		// - - - - - CORE OF THE METHOD - - - - -
		/**
		 * Comparing two observations with the same QoCIndicator but different
		 * QoC value
		 */
		contextObservation1.list_qoCIndicator
				.add(TestCriterionZeroFactory.getInstance().newQoCIndicator(DEFAULT_QOCID, DEFAULT_QOCVALUE));
		contextObservation2.list_qoCIndicator
				.add(TestCriterionZeroFactory.getInstance().newQoCIndicator(DEFAULT_QOCID, DEFAULT_QOCVALUE + 1));
		isMessageComparable = true;
	}

	@Test
	public final void compareWithSameQoCIndicator() {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		final QoCIndicator qoCIndicator0 = TestCriterionZeroFactory.getInstance().newQoCIndicator(DEFAULT_QOCID,
				DEFAULT_QOCVALUE);
		final QoCIndicator qoCIndicator1 = TestCriterionOneFactory.getInstance().newQoCIndicator(DEFAULT_QOCID,
				DEFAULT_QOCVALUE + 1);
		// - - - - - CORE OF THE METHOD - - - - -
		/**
		 * Comparing two observations with the same QoCIndicators
		 */
		contextObservation1.list_qoCIndicator.add(qoCIndicator0);
		contextObservation1.list_qoCIndicator.add(qoCIndicator1);
		contextObservation2.list_qoCIndicator.add(qoCIndicator0);
		contextObservation2.list_qoCIndicator.add(qoCIndicator1);
		isMessageComparable = true;
	}

	// # # # # # PRIVATE METHODS # # # # #

	private static ContextObservation<?> createObservation(final String _entityUri, final String _observableUri,
			final String _entityName, final String _observableName) {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		ContextEntity entity = null;
		ContextObservable observable = null;
		ContextObservation<?> observation = null;
		final ContextDataModelFacade facade = new ContextDataModelFacade("facade");
		// - - - - - CORE OF THE METHOD - - - - -
		try {
			entity = facade.createContextEntity(_entityName, new URI(_entityUri));
			observable = facade.createContextObservable(_observableName, new URI(_observableUri), entity);
			observation = facade.createContextObservation(DEFAULT_OBSERVATIONNAME, DEFAULT_OBSERVATIONVALUE,
					observable);
		} catch (final URISyntaxException e) {
			e.printStackTrace();
		}
		// - - - - - RETURN STATEMENT - - - - -
		return observation;
	}
}
