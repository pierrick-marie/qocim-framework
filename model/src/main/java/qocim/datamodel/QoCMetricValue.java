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

import java.util.Date;

/**
 * QoCMetricValue represents the value of the QoC. It also contains logs about
 * the date of creation and modification of the value.
 *
 * @see qocim.datamodel.QoCMetricDefinition
 * @see qocim.datamodel.QoCIndicator
 *
 * @author Pierrick MARIE
 */
public class QoCMetricValue extends QoCIM {

    // # # # # # CONSTANTS # # # # #

    /**
     * The default value of the QoC used in the constructor.
     */
    private static final int DEFAULT_VALUE = 0;
    /**
     * The default id used in the constructor.
     */
    private static final String DEFAULT_ID = "0";

    // # # # # # PROTECTED VARIABLES # # # # #

    /**
     * An id used to uniquely identify each QoCMetricValue linked to its
     * container (a QoCIndicator) and the qoCMetricDefintion
     * (QoCMetricDefinition).
     */
    String id;
    /**
     * The current date, modified with the current date in the constructor and
     * never set again.
     */
    Date creationDate;
    /**
     * The modification date, modified with the current date in the constructor
     * and each time when the attribute value is set with the current date.
     */
    Date modificationDate;
    /**
     * The definition used to configure the production of the value of the QoC.
     */
    QoCMetricDefinition qoCMetricDefinition;
    /**
     * The value of the QoC.
     */
    Double value;

    // # # # # # CONSTRUCTORS # # # # #

    public QoCMetricValue() {
        // - - - - - INITIALIZE THE VARIABLES - - - - -
        super();
        id = DEFAULT_ID;
        creationDate = new Date();
        modificationDate = creationDate;
        qoCMetricDefinition = new QoCMetricDefinition();
        value = Double.valueOf(DEFAULT_VALUE);
    }

    public QoCMetricValue(final QoCMetricValue _qoCMetricValue) {
        // - - - - - INITIALIZE THE VARIABLES - - - - -
        super(_qoCMetricValue);
        id = _qoCMetricValue.id;
        creationDate = new Date(_qoCMetricValue.creationDate.getTime());
        modificationDate = new Date(_qoCMetricValue.modificationDate.getTime());
        value = _qoCMetricValue.value;
        // Do not create a copy of <i>_qoCMetricValue.qoCMetricDefinition</i> to
        // avoid circular loop!
        qoCMetricDefinition = _qoCMetricValue.qoCMetricDefinition;
    }

    // # # # # # PUBLIC METHODS # # # # #

    @Override
    public boolean equals(final Object _comparable) {
        // - - - - - INITIALIZE THE VARIABLES - - - - -
        QoCMetricValue qoCMetricValue;
        // - - - - - CORE OF THE METHOD - - - - -
        if (_comparable != null && _comparable instanceof QoCMetricValue) {
            qoCMetricValue = (QoCMetricValue) _comparable;
            return id.equals(qoCMetricValue.id) && creationDate.equals(qoCMetricValue.creationDate) && modificationDate.equals(qoCMetricValue.modificationDate)
                    && value.equals(qoCMetricValue.value);
        }
        // - - - - - RETURN STATEMENT - - - - -
        return false;
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
        // - - - - - CORE OF THE METHOD - - - - -
        ret_stringBuffer.append(super.toString());
        ret_stringBuffer.append("[id= " + id + ", ");
        ret_stringBuffer.append("value= " + value + ", ");
        ret_stringBuffer.append("creationDate= " + creationDate + ", ");
        ret_stringBuffer.append("modificationDate= " + modificationDate + ", ");
        if (qoCMetricDefinition != null) {
            ret_stringBuffer.append("definition= " + qoCMetricDefinition.name + " (" + qoCMetricDefinition.id + ")");
        }
        ret_stringBuffer.append("]");
        // - - - - - RETURN STATEMENT - - - - -
        return ret_stringBuffer.toString();
    }

    public String id() {
        return id;
    }

    public QoCMetricValue id(final String _id) {
        id = _id;
        return this;
    }

    public Date creationDate() {
        return new Date(creationDate.getTime());
    }

    public QoCMetricValue creationDate(final Date _creationDate) {
        creationDate = _creationDate;
        return this;
    }

    public Date modificationDate() {
        return new Date(modificationDate.getTime());
    }

    public QoCMetricValue modificationDate(final Date _modificationDate) {
        modificationDate = _modificationDate;
        return this;
    }

    public QoCMetricDefinition qoCMetricDefinition() {
        return new QoCMetricDefinition(qoCMetricDefinition);
    }

    public QoCMetricValue qoCMetricDefinition(final QoCMetricDefinition _qoCMetricDefinition) {
        qoCMetricDefinition = _qoCMetricDefinition;
        return this;
    }

    public Double value() {
        return value;
    }

    public QoCMetricValue value(final Double _value) {
        value = _value;
        return this;
    }
}
