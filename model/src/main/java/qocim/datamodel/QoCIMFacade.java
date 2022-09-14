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

import qocim.datamodel.utils.ConstraintChecker;
import qocim.datamodel.utils.ConstraintCheckerException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This028.3. facade contains many useful low-level method to manipulate the QoC
 * Information model. Those methods are used by the tools and QoC management
 * functions. This facade constitute the low-level API to manipulate QoCIM, that
 * is why it is strongly discouraged to use it. It is preferable to use the tool
 * or QoC management functions instead.
 *
 * @author Pierrick MARIE
 */
public class QoCIMFacade {

    /**
     * The method adds a QoC definition into the list of composite definition of
     * another definition. If the composite QoC metric definition is already
     * referenced by the definition, the method makes nothing.
     *
     * @param _compositeQoCMetricDefinition
     *            The composite QoC metric definition to add.
     * @param _qoCMetricDefinition
     *            The QoC metric definition modified by the method.
     * @return True if the definition changed.
     */
    public static Boolean addCompositeQoCMetricDefinition(final QoCMetricDefinition _compositeQoCMetricDefinition,
                                                          final QoCMetricDefinition _qoCMetricDefinition) {
        // - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
        try {
            String message = "QoCIMFacade.addCompositeQoCMetricDefinition(QoCMetricDefinition, QoCMetricDefinition): the argument _compositeQoCMetricDefinition is null";
            ConstraintChecker.notNull(_compositeQoCMetricDefinition, message);
            message = "QoCIMFacade.addCompositeQoCMetricDefinition(QoCMetricDefinition, QoCMetricDefinition): the argument _qoCMetricDefinition is null";
            ConstraintChecker.notNull(_qoCMetricDefinition, message);
        } catch (final ConstraintCheckerException e) {
            return false;
        }
        // - - - - - CORE OF THE METHOD - - - - -
        if (!_qoCMetricDefinition.list_compositeQoCMetricDefinition.contains(_compositeQoCMetricDefinition)) {
            return _qoCMetricDefinition.list_compositeQoCMetricDefinition.add(_compositeQoCMetricDefinition);
        }
        // - - - - - RETURN STATEMENT - - - - -
        return false;
    }

    /**
     * The method sets the verbatim description of a QoC metric definition. It
     * sets the value of the field <i>description</i> of a QoC metric
     * definition.
     *
     * @param _description
     *            The verbatim description.
     * @param _qoCMetricDefinition
     *            The QoC metric definition modified by the method.
     * @return True if the definition changed.
     */
    public static Boolean addDescription(final Description _description, final QoCMetricDefinition _qoCMetricDefinition) {
        // - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
        try {
            String message = "QoCIMFacade.addDescription(Descripiton, QoCMetricDefinition): the argument _description is null";
            ConstraintChecker.notNull(_description, message);
            message = "QoCIMFacade.addQoCMetricDefinition(Descripiton, QoCMetricDefinition): the argument _qoCMetricDefinition is null";
            ConstraintChecker.notNull(_qoCMetricDefinition, message);
        } catch (final ConstraintCheckerException e) {
            return false;
        }
        // - - - - - CORE OF THE METHOD - - - - -
        _qoCMetricDefinition.description = _description;
        _description.container = _qoCMetricDefinition;
        // - - - - - RETURN STATEMENT - - - - -
        return true;
    }

    /**
     * The method adds a QoC definition into the list of primitive definition of
     * another definition. If the primitive QoC metric definition is already
     * referenced by the definition, the method makes nothing.
     *
     * @param _primitiveQoCMetricDefinition
     *            The primitive QoC metric definition to add.
     * @param _qoCMetricDefinition
     *            The QoC metric definition modified by the method.
     * @return True if the definition changed.
     */
    public static Boolean addPrimitiveQoCMetricDefinition(final QoCMetricDefinition _primitiveQoCMetricDefinition,
                                                          final QoCMetricDefinition _qoCMetricDefinition) {
        // - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
        try {
            String message = "QoCIMFacade.addPrimitiveQoCMetricDefinition(QoCMetricDefinition, QoCMetricDefinition): the argument _primitiveQoCMetricDefinition is null";
            ConstraintChecker.notNull(_primitiveQoCMetricDefinition, message);
            message = "QoCIMFacade.addPrimitiveQoCMetricDefinition(QoCMetricDefinition, QoCMetricDefinition): the argument _qoCMetricDefinition is null";
            ConstraintChecker.notNull(_qoCMetricDefinition, message);
        } catch (final ConstraintCheckerException e) {
            return false;
        }
        // - - - - - CORE OF THE METHOD - - - - -
        if (!_qoCMetricDefinition.list_primitiveQoCMetricDefinition.contains(_primitiveQoCMetricDefinition)) {
            return _qoCMetricDefinition.list_primitiveQoCMetricDefinition.add(_primitiveQoCMetricDefinition);
        }
        // - - - - - RETURN STATEMENT - - - - -
        return false;
    }

