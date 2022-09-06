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

import java.io.IOException;
import java.io.StringReader;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import mudebs.common.filters.JavaScriptRoutingFilter;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class Util {
    static boolean evaluate(final String filterToTest, final String serialised) throws ScriptException, ParserConfigurationException, SAXException, IOException {
	ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
	ScriptEngine engine = scriptEngineManager.getEngineByName("JavaScript");
	engine.eval(filterToTest);
	Invocable inv = (Invocable) engine;
	JavaScriptRoutingFilter mf = inv.getInterface(JavaScriptRoutingFilter.class);
	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	DocumentBuilder builder = factory.newDocumentBuilder();
	InputSource is = new InputSource();
	is.setCharacterStream(new StringReader(serialised));
	Document doc = builder.parse(is);

	return mf.evaluate(doc);
    }
}
