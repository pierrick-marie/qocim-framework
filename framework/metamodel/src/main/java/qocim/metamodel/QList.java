package qocim.metamodel;

import java.util.LinkedList;

public class QList extends LinkedList<QAttribut<?>> {

    public final String name;
    public final QClass container;

    private QList() {
        name = "";
        container = null;
    }

    public QList(final String name, final QClass container) {

        this.name = name;
        this.container = container;
    }

    @Override
    public boolean equals(final Object comparable) {

        QList compareList;

        if (comparable instanceof QList) {
            compareList = (QList) comparable;

            if (!compareList.name.equals(name)) {
                return false;
            }

            for (final QAttribut<?> object : this) {
                if (!compareList.contains(object)) {
                    return false;
                }
            }

            for (final QAttribut<?> object : compareList) {
                if (!this.contains(object)) {
                    return false;
                }
            }

            return true;
        }

        return false;
    }

    public QAttribut<?> get(final String attributName) {

        for (QAttribut<?> attribut: this) {
            if (attribut.name.equals(attributName)) {
                return attribut;
            }
        }

        return null;
    }

    public String toString() {
        return name;
    }
}
