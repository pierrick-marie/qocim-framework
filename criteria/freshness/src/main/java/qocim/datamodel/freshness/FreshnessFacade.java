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
package qocim.datamodel.freshness;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.reflections.Reflections;

import qocim.datamodel.freshness.definitions.SimpleDefinition;
import qocim.datamodel.freshness.values.FreshnessMetricValue;
import qocim.datamodel.freshness.values.MaxFreshnessMetricValue;
import qocim.datamodel.freshness.values.MinFreshnessMetricValue;
import qocim.information.QInformation;
import qocim.model.IQoCEvaluator;
import qocim.model.IQoCFacade;
import qocim.model.QoCDescription;
import qocim.model.QoCMetricDefinition;
import qocim.model.QoCMetricValue;
import qocim.utils.logs.QoCIMLogger;

public class FreshnessFacade implements IQoCFacade {

    private final Integer NB_THREADS = 10;

    private FreshnessQoCIndicator indicator;
    private FreshnessQoCCriterion criterion;
    private QoCMetricDefinition definition;
    private QoCDescription description;

    private IQoCEvaluator evaluator;
    private Integer metricValueId = 0;
    private String listenerMethodName = "fireNewQoCMetricValue";
    final ExecutorService executor;

    private final Class<? extends QoCMetricDefinition> defaultDefinition;
    private final List<Object> qocListeners = new LinkedList<>();

    public FreshnessFacade() {
        /*
         * Default definition.
         */
        defaultDefinition = SimpleDefinition.class;
        executor = Executors.newFixedThreadPool(NB_THREADS);
        evaluator = null;
        try {
            setupClasses();
        } catch (InstantiationException | IllegalAccessException exception) {
            QoCIMLogger.logger.info(exception.getMessage());
        }
    }

    public FreshnessFacade(final Class<? extends QoCMetricDefinition> defaultDefinition,
                           final IQoCEvaluator evaluator) {
        /*
         * An alternative definition.
         */
        this.defaultDefinition = defaultDefinition;
        executor = Executors.newFixedThreadPool(NB_THREADS);
        this.evaluator = evaluator;
        try {
            setupClasses();
        } catch (InstantiationException | IllegalAccessException exception) {
            QoCIMLogger.logger.info(exception.getMessage());
        }
    }

    @Override
    public void addQoCListener(final Object qocListener) {
        qocListeners.add(qocListener);
    }

    /**
     * This method compares the supportedType of an evaluator with an expected one.
     *
     * @param evaluator
     *            The evaluator to check.
     * @param searchedType
     *            The expected type.
     * @return True if the evaluator supports the expected type.
     */
    private Boolean evaluatorSupportsType(final IQoCEvaluator evaluator, final Class<?> searchedType) {

        if (null == evaluator) {
            return false;
        }

        for (final Class<?> supportedType : evaluator.supportedInformationType()) {
            if (supportedType.isAssignableFrom(searchedType)) {
                return true;
            }
        }

        return false;
    }

    /**
     * This method is used to find the most appropriate engine following the
     * expected QoC definition and the type of the information to qualify.
     *
     * @param informationType
     *            The type of information to qualify;
     * @return the most appropriate evaluator found among those available;
     * @throws Exception
     *             If no evaluator have been found;
     */
    private IQoCEvaluator findEvaluator(final Class<?> informationType) {

        // Compare supported type of the the default engine with the information
        // type.
        if (evaluatorSupportsType(evaluator, informationType)) {
            return evaluator;
        }

        /*
         * The evaluator to return.
         */
        IQoCEvaluator myEvaluator = null;
        /*
         * A reflection to search into this package.
         */
        final Reflections reflections = new Reflections(this.getClass().getPackage().getName() + ".evaluators");
        /*
         * The list of all evaluators found with the reflection.
         */
        final Set<Class<? extends IQoCEvaluator>> evaluators = reflections.getSubTypesOf(IQoCEvaluator.class);

        try {
            for (final Class<? extends IQoCEvaluator> evaluator : evaluators) {
                myEvaluator = evaluator.newInstance();
                // Check the supported information type of the current evaluator.
                if (evaluatorSupportsType(myEvaluator, informationType)) {
                    return myEvaluator;
                }
            }
        } catch (InstantiationException exception) {
            QoCIMLogger.severe(exception.getMessage());
        } catch (IllegalAccessException exception) {
            QoCIMLogger.severe(exception.getMessage());
        }

        QoCIMLogger.severe("Impossible to find an appropriate evaluator.");
        return null;
    }

