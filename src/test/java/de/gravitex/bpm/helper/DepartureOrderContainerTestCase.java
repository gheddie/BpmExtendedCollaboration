package de.gravitex.bpm.helper;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.gravitex.bpm.helper.entity.traindepartmentnew.DepartureOrder;
import de.gravitex.bpm.helper.logic.traindepartmentnew.DepartureOrderContainer;

public class DepartureOrderContainerTestCase {

	@Test
	public void testDepartureOrderContainer() {
		DepartureOrderContainer departureOrderContainer = DepartureOrderContainer.fromBusinessKey("123");
		departureOrderContainer.accept(makeDepartureOrder()).accept(makeDepartureOrder()).accept(makeDepartureOrder());
		assertEquals(2, departureOrderContainer.getHistoryLevel());
	}

	private DepartureOrder makeDepartureOrder() {
		return DepartureOrder.fromWaggons(null, null, null);
	}
}