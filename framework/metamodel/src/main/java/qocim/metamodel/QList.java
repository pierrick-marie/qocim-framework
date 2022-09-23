package qocim.metamodel;

import qocim.utils.logs.QoCIMLogger;

import java.util.LinkedList;

public class QList {

	public final String name;
	public final QClass container;
	private final LinkedList<QAttribut<?>> elements;

	private QList() {
		name = "";
		container = null;
		elements = new LinkedList<>();
	}

	public QList(final String name, final QClass container) {
		this.name = name;
		this.container = container;
		elements = new LinkedList<>();
	}

	@Override
	public boolean equals(final Object comparable) {

		QList compareList;

		if (comparable instanceof QList) {
			compareList = (QList) comparable;

			if (!compareList.name.equals(name)) {
				return false;
			}

			for (final QAttribut<?> object : elements) {
				if (null == compareList.getElement(object.name)) {
					return false;
				}
			}

			for (final QAttribut<?> object : compareList.elements) {
				if (null == getElement(object.name)) {
					return false;
				}
			}

			return true;
		}

		return false;
	}

	public QAttribut<?> getElement(final String elementName) {

		for (QAttribut<?> attribut : elements) {
			if (attribut.name.equals(elementName)) {
				return attribut;
			}
		}

		return null;
	}

	public Boolean addElement(final QAttribut<?> element) {
		return elements.add(element);
	}

	public Boolean removeElement(final String elementName) {
		QAttribut<?> searchedElement = getElement(elementName);
		if (null != searchedElement) {
			return elements.remove(searchedElement);
		}
		return false;
	}

	public Boolean setElement(final String elementName, final Object value) {
		QAttribut<?> attribut;
		attribut = getElement(elementName);
		if (null != attribut) {
			attribut.setObjectValue(value);
			return true;
		} else {
			QoCIMLogger.error("QList.setAttribut: access to null object");
			return false;
		}
	}

	public int nbElements() {
		return elements.size();
	}

	public String toString() {
		return name;
	}
}
