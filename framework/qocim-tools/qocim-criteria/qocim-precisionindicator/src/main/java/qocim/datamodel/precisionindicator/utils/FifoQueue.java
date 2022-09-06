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
package qocim.datamodel.precisionindicator.utils;

import java.util.concurrent.ArrayBlockingQueue;

import qocim.datamodel.utils.ConstraintChecker;
import qocim.datamodel.utils.ConstraintCheckerException;

public class FifoQueue<T> extends ArrayBlockingQueue<T> {

	// # # # # # CONSTANTS # # # # #

	private static final long serialVersionUID = -4308766340272910429L;

	// # # # # # CONSTRUCTORS # # # # #

	public FifoQueue(final Integer capacity) {
		// - - - - - CORE OF THE METHOD - - - - -
		super(capacity);
	}

	// # # # # # PUBLIC METHODS # # # # #

	@Override
	public boolean add(final T _element) {
		// - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
		try {
			ConstraintChecker.notNull(_element, "FifoQueue.add: _element is null");
		} catch (final ConstraintCheckerException e) {
			return false;
		}
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		Boolean ret_queueIsNotFull = true;
		// - - - - - CORE OF THE METHOD - - - - -
		try {
			super.add(_element);
		} catch (final IllegalStateException exception) {
			super.poll();
			super.add(_element);
			ret_queueIsNotFull = false;
		}
		// - - - - - RETURN STATEMENT - - - - -
		return ret_queueIsNotFull;
	}
}
