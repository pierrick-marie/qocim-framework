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
package collector;

import mucontext.contextcollector.BasicContextCollector;
import mucontext.datamodel.context.ContextDataModelFacade;
import mucontext.datamodel.context.ContextObservation;
import mucontext.datamodel.context.ContextReport;
import mucontext.datamodel.contextcontract.BasicContextProducerContract;
import mudebs.common.algorithms.ACK;
import mudebs.common.algorithms.OperationalMode;
import collector.utils.AdvertisementFilter;
import collector.utils.ContextReportDisplay;
import collector.utils.QoCEvaluator;
import collector.utils.RandomContextObservation;

/**
 * This class defines an example context collector that use QoCIM to create an
 * advertisement filter and evaluate the QoC meta-data of the observation.
 *
 * @author Denis Conan
 * @author Pierrick MARIE
 */
public class Main {

    /**
     * Number of iterations in the outer loop.
     */
    private static final int NBITER_OUTER_LOOP = 1000;

    /**
     * Time to wait during publications (in ms).
     */
    private static final long TIME_TO_WAIT = 3000;

    /**
     * The termination of the main thread is asked for.
     */
    private static boolean termination = false;

    /**
     * The facade used to create the context reports.
     */
    private static ContextDataModelFacade facade;

    /**
     * Minimal random value of the context report id.
     */
    private static final int MIN_REPORT_ID_VALUE = 1;

    /**
     * Maximal random value of the context report id.
     */
    private static final int MAX_REPORT_ID_VALUE = 1000;

    /**
     * Salt to create random context report id.
     */
    private static final double CONTEXT_REPORT_SALT_ID = RandomContextObservation.getRandomValue(MIN_REPORT_ID_VALUE, MAX_REPORT_ID_VALUE);

    /**
     * The main method of the context collector.
     *
     * @param _args
     *            The arguments of the context collector, which are seen as
     *            command line arguments, that is to say as an array of strings.
     * @throws Exception
     *             Exception in main thread.
     */
    public static void main(final String[] _args) throws Exception {

	facade = new ContextDataModelFacade("facade");
	final BasicContextCollector contextCollector = new BasicContextCollector(_args);
	final RandomContextObservation observationUtil = new RandomContextObservation(facade);
	final AdvertisementFilter filter = new AdvertisementFilter();
	final QoCEvaluator qoCEvaluator = new QoCEvaluator();
	ContextReport report;
	ContextObservation<Double> contextObservation;
	String serializedReport;

	{
	    // TODO To answer to exercise 1 and display the advertisement
	    // filter, uncomment the following
	    // lines
	    // final StringBuffer buffer = new StringBuffer();
	    // buffer.append("############## \n");
	    // buffer.append("# \n");
	    // buffer.append(filter.toString() + "\n");
	    // buffer.append("# \n");
	    // buffer.append("############## \n");
	    // System.out.println(buffer.toString());
	}

	// Initialization of the advertisement QoC-based routing filter.
	contextCollector.setContextProducerContract(new BasicContextProducerContract("Somecontract", OperationalMode.LOCAL, filter.toString(), null));

	while (!termination) {

	    for (int i = 0; i < NBITER_OUTER_LOOP; i++) {
		report = facade.createContextReport("Report percent collector " + CONTEXT_REPORT_SALT_ID + i);

		// Create a new context observation.
		contextObservation = observationUtil.getRandomContextObservation();

		// Adding the QoC meta-data
		qoCEvaluator.addQoCMetaData(contextObservation);

		// Final step to create the context report and pushing it.
		facade.addContextObservation(report, contextObservation);
		serializedReport = facade.serialiseToXML(report);
		contextCollector.push("Somecontract", serializedReport, ACK.LOCAL);

		{
		    // TODO To answer to exercise 1 and display the xml document
		    // sent to the application,
		    // uncomment the following lines
		    // final StringBuffer buffer = new StringBuffer();
		    // buffer.append("############## \n");
		    // buffer.append("# \n");
		    // buffer.append(serializedReport + "\n");
		    // buffer.append("# \n");
		    // buffer.append("############## \n");
		    // System.out.println(buffer.toString());
		}

		// Print logs into the console
		System.out.println(new ContextReportDisplay(report, contextObservation));

		Thread.sleep(TIME_TO_WAIT);
	    }
	    contextCollector.unsetAllContextProducerContracts();
	    termination = true;
	    contextCollector.shutdown();
	}
    }
}
