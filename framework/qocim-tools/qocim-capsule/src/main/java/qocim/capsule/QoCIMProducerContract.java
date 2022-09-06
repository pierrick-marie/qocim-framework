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


import javax.xml.transform.TransformerException;

import mucontext.api.ContextCapsule;
import mucontext.datamodel.contextcontract.BasicContextProducerContract;
import mudebs.common.MuDEBSException;
import mudebs.common.algorithms.OperationalMode;
import mudebs.common.filters.XACMLPolicy;
import qocim.datamodel.utils.ConstraintChecker;
import qocim.datamodel.utils.ConstraintCheckerException;
import qocim.datamodel.utils.QoCIMLogger;

/**
 * This class represents a context producer contract used by a QoCIM capsule.
 * The type of contracts handled by this class is
 * <b>BasicContextProducerContract</b>. This class provide a solution to change,
 * at runtime, the advertisement filter used in the contract. Concretely when
 * the advertisement filter is set, the old contract is replaced by a new one.
 *
 * @author Pierrick MARIE
 */
public class QoCIMProducerContract {

    // # # # # # PRIVATE VARIABLES # # # # #

    /**
     * Determine if the contract is local or global.
     */
    private final OperationalMode operationalMode;
    /**
     * The XACML policy associated to the contract.
     */
    private final XACMLPolicy xacmlPolicy;
    /**
     * The advertisement filter used by the contract.
     */
    private String advertisementFilter;

    // # # # # # CONSTRUCTORS # # # # #

    public QoCIMProducerContract(final OperationalMode _operationalMode, final XACMLPolicy _xacmlPolicy) {
	// - - - - - CORE OF THE METHOD - - - - -
	operationalMode = _operationalMode;
	xacmlPolicy = _xacmlPolicy;
	advertisementFilter = "";
    }

    // # # # # # PUBLIC METHODS # # # # #

    /**
     * This method sets the advertisement filter associated to the context
     * producer contract.
     *
     * @param _filter
     *            The new advertisement filter.
     * @return this.
     */
    public void setAdvertisementFilter(final String _contractId, final String _filter, final ContextCapsule _contextCapsule) {
	// - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
	try {
	    String message = "QoCIMProducerContract.setAdvertisementFilte(String, String, ContextCapsule): the argument _filter is null.";
	    ConstraintChecker.notNull(_filter, message);
	    message = "QoCIMProducerContract.setAdvertisementFilte(String, String, ContextCapsule): the argument _contextCapsule is null.";
	    ConstraintChecker.notNull(_contextCapsule, message);
	} catch (final ConstraintCheckerException e) {
	    return;
	}
	// - - - - - CORE OF THE METHOD - - - - -
	/*
	 * Check if the new advertisement filter changed.
	 */
	if (!advertisementFilter.equals(_filter)) {
	    advertisementFilter = _filter;
	    try {
		QoCIMLogger.event("QoCIMConsumerContract: setting advertisement filter: ");
		/*
		 * Removing old producer contracts.
		 */
		_contextCapsule.unsetAllContextProducerContracts();
		/*
		 * Creating a new producer contract with the new advertisement
		 * filter.
		 */
		_contextCapsule.setContextProducerContract(new BasicContextProducerContract(_contractId, operationalMode, advertisementFilter, xacmlPolicy));
	    } catch (final MuDEBSException _exception) {
		final String message = "QoCIMConsumerContract.setAdvertismentFilter(String): Fail to set the context consumer contract!";
		QoCIMLogger.logger.log(Level.WARNING, message, _exception);
	    } catch (final TransformerException _exception) {
		final String message = "QoCIMConsumerContract.setAdvertismentFilter(String): Fail to set the context consumer contract!";
		QoCIMLogger.logger.log(Level.WARNING, message, _exception);
	    }
	}
    }
}
