package qocim.model;

import qocim.information.QInformation;
import qocim.metamodel.QClass;

import java.util.Date;
import java.util.LinkedList;

/**
 *
 */
public class QoCValue<T> extends QClass {

	private static final String ID = "id";
	private static final String CREATION_DATE = "creation date";
	private static final String MODIFICATION_DATE = "modification date";
	private static final String VALUE = "value";
	private static final String TO_STRING = "QoC Value: ";

	public QoCValue(final String name, final Integer id) {
		super(name + " #" + id);
		add(ID, id);
		add(CREATION_DATE, new Date());
		add(MODIFICATION_DATE, new Date());
		add(VALUE, null);
	}

	public Integer id() {
		return (Integer) get(ID);
	}

	public Date creationDate() {
		return (Date) get(CREATION_DATE);
	}

	public Date modificationDate() {
		return (Date) get(MODIFICATION_DATE);
	}

	public T value() {
		return (T) get(VALUE);
	}

	public QoCValue setValue(final T value) {
		if (set(VALUE, value)) {
			set(MODIFICATION_DATE, new Date());
		}
		return this;
	}

	@Override
	public String toString() {
		return TO_STRING + name;
	}
}
