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
package qocim.datamodel.utils.test;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import qocim.datamodel.utils.ConstraintChecker;
import qocim.datamodel.utils.ConstraintCheckerException;

@Ignore
public class TestArgumentChecker {

    @Test
    public final void testExpectedSizeOfArray() {

	/**
	 * Compares 4 arrays. The size varies between 0 and 3.
	 */
	Integer expectedSize;
	String errorMessage;
	Object[] list_object;
	Boolean catchException;
	for (int i = 0; i < 4; i++) {
	    errorMessage = "Expected size: " + i;
	    expectedSize = i;
	    list_object = new Object[i];

	    try {
		ConstraintChecker.expectedSizeOfArray(list_object, expectedSize, errorMessage);
	    } catch (final Exception _exception) {
		fail("Test " + i + " failed " + errorMessage);
	    }
	}

	/**
	 * Compares an array with wrong expected value
	 */
	errorMessage = "Wrong expected size";
	expectedSize = 1;
	list_object = new Object[0];
	catchException = false;
	try {
	    ConstraintChecker.expectedSizeOfArray(list_object, expectedSize, errorMessage);
	} catch (final Exception _exception) {
	    catchException = true;
	}
	if (!catchException) {
	    fail("Test failed " + errorMessage);
	}
    }

    @Test
    public final void testIntegerIsBetweenBounds() {

	List<List<Double>> list_test;
	/**
	 * list_testedValue is a tuple of three values: testedValue, minValue,
	 * maxValue
	 */
	List<Double> list_testedValue;
	Boolean catchException;

	list_test = new ArrayList<List<Double>>();

	/**
	 * tested value: 0 min value: 0 max value: 0
	 */
	list_testedValue = new ArrayList<Double>();
	list_testedValue.add(0.0);
	list_testedValue.add(0.0);
	list_testedValue.add(0.0);
	list_test.add(list_testedValue);

	/**
	 * tested value: 0 min value: 0 max value: 1
	 */
	list_testedValue = new ArrayList<Double>();
	list_testedValue.add(0.0);
	list_testedValue.add(0.0);
	list_testedValue.add(1.0);
	list_test.add(list_testedValue);

	/**
	 * tested value: 1 min value: 0 max value: 1
	 */
	list_testedValue = new ArrayList<Double>();
	list_testedValue.add(1.0);
	list_testedValue.add(0.0);
	list_testedValue.add(1.0);
	list_test.add(list_testedValue);

	/**
	 * tested value: 1 min value: 0 max value: 2
	 */
	list_testedValue = new ArrayList<Double>();
	list_testedValue.add(1.0);
	list_testedValue.add(0.0);
	list_testedValue.add(2.0);
	list_test.add(list_testedValue);

	for (final List<Double> list_value : list_test) {
	    try {
		ConstraintChecker.doubleIsBetweenBounds(list_value.get(0), list_value.get(1), list_value.get(2), "Test expected value: " + list_value.get(0));
	    } catch (final ConstraintCheckerException e) {
		fail("Test good value: " + list_value.get(0) + " failled");
	    }
	}

	/**
	 * tested value: 0 min value: 1 max value: 1
	 */
	list_test = new ArrayList<List<Double>>();
	list_testedValue = new ArrayList<Double>();
	list_testedValue.add(0.0);
	list_testedValue.add(1.0);
	list_testedValue.add(1.0);
	list_test.add(list_testedValue);

	/**
	 * tested value: 1 min value: 1 max value: 0
	 */
	list_testedValue = new ArrayList<Double>();
	list_testedValue.add(1.0);
	list_testedValue.add(1.0);
	list_testedValue.add(0.0);
	list_test.add(list_testedValue);

	catchException = false;
	for (final List<Double> list_value : list_test) {
	    try {
		ConstraintChecker.doubleIsBetweenBounds(list_value.get(0), list_value.get(1), list_value.get(2), "Test expected value: " + list_value.get(0));
	    } catch (final ConstraintCheckerException e) {
		catchException = true;
	    }
	    if (!catchException) {
		fail("Test wrong value: " + list_value.get(0) + " failled");
	    }
	}
    }

    @Test
    public final void testNotEmpty() {

	Object[] list_object;
	Boolean catchException;

	/**
	 * test not empty array
	 */
	list_object = new Object[1];
	try {
	    ConstraintChecker.notEmpty(list_object, "Test array not empty");
	} catch (final Exception _exception) {
	    fail("Test array not empty failled");
	}

	/**
	 * test empty array
	 */
	list_object = new Object[0];
	catchException = false;
	try {
	    ConstraintChecker.notEmpty(list_object, "Test array empty");
	} catch (final Exception _exception) {
	    catchException = true;
	}
	if (!catchException) {
	    fail("Test array empty failled");
	}
    }

    @Test
    public final void testNotNull() {

	Object object;
	Boolean catchException;

	/**
	 * Test not null object
	 */
	object = new Object();
	try {
	    ConstraintChecker.notNull(object, "Test object not null");
	} catch (final Exception _exception) {
	    fail("Test object not null failled");
	}

	/**
	 * Test null object
	 */
	object = null;
	catchException = false;
	try {
	    ConstraintChecker.notNull(object, "Test object null");
	} catch (final ConstraintCheckerException e) {
	    catchException = true;
	}
	if (!catchException) {
	    fail("Test object null failled");
	}
    }
}
