package qocim.information;

import java.util.Date;
import java.util.List;

import qocim.model.QoCIndicator;

public interface QInformation<T> extends Comparable<QInformation<T>> {

	Date creationDate();

	T data();

	List<QoCIndicator> indicators();

	String name();

	void setCreationDate(Date creationDate);

	void setData(T data);

	void setIndicators(List<QoCIndicator> indicators);

	void setName(String name);

	@Override
	String toString();
}