    /**
     * The method adds a QoC criterion to a QoC indicator the QoC criterion
     * associated to a QoC indicator.
     *
     * @param _qoCCriterion
     *            The QoC criterion referenced by the QoC indicator.
     * @param _qoCIndicator
     *            The QoC indicator modified by the method.
     * @return True if the indicator changed.
     */
    public static Boolean addQoCCriterion(final QoCCriterion _qoCCriterion, final QoCIndicator _qoCIndicator) {
        // - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
        try {
            String message = "QoCIMFacade.addQoCCriterion(QoCCriterion, QoCIndicator): the argument _qoCCriterion is null";
            ConstraintChecker.notNull(_qoCCriterion, message);
            message = "QoCIMFacade.addQoCCriterion(QoCCriterion, QoCIndicator): the argument _qoCIndicator is null";
            ConstraintChecker.notNull(_qoCIndicator, message);
        } catch (final ConstraintCheckerException e) {
            return false;
        }
        // - - - - - CORE OF THE METHOD - - - - -
        _qoCIndicator.qoCCriterion = _qoCCriterion;
        _qoCCriterion.container = _qoCIndicator;
        // - - - - - RETURN STATEMENT - - - - -
        return true;
    }

    /**
     * The method adds a QoC metric definition into the list of definition of a
     * QoC criterion. The method does not reference the definition by the
     * criterion if it is already done before.
     *
     * @param _qoCMetricDefinition
     *            The QoC metric definition referenced by the QoC criterion.
     * @param _qoCCriterion
     *            The QoC criterion modified by the method.
     * @return True if the criterion changed.
     */
    public static Boolean addQoCMetricDefinition(final QoCMetricDefinition _qoCMetricDefinition, final QoCCriterion _qoCCriterion) {
        // - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
        try {
            String message = "QoCIMFacade.addQoCMetricDefinition(QoCMetricDefinition, QoCCriterion): the argument _qoCMetricDefinition is null";
            ConstraintChecker.notNull(_qoCMetricDefinition, message);
            message = "QoCIMFacade.addQoCMetricDefinition(QoCMetricDefinition, QoCCriterion): the argument _qoCCriterion is null";
            ConstraintChecker.notNull(_qoCCriterion, message);
        } catch (final ConstraintCheckerException e) {
            return false;
        }
        // - - - - - CORE OF THE METHOD - - - - -
        if (!_qoCCriterion.list_qoCMetricDefinition.contains(_qoCMetricDefinition)) {
            if (!_qoCCriterion.list_qoCMetricDefinition.add(_qoCMetricDefinition)) {
                return false;
            }
        }
        _qoCMetricDefinition.container = _qoCCriterion;
        // - - - - - RETURN STATEMENT - - - - -
        return true;
    }

