package qocim.aggregation;

import qocim.information.QInformation;

public interface IAggregationListener {

	void newInformation(QInformation<?> information);
}
