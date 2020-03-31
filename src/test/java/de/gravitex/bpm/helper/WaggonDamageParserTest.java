package de.gravitex.bpm.helper;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import de.gravitex.bpm.helper.entity.traindepartmentnew.WaggonDamage;
import de.gravitex.bpm.helper.entity.traindepartmentnew.parser.WaggonDamageParser;

public class WaggonDamageParserTest {

	@Test
	public void testWaggonDamageParser() {
		
		List<WaggonDamage> result = null;
		
		WaggonDamageParser waggonDamageParser = new WaggonDamageParser();
		
		result = waggonDamageParser.parse(null, "D1=C1,N1#D2=C2");
		assertEquals(2, result.size());
		
		result = waggonDamageParser.parse(null, "");
		assertEquals(0, result.size());
		
		result = waggonDamageParser.parse(null, null);
		assertEquals(0, result.size());
		
		result = waggonDamageParser.parse(null, "D2=C1");
		assertEquals(1, result.size());
	}
}