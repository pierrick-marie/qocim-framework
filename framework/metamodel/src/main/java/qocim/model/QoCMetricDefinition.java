package qocim.model;

import java.net.URI;
import java.net.URISyntaxException;

import javax.measure.unit.Unit;

import qocim.metamodel.QClass;
import qocim.metamodel.QList;
import qocim.metamodel.QAttribut;
import qocim.utils.logs.QoCIMLogger;

public class QoCMetricDefinition extends QClass {

	private QAttribut<String> name;
	private QAttribut<String> id;
	private QAttribut<Boolean> isInvariant;
	private QAttribut<Unit> unit;
	private QAttribut<URI> providerUri;
	private QAttribut<Order> direction;
	private QAttribut<Boolean> defaultDefinition;

	private QoCMetricValue minValue;
	private QoCMetricValue maxValue;
	private QoCDescription description;
	private QoCCriterion criterion;


	private QList<QoCMetricValue> metricValues;
	private QList<QoCMetricDefinition> primitiveDefinitions;
	private QList<QoCMetricDefinition> compositeDefinitions;

	public QoCMetricDefinition() {

		super();

		setName(new QAttribut<String>("name", this, ""));
		setId(new QAttribut<String>("id", this, ""));
		setIsInvariant(new QAttribut<Boolean>("is invariant", this, false));
		setUnit(new QAttribut<Unit>("unit", this, Unit.ONE));
		setProviderUri(new QAttribut<URI>("provider uri", this, parseURI("")));
		setDirection(new QAttribut<Order>("direction", this, Order.UNKNOWN));
		setDefaultDefinition(new QAttribut<Boolean>("default definition", this, false));

		setMetricValues(new QList<>("metric-values"));
		setPrimitiveDefinitions(new QList<>("primitive-definitions"));
		setCompositeDefinitions(new QList<>("composite-definitions"));
	}


	public QoCMetricDefinition(final QAttribut<String> id, final QAttribut<Boolean> isInvariant, final QAttribut<Unit> unit,
							   final QAttribut<URI> providerUri, final QoCMetricValue minValue, final QoCMetricValue maxValue, final QAttribut<Order> direction,
							   final QAttribut<Boolean> defaultDefinition, final QoCDescription description,
							   final QoCCriterion criterion, final QList<QoCMetricValue> metricValues, final QList<QoCMetricDefinition> primitiveDefinitions,
							   final QList<QoCMetricDefinition> compositeDefinitions) {

		super();

		setId(id);
		setIsInvariant(isInvariant);
		setUnit(unit);
		setProviderUri(providerUri);
		setMinValue(minValue);
		setMaxValue(maxValue);
		setDirection(direction);

		setDefaultDefinition(defaultDefinition);

		setDescription(description);
		setCriterion(criterion);

		setMetricValues(metricValues);
		setPrimitiveDefinitions(primitiveDefinitions);
		setCompositeDefinitions(compositeDefinitions);
	}

	private URI parseURI(final String stringUri){

		URI uri = null;

		try {
			uri = new URI(stringUri);
		} catch (URISyntaxException exception) {
			QoCIMLogger.error(exception.getMessage());
		}

		return uri;
	}

	public void addPrimitiveDefinitions(final QoCMetricDefinition primitiveDefinition) {
		primitiveDefinitions.add(primitiveDefinition);
	}
	
	public void addCompositeDefinitions(final QoCMetricDefinition compositeDefinition) {
		compositeDefinitions.add(compositeDefinition);
	}

	public void addMetricValues(final QoCMetricValue metricValue) {
		metricValues.add(metricValue);
	}

	@Override
	public boolean equals(final Object comparable) {

		QoCMetricDefinition metricDefinition;

		if (comparable instanceof QoCMetricDefinition) {
			metricDefinition = (QoCMetricDefinition) comparable;
			return metricDefinition.id.equals(id) && metricDefinition.isInvariant.equals(isInvariant)
					&& metricDefinition.unit.equals(unit) && metricDefinition.providerUri.equals(providerUri)
					&& metricDefinition.minValue.equals(minValue) && metricDefinition.maxValue.equals(maxValue)
					&& metricDefinition.direction.equals(direction)
					&& metricDefinition.defaultDefinition.equals(defaultDefinition)
					&& metricDefinition.description.equals(description) && metricDefinition.criterion.equals(criterion);
		}

		return false;
	}

