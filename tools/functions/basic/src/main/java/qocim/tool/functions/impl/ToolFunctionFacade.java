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
package qocim.tool.functions.impl;

import java.util.ArrayList;
import java.util.List;

import qocim.datamodel.QoCIndicator;
import qocim.datamodel.QoCMetaData;
import qocim.datamodel.QoCMetricDefinition;
import qocim.datamodel.information.QInformation;
import qocim.tool.functions.utils.QoCFilter;

/**
 * ToolFunctionFacade is a facade to easily use the tool functions defined in
 * every classes of the package <b>qocim.tool.functions.impl</b>.
 *
 * @author Pierrick MARIE
 */
public class ToolFunctionFacade {

    // # # # # # CONSTANTS # # # # #

    /**
     * This list is used to configure the functions of the class
     * <b>ToolFunctionFacade</b>. The list contains all available
     * <b>QoCMetricValue</b>.
     */
    public static final List<QoCMetricDefinition> list_availableQoCMetricDefinition = new ArrayList<QoCMetricDefinition>();

    /**
     * This list is used to configure the functions of the class
     * <b>ToolFunctionFacade</b>. The list contains all available
     * <b>QoCIndicator</b>.
     */
    public static final List<QoCIndicator> list_availableQoCIndicator = new ArrayList<QoCIndicator>();

    // # # # # # PUBLIC METHODS # # # # #

    /**
     * The method computes the value of the private field <i>id</i> of the next
     * QoC metric value associated to a specific QoC indicator and an existing
     * context observation.
     *
     * @param _list_qoCMetaData
     *            The list of the QoC meta-data already associated to the
     *            context observation.
     * @param _qoCIndicatorId
     *            The <i>id</i> of the QoC indicator used to compute the
     *            <i>id</i> of the next QoC metric value.
     * @return The <i>id</i> of the next QoC metric value.
     *
     * @see qocim.tool.functions.impl.ComputeQoCMetricId
     */
    public static Integer computeQoCMetricId(final List<QoCMetaData> _list_qoCMetaData, final Integer _qoCIndicatorId) {
        // - - - - - INITIALIZE THE VARIABLES - - - - -
        final ComputeQoCMetricId computeQoCMetricId = new ComputeQoCMetricId();
        // - - - - - CORE OF THE METHOD - - - - -
        computeQoCMetricId.setUp(_list_qoCMetaData, _qoCIndicatorId);
        // - - - - - RETURN STATEMENT - - - - -
        return (Integer) computeQoCMetricId.exec();
    }

    /**
     * The method computes the value of QoC metric-value from the values of a
     * context observation.
     *
     * @param information
     *            The  context information.
     * @param qoCMetricDefinitionId
     *            The <i>id</i> of the QoC metric definition used to produce the
     *            value of the QoC meta-data.
     * @param qoCMetaDataList
     *            The list of the existing QoC meta-data associated to the
     *            context observation.
     * @return The <i>value</i> of the QoC meta-data.
     *
     * @see qocim.tool.functions.impl.ComputeQoCMetricValue
     */
    public static Integer computeQoCMetricValue(final QInformation information,
                                                final String qoCMetricDefinitionId, final List<QoCMetaData> qoCMetaDataList) {
        // - - - - - INITIALIZE THE VARIABLES - - - - -
        final ComputeQoCMetricValue computeQoCMetricValue = new ComputeQoCMetricValue(
                list_availableQoCMetricDefinition);
        // - - - - - CORE OF THE METHOD - - - - -
        computeQoCMetricValue.setUp(information, qoCMetricDefinitionId, qoCMetaDataList);
        // - - - - - RETURN STATEMENT - - - - -
        return (Integer) computeQoCMetricValue.exec();
    }

    /**
     * The method returns the current date.
     *
     * @return The current date exported in nanoseconds.
     *
     * @see qocim.tool.functions.impl.CurrentDate
     */
    public static Double currentDate() {
        // - - - - - CORE OF THE METHOD - - - - -
        final CurrentDate currentDate = new CurrentDate();
        // - - - - - RETURN STATEMENT - - - - -
        return (Double) currentDate.exec();
    }

    /**
     * The method returns the list of the primitive QoC metric definition of a
     * composite QoC metric definition.
     *
     * @param _qoCMetricDefinitionId
     *            The <i>id</i> of the QoC metric definition where the method
     *            have to get the primitive QoC metric definition.
     * @return The list of primitive QoC metric definition.
     *
     * @see qocim.tool.functions.impl.GetMostRecentQoCMetaData
     */
    @SuppressWarnings("unchecked")
    public static List<QoCMetricDefinition> getListPrimitiveDefinition(final String _qoCMetricDefinitionId) {
        // - - - - - INITIALIZE THE VARIABLES - - - - -
        final GetListPrimitiveQoCMetricDefinition getListPrimitiveQoCMetricDefinition = new GetListPrimitiveQoCMetricDefinition(
                list_availableQoCIndicator);
        // - - - - - CORE OF THE METHOD - - - - -
        getListPrimitiveQoCMetricDefinition.setUp(_qoCMetricDefinitionId);
        // - - - - - RETURN STATEMENT - - - - -
        return (ArrayList<QoCMetricDefinition>) getListPrimitiveQoCMetricDefinition.exec();
    }

