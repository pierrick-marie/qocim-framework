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

import qocim.datamodel.QoCIndicator;
import qocim.datamodel.QoCMetricDefinition;
import qocim.datamodel.utils.ConstraintChecker;
import qocim.datamodel.utils.ConstraintCheckerException;
import qocim.datamodel.utils.log.QoCIMLogger;
import qocim.tool.functions.IToolFunction;

/**
 * GetListPrimitiveQoCMetricDefinition returns the list of the primitive QoC
 * metric definitions of a composite QoC metric definition. A QoC metric
 * definition is considered as composite if the tool function
 * <i>isCompositeQoCMetricDefinition</i> returns <i>True</i>. The function
 * searches into a list of QoC indicators the QoC metric definition that must be
 * evaluated and then, gets its primitive QoC metric definitions.
 *
 * @author Pierrick MARIE
 */
public class GetListPrimitiveQoCMetricDefinition implements IToolFunction {

	// # # # # # PRIVATE VARIABLES # # # # #

	/**
	 * Verifies if the configuration of the function has been done.
	 */
	private Boolean setUpIsDone;
	/**
	 * The list of <b>QoCIndicator</b> where the function searches the
	 * <b>QoCMetricDefinition</b> that have to be evaluated.
	 */
	private final List<QoCIndicator> list_availableQoCIndicator;
	/**
	 * The <i>id</i> of the QoC metric definition that have to be evaluated.
	 */
	private String qoCMetricDefinitionId;
	/**
	 * The tool function used to verify whether the evaluated QoC metric
	 * definition is composite or not.
	 */
	private final IsCompositeQoCMetricDefinition isCompositeCriterion;

	// # # # # # CONSTRUCTORS # # # # #

	public GetListPrimitiveQoCMetricDefinition(final List<QoCIndicator> _list_availableQoCIndicator) {
		// - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
		try {
			String message = "GetListQoCPrimitiveMetricDefinition.constructor(List<QoCIndicator>): the argument _list_availableQoCIndicator is null";
			ConstraintChecker.notNull(_list_availableQoCIndicator, message);
			message = "GetListQoCPrimitiveMetricDefinition.constructor(List<QoCIndicator>): the argument _list_availableQoCIndicator is empty";
			ConstraintChecker.notEmpty(_list_availableQoCIndicator.toArray(), message);
		} catch (final ConstraintCheckerException _exception) {
			final String message = "GetListQoCPrimitiveMetricDefinition.constructor(List<QoCIndicator>): Fatal error.";
			QoCIMLogger.logger.log(Level.SEVERE, message, _exception);
			System.exit(-1);
		}
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		setUpIsDone = false;
		list_availableQoCIndicator = _list_availableQoCIndicator;
		isCompositeCriterion = new IsCompositeQoCMetricDefinition(list_availableQoCIndicator);
	}

	// # # # # # PUBLIC METHODS # # # # #

	/**
	 * The method executes the function
	 * <i>getListPrimiiveQoCMetricDefinition</i>. In a first time, the method
	 * creates the list of all available QoC metric definition from the private
	 * field <i>list_availableQoCIndicator</i>. In a second time, the method
	 * search from the list of QoC metric definition the appropriate QoC metric
	 * definition. Finally, the method gets and returns the list of primitive
	 * QoC metric definitions.
	 *
	 * @return The list of the primitive QoCMetricDefinition referenced by
	 *         QoCMetricDefinition identified by <i>qoCMetricDefinitionId</i>.
	 */
	@Override
	public Object exec() {
		// - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
		try {
			final String message = "GetListPrimitiveQoCMetricDefinition.exec() method setup(String) have to be called before.";
			ConstraintChecker.assertTrue(setUpIsDone, message);
		} catch (final ConstraintCheckerException e) {
			return new Object();
		}
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		/*
		 * The value returned by the method, the list of the primitive QoC
		 * metric definition.
		 */
		final List<QoCMetricDefinition> ret_list_primitiveQoCMetricDefinition = new ArrayList<QoCMetricDefinition>();
		/*
		 * The list of all available QoC metric definition builded with the
		 * private field <i>list_availableQoCIndicator</i>.
		 */
		final List<QoCMetricDefinition> list_availableQoCMetricDefinition = new ArrayList<QoCMetricDefinition>();
		// - - - - - CORE OF THE METHOD - - - - -
		for (final QoCIndicator loop_qoCIndicator : list_availableQoCIndicator) {
			list_availableQoCMetricDefinition.addAll(loop_qoCIndicator.qoCCriterion().list_qoCMetricDefinition);
		}
		for (final QoCMetricDefinition loop_qoCMetricDefinition : list_availableQoCMetricDefinition) {
			if (loop_qoCMetricDefinition.id().equals(qoCMetricDefinitionId)) {
				ret_list_primitiveQoCMetricDefinition
						.addAll(loop_qoCMetricDefinition.list_primitiveQoCMetricDefinition);
			}
		}
		// - - - - - RETURN STATEMENT - - - - -
		return ret_list_primitiveQoCMetricDefinition;
	}

	/**
	 * The method initializes the argument of the function
	 * <i>getListPrimitiveQoCMetricDefinition</i>.
	 *
	 * @param _qoCMetricDefinitionId
	 *            The <i>id</i> of the QoC metric definition used to get the
	 *            list of the primitive QoC metric definition.
	 * @return <b>this</b>
	 */
	public GetListPrimitiveQoCMetricDefinition setUp(final String _qoCMetricDefinitionId) {
		// - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
		try {
			final String message = "GetListQoCPrimitiveMetricDefinition.setup(String): the argument _qoCMetricDefinitionId is null";
			ConstraintChecker.notNull(_qoCMetricDefinitionId, message);
		} catch (final ConstraintCheckerException e) {
			setUpIsDone = false;
			return this;
		}
		isCompositeCriterion.setUp(_qoCMetricDefinitionId);
		if (!(Boolean) isCompositeCriterion.exec()) {
			final String message = "GetListQoCPrimitiveMetricDefinition.setUp(String): the definition identified with the argument _qoCMetricDefinitionId is a primitive definition";
			QoCIMLogger.logger.log(Level.SEVERE, message);
			setUpIsDone = false;
			return this;
		}
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		qoCMetricDefinitionId = _qoCMetricDefinitionId;
		setUpIsDone = true;
		// - - - - - RETURN STATEMENT - - - - -
		return this;
	}
}
