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

import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;

import qocim.datamodel.QoCCriterion;
import qocim.datamodel.QoCIndicator;
import qocim.datamodel.QoCMetricDefinition;
import qocim.datamodel.utils.ConstraintChecker;
import qocim.datamodel.utils.ConstraintCheckerException;
import qocim.datamodel.utils.QoCIMLogger;
import qocim.tool.functions.IToolFunction;

/**
 * IsCompositeQoCCriterion verifies if a QoC criterion is a composite QoC
 * criterion or not. A QoC criterion is composite if all its associated QoC
 * metric definition are composite definition. In other words, a QoC criterion
 * is composite if the tool function <i>isCompositeQoCMetricDefinition</i>
 * returns <i>True</i> for all its associated QoC metric definition. The
 * function searches into a list of QoC indicator all the QoC metric definition
 * associated to the QoC criterion that have to be evaluated and uses for all of
 * them the tool function <i>isCompositeQoCMetricDefinition</i>.
 *
 * @see mucontext.datamodel.qocim.QoCMetricDefinition
 * @see mucontext.datamodel.qocim.QoCIndicator
 * @see mucontext.datamodel.qocim.QoCCriterion
 * @see qocim.tool.functions.impl.IsCompositeQoCCriterion
 *
 * @author Pierrick MARIE
 */
public class IsCompositeQoCCriterion implements IToolFunction {

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
	private String qoCCriterionId;
	/**
	 * The tool function used to verify whether a QoC metric definition is
	 * composite or not.
	 */
	private final IsCompositeQoCMetricDefinition isCompositeQoCMetricDefinition;

	// # # # # # CONSTRUCTORS # # # # #

	public IsCompositeQoCCriterion(final List<QoCIndicator> _list_availableQoCIndicator) {
		// - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
		try {
			String message = "IsCompositeCriterion.constructor(List<QoCIndicator>): the argument _list_availableQoCIndicator is null";
			ConstraintChecker.notNull(_list_availableQoCIndicator, message);
			message = "IsCompositeCriterion.constructor(List<QoCIndicator>): the argument _list_availableQoCIndicator is empty";
			ConstraintChecker.notEmpty(_list_availableQoCIndicator.toArray(), message);
		} catch (final ConstraintCheckerException _exception) {
			QoCIMLogger.logger.log(Level.SEVERE, "IsCompositeCriterion.constructor(List<QoCIndicator>): Fatal error.",
					_exception);
			System.exit(-1);
		}
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		setUpIsDone = false;
		list_availableQoCIndicator = _list_availableQoCIndicator;
		isCompositeQoCMetricDefinition = new IsCompositeQoCMetricDefinition(_list_availableQoCIndicator);
	}

	// # # # # # PUBLIC METHODS # # # # #

	/**
	 * The method executes the function <i>isCompositeQoCCriterion</i>. The
	 * method searches from the private filed <i>list_availableQoCIndicator</i>
	 * the appropriate QoC criterion and then, verifies if the criterion is
	 * composite.
	 *
	 * @return <True> if <i>_qoCCriterion</i> is a composite criterion, else
	 *         <i>False</i>
	 */
	@Override
	public Object exec() {
		// - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
		try {
			final String message = "IsCompositeQoCCriterion.exec() method setup(String) have to be called before.";
			ConstraintChecker.assertTrue(setUpIsDone, message);
		} catch (final ConstraintCheckerException e) {
			return new Object();
		}
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		final QoCCriterion qoCCriterion = searchQoCCriterion();
		// - - - - - CORE OF THE METHOD - - - - -
		if (qoCCriterion == null) {
			return false;
		} else {
			return isCompositeQoCCriterion(qoCCriterion);
		}
	}

	/**
	 * The method initializes the arguments of the function
	 * <i>isCompositeQoCCriterion</i>.
	 *
	 * @param _qoCCriterionId
	 *            The <i>id</i> of the QoC criterion analyzed by the function.
	 * @return <b>this</b>
	 */
	public IsCompositeQoCCriterion setUp(final String _qoCCriterionId) {
		// - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
		try {
			final String message = "IsCompositeCriterion.setup(String): the argument _qoCCriterionId is null";
			ConstraintChecker.notNull(_qoCCriterionId, message);
		} catch (final ConstraintCheckerException e) {
			setUpIsDone = false;
			return this;
		}
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		qoCCriterionId = _qoCCriterionId;
		setUpIsDone = true;
		// - - - - - RETURN STATEMENT - - - - -
		return this;
	}

	// # # # # # PRIVATE METHODS # # # # #

	/**
	 * The method verifies if all QoC metric definition associated to the
	 * argument <i>_qoCCriterion</i> are composite QoC metric definition.
	 *
	 * @param _qoCCriterion
	 *            The QoC criterion analyzed by the method.
	 * @return <True> if <i>_qoCCriterion</i> is a composite criterion, else
	 *         <i>False</i>.
	 */
	private Boolean isCompositeQoCCriterion(final QoCCriterion _qoCCriterion) {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		/*
		 * The value returned by the method.
		 */
		Boolean ret_isCompositeQoCCriterion = true;
		/*
		 * A QoC metric definition used to analyze all the definition associated
		 * to <i>_qoCCriterion</i>.
		 */
		QoCMetricDefinition qoCMetricDefinition;
		/*
		 * An iterator of <b>QoCMetricDefinition</b> to iterate on the QoC
		 * metric definition of <i>_qoCCriterio.n</i>.
		 */
		final Iterator<QoCMetricDefinition> iterator_qoCMetricDefinition = _qoCCriterion.list_qoCMetricDefinition
				.iterator();
		// - - - - - CORE OF THE METHOD - - - - -
		while (iterator_qoCMetricDefinition.hasNext() && ret_isCompositeQoCCriterion) {
			qoCMetricDefinition = iterator_qoCMetricDefinition.next();
			isCompositeQoCMetricDefinition.setUp(qoCMetricDefinition.id());
			ret_isCompositeQoCCriterion = (Boolean) isCompositeQoCMetricDefinition.exec();
		}
		// - - - - - RETURN STATEMENT - - - - -
		return ret_isCompositeQoCCriterion;
	}

	/**
	 * The method searches into the private filed
	 * <i>list_availableQoCIndicator</i> the QoC criterion that have to be
	 * analyzed by the function. The criterion is identified with the private
	 * field <i>qoCCriterionId</i>. If no criterion is found, the method returns
	 * null.
	 *
	 * @return The QoCriterion concerned by the function.
	 */
	private QoCCriterion searchQoCCriterion() {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		/*
		 * The value returned by the method.
		 */
		QoCCriterion ret_qoCCriterion = null;
		/*
		 * An iterator of QoC indicator to iterate on the private filed
		 * <i>list_availableQoCIndicator</i>.
		 */
		final Iterator<QoCIndicator> iterator_qoCIndicator = list_availableQoCIndicator.iterator();
		/*
		 * This attribute is used to indicate if the expected QoC criterion is
		 * found or not.
		 */
		Boolean foundQoCCriterion = false;
		// - - - - - CORE OF THE METHOD - - - - -
		while (iterator_qoCIndicator.hasNext() && !foundQoCCriterion) {
			ret_qoCCriterion = iterator_qoCIndicator.next().qoCCriterion();
			if (ret_qoCCriterion.id().equals(qoCCriterionId)) {
				foundQoCCriterion = true;
			}
		}
		if (!foundQoCCriterion) {
			ret_qoCCriterion = null;
		}
		// - - - - - RETURN STATEMENT - - - - -
		return ret_qoCCriterion;
	}
}
