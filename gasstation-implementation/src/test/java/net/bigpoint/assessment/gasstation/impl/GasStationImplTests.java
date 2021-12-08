package net.bigpoint.assessment.gasstation.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Collection;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.boot.test.context.SpringBootTest;

import net.bigpoint.assessment.gasstation.GasPump;
import net.bigpoint.assessment.gasstation.GasStation;
import net.bigpoint.assessment.gasstation.GasType;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class GasStationImplTests {
	
	private GasStation gasStation;
	
	@BeforeAll
	public void initSetUp() {
		gasStation = new GasStationImpl();
	}
	
	@Test
	public void addGasPump() {
		gasStation = new GasStationImpl();
		GasPump pump = new GasPump(GasType.REGULAR, 10);
		gasStation.addGasPump(pump);
		Collection<GasPump> p = gasStation.getGasPumps();
		assertEquals(1, p.size());
	}
	
	@Test
	public void getGasPumps() {
		fail();
	}
	
	@Test
	public void buyGas() {
		fail();
	}
	
	@Test
	public void getRevenue() {
		fail();
	}
	
	@Test
	public void getNumberOfCancellationsNoGas() {
		fail();
	}
	
	@Test
	public void getNumberOfCancellationsTooExpensive() {
		fail();
	}
	
	@Test
	public void getPrice() {
		fail();
	}

}
