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

import java.util.ArrayList;
import java.util.List;

import mucontext.datamodel.context.ContextObservation;
import mucontext.datamodel.context.ContextReport;
import qocim.datamodel.utils.ConstraintChecker;
import qocim.datamodel.utils.ConstraintCheckerException;
import qocim.tool.functions.IToolFunction;

/**
 * @author Pierrick MARIE
 */
public class ContextReportComparable implements IToolFunction {

	// # # # # # PRIVATE VARIABLES # # # # #

	/**
	 * Verifies if the configuration of the function has been done.
	 */
	private Boolean setUpIsDone;
	/**
	 * The first context report to compare.
	 */
	private ContextReport contextReport1;
	/**
	 * The second context report to compare.
	 */
	private ContextReport contextReport2;
	/**
	 * The class used to compare messages.
	 */
	private final MessageComparable messageComparable;

	// # # # # # CONSTRUCTORS # # # # #

	public ContextReportComparable() {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		setUpIsDone = false;
		contextReport1 = null;
		contextReport2 = null;
		messageComparable = new MessageComparable();
	}

	// # # # # # PUBLIC METHODS # # # # #

	/**
	 * The method executes the function <i>messageEqual</i>. The method verifies
	 * if the context observations of the context reports are comparable and
	 * then, verifies if the QoC meta-data of the context reports are also
	 * comparable.
	 *
	 * @return <i>True</i> if the messages are equal, else <i>False</i>
	 */
	@Override
	public Object exec() {
		// - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
		try {
			ConstraintChecker.assertTrue(setUpIsDone,
					"ContextReportComparable.exec() method setup(ContextReport, ContextReport) have to be called before.");
		} catch (final ConstraintCheckerException e) {
			return new Object();
		}
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		/*
		 * The attribute returned by the function.
		 */
		Boolean contextReportAreComparable = false;
		/*
		 * The first context observation to compare.
		 */
		final List<ContextObservation<?>> list_contextObservation1 = new ArrayList<ContextObservation<?>>(
				contextReport1.observations);
		/*
		 * The second context observation to compare.
		 */
		final List<ContextObservation<?>> list_contextObservation2 = new ArrayList<ContextObservation<?>>(
				contextReport2.observations);
		// - - - - - CORE OF THE METHOD - - - - -
		if (list_contextObservation1.size() != list_contextObservation2.size()) {
			return false;
		}
		if (list_contextObservation1.size() == 0) {
			return true;
		}
		for (final ContextObservation<?> loop_contextObservation1 : list_contextObservation1) {
			for (final ContextObservation<?> loop_contextObservation2 : list_contextObservation2) {
				messageComparable.setUp(loop_contextObservation1, loop_contextObservation2);
				if ((Boolean) messageComparable.exec()) {
					contextReportAreComparable = true;
					break;
				}
			}
			if (!contextReportAreComparable) {
				return false;
			}
			contextReportAreComparable = false;
		}
		// - - - - - RETURN STATEMENT - - - - -
		return true;
	}

	/**
	 * The method initializes the arguments of the function
	 * <i>contextReportComparable</i>.
	 *
	 * @return this
	 */
	public ContextReportComparable setUp(final ContextReport _contextReport1, final ContextReport _contextReport2) {
		// - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
		try {
			String message = "ContextReportComparable.setup(ContextReport, ContextReport): the argument _contextReport1 is null";
			ConstraintChecker.notNull(_contextReport1, message);
			message = "ContextReportComparable.setup(ContextReport, ContextReport): the argument _contextReport2 is null";
			ConstraintChecker.notNull(_contextReport2, message);
		} catch (final ConstraintCheckerException e) {
			setUpIsDone = false;
			return this;
		}
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		contextReport1 = _contextReport1;
		contextReport2 = _contextReport2;
		setUpIsDone = true;
		// - - - - - RETURN STATEMENT - - - - -
		return this;
	}
}
