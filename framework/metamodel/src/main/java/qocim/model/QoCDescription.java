package qocim.model;

import java.util.List;

import qocim.metamodel.QList;
import qocim.metamodel.QAttribut;
import qocim.metamodel.QClass;

public class QoCDescription extends QClass {

	private QList<String> listKeywords;

	private QAttribut<String> name;
	private QAttribut<String> informalDescription;

	private QoCMetricDefinition definition;

	public QoCDescription() {

		super();

		setInformalDescription(new QAttribut<String>("informalDescription", this, ""));
		setListKeywords(new QList<>("listKeywords"));
	}

	public QoCDescription(final QList<String> listKeyword, final QAttribut<String> informationDescription,
                          final QoCMetricDefinition definition) {
		super();

		setListKeywords(listKeyword);
		setInformalDescription(informationDescription);
		setDefinition(definition);
	}

	private void parseKeywords(final String[] keywords){
		for(String keyword : keywords) {
			addKeyword(keyword);
		}
	}

	public void addKeyword(final String keyWord) {
		listKeywords.add(keyWord);

	}

	@Override
	public boolean equals(final Object comparable) {

		QoCDescription description;

		if (comparable instanceof QoCDescription) {
			description = (QoCDescription) comparable;
			return description.listKeywords.equals(listKeywords)
					&& description.informalDescription.equals(informalDescription)
					&& description.definition.equals(definition);
		}

		return false;
	}

	public QoCMetricDefinition getDefinition() {
		return definition;
	}

	public QAttribut<String> getInformalDescription() {
		return informalDescription;
	}

	public String informalDescription() {
		return (String) informalDescription.getObject();
	}

	public QList<String> getListKeywords() {
		return listKeywords;
	}

	public List<String> listKeywords() { return listKeywords; }

	public QAttribut<String> getName() {
		return name;
	}

	public String name() { return (String) name.getObject();}

	public void setDefinition(final QoCMetricDefinition definition) {
		this.definition = definition;
	}

	public void setInformalDescription(final QAttribut<String> informalDescription) {
		this.informalDescription = informalDescription;
	}

	public void setInformalDescription(final String informalDescription) {
		this.informalDescription.setObject(informalDescription);
	}

	public void setName(final QAttribut<String> name) {
		this.name = name;
	}

	public void setName(final String name) {
		this.name.setObject(name);
	}

	public void setListKeywords(final QList<String> listKeywords) {
		this.listKeywords = listKeywords;

	}

	@Override
	public String toString() {
		return "QoC description: " + getInformalDescription().getObject().toString();
	}
}
