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
package qocim.datamodel.utils;

/**
 * The ArgumentChecker class is used to verify basic properties of the objects.
 *
 * If the expected properties of the objects are not respected, an exception is
 * thrown.
 *
 * @author Pierrick MARIE
 */
public class ConstraintChecker {

    // # # # # # PUBLIC METHODS # # # # #

    /**
     * This method verifies if the value of a boolean is true. If not, an
     * ArgumentCheckerException is thrown.
     *
     * @param _testedValue
     *            The boolean evaluated by the method.
     * @param _errorMessage
     *            The message of the exception.
     * @throws ConstraintCheckerException
     */
    public static final void assertTrue(final Boolean _testedValue, final String _errorMessage) throws ConstraintCheckerException {
	// - - - - - CORE OF THE METHOD - - - - -
	if (!_testedValue) {
	    throw new ConstraintCheckerException(_errorMessage + ": " + _testedValue);
	}
    }

    /**
     * This method verifies the size of an array. If not, an
     * ArgumentCheckerException is thrown.
     *
     * @param _testedObjectsList
     *            The array evaluated by the method.
     * @param _expectedSize
     *            The expected size of the array.
     * @param _errorMessage
     *            The message of the exception.
     * @throws ConstraintCheckerException
     */
    public static final void expectedSizeOfArray(final Object[] _testedObjectsList, final Integer _expectedSize, final String _errorMessage)
	    throws ConstraintCheckerException {
	// - - - - - CORE OF THE METHOD - - - - -
	notNull(_testedObjectsList, "ArgumentChecker.expectedSizeOfList: argument _testedObjectsList is null");
	notNull(_expectedSize, "ArgumentChecker.expectedSizeOfList: argument _expectedSize is null");
	notNull(_errorMessage, "ArgumentChecker.expectedSizeOfList: argument _errorValue is null");
	if (_testedObjectsList.length != _expectedSize) {
	    throw new ConstraintCheckerException(_errorMessage);
	}
    }

    /**
     * The method verifies if a double is between bounds. If not, an
     * ArgumentCheckerException is thrown.
     *
     * @param _testedValue
     *            The evaluated integer.
     * @param _minValue
     *            The min value of the integer.
     * @param _maxValue
     *            The max value of the integer.
     * @param _errorMessage
     *            The message of the exception.
     * @throws ConstraintCheckerException
     */
    public static final void doubleIsBetweenBounds(final Double _testedValue, final Double _minValue, final Double _maxValue, final String _errorMessage)
	    throws ConstraintCheckerException {
	// - - - - - CORE OF THE METHOD - - - - -
	notNull(_testedValue, "ArgumentChecker.integerIsBetweenBounds: argument _testedValue is null");
	notNull(_minValue, "ArgumentChecker.integerIsBetweenBounds: argument _minValue is null");
	notNull(_maxValue, "ArgumentChecker.integerIsBetweenBounds: argument _maxValue is null");
	notNull(_errorMessage, "ArgumentChecker.integerIsBetweenBounds: argument _errorValue is null");
	if (_minValue > _testedValue || _maxValue < _testedValue) {
	    throw new ConstraintCheckerException(_errorMessage + ": " + _testedValue);
	}
    }

    /**
     * The method verifies if an integer is between bounds. If not, an
     * ArgumentCheckerException is thrown.
     *
     * @param _testedValue
     *            The evaluated integer.
     * @param _minValue
     *            The min value of the integer.
     * @param _maxValue
     *            The max value of the integer.
     * @param _errorMessage
     *            The message of the exception.
     * @throws ConstraintCheckerException
     */
    public static final void integerIsBetweenBounds(final Integer _testedValue, final Integer _minValue, final Integer _maxValue, final String _errorMessage)
	    throws ConstraintCheckerException {
	// - - - - - CORE OF THE METHOD - - - - -
	notNull(_testedValue, "ArgumentChecker.integerIsBetweenBounds: argument _testedValue is null");
	notNull(_minValue, "ArgumentChecker.integerIsBetweenBounds: argument _minValue is null");
	notNull(_maxValue, "ArgumentChecker.integerIsBetweenBounds: argument _maxValue is null");
	notNull(_errorMessage, "ArgumentChecker.integerIsBetweenBounds: argument _errorValue is null");
	if (_minValue > _testedValue || _maxValue < _testedValue) {
	    throw new ConstraintCheckerException(_errorMessage + ": " + _testedValue);
	}
    }

    /**
     * This method verifies if an array is not empty. If not, an
     * ArgumentCheckerException is thrown.
     *
     * @param _testedObjectList
     *            The array evaluated by the method.
     * @param _errorMessage
     *            The message of the exception.
     * @throws ConstraintCheckerException
     */
    public static final void notEmpty(final Object[] _testedObjectList, final String _errorMessage) throws ConstraintCheckerException {
	// - - - - - CORE OF THE METHOD - - - - -
	notNull(_testedObjectList, "ArgumentChecker.notEmpty: argument _testedObjectList is null");
	notNull(_errorMessage, "ArgumentChecker.notEmpty: argument _errorMessage is null");
	if (_testedObjectList.length == 0) {
	    throw new ConstraintCheckerException(_errorMessage);
	}
    }

    /**
     * This method verifiers if an object is not null. If not, an
     * ArgumentCheckerException is thrown.
     *
     * @param _testedObject
     *            The object evaluated by the method.
     * @param _errorMessage
     *            The message of the exception.
     * @throws ConstraintCheckerException
     */
    public static final void notNull(final Object _testedObject, final String _errorMessage) throws ConstraintCheckerException {
	// - - - - - CORE OF THE METHOD - - - - -
	if (_errorMessage == null) {
	    throw new ConstraintCheckerException("_errorMessage is null");
	}
	if (_testedObject == null) {
	    throw new ConstraintCheckerException(_errorMessage);
	}
    }
}