    /**
     * The method adds a QoC metric value into the list of values of a QoC
     * indicator. The method uses the methods
     * <i>addQoCMetricDefinition(QoCMetricDefinition, QoCCriterion)</i> and
     * <i>addQoCMetricValue(QoCMetricValue, QoCMetricDefinitionà</i> to verify
     * whether all the references between the class of the model are correctly
     * updated.
     *
     * @param _qoCMetricValue
     *            The value referenced by the indicator.
     * @param _qoCIndicator
     *            The indicator modified by the method.
     * @return True if the indicator changed.
     */
    public static Boolean addQoCMetricValue(final QoCMetricValue _qoCMetricValue, final QoCIndicator _qoCIndicator) {
        // - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
        try {
            String message = "QoCIMFacade.addQoCMetricValue(QoCMetricValue, QoCIndicator): the argument _qoCIndicator is null";
            ConstraintChecker.notNull(_qoCIndicator, message);
            message = "QoCIMFacade.addQoCMetricValue(QoCMetricValue, QoCIndicator): the argument _qoCIndicator.qoCCriterion is null";
            ConstraintChecker.notNull(_qoCIndicator.qoCCriterion, message);
            message = "QoCIMFacade.addQoCMetricValue(QoCMetricValue, QoCIndicator): the argument _qocMetricValue is null";
            ConstraintChecker.notNull(_qoCMetricValue, message);
            message = "QoCIMFacade.addQoCMetricValue(QoCMetricValue, QoCIndicator): the argument _qoCMetricValue.qoCMetricDefinition is null";
            ConstraintChecker.notNull(_qoCMetricValue.qoCMetricDefinition, message);
        } catch (final ConstraintCheckerException e) {
            return false;
        }
        // - - - - - INITIALIZE THE VARIABLES - - - - -
        /*
         * The index of the QoC metric definition of the <i>_qoCMetricValue</i>
         * contained into
         * <i>_qoCIndicator.qoCCriterion.list_qoCMetricDefinition</i>.
         */
        Integer index_qoCMetricDefinition;
        // - - - - - CORE OF THE METHOD - - - - -
        if (!_qoCIndicator.list_qoCMetricValue.contains(_qoCMetricValue)) {
            _qoCIndicator.list_qoCMetricValue.add(_qoCMetricValue);
        }
        _qoCMetricValue.container = _qoCIndicator;
        addQoCMetricDefinition(_qoCMetricValue.qoCMetricDefinition, _qoCIndicator.qoCCriterion);
        index_qoCMetricDefinition = _qoCIndicator.qoCCriterion.list_qoCMetricDefinition.indexOf(_qoCMetricValue.qoCMetricDefinition);
        // - - - - - RETURN STATEMENT - - - - -
        return addQoCMetricValue(_qoCMetricValue, _qoCIndicator.qoCCriterion.list_qoCMetricDefinition.get(index_qoCMetricDefinition));
    }

    /**
     * The method reference a QoC metric value into the list of values of a QoC
     * metric definition. The method only adds the QoC metric value into the
     * list if it is not already done before.
     *
     * @param _qoCMetricValue
     *            The value referenced by the definition.
     * @param _qoCMetricDefinition
     *            The definition modified by the method.
     * @return True if the definition changed.
     */
    public static Boolean addQoCMetricValue(final QoCMetricValue _qoCMetricValue, final QoCMetricDefinition _qoCMetricDefinition) {
        // - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
        try {
            String message = "QoCIMFacade.addQoCMetricValue(QoCMetricValue, QoCMetricDefinition): the argument _qoCMetricValue is null";
            ConstraintChecker.notNull(_qoCMetricValue, message);
            message = "QoCIMFacade.addQoCMetricValue(QoCMetricValue, QoCMetricDefinition): the argument _qoCMetricDefinition is null";
            ConstraintChecker.notNull(_qoCMetricDefinition, message);
        } catch (final ConstraintCheckerException e) {
            return false;
        }
        // - - - - - CORE OF THE METHOD - - - - -
        if (!_qoCMetricDefinition.list_qoCMetricValue.contains(_qoCMetricValue)) {
            if (!_qoCMetricDefinition.list_qoCMetricValue.add(_qoCMetricValue)) {
                return false;
            }
        }
        _qoCMetricValue.qoCMetricDefinition = _qoCMetricDefinition;
        // - - - - - RETURN STATEMENT - - - - -
        return true;
    }

