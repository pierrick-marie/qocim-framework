package qocim.aggregation.test;

import qocim.aggregation.IResultListener;
import qocim.information.QInformation;

public class ResultListener implements IResultListener {

	private QInformation<?> information = null;

	@Override
	public void newInformation(QInformation<?> information) {
		this.information = information;
	}

	public QInformation<?> getInformation() {
		return information;
	}
}
