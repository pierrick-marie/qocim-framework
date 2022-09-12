/**
 * This file is part of the QoCIM middleware.
 * <p>
 * Copyright (C) 2014 IRIT, Télécom SudParis
 * <p>
 * The QoCIM software is free software: you can redistribute it and/or modify
 * It under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * The QoCIM software platform is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * <p>
 * See the GNU Lesser General Public License
 * for more details: http://www.gnu.org/licenses
 * <p>
 * Initial developer(s): Pierrick MARIE
 * Contributor(s):
 */
package qocim.qocmanagement.functions.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import qocim.datamodel.QoCIMFacade;
import qocim.datamodel.QoCIndicator;
import qocim.datamodel.QoCMetricDefinition;
import qocim.datamodel.QoCMetricValue;
import qocim.datamodel.information.QInformation;
import qocim.datamodel.utils.ConstraintChecker;
import qocim.datamodel.utils.ConstraintCheckerException;
import qocim.datamodel.utils.IQoCIMFactory;
import qocim.datamodel.utils.QoCIMLogger;
import qocim.qocmanagement.functions.IQoCManagementFunction;
import qocim.qocmanagement.functions.utils.EQoCManagementFunction;
import qocim.qocmanagement.functions.utils.LogMessages;
import qocim.tool.functions.impl.ComputeQoCMetricValue;
import qocim.tool.functions.impl.CurrentDate;

/**
 * UpdateQoCIndicator updates one value of one QoC indicator of all context
 * observations of a context report. import
 * qocim.tool.functions.impl.ComputeQoCMetricValue; import
 * qocim.tool.functions.impl.CurrentDate;The function uses the tool function
 * <i>computeQoCMetricValue</i> to compute the new value of the QoC and the tool
 * function <i>currentDate</i> to set the field modification date of the class
 * QoCMetricValue.
 *
 * @author Pierrick MARIE
 */
public class UpdateQoCMetricValue implements IQoCManagementFunction {

    // # # # # # CONSTANTS # # # # #

    /**
     * The name of the function.
     */
    public static final String FUNCTION_NAME = EQoCManagementFunction.UPDATEQOCMETRICVALUE.toString();
    /**
     * The name of the parameter 1.
     */
    public static final String PARAM_QOC_METRIC_VALUE_ID = "qoc_metric_value_id";
    /**
     * The name of the parameter 2.
     */
    public static final String PARAM_QOC_INDICATOR_ID = "qoc_indicator_id";

    // # # # # # PRIVATE VARIABLES # # # # #

    /**
     * Verifies if the configuration of the function has been done.
     */
    private Boolean setUpIsDone;
    /**
     * The <i>id</i> of the QoC metric value that have to be updated.
     */
    private String qoCMetricValueId;
    /**
     * The <i>id</i> of the QoC indicator that reference the QoC metric value
     * that is going to be updated.
     */
    private Integer qoCIndicatorId;
    /**
     * The tool function <i>computeQoCMetricValue</i> used to compute the new
     * value of the QoC.
     */
    private final ComputeQoCMetricValue computeQoCMetricValue;
    /**
     * The tool function <i>currentDate</i> used to get the current date.
     */
    private final CurrentDate currentDate;

    // # # # # # CONSTRUCTORS # # # # #

    public UpdateQoCMetricValue(final Map<QoCMetricDefinition, IQoCIMFactory> _map_availableQoCIMFacade) {
        // - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
        try {
            String message = "UpdateQoCMetricValue.constructor(Map<String, IQoCIMFacade>): the argument _list_availableQoCIndicator is null";
            ConstraintChecker.notNull(_map_availableQoCIMFacade, message);
            message = "UpdateQoCMetricValue.constructor(Map<String, IQoCIMFacade>): the argument _list_availableQoCIndicator is empty";
            ConstraintChecker.doubleIsBetweenBounds(new Double(_map_availableQoCIMFacade.size()), 0.0, Double.MAX_VALUE,
                    message);
        } catch (final ConstraintCheckerException _exception) {
            final String message = "UpdateQoCMetricValue.constructor(Map<String, IQoCIMFacade>): Fatal error.";
            QoCIMLogger.logger.log(Level.SEVERE, message, _exception);
            System.exit(-1);
        }
        // - - - - - INITIALIZE THE VARIABLES - - - - -
        setUpIsDone = false;
        computeQoCMetricValue = new ComputeQoCMetricValue(
                new ArrayList<QoCMetricDefinition>(_map_availableQoCIMFacade.keySet()));
        currentDate = new CurrentDate();
        QoCIMLogger.functionLog(FUNCTION_NAME, LogMessages.NEW_FUNCTION_INSTANCE);
    }

    // # # # # # PUBLIC METHODS # # # # #

    /**
     * The method executes the function <i>updateQoCIndicator</i>. The method
     * browses all the context observations of the context report and then,
     * calls the private method "updateQoCIndicator".
     */
    @Override
    public QInformation exec(final QInformation information) {
        // - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
        try {
            String message = "UpdateQoCMetricValueexec() method .setup(Integer, Integer) have to be called before.";
            ConstraintChecker.assertTrue(setUpIsDone, message);
            message = "UpdateQoCMetricValue.exec(ContextReport): the argument information is null.";
            ConstraintChecker.notNull(information, message);
        } catch (final ConstraintCheckerException e) {
            return information;
        }
        // - - - - - CORE OF THE METHOD - - - - -
        QoCIMLogger.functionLog(FUNCTION_NAME, LogMessages.BEGIN_EXECUTION_FUNCTION);
        updateQoCIndicator(information);
        QoCIMLogger.functionLog(FUNCTION_NAME, LogMessages.END_EXECUTION_FUNCTION);
        // - - - - - RETURN STATEMENT - - - - -
        return information;
    }