    /**
     * The method creates a list of QoC indicator from a list of QoC meta-data.
     * The method <i>createListQoCMetaData</i> applies the opposite operation.
     * The method analyzes all the QoC meta-data and gathers the QoC metric
     * values referenced by a same QoC indicator into a same indicator. The
     * method uses the method <i>searchFirstQoCIndicator(List QoCIndicator,
     * QoCIndicator.id)</i> to search whether a QoC indicator is not already
     * inserted into the returned list of indicator and
     * <i>addQoCMetricValue(QoCMetricValue, QoCIndicator)</i> to gather the QoC
     * metric values referenced by a same QoC indicator.
     *
     * @param _list_qoCMetaData
     *            The list of QoC mete-data analyzed by the method.
     * @return The list of QoC indicator built from the list of QoC mete-data.
     */
    public static List<QoCIndicator> createListQoCIndicator(final List<QoCMetaData> _list_qoCMetaData) {
        // - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
        try {
            final String message = "QoCIMFacade.createListQoCIndicator(List<QoCMetaData>): the argument _list_qoCMetaData.";
            ConstraintChecker.notNull(_list_qoCMetaData, message);
        } catch (final ConstraintCheckerException e) {
            return new ArrayList<QoCIndicator>();
        }
        // - - - - - INITIALIZE THE VARIABLES - - - - -
        /*
         * The resulting list of QOC indicator returned by the method.
         */
        final List<QoCIndicator> ret_list_qoCIndicator = new ArrayList<QoCIndicator>();
        /*
         * The variable that contains the QoC indicators inserted into the
         * <i>ret_list_qoCIndicator</i>.
         */
        QoCIndicator qoCIndicator;
        // - - - - - CORE OF THE METHOD - - - - -
        for (final QoCMetaData loop_qoCMetaData : _list_qoCMetaData) {
            qoCIndicator = searchFirstQoCIndicator(ret_list_qoCIndicator, loop_qoCMetaData.qoCIndicatorId());
            if (qoCIndicator == null) {
                ret_list_qoCIndicator.add(loop_qoCMetaData.qoCIndicator());
            } else {
                addQoCMetricValue(loop_qoCMetaData.qoCMetricValue(), qoCIndicator);
            }
        }
        // - - - - - RETURN STATEMENT - - - - -
        return ret_list_qoCIndicator;
    }

    /**
     * The method creates a list of QoC meta-data from a QoC indicator. The
     * method add into the returned list a new QoC meta-data for each QoC metric
     * value referenced by the QoC indicator.
     *
     * @param _qoCIndicator
     *            The QoC indicator analyzed by the method.
     * @return The list of QoC meta-data created by the method.
     */
    public static List<QoCMetaData> createListQoCMetaData(final QoCIndicator _qoCIndicator) {
        // - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
        try {
            final String message = "QoCIMFacade.createListQoCMetaData(QoCIndicator): the argument _qoCIndicator is null";
            ConstraintChecker.notNull(_qoCIndicator, message);
        } catch (final ConstraintCheckerException e) {
            return new ArrayList<QoCMetaData>();
        }
        // - - - - - INITIALIZE THE VARIABLES - - - - -
        /*
         * The list of result QoC meta-data produced by the method.
         */
        final List<QoCMetaData> ret_list_qoCMetaData = new ArrayList<QoCMetaData>();
        // - - - - - CORE OF THE METHOD - - - - -
        for (final QoCMetricValue loop_qoCMetricValue : _qoCIndicator.list_qoCMetricValue) {
            ret_list_qoCMetaData.add(new QoCMetaData(_qoCIndicator, loop_qoCMetricValue));
        }
        // - - - - - RETURN STATEMENT - - - - -
        return ret_list_qoCMetaData;
    }

    /**
     * The method removed a QoC metric value from the list of QoC metric value
     * of a QoC indicator.
     *
     * @param _qoCMetricValue
     *            The QoC metric value to remove.
     * @param _qoCIndicator
     *            The QoC indicator modified by the method.
     * @return True if the indicator changed.
     */
    public static Boolean removeQoCMetricValue(final QoCMetricValue _qoCMetricValue, final QoCIndicator _qoCIndicator) {
        // - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
        try {
            String message = "QoCIMFacade.removeQoCMetricValue(QoCMetricValue, QoCIndicator): the argument _qoCIndicator is null";
            ConstraintChecker.notNull(_qoCIndicator, message);
            message = "QoCIMFacade.removeQoCMetricValue(QoCMetricValue, QoCIndicator): the argument _qocMetricValue is null";
            ConstraintChecker.notNull(_qoCMetricValue, message);
        } catch (final ConstraintCheckerException e) {
            return false;
        }
        // - - - - - CORE OF THE METHOD - - - - -
        if (_qoCIndicator.list_qoCMetricValue.remove(_qoCMetricValue)) {
            _qoCMetricValue.container = null;
            return true;
        }
        // - - - - - RETURN STATEMENT - - - - -
        return false;
    }

