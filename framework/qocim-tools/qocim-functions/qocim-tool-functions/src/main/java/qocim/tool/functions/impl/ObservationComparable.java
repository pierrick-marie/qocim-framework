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

import mucontext.datamodel.context.ContextObservation;
import qocim.datamodel.utils.ConstraintChecker;
import qocim.datamodel.utils.ConstraintCheckerException;
import qocim.tool.functions.IToolFunction;

/**
 * ObservationComparable verifies if two context observations are comparable.
 *
 * @see mucontext.datamodel.context.ContextObservation
 *
 * @author Pierrick MARIE
 */
public class ObservationComparable implements IToolFunction {

	// # # # # # PRIVATE VARIABLES # # # # #

	/**
	 * Verifies if the configuration of the function has been done.
	 */
	private Boolean setUpIsDone;
	/**
	 * The first context observation compared with the second one.
	 */
	private ContextObservation<?> contextObservation1;
	/**
	 * The second context observation compared with the first one.
	 */
	private ContextObservation<?> contextObservation2;

	// # # # # # CONSTRUCTORS # # # # #

	public ObservationComparable() {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		setUpIsDone = false;
		contextObservation1 = null;
		contextObservation2 = null;
	}

	// # # # # # PUBLIC METHODS # # # # #

	/**
	 * The method executes the function <i>observationComparable</i>. It
	 * compares the following fields
	 * <ul>
	 * <li>observable.entity.name</li>
	 * <li>observable.entity.uri</li>
	 * <li>observable.uri</li>
	 * <li>observable.name</li>
	 * </ul>
	 *
	 * @return <i>True</i> if the compared fields are equal, else <i>False</i>.
	 */
	@Override
	public Object exec() {
		// - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
		try {
			final String message = "ObservationComparable.exec() method setup(ContextObservation<?>, ContextObservation<?>) have to be called before.";
			ConstraintChecker.assertTrue(setUpIsDone, message);
		} catch (final ConstraintCheckerException e) {
			return new Object();
		}
		// - - - - - RETURN STATEMENT - - - - -
		return contextObservation1.observable.entity.name.equals(contextObservation2.observable.entity.name)
				&& contextObservation1.observable.entity.uri.equals(contextObservation2.observable.entity.uri)
				&& contextObservation1.observable.uri.equals(contextObservation2.observable.uri)
				&& contextObservation1.observable.name.equals(contextObservation2.observable.name);
	}

	/**
	 * The method initializes the arguments of the function
	 * <i>observationComparable</i>.
	 *
	 * @param _contextObservation1
	 *            the first context observation that is compared.
	 * @param _contextObservation2
	 *            the first context observation that is compared.
	 * @return <b>this</b>
	 */
	public ObservationComparable setUp(final ContextObservation<?> _contextObservation1,
			final ContextObservation<?> _contextObservation2) {
		// - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
		try {
			String message = "ObservationComparable.setup(ContextObservation<?>, ContextObservation<?>): the argument _contextObservation1 is null";
			ConstraintChecker.notNull(_contextObservation1, message);
			message = "ObservationComparable.setup(ContextObservation<?>, ContextObservation<?>): the argument _contextObservation2 is null";
			ConstraintChecker.notNull(_contextObservation2, message);
		} catch (final ConstraintCheckerException e) {
			setUpIsDone = false;
			return this;
		}
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		contextObservation1 = _contextObservation1;
		contextObservation2 = _contextObservation2;
		setUpIsDone = true;
		// - - - - - RETURN STATEMENT - - - - -
		return this;
	}
}
