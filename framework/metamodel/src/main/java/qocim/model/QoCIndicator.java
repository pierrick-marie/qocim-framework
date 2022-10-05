package qocim.model;

import qocim.metamodel.QClass;

import java.util.LinkedList;
import java.util.List;

public class QoCIndicator extends QClass {

	public static final String ID = "id";
	public static final String CRITERIA = "criteria";
	public static final String VALUES = "values";
	public static final String TO_STRING = "QoC Indicator: ";

	public QoCIndicator(final String name, final Integer id) {
		super(name);
		add(ID, id);
		add(CRITERIA, new LinkedList<QoCCriterion>());
		add(VALUES, new LinkedList<QoCValue>());
	}

	public Integer id() {
		return (Integer) get(ID);
	}

	public List<QoCCriterion> qocCriteria() {
		return (List<QoCCriterion>) get(CRITERIA);
	}

	public QoCIndicator addQoCCriterion(final QoCCriterion criterion) {
		criterion.setContainer(this);
		qocCriteria().add(criterion);
		return this;
	}

	public QoCIndicator removeQoCCriteria(final QoCCriterion criterion) {
		criterion.setContainer(null);
		qocCriteria().remove(criterion);
		return this;
	}

	public QoCCriterion getQoCCriterionById(final String criterionId) {
		for (QoCCriterion criterion: qocCriteria()) {
			if (criterion.id().equals(criterionId)) {
				return criterion;
			}
		}
		return null;
	}

	public List<QoCValue<?>> qocValues() {
		return (List<QoCValue<?>>) get(VALUES);
	}

	public QoCIndicator addQoCValue(final QoCValue<?> value) {
		value.setContainer(this);
		qocValues().add(value);
		return this;
	}

	public QoCIndicator removeQoCValue(final QoCValue<?> value) {
		value.setContainer(null);
		qocValues().remove(value);
		return this;
	}

	public QoCValue<?> getQoCValueById(final Integer qocValueId) {
		for (QoCValue<?> value: qocValues()) {
			if (value.id().equals(qocValueId)) {
				return value;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return TO_STRING + name + " " + id();
	}
}
