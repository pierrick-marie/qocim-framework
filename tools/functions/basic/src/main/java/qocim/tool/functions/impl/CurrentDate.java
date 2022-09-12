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
package qocim.tool.functions.impl;

import java.util.Date;

import qocim.tool.functions.IToolFunction;

/**
 * CreationDate returns the current date.
 *
 * @author Pierrick MARIE
 */
public class CurrentDate implements IToolFunction {

    // # # # # # PUBLIC METHODS # # # # #

    @Override
    public Object exec() {
	// - - - - - RETURN STATEMENT - - - - -
	return new Date();
    }
}
