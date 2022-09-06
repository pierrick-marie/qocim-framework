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

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.LinkedBlockingQueue;

import qocim.datamodel.utils.ConstraintChecker;
import qocim.datamodel.utils.ConstraintCheckerException;

public class SortedBlockingList<T extends Comparable<? super T>> extends ArrayList<T> {

	// # # # # # CONSTANTS # # # # #

	private static final long serialVersionUID = 8951008834140561754L;

	// # # # # # PRIVATE VARIABLES # # # # #

	private final LinkedBlockingQueue<T> lastInsertedElements;

	// # # # # # CONSTRUCTORS # # # # #

	public SortedBlockingList(final Integer _capacity) {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		super(_capacity);
		lastInsertedElements = new LinkedBlockingQueue<T>(_capacity);
	}

	// # # # # # PUBLIC METHODS # # # # #

	public Integer put(final T _element) {
		// - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
		try {
			ConstraintChecker.notNull(_element, "SortedBlockingList.put: _element is null");
		} catch (final ConstraintCheckerException e) {
			return Integer.MIN_VALUE;
		}
		// - - - - - CORE OF THE METHOD - - - - -
		try {
			lastInsertedElements.add(_element);
		} catch (final IllegalStateException exception) {
			super.remove(lastInsertedElements.poll());
			lastInsertedElements.add(_element);
		}
		super.add(_element);
		Collections.sort(this);
		// - - - - - RETURN STATEMENT - - - - -
		return indexOf(_element);
	}
}
