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
package qocim.routingfilters.test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.lang.reflect.UndeclaredThrowableException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.script.ScriptException;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import junit.framework.Assert;
import mucontext.datamodel.context.ContextDataModelFacade;
import mucontext.datamodel.context.ContextEntity;
import mucontext.datamodel.context.ContextObservable;
import mucontext.datamodel.context.ContextObservation;
import mucontext.datamodel.context.ContextReport;
import qocim.datamodel.QoCIndicator;
import qocim.datamodel.QoCMetaData;
import qocim.datamodel.test.criterion.one.TestCriterionOneFactory;
import qocim.datamodel.test.criterion.zero.TestCriterionZeroFactory;
import qocim.routingfilters.EComparator;
import qocim.routingfilters.impl.JavaScriptBeginRoutingFilterGenerator;
import qocim.routingfilters.impl.JavaScriptEndRoutingFilterGenerator;
import qocim.routingfilters.impl.JavaScriptQoCIMRoutingFilterGenerator;
import qocim.routingfilters.impl.QoCIMRoutingFilter;

public class TestJavaScriptQoCIMRoutingFilterGenerator {

	private static final double DEFAULT_QOCVALUE = 0;
	private static final double DEFAULT_OBSERVATIONVALUE = 0;
	private QoCMetaData qoCMetaDataIndicatorZero;
	private QoCMetaData qoCMetaDataIndicatorOne;
	private JavaScriptBeginRoutingFilterGenerator beginFilterGenerator;
	private JavaScriptQoCIMRoutingFilterGenerator qoCFilterGenerator;
	private JavaScriptEndRoutingFilterGenerator endFilterGenerator;
	private QoCIMRoutingFilter routingFilter;
	private ContextDataModelFacade facade;

	private ContextObservation<?> contextObservation;
	private Integer counter_qoCMetricValueId = 0;

	private void initContextObservation() {

		String uriPath;
		ContextEntity userEntity;
		final List<ContextEntity> list_entitie;
		ContextObservable observable;
		Double observationValue;
		URI uri;

		list_entitie = new ArrayList<ContextEntity>();
		contextObservation = null;

		uriPath = "mucontext://localhost:3000";
		try {
			uri = new URI(uriPath + "/user/Somebody");
			userEntity = facade.createContextEntity("Somebody", uri);
			list_entitie.add(userEntity);
			uri = new URI(uriPath + "/Something");
			observable = facade.createContextObservable("Something", uri, userEntity);
			observationValue = DEFAULT_OBSERVATIONVALUE;
			contextObservation = facade.createContextObservation("Observation", observationValue, observable);
		} catch (final URISyntaxException e) {
			e.printStackTrace();
		}
	}

	@Before
	public void setUp() throws Exception {

		qoCMetaDataIndicatorZero = new QoCMetaData(
				TestCriterionZeroFactory.instance().newQoCIndicator("" + counter_qoCMetricValueId++, DEFAULT_QOCVALUE));
		qoCMetaDataIndicatorOne = new QoCMetaData(
				TestCriterionOneFactory.instance().newQoCIndicator("" + counter_qoCMetricValueId++, DEFAULT_QOCVALUE));
		qoCFilterGenerator = new JavaScriptQoCIMRoutingFilterGenerator();
		beginFilterGenerator = new JavaScriptBeginRoutingFilterGenerator();
		endFilterGenerator = new JavaScriptEndRoutingFilterGenerator();
		routingFilter = new QoCIMRoutingFilter();
		facade = new ContextDataModelFacade("facade");

		initContextObservation();
	}

