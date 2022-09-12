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

import java.util.List;

import qocim.datamodel.QoCMetaData;
import qocim.datamodel.utils.ConstraintChecker;
import qocim.datamodel.utils.ConstraintCheckerException;
import qocim.tool.functions.IToolFunction;

/**
 * ComputeQoCMetricId computes the value of the filed <i>id</i> of the class
 * <b>QoCMetricValue</b> for a specific QoC indicator. The function returns the
 * number of the existing QoC metric value relative to the QoC indicator
 * identified by the private filed <i>qoCIndicatorId</i>. If there is no
 * QoCMetricValue associated to the QoC indicator, the function returns
 * <b>zero</b>.
 *
 * @author Pierrick MARIE
 */
public class ComputeQoCMetricId implements IToolFunction {

	// # # # # # PRIVATE VARIABLES # # # # #

	/**
	 * Verifies if the configuration of the function has been done.
	 */
	private Boolean setUpIsDone;
	/**
	 * The list of all the existing QoC metric value associated to a context
	 * observation.
	 */
	private List<QoCMetaData> list_qoCMetaData;
	/**
	 * The id of the QoC indicator used to compute the next <i>id</i> of a QoC
	 * metric value.
	 */
	private Integer qoCIndicatorId;
	/**
	 * The default value of the counter: <b>zero</b>.
	 */
	private static final int INIT_COUNTER_VALUE = 0;

	// # # # # # CONSTRUCTORS # # # # #

	public ComputeQoCMetricId() {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		setUpIsDone = false;
		list_qoCMetaData = null;
	}

	// # # # # # PUBLIC METHODS # # # # #

	/**
	 * The method executes the function <i>computeQoCMetricId</i>. It counts and
	 * returns the number of QoC metric value associated to the appropriate QoC
	 * indicator.
	 *
	 * @return The id of the next QoCMetriValue associated to the QoCIndicator
	 *         identified by <i>qoCIndicatorId</i>.
	 */
	@Override
	public Object exec() {
		// - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
		try {
			final String message = "ComputeQoCMetricId.exec() method setUp(List<QoCMetaData>,Integer) have to be called before.";
			ConstraintChecker.assertTrue(setUpIsDone, message);
		} catch (final ConstraintCheckerException e) {
			return new Object();
		}
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		/*
		 * The value returned by the method: the number of QoC metric value
		 * associated to a specific QoC indicator.
		 */
		Integer ret_counter = INIT_COUNTER_VALUE;
		// - - - - - CORE OF THE METHOD - - - - -
		for (final QoCMetaData qoCMetaData : list_qoCMetaData) {
			if (qoCMetaData.qoCIndicatorId().equals(qoCIndicatorId)) {
				ret_counter++;
			}
		}
		// - - - - - RETURN STATEMENT - - - - -

		return ret_counter;
	}

	/**
	 * The method initializes the arguments of the function
	 * <i>computeQoCMetricId</i>.
	 *
	 * @param _list_qoCMetaData
	 *            The list of all existing QoC meta-data associated to a context
	 *            observation.
	 * @param _qoCIndicatorId
	 *            The <i>id</i> of the QoC indicator used to compute the
	 *            <i>id</i> of the next QoC metric value.
	 * @return <b>this</b>
	 */
	public IToolFunction setUp(final List<QoCMetaData> _list_qoCMetaData, final Integer _qoCIndicatorId) {
		// - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
		try {
			String message = "ComputeQoCMetricId.setup(List<QoCMetaData>, Integer): the argument _qocMetaDataList is null";
			ConstraintChecker.notNull(_list_qoCMetaData, message);
			message = "ComputeQoCMetricId.setup(List<QoCMetaData>, Integer): the argument _qocIndicatorId is null";
			ConstraintChecker.notNull(_qoCIndicatorId, message);
			message = "ComputeQoCMetricId.setup(Integer, String, String): the argument _qoCIndicatorId is not between the bounds 0 and "
					+ Integer.MAX_VALUE;
			ConstraintChecker.doubleIsBetweenBounds(new Double(_qoCIndicatorId), 0.0, Double.MAX_VALUE, message);
		} catch (final ConstraintCheckerException e) {
			setUpIsDone = false;
			return this;
		}
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		list_qoCMetaData = _list_qoCMetaData;
		qoCIndicatorId = _qoCIndicatorId;
		setUpIsDone = true;
		// - - - - - RETURN STATEMENT - - - - -
		return this;
	}
}
