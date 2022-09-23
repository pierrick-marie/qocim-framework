package qocim.model;

import qocim.information.QInformation;
import qocim.metamodel.QClass;

import java.util.Date;
import java.util.LinkedList;

/**
 *
 */
public class QoCValue extends QClass {

	private static final String ID = "id";
	private static final String NAME = "qoc value";
	private static final String CREATION_DATE = "creation date";
	private static final String MODIFICATION_DATE = "modification date";
	private static final String VALUE = "value";

	public QoCValue(final String name, final Integer id) {
		super(NAME + " #" + id);
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

	public QoCValue setCreationDate(final Date creationDate) {
		set(CREATION_DATE, creationDate);
		return this;
	}

	public Date modificationDate() {
		return (Date) get(MODIFICATION_DATE);
	}

	public QoCValue setModificationDate(final Date modificationDate) {
		set(MODIFICATION_DATE, modificationDate);
		return this;
	}

	public Object value() {
		return (Object) get(VALUE);
	}

	public QoCValue setValue(final Object value) {
		set(VALUE, value);
		return this;
	}
}
