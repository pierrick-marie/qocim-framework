package qocim.information;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import qocim.model.QoCIndicator;

public class InformationImpl implements QInformation {

	private Object data;
	private Date creationDate;
	private List<QoCIndicator> indicators;
	private String name;

	public InformationImpl() {
		setCreationDate(new Date());
		setData(new Object());
		indicators = new ArrayList<>();
		setName("");
	}

	public InformationImpl(final Object data) {
		setData(data);
		setCreationDate(new Date());
		indicators = new ArrayList<>();
		setName("");
	}

	@Override
	public int compareTo(final QInformation information) {

		if (information.getData().equals(data) && information.getName().equals(name)) {
			return 0;
		}

		return 1;
	}

	@Override
	public Date getCreationDate() {
		return creationDate;
	}

	@Override
	public Object getData() {
		return data;
	}

	@Override
	public List<QoCIndicator> getIndicators() {
		return indicators;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Override
	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public void setIndicators(List<QoCIndicator> indicators) {
		this.indicators = indicators;
	}

	@Override
	public void setName(String name) {
		this.name = name;

	}

	@Override
	public String toString() {
		return name.toString();
	}
}
