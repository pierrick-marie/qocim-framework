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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;

import mucontext.datamodel.context.ContextDataModelFacade;
import mucontext.datamodel.context.ContextEntity;
import mucontext.datamodel.context.ContextObservable;
import mucontext.datamodel.context.ContextObservation;
import mucontext.datamodel.context.ContextReport;
import qocim.datamodel.utils.ConstraintChecker;
import qocim.datamodel.utils.ConstraintCheckerException;
import qocim.datamodel.utils.QoCIMLogger;
import qocim.tool.functions.IToolFunction;

/**
 * CreateNewMessage is a facade to create a new context report The class use the
 * facade <b>ContextDataModelFacade</b> to help the creation of the context
 * report.
 *
 * @see mucontext.datamodel.context.ContextEntity
 * @see mucontext.datamodel.context.ContextObservable
 * @see mucontext.datamodel.context.ContextObservation
 * @see mucontext.datamodel.context.ContextDataModelFacade
 *
 * @author Pierrick MARIE
 */
public class CreateNewMessage implements IToolFunction {

	// # # # # # CONSTANTS # # # # #

	/**
	 * The default value of the field <i>counter_reportId</i>: <b>zero</b>.
	 */
	private static final int DEFAULT_REPORT_ID = 0;

	// # # # # # PRIVATE VARIABLES # # # # #

	/**
	 * Verifies if the configuration of the function has been done.
	 */
	private Boolean setUpIsDone;
	/**
	 * The name of the entity.
	 */
	private String contextEntityName;
	/**
	 * The URI of the entity.
	 */
	private String contextEntityUri;
	/**
	 * The name of the context observable.
	 */
	private String contextObservableName;
	/**
	 * The URI of the context observable.
	 */
	private String contextObservableUri;
	/**
	 * The id of the context observation.
	 */
	private String contextObservationId;
	/**
	 * The value of the context observation.
	 */
	private String contextObservationValue;
	/**
	 * A counter used to set the field <b>id</b> of the context reports created
	 * by the function. The <b>id</b> of the context reports have to be unique.
	 * The counter auto-incremented every time a context report is created.
	 */
	private Integer counter_reportId;
	/**
	 * The facade provided by the context data model to easily create instances
	 * of the model.
	 */
	private final ContextDataModelFacade facade;

	// # # # # # CONSTRUCTORS # # # # #

	public CreateNewMessage() {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		setUpIsDone = false;
		counter_reportId = Integer.valueOf(DEFAULT_REPORT_ID);
		facade = new ContextDataModelFacade("facade");
	}

	// # # # # # PUBLIC METHODS # # # # #

	/**
	 * The method executes the function <i>createContextObservation</i>. It
	 * creates a new context observation and associates to a new context report
	 * the observation.
	 *
	 * @return A new context report.
	 */
	@Override
	public Object exec() {
		// - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
		try {
			final String message = "CreateNewMessage.exec() method setUp(String, String, String, String, String, Long, String) have to be called before.";
			ConstraintChecker.assertTrue(setUpIsDone, message);
		} catch (final ConstraintCheckerException e) {
			return new Object();
		}
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		/*
		 * The value returned by the method: the new context report.
		 */
		final ContextReport ret_contextReport = facade.createContextReport("report-" + counter_reportId);
		/*
		 * The context observation associated to <i>ret_contextReport</i>.
		 */
		final ContextObservation<?> observation = createContextObservation();
		// - - - - - CORE OF THE METHOD - - - - -
		facade.addContextObservation(ret_contextReport, observation);
		counter_reportId++;
		// - - - - - RETURN STATEMENT - - - - -
		return ret_contextReport;
	}

