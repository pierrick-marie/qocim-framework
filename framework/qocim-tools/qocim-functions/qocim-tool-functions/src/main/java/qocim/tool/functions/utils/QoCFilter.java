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
package qocim.tool.functions.utils;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.logging.Level;

import qocim.datamodel.utils.ConstraintChecker;
import qocim.datamodel.utils.ConstraintCheckerException;
import qocim.datamodel.utils.QoCIMLogger;

/**
 * QoCFilter represents a filter of QoC meta-data. The filter express
 * constraints on different values of QoC meta-data. The filter is then
 * evaluated by the function matchingQoCFilter on a QoCMetaData.
 *
 * @see qocim.tool.functions.utils.EBinanryOperator
 * @see qocim.tool.functions.utils.QoCFilterExpression
 * @see qocim.tool.functions.impl.MatchingQoCFilter
 * @see mucontext.datamodel.qocim.QoCMetaData
 *
 * @author Pierrick MARIE
 */
public class QoCFilter {

	// # # # # # PRIVATE VARIABLES # # # # #

	/**
	 * The operator used to link different constraints. The possible values of
	 * the operator are <i>AND</i>, <i>OR</b> and <i>UNARY_OPERATOR</i>. The
	 * last value compels the filter to have only one constraint.
	 */
	private final EBinaryOperator operator;
	/**
	 * The list of the constraints of the filter. If the value of the field
	 * <b>operator</b> is <i>UNARY_OPERATOR</i>, the size of the list must be 1.
	 */
	private final HashSet<QoCFilterExpression> list_qoCFilterExpression;

	// # # # # # CONSTRUCTORS # # # # #

	public QoCFilter(final EBinaryOperator _operator) {
		// - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
		try {
			ConstraintChecker.notNull(_operator, "QoCFilter.constructor(EOperator): the argument _operator is null");
		} catch (final ConstraintCheckerException _exception) {
			QoCIMLogger.logger.log(Level.SEVERE, "QoCFilter.constructor(EOperator): Fatal error.", _exception);
			System.exit(-1);
		}
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		list_qoCFilterExpression = new LinkedHashSet<QoCFilterExpression>();
		operator = _operator;
	}

	// # # # # # PUBLIC METHODS # # # # #

	/**
	 * This method add a new expression that will be handled by the filter.
	 *
	 * @param _qoCFilterExpression
	 *            The new expression.
	 * @return <b>this</b>
	 */
	public QoCFilter addQocFilterExpression(final QoCFilterExpression _qoCFilterExpression) {
		// - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
		try {
			final String message = "QoCFilter.addQocFilterExpressions(QoCFilterExpression): the argument _qoCFilterExpression is null";
			ConstraintChecker.notNull(_qoCFilterExpression, message);
		} catch (final ConstraintCheckerException e) {
			return this;
		}
		// - - - - - CORE OF THE METHOD - - - - -
		list_qoCFilterExpression.add(new QoCFilterExpression(_qoCFilterExpression));
		// - - - - - RETURN STATEMENT - - - - -
		return this;
	}

	/**
	 * This method remove all the expressions handled by the filter.
	 *
	 * @return <b>this</b>
	 */
	public QoCFilter clearListQoCFilterExpression() {
		// - - - - - CORE OF THE METHOD - - - - -
		list_qoCFilterExpression.clear();
		// - - - - - RETURN STATEMENT - - - - -
		return this;
	}

	/**
	 * @return The list of expressions handled by the filter.
	 */
	public HashSet<QoCFilterExpression> getListQocFilterExpression() {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		final HashSet<QoCFilterExpression> ret_list_qoCFilterExpression = new LinkedHashSet<QoCFilterExpression>();
		final Iterator<QoCFilterExpression> iterator_qoCFilterExpression = list_qoCFilterExpression.iterator();
		// - - - - - CORE OF THE METHOD - - - - -
		while (iterator_qoCFilterExpression.hasNext()) {
			ret_list_qoCFilterExpression.add(new QoCFilterExpression(iterator_qoCFilterExpression.next()));
		}
		// - - - - - RETURN STATEMENT - - - - -
		return ret_list_qoCFilterExpression;
	}

	/**
	 * @return The current operator of the filter.
	 */
	public EBinaryOperator getOperator() {
		// - - - - - RETURN STATEMENT - - - - -
		return operator;
	}
}
