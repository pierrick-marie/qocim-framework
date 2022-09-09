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
package qocim.capsule.configuration;

import java.util.LinkedList;

import qocim.datamodel.utils.QoCIMPerformanceLogLevel;


/**
 * The IQoCIMCapsuleConfiguration interface defines a set methods used to
 * configure the execution a QoCIM-context-capsule.
 *
 * A QoCIM-capsule executes a set of functions to modify context data flows. The
 * functions are executed sequentially. So, the order of the functions impacts
 * the resulting data flow produced by the capsule.
 *
 * The interface controls the functions (the order and their parameters)
 * executed by the capsule, the log levels, the subscription and advertisement
 * filters of the capsule.
 *
 * @author Pierrick MARIE
 */
public interface IQoCIMCapsuleConfiguration {

    /**
     * This method adds into the list of function executed by the capsule a new
     * QoC management function. The function is added at the end of the list and
     * will be executed after all previously added. There is no distinction
     * between the QoC management function and the context data flow management
     * function. All of them are handled in the same stack of functions.
     *
     * @param _function
     *            The function to add.
     * @param _map_parameter
     *            The parameters of the function.
     * @return True if the has been correctly added.
     */
    Boolean addFunction(QoCIMCapsuleFunction _function);

    /**
     * This method removes a <b>QoCIMCapsuleFunction</b> from the list of
     * functions executed by the capsule.
     *
     * @param _function
     *            The function to remove.
     * @return True if the <i>_function</i> has been correctly removed.
     */
    Boolean removeFunction(QoCIMCapsuleFunction _function);

    /**
     * @return The advertisement filter.
     */
    String advertisementFilter();

    /**
     * @return The current log level.
     */
    QoCIMPerformanceLogLevel logLevel();

    /**
     * @return A map of functions executed by the capsule. All the functions are
     *         identified with a unique number.
     */
    LinkedList<QoCIMCapsuleFunction> orderedListFunction();

    /**
     * @return The subscription filter.
     */
    String subscriptionFilter();

    /**
     * This method modifies the advertisement filter of the capsule.
     *
     * @param _advertisementFilter
     *            The new advertisement filter used by the capsule.
     * @return this.
     */
    IQoCIMCapsuleConfiguration setAdvertisementFilter(String _advertisementFilter);

    /**
     * This method controls the detail level of information logged by the
     * capsule.
     *
     * @param _logLevel
     *            The QoCIMCapsulelogLevel used to determine the type
     *            information that will be logged.
     * @return this.
     */
    IQoCIMCapsuleConfiguration setLogLevel(QoCIMPerformanceLogLevel _logLevel);

    /**
     * This method modifies the subscription filter of the capsule.
     *
     * @param _advertisementFilter
     *            The new subscription filter used by the capsule.
     * @return this.
     */
    IQoCIMCapsuleConfiguration setSubscriptionFilter(String _subscriptionFilter);
}
