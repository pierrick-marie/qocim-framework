package qocim.information;

import qocim.model.QoCIndicator;
import qocim.metamodel.QClass;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InformationImpl<T> extends QClass implements QInformation<T>  {

    private T data;
    private Date creationDate;
    private List<QoCIndicator> indicators;

    public InformationImpl(final String name, final T data) {
        super(name);
        creationDate = new Date();
        this.data = data;
        indicators = new ArrayList<>();
    }

    @Override
    public int compareTo(final QInformation<T> information) {

        if (!information.data().equals(data)) { return -1; }
        if (!information.name().equals(name)) { return -2; }
        if (!information.creationDate().equals(creationDate)) { return -3; }

        // information are equal
        return 0;
    }

    @Override
    public Date creationDate() {
        return creationDate;
    }

    @Override
    public T data() {
        return data;
    }

    @Override
    public String name() {
        return super.toString();
    }

    @Override
    public List<QoCIndicator> indicators() {
        return indicators;
    }

    @Override
    public void setIndicators(List<QoCIndicator> indicators) {
        this.indicators = indicators;
    }

    @Override
    public String toString() {
        return name.toString();
    }
}
