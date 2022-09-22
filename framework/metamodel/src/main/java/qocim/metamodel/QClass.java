package qocim.metamodel;

public class QClass {

	public final String name;
	private final QList attributs;
	private QClass container;

	public QClass() {

		this.name = this.getClass().getSimpleName();
		attributs = new QList("Test list", this);
		container = this;
	}

	public QClass(final String name) {

		this.name = name;
		attributs = new QList("Test list", this);
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

	public boolean add(final String name, final Object value) {
		return attributs.add(new QAttribut<>(name, this, value));
	}

	public QAttribut<?> get(final String name) {
		return attributs.get(name);
	}

	public boolean remove(final String name) {
		QAttribut<?> attribut = get(name);
		return attributs.remove(attribut);
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
