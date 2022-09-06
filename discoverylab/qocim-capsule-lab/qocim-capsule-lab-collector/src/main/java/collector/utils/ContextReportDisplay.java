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
package collector.utils;

import java.util.Iterator;

import mucontext.datamodel.context.ContextObservation;
import mucontext.datamodel.context.ContextReport;
import qocim.datamodel.QoCIndicator;

/**
 * This class display into a console the most important information of a context
 * report.
 *
 * @author Pierrick MARIE
 */
public class ContextReportDisplay {

	StringBuffer stringBuffer;

	public ContextReportDisplay(final ContextReport _report, final ContextObservation<?> _observation) {

		Iterator<QoCIndicator> qoCIndicatorIterator;
		QoCIndicator qoCIndicator;

		stringBuffer = new StringBuffer();
		qoCIndicatorIterator = _observation.list_qoCIndicator.iterator();

		stringBuffer.append("Pushed report number: " + _report.id + "\n");
		stringBuffer.append("\t - observation value: " + _observation.value + "\n");

		// Iterate all along the QoC meta-data to display all of them.
		while (qoCIndicatorIterator.hasNext()) {
			qoCIndicator = qoCIndicatorIterator.next();
			stringBuffer.append("\t - QoC indicator " + qoCIndicator.name() + ", value: "
					+ qoCIndicator.list_qoCMetricValue.iterator().next().value() + "\n");
		}
	}

	@Override
	public String toString() {
		return stringBuffer.toString();
	}
}
