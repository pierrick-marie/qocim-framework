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
 * The function search the most recent QoC meta-data created by a specific QoC
 * metric definition and contained into a list of <b>QoCMetaData</b>. The
 * function compares the fields <i>creation date</i> to get the most recent QoC
 * meta-data and verifies if the field <i>qoCMetricDefinitionId</i> of the QoC
 * meta-data is equal to the expected QoC metric definition <i>id</i>.
 *
 * @author Pierrick MARIE
 */
public class GetMostRecentQoCMetaData implements IToolFunction {

	// # # # # # PRIVATE VARIABLES # # # # #

	/**
	 * Verifies if the configuration of the function has been done.
	 */
	private Boolean setUpIsDone;
	/**
	 * The <i>id</i> of the QoC metric definition used to produce the most
	 * recent QoC meta-data.
	 */
	private String qoCMetricDefinitionId;
	/**
	 * The list of QoC meta-data where the most recent QoC meta-data is
	 * searched.
	 */
	private List<QoCMetaData> list_qoCMetaData;

	// # # # # # CONSTRUCTORS # # # # #

	public GetMostRecentQoCMetaData() {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		setUpIsDone = false;
		list_qoCMetaData = null;
	}

	// # # # # # PUBLIC METHODS # # # # #

	/**
	 * The method executes the function <i>getMostRecentQoCMetaData</i>. The
	 * method searches from a list of QoC meta-data the most recent QoC
	 * meta-data associated to a specific QoC metric definition. To do it, the
	 * method compares the private field <i>creation date</i>.
	 *
	 * @return The most recent QoC meta-data created by the QoC
	 *         metric-definition referenced by <i>qoCMetricDefinitionId</i>.
	 */
	@Override
	public Object exec() {
		// - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
		try {
			final String message = "GetMostRecentQoCMetaData.exec() method setUp(String, List<QoCMetaData>) have to be called before.";
			ConstraintChecker.assertTrue(setUpIsDone, message);
		} catch (final ConstraintCheckerException e) {
			return new Object();
		}
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		/*
		 * The QoC most recent meta-data returned by the method.
		 */
		QoCMetaData ret_qoCMetaData = null;
		/*
		 * The creation date of the analyzed QoC meta-data.
		 */
		Long creationDate = Long.valueOf(0);
		// - - - - - CORE OF THE METHOD - - - - -
		for (final QoCMetaData qoCMetaData : list_qoCMetaData) {
			if (qoCMetaData.qoCMetricDefinitionId().equals(qoCMetricDefinitionId)) {
				if (creationDate < qoCMetaData.qoCMetricValueCreationDate()) {
					creationDate = qoCMetaData.qoCMetricValueCreationDate();
					ret_qoCMetaData = qoCMetaData;
				}
			}
		}
		// - - - - - RETURN STATEMENT - - - - -
		return ret_qoCMetaData;
	}

	/**
	 * The method initializes the arguments of the function
	 * <i>getMostRecentQoCMetaData</i>.
	 *
	 * @param _qoCMetricDefinitionId
	 *            The id of the QoC metric definition used to search the most
	 *            recent QoC meta-data.
	 * @param _list_qoCMetaData
	 *            The list of QoC meta-data where to search the most recent QoC
	 *            meta-data.
	 * @return <b>this</b>
	 */
	public GetMostRecentQoCMetaData setUp(final String _qoCMetricDefinitionId,
			final List<QoCMetaData> _list_qoCMetaData) {
		// - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
		try {
			String message = "GetMostRecentQoCMetaData.setUp(String, List<QoCMetaData>): the argument _qoCMetricDefinitionId is null";
			ConstraintChecker.notNull(_qoCMetricDefinitionId, message);
			message = "GetMostRecentQoCMetaData.setUp(String, List<QoCMetaData>): the argument _list_qoCMetaData is null";
			ConstraintChecker.notNull(_list_qoCMetaData, message);
			message = "GetMostRecentQoCMetaData.setUp(String, List<QoCMetaData>): the argument _list_qoCMetaData is empty";
			ConstraintChecker.notEmpty(_list_qoCMetaData.toArray(), message);
		} catch (final ConstraintCheckerException e) {
			setUpIsDone = false;
			return this;
		}
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		list_qoCMetaData = _list_qoCMetaData;
		qoCMetricDefinitionId = _qoCMetricDefinitionId;
		setUpIsDone = true;
		// - - - - - RETURN STATEMENT - - - - -
		return this;
	}
}