    /**
     * The method removed all QoC metric value from the list of QoC metric value
     * of a QoC indicator.
     *
     * @param _qoCIndicator
     *            The QoC indicator modified by the method.
     */
    public static void removeAllQoCMetricValue(final QoCIndicator _qoCIndicator) {
        // - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
        try {
            final String message = "QoCIMFacade.removeAllQoCMetricValue(QoCIndicator): the argument _qoCIndicator is null";
            ConstraintChecker.notNull(_qoCIndicator, message);
        } catch (final ConstraintCheckerException e) {
            return;
        }
        // - - - - - CORE OF THE METHOD - - - - -
        for (final QoCMetricValue loop_qoCMetricValue : _qoCIndicator.list_qoCMetricValue) {
            loop_qoCMetricValue.container = null;
        }
        _qoCIndicator.list_qoCMetricValue.clear();
    }

    /**
     * The method removes a QoC metric value from the list of QoC metric value
     * of a QoC metric definition.
     *
     * @param _qoCMetricValue
     *            The QoC metric value to remove.
     * @param _qoCMetricDefinition
     *            The QoC metric definition modified by the method.
     * @return True if the definition changed.
     */
    public static Boolean removeQoCMetricValue(final QoCMetricValue _qoCMetricValue, final QoCMetricDefinition _qoCMetricDefinition) {
        // - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
        try {
            String message = "QoCIMFacade.removeQoCMetricValue(QoCMetricValue, QoCMetricDefinition): the argument _qoCMetricDefinition is null";
            ConstraintChecker.notNull(_qoCMetricDefinition, message);
            message = "QoCIMFacade.removeQoCMetricValue(QoCMetricValue, QoCMetricDefinition): the argument _qocMetricValue is null";
            ConstraintChecker.notNull(_qoCMetricValue, message);
        } catch (final ConstraintCheckerException e) {
            return false;
        }
        // - - - - - RETURN STATEMENT - - - - -
        return _qoCMetricDefinition.list_qoCMetricValue.remove(_qoCMetricValue);
    }

    /**
     * The method removes all QoC metric value of a QoC metric definition.
     *
     * @param _qoCMetricDefinition
     *            The QoC metric definition modified by the method.
     */
    public static void removeAllQoCMetricValue(final QoCMetricDefinition _qoCMetricDefinition) {
        // - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
        try {
            final String message = "QoCIMFacade.removeAllQoCMetricValue(QoCMetricValue, QoCMetricDefinition): the argument _qoCMetricDefinition is null";
            ConstraintChecker.notNull(_qoCMetricDefinition, message);
        } catch (final ConstraintCheckerException e) {
            return;
        }
        for (final QoCMetricValue loop_qoCMetricValue : _qoCMetricDefinition.list_qoCMetricValue) {
            loop_qoCMetricValue.qoCMetricDefinition = null;
        }
        _qoCMetricDefinition.list_qoCMetricValue.clear();
    }

    /**
     * The method replaces a QoC metric value by another one into a QoC
     * indicator. The method completely removes the old QoC metric value from
     * the references of the QoC indicator and the associated QoC metric
     * definitions. Then, the method adds the new QoC metric value into the
     * references of the QoC indicator and the associated QoC metric definition.
     *
     * @param _oldQoCMetricValue
     *            The old QoC metric value to remove.
     * @param _newQoCMetricValue
     *            The new QoC metric value to add.
     * @param _qoCIndicator
     *            The QoQ indicator modified by the method.
     * @return True if the indicator changed.
     */
    public static Boolean replaceQoCMetricValue(final QoCMetricValue _oldQoCMetricValue, final QoCMetricValue _newQoCMetricValue,
                                                final QoCIndicator _qoCIndicator) {
        // - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
        try {
            String message = "QoCIMFacade.replaceQoCMetricValue(QoCMetricValue, QoCMetricValue, QoCIndicator): the argument _oldQoCMetricValue.";
            ConstraintChecker.notNull(_oldQoCMetricValue, message);
            message = "QoCIMFacade.replaceQoCMetricValue(QoCMetricValue, QoCMetricValue, QoCIndicator): the argument _newQoCMetricValue.";
            ConstraintChecker.notNull(_newQoCMetricValue, message);
            message = "QoCIMFacade.replaceQoCMetricValue(QoCMetricValue, QoCMetricValue, QoCIndicator): the argument _qoCIndicator.";
            ConstraintChecker.notNull(_qoCIndicator, message);
        } catch (final ConstraintCheckerException e) {
            return false;
        }
        // - - - - - CORE OF THE METHOD - - - - -
        if (removeQoCMetricValue(_oldQoCMetricValue, _qoCIndicator)) {
            if (removeQoCMetricValue(_oldQoCMetricValue, _oldQoCMetricValue.qoCMetricDefinition)) {
                if (addQoCMetricDefinition(_newQoCMetricValue.qoCMetricDefinition, _qoCIndicator.qoCCriterion)) {
                    return addQoCMetricValue(_newQoCMetricValue, _qoCIndicator);
                }
            }
        }
        // - - - - - RETURN STATEMENT - - - - -
        return false;
    }

