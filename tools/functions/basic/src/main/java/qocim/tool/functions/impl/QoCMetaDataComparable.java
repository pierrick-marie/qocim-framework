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

/**
 * QoCMetaDataComparable verifies if two QoC meta-data are comparable.
 *
 * @see mucontext.datamodel.qocim.QoCMetaData
 *
 * @author Pierrick MARIE
 */
public class QoCMetaDataComparable implements IToolFunction {

	// # # # # # PRIVATE VARIABLES # # # # #

	/**
	 * Verifies if the configuration of the function has been done.
	 */
	private Boolean setUpIsDone;
	/**
	 * The first QoC meta-data compared with the second one.
	 */
	private QoCMetaData qoCMetaData1;
	/**
	 * The second QoC meta-data compared with the first one.
	 */
	private QoCMetaData qoCMetaData2;

	// # # # # # CONSTRUCTORS # # # # #

	public QoCMetaDataComparable() {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		setUpIsDone = false;
		qoCMetaData1 = null;
		qoCMetaData2 = null;
	}

	// # # # # # PUBLIC METHODS # # # # #

	/**
	 * The method executes the function <i>qoCMetaDataComparable</i>. It
	 * compares the following fields:
	 * <ul>
	 * <li>getQoCCriterionId()</li>
	 * <li>getQoCMetricDefinitionId()</li>
	 * <li>getQoCIndicatorId()</li>
	 * </ul>
	 *
	 * @return <b>True</b> or <b>False</b> whether all the compared fields are
	 *         equal.
	 */
	@Override
	public Object exec() {
		// - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
		try {
			final String message = "QoCMetaDataComparable.exec() method setup(QoCMetaData, QoCMetaData) have to be called before.";
			ConstraintChecker.assertTrue(setUpIsDone, message);
		} catch (final ConstraintCheckerException e) {
			return new Object();
		}
		// - - - - - RETURN STATEMENT - - - - -
		return qoCMetaData1.qoCCriterionId().equals(qoCMetaData2.qoCCriterionId())
				&& qoCMetaData1.qoCMetricDefinitionId().equals(qoCMetaData2.qoCMetricDefinitionId())
				&& qoCMetaData1.qoCIndicatorId().equals(qoCMetaData2.qoCIndicatorId());
	}

	/**
	 * The method initializes the arguments of the function
	 * <i>qoCMetaDataComparable</i>.
	 *
	 * @param _qoCMetaData1
	 *            the first QoC meta-data that should be compared.
	 * @param _qoCMetaData2
	 *            the second QoC meta-data that should to be compared.
	 * @return <b>this</b>
	 */
	public QoCMetaDataComparable setUp(final QoCMetaData _qoCMetaData1, final QoCMetaData _qoCMetaData2) {
		// - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
		try {
			String message = "QoCMetaDataComparable.setup(QoCMetaData, QoCMetaData): the argument _qoCMetaData1 is null";
			ConstraintChecker.notNull(_qoCMetaData1, message);
			message = "QoCMetaDataComparable.setup(QoCMetaData, QoCMetaData): the argument _qoCMetaData2 is null";
			ConstraintChecker.notNull(_qoCMetaData2, message);
		} catch (final ConstraintCheckerException e) {
			setUpIsDone = false;
			return this;
		}
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		qoCMetaData1 = _qoCMetaData1;
		qoCMetaData2 = _qoCMetaData2;
		setUpIsDone = true;
		// - - - - - RETURN STATEMENT - - - - -
		return this;
	}
}
