package qocim.datamodel.precision.simple.definitions;

import qocim.datamodel.precision.simple.definitions.values.PrecisionValue;
import qocim.information.QInformation;
import qocim.model.QoCValue;
import qocim.model.tools.IQoCEvaluator;

public enum SimpleEvaluator implements IQoCEvaluator {

	INSTANCE;

	private Integer qocValueCount;

	SimpleEvaluator() {
		qocValueCount = 0;
	}

	@Override
	public QoCValue<?> call() throws Exception {
		return new PrecisionValue(qocValueCount++, (int) (Math.random() * 100)).setDefinitionId(SimpleDefinition.ID);
	}

	@Override
	public void newInformation(QInformation<?> information) {
		/* Do nothing */
	}
}
