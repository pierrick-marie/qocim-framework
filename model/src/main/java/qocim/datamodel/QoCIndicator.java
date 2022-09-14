/**
 * This file is part of the QoCIM middleware.
 * <p>
 * Copyright (C) 2014 IRIT, Télécom SudParis
 * <p>
 * The QoCIM software is free software: you can redistribute it and/or modify
 * It under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * The QoCIM software platform is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * <p>
 * See the GNU Lesser General Public License
 * for more details: http://www.gnu.org/licenses
 * <p>
 * Initial developer(s): Pierrick MARIE
 * Contributor(s):
 */
package qocim.datamodel;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * QoCIndicator is the link between the value of the QoC and the definitions of
 * the QoC criterion.
 *
 * @see qocim.datamodel.QoCMetricValue
 * @see qocim.datamodel.QoCCriterion
 *
 * @author Pierrick MARIE
 */
public class QoCIndicator extends QoCIM {

    // # # # # # CONSTANTS # # # # #

    /**
     * The default value for the attribute id.
     */
    private static final int DEFAULT_ID = 0;

    // # # # # # PRIVATE VARIABLES # # # # #

    /**
     * The unique identify a QoCIndicator.
     */
    Integer id;
    /**
     * The list of different QoC values relative to this QoC indicator.
     */
    public final LinkedList<QoCMetricValue> list_qoCMetricValue;
    /**
     * All the definitions used to produce the QoC values referenced by the
     * attribute qoCMetricValueList.
     */
    QoCCriterion qoCCriterion;

    // # # # # # CONSTRUCTORS # # # # #

    public QoCIndicator() {
        // - - - - - INITIALIZE THE VARIABLES - - - - -
        super();
        id = DEFAULT_ID;
        list_qoCMetricValue = new LinkedList<QoCMetricValue>();
        qoCCriterion = new QoCCriterion();
    }

    public QoCIndicator(final QoCIndicator _qoCIndicator) {
        // - - - - - INITIALIZE THE VARIABLES - - - - -
        super(_qoCIndicator);
        id = _qoCIndicator.id;
        list_qoCMetricValue = _qoCIndicator.listQoCMetricValue();
        qoCCriterion = new QoCCriterion(_qoCIndicator.qoCCriterion);
    }

    // # # # # # PUBLIC METHODS # # # # #

    @Override
    public boolean equals(final Object _comparable) {
        // - - - - - INITIALIZE THE VARIABLES - - - - -
        QoCIndicator qoCIndicator;
        // - - - - - CORE OF THE METHOD - - - - -
        if (_comparable != null && _comparable instanceof QoCIndicator) {
            qoCIndicator = (QoCIndicator) _comparable;
            return id.equals(qoCIndicator.id);
        }
        // - - - - - RETURN STATEMENT - - - - -
        return false;
    }

    public LinkedList<QoCMetricValue> listQoCMetricValue() {
        // - - - - - INITIALIZE THE VARIABLES - - - - -
        final LinkedList<QoCMetricValue> ret_list_qoCMetricValue = new LinkedList<QoCMetricValue>();
        // - - - - - CORE OF THE METHOD - - - - -
        for (final QoCMetricValue loop_qoCMetricValue : list_qoCMetricValue) {
            ret_list_qoCMetricValue.add(new QoCMetricValue(loop_qoCMetricValue));
        }
        // - - - - - RETURN STATEMENT - - - - -
        return ret_list_qoCMetricValue;
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
        final Iterator<QoCMetricValue> iterator_qoCMetricValue = list_qoCMetricValue.iterator();
        QoCMetricValue next;
        // - - - - - CORE OF THE METHOD - - - - -
        ret_stringBuffer.append(super.toString());
        ret_stringBuffer.append("[id= " + id + ", ");
        ret_stringBuffer.append("values= [");
        while (iterator_qoCMetricValue.hasNext()) {
            next = iterator_qoCMetricValue.next();
            if (iterator_qoCMetricValue.hasNext()) {
                ret_stringBuffer.append(" " + next.toString() + ", ");
            } else {
                ret_stringBuffer.append(" " + next.toString() + " ], ");
            }
        }
        ret_stringBuffer.append("criterion= [" + qoCCriterion + "]");
        ret_stringBuffer.append("]");
        // - - - - - RETURN STATEMENT - - - - -
        return ret_stringBuffer.toString();
    }

    public QoCIndicator id(final Integer _id) {
        id = _id;
        return this;
    }

    public Integer id() {
        return id;
    }

    public QoCIndicator qoCCriterion(final QoCCriterion _qoCCriterion) {
        qoCCriterion = _qoCCriterion;
        return this;
    }

    public QoCCriterion qoCCriterion() {
        return new QoCCriterion(qoCCriterion);
    }
}
