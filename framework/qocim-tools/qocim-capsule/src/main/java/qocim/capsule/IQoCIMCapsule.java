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

import mucontext.datamodel.context.ContextReport;
import qocim.capsule.configuration.IQoCIMCapsuleConfiguration;

/**
 * IQoCIMCapsule defines a QoCIM capsule. It provides methods to configure the
 * capsule and set the producer and consumer contract.
 *
 * @author Pierrick MARIE
 */
public interface IQoCIMCapsule {

    /**
     * @return The current QoCIM capsule configuration.
     */
    IQoCIMCapsuleConfiguration qoCIMCapsuleConfiguration();

    /**
     * This method is used to publish a context report with the context producer
     * contract.
     *
     * @param _contextReport
     *            The context report to publish.
     * @return The result of the push method.
     */
    void publish(final ContextReport _contextReport);

    /**
     * This method sets the current configuration of the QoCIM capsule.
     *
     * @param _configuration
     *            The new configuration.
     * @return this.
     */
    IQoCIMCapsule setQoCIMCapsuleConfiguration(IQoCIMCapsuleConfiguration _configuration);

    /**
     * This method sets the current consumer contract.
     *
     * @param _consumerContract
     *            The new consumer contract.
     * @return this.
     */
    IQoCIMCapsule setQoCIMConsumerContract(QoCIMConsumerContract _consumerContract);

    /**
     * This method sets the current producer contract.
     *
     * @param _producerContract
     *            The new producer contract.
     * @return this.
     */
    IQoCIMCapsule setQoCIMProducerContract(QoCIMProducerContract _producerContract);
}
