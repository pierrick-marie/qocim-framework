package qocim.datamodel.freshness.definitions.simple;


import qocim.datamodel.freshness.definitions.values.FreshnessValue;
import qocim.information.QInformation;
import qocim.model.QoCValue;
import qocim.model.tools.IQoCEvaluator;

import java.util.Date;

public enum SimpleEvaluator implements IQoCEvaluator {

	INSTANCE;

	private Integer qocValueCount;
	private QInformation<?> currentInformation;

	SimpleEvaluator() {
		qocValueCount = 0;
	}

	@Override
	public QoCValue<?> call() throws Exception {
		long value = new Date().getTime() - currentInformation.creationDate().getTime();
		return new FreshnessValue(qocValueCount++, (int) value).setDefinitionId(SimpleDefinition.ID);
	}

	@Override
	public void newInformation(QInformation<?> information) {
		currentInformation = information;
	}
}
