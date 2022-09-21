package qocim.model;

import qocim.metamodel.QList;
import qocim.metamodel.QAttribut;
import qocim.metamodel.QClass;

public class QoCCriterion extends QClass {

	private QAttribut<String> name;
	private QAttribut<String> id;
	private QoCIndicator indicator;

	private QList<QoCMetricDefinition> metricDefinitions;

	public QoCCriterion() {

		super();

		setName(new QAttribut<String>("name", this, ""));
		setId(new QAttribut<String>("id", this, ""));
		setMetricDefinitions(new QList<QoCMetricDefinition>("definitions"));
	}

	public QoCCriterion(final QAttribut<String> name, final QAttribut<String> id, final QoCIndicator indicator,
			final QList<QoCMetricDefinition> metricDefinitions) {

		super();

		setName(name);
		setId(id);
		setIndicator(indicator);
		setMetricDefinitions(metricDefinitions);
	}

	private String parseID(final String[] id){

		StringBuilder textID = new StringBuilder();

		textID.append("{");
		for(String i : id) {
			textID.append("\"").append(i).append("\"");
			if(!i.equals(id[id.length-1])){
				textID.append(",");
			}
		}
		textID.append("}");

		return textID.toString();
	}

	public void addMetricDefinition(final QoCMetricDefinition metricDefinition) {
		metricDefinitions.add(metricDefinition);
	}

	@Override
	public boolean equals(final Object comparable) {

		QoCCriterion criterion;

		if (comparable instanceof QoCCriterion) {
			criterion = (QoCCriterion) comparable;
			return criterion.id.equals(id) && criterion.indicator.equals(indicator);
		}

		return false;
	}

	public QAttribut<String> getId() {
		return id;
	}

	public String id(){ return (String) id.getObject();}

	public QoCIndicator getIndicator() {
		return indicator;
	}

	public QList<QoCMetricDefinition> getMetricDefinitions() {
		return metricDefinitions;
	}

	public QAttribut<String> getName() {
		return name;
	}

	public String name() {return (String) name.getObject(); }

	public void setId(final QAttribut<String> id) {
		this.id = id;
	}

	public void setId(final String id) {
		this.id.setObject(id);
	}

	public void setIndicator(final QoCIndicator indicator) {
		this.indicator = indicator;
	}

	public void setMetricDefinitions(final QList<QoCMetricDefinition> metricDefinitions) {
		this.metricDefinitions = metricDefinitions;
	}

	public void setName(final QAttribut<String> name) {
		this.name = name;
	}

	public void setName(final String name) {
		this.name.setObject(name);
	}

	@Override
	public String toString() {
		return "QoC criterion: " + getId().getObject().toString();
	}
}
