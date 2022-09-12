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
package qocim.cdfm.operator.aggregation;

import qocim.cdfm.function.ICDFMOperator;
import qocim.cdfm.operator.utils.NotValidInformationException;
import qocim.datamodel.information.QInformation;
import qocim.datamodel.utils.ConstraintChecker;
import qocim.datamodel.utils.ConstraintCheckerException;

import java.util.*;

public abstract class ArithmeticAggregationOperator implements ICDFMOperator {

    // # # # # # CONSTRUCTORS # # # # #

    protected ArithmeticAggregationOperator() { }

    // # # # # # PUBLIC METHODS # # # # #

    /**
     * This method aggregated a list of double values into another double.
     *
     * @param informationList
     *            The list of double to aggregate.
     * @return The resulting double value.
     */
    public abstract QInformation<?> aggregateInformation(final List<QInformation<?>> informationList);

    @Override
    public List<QInformation<?>> applyOperator(final List<QInformation<?>> informationList) throws NotValidInformationException {
        // - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
        try {
            final String message = "ArithmeticAggregationOperator.applyOperator(List<ContextReport>): the argument informationList is not valid.";
            ConstraintChecker.notEmpty(informationList.toArray(), message);
        } catch (final ConstraintCheckerException _exception) {
            throw new NotValidInformationException(_exception.getMessage());
        }
        // - - - - - INITIALIZE THE VARIABLES - - - - -
        /*
         * The resulting list of information. For the aggregation the list
         * contains only one context report.
         */
        final List<QInformation<?>> ret_informationList = new ArrayList<QInformation<?>>();

        ret_informationList.add(aggregateInformation(informationList));
        // - - - - - RETURN STATEMENT - - - - -
        return ret_informationList;
    }

    @Override
    public Map<String, String> parameters() {
        // - - - - - INITIALIZE THE VARIABLES - - - - -
        final Map<String, String> ret_mapParameter = new HashMap<String, String>();
        // - - - - - RETURN STATEMENT - - - - -
        return ret_mapParameter;
    }

    // # # # # # PROTECTED METHODS # # # # #

    /**
     * This method is used to clear the context information and QoC meta-data of
     * a list of context reports.
     *
     * @param informationList
     *            The list of context report to clear.
     *
     * @deprecated
     */
    protected void clearInformationList(final List<QInformation<?>> informationList) {
        // - - - - - CORE OF THE METHOD - - - - -
        for (final QInformation<?> information : informationList) {
            information.indicators().clear();
        }
    }
}
