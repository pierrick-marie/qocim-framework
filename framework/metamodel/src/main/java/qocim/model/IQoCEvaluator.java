package qocim.model;

import java.util.List;
import java.util.concurrent.Callable;

import qocim.information.QInformation;

public interface IQoCEvaluator extends Callable<QoCMetricValue> {

	@Override
	QoCMetricValue call();

	void newInformation(QInformation information);

	List<Class<?>> supportedDefinitionType();

	List<Class<?>> supportedInformationType();
}
