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
package qocim.cdfm.function;

import java.util.List;
import java.util.Map;

import mucontext.datamodel.context.ContextReport;
import qocim.cdfm.operator.utils.NotValidInformationException;

/**
 * ICDFMOperator is the interface to define an operator. The operator is used by
 * a context data flow management function to produce new context reports.
 *
 * @see qocim.cdfm.function.SaveICDFMFunction
 *
 * @author Pierrick MARIE
 */
public interface ICDFMOperator {

    /**
     * This method executes the operation of the operator. It produces a new
     * information.
     *
     * @param _input
     *            The information used to execute the operation.
     * @return The new information.
     * @throws NotValidInformationException
     *             An exception if it is impossible to execute the operation.
     */
    List<ContextReport> applyOperator(List<ContextReport> _input) throws NotValidInformationException;

    /**
     * This method sets the parameters of the operator.
     *
     * @param _map_parameter
     *            the parameters of the operator.
     * @return this.
     */
    ICDFMOperator setParameters(Map<String, String> _map_parameter);

    /**
     * @return The parameters of the operator.
     */
    Map<String, String> parameters();

    /**
     * @return The name of the operator.
     */
    String getName();
}
