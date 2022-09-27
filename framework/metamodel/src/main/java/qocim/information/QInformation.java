package qocim.information;

import java.util.Date;
import java.util.List;

import qocim.model.QoCIndicator;

public interface QInformation<T> extends Comparable<QInformation<T>> {

	Date creationDate();

	T data();

	String name();

	List<QoCIndicator> indicators();

	void setIndicators(List<QoCIndicator> indicators);

	@Override
	String toString();
}
