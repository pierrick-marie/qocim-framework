package qocim.datamodel.precision.evaluators;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import qocim.datamodel.precision.definitions.SimpleDefinition;
import qocim.datamodel.precision.values.MaxPrecisionMetricValue;
import qocim.datamodel.precision.values.PrecisionMetricValue;
import qocim.information.QInformation;
import qocim.model.IQoCEvaluator;
import qocim.model.QoCMetricValue;

public class SimpleEvaluator implements IQoCEvaluator {

	@Override
	public QoCMetricValue call() {

		final Random random = new Random();
		final Double precisionValue = random.nextDouble() * (double) MaxPrecisionMetricValue.VALUE;
		final PrecisionMetricValue value = new PrecisionMetricValue();
		value.setObjectValue(precisionValue);

		return value;
	}

	@Override
	public void newInformation(QInformation information) {
	}

	@Override
	public List<Class<?>> supportedDefinitionType() {

		// TODO Auto-generated method stub
		final List<Class<?>> supportedDefintinitionType = new LinkedList<>();
		supportedDefintinitionType.add(SimpleDefinition.class);
		return supportedDefintinitionType;
	}

	@Override
	public List<Class<?>> supportedInformationType() {

		// TODO Auto-generated method stub
		final List<Class<?>> supportedInformationType = new LinkedList<>();
		supportedInformationType.add(QInformation.class);
		return supportedInformationType;
	}
}
