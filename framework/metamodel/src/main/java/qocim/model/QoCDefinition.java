package qocim.model;

import qocim.metamodel.QClass;

import javax.measure.unit.Unit;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class QoCDefinition extends QClass {

	private static final String ID = "id";
	private static final String NAME = "name";
	private static final String INVARIANT = "invariant";
	private static final String UNIT = "unit";
	private static final String PROVIDER_URI = "provider uri";
	private static final String DIRECTION = "direction";
	private static final String DEFAULT = "default";
	private static final String MIN_VALUE = "min value";
	private static final String MAX_VALUE = "max value";
	private static final String DESCRIPTION = "description";
	private static final String QOC_VALUES = "qoc values";
	private static final String RELATED_DEFINITIONS = "related definitions";

	public QoCDefinition(final String name, final String id) {
		super(name);
		add(ID, id);
		add(NAME, name);
		add(INVARIANT, false);
		add(UNIT, Unit.ONE);
		try {
			add(PROVIDER_URI, new URI(""));
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
		add(DIRECTION, Direction.UNKNOWN);
		add(DEFAULT, true);
		add(MIN_VALUE, null);
		add(MAX_VALUE, null);
		add(DESCRIPTION, null);
		add(QOC_VALUES, new LinkedList<QoCValue>());
		add(RELATED_DEFINITIONS, new LinkedList<QoCDefinition>());
	}

	public String id() {
		return (String) get(ID);
	}

	public String name() {
		return (String) get(NAME);
	}

	public QoCDefinition setName(final String name) {
		set(NAME, name);
		return this;
	}

	public Unit unit() {
		return (Unit) get(UNIT);
	}

	public QoCDefinition setUnit(final Unit unit) {
		set(UNIT, unit);
		return this;
	}

	public URI uri() {
		return (URI) get(PROVIDER_URI);
	}

	public QoCDefinition setUri(final URI uri) {
		set(PROVIDER_URI, uri);
		return this;
	}

	public Direction direction() {
		return (Direction) get(DIRECTION);
	}

	public QoCDefinition setDirection(final Direction direction) {
		set(DIRECTION, direction);
		return this;
	}

	public Boolean isDefault() {
		return (Boolean) get(DEFAULT);
	}

	public QoCDefinition setIsDefault(final Boolean isDefault) {
		set(DEFAULT, isDefault);
		return this;
	}

	public QoCValue minValue() {
		return (QoCValue) get(MIN_VALUE);
	}

	public QoCDefinition setMinValue(final QoCValue value) {
		set(MIN_VALUE, value);
		return this;
	}

	public QoCValue maxValue() {
		return (QoCValue) get(MAX_VALUE);
	}

	public QoCDefinition setMaxValue(final QoCValue value) {
		set(MAX_VALUE, value);
		return this;
	}

	public String desription() {
		return (String) get(DESCRIPTION);
	}

	public QoCDefinition setDescription(final String description) {
		set(DESCRIPTION, description);
		return this;
	}

	public List<QoCValue> values() {
		return (List<QoCValue>) get(QOC_VALUES);
	}

	public QoCDefinition addValue(final QoCValue value) {
		value.setContainer(this);
		values().add(value);
		return this;
	}

	public QoCDefinition removeValue(final QoCValue value) {
		value.setContainer(null);
		values().remove(value);
		return this;
	}

	public Boolean isInvariant() {
		return (Boolean) get(INVARIANT);
	}

	public QoCDefinition setIsInvariant(final Boolean isInvariant) {
		set(INVARIANT, isInvariant);
		return this;
	}

	public List<QoCDefinition> relatedDefinitions() {
		return (List<QoCDefinition>) get(RELATED_DEFINITIONS);
	}

	public QoCDefinition addRelatedDefinition(final QoCDefinition definition) {
		relatedDefinitions().add(definition);
		return this;
	}

	public QoCDefinition removeRelatedDefinition(final QoCDefinition definition) {
		relatedDefinitions().remove(definition);
		return this;
	}
}
