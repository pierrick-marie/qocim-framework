package qocim.model.tools;

import java.util.List;

import qocim.information.QInformation;
import qocim.model.QoCValue;

public interface IQoCFacade {

	void addQoCListener(final IQoCListener qocListener);

	void fireNewInformation(final QInformation<?> information);

	QoCValue maxQoCMetricValue();

	QoCValue minQoCMetricValue();

	IQoCEvaluator qoCEvaluator();

	List<IQoCListener> qoCListeners();

	QoCValue newQoCMetricValue(final Object value);

	QoCValue newQoCMetricValue(final QInformation information, final Object metricValue);

	QoCValue qualifyInformation(final QInformation information);

	Boolean setEvaluator(final IQoCEvaluator qocEvaluator);

	void setListenerMethodName(final String listenerMethodName);
}
