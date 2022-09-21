package qocim.metamodel;

import qocim.utils.logs.QoCIMLogger;

public class QAttribut<T> {

	private String name;
	private T object;
	private Object container;

	public QAttribut() {
		setName("");
		setObject(null);
		setContainer(null);
	}

	public QAttribut(final String name, final Object container, final T value) {
		setName(name);
		setContainer(container);
		setObject(value);
	}

	@Override
	public boolean equals(final Object comparable) {

		QAttribut attribut;

		if (comparable instanceof QAttribut) {
			attribut = (QAttribut) comparable;
			return attribut.name.equals(name) && attribut.object.equals(object)
					&& attribut.container.equals(container);
		}

		return false;
	}

	public Object getContainer() {
		if (null == container) {
			QoCIMLogger.info("QAttribut.getContainer: trying to access to a null object");
		}
		return container;
	}

	public String getName() {
		if (null == name) {
			QoCIMLogger.info("QAttribut.getName: trying to access to a null object");
		}
		return name;
	}

	public Object getObject() {
		if (null == object) {
			QoCIMLogger.info("QAttribut.getObject: trying to access to a null object");
		}
		return object;
	}

	public void setContainer(final Object container) {
		this.container = container;
	}

	public void setName(final String name) {
		if (null == this.name || this.name.isEmpty()) {
			this.name = name;
		}
	}

	public void setObject(final T object) {
		this.object = object;
	}
}
