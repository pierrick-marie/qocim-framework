package qocim.metamodel;

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
				if (null == compareList.get(object.name)) {
					return false;
				}
			}

			for (final QAttribut<?> object : compareList.all()) {
				if (null == get(object.name)) {
					return false;
				}
			}

			return true;
		}

		return false;
	}

	public QAttribut<?> get(final String elementName) {

		for (QAttribut<?> attribut : elements) {
			if (attribut.name.equals(elementName)) {
				return attribut;
			}
		}

		return null;
	}

	public Boolean add(final QAttribut<?> element) {
		return elements.add(element);
	}

	public Boolean remove(final String elementName) {
		QAttribut<?> searchedElement = get(elementName);
		if (null != searchedElement) {
			return elements.remove(searchedElement);
		}
		return false;
	}

	public LinkedList<QAttribut<?>> all() {
		return elements;
	}

	public String toString() {
		return name;
	}
}
