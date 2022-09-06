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
package qocim.qocmanagement.functions.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mucontext.datamodel.context.ContextEntity;
import mucontext.datamodel.context.ContextObservable;
import mucontext.datamodel.context.ContextObservation;
import mucontext.datamodel.context.ContextReport;
import qocim.datamodel.QoCIMFacade;
import qocim.datamodel.QoCIndicator;
import qocim.datamodel.QoCMetaData;
import qocim.datamodel.QoCMetricDefinition;
import qocim.datamodel.utils.ConstraintChecker;
import qocim.datamodel.utils.ConstraintCheckerException;
import qocim.datamodel.utils.IQoCIMFactory;
import qocim.datamodel.utils.QoCIMLogger;
import qocim.qocmanagement.functions.IQoCManagementFunction;
import qocim.qocmanagement.functions.utils.EQoCManagementFunction;
import qocim.qocmanagement.functions.utils.LogMessages;
import qocim.tool.functions.impl.ComputeQoCMetricId;
import qocim.tool.functions.impl.ComputeQoCMetricValue;

/**
 * AddQoCIndicator adds a new QoC meta-data into the QoC meta-data of a context
 * report. The QoC meta-data is associated to the context observations of a
 * context report. Because a context report can contain more than one context
 * observation, the new QoC meta-data is inserted into the list of existing QoC
 * meta-data of all context observations of the context report. If the QoC
 * indicator of the new QoC meta-data does not already exists in the list of QoC
 * meta-data, the indicator is inserted, else only the QoC metric value is
 * inserted in the QoC meta-data. To realize it, the function uses the
 * <b>QoCIMFacade</b> provided by the maven module QoCIM-common.
 *
 * @see mucontext.datamodel.qocim.QoCIndicator
 * @see mucontext.datamodel.qocim.QoCCriterion
 * @see mucontext.datamodel.qocim.QoCMetricDefinition
 * @see mucontext.datamodel.qocim.QoCMetricValue
 * @see mucontext.datamodel.qocim.QoCIMFacade
 * @see mucontext.datamodel.context.ContextReport
 * @see mucontext.datamodel.context.ContextObservation
 *
 * @author Pierrick MARIE
 */
public class AddQoCIndicator implements IQoCManagementFunction {

	// # # # # # CONSTANTS # # # # #

	/**
	 * The name of the function.
	 */
	public static final String FUNCTION_NAME = EQoCManagementFunction.ADDQOCINDICATOR.toString();
	/**
	 * The name of the parameter 1.
	 */
	public static final String PARAM_QOC_INDICATOR_ID = "qoc_indicator_id";
	/**
	 * The name of the pParameter 2.
	 */
	public static final String PARAM_QOC_CRITERION_ID = "qoc_criterion_id";
	/**
	 * The name of the parameter 3.
	 */
	public static final String PARAM_QOC_DEFINITION_ID = "qoc_definition_id";

	// # # # # # PRIVATE VARIABLES # # # # #

	/**
	 * Verifies if the configuration of the function has been done.
	 */
	private Boolean setUpIsDone;
	/**
	 * The <i>id</i> of the QoC indicator that have to be add into the QoC
	 * meta-data.
	 */
	private Integer qoCIndicatorId;
	/**
	 * The <i>id</i> of the QoC criterion that have to be add into the QoC
	 * meta-data.
	 */
	private String qoCCriterionId;
	/**
	 * The <i>id</i> of the QoC metric definition that have to be add into the
	 * QoC meta-data.
	 */
	private String qoCMetricDefinitionId;
	/**
	 * The tool function used to compute value of the QoC meta-data that have to
	 * be inserted.
	 */
	private final ComputeQoCMetricValue computeQoCMetricValue;
	/**
	 * The tool function used to compute id of the QoC meta-data that have to be
	 * inserted.
	 */
	private final ComputeQoCMetricId computeQoCMetricId;
	/**
	 * The map used to find the right <b>IQoCIMFactory</b> used to compute the
	 * value of the QoC metric value that have to be inserted.
	 */
	private final Map<QoCMetricDefinition, IQoCIMFactory> map_availableQoCIMFacade;

	// # # # # # CONSTRUCTORS # # # # #

