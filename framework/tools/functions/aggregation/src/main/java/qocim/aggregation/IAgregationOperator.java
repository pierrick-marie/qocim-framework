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
package qocim.aggregation;

import qocim.cdfm.operator.utils.NotValidInformationException;
import qocim.information.QInformation;

import java.util.List;
import java.util.Map;

/**
 * ICDFMOperator is the interface to define an operator. The operator is used by
 * a context data flow management function to produce new context reports.
 *
 *
 * @author Pierrick MARIE
 */
public interface IAgregationOperator {

	/**
	 * This method executes the operation of the operator. It produces a new
	 * information.
	 *
	 * @param input
	 *            The information used to execute the operation.
	 * @return The new information.
	 * @throws NotValidInformationException
	 *             An exception if it is impossible to execute the operation.
	 */
	List<QInformation> applyOperator(List<QInformation> input) throws NotValidInformationException;

	/**
	 * This method sets the parameters of the operator.
	 *
	 * @param parameters
	 *            the parameters of the operator.
	 * @return this.
	 */
	IAgregationOperator setParameters(Map<String, String> parameters);

	/**
	 * @return The parameters of the operator.
	 */
	Map<String, String> parameters();

	/**
	 * @return The name of the operator.
	 */
	String getName();
}