	public QList<QoCMetricDefinition> getCompositeDefinitions() {
		return compositeDefinitions;
	}

	public QoCCriterion getCriterion() {
		return criterion;
	}

	public QAttribut<Boolean> getDefaultDefinition() {
		return defaultDefinition;
	}

	public Boolean defaultDefinition(){return (Boolean) defaultDefinition.getObject();}

	public QoCDescription getDescription() {
		return description;
	}

	public QAttribut<Order> getDirection() {
		return direction;
	}

	public Order direction() { return (Order) direction.getObject(); }

	public QAttribut<String> getId() {
		return id;
	}

	public String id() {return (String) id.getObject(); }

	public QAttribut<Boolean> getIsInvariant() {
		return isInvariant;
	}

	public Boolean isInvariant() { return (Boolean) isInvariant.getObject(); }

	public QoCMetricValue getMaxValue() {
		return maxValue;
	}

	public QList<QoCMetricValue> getMetricValues() {
		return metricValues;
	}

	public QoCMetricValue getMinValue() {
		return minValue;
	}

	public QAttribut<String> getName() {
		return name;
	}

	public String name() { return (String) name.getObject(); }

	public QList<QoCMetricDefinition> getPrimitiveDefinitions() {
		return primitiveDefinitions;
	}

	public QAttribut<URI> getProviderUri() {
		return providerUri;
	}

	public URI providerUri() { return (URI) providerUri.getObject(); }

	public QAttribut<Unit> getUnit() {
		return unit;
	}

	public Unit unit() { return (Unit) unit.getObject(); }

	public void setCompositeDefinitions(final QList<QoCMetricDefinition> compositeDefinitions) {
		this.compositeDefinitions = compositeDefinitions;
	}

	public void setCriterion(final QoCCriterion criterion) {
		this.criterion = criterion;
	}

	public void setDefaultDefinition(final Boolean defaultDefinition) {
		this.defaultDefinition.setObject(defaultDefinition);
	}

	public void setDefaultDefinition(final QAttribut<Boolean> defaultDefinition) {
		this.defaultDefinition = defaultDefinition;
	}

	public void setDescription(final QoCDescription description) {
		this.description = description;
	}

	public void setDirection(final Order direction) {
		this.direction.setObject(direction);
	}

	public void setDirection(final QAttribut<Order> direction) {
		this.direction = direction;
	}

	public void setId(final QAttribut<String> id) {
		this.id = id;
	}

	public void setId(final String id) {
		this.id.setObject(id);
	}

	public void setIsInvariant(final Boolean isInvariant) {
		this.isInvariant.setObject(isInvariant);
	}

	public void setIsInvariant(final QAttribut<Boolean> isInvariant) {
		this.isInvariant = isInvariant;
	}

	public void setMaxValue(final QoCMetricValue maxValue) {
		this.maxValue = maxValue;
	}

	public void setMetricValues(final QList<QoCMetricValue> metricValues) {
		this.metricValues = metricValues;
	}

	public void setMinValue(final QoCMetricValue minValue) {
		this.minValue = minValue;
	}

	public void setName(final QAttribut<String> name) {
		this.name = name;
	}

	public void setName(final String name) {
		this.name.setObject(name);
	}

	public void setPrimitiveDefinitions(final QList<QoCMetricDefinition> primitiveDefinitions) {
		this.primitiveDefinitions = primitiveDefinitions;
	}

	public void setProviderUri(final QAttribut<URI> providerUri) {
		this.providerUri = providerUri;
	}

	public void setProviderUri(final URI providerUri) {
		this.providerUri.setObject(providerUri);
	}

	public void setUnit(final QAttribut<Unit> unit) {
		this.unit = unit;
	}

	public void setUnit(final Unit<?> unit) {
		this.unit.setObject(unit);
	}

	@Override
	public String toString() {
		return "QoC metric definition: " + getId().getObject().toString();
	}
}
