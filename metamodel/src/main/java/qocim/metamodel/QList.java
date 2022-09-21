package qocim.metamodel;

import java.util.LinkedList;
import java.util.List;

import qocim.utils.logs.QoCIMLogger;

public class QList<T> extends LinkedList<T> {

	private String name;

	public QList(final String name) {
		setName(name);
	}

	public QList(final String name, final List<T> objects) {
		setName(name);
	}

	@Override
	public boolean equals(final Object comparable) {

		QList<T> aggregation;

		if (comparable instanceof QList) {
			aggregation = (QList<T>) comparable;

			if (!aggregation.name.equals(name)) {
				return false;
			}

			for (final T object : this) {
				if (!aggregation.contains(object)) {
					return false;
				}
			}

			for (final T object : aggregation) {
				if (!this.contains(object)) {
					return false;
				}
			}

			return true;
		}

		return false;
	}

	public String getName() {
		if (null == name) {
			QoCIMLogger.debug("QAggregation.getName: trying to access to a null object");
		}
		return name;
	}

	public void setName(final String name) {
		if (null == this.name || this.name.isEmpty()) {
			this.name = name;
		}
	}
}
