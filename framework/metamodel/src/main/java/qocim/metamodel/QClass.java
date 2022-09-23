package qocim.metamodel;

public class QClass {

	public final String name;
	private final QList attributs;
	private QClass container;

	public QClass() {

		this.name = this.getClass().getSimpleName();
		attributs = new QList("QClass attributs", this);
		container = this;
	}

	public QClass(final String name) {

		this.name = name;
		attributs = new QList(name + " attributs", this);
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
		return attributs.addElement(new QAttribut<>(name, this, value));
	}

	public Object get(final String name) {

		QAttribut<?> element = attributs.getElement(name);
		if (null != element) {
			return element.value();
		}
		return null;
	}

	public Boolean set(final String name, final Object value) {
		return attributs.setElement(name, value);
	}

	public Boolean remove(final String name) {
		return attributs.removeElement(name);
	}

	public String toString() {
		return name;
	}

	public QClass container() {
		return container;
	}

	public QClass setContainer(final QClass container) {
		this.container = container;
		return this;
	}
}
