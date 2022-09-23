package qocim.model;

import qocim.metamodel.QClass;

import java.util.LinkedList;
import java.util.List;

public class QoCCriterion extends QClass {

	private static final String ID = "id";
	private static final String NAME = "name";
	private static final String DEFINITIONS = "definitions";

	public QoCCriterion(final String name, final String id) {
		super(name);
		add(ID, id);
		add(NAME, "");
		add(DEFINITIONS, new LinkedList<QoCDefinition>());
	}

	public String id() {
		return (String) get(ID);
	}

	public String name() {
		return (String) get(NAME);
	}

	public List<QoCDefinition> qocDefinitions() {
		return (List<QoCDefinition>) get(DEFINITIONS);
	}

	public QoCCriterion addQoCDefinition(final QoCDefinition definition) {
		definition.setContainer(this);
		qocDefinitions().add(definition);
		return this;
	}

	public QoCCriterion removeQoCDefinition(final QoCDefinition definition) {
		definition.setContainer(null);
		qocDefinitions().remove(definition);
		return this;
	}
}
