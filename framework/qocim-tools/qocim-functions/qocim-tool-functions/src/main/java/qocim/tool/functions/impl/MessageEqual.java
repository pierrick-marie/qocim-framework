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
import java.util.Iterator;
import java.util.List;

import mucontext.datamodel.context.ContextObservation;
import qocim.datamodel.QoCIMFacade;
import qocim.datamodel.QoCIndicator;
import qocim.datamodel.QoCMetaData;
import qocim.datamodel.utils.ConstraintChecker;
import qocim.datamodel.utils.ConstraintCheckerException;
import qocim.tool.functions.IToolFunction;

/**
 * MessageEqual verifies if two context observations are equal. Two context
 * observations are consider as equal if the tool function
 * <i>observationEqual</i> returns <b>True</b> <i>and</i> if all the QoC
 * meta-data associated to the context observations are considered equal by the
 * tool function <i>qoCMetaDataEqual</i>. The expected arguments are two context
 * observation that have to be compared.
 *
 * @see qocim.tool.functions.impl.ObservationEqual
 * @see qocim.tool.functions.impl.QoCMetaDataEqual
 * @see mucontext.datamodel.context.ContextObservation
 * @see mucontext.datamodel.qocim.QoCMetaData
 *
 * @author Pierrick MARIE
 */
public class MessageEqual implements IToolFunction {

	// # # # # # CONSTANTS # # # # #

	/**
	 * The default value used to indicate if two QoC meta-data are equal:
	 * <b>zero</b>.
	 */
	private final static int QOCMETADATA_EQUALS = 0;

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
	 * The tool function used to verify if two QoC meta-data are equal or not.
	 */
	private QoCMetaDataEqual qoCMetaDataEqual;
	/**
	 * The tool function used to verify if the context observation are equal or
	 * not.
	 */
	private final ObservationEqual observationEqual;

	// # # # # # CONSTRUCTORS # # # # #

	public MessageEqual() {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		setUpIsDone = false;
		contextObservation1 = null;
		contextObservation2 = null;
		qoCMetaDataEqual = new QoCMetaDataEqual();
		observationEqual = new ObservationEqual();
		qoCMetaDataEqual = new QoCMetaDataEqual();
	}

	// # # # # # PUBLIC METHODS # # # # #

	/**
	 * The method executes the function <i>messageEqual</i>.
	 *
	 * @return <i>True</i> if the compared context observations are equal, else
	 *         * <i>False</i>
	 */
	@Override
	public Object exec() {
		// - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
		try {
			final String message = "MessageComparable.exec() method setup(ContextObservation<?>, ContextObservation<?>) have to be called before.";
			ConstraintChecker.assertTrue(setUpIsDone, message);
		} catch (final ConstraintCheckerException e) {
			return new Object();
		}
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		/*
		 * The value returned by the function.
		 */
		Boolean ret_qoCMetaDataEqual = true;
		/*
		 * The list of <b>QoCMetaData</b> associated to the first compared QoC
		 * meta-data.
		 */
		final List<QoCMetaData> list_qoCMetaData1 = getListQoCMeteData(contextObservation1);
		/*
		 * The list of <b>QoCMetaData</b> associated to the second compared QoC
		 * meta-data.
		 */
		final List<QoCMetaData> list_qoCMetaData2 = getListQoCMeteData(contextObservation2);
		/*
		 * An iterator of <b>QoCMetaData</b> to analyze the attributes
		 * <i>list_qoCMetaData1</i> and <i>list_qoCMetaData2</i>
		 */
		Iterator<QoCMetaData> iterator_qoCMetaData;
		// - - - - - CORE OF THE METHOD - - - - -
		observationEqual.setUp(contextObservation1, contextObservation2);
		if (!(Boolean) observationEqual.exec()) {
			return false;
		}
		if (list_qoCMetaData1.size() != list_qoCMetaData2.size()) {
			return false;
		}
		iterator_qoCMetaData = list_qoCMetaData1.iterator();
		while (iterator_qoCMetaData.hasNext() && ret_qoCMetaDataEqual) {
			ret_qoCMetaDataEqual = compareListQoCMetaData(iterator_qoCMetaData.next(), list_qoCMetaData2);
		}
		// - - - - - RETURN STATEMENT - - - - -
		return ret_qoCMetaDataEqual;
	}

