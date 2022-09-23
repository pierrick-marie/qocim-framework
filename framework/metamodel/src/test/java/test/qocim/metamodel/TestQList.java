package test.qocim.metamodel;

import org.junit.Before;
import org.junit.Test;
import qocim.metamodel.QAttribut;
import qocim.metamodel.QClass;
import qocim.metamodel.QList;
import test.qocim.metamodel.log.Messages;

import static org.junit.Assert.*;

public class TestQList {

	private final String CONTAINER_NAME = "test container";
	private final String ATTRIBUT_NAME = "test attribut";
	private final String QLIST_NAME = "test list";

	private QList testList;
	private QClass container;

	@Before
	public final void before() {
		System.out.println(Messages.BEFORE);
		container = new QClass(CONTAINER_NAME);
		testList = new QList(QLIST_NAME, container);

		addAttributs(testList);
	}

	private void addAttributs(QList list) {
		for (int i = 0; i < 5; i++) {
			list.addElement(new QAttribut<>(ATTRIBUT_NAME + " " + i, container, i));
		}
	}

	@Test
	public final void testAdd() {
		assertEquals(5, testList.nbElements());
		for (int i = 0; i < 5; i++) {
			assertTrue(null != testList.getElement(ATTRIBUT_NAME + " " + i));
		}
	}

	@Test
	public final void testToString() {
		assertEquals(QLIST_NAME, testList.name);
		assertEquals(QLIST_NAME, testList.toString());
	}

	@Test
	public final void testContainer() {
		assertTrue(container == testList.container);
	}

	@Test
	public final void testEquals() {
		assertTrue(testList.equals(testList));
		QList testList2 = new QList(QLIST_NAME, container);
		// Same name but different element in the lists
		assertFalse(testList2.equals(testList));
		addAttributs(testList2);
		// Same name and same element in the lists
		assertTrue(testList2.equals(testList));
		// Same name but different element in the lists
		testList2.removeElement(ATTRIBUT_NAME + " " + 0);
		assertFalse(testList2.equals(testList));
		// Different name and different element in the lists
		QList testList3 = new QList(QLIST_NAME + " FALSE ", container);
		assertFalse(testList3.equals(testList));

		// TODO test with elements in the list
	}

	@Test
	public final void testGet() {
		assertEquals(
			new QAttribut<>(ATTRIBUT_NAME + " " + 0, container, 0), testList.getElement(ATTRIBUT_NAME + " " + 0)
		);
		assertTrue(null == testList.getElement(ATTRIBUT_NAME + " NULL"));
	}

	@Test
	public final void testRemove() {
		testList.removeElement(ATTRIBUT_NAME + " " + 0);
		assertEquals(4, testList.nbElements());
	}
}
