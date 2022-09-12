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
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

import qocim.datamodel.QoCMetaData;
import qocim.datamodel.QoCMetricDefinition;
import qocim.datamodel.information.QInformation;
import qocim.datamodel.utils.ConstraintChecker;
import qocim.datamodel.utils.ConstraintCheckerException;
import qocim.datamodel.utils.QoCIMLogger;
import qocim.tool.functions.IToolFunction;

/**
 * ComputeQoCMetricValue computes and returns the value of the filed
 * <i>value</i> of the class <b>QoCMetricValue</b>. The class requires a map of
 * <b>String</b>, <b>IQoCIMFactory</b>: <i>map_availableQoCIMFacade</i>. The
 * function searched and delegates to the right QoCIM factory the task of
 * computing the value of the QoC. The class find the right QoCIM factory by
 * comparing the key of the field <i>map_availableQoCIMFacade</i> with the
 * private field <i>qoCMetricDefinitionId</i>.
 *
 *X @author Pierrick MARIE
 */
public class ComputeQoCMetricValue implements IToolFunction {

	// # # # # # PRIVATE VARIABLES # # # # #

	/**
	 * Verifies if the configuration of the function has been done.
	 */
	private Boolean setUpIsDone;
	/**
	 * The map used to search the right QoCIM factory used to produce the value
	 * of the QoC. The key of the map is the field <i>id</i> of all QoC metric
	 * definition available to compute the value of the QoC of a context
	 * observation. The value of the map is the corresponding QoCIM
	 * factory<b>IQoCIMFacade</b> that contain the methods to used to compute
	 * the value of the QoC.
	 */
	private final List<QoCMetricDefinition> list_availableQoCMetricDefinition;
	/**
	 * The context information.
	 */
	private QInformation information;
	private String qoCMetricDefinitionId;
	/**
	 * The list of the <b>QoCMetaData</b> already associated to the context
	 * observation that is evaluated.
	 */
	private List<QoCMetaData> list_qoCMetaData;

	// # # # # # CONSTRUCTORS # # # # #

	public ComputeQoCMetricValue(final List<QoCMetricDefinition> _list_availableQoCMetricDefinition) {
		// - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
		try {
			final String message = "ComputeQoCMetricValue(List<QoCMetricDefinition>): the argument _list_availableQoCIndicator is null";
			ConstraintChecker.notNull(_list_availableQoCMetricDefinition, message);
		} catch (final ConstraintCheckerException _exception) {
			QoCIMLogger.logger.log(Level.SEVERE, "ComputeQoCMetricValue(List<QoCMetricDefinition>): Fatal error.",
					_exception);
			System.exit(-1);
		}
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		setUpIsDone = false;
		list_availableQoCMetricDefinition = _list_availableQoCMetricDefinition;
		information = null;
		qoCMetricDefinitionId = "";
		list_qoCMetaData = new ArrayList<QoCMetaData>();
	}

	// # # # # # PUBLIC METHODS # # # # #

	/**
	 * The method executes the function <i>computeQoCMetricValue</i>. It
	 * searches the appropriate QoCIM factory and then, use it to compute the
	 * value of the QoC metric.
	 *
	 * @return The value of the QoC meta-data.
	 */
	@Override
	public Object exec() {
		// - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
		try {
			final String message = "ComputeQoCMetricValue.exec() method setUp(String, String, Long, Integer, String, List<QoCMetaData>) have to be called before.";
			ConstraintChecker.assertTrue(setUpIsDone, message);
		} catch (final ConstraintCheckerException e) {
			return new Object();
		}
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		/*
		 * The value returned by the method: the value of the QoC metric.
		 */
		Double ret_qoCMetricValue;
		/*
		 * The appropriate <b>QoCMetricDefinition</b> used to produce the value
		 * of the QoC metric value.
		 */
		final QoCMetricDefinition qoCMetricDefinition = searchQoCMetricDefinition();
		// - - - - - CORE OF THE METHOD - - - - -
		if (qoCMetricDefinition != null) {
			ret_qoCMetricValue = qoCMetricDefinition.computeQoCMetricValue(information, list_qoCMetaData);
		} else {
			ret_qoCMetricValue = 0.0;
		}
		// - - - - - RETURN STATEMENT - - - - -
		return ret_qoCMetricValue;
	}

	/**
	 * The method initializes the arguments of the function
	 * <i>computeQoCMetricValue</i>.
	 *
	 * @param information
	 *            The context observation.
	 * @param qoCMetricDefinitionId
	 *            The id of the QoCMetricDefinition used to create the new
	 *            QoCMetricValue.
	 * @param qoCMetaDataList
	 *            The list of existing QoCMetricValue associated to the context
	 *            observation.
	 * @return <b>this</b>
	 */
	public ComputeQoCMetricValue setUp(final QInformation information,
									   final String qoCMetricDefinitionId, final List<QoCMetaData> qoCMetaDataList) {
		// - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
		try {
			String message = "ComputeQoCMetricValue.setUp(QInformation, String, List<QoCMetaData): the argument information is null";
			ConstraintChecker.notNull(information, message);
			message = "ComputeQoCMetricValue.setUp(QInformation, String, List<QoCMetaData): the argument _qoCMetricDefinitionId is null";
			ConstraintChecker.notNull(qoCMetricDefinitionId, message);
			message = "ComputeQoCMetricValue.setUp(QInformation, String, List<QoCMetaData): the argument _list_qoCMetaData is null";
			ConstraintChecker.notNull(qoCMetaDataList, message);
		} catch (final ConstraintCheckerException e) {
			setUpIsDone = false;
			return this;
		}
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		this.information = information;
		this.qoCMetricDefinitionId = qoCMetricDefinitionId;
		list_qoCMetaData = qoCMetaDataList;
		setUpIsDone = true;
		// - - - - - RETURN STATEMENT - - - - -
		return this;
	}

	// # # # # # PRIVATE METHODS # # # # #

	/**
	 * The method searches the appropriate QoCIM factory by comparing the
	 * private field <i>qoCMetricDefinitionId</i> from the list
	 * <i>list_availableQoCMetricDefinition</i>.
	 *
	 * @return The appropriate <b>QoCMetricDefinition</b>.
	 */
	private QoCMetricDefinition searchQoCMetricDefinition() {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		/*
		 * The returned value: the <b>QoCMetricDefinition</b> founded by the
		 * method.
		 */
		QoCMetricDefinition ret_qoCMetricDefinition = null;

		// - - - - - CORE OF THE METHOD - - - - -
		for (final QoCMetricDefinition loop_qoCMetricDefintion : list_availableQoCMetricDefinition) {
			if (loop_qoCMetricDefintion.id().equals(qoCMetricDefinitionId)) {
				ret_qoCMetricDefinition = loop_qoCMetricDefintion;
			}
		}
		// - - - - - RETURN STATEMENT - - - - -
		return ret_qoCMetricDefinition;
	}
}