    /**
     * The method searches and returns the first QoC indicator identified with
     * its field <i>id</i> from a list of QoC indicator.
     *
     * @param _list_qoCIndicator
     *            The list of QoC indicator analyzed by the method.
     * @param _qoCIndicatorId
     *            The id of the QoC indicator searched by the method.
     * @return The QoC indicator found by the method.
     */
    public static QoCIndicator searchFirstQoCIndicator(final List<QoCIndicator> _list_qoCIndicator, final Integer _qoCIndicatorId) {
        // - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
        try {
            String message = "QoCIMFacade.searchFirstQoCIndicator(List<QoCIndicator>, Integer): the argument _list_qoCIndicator.";
            ConstraintChecker.notNull(_list_qoCIndicator, message);
            message = "QoCIMFacade.searchFirstQoCIndicator(List<QoCIndicator>, Integer): the argument _qoCIndicatorId.";
            ConstraintChecker.notNull(_qoCIndicatorId, message);
        } catch (final ConstraintCheckerException e) {
            return new QoCIndicator();
        }
        // - - - - - INITIALIZE THE VARIABLES - - - - -
        /*
         * The resulting QoC indicator produced by the method.
         */
        QoCIndicator ret_qoCIndicator = null;
        /*
         * An iterator to browse the <i>_list_qoCIndicator</i>.
         */
        final Iterator<QoCIndicator> iterator_qoCIndicator = _list_qoCIndicator.iterator();
        /*
         * A boolean to indicate if the QoC indicator has been found or not.
         */
        Boolean found_qoCIndicator = false;
        /*
         * A variable used to search the right QoC indicator.
         */
        QoCIndicator qoCIndicator;
        // - - - - - CORE OF THE METHOD - - - - -
        while (!found_qoCIndicator && iterator_qoCIndicator.hasNext()) {
            qoCIndicator = iterator_qoCIndicator.next();
            if (qoCIndicator.id.equals(_qoCIndicatorId)) {
                ret_qoCIndicator = qoCIndicator;
                found_qoCIndicator = true;
            }
        }
        // - - - - - RETURN STATEMENT - - - - -
        return ret_qoCIndicator;
    }

    /**
     * The method searches and returns the first QoC metric definition
     * identified by its field <i>id</i> from a list of definition.
     *
     * @param _list_qoCMetricDefinition
     *            The list of QoC metric definition analyzed by the method.
     * @param _qoCMetricDefinitionId
     *            The id of the QoC metric definition searched by the method.
     * @return The QoC metric definition found by the method.
     */
    public static QoCMetricDefinition searchFirstQoCMetricDefinition(final List<QoCMetricDefinition> _list_qoCMetricDefinition,
                                                                     final String _qoCMetricDefinitionId) {
        // - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
        try {
            String message = "QoCIMFacade.searchFirstQoCMetricDefinition(List<QoCMetricDefinition>, String): the argument _list_qoCMetricDefinition.";
            ConstraintChecker.notNull(_list_qoCMetricDefinition, message);
            message = "QoCIMFacade.searchFirstQoCMetricDefinition(List<QoCMetricDefinition>, String): the argument _qoCMetricDefinitionId.";
            ConstraintChecker.notNull(_qoCMetricDefinitionId, message);
        } catch (final ConstraintCheckerException e) {
            return new QoCMetricDefinition();
        }
        // - - - - - INITIALIZE THE VARIABLES - - - - -
        /*
         * The QoC metric definition returned by the method.
         */
        QoCMetricDefinition ret_qoCMetricDefinition = null;
        /*
         * A boolean that indicates if the QoC metric definition has been found.
         */
        Boolean found_qoCMetricDefinition = false;
        /*
         * An iterator used to browse the <i>_list_qoCMetricDefinition</i>.
         */
        final Iterator<QoCMetricDefinition> iterator_qoCMetricDefinition = _list_qoCMetricDefinition.iterator();
        /*
         * A variable used to search the right QoC metric definition.
         */
        QoCMetricDefinition qoCMetricDefinition;
        // - - - - - CORE OF THE METHOD - - - - -
        while (!found_qoCMetricDefinition && iterator_qoCMetricDefinition.hasNext()) {
            qoCMetricDefinition = iterator_qoCMetricDefinition.next();
            if (qoCMetricDefinition.id.equals(_qoCMetricDefinitionId)) {
                ret_qoCMetricDefinition = qoCMetricDefinition;
                found_qoCMetricDefinition = true;
            }
        }
        // - - - - - RETURN STATEMENT - - - - -
        return ret_qoCMetricDefinition;
    }

