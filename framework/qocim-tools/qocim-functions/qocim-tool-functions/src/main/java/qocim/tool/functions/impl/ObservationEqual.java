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
 * ObservationEqual verifies if two context observations are equal. The context
 * observations are considered as equal if they are considered as comparable and
 * their values (the filed <i>ContextObservation.value</i>) are equal. The QoC
 * meta-data associated to the context observation is not compared by the
 * function. The expected arguments are two ContextObservation that have to be
 * compared.
 *
 * @see qocim.tool.functions.impl.ObservationComparable
 * @see mucontext.datamodel.context.ContextObservation
 *
 * @author Pierrick MARIE
 */
public class ObservationEqual implements IToolFunction {

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
	/**
	 * The function used to verify if the context observations are comparable or
	 * not.
	 */
	private final ObservationComparable observationComparable;

	// # # # # # CONSTRUCTORS # # # # #

	public ObservationEqual() {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		setUpIsDone = false;
		contextObservation1 = null;
		contextObservation2 = null;
		observationComparable = new ObservationComparable();
	}

	// # # # # # PUBLIC METHODS # # # # #

	/**
	 * The method executes the function <i>observationEqual</i>. It verifies if
	 * the context observation are comparable and their values are equal.
	 *
	 * @return <b>True</b> or <b>False</b> whether the context observations are
	 *         equal or not.
	 */
	@Override
	public Object exec() {
		// - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
		try {
			final String message = "ObservationEqual.exec() method setup(ContextObservation<?>, ContextObservation<?>) have to be called before.";
			ConstraintChecker.assertTrue(setUpIsDone, message);
		} catch (final ConstraintCheckerException e) {
			return new Object();
		}
		// - - - - - RETURN STATEMENT - - - - -
		return (Boolean) observationComparable.exec() && contextObservation1.value.equals(contextObservation2.value);
	}

	/**
	 * The method initializes the arguments of the function
	 * <i>observationEqual</i>.
	 *
	 * @param _contextObservation1
	 *            the first context observation that will be compared.
	 * @param _contextObservation2
	 *            the second context observation that will be compared.
	 * @return <b>this</b>
	 */
	public ObservationEqual setUp(final ContextObservation<?> _contextObservation1,
			final ContextObservation<?> _contextObservation2) {
		// - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
		try {
			String message = "ObservationEqual.setup(ContextObservation<?>, ContextObservation<?>): the argument _contextObservation1 is null";
			ConstraintChecker.notNull(_contextObservation1, message);
			message = "ObservationEqual.setup(ContextObservation<?>, ContextObservation<?>): the argument _contextObservation2 is null";
			ConstraintChecker.notNull(_contextObservation2, message);
		} catch (final ConstraintCheckerException e) {
			setUpIsDone = false;
			return this;
		}
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		observationComparable.setUp(_contextObservation1, _contextObservation2);
		contextObservation1 = _contextObservation1;
		contextObservation2 = _contextObservation2;
		setUpIsDone = true;
		// - - - - - RETURN STATEMENT - - - - -
		return this;
	}
}