	@Test
	public final void testGenerateFilterWithManyQoCCriterionConstraintANDOperator() {

		List<QoCMetaData> list_metaData;
		List<QoCIndicator> list_qoCIndicator;
		ContextReport contextReport;

		list_metaData = new ArrayList<QoCMetaData>();
		list_qoCIndicator = new ArrayList<QoCIndicator>();

		/**
		 * Create a filter with many QoC-value constraints separated with an OR
		 * binary operator
		 */
		list_metaData.add(qoCMetaDataIndicatorZero);
		qoCFilterGenerator.addQoCCriterionConstraints(list_metaData);
		list_metaData = new ArrayList<QoCMetaData>();
		list_metaData.add(qoCMetaDataIndicatorOne);
		qoCFilterGenerator.addQoCCriterionConstraints(list_metaData);

		/**
		 * Test many QoC-criterion constraint with
		 * JavaScriptBeginRoutingFilterGenerator
		 */
		assertEquals(qoCFilterGenerator.generateFilter(),
				"if( (xpath.evaluate(\"//qocindicator[@id='0' and qoccriterion[@id='[0.1]']/qocmetricdefinition[@id='0.1']]\", doc, XPathConstants.NODESET).length == 0)) {return false}if( (xpath.evaluate(\"//qocindicator[@id='1' and qoccriterion[@id='[1.1]']/qocmetricdefinition[@id='1.1']]\", doc, XPathConstants.NODESET).length == 0)) {return false}");

		/**
		 * Test the production of the entire filter composed of one
		 * QoC-criterion constraint
		 */
		routingFilter.addFilterGenerator(beginFilterGenerator);
		routingFilter.addFilterGenerator(qoCFilterGenerator);
		routingFilter.addFilterGenerator(endFilterGenerator);
		assertEquals(routingFilter.getRoutingFilter(), "function evaluate(doc) {"
				+ "	load(\"nashorn:mozilla_compat.js\");importPackage(javax.xml.xpath);	var xpath = XPathFactory.newInstance().newXPath();if( (xpath.evaluate(\"//qocindicator[@id='0' and qoccriterion[@id='[0.1]']/qocmetricdefinition[@id='0.1']]\", doc, XPathConstants.NODESET).length == 0)) {return false}if( (xpath.evaluate(\"//qocindicator[@id='1' and qoccriterion[@id='[1.1]']/qocmetricdefinition[@id='1.1']]\", doc, XPathConstants.NODESET).length == 0)) {return false}return true;}");

		try {
			/**
			 * Test filter with a context report without QoC
			 */
			contextReport = facade.createContextReport("report 0");
			list_qoCIndicator.clear();
			facade.addQoCIndicators(contextObservation, list_qoCIndicator);
			facade.addContextObservation(contextReport, contextObservation);
			Assert.assertFalse(Util.evaluate(routingFilter.getRoutingFilter(), facade.serialiseToXML(contextReport)));

			/**
			 * Test filter with a context report with one QoC indicator
			 * (indicator zero)
			 */
			contextReport = facade.createContextReport("report 1");
			initContextObservation();
			list_qoCIndicator.clear();
			list_qoCIndicator.add(qoCMetaDataIndicatorZero.qoCIndicator());
			facade.addQoCIndicators(contextObservation, list_qoCIndicator);
			facade.addContextObservation(contextReport, contextObservation);
			Assert.assertFalse(Util.evaluate(routingFilter.getRoutingFilter(), facade.serialiseToXML(contextReport)));

			/**
			 * Test filter with a context report with one QoC indicator
			 * (indicator one)
			 */
			contextReport = facade.createContextReport("report 2");
			initContextObservation();
			list_qoCIndicator.clear();
			list_qoCIndicator.add(qoCMetaDataIndicatorOne.qoCIndicator());
			facade.addQoCIndicators(contextObservation, list_qoCIndicator);
			facade.addContextObservation(contextReport, contextObservation);
			Assert.assertFalse(Util.evaluate(routingFilter.getRoutingFilter(), facade.serialiseToXML(contextReport)));

			/**
			 * Test filter with a context report with both QoC indicator
			 * (indicator zero and one)
			 */
			contextReport = facade.createContextReport("report 3");
			initContextObservation();
			list_qoCIndicator.clear();
			list_qoCIndicator.add(qoCMetaDataIndicatorOne.qoCIndicator());
			list_qoCIndicator.add(qoCMetaDataIndicatorZero.qoCIndicator());
			facade.addQoCIndicators(contextObservation, list_qoCIndicator);
			facade.addContextObservation(contextReport, contextObservation);
			Assert.assertTrue(Util.evaluate(routingFilter.getRoutingFilter(), facade.serialiseToXML(contextReport)));
		} catch (final ScriptException e) {
			e.printStackTrace();
		} catch (final ParserConfigurationException e) {
			e.printStackTrace();
		} catch (final SAXException e) {
			e.printStackTrace();
		} catch (final IOException e) {
			e.printStackTrace();
		} catch (final UndeclaredThrowableException e) {
			e.printStackTrace();
		}
	}

