package qocim.model.tools;

import qocim.information.QInformation;
import qocim.model.QoCValue;

public interface IQoCFacade {

	QoCValue maxQoCMetricValue();

	QoCValue minQoCMetricValue();

	void fireNewInformation(final QInformation<?> information);

	QoCValue qualifyInformation(final QInformation information);

	IQoCEvaluator qocEvaluator();

	void setQoCEvaluator(final IQoCEvaluator qocEvaluator);

	IQoCListener qoCListeners();

	void setQoCListener(final IQoCListener qocListener);

	@Deprecated
	QoCValue newQoCValue(final Object value);

	@Deprecated
	QoCValue newQoCValue(final QInformation information, final Object metricValue);

	@Deprecated
	void setListenerMethodName(final String listenerMethodName);
}
