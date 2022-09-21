package qocim.metamodel;

import qocim.utils.logs.QoCIMLogger;

import java.lang.reflect.Field;

public class QClass {

	private String qName;

	public QClass() {

		setQName(this.getClass().getSimpleName());
	}

	public QClass(final String qName) {

		setQName(qName);
	}

	@Override
	public boolean equals(final Object comparable) {

		QClass classComparable;

		if (comparable instanceof QClass) {
			classComparable = (QClass) comparable;
			return classComparable.qName.equals(qName);
		}

		return false;
	}

	public String getQName() {
		if (null == qName) {
			QoCIMLogger.debug("QClass.getQName: trying to access to a null object");
		}
		return qName;
	}

	public void setQName(final String qName) {
		this.qName = qName;
	}

	protected Object inspectField(final String fieldName){

		try {
			Field field = this.getClass().getDeclaredField(fieldName);
			return field.get(this);
		} catch (NoSuchFieldException exception) {
			QoCIMLogger.info(exception.getMessage());
		} catch (IllegalAccessException exception) {
			QoCIMLogger.info(exception.getMessage());
		}
		return null;
	}
}
