/**
 * This file is part of the QoCIM middleware.
 * <p>
 * Copyright (C) 2014 IRIT, Télécom SudParis
 * <p>
 * The QoCIM software is free software: you can redistribute it and/or modify
 * It under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * The QoCIM software platform is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * <p>
 * See the GNU Lesser General Public License
 * for more details: http://www.gnu.org/licenses
 * <p>
 * Initial developer(s): Pierrick MARIE
 * Contributor(s):
 */
package qocim.datamodel;

import qocim.datamodel.utils.ConstraintChecker;
import qocim.datamodel.utils.ConstraintCheckerException;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;

/**
 * Description textually describes a QoC criterion.
 *
 * @see qocim.datamodel.QoCMetricDefinition
 *
 * @author Pierrick MARIE
 *
 */
public class Description extends QoCIM {

    // # # # # # PRIVATE VARIABLES # # # # #

    /**
     * A set of strings to describe the QoC criterion with few keywords.
     */
    public final HashSet<String> list_keyword;
    /**
     * A text to describe the QoC criterion.
     */
    String informalDescription;

    // # # # # # CONSTRUCTORS # # # # #

    public Description() {
        // - - - - - INITIALIZE THE VARIABLES - - - - -
        super();
        list_keyword = new LinkedHashSet<String>();
        informalDescription = "";
    }

    public Description(final Description _description) {
        // - - - - - INITIALIZE THE VARIABLES - - - - -
        super(_description);
        list_keyword = new LinkedHashSet<String>();
        for (final String keyword : _description.list_keyword) {
            list_keyword.add(keyword);
        }
        informalDescription = _description.informalDescription;
    }

    // # # # # # PUBLIC METHODS # # # # #

    public Description addKeyword(final String _keyword) {
        // - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
        try {
            ConstraintChecker.notNull(_keyword, "Description.addKeyword(String): the argument _keyword is null");
        } catch (final ConstraintCheckerException e) {
            return this;
        }
        // - - - - - CORE OF THE METHOD - - - - -
        list_keyword.add(_keyword);
        // - - - - - RETURN STATEMENT - - - - -
        return this;
    }

    @Override
    public boolean equals(final Object _comparable) {
        // - - - - - INITIALIZE THE VARIABLES - - - - -
        Description description;
        // - - - - - CORE OF THE METHOD - - - - -
        if (_comparable != null && _comparable instanceof Description) {

            description = (Description) _comparable;

            return informalDescription.equals(description.informalDescription);
        }
        // - - - - - RETURN STATEMENT - - - - -
        return false;
    }

    public String informalDescription() {
        return informalDescription;
    }

    @Override
    public int hashCode() {
        // - - - - - RETURN STATEMENT - - - - -
        return informalDescription.hashCode();
    }

    public Description informalDescription(final String _informalDescription) {
        informalDescription = _informalDescription;
        return this;
    }

    @Override
    public String toString() {
        // - - - - - INITIALIZE THE VARIABLES - - - - -
        final StringBuffer ret_stringBuffer = new StringBuffer();
        final Iterator<String> iterator_keyWord = list_keyword.iterator();
        String next;
        // - - - - - CORE OF THE METHOD - - - - -
        ret_stringBuffer.append(super.toString());
        ret_stringBuffer.append("Keywords: List<String>= [");
        while (iterator_keyWord.hasNext()) {
            next = iterator_keyWord.next();
            if (iterator_keyWord.hasNext()) {
                ret_stringBuffer.append(next + ", ");
            } else {
                ret_stringBuffer.append(next + " ]\n");
            }
        }
        ret_stringBuffer.append("InformalDescription: String= \"" + informalDescription + "\"\n");
        // - - - - - RETURN STATEMENT - - - - -
        return ret_stringBuffer.toString();
    }
}
