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
import java.util.HashMap;
import java.util.Map;

import qocim.datamodel.QoCIMFacade;
import qocim.datamodel.QoCIndicator;
import qocim.datamodel.QoCMetricValue;
import qocim.datamodel.information.QInformation;
import qocim.datamodel.utils.ConstraintChecker;
import qocim.datamodel.utils.ConstraintCheckerException;
import qocim.datamodel.utils.QoCIMLogger;
import qocim.qocmanagement.functions.IQoCManagementFunction;
import qocim.qocmanagement.functions.utils.EQoCManagementFunction;
import qocim.qocmanagement.functions.utils.LogMessages;

/**
 * RemoveQoCIndicator identifies and removes one QoC value of all the context
 * observations contained into a context report. The function search the right
 * QoC metric value and use the QoCIM facade to remove it.
 *
 * @author Pierrick MARIE
 */
public class RemoveQoCMetricValue implements IQoCManagementFunction {

    // # # # # # CONSTANTS # # # # #

    /**
     * The name of the function.
     */
    public static final String FUNCTION_NAME = EQoCManagementFunction.REMOVEQOCMETRICVALUE.toString();
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
     * The <i>id</i> of the QoC metric value that have to be removed.
     */
    private String qoCMetricValueId;
    /**
     * The <i>id</i> of the QoC indicator used to identify the QoC metric value
     * that will be removed.
     */
    private Integer qoCIndicatorId;

    // # # # # # CONSTRUCTORS # # # # #

    public RemoveQoCMetricValue() {
        // - - - - - INITIALIZE THE VARIABLES - - - - -
        setUpIsDone = false;
        QoCIMLogger.functionLog(FUNCTION_NAME, LogMessages.NEW_FUNCTION_INSTANCE);
    }

    // # # # # # PUBLIC METHODS # # # # #

    /**
     * The method executes the function <i>removeQoCIndicator</i>. The method
     * browses the context observations of the context report and calls the
     * private method <i>removeQoCIndicator</i>.
     */
    @Override
    public QInformation exec(final QInformation information) {
        // - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
        try {
            String message = "RemoveQoCMetricValueexec() method .setup(Integer, Integer) have to be called before.";
            ConstraintChecker.assertTrue(setUpIsDone, message);
            message = "RemoveQoCMetricValue.exec(ContextReport): the argument information is null.";
            ConstraintChecker.notNull(information, message);
        } catch (final ConstraintCheckerException e) {
            return information;
        }
        // - - - - - CORE OF THE METHOD - - - - -
        QoCIMLogger.functionLog(FUNCTION_NAME, LogMessages.BEGIN_EXECUTION_FUNCTION);
        removeQoCIndicator(information);
        QoCIMLogger.functionLog(FUNCTION_NAME, LogMessages.END_EXECUTION_FUNCTION);
        // - - - - - RETURN STATEMENT - - - - -
        return information;
    }

    /**
     * The methods initializes the arguments of the function
     * <i>removeQoCIndicator</i>.
     *
     * @param _qoCMetricValueId
     *            The <i>id</i> of the QoC metric value that have to be removed.
     * @param _qoCIndicatorId
     *            The <i>id</i> of the QoC indicator used to identify the QoC
     *            metric value that will be removed.
     * @return <b>this</b>
     */
    public RemoveQoCMetricValue setUp(final String _qoCMetricValueId, final Integer _qoCIndicatorId) {
        // - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
        try {
            String message = "RemoveQoCMetricValue.setup(Integer, Integer): the argument _qoCIndicatorId is null";
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
     * The method searches the right QoC metric value that have to be removed
     * from the list of the QoC meta-data of a context observation. Then, the
     * QoC metric value is removed. If the QoC indicator that contained the QoC
     * metric value does not contain any more QoC metric value
     * (<i>QoCIndicator.getListQoCMetricValue().isEmpty()</i>), the QoC
     * indicator is also removed from the QoC meta-data of the context
     * observation.
     *
     * @param information
     *            The context informaiton that will be updated.
     */
    private void removeQoCIndicator(final QInformation information) {
        // - - - - - INITIALIZE THE VARIABLES - - - - -
        /*
         * The QoC metric value that will be removed.
         */
        QoCMetricValue found_qoCMetricValue;
        /*
         * The list of the QoC indicator used as QoC meta-data for the
         * <i>_contextObservation<i>. This field is used as a wrapper to
         * correctly use the method <i>searchFirstQoCIndicator</i> of the class
         * <b>QoCIMFacade</b>.
         */
        final Collection<QoCIndicator> list_qoCIndicator = information.indicators();
        /*
         * The QoC indicator of that contains the QoC metric value that will be
         * removed.
         */
        final QoCIndicator found_qoCIndicator = QoCIMFacade
                .searchFirstQoCIndicator(new ArrayList<QoCIndicator>(list_qoCIndicator), qoCIndicatorId);
        // - - - - - CORE OF THE METHOD - - - - -
        if (found_qoCIndicator != null) {
            found_qoCMetricValue = QoCIMFacade.searchFirstQoCMetricValue(found_qoCIndicator, qoCMetricValueId);
            if (found_qoCMetricValue != null) {
                QoCIMFacade.removeQoCMetricValue(found_qoCMetricValue, found_qoCIndicator);
                QoCIMFacade.removeQoCMetricValue(found_qoCMetricValue, found_qoCMetricValue.qoCMetricDefinition());
            }
            if (found_qoCIndicator.list_qoCMetricValue.isEmpty()) {
                information.indicators().remove(found_qoCIndicator);
            }
        }
    }
}
