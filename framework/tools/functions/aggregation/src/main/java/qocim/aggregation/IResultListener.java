package qocim.aggregation;

import qocim.information.QInformation;

public interface IResultListener {

	void newInformation(QInformation<?> information);
}
