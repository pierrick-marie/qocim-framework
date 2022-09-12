package qocim.datamodel.information;

import qocim.datamodel.QoCIndicator;

import java.util.Date;
import java.util.List;

public interface QInformation<I> extends Comparable<QInformation> {

	Date creationDate();

	I data();

	List<QoCIndicator> indicators();

	String name();

	void setCreationDate(Date creationDate);

	void setData(I data);

	void setIndicators(List<QoCIndicator> indicators);

	void setName(String name);

	@Override
	String toString();
}
