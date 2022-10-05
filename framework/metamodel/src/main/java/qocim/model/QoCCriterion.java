package qocim.model;

import qocim.metamodel.QClass;

import java.util.LinkedList;
import java.util.List;

public class QoCCriterion extends QClass {

	public static final String ID = "id";
	public static final String DEFINITIONS = "definitions";
	public static final String TO_STRING = "QoC criterion: ";

	public QoCCriterion(final String name, final String id) {
		super(name);
		add(ID, id);
		add(DEFINITIONS, new LinkedList<QoCDefinition>());
	}

	public String id() {
		return (String) get(ID);
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

	public QoCDefinition getQoCDefinitionById(final String definitionId) {
		for (QoCDefinition definition: qocDefinitions()) {
			if (definition.id().equals(definitionId)) {
				return definition;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return TO_STRING + name + " " + id();
	}
}
