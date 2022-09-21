package main;

import qocim.model.QoCMetricValue;
import qocim.utils.logs.QoCIMLogger;

public class QoCListener {

	public void fireNewQoCMetricValue(final QoCMetricValue metricValue, final Object information) {

		log(information, metricValue);
	}

	private void log(final Object information, final QoCMetricValue qocValue) {

		StringBuffer buffer = new StringBuffer();

		buffer.append("Async QoC evaluation\n");
		buffer.append(" * Information type: " + information.getClass().getSimpleName() + ".class - Information value: "
				+ information.toString() + "\n");
		buffer.append(" * " + qocValue);

		QoCIMLogger.logger.info(buffer.toString());
	}
}
