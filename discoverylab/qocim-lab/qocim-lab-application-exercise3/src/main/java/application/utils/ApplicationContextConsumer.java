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
package application.utils;

import java.util.Iterator;
import java.util.LinkedList;

import mucontext.api.ContextConsumer;
import mucontext.datamodel.context.ContextDataModelFacade;
import mucontext.datamodel.context.ContextObservation;
import mucontext.datamodel.context.ContextReport;
import qocim.datamodel.QoCIndicator;

/**
 * This class print all the relevant information of a context information
 * received by the application.
 *
 * @author Pierrick MARIE
 */
public class ApplicationContextConsumer implements ContextConsumer {

	/**
	 * Facade to the context data model.
	 */
	private static ContextDataModelFacade facade = null;

	/**
	 * String buffer to display the consumed context information.
	 */
	private final StringBuffer stringBuffer;

	public ApplicationContextConsumer() {

		stringBuffer = new StringBuffer();
		facade = new ContextDataModelFacade("facade");
	}

	private void bufferingContextInformation(final ContextReport _contextReport,
			final ContextObservation<?> _contextObservation) {

		// Fill the <i>stingBuffer</i> with the interesting information.
		stringBuffer.append("The context application consumes report number: " + _contextReport.id + "\n");
		stringBuffer.append("\t - Observation value: " + _contextObservation.value + "\n");
	}

	private void bufferingQoCMetaData(final LinkedList<QoCIndicator> _listQoCIndicator) {

		Iterator<QoCIndicator> qoCIndicatorIterator;
		QoCIndicator qoCIndicator;

		qoCIndicatorIterator = _listQoCIndicator.iterator();

		// Iterate all along the QoC meta-data to display all of them.
		while (qoCIndicatorIterator.hasNext()) {
			qoCIndicator = qoCIndicatorIterator.next();
			stringBuffer.append("\t - QoC indicator " + qoCIndicator.name() + ", value: "
					+ qoCIndicator.list_qoCMetricValue.iterator().next().value() + "\n");
		}
	}

	@Override
	public void consume(final String _contextData) {
		ContextReport contextReport;
		ContextObservation<?> contextObservation;

		// Flushing the buffer
		stringBuffer.setLength(0);

		// Unserialize the information contained into <i>_contextData</i>.
		contextReport = facade.unserialiseFromXML(_contextData);
		contextObservation = contextReport.observations.iterator().next();

		// Buffering the most important context information and QOC meta-data.
		bufferingContextInformation(contextReport, contextObservation);
		bufferingQoCMetaData(contextObservation.list_qoCIndicator);

		System.out.println(stringBuffer.toString());
	}
}