	public AddQoCIndicator(final Map<QoCMetricDefinition, IQoCIMFactory> _map_availableQoCIMFacade) {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		setUpIsDone = false;
		map_availableQoCIMFacade = _map_availableQoCIMFacade;
		computeQoCMetricValue = new ComputeQoCMetricValue(
				new ArrayList<QoCMetricDefinition>(map_availableQoCIMFacade.keySet()));
		computeQoCMetricId = new ComputeQoCMetricId();
		QoCIMLogger.functionLog(FUNCTION_NAME, LogMessages.NEW_FUNCTION_INSTANCE);
	}

	// # # # # # PUBLIC METHODS # # # # #

	/**
	 * The method executes the function <i>addQoCindicator</i>. For each context
	 * observation of the context report, the method computes the new values of
	 * the QoC, create a new QoC indicator with the values and insert the
	 * indicator in the the corresponding context observation.
	 */
	@Override
	public ContextReport exec(final ContextReport _contextReport) {
		// - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
		try {
			String message = "AddQoCIndicator.exec() method setup(Integer, String, String) have to be called before.";
			ConstraintChecker.assertTrue(setUpIsDone, message);
			message = "AddQoCIndicator.exec(ContextReport): the argument _contextReport is null.";
			ConstraintChecker.notNull(_contextReport, message);
		} catch (final ConstraintCheckerException e) {
			return _contextReport;
		}
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		/*
		 * The context entity associated to the context observation that is
		 * modified.
		 */
		ContextEntity contextEntity;
		/*
		 * The context observable associated to the context observation that is
		 * modified.
		 */
		ContextObservable contextObservable;
		/*
		 * The list of QoC meta-data associated to the context observation that
		 * is modified.
		 */
		List<QoCMetaData> list_qoCMetaData;
		/*
		 * The value of the QoC metric inserted into the QoC meta-data of the
		 * context observation.
		 */
		Double new_qoCMetricValueValue;
		/*
		 * The value of the <i>id</i> of QoC metric inserted into the QoC
		 * meta-data of the context observation.
		 */
		String new_qoCMetricValueId;
		/*
		 * The QoC indicator inserted into the QoC meta-data of the context
		 * observation.
		 */
		QoCIndicator new_qoCIndicator;
		// - - - - - CORE OF THE METHOD - - - - -
		QoCIMLogger.functionLog(FUNCTION_NAME, LogMessages.BEGIN_EXECUTION_FUNCTION);
		for (final ContextObservation<?> loop_contextObservation : _contextReport.observations) {
			contextObservable = loop_contextObservation.observable;
			contextEntity = contextObservable.entity;
			list_qoCMetaData = getListQoCMetaData(loop_contextObservation);
			new_qoCMetricValueId = computeQoCMetricValueId(list_qoCMetaData);
			new_qoCMetricValueValue = computeQoCMetricValueValue(loop_contextObservation, contextObservable,
					contextEntity, list_qoCMetaData);
			new_qoCIndicator = createNewQoCIndicator(new_qoCMetricValueId, new_qoCMetricValueValue);
			insertNewQoCIndicator(loop_contextObservation, new_qoCIndicator);
		}
		QoCIMLogger.functionLog(FUNCTION_NAME, LogMessages.END_EXECUTION_FUNCTION);
		// - - - - - RETURN STATEMENT - - - - -
		return _contextReport;
	}

	/**
	 * The method initializes the arguments of the function
	 * <i>addQoCindicator</i>.
	 *
	 * @param _qoCIndicatorId
	 *            The <i>id</i> of the QoC indicator that will be inserted into
	 *            the QoC meta-data.
	 * @param _qoCCriterionId
	 *            The <i>id</i> of the QoC criterion that will be inserted into
	 *            the QoC meta-data.
	 * @param _qoCMetricDefinitionId
	 *            The <i>id</i> of the QoC metric definition that will be
	 *            inserted into the QoC meta-data.
	 * @return <b>this</b>
	 */
	public IQoCManagementFunction setUp(final Integer _qoCIndicatorId, final String _qoCCriterionId,
			final String _qoCMetricDefinitionId) {
		// - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
		try {
			String message = "AddQoCIndicator.setup(Integer, String, String): the argument _qoCIndicatorId is null";
			ConstraintChecker.notNull(_qoCIndicatorId, message);
			message = "AddQoCIndicator.setup(Integer, String, String): the argument _qoCCriterionId is null";
			ConstraintChecker.notNull(_qoCCriterionId, message);
			message = "AddQoCIndicator.setup(Integer, String, String): the argument _qoCMetricDefinitionId is null";
			ConstraintChecker.notNull(_qoCMetricDefinitionId, message);
		} catch (final ConstraintCheckerException e) {
			setUpIsDone = false;
			return this;
		}
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		qoCIndicatorId = _qoCIndicatorId;
		qoCCriterionId = _qoCCriterionId;
		qoCMetricDefinitionId = _qoCMetricDefinitionId;
		setUpIsDone = true;
		QoCIMLogger.functionLog(FUNCTION_NAME, LogMessages.SETUP_FUNCTION);
		// - - - - - RETURN STATEMENT - - - - -
		return this;
	}

