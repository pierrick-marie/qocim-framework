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
package qocim.datamodel;

import qocim.datamodel.utils.ConstraintChecker;
import qocim.datamodel.utils.ConstraintCheckerException;
import qocim.datamodel.utils.log.QoCIMLogger;

import java.util.logging.Level;

/**
 * QoCIM is the super class of the QoCIM model. All the class of the QoCIM model
 * inherit of this class.
 *
 * @author Pierrick MARIE
 *
 */
public abstract class QoCIM implements Cloneable {

    // # # # # # PROTECTED VARIABLES # # # # #

    /**
     * The name of the classes that extend the classes of QoCIM model.
     */
    String name;

    /**
     * The reverse-association between two classes. Example: the attribute
     * qoCCriterion of the class QoCIndicator represents the association between
     * QoCIndicator and QoCCriterion. In this example, the container of
     * QoCCriterion is QoCIndicator.
     */
    QoCIM container;

    // # # # # # CONSTRUCTORS # # # # #

    public QoCIM() {
        // - - - - - CORE OF THE METHOD - - - - -
        super();
        name = this.getClass().getSimpleName();
        // container = null;
    }

    public QoCIM(final QoCIM _qoCIM) {
        super();
        // - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
        try {
            ConstraintChecker.notNull(_qoCIM, "QoCIM.constructor(QoCIM): the argument _qoCIM is null.");
        } catch (final ConstraintCheckerException _exception) {
            final String message = "QoCIM.constructor(QoCIM): Fatal error, impossible to create a new QoCIM instance.";
            QoCIMLogger.logger.log(Level.SEVERE, message, _exception);
            System.exit(-1);
        }
        // - - - - - CORE OF THE METHOD - - - - -
        name = _qoCIM.name;
        container = _qoCIM.container;
    }

    // # # # # # PUBLIC METHODS # # # # #

    @Override
    public String toString() {
        // - - - - - RETURN STATEMENT - - - - -
        return name + " ";
    }

    public String name() {
        return name;
    }

    public QoCIM name(final String _name) {
        name = _name;
        return this;
    }

    public QoCIM container() {
        return container;
    }

    public QoCIM container(final QoCIM _container) {
        container = _container;
        return this;
    }
}
