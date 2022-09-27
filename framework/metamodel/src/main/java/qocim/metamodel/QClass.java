package qocim.metamodel;

import qocim.utils.logs.QoCIMLogger;

import java.util.LinkedList;
import java.util.List;

public class QClass {

	public final String name;

	private final List<QAttribut<?>> attributs;
	private QClass container;

	public QClass(final String name) {

		this.name = name;
		attributs = new LinkedList<>();
		container = this;
	}

	@Override
	public boolean equals(final Object comparable) {

		QClass classComparable;

		if (comparable instanceof QClass) {
			classComparable = (QClass) comparable;
			return classComparable.name.equals(name);
		}

		return false;
	}

	public Boolean add(final String name, final Object value) {
		return attributs.add(new QAttribut<>(name, this, value));
	}

	public Object get(final String name) {

		QAttribut<?> element = getAttribut(name);
		if (null != element) {
			return element.value();
		}
		return null;
	}

	public Boolean set(final String name, final Object value) {
		QAttribut<?> attribut;
		attribut = getAttribut(name);
		if (null != attribut) {
			attribut.setObjectValue(value);
			return true;
		} else {
			QoCIMLogger.error("QList.setAttribut: access to null object");
			return false;
		}
	}

	public Boolean remove(final String name) {
		QAttribut<?> searchedElement = getAttribut(name);
		if (null != searchedElement) {
			return attributs.remove(searchedElement);
		}
		return false;
	}

	public QClass container() {
		return container;
	}

	public QClass setContainer(final QClass container) {
		this.container = container;
		return this;
	}

	private QAttribut<?> getAttribut(final String elementName) {
		for (QAttribut<?> attribut : attributs) {
			if (attribut.name.equals(elementName)) {
				return attribut;
			}
		}
		return null;
	}

	public String toString() {
		return name;
	}
}
