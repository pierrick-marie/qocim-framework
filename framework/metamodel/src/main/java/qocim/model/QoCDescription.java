package qocim.model;

import java.util.LinkedList;
import java.util.List;

import qocim.metamodel.QClass;

public class QoCDescription extends QClass {

	private static final String NAME = "qoc value";
	private static final String KEYWORDS = "keywords";
	private static final String DESCRIPTION = "description";

	public QoCDescription(final String name) {
		super(name);
		add(NAME, name);
		add(KEYWORDS, new LinkedList<String>());
		add(DESCRIPTION, "");
	}

	public String name() {
		return (String) get(NAME);
	}

	public QoCDescription setName(final String name) {
		set(NAME, name);
		return this;
	}

	public List<String> keywords() {
		return (List<String>) get(KEYWORDS);
	}

	public QoCDescription addKeyword(final String name) {
		keywords().add(name);
		return this;
	}

	public QoCDescription removeKeyword(final String name) {
		keywords().remove(name);
		return this;
	}

	public String description() {
		return (String) get(DESCRIPTION);
	}

	public QoCDescription setDescription(final String description) {
		set(DESCRIPTION, description);
		return this;
	}
}
