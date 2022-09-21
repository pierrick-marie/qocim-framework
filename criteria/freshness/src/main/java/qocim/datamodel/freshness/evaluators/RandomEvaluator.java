package qocim.datamodel.freshness.evaluators;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import qocim.datamodel.freshness.definitions.RandomDefinition;
import qocim.datamodel.freshness.values.FreshnessMetricValue;
import qocim.datamodel.freshness.values.MaxFreshnessMetricValue;
import qocim.information.QInformation;
import qocim.model.IQoCEvaluator;
import qocim.model.QoCMetricValue;

public class RandomEvaluator implements IQoCEvaluator {

	@Override
	public QoCMetricValue call() {

		final Random random = new Random();
		final Double freshnessValue = random.nextDouble() * (double) MaxFreshnessMetricValue.VALUE;
		final FreshnessMetricValue value = new FreshnessMetricValue();
		value.setObjectValue(freshnessValue);

		return value;
	}

	@Override
	public void newInformation(QInformation information) {
	}

	@Override
	public List<Class<?>> supportedDefinitionType() {

		// TODO Auto-generated method stub
		final List<Class<?>> supportedDefintinitionType = new LinkedList<>();
		supportedDefintinitionType.add(RandomDefinition.class);
		return supportedDefintinitionType;
	}

	@Override
	public List<Class<?>> supportedInformationType() {

		// TODO Auto-generated method stub
		final List<Class<?>> supportedInformationType = new LinkedList<>();
		supportedInformationType.add(QInformation.class);
		supportedInformationType.add(String.class);
		return supportedInformationType;
	}
}