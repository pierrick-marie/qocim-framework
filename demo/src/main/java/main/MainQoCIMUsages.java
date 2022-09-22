package main;

import qocim.datamodel.freshness.FreshnessFacade;
import qocim.datamodel.freshness.definitions.RandomDefinition;
import qocim.datamodel.freshness.evaluators.RandomEvaluator;
import qocim.information.InformationImpl;
import qocim.information.QInformation;
import qocim.model.QoCMetricValue;
import qocim.utils.logs.QoCIMLogger;

public class MainQoCIMUsages {

	private final static Integer TIME_TO_SLEEP = 50;

	public static void main(String[] args) {

		QoCIMLogger.loadDefaultConfig();

		System.out.println(" ================== Start QoCIM demo ================== ");
		new MainQoCIMUsages();
		System.out.println(" ================== End QoCIM demo ================== ");
	}

	public MainQoCIMUsages() {

		// Synchronous freshness evaluation of an information
		syncEvaluation();

		// Asynchronous freshness evaluation of an information
//		asyncEvaluation();

		// Synchronous random freshness evaluation of an information
//		randomSyncEvaluation();

		// Asynchronous random freshness evaluation of an information
//		randomAsyncEvaluation();

		// Synchronous random freshness evaluation of a string
//		randomSyncStringEvaluation();
	}

	public void asyncEvaluation() {
		// A new freshness facade used to compute QoC value with dates
		final FreshnessFacade facade = initNewSimpleFacade();
		// A new information (Information.class)
		final QInformation information = initNewInformation("Temperature = 40°C");

		// Waiting TIME_TO_SLEEP milliseconds to get a "real" QoC value
		try {
			Thread.sleep(TIME_TO_SLEEP);
		} catch (final InterruptedException exception) {
			QoCIMLogger.logger.warning(exception.toString());
		}
		// Notify the facade that a new information is coming
		// @see QoCListener.fireNewQoCMetricValue
		// @see qocim.datamodel.freshness.evaluators.SimpleEvaluator.computeQoC
		facade.fireNewInformation(information);
	}

	/**
	 * It created a new Freshness facade and setup the listener that will receive
	 * the asynchronous QoC evaluations. The facade uses the RandomDefinition of
	 * freshness. This definition computes the freshness with a random value (use
	 * this definition just for the demo).
	 *
	 * @return The freshness facade.
	 */
	private FreshnessFacade initFreshnessFacade() {

		FreshnessFacade facade = null;
		try {
			// Change the default definition to RandomDefinition
			facade = new FreshnessFacade(RandomDefinition.class, new RandomEvaluator());
		} catch (final Exception exception) {
			QoCIMLogger.logger.warning(exception.toString());
			System.exit(1);
		}
		// Setup the QoC listener
		facade.addQoCListener(new QoCListener());

		return facade;
	}

	/**
	 * It creates a new Information
	 *
	 * @param data
	 *            The value of the information.
	 * @return The new information.
	 */
	private QInformation initNewInformation(final Object data) {

		final QInformation information = new InformationImpl();
		information.setData(data);

		return information;
	}

	/**
	 * It created a new Freshness facade and setup the listener that will receive
	 * the asynchronous QoC evaluations. The facade uses the SimpleDefinition of
	 * freshness. This definition computes the freshness with current time.
	 *
	 * @return The freshness facade.
	 */
	private FreshnessFacade initNewSimpleFacade() {

		final FreshnessFacade facade = new FreshnessFacade();
		// Setup the QoC listener
		facade.addQoCListener(new QoCListener());

		return facade;
	}

	/**
	 * It displays values of a qualified information and its associated QoC value.
	 *
	 * @param information
	 *            The qualified information.
	 * @param qocValue
	 *            The associated QoC value.
	 */
	private void log(final Object information, final QoCMetricValue qocValue) {

		final StringBuffer buffer = new StringBuffer();

		buffer.append("Sync QoC evaluation\n");
		buffer.append(" * Information type: " + information.getClass().getSimpleName() + ".class - Information value: "
				+ information.toString() + "\n");
		buffer.append(" * " + qocValue);

		QoCIMLogger.logger.info(buffer.toString());
	}

	public void randomAsyncEvaluation() {
		// A new freshness facade used to compute QoC value with random numbers
		final FreshnessFacade facade = initFreshnessFacade();
		// A new information (Information.class)
		final QInformation information = initNewInformation("Temperature = 40°C");

		// Change the default evaluator to use random numbers
		if (facade.setEvaluator(new RandomEvaluator())) {
			// Notify the facade that a new information is coming
			// @see QoCListener.fireNewQoCMetricValue(QoCMetricValue, Object)
			// @see qocim.datamodel.freshness.evaluators.RandomEvaluator.computeQoC
			facade.fireNewInformation(information);
		} else {
			QoCIMLogger.logger.warning("Failed to change default evaluator");
		}
	}

	public void randomSyncEvaluation() {
		// A new freshness facade used to compute QoC value with random numbers
		final FreshnessFacade facade = initFreshnessFacade();
		// A new information (Information.class)
		final QInformation information = initNewInformation("Temperature = 40°C");

		// Change the default evaluator to use random numbers
		if (facade.setEvaluator(new RandomEvaluator())) {
			// Displays qualified information and its associated QoC value
			// @see qocim.datamodel.freshness.evaluators.RandomEvaluator.computeQoC
			log(information, facade.qualifyInformation(information));
		} else {
			QoCIMLogger.logger.warning("Failed to change default evaluator");
		}
	}

	public void randomSyncStringEvaluation() {
		// A new freshness facade used to compute QoC value with random numbers
		final FreshnessFacade facade = initFreshnessFacade();
		// A new information (Information.class)
		final String data = "Temperature = 40°C";
		final QInformation information = new InformationImpl();
		information.setData(data);

		// Change the default evaluator to use random numbers
		if (facade.setEvaluator(new RandomEvaluator())) {
			// Displays qualified information and its associated QoC value
			// @see qocim.datamodel.freshness.evaluators.RandomEvaluator.computeQoC
			log(information, facade.qualifyInformation(information));
		} else {
			QoCIMLogger.logger.warning("Failed to change default evaluator");
		}
	}

	public void syncEvaluation() {
		// A new freshness facade used to compute QoC value with dates
		final FreshnessFacade facade = initNewSimpleFacade();
		// A new information (Information.class)
		final QInformation information = initNewInformation("Temperature = 40°C");

		// Waiting TIME_TO_SLEEP milliseconds to get a "real" QoC value
		try {
			Thread.sleep(TIME_TO_SLEEP);
		} catch (final InterruptedException exception) {
			QoCIMLogger.logger.warning(exception.toString());
		}

		// Displays qualified information and its associated QoC value
		// @see qocim.datamodel.freshness.evaluators.SimpleEvaluator.computeQoC
		log(information, facade.qualifyInformation(information));
	}
}