	@Test
	public final void testGenerateFilterWithManyQoCCriterionConstraintOROperator() {

		final List<QoCMetaData> list_metaData;
		List<QoCIndicator> list_qoCIndicator;
		ContextReport contextReport;

		list_metaData = new ArrayList<QoCMetaData>();
		list_qoCIndicator = new ArrayList<QoCIndicator>();

		/**
		 * Create a filter with many QoC-value constraints separated with an OR
		 * binary operator
		 */
		list_metaData.add(qoCMetaDataIndicatorZero);
		list_metaData.add(qoCMetaDataIndicatorOne);
		qoCFilterGenerator.addQoCCriterionConstraints(list_metaData);

		/**
		 * Test many QoC-criterion constraint with
		 * JavaScriptBeginRoutingFilterGenerator
		 */
		assertEquals(qoCFilterGenerator.generateFilter(),
				"if( (xpath.evaluate(\"//qocindicator[@id='0' and qoccriterion[@id='[0.1]']/qocmetricdefinition[@id='0.1']]\", doc, XPathConstants.NODESET).length == 0) && (xpath.evaluate(\"//qocindicator[@id='1' and qoccriterion[@id='[1.1]']/qocmetricdefinition[@id='1.1']]\", doc, XPathConstants.NODESET).length == 0)) {return false}");

		/**
		 * Test the production of the entire filter composed of one
		 * QoC-criterion constraint
		 */
		routingFilter.addFilterGenerator(beginFilterGenerator);
		routingFilter.addFilterGenerator(qoCFilterGenerator);
		routingFilter.addFilterGenerator(endFilterGenerator);
		assertEquals(routingFilter.getRoutingFilter(), "function evaluate(doc) {"
				+ "	load(\"nashorn:mozilla_compat.js\");importPackage(javax.xml.xpath);	var xpath = XPathFactory.newInstance().newXPath();if( (xpath.evaluate(\"//qocindicator[@id='0' and qoccriterion[@id='[0.1]']/qocmetricdefinition[@id='0.1']]\", doc, XPathConstants.NODESET).length == 0) && (xpath.evaluate(\"//qocindicator[@id='1' and qoccriterion[@id='[1.1]']/qocmetricdefinition[@id='1.1']]\", doc, XPathConstants.NODESET).length == 0)) {return false}return true;}");

		try {
			/**
			 * Test filter with a context report without QoC
			 */
			contextReport = facade.createContextReport("report 0");
			initContextObservation();
			list_qoCIndicator.clear();
			facade.addQoCIndicators(contextObservation, list_qoCIndicator);
			facade.addContextObservation(contextReport, contextObservation);
			Assert.assertFalse(Util.evaluate(routingFilter.getRoutingFilter(), facade.serialiseToXML(contextReport)));

			/**
			 * Test filter with a context report with one QoC indicator
			 * (indicator zero)
			 */
			contextReport = facade.createContextReport("report 1");
			initContextObservation();
			list_qoCIndicator.clear();
			list_qoCIndicator.add(qoCMetaDataIndicatorZero.qoCIndicator());
			facade.addQoCIndicators(contextObservation, list_qoCIndicator);
			facade.addContextObservation(contextReport, contextObservation);
			Assert.assertTrue(Util.evaluate(routingFilter.getRoutingFilter(), facade.serialiseToXML(contextReport)));

			/**
			 * Test filter with a context report with one QoC indicator
			 * (indicator one)
			 */
			contextReport = facade.createContextReport("report 2");
			initContextObservation();
			list_qoCIndicator.clear();
			list_qoCIndicator.add(qoCMetaDataIndicatorOne.qoCIndicator());
			facade.addQoCIndicators(contextObservation, list_qoCIndicator);
			facade.addContextObservation(contextReport, contextObservation);
			Assert.assertTrue(Util.evaluate(routingFilter.getRoutingFilter(), facade.serialiseToXML(contextReport)));

			/**
			 * Test filter with a context report with both QoC indicator
			 * (indicator zero and one)
			 */
			contextReport = facade.createContextReport("report 3");
			initContextObservation();
			list_qoCIndicator.clear();
			list_qoCIndicator.add(qoCMetaDataIndicatorOne.qoCIndicator());
			list_qoCIndicator.add(qoCMetaDataIndicatorZero.qoCIndicator());
			facade.addQoCIndicators(contextObservation, list_qoCIndicator);
			facade.addContextObservation(contextReport, contextObservation);
			Assert.assertTrue(Util.evaluate(routingFilter.getRoutingFilter(), facade.serialiseToXML(contextReport)));
		} catch (final ScriptException e) {
			e.printStackTrace();
		} catch (final ParserConfigurationException e) {
			e.printStackTrace();
		} catch (final SAXException e) {
			e.printStackTrace();
		} catch (final IOException e) {
			e.printStackTrace();
		} catch (final UndeclaredThrowableException e) {
			e.printStackTrace();
		}
	}

