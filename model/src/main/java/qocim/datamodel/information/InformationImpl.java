package qocim.datamodel.information;

import qocim.datamodel.QoCIndicator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InformationImpl<I> implements QInformation<I> {

	private I data;
	private Date creationDate;
	private List<QoCIndicator> indicators;
	private String name;

	public InformationImpl() {
		setCreationDate(new Date());
		setData(null);
		indicators = new ArrayList<>();
		setName("");
	}

	public InformationImpl(final I data) {
		setData(data);
		setCreationDate(new Date());
		indicators = new ArrayList<>();
		setName("");
	}

	public InformationImpl(final String name, final I data) {
		setData(data);
		setCreationDate(new Date());
		indicators = new ArrayList<>();
		setName(name);
	}

	@Override
	public int compareTo(final QInformation information) {

		if (information.data().equals(data) && information.name().equals(name)) {
			return 0;
		}

		return 1;
	}

	@Override
	public Date creationDate() {
		return creationDate;
	}

	@Override
	public I data() {
		return data;
	}

	@Override
	public List<QoCIndicator> indicators() {
		return indicators;
	}

	@Override
	public String name() {
		return name;
	}

	@Override
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Override
	public void setData(I data) {
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
