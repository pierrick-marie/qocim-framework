package qocim.information;

import java.util.Date;
import java.util.List;

import qocim.model.QoCIndicator;

public interface QInformation extends Comparable<QInformation> {

	Date getCreationDate();

	Object getData();

	List<QoCIndicator> getIndicators();

	String getName();

	void setCreationDate(Date creationDate);

	void setData(Object data);

	void setIndicators(List<QoCIndicator> indicators);

	void setName(String name);

	@Override
	String toString();
}
