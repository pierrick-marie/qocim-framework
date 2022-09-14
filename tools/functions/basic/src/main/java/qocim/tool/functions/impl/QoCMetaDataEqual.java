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

import java.util.logging.Level;

import qocim.datamodel.Order;
import qocim.datamodel.QoCMetaData;
import qocim.datamodel.utils.ConstraintChecker;
import qocim.datamodel.utils.ConstraintCheckerException;
import qocim.datamodel.utils.log.QoCIMLogger;
import qocim.tool.functions.IToolFunction;

/**
 * QoCMetaDataEqual verifies if two QoC meta-data are equal. Two QoC meta-data
 * are considered as equal if they are comparable (in terms of the tool function
 * <i>qoCMetaDataComparable</i>) and the following fields are equal:
 * <ul>
 * <li>QoCMetaData.getQoCMetricValueValue()</li>
 * <li>QoCMetaData.getQoCMetricValueCreationDate()</li>
 * <li>QoCMetaData.getQoCMetricValueModificationDate()</li>
 * </ul>
 * If all the fields are equal, the function returns <b>0</b>. If not, the
 * function compares the value of the other fields and returns <b>-1</b> or
 * <b>+1</b> whether the first compared QoCMetaData is <i>lower</i> than the
 * second QoCMetaData or not. The function compares in priority the filed
 * <i>value</i> and then the field <i>creation date</i> and finally the field
 * <i>modification date</i>.
 *
 * @see qocim.tool.functions.impl.QoCMetaDataComparable
 *
 * @author Pierrick MARIE
 */
public class QoCMetaDataEqual implements IToolFunction {

	// # # # # # CONSTANTS # # # # #

	/**
	 * If the QoC meta-data are equal the function returns <b>0</b>.
	 */
	private final static int QOC1_EQUALS_QOC2 = 0;
	/**
	 * If the first argument <i>qoCMetaData1</i> is upper than the second one,
	 * <i>qoCMetaData2</i>, the function returns <b>+1</b>.
	 */
	private final static int QOC1_UPPER_QOC2 = 1;
	/**
	 * If the first argument <i>qoCMetaData1</i> is lower than the second one,
	 * <i>qoCMetaData2</i>, the function returns <b>-1</b>.
	 */
	private final static int QOC1_LOWER_QOC2 = -1;

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
	/**
	 * The function used to verify whether the QoCMetaData are comparable or
	 * not.
	 */
	private final QoCMetaDataComparable qoCMetaDataComparable;

	// # # # # # CONSTRUCTORS # # # # #

	public QoCMetaDataEqual() {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		setUpIsDone = false;
		qoCMetaData1 = null;
		qoCMetaData2 = null;
		qoCMetaDataComparable = new QoCMetaDataComparable();
	}

	// # # # # # PUBLIC METHODS # # # # #

	/**
	 * The method executes the function <i>qoCMetaDataEqual</i>. The method
	 * compared in priority the value of the QoC metric value. If they are
	 * equal, the method compares the creation dates and then the modification
	 * dates of the QoC metric value.
	 *
	 * @return <b>0</b>, <b>-1</b> or <b>+1</b> whether the compared QoC
	 *         meta-data are equal or not.
	 */
	@Override
	public Object exec() {
		// - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
		try {
			final String message = "QoCMetaDataEqual.exec() method setup(QoCMetaData, QoCMetaData) have to be called before.";
			ConstraintChecker.assertTrue(setUpIsDone, message);
		} catch (final ConstraintCheckerException e) {
			return new Object();
		}
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		/*
		 * This attribute contains value of the field <b>direction</b> of the
		 * class <b>QoCMetricDefinition</b> used to produce the value of the QoC
		 * meta-data. Because the private fields <i>qoCMetaData1</i> and
		 * <i>qoCMetaData2</i> are comparable (it is checked in the method
		 * <i>setUp</i>), both have the same direction.
		 */
		final Order direction = qoCMetaData1.qoCMetricDefinition().direction();
		/*
		 * This attributes is the result of the difference between the value of
		 * the private fields <i>qoCMetaData1</i> and <i>qoCMetaData2</i>.
		 */
		final Integer diffQoCMetricValue = (int) (qoCMetaData1.qoCMetricValueValue()
				- qoCMetaData2.qoCMetricValueValue());
		// - - - - - CORE OF THE METHOD - - - - -
		if (diffQoCMetricValue.equals(QOC1_EQUALS_QOC2)) {
			return checkDates(qoCMetaData1, qoCMetaData2);
		} else {
			return checkValues(direction, diffQoCMetricValue);
		}
	}

