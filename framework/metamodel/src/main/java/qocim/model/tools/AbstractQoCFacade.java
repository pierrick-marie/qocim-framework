package qocim.model.tools;

import qocim.information.QInformation;
import qocim.model.*;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public abstract class AbstractQoCFacade {

	private final ExecutorService executorService;

	public AbstractQoCFacade() {
		executorService = Executors.newSingleThreadExecutor();
	}

	public abstract QoCIndicator newQoCIndicator() ;

	public abstract String indicatorName();

	private QoCIndicator setupIndicator(QInformation<?> information) {
		QoCIndicator searchedIndicator = information.getIndicator(indicatorName());
		if (null == searchedIndicator) {
			searchedIndicator = newQoCIndicator();
			information.indicators().add(searchedIndicator);
		}

		return searchedIndicator;
	}

	public QInformation<?> qualify(QInformation<?> information, final IQoCEvaluator evaluator) {

		QoCIndicator indicator = setupIndicator(information);

		evaluator.newInformation(information);
		Future<QoCValue<?>> futureQoCValue = executorService.submit(evaluator);

		try {
			QoCValue<?> value = futureQoCValue.get();
			indicator.addQoCValue(value);
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException(e);
		}

		return information;
	}
}
