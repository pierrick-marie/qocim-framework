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
package application;

import mucontext.api.ContextApplication;
import mucontext.api.ContextConsumer;
import mucontext.contextapplication.BasicContextApplication;
import mucontext.datamodel.contextcontract.BasicContextConsumerContract;
import mudebs.common.algorithms.OperationalMode;
import application.utils.ApplicationContextConsumer;
import application.utils.SubscriptionFilter;

/**
 * This class defines an example context application that use QoCIM to create a
 * subscription filter and evaluate the QoC meta-data of the observation.
 *
 * @author Denis Conan
 * @author Pierrick MARIE
 */
public class Main {

    /**
     * The main method of the application.
     *
     * @param _args
     *            The arguments of the context application, which are seen as
     *            command line arguments, that is to say as an array of strings.
     * @throws Exception
     *             Exception in main thread.
     */
    public static void main(final String[] _args) throws Exception {

	ContextApplication contextApplication;
	ContextConsumer consumer;
	SubscriptionFilter filter;

	contextApplication = new BasicContextApplication(_args);
	filter = new SubscriptionFilter();
	consumer = new ApplicationContextConsumer();

	contextApplication.setContextConsumerContract(
		new BasicContextConsumerContract("somecontract-with-percentprecision", OperationalMode.GLOBAL, filter.toString(), null), consumer);

	while (loopNumber > 0) {
	    Thread.sleep(TIME_TO_WAIT);
	    loopNumber--;
	}

	contextApplication.unsetAllContextConsumerContracts();
	contextApplication.terminate();
    }

    /**
     * Time to wait during polling (in ms).
     */
    private static final long TIME_TO_WAIT = 5000;
    /**
     * The termination of the main thread is asked for.
     */
    private static int loopNumber = Integer.MAX_VALUE;

}