	@Test
	public final void testGenerateFilterWithOneQoCCriterionConstraint() {

		List<QoCMetaData> list_metaData;
		List<QoCIndicator> list_qoCIndicator;
		ContextReport contextReport;

		list_metaData = new ArrayList<QoCMetaData>();
		list_qoCIndicator = new ArrayList<QoCIndicator>();

		/**
		 * Create a filter with one QoC-value constraint
		 */
		list_metaData.add(qoCMetaDataIndicatorZero);
		qoCFilterGenerator.addQoCCriterionConstraints(list_metaData);

		/**
		 * Test one QoC-criterion constraint with
		 * JavaScriptBeginRoutingFilterGenerator
		 */
		assertEquals(qoCFilterGenerator.generateFilter(),
				"if( (xpath.evaluate(\"//qocindicator[@id='0' and qoccriterion[@id='[0.1]']/qocmetricdefinition[@id='0.1']]\", doc, XPathConstants.NODESET).length == 0)) {return false}");

		/**
		 * Test the production of the entire filter composed of one
		 * QoC-criterion constraint
		 */
		routingFilter.addFilterGenerator(beginFilterGenerator);
		routingFilter.addFilterGenerator(qoCFilterGenerator);
		routingFilter.addFilterGenerator(endFilterGenerator);
		assertEquals(routingFilter.getRoutingFilter(), "function evaluate(doc) {"
				+ "	load(\"nashorn:mozilla_compat.js\");importPackage(javax.xml.xpath);	var xpath = XPathFactory.newInstance().newXPath();if( (xpath.evaluate(\"//qocindicator[@id='0' and qoccriterion[@id='[0.1]']/qocmetricdefinition[@id='0.1']]\", doc, XPathConstants.NODESET).length == 0)) {return false}return true;}");

		try {
			/**
			 * Test filter with a context report without QoC
			 */
			contextReport = facade.createContextReport("report 0");
			initContextObservation();
			list_qoCIndicator.clear();
			facade.addQoCIndicators(contextObservation, list_qoCIndicator);
			facade.addContextObservation(contextReport, contextObservation);
			Assert.assertFalse(Util.evaluate(routingFilter.getRoutingFilter(), facade.serialiseToXML(contextReport)));

			/**
			 * Test filter with a context report with wrong QoC
			 */
			contextReport = facade.createContextReport("report 1");
			initContextObservation();
			list_qoCIndicator.clear();
			list_qoCIndicator.add(qoCMetaDataIndicatorOne.qoCIndicator());
			facade.addQoCIndicators(contextObservation, list_qoCIndicator);
			facade.addContextObservation(contextReport, contextObservation);
			Assert.assertFalse(Util.evaluate(routingFilter.getRoutingFilter(), facade.serialiseToXML(contextReport)));

			/**
			 * Test filter with a context report with good QoC
			 */
			contextReport = facade.createContextReport("report 2");
			initContextObservation();
			list_qoCIndicator.clear();
			list_qoCIndicator.add(qoCMetaDataIndicatorZero.qoCIndicator());
			facade.addQoCIndicators(contextObservation, list_qoCIndicator);
			facade.addContextObservation(contextReport, contextObservation);
			Assert.assertTrue(Util.evaluate(routingFilter.getRoutingFilter(), facade.serialiseToXML(contextReport)));

			/**
			 * Test filter with a context report with good and wrong QoC
			 */
			contextReport = facade.createContextReport("report 3");
			initContextObservation();
			list_qoCIndicator.clear();
			list_qoCIndicator.add(qoCMetaDataIndicatorZero.qoCIndicator());
			list_qoCIndicator.add(qoCMetaDataIndicatorOne.qoCIndicator());
			facade.addQoCIndicators(contextObservation, list_qoCIndicator);
			facade.addContextObservation(contextReport, contextObservation);
			Assert.assertTrue(Util.evaluate(routingFilter.getRoutingFilter(), facade.serialiseToXML(contextReport)));
		} catch (final ScriptException e) {
			e.printStackTrace();
		} catch (final ParserConfigurationException e) {
			e.printStackTrace();
		} catch (final SAXException e) {
			e.printStackTrace();
		} catch (final IOException e) {
			e.printStackTrace();
		} catch (final UndeclaredThrowableException e) {
			e.printStackTrace();
		}
	}

