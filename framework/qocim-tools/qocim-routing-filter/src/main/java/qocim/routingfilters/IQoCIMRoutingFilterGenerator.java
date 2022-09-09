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
package qocim.routingfilters;

import java.util.List;
import java.util.Map;

import qocim.datamodel.QoCMetaData;

/**
 * This class extends the IRoutingFilterGenerator interface by adding two other
 * methods. This class is dedicated to generate QoCIM-based routing filters.
 *
 * @author Pierrick MARIE
 */
public interface IQoCIMRoutingFilterGenerator extends IRoutingFilterGenerator {

	// # # # # # PUBLIC METHODS # # # # #

	/**
	 * This method adds QoC-criterion constraints into a QoCIM-based routing
	 * filters.
	 *
	 * @param _qoCCriterionConstraints
	 *            The constraints to add into the routing filter.
	 */
	void addQoCCriterionConstraints(List<QoCMetaData> _qoCCriterionConstraints);

	/**
	 * This method adds QoC-value constraints into a QoCIM-based routing
	 * filters.
	 *
	 * @param _qoCValueConstraints
	 *            The QoC constraint to add into the filter.
	 */
	void addQoCValueConstraints(Map<QoCMetaData, EComparator> _qoCValueConstraints);
}
