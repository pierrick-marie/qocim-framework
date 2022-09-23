package qocim.metamodel;

public class QAttribut<T> {

	public final String name;
	private T value;
	private QClass container;

	private QAttribut() {
		name = "";
		container = null;
		value = null;
	}

	public QAttribut(final String name, final QClass container, final T value) {
		this.name = name;
		this.container = container;
		this.value = value;
	}

	@Override
	public boolean equals(final Object comparable) {

		QAttribut<?> qAttribut = null;
		String name = "";

		if (comparable instanceof QAttribut<?>) {
			qAttribut = (QAttribut<?>) comparable;
			return qAttribut.name.equals(this.name);
		}

		if (comparable instanceof String) {
			name = (String) comparable;
			return this.name.equals(name);
		}

		return false;
	}

	public T value() {
		return value;
	}

	public QAttribut<?> setTValue(final T value) {
		this.value = value;
		return this;
	}

	public Boolean setObjectValue(final Object value) {
		if (value.getClass().isInstance(this.value)) {
			this.value = (T) value;
			return true;
		}
		return false;
	}

	public String toString() {
		return name;
	}

	public QAttribut<?> setContainer(final QClass container) {
		this.container = container;
		return this;
	}

	public QClass container() {
		return container;
	}
}
