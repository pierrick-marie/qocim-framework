package qocim.information;

import qocim.model.QoCIndicator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InformationImpl<T> implements QInformation<T> {

    private T data;
    private Date creationDate;
    private List<QoCIndicator> indicators;
    private String name;

    public InformationImpl() {
        creationDate = new Date();
        data = null;
        name = "";
        indicators = new ArrayList<>();
    }

    public InformationImpl(final String name, final T data) {
        creationDate = new Date();
        this.data = data;
        this.name = name;
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
    public List<QoCIndicator> indicators() {
        return indicators;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public void setData(T data) {
        this.data = data;
    }

    @Override
    public void setIndicators(List<QoCIndicator> indicators) {
        this.indicators = indicators;
    }

    @Override
    public void setName(String name) {
        this.name = name;

    }

    @Override
    public String toString() {
        return name.toString();
    }
}
