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

import java.util.logging.Level;

import qocim.datamodel.utils.ConstraintChecker;
import qocim.datamodel.utils.ConstraintCheckerException;
import qocim.datamodel.utils.QoCIMLogger;

/**
 * QoCFilterExpression represents a constraint about one field of the QoC
 * meta-data.
 *
 * @see mucontext.datamodel.qocim.QoCMetaData
 * @see qocim.tool.functions.utils.EQoCIdentificator
 * @see qocim.tool.functions.utils.EBinaryComparator
 *
 * @author Pierrick MARIE
 */
public class QoCFilterExpression {

	// # # # # # PRIVATE VARIABLES # # # # #

	/**
	 * eQocIdentificator identifies on which field of an instance of QoCMetaData
	 * the constraint relies on.
	 */
	private final EQoCIdentificator eQocIdentificator;
	/**
	 * eComparator is the operation use to compare the real value of the field
	 * with its expected value.
	 */
	private final EBinaryComparator eComparator;
	/**
	 * expectedValue is the expected value of the field that is compared.
	 */
	private final String expectedValue;

	// # # # # # CONSTRUCTORS # # # # #

	public QoCFilterExpression(final EQoCIdentificator _eQoCIdentificator, final EBinaryComparator _eComparator,
			final String _expectedValue) {
		// - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
		try {
			String message = "QoCFilterExpression.constructor(EQoCIdentificator, EComparator, String): the argument _eQoCIdentificator is null";
			ConstraintChecker.notNull(_eQoCIdentificator, message);
			message = "QoCFilterExpression.constructor(EQoCIdentificator, EComparator, String): the argument _eComparator is null";
			ConstraintChecker.notNull(_eQoCIdentificator, message);
			message = "QoCFilterExpression.constructor(EQoCIdentificator, EComparator, String): the argument _expectedValue is null";
			ConstraintChecker.notNull(_eQoCIdentificator, message);
		} catch (final ConstraintCheckerException _exception) {
			final String message = "QoCFilterExpression.constructor(EQoCIdentificator, EComparator, String): Fatal error.";
			QoCIMLogger.logger.log(Level.SEVERE, message, _exception);
			System.exit(-1);
		}
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		eQocIdentificator = _eQoCIdentificator;
		eComparator = _eComparator;
		expectedValue = _expectedValue;
	}

	public QoCFilterExpression(final QoCFilterExpression _qoCFilterExpression) {
		// - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
		try {
			final String message = "QoCFilterExpression.constructor(QoCFilterExpression): the argument _qoCFilterExpression is null";
			ConstraintChecker.notNull(_qoCFilterExpression, message);
		} catch (final ConstraintCheckerException _exception) {
			final String message = "QoCFilterExpression.constructor(QoCFilterExpression): Fatal error.";
			QoCIMLogger.logger.log(Level.SEVERE, message, _exception);
			System.exit(-1);
		}
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		eQocIdentificator = _qoCFilterExpression.eQocIdentificator;
		eComparator = _qoCFilterExpression.eComparator;
		expectedValue = _qoCFilterExpression.expectedValue;
	}

	// # # # # # PUBLIC METHODS # # # # #

	/**
	 * @return The current comparator.
	 */
	public EBinaryComparator getComparator() {
		// - - - - - RETURN STATEMENT - - - - -
		return eComparator;
	}

	/**
	 * @return The current QoC identificator.
	 */
	public EQoCIdentificator getQocIdentificator() {
		// - - - - - RETURN STATEMENT - - - - -
		return eQocIdentificator;
	}

	/**
	 * @return The expected value of the filter expression.
	 */
	public String getExpectedValue() {
		// - - - - - RETURN STATEMENT - - - - -
		return expectedValue;
	}
}
