package qocim.model.tools;

import java.util.List;

import qocim.information.QInformation;
import qocim.model.QoCMetricValue;

public interface IQoCFacade {

	void addQoCListener(final IQoCListener qocListener);

	void fireNewInformation(final QInformation<?> information);

	QoCMetricValue maxQoCMetricValue();

	QoCMetricValue minQoCMetricValue();

	IQoCEvaluator qoCEvaluator();

	List<IQoCListener> qoCListeners();

	QoCMetricValue newQoCMetricValue(final Object value);

	QoCMetricValue newQoCMetricValue(final QInformation information, final Object metricValue);

	QoCMetricValue qualifyInformation(final QInformation information);

	Boolean setEvaluator(final IQoCEvaluator qocEvaluator);

	void setListenerMethodName(final String listenerMethodName);
}
