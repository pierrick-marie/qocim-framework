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
package qocim.datamodel.precision.simple;

import java.util.List;
import java.util.concurrent.Future;

import qocim.datamodel.precision.PrecisionIndicator;
import qocim.datamodel.precision.simple.criteria.PrecisionCriterion;
import qocim.information.QInformation;
import qocim.model.*;
import qocim.model.IQoCEvaluator;
import qocim.model.IQoCFacade;
import qocim.model.QoCMetricDefinition;
import qocim.model.QoCMetricValue;
import qocim.model.tools.IQoCFacade;

public class SimplePrecisionFacade implements IQoCFacade {

    private final PrecisionIndicator indicator;
    private final PrecisionCriterion criterion;

    public SimplePrecisionFacade() {
        indicator = new PrecisionIndicator();
        criterion = new PrecisionCriterion();
        indicator.addCriterion(criterion);
    }

    @Override
    public QoCIndicator qocIndicator() {
        return indicator;
    }

    @Override
    public QoCCriterion qocCritrion() {
        return criterion;
    }

    @Override
    public List<QoCValue<?>> qocValues() {
        return indicator.qocValues();
    }

    @Override
    public QoCDefinition qocDefinition() {
        return
    }

    @Override
    public QoCDescription qocDescription() {
        return null;
    }

    @Override
    public Future<QoCValue<?>> qualify(QInformation<?> qInformation) {
        return null;
    }
}
