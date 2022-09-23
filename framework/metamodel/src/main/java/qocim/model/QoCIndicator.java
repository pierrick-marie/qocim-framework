package qocim.model;

import qocim.information.QInformation;
import qocim.metamodel.QClass;

import java.util.LinkedList;
import java.util.List;

public class QoCIndicator extends QClass {

	private static final String ID = "id";
	private static final String INFORMATION = "informaiton";
	private static final String CRITERIA = "criteria";
	private static final String VALUES = "values";
	private static final String TO_STRING = "QoC Indicator: ";

	public QoCIndicator(final String name, final Integer id) {
		super(name);
		add(ID, id);
		add(INFORMATION, null);
		add(CRITERIA, new LinkedList<QoCCriterion>());
		add(VALUES, new LinkedList<QoCValue>());
	}

	public Integer id() {
		return (Integer) get(ID);
	}

	public QInformation<?> information() {
		return (QInformation<?>) get(INFORMATION);
	}

	public QoCIndicator setInformation(final QInformation<?> information) {
		set(INFORMATION, information);
		return this;
	}

	public List<QoCCriterion> criteria() {
		return (List<QoCCriterion>) get(CRITERIA);
	}

	public QoCIndicator addCriteria(final QoCCriterion criterion) {
		criterion.setContainer(this);
		criteria().add(criterion);
		return this;
	}

	public QoCIndicator removeCriteria(final QoCCriterion criterion) {
		criterion.setContainer(null);
		criteria().remove(criterion);
		return this;
	}

	public List<QoCValue> qocValues() {
		return (List<QoCValue>) get(VALUES);
	}

	public QoCIndicator addQoCValue(final QoCValue value) {
		value.setContainer(this);
		qocValues().add(value);
		return this;
	}

	public QoCIndicator removeQoCValue(final QoCValue value) {
		value.setContainer(null);
		qocValues().remove(value);
		return this;
	}

	@Override
	public String toString() {
		return TO_STRING + id();
	}
}
