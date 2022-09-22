package qocim.model;

import java.util.Date;

import qocim.metamodel.QAttribut;
import qocim.metamodel.QClass;

/**
 *
 */
public class QoCMetricValue {
/*
public class QoCMetricValue<T> extends QClass implements Comparable<QoCMetricValue<T>>  {
	private QAttribut<Integer> id;
	private QAttribut<Date> creationDate;
	private QAttribut<Date> modificationDate;
	private QAttribut<T> value;

	private QoCIndicator indicator;
	private QoCMetricDefinition metricDefinition;

	public QoCMetricValue() {

		super();

		setId(new QAttribut<Integer>("id", this, 0));
		setCreationDate(new QAttribut<>("creation date", this, new Date()));
		setModificationDate(new QAttribut<>("modification date", this, new Date()));
	}

	public QoCMetricValue(final QAttribut<Integer> id, final QAttribut<Date> creationDate, final QAttribut<Date> modificationDate,
			final QAttribut<T> value, final QoCIndicator indicator, final QoCMetricDefinition metricDefinition) {

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

		if( (value.value.value() instanceof Number) && (this.value.value() instanceof Number)){
			return ((Double) value.value.value()).compareTo((Double) this.value.value());
		}

		return 0;
	}

	@Override
	public boolean equals(final Object comparable) {

		QoCMetricValue<T> metricValue;

		if (comparable instanceof QoCMetricValue<?>) {
			metricValue = (QoCMetricValue<T>) comparable;
			return metricValue.id.equals(id) && metricValue.creationDate.equals(creationDate)
						&& metricValue.modificationDate.equals(modificationDate) && metricValue.value.equals(value)
					&& metricValue.indicator.equals(indicator) && metricValue.metricDefinition.equals(metricDefinition);
		}

		return false;
	}

	public QAttribut<Date> getCreationDate() {
		return creationDate;
	}

	public Date creationDate() { return (Date) creationDate.value(); }

	public QoCMetricDefinition getDefinition() {
		return metricDefinition;
	}

	public QAttribut<Integer> getId() {
		return id;
	}

	public Integer id() { return (Integer) id.value(); }

	public QoCIndicator getIndicator() {
		return indicator;
	}

	public QAttribut<Date> getModificationDate() {
		return modificationDate;
	}

	public Date modificationDate() { return (Date) modificationDate.value();}

	public QAttribut<T> getValue() {
		return value;
	}

	public Object value() {
		return value.value();
	}

	public void setCreationDate(final Date creationDate) {
		this.creationDate.setValue(creationDate);
	}

	public void setCreationDate(final QAttribut<Date> creationDate) {
		this.creationDate = creationDate;
	}

	public void setId(final Integer id) {
		this.id.setValue(id);
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
		this.modificationDate.setValue(modificationDate);
	}

	public void setModificationDate(final QAttribut<Date> modificationDate) {
		this.modificationDate = modificationDate;
	}

	public void setObjectValue(final T value) {
		this.value.setValue(value);
	}

	public void setValue(final QAttribut<T> value) {
		this.value = value;
	}

	@Override
	public String toString() {
		final QoCMetricDefinition definition = (QoCMetricDefinition) metricDefinition;

		if (null != definition && null != definition.getUnit().value()) {
			return getClass().getSimpleName() + " : " + value() + " " + definition.getUnit().value();
		} else {
			return getClass().getSimpleName() + " : " + value() + " ";
		}
	}

 */
}
