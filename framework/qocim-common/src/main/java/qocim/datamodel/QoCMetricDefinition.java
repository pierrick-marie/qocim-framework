/**
 * This file is part of the QoCIM middleware.
 *
 * Copyright (C) 2014 IRIT, Télécom SudParis
 *
 * The QoCIM software is free software: you can redistribute it and/or modify
 * It under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * The QoCIM software platform is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * See the GNU Lesser General Public License 
 * for more details: http://www.gnu.org/licenses
 *
 * Initial developer(s): Pierrick MARIE
 * Contributor(s): 
 */
package qocim.datamodel;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;

/**
 * QoCMetricDefinition configures the production of QoCMetricValues. The
 * definition can refers to other (primitive) definition and (composite)
 * definition.
 *
 * A primitive definition represents a low level criterion. A composite
 * definition represents a high level criterion.
 *
 * @see qocim.datamodel.QoCCriterion
 * @see qocim.datamodel.QoCMetricValue
 * @see qocim.datamodel.Description
 * @see qocim.datamodel.Order
 *
 * @author Pierrick MARIE
 */
public class QoCMetricDefinition extends QoCIM {

    // # # # # # CONSTANTS # # # # #

    /**
     * The default value of the field isInvariant.
     */
    private static final boolean DEFAULT_ISINVARIANT = false;
    /**
     * The default value of the field minValue.
     */
    private static final double DEFAULT_MINVALUE = 0;
    /**
     * The default value of the attribute maxValue.
     */
    private static final double DEFAULT_MAXVALUE = 0;
    /**
     * The default value of the attribute direction.
     */
    private static final Order DEFAULT_ORDER = Order.UNKNOWN;

    // # # # # # PROTECTED VARIABLES # # # # #

    /**
     * An id of the definition referenced by the QoCCriterion.
     */
    @XmlAttribute
    @XmlID
    String id;
    /**
     * Semantic information about the how interpret values of QoC. If
     * isInvariant is True, the value of the QoC have to be composed of discrete
     * values (a set of well defined and non modifiable QoCMetricValue). Else,
     * the value of the QoC is continuous and is modifiable values.
     */
    @XmlAttribute
    Boolean isInvariant;
    /**
     * The unit of the QoC value.
     */
    @XmlAttribute
    String unit;
    /**
     * The URI of the entity that computes the QoC value.
     */
    @XmlAttribute
    String providerUri;
    /**
     * The minimum value of the QoC.
     */
    @XmlAttribute
    Double minValue;
    /**
     * The maximal value of the QoC.
     */
    @XmlAttribute
    Double maxValue;
    /**
     * Indication to know how interpret the value of the QoC.
     *
     * @see qocim.datamodel.Order
     */
    @XmlAttribute
    Order direction;
    /**
     * Verbatim description of the definition of the QoC.
     */
    @XmlElement(name = "description")
    Description description;
    /**
     * The collection of the QoCMetricValue produced with this definition.
     */
    @XmlAttribute(name = "values")
    @XmlIDREF
    public final LinkedList<QoCMetricValue> list_qoCMetricValue;
    /**
     * The list of primitive definitions.
     */
    @XmlAttribute(name = "primitiveDefinition")
    @XmlIDREF
    public final LinkedList<QoCMetricDefinition> list_primitiveQoCMetricDefinition;
    /**
     * The list of composite definitions.
     */
    @XmlAttribute(name = "compositeDefinition")
    @XmlIDREF
    public final LinkedList<QoCMetricDefinition> list_compositeQoCMetricDefinition;

    // # # # # # CONSTRUCTORS # # # # #

    public QoCMetricDefinition() {
	// - - - - - INITIALIZE THE VARIABLES - - - - -
	super();
	id = "";
	isInvariant = DEFAULT_ISINVARIANT;
	unit = "";
	providerUri = "";
	minValue = DEFAULT_MINVALUE;
	maxValue = DEFAULT_MAXVALUE;
	direction = DEFAULT_ORDER;
	list_qoCMetricValue = new LinkedList<QoCMetricValue>();
	list_primitiveQoCMetricDefinition = new LinkedList<QoCMetricDefinition>();
	list_compositeQoCMetricDefinition = new LinkedList<QoCMetricDefinition>();
	description = new Description();
    }

    public QoCMetricDefinition(final QoCMetricDefinition _qoCMetricDefinition) {
	// - - - - - INITIALIZE THE VARIABLES - - - - -
	super(_qoCMetricDefinition);
	id = _qoCMetricDefinition.id;
	isInvariant = _qoCMetricDefinition.isInvariant;
	unit = _qoCMetricDefinition.unit;
	providerUri = _qoCMetricDefinition.providerUri;
	minValue = _qoCMetricDefinition.minValue;
	maxValue = _qoCMetricDefinition.maxValue;
	direction = _qoCMetricDefinition.direction;
	list_qoCMetricValue = _qoCMetricDefinition.list_qoCMetricValue;
	list_primitiveQoCMetricDefinition = _qoCMetricDefinition.list_primitiveQoCMetricDefinition;
	list_compositeQoCMetricDefinition = _qoCMetricDefinition.list_compositeQoCMetricDefinition;
	description = new Description(_qoCMetricDefinition.description);
    }

    // # # # # # PUBLIC METHODS # # # # #