    /**
     * The method finds the most recent QoC meta-data from a list of QoC
     * meta-data and produced with a specific QoC metric definition.
     *
     * @param _qoCMetricDefinitionId
     *            The <i>id</i> of the QoC metric definition used to produce the
     *            QoC meta-data that the method is looking for.
     * @param _list_qoCMetaData
     *            The list of the QoC meta-date where the method have to search
     *            the most recent QoC meta-data.
     * @return The most recent QoC meta-data.
     *
     * @see qocim.tool.functions.impl.GetMostRecentQoCMetaData
     */
    public static QoCMetaData getMostRecentQoCMetaData(final String _qoCMetricDefinitionId,
                                                       final List<QoCMetaData> _list_qoCMetaData) {
        // - - - - - INITIALIZE THE VARIABLES - - - - -
        final GetMostRecentQoCMetaData getMostRecentQoCMetaData = new GetMostRecentQoCMetaData();
        // - - - - - CORE OF THE METHOD - - - - -
        getMostRecentQoCMetaData.setUp(_qoCMetricDefinitionId, _list_qoCMetaData);
        // - - - - - RETURN STATEMENT - - - - -
        return (QoCMetaData) getMostRecentQoCMetaData.exec();
    }

    /**
     * The method verifies if a QoC criterion is a composite criterion.
     *
     * @param _qoCCriterionId
     *            The <i>id</i> of the QoC criterion that should be checked.
     * @return A boolean: <i>True</i> means the QoC criterion is composite,
     *         <i>False</i> means the criterion is primitive (not composite).
     */
    public static Boolean isCompositeCriterion(final String _qoCCriterionId) {
        // - - - - - INITIALIZE THE VARIABLES - - - - -
        final IsCompositeQoCCriterion isCompositeQoCCriterion = new IsCompositeQoCCriterion(list_availableQoCIndicator);
        // - - - - - CORE OF THE METHOD - - - - -
        isCompositeQoCCriterion.setUp(_qoCCriterionId);
        // - - - - - RETURN STATEMENT - - - - -
        return (Boolean) isCompositeQoCCriterion.exec();
    }

    /**
     * The method compares the values of a QoC meta-data with the constraints of
     * a QoC filter.
     *
     * @param _qoCMetaData
     *            The QoC meta-data that should be compared.
     * @param _qoCFilter
     *            The QoC filter that contains the constraints.
     * @return A boolean: <i>True</i> means the values of the QoC meta-data
     *         respect the constraints of the filter, <i>False</i> means the
     *         value of the QoC meta-data do not respect the constraints of the
     *         QoC filter.
     *
     * @see qocim.tool.functions.impl.MatchingQoCFilter
     */
    public static Boolean matchingQoCFilter(final QoCMetaData _qoCMetaData, final QoCFilter _qoCFilter) {
        // - - - - - INITIALIZE THE VARIABLES - - - - -
        final MatchingQoCFilter matchingQoCFilter = new MatchingQoCFilter();
        // - - - - - CORE OF THE METHOD - - - - -
        matchingQoCFilter.setUp(_qoCMetaData, _qoCFilter);
        // - - - - - RETURN STATEMENT - - - - -
        return (Boolean) matchingQoCFilter.exec();
    }

    /**
     * The method verifies if two QoC meta-data are comparable.
     *
     * @param _qoCMetaData1
     *            The first QoC meta-data that should be compared.
     * @param _qoCMetaData2
     *            The second QoC meta-data that should be compared.
     * @return A boolean: <i>True</i> means the QoC meta-data are comparable,
     *         <i>False</i> means the QoC meta-data are not comparable.
     *
     * @see qocim.tool.functions.impl.QoCMetaDataComparable
     */
    public static Boolean qoCMetaDataComparable(final QoCMetaData _qoCMetaData1, final QoCMetaData _qoCMetaData2) {
        // - - - - - INITIALIZE THE VARIABLES - - - - -
        final QoCMetaDataComparable qoCMetaDataComparable = new QoCMetaDataComparable();
        // - - - - - CORE OF THE METHOD - - - - -
        qoCMetaDataComparable.setUp(_qoCMetaData1, _qoCMetaData2);
        // - - - - - RETURN STATEMENT - - - - -
        return (Boolean) qoCMetaDataComparable.exec();
    }

    /**
     * The method verifies if two QoC meta-data are equal.
     *
     * @param _qoCMetaData1
     *            The first QoC meta-data that should be compared.
     * @param _qoCMetaData2
     *            The second QoC meta-data that should be compared.
     * @return An integer: -1, 0 or +1.
     *         <ul>
     *         <li><i>-1</i>: the first QoC meta-data is <i>lower</i> than the
     *         second one</li>
     *         <li><i>0</i>: the QoC meta-data are equal</li>
     *         <li><i>+1</i>: the first QoC meta-data is <i>upper</i> than the
     *         second one</li>
     *         </ul>
     *
     * @see qocim.tool.functions.impl.QoCMetaDataEqual
     */
    public static Integer qoCMetaDataEqual(final QoCMetaData _qoCMetaData1, final QoCMetaData _qoCMetaData2) {
        // - - - - - INITIALIZE THE VARIABLES - - - - -
        final QoCMetaDataEqual qoCMetaDataEqual = new QoCMetaDataEqual();
        // - - - - - CORE OF THE METHOD - - - - -
        qoCMetaDataEqual.setUp(_qoCMetaData1, _qoCMetaData2);
        // - - - - - RETURN STATEMENT - - - - -
        return (Integer) qoCMetaDataEqual.exec();
    }
}