    /**
     * The method is used to notify the facade a new information requires a QoC
     * evaluation.
     *
     * @param information
     *            The information to qualify;
     */
    @Override
    public void fireNewInformation(final QInformation information) {
        /*
         * The return value.
         */
        final QoCMetricValue metricValue = qualifyInformation(information);

        // For all registered listeners.
        for (final Object listener : qocListeners) {
            try {
                final Method method = listener.getClass().getMethod(listenerMethodName, QoCMetricValue.class,
                        Object.class);
                // Invoke the method of the listeners.
                method.invoke(listener, metricValue, information);
            } catch (NoSuchMethodException | SecurityException e) {
                e.printStackTrace();
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    public FreshnessQoCCriterion getCriterion() {
        return criterion;
    }

    public QoCMetricDefinition getDefinition() {
        return definition;
    }

    public QoCDescription getDescription() {
        return description;
    }

    public FreshnessQoCIndicator getIndicator() {
        return indicator;
    }

    @Override
    public String getListenerMethodName() {
        return listenerMethodName;
    }

    @Override
    public QoCMetricValue getMaxQoCMetricValue() {
        /*
         * The result of the method.
         */
        final MaxFreshnessMetricValue metricValue = new MaxFreshnessMetricValue();
        metricValue.setMetricDefinition(definition);
        definition.addMetricValues(metricValue);
        return metricValue;
    }

    @Override
    public QoCMetricValue getMinQoCMetricValue() {
        /*
         * The result of the method.
         */
        final MinFreshnessMetricValue metricValue = new MinFreshnessMetricValue();
        metricValue.setMetricDefinition(definition);
        definition.addMetricValues(metricValue);
        return metricValue;
    }

    @Override
    public IQoCEvaluator getQoCEvaluator() {
        return evaluator;
    }

    @Override
    public List<Object> getQoCListeners() {
        return qocListeners;
    }

    private QoCMetricValue invoqueEvaluator(final QInformation information) {

        /*
         * The return value.
         */
        QoCMetricValue value = null;

        try {
            /*
             * Searching the most appropriate evaluator.
             */
            final IQoCEvaluator evaluator = findEvaluator(information.getData().getClass());

            /*
             * The submiting the task to execute.
             */
            evaluator.newInformation(information);
            final Future<QoCMetricValue> future = executor.submit(evaluator);
            value = future.get();
        } catch (final Exception exception) {
            QoCIMLogger.info(exception.getMessage());
        }

        return value;
    }

    @Override
    public QoCMetricValue newQoCMetricValue(final Object value) {
        /*
         * The result of the method.
         */
        FreshnessMetricValue metricValue = new FreshnessMetricValue();
        metricValue.setObjectValue(value);

        try {
            // Init new indicator, criterion, definition
            setupClasses();

            // The metric value that qualifies the information.

            // Check if the value of the metric is correct.
            if (!verifyQoCMetricValue(metricValue)) {
                QoCIMLogger.severe("The QoC metric value has invalid estimation: " + metricValue);
                return null;
            }

            // Setup the metric value
            metricValue.setId(metricValueId++);
            metricValue.setMetricDefinition(definition);
            definition.addMetricValues(metricValue);
        } catch (final Exception exception) {
            QoCIMLogger.error(exception.getMessage());
            metricValue = null;
        }

        return metricValue;
    }

    @Override
    public QoCMetricValue newQoCMetricValue(final QInformation information, final Object value) {
        /*
         * The result of the method.
         */
        FreshnessMetricValue metricValue = new FreshnessMetricValue();
        metricValue.setObjectValue(value);

        try {
            // Init new indicator, criterion, definition
            setupClasses();

            // The metric value that qualifies the information.

            // Check if the value of the metric is correct.
            if (!verifyQoCMetricValue(metricValue)) {
                QoCIMLogger.severe("The QoC metric value has invalid estimation: " + metricValue);
                return null;
            }

            // Setup the metric value
            setupQoCMetricValue(metricValue, information);
        } catch (final Exception exception) {
            QoCIMLogger.error(exception.getMessage());
            metricValue = null;
        }

        return metricValue;
    }

    /**
     * This method is use to qualify an information. The method searches the most
     * appropriate engine and use it to qualify the information.
     *
     * @param information
     *            The information to qualify.
     * @return A QoC metric value containing the QoC evaluation of the information.
     */
    @Override
    public QoCMetricValue qualifyInformation(final QInformation information) {

        /*
         * The result of the method.
         */
        QoCMetricValue metricValue = null;

        try {
            // Init new indicator, criterion, definition
            setupClasses();

            // The metric value that qualifies the information.
            metricValue = invoqueEvaluator(information);

            // Check if the value of the metric is correct.
            if (!verifyQoCMetricValue((FreshnessMetricValue) metricValue)) {
                QoCIMLogger.severe("The QoC metric value has invalid estimation: " + metricValue);
                return null;
            }

            // Setup the metric value
            setupQoCMetricValue(metricValue, information);
        } catch (final Exception exception) {
            QoCIMLogger.logger.info(exception.getMessage());
            metricValue = null;
        }

        return metricValue;
    }

    @Override
    public Boolean setEvaluator(final IQoCEvaluator qocEvaluator) {

        Boolean found = false;

        for (final Class<?> definitionType : qocEvaluator.supportedDefinitionType()) {
            if (definitionType.isAssignableFrom(definition.getClass())) {
                evaluator = qocEvaluator;
                found = true;
            }
        }

        return found;
    }

    @Override
    public void setListenerMethodName(final String listenerMethodName) {
        this.listenerMethodName = listenerMethodName;
    }

    private void setupClasses() throws InstantiationException, IllegalAccessException {

        indicator = new FreshnessQoCIndicator();
        criterion = new FreshnessQoCCriterion();
        definition = defaultDefinition.newInstance();

        // System.out.println("Definition : " + definition);

        indicator.addCriterion(criterion);
        criterion.setIndicator(indicator);
        criterion.addMetricDefinition(definition);
        definition.setCriterion(criterion);
        description = definition.getDescription();
    }

    private QoCMetricValue setupQoCMetricValue(final QoCMetricValue metricValue, final QInformation information) {

        metricValue.setId(metricValueId++);
        metricValue.setIndicator(indicator);
        indicator.addMetricValue(metricValue);
        metricValue.setMetricDefinition(definition);
        definition.addMetricValues(metricValue);

        // Setup information to indicator
        indicator.setInformation(information);
        information.getIndicators().add(indicator);

        return metricValue;
    }

    private Boolean verifyQoCMetricValue(final FreshnessMetricValue metricValue) {

        if (null == metricValue) {
            return false;
        }

        final MinFreshnessMetricValue minValue = new MinFreshnessMetricValue();
        final MaxFreshnessMetricValue maxValue = new MaxFreshnessMetricValue();

        return minValue.compareTo(metricValue) <= 0 && maxValue.compareTo(metricValue) >= 0;
    }
}
