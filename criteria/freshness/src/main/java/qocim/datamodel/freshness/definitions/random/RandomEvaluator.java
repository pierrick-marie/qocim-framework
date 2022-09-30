package qocim.datamodel.freshness.definitions.random;


import qocim.datamodel.freshness.definitions.values.FreshnessValue;
import qocim.information.QInformation;
import qocim.model.QoCValue;
import qocim.model.tools.IQoCEvaluator;

public enum RandomEvaluator implements IQoCEvaluator {

	INSTANCE;

	private Integer qocValueCount;

	RandomEvaluator() {
		qocValueCount = 0;
	}

	@Override
	public QoCValue<?> call() throws Exception {
		return new FreshnessValue(qocValueCount++, (int) (Math.random() * 100)).setDefinitionId(RandomDefinition.ID);
	}

	@Override
	public void newInformation(QInformation<?> information) {
		/* Do nothing */
	}
}