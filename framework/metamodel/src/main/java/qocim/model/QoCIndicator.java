package qocim.model;

import qocim.information.QInformation;
import qocim.metamodel.QAttribut;
import qocim.metamodel.QClass;
import qocim.metamodel.QList;
import qocim.utils.logs.QoCIMLogger;

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
		add(CRITERIA, new QList("list " + CRITERIA, this));
		add(VALUES, new QList("list " + VALUES, this));
	}

	public Integer id() {
		return (Integer) get(ID);
	}

	public QInformation<?> information() {
		return (QInformation<?>) get(INFORMATION);
	}

	public QList criteria() {
		return (QList) get(CRITERIA);
	}

	public QList values() {
		return (QList) get(VALUES);
	}

	@Override
	public String toString() {
		return TO_STRING + id();
	}
}
