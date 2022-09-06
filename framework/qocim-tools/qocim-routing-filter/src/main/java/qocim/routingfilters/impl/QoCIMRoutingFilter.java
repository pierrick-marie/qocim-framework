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
package qocim.routingfilters.impl;

import java.util.HashSet;
import java.util.LinkedHashSet;

import qocim.routingfilters.IRoutingFilterGenerator;

/**
 * This class is used to generate a QoCIM-based routing filter. The class
 * provides a method to add <b>IRoutingFilterGenerator</b>. To generate the
 * routing filter, the class calls the method <i>generateFilter</i> for each
 * added <b>IRoutingFilterGenerator</b>.
 *
 * @author Pierrick MARIE
 */
public class QoCIMRoutingFilter {

    // # # # # # PRIVATE VARIABLES # # # # #

    /**
     * The list of routing filter generator used to generate the filter.
     */
    private final HashSet<IRoutingFilterGenerator> list_routingFiltersGenerator;

    // # # # # # CONSTRUCTORS # # # # #

    public QoCIMRoutingFilter() {
	// - - - - - INITIALIZE THE VARIABLES - - - - -
	list_routingFiltersGenerator = new LinkedHashSet<IRoutingFilterGenerator>();
    }

    // # # # # # PUBLIC METHODS # # # # #

    /**
     * The method add a routing filter generator into the
     * <i>routingFiltersGeneratorList</i>.
     *
     * @param _routingFilterGenerator
     *            The filter generator to add into the list.
     */
    public void addFilterGenerator(final IRoutingFilterGenerator _routingFilterGenerator) {
	// - - - - - CORE OF THE METHOD - - - - -
	list_routingFiltersGenerator.add(_routingFilterGenerator);
    }

    /**
     * The method used to generate a QoC-based routing filter. It calls and
     * concats the result of the method <i>generateFilter</i> to each
     * <b>IRoutingFilterGenerator</b> of <i>list_routingFiltersGenerator</i>.
     *
     * @return The QoC-based routing filter.
     */
    public String getRoutingFilter() {
	// - - - - - INITIALIZE THE VARIABLES - - - - -
	/*
	 * The filter returned by the method.
	 */
	final String ret_filter;
	final StringBuffer stringBuffer = new StringBuffer();
	// - - - - - CORE OF THE METHOD - - - - -
	for (final IRoutingFilterGenerator routingFilterGenerator : list_routingFiltersGenerator) {
	    stringBuffer.append(routingFilterGenerator.generateFilter());
	}
	// Removing all end of line characters.
	ret_filter = stringBuffer.toString().replaceAll("[\r\n]+$", "");
	// - - - - - RETURN STATEMENT - - - - -
	return ret_filter;
    }

    @Override
    public String toString() {
	// - - - - - RETURN STATEMENT - - - - -
	return getRoutingFilter();
    }
}
