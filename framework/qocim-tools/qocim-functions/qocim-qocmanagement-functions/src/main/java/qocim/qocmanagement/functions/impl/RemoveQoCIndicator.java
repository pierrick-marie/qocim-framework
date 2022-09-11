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
import qocim.datamodel.information.QInformation;
import qocim.datamodel.utils.ConstraintChecker;
import qocim.datamodel.utils.ConstraintCheckerException;
import qocim.datamodel.utils.QoCIMLogger;
import qocim.qocmanagement.functions.IQoCManagementFunction;
import qocim.qocmanagement.functions.utils.EQoCManagementFunction;
import qocim.qocmanagement.functions.utils.LogMessages;

/**
 * RemoveQoCIndicator identifies and removes one QoC indicator of all the
 * context observations contained into a context report.
 *
 * @author Pierrick MARIE
 */
public class RemoveQoCIndicator implements IQoCManagementFunction {

    // # # # # # CONSTANTS # # # # #

    /**
     * The name of the function.
     */
    public static final String FUNCTION_NAME = EQoCManagementFunction.REMOVEQOCINDICATOR.toString();
    /**
     * The name of the parameter 1.
     */
    public static final String PARAM_QOC_INDICATOR_ID = "qoc_indicator_id";

    // # # # # # PRIVATE VARIABLES # # # # #

    /**
     * Verifies if the configuration of the function has been done.
     */
    private Boolean setUpIsDone;
    /**
     * The <i>id</i> of the QoC indicator used to identify the QoC metric value
     * that will be removed.
     */
    private Integer qoCIndicatorId;

    // # # # # # CONSTRUCTORS # # # # #

    public RemoveQoCIndicator() {
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

    @Override
    public String getName() {
        return FUNCTION_NAME;
    }

    @Override
    public Map<String, String> parameters() {
        // - - - - - INITIALIZE THE VARIABLES - - - - -
        final Map<String, String> ret_mapParameter = new HashMap<String, String>();
        // - - - - - CORE OF THE METHOD - - - - -
        ret_mapParameter.put(PARAM_QOC_INDICATOR_ID, "" + qoCIndicatorId);
        // - - - - - RETURN STATEMENT - - - - -
        return ret_mapParameter;
    }

    /**
     * The method searches the right QoC indicator and remove it from the list
     * of QoC meta-data associated to a context observation.
     *
     * @param information
     *            The <b>ContextObservation</b> that will be updated.
     */
    private void removeQoCIndicator(final QInformation information) {
        // - - - - - INITIALIZE THE VARIABLES - - - - -
        /*
         * The list of the QoC indicator used as QoC meta-data for the
         * <i>information<i>. This field is used as a wrapper to
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
            information.indicators().remove(found_qoCIndicator);
        }
    }

    @Override
    public void setParameters(final Map<String, String> _map_paramaters) {
        // - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
        try {
            final String message = "RemoveQoCIndicator.setParameters(Map<String, String>): the argument _map_paramaters is null";
            ConstraintChecker.notNull(_map_paramaters, message);
        } catch (final ConstraintCheckerException e) {
            return;
        }
        // - - - - - INITIALIZE THE VARIABLES - - - - -
        final String qoCIndicatorId = _map_paramaters.get(PARAM_QOC_INDICATOR_ID);
        // - - - - - CORE OF THE METHOD - - - - -
        if (qoCIndicatorId != null) {
            setUp(new Integer(qoCIndicatorId));
        }
    }

    // # # # # # PRIVATE METHODS # # # # #

    /**
     * The methods initializes the arguments of the function
     * <i>removeQoCIndicator</i>.
     *
     * @param _qoCIndicatorId
     *            The <i>id</i> of the QoC indicator used to identify the QoC
     *            metric value that will be removed.
     * @return <b>this</b>
     */
    public RemoveQoCIndicator setUp(final Integer _qoCIndicatorId) {
        // - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
        try {
            final String message = "RemoveQoCIndicator.setup(Integer): the argument _qoCIndicatorId is null";
            ConstraintChecker.notNull(_qoCIndicatorId, message);
        } catch (final ConstraintCheckerException e) {
            setUpIsDone = false;
            return this;
        }
        // - - - - - INITIALIZE THE VARIABLES - - - - -
        qoCIndicatorId = _qoCIndicatorId;
        setUpIsDone = true;
        QoCIMLogger.functionLog(FUNCTION_NAME, LogMessages.SETUP_FUNCTION);
        // - - - - - RETURN STATEMENT - - - - -
        return this;
    }
}
