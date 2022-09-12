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

import qocim.datamodel.QoCMetaData;
import qocim.datamodel.utils.ConstraintChecker;
import qocim.datamodel.utils.ConstraintCheckerException;
import qocim.tool.functions.IToolFunction;
import qocim.tool.functions.utils.EBinaryComparator;
import qocim.tool.functions.utils.EBinaryOperator;
import qocim.tool.functions.utils.QoCFilter;
import qocim.tool.functions.utils.QoCFilterExpression;

/**
 * MatchingQoCFilter verifies if the values of a QoC meta-data respects the
 * constraints express into a QoC filter. A QoC filter contains one or more
 * constraints. If one of them if not match by the values of the QoC meta-data,
 * the function returns <i>False</i>, else the function returns <i>True</i>.
 *
 * @see qocim.tool.functions.utils.QoCFilter
 *
 * @author Pierrick MARIE
 */
public class MatchingQoCFilter implements IToolFunction {

	// # # # # # PRIVATE VARIABLES # # # # #

	/**
	 * Verifies if the configuration of the function has been done.
	 */
	private Boolean setUpIsDone;
	/**
	 * The QoC meta-data that is compared.
	 */
	private QoCMetaData qoCMetaData;
	/**
	 * The QoC filter that contains the constraints that have to be check.
	 */
	private QoCFilter qoCFilter;

	// # # # # # CONSTRUCTORS # # # # #

	public MatchingQoCFilter() {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		setUpIsDone = false;
		qoCFilter = null;
		qoCMetaData = null;
	}

	// # # # # # PUBLIC METHODS # # # # #

	/**
	 * The method executes the function <i>matchQoCFilter</i>. To verify if QoC
	 * meta-data respects the constraints of the filter, the method calls the
	 * appropriate private method following the type of the operator of the
	 * filter.
	 *
	 * @return <i>True</i> if the values if <i>_qoCMetaData</i> respects all the
	 *         constraints of the QoC filter.
	 */
	@Override
	public Object exec() {
		// - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
		try {
			final String message = "MatchingQoCFilter.exec() method setup(QoCMetaData, QoCFilter) have to be called before.";
			ConstraintChecker.assertTrue(setUpIsDone, message);
		} catch (final ConstraintCheckerException e) {
			return new Object();
		}
		// - - - - - CORE OF THE METHOD - - - - -
		if (qoCFilter.getOperator().equals(EBinaryOperator.AND)) {
			return checkAndOperator(qoCFilter);
		} else {
			if (qoCFilter.getOperator().equals(EBinaryOperator.OR)) {
				return checkOrOperator(qoCFilter);
			} else if (qoCFilter.getOperator().equals(EBinaryOperator.UNARY_OPERATOR)) {
				try {
					final String message = "MatchingQoCFilter.exec() method setup(QoCMetaData, QoCFilter): wrong number of QocFilterExpressions, expected 1, real value: "
							+ qoCFilter.getListQocFilterExpression().size() + ".";
					ConstraintChecker.expectedSizeOfArray(qoCFilter.getListQocFilterExpression().toArray(), 1, message);
				} catch (final ConstraintCheckerException e) {
					return new Object();
				}
				return checkUnaryOperator(qoCFilter);
			}
		}
		// - - - - - RETURN STATEMENT - - - - -
		return false;
	}

	/**
	 * The method initializes the arguments of the function
	 * <i>matchQoCFilter</i>.
	 *
	 * @param _qoCMetaData
	 *            The QoC meta-data analyzed by the function.
	 * @param _qoCFilter
	 *            The filter used to verify the values of <i>_qoCMetaData</i>.
	 * @return <b>this</b>
	 */
	public IToolFunction setUp(final QoCMetaData _qoCMetaData, final QoCFilter _qoCFilter) {
		// - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
		try {
			String message = "MatchingQoCFilter.setup(QoCMetaData, QoCFilter): the argument _qocMetaData is null";
			ConstraintChecker.notNull(_qoCMetaData, message);
			message = "MatchingQoCFilter.setup(QoCMetaData, QoCFilter): the argument _qocFilter is null";
			ConstraintChecker.notNull(_qoCFilter, message);
		} catch (final ConstraintCheckerException e) {
			setUpIsDone = false;
			return this;
		}
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		qoCMetaData = _qoCMetaData;
		qoCFilter = _qoCFilter;
		setUpIsDone = true;
		// - - - - - RETURN STATEMENT - - - - -
		return this;
	}

	// # # # # # PRIVATE METHODS # # # # #

	/**
	 * The method compares the values of the QoC meta-data with many QoC filter
	 * expression linked with the <i>AND</i> operator and expressed into the QoC
	 * filer.
	 *
	 * @param _qoCFilter
	 *            The filter that contains the QoC filter expression.
	 * @return <i>True</i> if the values of the QoC meta-data respect the QoC
	 *         filter, else <i>False</i>.
	 */
	private Boolean checkAndOperator(final QoCFilter _qoCFilter) {
		// - - - - - CORE OF THE METHOD - - - - -
		for (final QoCFilterExpression qocFilterExpression : qoCFilter.getListQocFilterExpression()) {
			if (!matchQoCFilterExpression(qoCMetaData, qocFilterExpression)) {
				return false;
			}
		}
		// - - - - - RETURN STATEMENT - - - - -
		return true;
	}

