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
package qocim.capsule.impl;

import java.util.Arrays;
import java.util.Iterator;
import java.util.logging.Level;

import mucontext.api.ContextCapsule;
import mucontext.api.ContextConsumer;
import mucontext.contextcapsule.BasicContextCapsule;
import mucontext.datamodel.context.ContextDataModelFacade;
import mucontext.datamodel.context.ContextReport;
import mudebs.common.MuDEBSException;
import mudebs.common.algorithms.ACK;
import qocim.capsule.IQoCIMCapsule;
import qocim.capsule.QoCIMConsumerContract;
import qocim.capsule.QoCIMProducerContract;
import qocim.capsule.configuration.IQoCIMCapsuleConfiguration;
import qocim.capsule.configuration.QoCIMCapsuleFunction;
import qocim.capsule.configuration.QoCIMCapsuleLastFunction;
import qocim.capsule.configuration.utils.QoCIMCapsuleConfigurationUnSerializer;
import qocim.datamodel.utils.ConstraintChecker;
import qocim.datamodel.utils.ConstraintCheckerException;
import qocim.datamodel.utils.QoCIMLogger;

public class QoCIMCapsule implements ContextConsumer, IQoCIMCapsule {

    // # # # # # CONSTANTS # # # # #

    /**
     * The name of the producer contracts.
     */
    private static final String PRODUCER_CONSTRACT_NAME = "QoCIMProducerContract";
    /**
     * The name of the consumer contracts.
     */
    private static final String CONSUMER_CONSTRACT_NAME = "QoCIMConsumerContract";
    /**
     * The argument key used to specify the XML configuration path.
     */
    public static final String ARG_CONFIGURATION_PATH = "--configuration-path";
    /**
     * The is of the context report published by the capsule.
     */
    public static final String CONTEXT_REPORT_ID = "QoCIMCapsule-ContextReport";

    // # # # # # PRIVATE VARIABLES # # # # #

    /**
     * The counter of producer contract id.
     */
    private Integer counter_producerContract;
    /**
     * The counter of consumer contract id.
     */
    private Integer counter_consumerContract;
    /**
     * the reference to the context capsule for advertising, etc.
     */
    private ContextCapsule capsule;
    /**
     * Facade to the context data model.
     */
    private static ContextDataModelFacade facade;
    /**
     * The last function executed by the capsule. It publishs the context
     * reports.
     */
    private final QoCIMCapsuleLastFunction lastQoCIMCapsuleFunction;
    /**
     * The XML configuration file unserializer.
     */
    private final QoCIMCapsuleConfigurationUnSerializer configurationUnSerializer;
    /**
     * The first function executed by the capsule. Because all the functions are
     * chained (@see QoCIMCapsuleFunction.nextFunction()) only the first
     * function is required.
     */
    private QoCIMCapsuleFunction firstQoCIMCapsuleFunction;
    /**
     * The context producer contract used to announce the type context reports
     * produced by the capsule.
     */
    private QoCIMProducerContract producerContrtact;
    /**
     * The context consumer contract used to filter the context reports received
     * by the capsule.
     */
    private QoCIMConsumerContract consumerContract;
    /**
     * The configuration of the capsule.
     */
    private IQoCIMCapsuleConfiguration configuration;

    // # # # # # CONSTRUCTORS # # # # #

    public QoCIMCapsule(final String[] commandArguments, final ACK _pushMode, final QoCIMConsumerContract _consumerContract,
	    final QoCIMProducerContract _producerContract) {
	capsule = null;
	counter_consumerContract = 0;
	counter_producerContract = 0;
	facade = new ContextDataModelFacade("facade");
	consumerContract = _consumerContract;
	producerContrtact = _producerContract;
	lastQoCIMCapsuleFunction = new QoCIMCapsuleLastFunction(this);
	try {
	    capsule = new BasicContextCapsule(commandArguments);
	} catch (final MuDEBSException _exception) {
	    QoCIMLogger.logger.log(Level.WARNING, "Fail to create a context capsule!", _exception);
	}
	configurationUnSerializer = new QoCIMCapsuleConfigurationUnSerializer(getConfigurationPath(commandArguments));
	setQoCIMCapsuleConfiguration(configurationUnSerializer.unSerialize());
	QoCIMLogger.event("New QoCIMCapsule created.");
    }

    @Override
    public void consume(final String _xmlMessage) {
	// - - - - - INITIALIZE THE VARIABLES - - - - -
	/*
	 * The context report from the XML message received by the capsule.
	 */
	final ContextReport contextReport = facade.unserialiseFromXML(_xmlMessage);
	/*
	 * Verify if the context report comes from the capsule.
	 */
	if (contextReport.id.equals(CONTEXT_REPORT_ID)) {
	    return;
	}
	// - - - - - CORE OF THE METHOD - - - - -
	QoCIMLogger.event("QoCIMCapsule: consuming a context report");
	/*
	 * Checking if the configuration of the capsule have to be reloaded.
	 */
	if (configurationUnSerializer.reloadConfigurationFileRequired()) {
	    setQoCIMCapsuleConfiguration(configurationUnSerializer.unSerialize());
	}
	/*
	 * Transform the context report with the first function. The other
	 * functions will be automatically executed (@see
	 * QoCIMCapsuleFunction.nextFunction()).
	 */
	firstQoCIMCapsuleFunction.newInformation(contextReport);
    }

