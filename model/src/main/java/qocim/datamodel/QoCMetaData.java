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

import java.util.logging.Level;

import qocim.datamodel.utils.ConstraintChecker;
import qocim.datamodel.utils.ConstraintCheckerException;
import qocim.datamodel.utils.log.QoCIMLogger;

/**
 * This facade used to handle QoC meta-data. The class manage one QoCIndicator,
 * one QoCCriterion, one QoCMetricDefinition and one QoCMetricValue.
 *
 * @author Pierrick MARIE
 */
public class QoCMetaData {

    // # # # # # PRIVATE VARIABLES # # # # #

    /**
     * The QoC indicator handled by the QoC meta-data.
     */
    private final QoCIndicator qoCIndicator;
    /**
     * The QoC metric value handled by the QoC meta-data.
     */
    private final QoCMetricValue qoCMetricValue;
    /**
     * The QoC criterion handled by the QoC meta-data.
     */
    private final QoCCriterion qoCCriterion;
    /**
     * The QoC metric definition handled by the QoC meta-data.
     */
    private final QoCMetricDefinition qoCMetricDefinition;
    /**
     * The description handled by the QoC meta-data.
     */
    private final Description description;

    // # # # # # CONSTRUCTORS # # # # #

    public QoCMetaData(final QoCIndicator _qoCIndicator) {
	// - - - - - INITIALIZE THE VARIABLES - - - - -
	this(_qoCIndicator, _qoCIndicator.list_qoCMetricValue.iterator().next());
    }

    public QoCMetaData(final QoCIndicator _qoCIndicator, final QoCMetricValue _qoCMetricValue) {
	// - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
	try {
	    String message = "QoCMetaData.consturctor(QoCIndicator, QoCMetricValue): the argument _qoCIndicator is null";
	    ConstraintChecker.notNull(_qoCIndicator, message);
	    message = "QoCMetaData.consturctor(QoCIndicator, QoCMetricValue): the argument _qoCMetricValue is null";
	    ConstraintChecker.notNull(_qoCMetricValue, message);
	    message = "QoCMetaData.consturctor(QoCIndicator, QoCMetricValue): the argument _qoCMetricValue is not referenced in the list _qoCIndicator.list_qoCMetricValue";
	    ConstraintChecker.assertTrue(_qoCIndicator.list_qoCMetricValue.contains(_qoCMetricValue), message);
	    message = "QoCMetaData.consturctor(QoCIndicator, QoCMetricValue): the argument _qoCIndicator.QoCCriterion is null";
	    ConstraintChecker.notNull(_qoCIndicator.qoCCriterion, message);
	    message = "QoCMetaData.consturctor(QoCIndicator, QoCMetricValue): the argument _qoCMetricValue.qoCMetricDefinition is not referenced in the list _qoCIndicator.qoCCriterion.list_qoCMetricDefinition";
	    ConstraintChecker.assertTrue(_qoCIndicator.qoCCriterion.list_qoCMetricDefinition.contains(_qoCMetricValue.qoCMetricDefinition), message);
	} catch (final ConstraintCheckerException _exception) {
	    final String message = "QoCMetaData.consturctor(QoCIndicator, QoCMetricValue): Fatal error.";
	    QoCIMLogger.logger.log(Level.SEVERE, message, _exception);
	    System.exit(-1);
	}
	// - - - - - INITIALIZE THE VARIABLES - - - - -
	qoCIndicator = _qoCIndicator;
	qoCMetricValue = _qoCMetricValue;
	qoCCriterion = _qoCIndicator.qoCCriterion;
	qoCMetricDefinition = qoCMetricValue.qoCMetricDefinition;
	description = qoCMetricValue.qoCMetricDefinition.description;
    }

    // # # # # # PUBLIC METHODS # # # # #

    @Override
    public boolean equals(final Object _comparable) {
	// - - - - - INITIALIZE THE VARIABLES - - - - -
	QoCMetaData qoCMetaData;
	// - - - - - CORE OF THE METHOD - - - - -
	if (_comparable != null && _comparable instanceof QoCMetaData) {
	    qoCMetaData = (QoCMetaData) _comparable;
	    return qoCIndicatorId().equals(qoCMetaData.qoCIndicatorId()) && qoCCriterionId().equals(qoCMetaData.qoCCriterionId())
		    && qoCMetricDefinitionId().equals(qoCMetaData.qoCMetricDefinitionId()) && qoCMetricValueValue().equals(qoCMetaData.qoCMetricValueValue())
		    && qoCMetricValueCreationDate().equals(qoCMetaData.qoCMetricValueCreationDate())
		    && qoCMetricValueModificationDate().equals(qoCMetaData.qoCMetricValueModificationDate());
	}
	// - - - - - RETURN STATEMENT - - - - -
	return false;
    }

    public Description description() {
	// - - - - - RETURN STATEMENT - - - - -
	return new Description(description);
    }

    public QoCCriterion qoCCriterion() {
	// - - - - - RETURN STATEMENT - - - - -
	return new QoCCriterion(qoCCriterion);
    }

    public String qoCCriterionId() {
	// - - - - - RETURN STATEMENT - - - - -
	return qoCCriterion.id;
    }

    public QoCIndicator qoCIndicator() {
	// - - - - - RETURN STATEMENT - - - - -
	return new QoCIndicator(qoCIndicator);
    }

    public Integer qoCIndicatorId() {
	// - - - - - RETURN STATEMENT - - - - -
	return qoCIndicator.id;
    }

    public QoCMetricDefinition qoCMetricDefinition() {
	// - - - - - RETURN STATEMENT - - - - -
	return new QoCMetricDefinition(qoCMetricDefinition);
    }

    public String qoCMetricDefinitionId() {
	// - - - - - RETURN STATEMENT - - - - -
	return qoCMetricDefinition.id;
    }

    public QoCMetricValue qoCMetricValue() {
	// - - - - - RETURN STATEMENT - - - - -
	return new QoCMetricValue(qoCMetricValue);
    }

    public Long qoCMetricValueCreationDate() {
	// - - - - - RETURN STATEMENT - - - - -
	return qoCMetricValue.creationDate.getTime();
    }

    public String qoCMetricValueId() {
	// - - - - - RETURN STATEMENT - - - - -
	return qoCMetricValue.id;
    }

    public Long qoCMetricValueModificationDate() {
	// - - - - - RETURN STATEMENT - - - - -
	return qoCMetricValue.modificationDate.getTime();
    }

    public Double qoCMetricValueValue() {
	// - - - - - RETURN STATEMENT - - - - -
	return qoCMetricValue.value;
    }

    @Override
    public int hashCode() {
	// - - - - - RETURN STATEMENT - - - - -
	return qoCIndicatorId().hashCode() + qoCMetricValueId().hashCode();
    }

    @Override
    public String toString() {
	// - - - - - RETURN STATEMENT - - - - -
	return qoCIndicator.toString();
    }
}
