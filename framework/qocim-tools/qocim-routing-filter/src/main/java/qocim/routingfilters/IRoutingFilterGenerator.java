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

/**
 * IRoutingFilterGenerator is an interface used to automatically generate
 * routing filters. The interface has only on method (generateFilter) used to
 * generate a part of a routing filter.
 *
 * The totality of a routing filter is generate with a sorted collection of
 * differents implementations of this interface.
 *
 * @author Pierrick MARIE
 */
public interface IRoutingFilterGenerator {

    // # # # # # PUBLIC METHODS # # # # #

    /**
     * This method produce a part a routing filter.
     *
     * @return A part of a routing filter.
     */
    String generateFilter();
}
