package qocim.model.tools;

import qocim.information.QInformation;
import qocim.model.*;

public interface IQoCFacade {

	public abstract QoCIndicator newQoCIndicator() ;

	public abstract QInformation<?> qualify(QInformation<?> Information) ;
}
