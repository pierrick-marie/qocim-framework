package qocim.model;

import java.util.List;

import qocim.information.QInformation;

public interface IQoCFacade {

	void addQoCListener(final Object qocListener);

	void fireNewInformation(final QInformation information);

	String getListenerMethodName();

	QoCMetricValue getMaxQoCMetricValue();

	QoCMetricValue getMinQoCMetricValue();

	IQoCEvaluator getQoCEvaluator();

	List<Object> getQoCListeners();

	QoCMetricValue newQoCMetricValue(final Object value);

	QoCMetricValue newQoCMetricValue(final QInformation information, final Object metricValue);

	QoCMetricValue qualifyInformation(final QInformation information);

	Boolean setEvaluator(final IQoCEvaluator qocEvaluator);

	void setListenerMethodName(final String listenerMethodName);
}