	@Test
	public final void testGenerateFilterWithOneQoCValueConstraint() {

		Map<QoCMetaData, EComparator> list_metaData;
		List<QoCIndicator> list_qoCIndicator;
		ContextReport contextReport;

		list_metaData = new HashMap<QoCMetaData, EComparator>();
		list_qoCIndicator = new ArrayList<QoCIndicator>();

		/**
		 * Create a filter with one QoC-value constraint
		 */
		list_metaData.put(qoCMetaDataIndicatorZero, EComparator.EQUALS);
		qoCFilterGenerator.addQoCValueConstraints(list_metaData);

		/**
		 * Test one QoC-value constraint with
		 * JavaScriptBeginRoutingFilterGenerator
		 */
		assertEquals(qoCFilterGenerator.generateFilter(),
				"if( (xpath.evaluate(\"//qocindicator[@id='0' and qoccriterion[@id='[0.1]']/qocmetricdefinition[@id='0.1'] and qocmetricvalue[@value='0.0']]\", doc, XPathConstants.NODESET).length == 0)) {return false}");

		/**
		 * Test the production of the entire filter composed of one
		 * QoC-criterion constraint
		 */
		routingFilter.addFilterGenerator(beginFilterGenerator);
		routingFilter.addFilterGenerator(qoCFilterGenerator);
		routingFilter.addFilterGenerator(endFilterGenerator);
		assertEquals(routingFilter.getRoutingFilter(), "function evaluate(doc) {"
				+ "	load(\"nashorn:mozilla_compat.js\");importPackage(javax.xml.xpath);	var xpath = XPathFactory.newInstance().newXPath();if( (xpath.evaluate(\"//qocindicator[@id='0' and qoccriterion[@id='[0.1]']/qocmetricdefinition[@id='0.1'] and qocmetricvalue[@value='0.0']]\", doc, XPathConstants.NODESET).length == 0)) {return false}return true;}");

		try {
			/**
			 * Test filter with a context report without QoC
			 */
			contextReport = facade.createContextReport("report 0");
			initContextObservation();
			list_qoCIndicator.clear();
			facade.addQoCIndicators(contextObservation, list_qoCIndicator);
			facade.addContextObservation(contextReport, contextObservation);
			Assert.assertFalse(Util.evaluate(routingFilter.getRoutingFilter(), facade.serialiseToXML(contextReport)));

			/**
			 * Test filter with a context report with wrong QoC indicator
			 */
			contextReport = facade.createContextReport("report 1");
			initContextObservation();
			list_qoCIndicator.clear();
			list_qoCIndicator.add(qoCMetaDataIndicatorOne.qoCIndicator());
			facade.addQoCIndicators(contextObservation, list_qoCIndicator);
			facade.addContextObservation(contextReport, contextObservation);
			Assert.assertFalse(Util.evaluate(routingFilter.getRoutingFilter(), facade.serialiseToXML(contextReport)));

			/**
			 * Test filter with a context report with good QoC indicator and
			 * wrong value
			 */
			contextReport = facade.createContextReport("report 2");
			initContextObservation();
			list_qoCIndicator.clear();
			qoCMetaDataIndicatorZero = new QoCMetaData(TestCriterionZeroFactory.instance()
					.newQoCIndicator("" + counter_qoCMetricValueId++, DEFAULT_QOCVALUE + 42));
			list_qoCIndicator.add(qoCMetaDataIndicatorZero.qoCIndicator());
			facade.addQoCIndicators(contextObservation, list_qoCIndicator);
			facade.addContextObservation(contextReport, contextObservation);
			Assert.assertFalse(Util.evaluate(routingFilter.getRoutingFilter(), facade.serialiseToXML(contextReport)));

			/**
			 * Test filter with a context report with good QoC indicator and
			 * value
			 */
			contextReport = facade.createContextReport("report 3");
			initContextObservation();
			list_qoCIndicator.clear();
			qoCMetaDataIndicatorZero = new QoCMetaData(TestCriterionZeroFactory.instance()
					.newQoCIndicator("" + counter_qoCMetricValueId++, DEFAULT_QOCVALUE));
			list_qoCIndicator.add(qoCMetaDataIndicatorZero.qoCIndicator());
			facade.addQoCIndicators(contextObservation, list_qoCIndicator);
			facade.addContextObservation(contextReport, contextObservation);
			Assert.assertTrue(Util.evaluate(routingFilter.getRoutingFilter(), facade.serialiseToXML(contextReport)));

			/**
			 * Test filter with a context report with good QoC indicator and
			 * wrong QoC indicator
			 */
			contextReport = facade.createContextReport("report 4");
			initContextObservation();
			list_qoCIndicator.clear();
			list_qoCIndicator.add(qoCMetaDataIndicatorZero.qoCIndicator());
			qoCMetaDataIndicatorOne = new QoCMetaData(TestCriterionOneFactory.instance()
					.newQoCIndicator("" + counter_qoCMetricValueId++, DEFAULT_QOCVALUE + 42));
			list_qoCIndicator.add(qoCMetaDataIndicatorOne.qoCIndicator());
			facade.addQoCIndicators(contextObservation, list_qoCIndicator);
			facade.addContextObservation(contextReport, contextObservation);
			Assert.assertTrue(Util.evaluate(routingFilter.getRoutingFilter(), facade.serialiseToXML(contextReport)));

		} catch (final ScriptException e) {
			e.printStackTrace();
		} catch (final ParserConfigurationException e) {
			e.printStackTrace();
		} catch (final SAXException e) {
			e.printStackTrace();
		} catch (final IOException e) {
			e.printStackTrace();
		} catch (final UndeclaredThrowableException e) {
			e.printStackTrace();
		}

	}
}