	/**
	 * The method initializes the arguments of the function
	 * <i>qoCMetaDataEqual</i>.
	 *
	 * @param _qoCMetaData1
	 *            The first QoC meta-data that have to be compared.
	 * @param _qoCMetaData2
	 *            The second QoC meta-data that have to be compared.
	 * @return <b>this</b>
	 */
	public QoCMetaDataEqual setUp(final QoCMetaData _qoCMetaData1, final QoCMetaData _qoCMetaData2) {
		// - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
		qoCMetaDataComparable.setUp(_qoCMetaData1, _qoCMetaData2);
		if (!(Boolean) qoCMetaDataComparable.exec()) {
			final String message = "QoCMetaDataEqual.setUp(QoCMetaData, QoCMetaData): QoCMetaData are not comparable";
			QoCIMLogger.logger.log(Level.SEVERE, message);
			setUpIsDone = false;
			return this;
		}
		if (!_qoCMetaData1.qoCCriterionId().equals(_qoCMetaData2.qoCCriterionId())) {
			QoCIMLogger.logger.log(Level.SEVERE,
					"QoCMetaDataEqual.setUp(QoCMetaData, QoCMetaData): Indicators are not equal");
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

	// # # # # # PRIVATE METHODS # # # # #

	/**
	 * The method compares the creation and modification date of
	 * <i>_qoCMetaData1</i> and <i>_qoCMetaData2</i>. If the creation dates are
	 * equal, the method compares the modification dates.
	 *
	 * @param _qoCMetaData1
	 *            the first QoC meta-data that should be compared.
	 * @param _qoCMetaData2
	 *            the second QoC meta-data that should be compared.
	 * @return <b>0</b>, <b>-1</b> or <b>+1</b> whether <i>_qoCMetaData1</i> is
	 *         respectively equal, lower or upper than <i>_qoCMetaData2</i>.
	 */
	private Integer checkDates(final QoCMetaData _qoCMetaData1, final QoCMetaData _qoCMetaData2) {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		/*
		 * This attribute contains the difference between the creation date of
		 * the arguments <i>_qoCMetaData1</i> and <i>_qoCMetaData2</i>.
		 */
		final Long diffCreationDate = qoCMetaData1.qoCMetricValueCreationDate()
				- qoCMetaData2.qoCMetricValueCreationDate();
		/*
		 * This attribute contains the difference between the modification date
		 * of the arguments <i>_qoCMetaData1</i> and <i>_qoCMetaData2</i>.
		 */
		final Long diffModificationDate = qoCMetaData1.qoCMetricValueModificationDate()
				- qoCMetaData2.qoCMetricValueModificationDate();
		// - - - - - CORE OF THE METHOD - - - - -
		if (diffCreationDate.equals(Long.valueOf(QOC1_EQUALS_QOC2))) {
			return diffModificationDate.compareTo(Long.valueOf(QOC1_EQUALS_QOC2));
		} else {
			if (diffCreationDate > QOC1_EQUALS_QOC2) {
				return QOC1_UPPER_QOC2;
			} else {
				return QOC1_LOWER_QOC2;
			}
		}
	}

	/**
	 * The method evaluates the difference between the value of
	 * <i>_qoCMetaData1</i> and <i>_qoCMetaData2</i>.
	 *
	 * @param direction
	 *            the value of the field direction of QoC metric definition used
	 *            to create the value of the QoC meta-data.
	 * @param diffQoCMetricValue
	 *            the difference between the values of the field value of the
	 *            first and the second QoC meta-data.
	 * @return <b>-1</b> or <b>+1</b> whether the argument
	 *         <i>diffQoCMetricValue</i> is lower or upper than zero and after
	 *         taking into account the value of the argument <i>direction</i>.
	 */
	private Integer checkValues(final Order direction, final Integer diffQoCMetricValue) {
		// - - - - - CORE OF THE METHOD - - - - -
		if (direction.equals(Order.UNKNOWN) || direction.equals(Order.UPPER)) {
			if (diffQoCMetricValue > 0) {
				return QOC1_UPPER_QOC2;
			} else {
				return QOC1_LOWER_QOC2;
			}
		} else {
			if (diffQoCMetricValue > 0) {
				return QOC1_LOWER_QOC2;
			} else {
				return QOC1_UPPER_QOC2;
			}
		}
	}
}
