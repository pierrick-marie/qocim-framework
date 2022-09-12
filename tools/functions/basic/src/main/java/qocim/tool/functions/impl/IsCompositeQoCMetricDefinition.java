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
package qocim.tool.functions.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import qocim.datamodel.QoCIMFacade;
import qocim.datamodel.QoCIndicator;
import qocim.datamodel.QoCMetricDefinition;
import qocim.datamodel.utils.ConstraintChecker;
import qocim.datamodel.utils.ConstraintCheckerException;
import qocim.datamodel.utils.QoCIMLogger;
import qocim.tool.functions.IToolFunction;

/**
 * IsCompositeQoCMetricDefinition verifies if a QoC metric definition is a
 * composite definition. A QoC metric definition is composite if its field
 * <i>list_primitiveQoCMetricDefinition</i> is not empty. The function searches
 * into a list of QoC indicators the QoC metric definition that must be compared
 * and then, verifies if is it a composite QoC metric definition.
 *
 * @see mucontext.datamodel.qocim.QoCMetricDefinition
 * @see mucontext.datamodel.qocim.QoCIndicator
 *
 * @author Pierrick MARIE
 */
public class IsCompositeQoCMetricDefinition implements IToolFunction {

	// # # # # # PRIVATE VARIABLES # # # # #

	/**
	 * Verifies if the configuration of the function has been done.
	 */
	private Boolean setUpIsDone;
	/**
	 * The list of <b>QoCIndicator</b> where the function searches the QoC
	 * metric definition that have to be evaluated.
	 */
	private final List<QoCIndicator> list_availableQoCIndicator;
	/**
	 * The <i>id</i> of the QoC metric definition that have to be compared.
	 */
	private String qoCMetricDefinitionId;

	// # # # # # CONSTRUCTORS # # # # #

	public IsCompositeQoCMetricDefinition(final List<QoCIndicator> _list_availableQoCIndicator) {
		// - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
		try {
			String message = "IsCompositeCriterion.constructor(List<QoCIndicator>): the argument _list_availableQoCIndicator is null";
			ConstraintChecker.notNull(_list_availableQoCIndicator, message);
			message = "IsCompositeCriterion.constructor(List<QoCIndicator>): the argument _list_availableQoCIndicator is empty";
			ConstraintChecker.notEmpty(_list_availableQoCIndicator.toArray(), message);
		} catch (final ConstraintCheckerException _exception) {
			final String message = "IsCompositeCriterion.constructor(List<QoCIndicator>): Fatal error.";
			QoCIMLogger.logger.log(Level.SEVERE, message, _exception);
			System.exit(-1);
		}
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		setUpIsDone = false;
		list_availableQoCIndicator = _list_availableQoCIndicator;
	}

	// # # # # # PUBLIC METHODS # # # # #

	/**
	 * The method executes the function <i>isCompositeQoCMetricDefinition</i>.
	 * It creates a list of all available QoC metric definition and then,
	 * searches and verifies if the expected QoC metric definition is primitive.
	 *
	 * @return <i>True</i> if the analyzed QoC metric definition is a composite
	 *         QoC metric definition, else <i>False</i>.
	 */
	@Override
	public Object exec() {
		// - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
		try {
			final String message = "IsCompositeCriterion.exec() method setup(String) have to be called before.";
			ConstraintChecker.assertTrue(setUpIsDone, message);
		} catch (final ConstraintCheckerException e) {
			return new Object();
		}
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		/*
		 * The list of all available <b>QoCMetricDefinition</b> initialized with
		 * the private field <i>list_availableQoCIndicator</i>.
		 */
		final List<QoCMetricDefinition> list_availableQoCMetricDefinition = new ArrayList<QoCMetricDefinition>();
		/*
		 * The QoC metric definition analyzed by the function.
		 */
		QoCMetricDefinition qoCMetricDefinition;
		// - - - - - CORE OF THE METHOD - - - - -
		for (final QoCIndicator qoCIndicator : list_availableQoCIndicator) {
			list_availableQoCMetricDefinition.addAll(qoCIndicator.qoCCriterion().list_qoCMetricDefinition);
		}
		qoCMetricDefinition = QoCIMFacade.searchFirstQoCMetricDefinition(list_availableQoCMetricDefinition,
				qoCMetricDefinitionId);
		// - - - - - RETURN STATEMENT - - - - -
		return isCompositeQoCMetricDefinition(qoCMetricDefinition);
	}

	/**
	 * The method initializes the arguments of the function
	 * <i>isCompositeQoCMetricDefinition</i>.
	 *
	 * @param _qoCMetricDefinitionId
	 *            the <i>id</i> of QoC metric definition.
	 * @return <b>this</b>
	 */
	public IsCompositeQoCMetricDefinition setUp(final String _qoCMetricDefinitionId) {
		// - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
		try {
			final String message = "IsCompositeCriterion.setup(String): the argument _qoCMetricDefinitionId is null";
			ConstraintChecker.notNull(_qoCMetricDefinitionId, message);
		} catch (final ConstraintCheckerException e) {
			setUpIsDone = false;
			return this;
		}
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		qoCMetricDefinitionId = _qoCMetricDefinitionId;
		setUpIsDone = true;
		// - - - - - RETURN STATEMENT - - - - -
		return this;
	}

	// # # # # # PRIVATE METHODS # # # # #

	/**
	 * The method verifies if the list of primitive QoC metric definition of the
	 * argument <i>_qoCMetricDefinition</i> is empty.
	 *
	 * @param _qoCMetricDefinition
	 *            the analyzed QoC metric definition.
	 * @return <True> if the analyzed QoCMetricDefinition is a composite QoC
	 *         metric definition.
	 */
	private Boolean isCompositeQoCMetricDefinition(final QoCMetricDefinition _qoCMetricDefinition) {
		// - - - - - RETURN STATEMENT - - - - -
		return !_qoCMetricDefinition.list_primitiveQoCMetricDefinition.isEmpty();
	}
}