	/**
	 * The method compares the values of the QoC meta-data with many QoC filter
	 * expression linked with the <i>OR</i> operator and expressed into the QoC
	 * filter.
	 *
	 * @param _qoCFilter
	 *            The filter that contains the QoC filter expression.
	 * @return <i>True</i> if the values of the QoC meta-data respect the QoC
	 *         filter, else <i>False</i>.
	 */
	private Boolean checkOrOperator(final QoCFilter _qoCFilter) {
		// - - - - - CORE OF THE METHOD - - - - -
		for (final QoCFilterExpression qocFilterExpression : qoCFilter.getListQocFilterExpression()) {
			if (matchQoCFilterExpression(qoCMetaData, qocFilterExpression)) {
				return true;
			}
		}
		// - - - - - RETURN STATEMENT - - - - -
		return false;
	}

	/**
	 * The method compares the values of the QoC meta-data with only one QoC
	 * filter expression expressed into the QoCFilter.
	 *
	 * @param _qoCFilter
	 *            The filter that contains the QoC filter expression.
	 * @return <i>True</i> if the values of the QoC meta-data respect the QoC
	 *         filter, else <i>False</i>.
	 */
	private Boolean checkUnaryOperator(final QoCFilter _qoCFilter) {
		// - - - - - RETURN STATEMENT - - - - -
		return matchQoCFilterExpression(qoCMetaData, qoCFilter.getListQocFilterExpression().iterator().next());
	}

	/**
	 * The method compares a value with another one following a specific
	 * comparator.
	 *
	 * @param _value
	 *            The compared value.
	 * @param _comparator
	 *            The operator used to compare the values.
	 * @param _expectedValue
	 *            The expected value of <i>_value</i>.
	 * @return <i>True</i> if the compared values respects <i>_comparator</i>,
	 *         else <i>False</i>.
	 */
	private Boolean compareValue(final String _value, final EBinaryComparator _comparator,
			final String _expectedValue) {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		/*
		 * The value returned by the method.
		 */
		Boolean ret_value = false;
		// - - - - - CORE OF THE METHOD - - - - -
		switch (_comparator) {
		case EQUALS:
			ret_value = _value.equals(_expectedValue);
			break;
		case UPPER_EQUALS:
			ret_value = _value.compareTo(_expectedValue) >= 0;
			break;
		case UPPER:
			ret_value = _value.compareTo(_expectedValue) > 0;
			break;
		case LOWER_EQUALS:
			ret_value = _value.compareTo(_expectedValue) <= 0;
			break;
		case LOWER:
			ret_value = _value.compareTo(_expectedValue) < 0;
			break;
		case DIFFERENT:
			ret_value = !_value.equals(_expectedValue);
			break;
		default:
			break;
		}
		// - - - - - RETURN STATEMENT - - - - -
		return ret_value;
	}

	/**
	 * The method verifies if the values of a QoC meta-data respect a
	 * constraint.
	 *
	 * @param _qoCMetaData
	 *            The QoCMetaData that is analyzed by the argument
	 *            <i>_qoCFilterExpression</i>.
	 * @param _qoCFilterExpression
	 *            The constraint used to analyze the values of the argument
	 *            <i>_qoCMetaData</i>
	 * @return <i>True</i> if the values if <i>_qoCMetaData</i> respects
	 *         <i>_qoCFilterExpression</i> else <i>False</i>.
	 */
	private Boolean matchQoCFilterExpression(final QoCMetaData _qoCMetaData,
			final QoCFilterExpression _qoCFilterExpression) {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		/*
		 * The value returned by the function.
		 */
		Boolean ret_value;
		/*
		 * The value of the argument <i>_qoCMetaData</i> concerned by the
		 * argument <i>_qoCFilterExpression</i>.
		 */
		String comparedValue = "";
		// - - - - - CORE OF THE METHOD - - - - -
		switch (_qoCFilterExpression.getQocIdentificator()) {
		case QOC_INDICATOR_ID:
			comparedValue = String.valueOf(_qoCMetaData.qoCIndicatorId());
			break;
		case QOC_METRICVALUE_ID:
			comparedValue = String.valueOf(_qoCMetaData.qoCMetricValueId());
			break;
		case QOC_METRICVALUE_VALUE:
			comparedValue = String.valueOf(_qoCMetaData.qoCMetricValueValue());
			break;
		case QOC_METRICVALUE_CREATIONDATE:
			comparedValue = String.valueOf(_qoCMetaData.qoCMetricValueCreationDate());
			break;
		case QOC_METRICVALUE_MODIFICATIONDATE:
			comparedValue = String.valueOf(_qoCMetaData.qoCMetricValueModificationDate());
			break;
		case QOC_CRITERION_ID:
			comparedValue = String.valueOf(_qoCMetaData.qoCCriterionId());
			break;
		case QOC_METRICDEFINITION_ID:
			comparedValue = String.valueOf(_qoCMetaData.qoCMetricDefinitionId());
			break;
		default:
			break;
		}
		ret_value = compareValue(comparedValue, _qoCFilterExpression.getComparator(),
				_qoCFilterExpression.getExpectedValue());
		// - - - - - RETURN STATEMENT - - - - -
		return ret_value;
	}
}
