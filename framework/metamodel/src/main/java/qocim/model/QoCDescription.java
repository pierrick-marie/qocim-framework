package qocim.model;

import java.util.LinkedList;
import java.util.List;

import qocim.metamodel.QClass;

public class QoCDescription extends QClass {

	public static final String KEYWORDS = "keywords";
	public static final String DESCRIPTION = "description";
	public static final String TO_STRING = "QoC Description: ";

	public QoCDescription(final String name) {
		super(name);
		add(KEYWORDS, new LinkedList<String>());
		add(DESCRIPTION, "");
	}

	public List<String> keywords() {
		return (List<String>) get(KEYWORDS);
	}

	public String description() {
		return (String) get(DESCRIPTION);
	}

	public QoCDescription setDescription(final String description) {
		set(DESCRIPTION, description);
		return this;
	}

	@Override
	public String toString() {
		return TO_STRING + name;
	}
}
