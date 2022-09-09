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

import java.util.Iterator;
import java.util.LinkedList;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * QoCCriterion contains a set of definitions used to configure the production
 * of QoC values.
 *
 * @see qocim.datamodel.QoCIndicator
 * @see qocim.datamodel.QoCMetricDefinition
 *
 * @author Pierrick MARIE
 */
public class QoCCriterion extends QoCIM {

    // # # # # # PRIVATE VARIABLES # # # # #

    /**
     * The id of the QoC criterion.
     */
    @XmlAttribute
    String id;
    /**
     * The collection of QoCMetricDefinition handled by the QoCCriterion.
     */
    @XmlElement(name = "qocmetricdefinition")
    public final LinkedList<QoCMetricDefinition> list_qoCMetricDefinition;

    // # # # # # CONSTRUCTORS # # # # #

    public QoCCriterion() {
	// - - - - - INITIALIZE THE VARIABLES - - - - -
	super();
	id = "";
	list_qoCMetricDefinition = new LinkedList<QoCMetricDefinition>();
    }

    public QoCCriterion(final QoCCriterion _qoCCriterion) {
	// - - - - - INITIALIZE THE VARIABLES - - - - -
	super(_qoCCriterion);
	id = _qoCCriterion.id;
	list_qoCMetricDefinition = _qoCCriterion.listQoCMetricDefinition();
    }

    // # # # # # PUBLIC METHODS # # # # #

    @Override
    public boolean equals(final Object _comparable) {
	// - - - - - INITIALIZE THE VARIABLES - - - - -
	QoCCriterion qoCCriterion;
	// - - - - - CORE OF THE METHOD - - - - -
	if (_comparable != null && _comparable instanceof QoCCriterion) {
	    qoCCriterion = (QoCCriterion) _comparable;
	    return id.equals(qoCCriterion.id);
	}
	// - - - - - RETURN STATEMENT - - - - -
	return false;
    }

    public LinkedList<QoCMetricDefinition> listQoCMetricDefinition() {
	// - - - - - INITIALIZE THE VARIABLES - - - - -
	final LinkedList<QoCMetricDefinition> ret_list_qoCMetricDefinition = new LinkedList<QoCMetricDefinition>();
	// - - - - - CORE OF THE METHOD - - - - -
	for (final QoCMetricDefinition loop_qoCMetricDefinition : list_qoCMetricDefinition) {
	    ret_list_qoCMetricDefinition.add(new QoCMetricDefinition(loop_qoCMetricDefinition));
	}
	// - - - - - RETURN STATEMENT - - - - -
	return ret_list_qoCMetricDefinition;
    }

    @Override
    public int hashCode() {
	// - - - - - RETURN STATEMENT - - - - -
	return id.hashCode();
    }

    @Override
    public String toString() {
	// - - - - - INITIALIZE THE VARIABLES - - - - -
	final StringBuffer ret_stringBuffer = new StringBuffer();
	final Iterator<QoCMetricDefinition> iterator_qoCMetricDefinition = list_qoCMetricDefinition.iterator();
	QoCMetricDefinition next;
	// - - - - - CORE OF THE METHOD - - - - -
	ret_stringBuffer.append(super.toString());
	ret_stringBuffer.append("[ id= " + id + ", ");
	ret_stringBuffer.append("definitions= [");
	while (iterator_qoCMetricDefinition.hasNext()) {
	    next = iterator_qoCMetricDefinition.next();
	    if (iterator_qoCMetricDefinition.hasNext()) {
		ret_stringBuffer.append(" " + next.name + "[ id= " + next.id + "], ");
	    } else {
		ret_stringBuffer.append(" " + next.name + "[ id= " + next.id + " ] ]");
	    }
	}
	ret_stringBuffer.append("]");
	// - - - - - RETURN STATEMENT - - - - -
	return ret_stringBuffer.toString();
    }

    public String id() {
	return id;
    }

    public QoCCriterion id(final String _id) {
	id = _id;
	return this;
    }
}