    /**
     * The method searches the first QoC metric definition into the list of QoC
     * metric definition of a QoC criterion.
     *
     * @param _qoCCriterion
     *            The QoC criterion analyzed by the method.
     * @param _qoCMetricDefinitionId
     *            The id of the QoC metric definition searched by the method.
     * @return The QoC metric definition found bu the method.
     */
    public static QoCMetricDefinition searchFirstQoCMetricDefinition(final QoCCriterion _qoCCriterion, final String _qoCMetricDefinitionId) {
        // - - - - - RETURN STATEMENT - - - - -
        return searchFirstQoCMetricDefinition(_qoCCriterion.list_qoCMetricDefinition, _qoCMetricDefinitionId);
    }

    /**
     * The method searches the first QoC metric value from a list of QoC metric
     * value identified by its id.
     *
     * @param _list_qoCMetricValue
     *            The list of QoC metric value analyzed by the method.
     * @param _qoCMetricValueId
     *            The id of the QoC metric value searched by the method.
     * @return The QoC metric value found by the method.
     */
    public static QoCMetricValue searchFirstQoCMetricValue(final List<QoCMetricValue> _list_qoCMetricValue, final String _qoCMetricValueId) {
        // - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
        try {
            String message = "QoCIMFacade.searchFirstQoCMetricValue(List<QoCMetricValue>, Integer): the argument _list_qoCMetricValue.";
            ConstraintChecker.notNull(_list_qoCMetricValue, message);
            message = "QoCIMFacade.searchFirstQoCMetricValue(List<QoCMetricValue>, Integer): the argument _qoCMetricValueId.";
            ConstraintChecker.notNull(_qoCMetricValueId, message);
        } catch (final ConstraintCheckerException e) {
            return new QoCMetricValue();
        }
        // - - - - - INITIALIZE THE VARIABLES - - - - -
        /*
         * The QoC metric value returned by the method.
         */
        QoCMetricValue ret_qoCMetricValue = null;
        /*
         * A boolean that indicates if the QoC metric value has been found.
         */
        Boolean found_qoCMetricValue = false;
        /*
         * An iterator to browse the <i>_list_qoCMetricValue</i>.
         */
        final Iterator<QoCMetricValue> iterator_qoCMetricValue = _list_qoCMetricValue.iterator();
        /*
         * A variable used to search the right QoC metric value.
         */
        QoCMetricValue qoCMetricValue;
        // - - - - - CORE OF THE METHOD - - - - -
        while (!found_qoCMetricValue && iterator_qoCMetricValue.hasNext()) {
            qoCMetricValue = iterator_qoCMetricValue.next();
            if (qoCMetricValue.id.equals(_qoCMetricValueId)) {
                ret_qoCMetricValue = qoCMetricValue;
                found_qoCMetricValue = true;
            }
        }
        // - - - - - RETURN STATEMENT - - - - -
        return ret_qoCMetricValue;
    }

    /**
     * The method searches a QoC metric value identified by its id from the list
     * of QoC metric value of a QoC indicator.
     *
     * @param _qoCIndicator
     *            The QoC indicator analyzed by the method.
     * @param _qoCMetricValueId
     *            The id of the QoC metric value searched by the method.
     * @return The QoC metric value found by the method.
     */
    public static QoCMetricValue searchFirstQoCMetricValue(final QoCIndicator _qoCIndicator, final String _qoCMetricValueId) {
        // - - - - - RETURN STATEMENT - - - - -
        return searchFirstQoCMetricValue(_qoCIndicator.list_qoCMetricValue, _qoCMetricValueId);
    }
}
