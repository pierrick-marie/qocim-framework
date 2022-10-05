package qocim.model;

import qocim.metamodel.QClass;

import java.util.Date;

/**
 *
 */
public class QoCValue<T> extends QClass {

	public static final String ID = "id";
	public static final String CREATION_DATE = "creation date";
	public static final String VALUE = "value";
	public static final String DEFINITION_ID = "definition id";
	public static final String TO_STRING = "QoC Value: ";

	public static final String DATE_FORMAT = "E MMM dd HH:mm:ss yyyy";

	public QoCValue(final String name, final Integer id, final T value) {
		super(name);
		add(ID, id);
		add(CREATION_DATE, new Date());
		add(VALUE, value);
		add(DEFINITION_ID, "");
	}

	public Integer id() {
		return (Integer) get(ID);
	}

	public Date creationDate() {
		return (Date) get(CREATION_DATE);
	}

	public T value() {
		return (T) get(VALUE);
	}

	public String definitionId() {
		return (String) get(DEFINITION_ID);
	}

	public QoCValue<T> setDefinitionId(final String defintionId) {
		set(DEFINITION_ID, defintionId);
		return this;
	}

	@Override
	public String toString() {
		return TO_STRING + name;
	}
}
