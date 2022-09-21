package qocim.model;

import qocim.information.QInformation;
import qocim.metamodel.QAttribut;
import qocim.metamodel.QClass;
import qocim.metamodel.QList;

public class QoCIndicator extends QClass {

    private QAttribut<String> name;
    private QAttribut<Integer> id;

    private QList<QoCCriterion> criteria;
    private QList<QoCMetricValue> metricValues;

    private QInformation information;

    public QoCIndicator() {

        super();

        setName(new QAttribut<String>("name", this, ""));
        setId(new QAttribut<Integer>("id", this, 0));

        setCriteria(new QList<QoCCriterion>("criteria"));
        setMetricValues(new QList<QoCMetricValue>("metric-values"));
    }

    protected QoCIndicator(final QAttribut<Integer> id, final QList<QoCCriterion> criterion, final QList<QoCMetricValue> metricValues) {

        super();

        setId(id);
        setCriteria(criterion);
        setMetricValues(metricValues);
    }

    public void addCriterion(final QoCCriterion criterion) {
        criteria.add(criterion);
    }


    public void addMetricValue(final QoCMetricValue metricValue) {
        metricValues.add(metricValue);
    }

    @Override
    public boolean equals(final Object comparable) {

        QoCIndicator indicator;

        if (comparable instanceof QoCIndicator) {
            indicator = (QoCIndicator) comparable;
            return indicator.id.equals(id);
        }

        return false;
    }

    public QList<QoCCriterion> getCriteria() {
        return criteria;
    }

    public QAttribut<Integer> getId() {
        return id;
    }

    public Integer id() {
        return (Integer) id.getObject();
    }

    public QInformation getInformation() {
        return information;
    }

    public Object information() {
        return information.getData();
    }

    public QList<QoCMetricValue> getMetricValues() {
        return metricValues;
    }

    public QAttribut<String> getName() {
        return name;
    }

    public String name() {
        return (String) name.getObject();
    }

    public void setCriteria(final QList<QoCCriterion> criteria) {
        this.criteria = criteria;
    }

    public void setId(final Integer id) {
        this.id.setObject(id);
    }

    public void setId(final QAttribut<Integer> id) {
        this.id = id;
    }

    public void setInformation(QInformation information) {
        this.information = information;
    }

    public void setMetricValues(final QList<QoCMetricValue> metricValues) {
        this.metricValues = metricValues;
    }

    public void setName(final QAttribut<String> name) {
        this.name = name;
    }

    public void setName(final String name) {
        this.name.setObject(name);
    }

    @Override
    public String toString() {
        return "QoC indicator: " + getId().getObject().toString();
    }
}