	/**
	 * The method initializes the arguments of the function <i>messageEqual</i>.
	 *
	 * @param _contextObservation1
	 *            The first context observation that will be compared.
	 * @param _contextObservation2
	 *            The second context observation that will be compared.
	 * @return <b>this</b>
	 */
	public MessageEqual setUp(final ContextObservation<?> _contextObservation1,
			final ContextObservation<?> _contextObservation2) {
		// - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
		try {
			String message = "MessageComparable.setup(ContextObservation<?>, ContextObservation<?>): the argument _contextObservation1 is null";
			ConstraintChecker.notNull(_contextObservation1, message);
			message = "MessageComparable.setup(ContextObservation<?>, ContextObservation<?>): the argument _contextObservation2 is null";
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

	// # # # # # PRIVATE METHODS # # # # #

	/**
	 * The method compares a QoC meta-data with a list of QoC meta-data. It
	 * verifies if the QoC meta-data is contained and is equal to at least one
	 * element of the list.
	 *
	 * @param _qoCMetaData
	 *            The QoC meta-data compared by the function.
	 * @param list_qoCMetaData
	 *            The list of QoCMetaData where the argument <i>_qoCMetaData</i>
	 *            is searched.
	 * @return <i>True</i> if <i>_qoCMetaData</i> is equal to one of the QoC
	 *         meta-data of the argument <i>list_qoCMetaData</i> else
	 *         <i>False</i>
	 */
	private Boolean compareListQoCMetaData(final QoCMetaData _qoCMetaData, final List<QoCMetaData> list_qoCMetaData) {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		/*
		 * The value returned by the method.
		 */
		Boolean ret_qoCMetaDataEqual = false;
		/*
		 * An iterator of <b>QoCMetaData</b> used to iterate on
		 * <i>list_qoCMetaData</i>
		 */
		final Iterator<QoCMetaData> iterator_qoCMetaData = list_qoCMetaData.iterator();
		// - - - - - CORE OF THE METHOD - - - - -
		while (!ret_qoCMetaDataEqual && iterator_qoCMetaData.hasNext()) {
			qoCMetaDataEqual.setUp(_qoCMetaData, iterator_qoCMetaData.next());
			if (((Integer) qoCMetaDataEqual.exec()).equals(QOCMETADATA_EQUALS)) {
				ret_qoCMetaDataEqual = true;
			}
		}
		// - - - - - RETURN STATEMENT - - - - -
		return ret_qoCMetaDataEqual;
	}

	/**
	 * The method provides the list of all QoC meta-data associated to all
	 * context observation. For each QoC indicator associated to the context
	 * observation, the method creates a corresponding list of QoC meta-data.
	 *
	 * @param contextObservation
	 *            The context observation used to create the list of QoC
	 *            meta-data.
	 * @return The list of QoC meta-data.
	 */
	private List<QoCMetaData> getListQoCMeteData(final ContextObservation<?> contextObservation) {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		/*
		 * The list of <b>QoCMetaData</b> returned by the method.
		 */
		final List<QoCMetaData> ret_list_qoCMetaData = new ArrayList<QoCMetaData>();
		// - - - - - CORE OF THE METHOD - - - - -
		for (final QoCIndicator loop_qoCIndicator : contextObservation.list_qoCIndicator) {
			ret_list_qoCMetaData.addAll(QoCIMFacade.createListQoCMetaData(loop_qoCIndicator));
		}
		// - - - - - RETURN STATEMENT - - - - -
		return ret_list_qoCMetaData;
	}
}
