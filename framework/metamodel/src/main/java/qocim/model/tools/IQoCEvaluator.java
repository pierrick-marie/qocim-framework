package qocim.model.tools;

import java.util.List;
import java.util.concurrent.Callable;

import qocim.information.QInformation;
import qocim.model.QoCValue;

public interface IQoCEvaluator extends Callable<QoCValue> {

	@Override
	QoCValue call();

	void fireNewInformation(QInformation information);

	List<Class<?>> supportedDefinitionType();

	List<Class<?>> supportedInformationType();
}