	@Override
	public void setParameters(final Map<String, String> _map_paramaters) {
		// - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
		try {
			final String message = "AddQoCIndicator.setParameters(Map<String, String>): the argument _map_paramaters is null";
			ConstraintChecker.notNull(_map_paramaters, message);
		} catch (final ConstraintCheckerException e) {
			return;
		}
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		final String qoCCriterionId = _map_paramaters.get(PARAM_QOC_CRITERION_ID);
		final String qoCDefinitionId = _map_paramaters.get(PARAM_QOC_DEFINITION_ID);
		final String qoCIndicatorId = _map_paramaters.get(PARAM_QOC_INDICATOR_ID);
		// - - - - - CORE OF THE METHOD - - - - -
		if (qoCIndicatorId != null && qoCCriterionId != null && qoCDefinitionId != null) {
			setUp(new Integer(qoCIndicatorId), qoCCriterionId, qoCDefinitionId);
		}
	}

	@Override
	public Map<String, String> parameters() {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		final Map<String, String> ret_mapParameter = new HashMap<String, String>();
		// - - - - - CORE OF THE METHOD - - - - -
		ret_mapParameter.put(PARAM_QOC_CRITERION_ID, qoCCriterionId);
		ret_mapParameter.put(PARAM_QOC_DEFINITION_ID, qoCMetricDefinitionId);
		ret_mapParameter.put(PARAM_QOC_INDICATOR_ID, "" + qoCIndicatorId);
		// - - - - - RETURN STATEMENT - - - - -
		return ret_mapParameter;
	}

	@Override
	public String getName() {
		return FUNCTION_NAME;
	}

	// # # # # # PRIVATE METHODS # # # # #

	/**
	 * The methods computes the fields <i>id</i> of the QoC metric value that
	 * have to be inserted. To do it, the method uses the tool functions
	 * <i>computeQoCMetricId</i> and <i>computeQoCMetricValue</i>.
	 *
	 * @param list_qoCMetaData
	 *            The list of the QoC meta-data associated to the context
	 *            observation.
	 * @return The <i>id</i> of of the QoC metric value.
	 */
	private String computeQoCMetricValueId(final List<QoCMetaData> list_qoCMetaData) {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		computeQoCMetricId.setUp(list_qoCMetaData, qoCIndicatorId);
		// - - - - - RETURN STATEMENT - - - - -
		return "" + computeQoCMetricId.exec();
	}

	/**
	 * The methods computes the fields <i>id</i> of the QoC metric value that
	 * have to be inserted. To do it, the method uses the tool functions
	 * <i>computeQoCMetricId</i> and <i>computeQoCMetricValue</i>.
	 *
	 * @param _contextObservation
	 *            The context observation used to compute the value of the QoC.
	 * @param contextObservable
	 *            The context observable associated to the context observation.
	 * @param contextEntity
	 *            The context entity associated to the context observable
	 * @param list_qoCMetaData
	 *            The list of the QoC meta-data associated to the context
	 *            observation.
	 * @return
	 */
	private Double computeQoCMetricValueValue(final ContextObservation<?> _contextObservation,
			final ContextObservable contextObservable, final ContextEntity contextEntity,
			final List<QoCMetaData> list_qoCMetaData) {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		computeQoCMetricValue.setUp(contextEntity.uri.toString(), contextObservable.uri.toString(),
				_contextObservation.date.getTime(), Double.valueOf(_contextObservation.value + ""),
				qoCMetricDefinitionId, list_qoCMetaData);
		// - - - - - RETURN STATEMENT - - - - -
		return (Double) computeQoCMetricValue.exec();
	}

