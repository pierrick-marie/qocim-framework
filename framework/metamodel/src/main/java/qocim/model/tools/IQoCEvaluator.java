package qocim.model.tools;

import qocim.information.QInformation;
import qocim.model.QoCValue;

import java.util.concurrent.Callable;

public interface IQoCEvaluator extends Callable<QoCValue<?>>  {

	@Override
	public QoCValue<?> call() throws Exception ;

	public void newInformation(final QInformation<?> information) ;
}
