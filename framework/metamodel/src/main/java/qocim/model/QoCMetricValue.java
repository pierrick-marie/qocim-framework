package qocim.model;

import java.util.Date;

import qocim.metamodel.QAttribut;
import qocim.metamodel.QClass;

public class QoCMetricValue extends QClass implements Comparable<QoCMetricValue>  {

	private QAttribut<Integer> id;
	private QAttribut<Date> creationDate;
	private QAttribut<Date> modificationDate;
	private QAttribut<Object> value;

	private QoCIndicator indicator;
	private QoCMetricDefinition metricDefinition;

	public QoCMetricValue() {

		super();

		setId(new QAttribut<Integer>("id", this, 0));
		setCreationDate(new QAttribut<>("creation date", this, new Date()));
		setModificationDate(new QAttribut<>("modification date", this, new Date()));
	}

	public QoCMetricValue(final QAttribut<Integer> id, final QAttribut<Date> creationDate, final QAttribut<Date> modificationDate,
			final QAttribut<Object> value, final QoCIndicator indicator, final QoCMetricDefinition metricDefinition) {

		super();

		setId(id);
		setCreationDate(creationDate);
		setModificationDate(modificationDate);
		setValue(value);

		setIndicator(indicator);
		setMetricDefinition(metricDefinition);
	}

	@Override
	public int compareTo(final QoCMetricValue value) {

		if( (value.value.getObject() instanceof Number) && (this.value.getObject() instanceof Number)){
			return ((Double) value.value.getObject()).compareTo((Double) this.value.getObject());
		}

		return 0;
	}

	@Override
	public boolean equals(final Object comparable) {

		QoCMetricValue metricValue;

		if (comparable instanceof QoCMetricValue) {
			metricValue = (QoCMetricValue) comparable;
			return metricValue.id.equals(id) && metricValue.creationDate.equals(creationDate)
						&& metricValue.modificationDate.equals(modificationDate) && metricValue.value.equals(value)
					&& metricValue.indicator.equals(indicator) && metricValue.metricDefinition.equals(metricDefinition);
		}

		return false;
	}

	public QAttribut<Date> getCreationDate() {
		return creationDate;
	}

	public Date creationDate() { return (Date) creationDate.getObject(); }

	public QoCMetricDefinition getDefinition() {
		return metricDefinition;
	}

	public QAttribut<Integer> getId() {
		return id;
	}

	public Integer id() { return (Integer) id.getObject(); }

	public QoCIndicator getIndicator() {
		return indicator;
	}

	public QAttribut<Date> getModificationDate() {
		return modificationDate;
	}

	public Date modificationDate() { return (Date) modificationDate.getObject();}

	public QAttribut<Object> getValue() {
		return value;
	}

	public Object value() {
		return value.getObject();
	}

	public void setCreationDate(final Date creationDate) {
		this.creationDate.setObject(creationDate);
	}

	public void setCreationDate(final QAttribut<Date> creationDate) {
		this.creationDate = creationDate;
	}

	public void setId(final Integer id) {
		this.id.setObject(id);
	}

	public void setId(final QAttribut<Integer> id) {
		this.id = id;
	}

	public void setIndicator(final QoCIndicator indicator) {
		this.indicator = indicator;
	}

	public void setMetricDefinition(final QoCMetricDefinition metricDefinition) {
		this.metricDefinition = metricDefinition;
	}

	public void setModificationDate(final Date modificationDate) {
		this.modificationDate.setObject(modificationDate);
	}

	public void setModificationDate(final QAttribut<Date> modificationDate) {
		this.modificationDate = modificationDate;
	}

	public void setObjectValue(final Object value) {
		this.value.setObject(value);
	}

	public void setValue(final QAttribut<Object> value) {
		this.value = value;
	}

	@Override
	public String toString() {
		final QoCMetricDefinition definition = (QoCMetricDefinition) metricDefinition;

		if (null != definition && null != definition.getUnit().getObject()) {
			return getClass().getSimpleName() + " : " + value() + " " + definition.getUnit().getObject();
		} else {
			return getClass().getSimpleName() + " : " + value() + " ";
		}
	}
}
