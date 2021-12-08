package net.bigpoint.assessment.gasstation.impl;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import net.bigpoint.assessment.gasstation.GasStation;

public class GasStationImplTest {
	
	private GasStation gasStation;
	
	@BeforeAll
	public void initSetUp() {
		gasStation = new GasStationImpl();
	}
	
	@Test
	public void addGasPump() {
		fail();
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
