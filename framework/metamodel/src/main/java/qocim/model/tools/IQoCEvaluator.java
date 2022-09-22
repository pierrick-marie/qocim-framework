package qocim.model.tools;

import java.util.List;
import java.util.concurrent.Callable;

import qocim.information.QInformation;
import qocim.model.QoCMetricValue;

public interface IQoCEvaluator extends Callable<QoCMetricValue> {

	@Override
	QoCMetricValue call();

	void fireNewInformation(QInformation information);

	List<Class<?>> supportedDefinitionType();

	List<Class<?>> supportedInformationType();
}