    @Override
    public boolean equals(final Object _comparable) {
	// - - - - - INITIALIZE THE VARIABLES - - - - -
	QoCMetricDefinition qoCMetricDefinition;
	// - - - - - CORE OF THE METHOD - - - - -
	if (_comparable != null && _comparable instanceof QoCMetricDefinition) {
	    qoCMetricDefinition = (QoCMetricDefinition) _comparable;
	    return id.equals(qoCMetricDefinition.id) && isInvariant.equals(qoCMetricDefinition.isInvariant) && unit.equals(qoCMetricDefinition.unit)
		    && providerUri.equals(qoCMetricDefinition.providerUri) && minValue.equals(qoCMetricDefinition.minValue)
		    && maxValue.equals(qoCMetricDefinition.maxValue) && direction.equals(qoCMetricDefinition.direction);
	}
	// - - - - - RETURN STATEMENT - - - - -
	return false;
    }

    @Override
    public int hashCode() {
	// - - - - - RETURN STATEMENT - - - - -
	return id.hashCode();
    }

    public Double computeQoCMetricValue(final String _contextEntityUri, final String _contextObservableUri, final Date _contextObservationDate,
	    final Double _contextObservationValue, final List<QoCMetaData> _list_qoCMetaData) {
	// - - - - - RETURN STATEMENT - - - - -
	return DEFAULT_MINVALUE;
    }

    @Override
    public String toString() {
	// - - - - - INITIALIZE THE VARIABLES - - - - -
	final StringBuffer ret_stringBbuffer = new StringBuffer();
	final Iterator<QoCMetricValue> iterator_qoCMetricValue = list_qoCMetricValue.iterator();
	final Iterator<QoCMetricDefinition> iterator_primitiveQoCMetricDefinition = list_primitiveQoCMetricDefinition.iterator();
	final Iterator<QoCMetricDefinition> iterator_compositeQoCMetricDefinition = list_compositeQoCMetricDefinition.iterator();
	QoCMetricValue nextQoCMetricValue;
	QoCMetricDefinition nextQoCMetricDefinition;
	// - - - - - CORE OF THE METHOD - - - - -
	ret_stringBbuffer.append(super.toString());
	ret_stringBbuffer.append("Id: String= \"" + id + "\"\n");
	ret_stringBbuffer.append("IsInvariant: Boolean= " + isInvariant + "\n");
	ret_stringBbuffer.append("Unit: String= \"" + unit + "\"\n");
	ret_stringBbuffer.append("ProviderUri: String= \"" + providerUri + "\"\n");
	ret_stringBbuffer.append("MinValue: Double= " + minValue + "\n");
	ret_stringBbuffer.append("MaxValue: Double= " + maxValue + "\n");
	ret_stringBbuffer.append("Direction: Order= " + direction + "\n");
	ret_stringBbuffer.append("\nValues: List<QoCMetricValue>= [");
	while (iterator_qoCMetricValue.hasNext()) {
	    nextQoCMetricValue = iterator_qoCMetricValue.next();

	    if (iterator_qoCMetricValue.hasNext()) {
		ret_stringBbuffer.append("\t" + nextQoCMetricValue.toString() + ", ");
	    } else {
		ret_stringBbuffer.append("\t" + nextQoCMetricValue.toString() + " ]\n");
	    }
	}
	ret_stringBbuffer.append("PrimitiveDefinition: List<QoCMetricDefinition>= [");
	while (iterator_primitiveQoCMetricDefinition.hasNext()) {
	    nextQoCMetricDefinition = iterator_primitiveQoCMetricDefinition.next();

	    if (iterator_primitiveQoCMetricDefinition.hasNext()) {
		ret_stringBbuffer.append("\t" + nextQoCMetricDefinition.name + "-" + nextQoCMetricDefinition.id + " ,");
	    } else {
		ret_stringBbuffer.append("\t" + nextQoCMetricDefinition.name + "-" + nextQoCMetricDefinition.id + " ]\n");
	    }
	}
	ret_stringBbuffer.append("CompositeDefinition: List<QoCMetricDefinition>= [");
	while (iterator_compositeQoCMetricDefinition.hasNext()) {
	    nextQoCMetricDefinition = iterator_compositeQoCMetricDefinition.next();

	    if (iterator_compositeQoCMetricDefinition.hasNext()) {
		ret_stringBbuffer.append("\t" + nextQoCMetricDefinition.name + "-" + nextQoCMetricDefinition.id + " ,");
	    } else {
		ret_stringBbuffer.append("\t" + nextQoCMetricDefinition.name + "-" + nextQoCMetricDefinition.id + " ]\n");
	    }
	}
	ret_stringBbuffer.append("\nDescription: " + description + "\n");
	// - - - - - RETURN STATEMENT - - - - -
	return ret_stringBbuffer.toString();
    }

    public String id() {
	return id;
    }

    public QoCMetricDefinition id(final String _id) {
	id = _id;
	return this;
    }

    public Boolean isInvariant() {
	return isInvariant;
    }

    public QoCMetricDefinition isInvariant(final Boolean _isInvariant) {
	isInvariant = _isInvariant;
	return this;
    }

    public String unit() {
	return unit;
    }

    public QoCMetricDefinition unit(final String _unit) {
	unit = _unit;
	return this;
    }

    public String providerUri() {
	return providerUri;
    }

    public QoCMetricDefinition providerUri(final String _providerUri) {
	providerUri = _providerUri;
	return this;
    }

    public Double minValue() {
	return minValue;
    }

    public QoCMetricDefinition minValue(final Double _minValue) {
	minValue = _minValue;
	return this;
    }

    public Double maxValue() {
	return maxValue;
    }

    public QoCMetricDefinition maxValue(final Double _maxValue) {
	maxValue = _maxValue;
	return this;
    }

    public Order direction() {
	return direction;
    }

    public QoCMetricDefinition direction(final Order _direction) {
	direction = _direction;
	return this;
    }

    public Description description() {
	return new Description(description);
    }

    public QoCMetricDefinition description(final Description _description) {
	description = _description;
	return this;
    }
}