    /**
     * The method initializes the arguments of the function
     * <i>updateQoCIndicator</i>.
     *
     * @param _qoCMetricValueId
     *            The <i>id</i> of the QoC metric value that have to be updated.
     * @param _qoCIndicatorId
     *            The <i>id</i> of the QoC indicator that references the QoC
     *            metric value that is going to be updated.
     * @return <b>this</b>
     */
    public UpdateQoCMetricValue setUp(final String _qoCMetricValueId, final Integer _qoCIndicatorId) {
        // - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
        try {
            String message = "UpdateQoCMetricValue.setup(Integer, Integer): the argument _qoCIndicatorId is null";
            ConstraintChecker.notNull(_qoCIndicatorId, message);
            message = "RemoveQoCMetricValue.setup(Integer, Integer): the argument _qoCMetricDefinitionId is null";
            ConstraintChecker.notNull(_qoCMetricValueId, message);
        } catch (final ConstraintCheckerException e) {
            setUpIsDone = false;
            return this;
        }
        // - - - - - INITIALIZE THE VARIABLES - - - - -
        qoCMetricValueId = _qoCMetricValueId;
        qoCIndicatorId = _qoCIndicatorId;
        setUpIsDone = true;
        QoCIMLogger.functionLog(FUNCTION_NAME, LogMessages.SETUP_FUNCTION);
        // - - - - - RETURN STATEMENT - - - - -
        return this;
    }

    @Override
    public void setParameters(final Map<String, String> _map_paramaters) {
        // - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
        try {
            final String message = "UpdateQoCMetricValue.setParameters(Map<String, String>): the argument _map_paramaters is null";
            ConstraintChecker.notNull(_map_paramaters, message);
        } catch (final ConstraintCheckerException e) {
            return;
        }
        // - - - - - INITIALIZE THE VARIABLES - - - - -
        final String qoCMetricValueId = _map_paramaters.get(PARAM_QOC_METRIC_VALUE_ID);
        final String qoCIndicatorId = _map_paramaters.get(PARAM_QOC_INDICATOR_ID);
        // - - - - - CORE OF THE METHOD - - - - -
        if (qoCIndicatorId != null && qoCMetricValueId != null) {
            setUp(qoCMetricValueId, new Integer(qoCIndicatorId));
        }
    }

    @Override
    public Map<String, String> parameters() {
        // - - - - - INITIALIZE THE VARIABLES - - - - -
        final Map<String, String> ret_mapParameter = new HashMap<String, String>();
        // - - - - - CORE OF THE METHOD - - - - -
        ret_mapParameter.put(PARAM_QOC_METRIC_VALUE_ID, "" + qoCMetricValueId);
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
     * The method searches the appropriate QoC metric value that have to be
     * updated and then calls the private method <i>updateQoCMetricValues</i>.
     *
     * @param information
     *            The context observation where the QoCMetricValue have to be
     *            updated.
     */
    private void updateQoCIndicator(final QInformation information) {
        // - - - - - INITIALIZE THE VARIABLES - - - - -
        /*
         * The list of the QoC indicator of the context observation. This
         * attribute is used to wrap the field <i>qoCIndicators</i> of the class
         * <b>ContextObservation</b> and correctly use the method
         * <i>searchFirstQoCIndicator</i> of the class <b>QoCIMFacade</b>.
         */
        final Collection<QoCIndicator> list_qoCIndicator = information.indicators();
        /*
         * The QoC indicator that contains the QoC metric value that have to be
         * updated.
         */
        final QoCIndicator found_qoCIndicator = QoCIMFacade
                .searchFirstQoCIndicator(new ArrayList<QoCIndicator>(list_qoCIndicator), qoCIndicatorId);
        /*
         * The old QoCMetricValue that will be updated.
         */
        QoCMetricValue old_qoCMetricValue = null;
        // - - - - - CORE OF THE METHOD - - - - -
        if (found_qoCIndicator != null) {
            old_qoCMetricValue = QoCIMFacade.searchFirstQoCMetricValue(found_qoCIndicator, qoCMetricValueId);
            if (old_qoCMetricValue != null) {
                updateQoCMetricValue(information, found_qoCIndicator, old_qoCMetricValue);
            }
        }
    }

    /**
     * The method computes the new value of the QoC and set the private fields
     * <i>value</i> and <i>modificationDate</i> of the QoC metric value that
     * have to be update.
     *
     * @param information
     *            The context observation that contains the QoC metric value
     *            that is updated.
     * @param _qoCIndicator
     *            The QoC indicator that contains the QoC metric value that will
     *            be update.
     * @param _old_QoCMetricValue
     *            The old QoC metric value that will be update.
     */
    private void updateQoCMetricValue(final QInformation information, final QoCIndicator _qoCIndicator,
                                      final QoCMetricValue _old_QoCMetricValue) {
        // - - - - - INITIALIZE THE VARIABLES - - - - -
        /*
         * The new value of the QoC metric value.
         */
        Double new_qoCMetricValue;
        // - - - - - CORE OF THE METHOD - - - - -
        computeQoCMetricValue.setUp(information, _old_QoCMetricValue.qoCMetricDefinition().id(),
                QoCIMFacade.createListQoCMetaData(_qoCIndicator));
        new_qoCMetricValue = (Double) computeQoCMetricValue.exec();
        _old_QoCMetricValue.value(new_qoCMetricValue);
        _old_QoCMetricValue.modificationDate((Date) currentDate.exec());
    }
}