	/**
	 * The method initializes the arguments of the function
	 * <i>createContextObservation</i>.
	 *
	 * @param _contextEntityName
	 *            The name of the context entity.
	 * @param _contextEntityUri
	 *            The URI of the context entity.
	 * @param _contextObservableName
	 *            The name of the context observable.
	 * @param _contextObservableUri
	 *            The URI of the context observable.
	 * @param _contextObservationId
	 *            The is of the context observation.
	 * @param _contextObservationDate
	 *            The creation date of the context observation.
	 * @param _contextObservationValue
	 *            The value of the context observation.
	 * @return <b>this</b>
	 */
	public CreateNewMessage setUp(final String _contextEntityName, final String _contextEntityUri,
			final String _contextObservableName, final String _contextObservableUri, final String _contextObservationId,
			final Long _contextObservationDate, final String _contextObservationValue,
			final String _contextObservationUnit) {
		// - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
		try {
			String message = "CreateNewMessage.setUp(String, String, String, String, String, Long, String, String): the argument _contextEntityName is null";
			ConstraintChecker.notNull(_contextEntityName, message);
			message = "CreateNewMessage.setUp(String, String, String, String, String, Long, String, String): the argument _contextEntityUri is null";
			ConstraintChecker.notNull(_contextEntityUri, message);
			message = "CreateNewMessage.setUp(String, String, String, String, String, Long, String, String): the argument _contextObservableName is null";
			ConstraintChecker.notNull(_contextObservableName, message);
			message = "CreateNewMessage.setUp(String, String, String, String, String, Long, String, String): the argument _contextObservableUri is null";
			ConstraintChecker.notNull(_contextObservableUri, message);
			message = "CreateNewMessage.setUp(String, String, String, String, String, Long, String, String): the argument _contextObservationId is null";
			ConstraintChecker.notNull(_contextObservationId, message);
			message = "CreateNewMessage.setUp(String, String, String, String, String, Long, String, String): the argument _contextObservationDate is null";
			ConstraintChecker.notNull(_contextObservationDate, message);
			message = "CreateNewMessage.setUp(String, String, String, String, String, Long, String, String): the argument _contextObservationValue is null";
			ConstraintChecker.notNull(_contextObservationValue, message);
			message = "CreateNewMessage.setUp(String, String, String, String, String, Long, String, String): the argument _contextObservationUnit is null";
			ConstraintChecker.notNull(_contextObservationUnit, message);
		} catch (final ConstraintCheckerException e) {
			setUpIsDone = false;
			return this;
		}
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		contextEntityName = _contextEntityName;
		contextEntityUri = _contextEntityUri;
		contextObservableName = _contextObservableName;
		contextObservableUri = _contextObservableUri;
		contextObservationId = _contextObservationId;
		contextObservationValue = _contextObservationValue;
		setUpIsDone = true;
		// - - - - - RETURN STATEMENT - - - - -
		return this;
	}

	// # # # # # PRIVATE METHODS # # # # #

	/**
	 * The method creates a new context observation with the arguments
	 * initialized by the method <i>setUp</i>.
	 *
	 * @return the new ContextObservation
	 */
	private ContextObservation<?> createContextObservation() {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		/*
		 * The value returned by the method.
		 */
		ContextObservation<?> ret_contextObservation = null;
		/*
		 * The context entity associated to <i>ret_contextObservation</i>.
		 */
		ContextEntity entity;
		/*
		 * The context observable associated to <i>ret_contextObservation</i>.
		 */
		ContextObservable observable;
		// - - - - - CORE OF THE METHOD - - - - -
		try {
			entity = facade.createContextEntity(contextEntityName, new URI(contextEntityUri));
			observable = facade.createContextObservable(contextObservableName, new URI(contextObservableUri), entity);
			ret_contextObservation = facade.createContextObservation(contextObservationId, contextObservationValue,
					observable);
		} catch (final URISyntaxException _exception) {
			QoCIMLogger.logger.log(Level.SEVERE, "CreatenewMessage.createContextObservation(): Fatal error.",
					_exception);
			System.exit(-1);
		}
		// - - - - - RETURN STATEMENT - - - - -
		return ret_contextObservation;
	}
}
