package qocim.datamodel.freshness.evaluators;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import qocim.datamodel.freshness.definitions.SimpleDefinition;
import qocim.datamodel.freshness.values.FreshnessMetricValue;
import qocim.information.QInformation;
import qocim.model.IQoCEvaluator;
import qocim.model.QoCMetricValue;

public class SimpleEvaluator implements IQoCEvaluator {

	private QInformation information;

	@Override
	public QoCMetricValue call() {
		final Date date = new Date();
		final Date informationDate = information.getCreationDate();

		final Double freshnessValue = (double) (date.getTime() - informationDate.getTime()) / 1000;
		final FreshnessMetricValue value = new FreshnessMetricValue();
		value.setObjectValue(freshnessValue);
		return value;
	}

	@Override
	public void newInformation(QInformation information) {
		this.information = information;
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
