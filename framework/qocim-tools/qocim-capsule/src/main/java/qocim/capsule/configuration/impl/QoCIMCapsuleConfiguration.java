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
package qocim.capsule.configuration.impl;

import java.util.Iterator;

import java.util.LinkedList;

import qocim.capsule.configuration.IQoCIMCapsuleConfiguration;
import qocim.capsule.configuration.QoCIMCapsuleFunction;
import qocim.datamodel.utils.ConstraintChecker;
import qocim.datamodel.utils.ConstraintCheckerException;
import qocim.datamodel.utils.QoCIMPerformanceLogLevel;

/**
 * The QoCIMCapsuleConfiguration class implements IQoCIMCapsuleConfiguration.
 *
 * The class uses represents the configuration of the functions with the
 * QoCIMCapsuleFunction class. This class is used as a singleton.
 *
 * @author Pierrick MARIE
 */
public class QoCIMCapsuleConfiguration implements IQoCIMCapsuleConfiguration {

    // # # # # # PRIVATE VARIABLES # # # # #

    /**
     * The list of function executed by the capsule.
     */
    private LinkedList<QoCIMCapsuleFunction> list_function;
    /**
     * The subscription filter used by the capsule.
     */
    private String subscriptionFilter;
    /**
     * The advertisement filter used by the capsule.
     */
    private String advertisementFilter;
    /**
     * The current log level notified by the capsule.
     */
    private QoCIMPerformanceLogLevel logLevel;

    // # # # # # CONSTRUCTORS # # # # #

    public QoCIMCapsuleConfiguration() {
	// - - - - - CORE OF THE METHOD - - - - -
	list_function = new LinkedList<QoCIMCapsuleFunction>();
	subscriptionFilter = "";
	advertisementFilter = "";
	logLevel = QoCIMPerformanceLogLevel.NONE;
    }

    public QoCIMCapsuleConfiguration(final QoCIMCapsuleConfiguration _configuration) {
	// - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
	try {
	    final String message = "QoCIMCapsuleConfiguration.constructor(QoCIMCapsuleConfiguration): the argument _configuration is null.";
	    ConstraintChecker.notNull(_configuration, message);
	} catch (final ConstraintCheckerException e) {
	    list_function = new LinkedList<QoCIMCapsuleFunction>();
	    subscriptionFilter = "";
	    advertisementFilter = "";
	    logLevel = QoCIMPerformanceLogLevel.NONE;
	    return;
	}
	// - - - - - CORE OF THE METHOD - - - - -
	list_function = _configuration.orderedListFunction();
	subscriptionFilter = _configuration.subscriptionFilter;
	advertisementFilter = _configuration.advertisementFilter;
	logLevel = _configuration.logLevel;
    }

    // # # # # # PUBLIC METHODS # # # # #

    @Override
    public Boolean addFunction(final QoCIMCapsuleFunction _function) {
	// - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
	try {
	    final String message = "QoCIMCapsuleConfiguration.addFunction(QoCIMCapsuleFunction): the argument _function is null.";
	    ConstraintChecker.notNull(_function, message);
	} catch (final ConstraintCheckerException e) {
	    return false;
	}
	// - - - - - CORE OF THE METHOD - - - - -
	if (!list_function.isEmpty()) {
	    list_function.getLast().setNextFunction(_function);
	}
	// - - - - - RETURN STATEMENT - - - - -
	return list_function.add(_function);
    }

    @Override
    public String advertisementFilter() {
	// - - - - - RETURN STATEMENT - - - - -
	return advertisementFilter;
    }

    @Override
    public QoCIMPerformanceLogLevel logLevel() {
	// - - - - - RETURN STATEMENT - - - - -
	return logLevel;
    }

    @Override
    public LinkedList<QoCIMCapsuleFunction> orderedListFunction() {
	// - - - - - INITIALIZE THE VARIABLES - - - - -
	/*
	 * The ordered list of functions returned by the method.
	 */
	final LinkedList<QoCIMCapsuleFunction> ret_listFunction = new LinkedList<QoCIMCapsuleFunction>();
	// - - - - - CORE OF THE METHOD - - - - -
	for (final QoCIMCapsuleFunction loop_function : list_function) {
	    ret_listFunction.add(loop_function);
	}
	// - - - - - RETURN STATEMENT - - - - -
	return ret_listFunction;
    }

    @Override
    public Boolean removeFunction(final QoCIMCapsuleFunction _function) {
	// - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
	try {
	    final String message = "QoCIMCapsuleConfiguration.removeFunction(QoCIMCapsuleFunction): the argument _function is null.";
	    ConstraintChecker.notNull(_function, message);
	} catch (final ConstraintCheckerException e) {
	    return false;
	}
	// - - - - - INITIALIZE THE VARIABLES - - - - -
	/*
	 * An iterator to analyze and compared all the functions.
	 */
	final Iterator<QoCIMCapsuleFunction> iterator_function = list_function.iterator();
	/*
	 * The current function returned by the iterator.
	 */
	QoCIMCapsuleFunction loop_function = null;
	// - - - - - CORE OF THE METHOD - - - - -
	while (iterator_function.hasNext()) {
	    loop_function = iterator_function.next();
	    /*
	     * Looking for the function that reference <i>_function</i> and
	     * modify it.
	     */
	    if (loop_function.nextFunction().equals(_function)) {
		loop_function.setNextFunction(_function.nextFunction());
		_function.setNextFunction(null);
	    }
	}
	// - - - - - RETURN STATEMENT - - - - -
	return list_function.remove(_function);
    }

    @Override
    public IQoCIMCapsuleConfiguration setAdvertisementFilter(final String _advertisementFilter) {
	// - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
	try {
	    final String message = "QoCIMCapsuleConfiguration.setAdvertisementFilter(String): the argument _advertisementFilter is null.";
	    ConstraintChecker.notNull(_advertisementFilter, message);
	} catch (final ConstraintCheckerException e) {
	    return this;
	}
	// - - - - - CORE OF THE METHOD - - - - -
	advertisementFilter = _advertisementFilter;
	// - - - - - RETURN STATEMENT - - - - -
	return this;
    }

    @Override
    public IQoCIMCapsuleConfiguration setLogLevel(final QoCIMPerformanceLogLevel _logLevel) {
	// - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
	try {
	    final String message = "QoCIMCapsuleConfiguration.setLogLevel(QoCIMPerformanceLogLevel): the argument _logLevel is null.";
	    ConstraintChecker.notNull(_logLevel, message);
	} catch (final ConstraintCheckerException e) {
	    return this;
	}
	// - - - - - CORE OF THE METHOD - - - - -
	logLevel = _logLevel;
	// - - - - - RETURN STATEMENT - - - - -
	return this;
    }

    @Override
    public IQoCIMCapsuleConfiguration setSubscriptionFilter(final String _subscriptionFilter) {
	// - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
	try {
	    final String message = "QoCIMCapsuleConfiguration.setSubscriptionFilter(String): the argument _subscriptionFilter is null.";
	    ConstraintChecker.notNull(_subscriptionFilter, message);
	} catch (final ConstraintCheckerException e) {
	    return this;
	}
	// - - - - - CORE OF THE METHOD - - - - -
	subscriptionFilter = _subscriptionFilter;
	// - - - - - RETURN STATEMENT - - - - -
	return this;
    }

    @Override
    public String subscriptionFilter() {
	// - - - - - RETURN STATEMENT - - - - -
	return subscriptionFilter;
    }
}