	/**
	 * The method is used to create the new QoC indicator that will be inserted
	 * into the QoC meta-data of all context observations of the context report.
	 *
	 * @param _qoCMetricValueId
	 *            The <i>id</i> of the QoC metric value associated to the QoC
	 *            indicator.
	 * @param _qoCMetricValueValue
	 *            The value of the QoC metric associated to the QoC indicator.
	 * @return The new QoC indicator.
	 */
	private QoCIndicator createNewQoCIndicator(final String _qoCMetricValueId, final Double _qoCMetricValueValue) {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		/*
		 * The QoC indicator returned by the method.
		 */
		QoCIndicator ret_new_QoCIndicator = null;
		// - - - - - CORE OF THE METHOD - - - - -
		for (final Map.Entry<QoCMetricDefinition, IQoCIMFactory> pairs_defintionFactory : map_availableQoCIMFacade
				.entrySet()) {
			if (pairs_defintionFactory.getKey().id().equals(qoCMetricDefinitionId)) {
				ret_new_QoCIndicator = pairs_defintionFactory.getValue().newQoCIndicator(_qoCMetricValueId,
						_qoCMetricValueValue);
				if (!ret_new_QoCIndicator.id().equals(qoCIndicatorId)
						|| !ret_new_QoCIndicator.qoCCriterion().id().equals(qoCCriterionId)) {
					ret_new_QoCIndicator = null;
				}
			}
		}
		try {
			final String message = "AddQoCIndicator.createNewQoCIndicator(Integer, Integer): impossible to compute the new QoC indicator";
			ConstraintChecker.notNull(ret_new_QoCIndicator, message);
		} catch (final ConstraintCheckerException _exception) {
			return new QoCIndicator();
		}
		// - - - - - RETURN STATEMENT - - - - -
		return ret_new_QoCIndicator;
	}

	/**
	 * The method creates a list containing all the QoC meta-data associated to
	 * a context observation.
	 *
	 * @param _contextObservation
	 *            The context observation used to create the list of QoC
	 *            meta-data.
	 * @return The list of QoC meta-data.
	 */
	private List<QoCMetaData> getListQoCMetaData(final ContextObservation<?> _contextObservation) {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		/*
		 * The returned list of the QoC meta-data associated to
		 * <i>_contextObservation</i>.
		 */
		final List<QoCMetaData> ret_listQoCMetaData = new ArrayList<QoCMetaData>();
		// - - - - - CORE OF THE METHOD - - - - -
		for (final QoCIndicator indicator : _contextObservation.list_qoCIndicator) {
			ret_listQoCMetaData.addAll(QoCIMFacade.createListQoCMetaData(indicator));
		}
		// - - - - - RETURN STATEMENT - - - - -
		return ret_listQoCMetaData;
	}

	/**
	 * The method inserts into the QoC meta-data of a context observation a new
	 * QoC indicator. If a same QoC indicator already exists into the list of
	 * QoC meta-data of the context observation, only its associated QoC metric
	 * value is inserted into the existing QoC indicator.
	 *
	 * @param _contextObservation
	 *            The context observation that will receive the QiC indicator.
	 * @param _qoCIndicator
	 *            The QoC indicator inserted into the QoC meta-data of the
	 *            context observation.
	 */
	private void insertNewQoCIndicator(final ContextObservation<?> _contextObservation,
			final QoCIndicator _qoCIndicator) {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		/*
		 * A boolean to indicate if a same QoC indicator as
		 * <i>new_qoCIndicator</i> already exists.
		 */
		Boolean found_qoCIndicator = false;
		// - - - - - CORE OF THE METHOD - - - - -
		for (final QoCIndicator loop_qoCIndicator : _contextObservation.list_qoCIndicator) {
			if (loop_qoCIndicator.equals(_qoCIndicator)) {
				QoCIMFacade.addQoCMetricValue(_qoCIndicator.list_qoCMetricValue.getFirst(), loop_qoCIndicator);
				found_qoCIndicator = true;
			}
		}
		if (!found_qoCIndicator) {
			_contextObservation.list_qoCIndicator.add(_qoCIndicator);
		}
	}
}
