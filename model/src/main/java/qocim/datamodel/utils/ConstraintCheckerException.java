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
package qocim.datamodel.utils;

import qocim.datamodel.utils.log.QoCIMLogger;

import java.util.logging.Level;

/**
 * The ConstraintCheckerException class is an exception thrown when a constraint
 * on an object is not verified.
 *
 * This exception extends the RuntimeException exception. The class logs the
 * messages of the exception with log4j.
 *
 * @author Pierrick MARIE
 */
public class ConstraintCheckerException extends Exception {

    // # # # # # CONSTANTS # # # # #

    private static final long serialVersionUID = 7819545460707546168L;

    // # # # # # PRIVATE VARIABLES # # # # #

    private final String message;

    // # # # # # CONSTRUCTORS # # # # #

    public ConstraintCheckerException(final String _message) {
        // - - - - - INITIALIZE THE VARIABLES - - - - -
        message = "Constraint checker exception: " + _message;
        // - - - - - CORE OF THE METHOD - - - - -
        QoCIMLogger.logger.log(Level.WARNING, message, this);
    }

    // # # # # # PUBLIC METHODS # # # # #

    @Override
    public String toString() {
        // - - - - - RETURN STATEMENT - - - - -
        return message;
    }
}
