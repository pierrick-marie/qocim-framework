package criteria.test.simple.definitions;

import criteria.test.simple.definitions.values.TestValue;
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
		return new TestValue(qocValueCount++, (int) (Math.random() * 100)).setDefinitionId(SimpleDefinition.ID);
	}

	@Override
	public void newInformation(QInformation<?> information) {
		/* Do nothing */
	}
}
