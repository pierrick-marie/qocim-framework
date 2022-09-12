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
package qocim.functions;


import qocim.datamodel.information.QInformation;

/**
 * ICDFMFunctionResultListener is the interface used to define the listener that
 * will receive the new context reports produced by a context data flow
 * management function.
 *
 * @author Pierrick MARIE
 *
 */
public interface IQoCIMFunctionListener {

    /**
     * This method is used to notify the listener when a new context reporthas
     * been produced by a function.
     *
     * @param information
     *            The new information.
     */
    void newInformation(QInformation<?> information);

}