    @Override
    public IQoCIMCapsuleConfiguration qoCIMCapsuleConfiguration() {
	// - - - - - RETURN STATEMENT - - - - -
	return configuration;
    }

    @Override
    public void publish(final ContextReport _contextReport) {
	QoCIMLogger.event("QoCIMCapsule: pushing a context report");
	// - - - - - INITIALIZE THE VARIABLES - - - - -
	/*
	 * The XML message representing the context report to publish.
	 */
	final ContextReport newContextReport = facade.createContextReport(CONTEXT_REPORT_ID);
	newContextReport.observations.addAll(_contextReport.observations);
	final String message = facade.serialiseToXML(newContextReport);
	// - - - - - CORE OF THE METHOD - - - - -
	try {
	    capsule.push(PRODUCER_CONSTRACT_NAME + counter_producerContract, message);
	} catch (final MuDEBSException _exception) {
	    QoCIMLogger.logger.log(Level.WARNING, "Fail to push message: " + message, _exception);
	}
    }

    @Override
    public IQoCIMCapsule setQoCIMCapsuleConfiguration(final IQoCIMCapsuleConfiguration _configuration) {
	// - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
	try {
	    final String message = "QoCIMCapsule.setQoCIMCapsuleConfiguration(IQoCIMCapsuleConfiguration): the argument _configuration is null.";
	    ConstraintChecker.notNull(_configuration, message);
	} catch (final ConstraintCheckerException e) {
	    return this;
	}
	// - - - - - INITIALIZE THE VARIABLES - - - - -
	configuration = _configuration;
	// - - - - - CORE OF THE METHOD - - - - -
	QoCIMLogger.event("QoCIMCapsule using a new configuration.");
	counter_consumerContract++;
	counter_producerContract++;
	consumerContract.setSubscriptionFilter(CONSUMER_CONSTRACT_NAME + counter_consumerContract, configuration.subscriptionFilter(), capsule, this);
	producerContrtact.setAdvertisementFilter(PRODUCER_CONSTRACT_NAME + counter_producerContract, configuration.advertisementFilter(), capsule);
	QoCIMLogger.logger.setLevel(configuration.logLevel());
	/*
	 * Configure the first and last function executed by the chain to push
	 * the context report.
	 */
	if (configuration.orderedListFunction().isEmpty()) {
	    firstQoCIMCapsuleFunction = lastQoCIMCapsuleFunction;
	} else {
	    firstQoCIMCapsuleFunction = configuration.orderedListFunction().getFirst();
	    configuration.orderedListFunction().getLast().setNextFunction(lastQoCIMCapsuleFunction);
	}
	// - - - - - RETURN STATEMENT - - - - -
	return this;
    }

    @Override
    public IQoCIMCapsule setQoCIMConsumerContract(final QoCIMConsumerContract _consumerContract) {
	// - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
	try {
	    final String message = "QoCIMCapsule.setQoCIMConsumerContract(QoCIMConsumerContract): the argument _consumerContract is null.";
	    ConstraintChecker.notNull(_consumerContract, message);
	} catch (final ConstraintCheckerException e) {
	    return this;
	}
	// - - - - - CORE OF THE METHOD - - - - -
	consumerContract = _consumerContract;
	// - - - - - RETURN STATEMENT - - - - -
	return this;
    }

    @Override
    public IQoCIMCapsule setQoCIMProducerContract(final QoCIMProducerContract _producerContract) {
	// - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
	try {
	    final String message = "QoCIMCapsule.setQoCIMConsumerContract(QoCIMProducerrContract): the argument _producerContract is null.";
	    ConstraintChecker.notNull(_producerContract, message);
	} catch (final ConstraintCheckerException e) {
	    return this;
	}
	// - - - - - CORE OF THE METHOD - - - - -
	producerContrtact = _producerContract;
	// - - - - - RETURN STATEMENT - - - - -
	return this;
    }

    // # # # # # PRIVATE METHODS # # # # #

    private String getConfigurationPath(final String[] _commandLineArguments) {
	// - - - - - CORE OF THE METHOD - - - - -
	final Iterator<String> iterator_argument = Arrays.asList(_commandLineArguments).iterator();
	while (iterator_argument.hasNext()) {
	    if (iterator_argument.next().equals(ARG_CONFIGURATION_PATH)) {
		return iterator_argument.next();
	    }
	}
	// - - - - - RETURN STATEMENT - - - - -
	return "";
    }
}
