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
package qocim.capsule;

import java.util.logging.Level;

import mucontext.api.ContextCapsule;
import mucontext.api.ContextConsumer;
import mucontext.datamodel.contextcontract.BasicContextConsumerContract;
import mudebs.common.MuDEBSException;
import mudebs.common.algorithms.OperationalMode;
import mudebs.common.algorithms.routing.ABACInformation;
import qocim.datamodel.utils.ConstraintChecker;
import qocim.datamodel.utils.ConstraintCheckerException;
import qocim.datamodel.utils.QoCIMLogger;

/**
 * This class represents a context consumer contract used by a QoCIM capsule.
 * The type of contracts handled by this class is
 * <b>BasicContextConsumerContract</b>. This class provide a solution to change,
 * at runtime, the subscription filter used in the contract. Concretely when the
 * subscription filter is set, the old contract is replaced by a new one.
 *
 * @author Pierrick MARIE
 */
public class QoCIMConsumerContract {

    // # # # # # PRIVATE VARIABLES # # # # #

    /**
     * Determine if the contract is local or global.
     */
    private final OperationalMode operationalMode;
    /**
     * The ABAC information associated to the contract.
     */
    private final ABACInformation abacInformation;
    /**
     * The subscription filter used by the contract.
     */
    private String subscriptionFilter;

    // # # # # # CONSTRUCTORS # # # # #

    public QoCIMConsumerContract(final OperationalMode _operationalMode, final ABACInformation _abacInformation) {
	// - - - - - CORE OF THE METHOD - - - - -
	operationalMode = _operationalMode;
	abacInformation = _abacInformation;
	subscriptionFilter = "";
    }

    // # # # # # PUBLIC METHODS # # # # #

    /**
     * This method sets the subscription filter associated to the context
     * consumer contract.
     *
     * @param _filter
     *            The new subscription filter.
     * @return this.
     */
    public void setSubscriptionFilter(final String _contractId, final String _filter, final ContextCapsule _contextCapsule,
	    final ContextConsumer _contextConsumer) {
	// - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
	try {
	    String message = "QoCIMConsumerContract.setSubscriptionFilter(String, String, ContextCapsule, ContextConsumer): the argument _filter is null.";
	    ConstraintChecker.notNull(_filter, message);
	    message = "QoCIMConsumerContract.setSubscriptionFilter(String, String, ContextCapsule, ContextConsumer): the argument _contextCapsule is null.";
	    ConstraintChecker.notNull(_contextCapsule, message);
	    message = "QoCIMConsumerContract.setSubscriptionFilter(String, String, ContextCapsule, ContextConsumer): the argument _contextConsumer is null.";
	    ConstraintChecker.notNull(_contextConsumer, message);
	} catch (final ConstraintCheckerException e) {
	    return;
	}
	// - - - - - CORE OF THE METHOD - - - - -
	/*
	 * Check if the new subscription filter changed.
	 */
	if (!subscriptionFilter.equals(_filter)) {
	    subscriptionFilter = _filter;
	    try {
		QoCIMLogger.event("QoCIMConsumerContract: setting advertisement filter");
		/*
		 * Creating a new consumer contract with the new subscription
		 * filter.
		 */
		_contextCapsule.setContextConsumerContract(new BasicContextConsumerContract(_contractId, operationalMode, subscriptionFilter, abacInformation),
			_contextConsumer);
	    } catch (final MuDEBSException _exception) {
		final String message = "QoCIMConsumerContract.setSubscriptionFilter(String): Fail to set the context consumer contract!";
		QoCIMLogger.logger.log(Level.WARNING, message, _exception);
	    }
	}
    }
}
