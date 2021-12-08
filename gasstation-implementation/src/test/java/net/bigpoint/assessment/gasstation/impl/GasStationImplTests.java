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
import net.bigpoint.assessment.gasstation.exceptions.GasTooExpensiveException;
import net.bigpoint.assessment.gasstation.exceptions.NotEnoughGasException;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class GasStationImplTests {
	
	private GasStation gasStation;
	
	@BeforeAll
	public void initSetUp() {
		gasStation = new GasStationImpl();
		GasPump pump = new GasPump(GasType.DIESEL, 10);
		gasStation.setPrice(GasType.DIESEL, 2.0);
		gasStation.addGasPump(pump);
	}
	
	@Test
	public void addGasPump() {
		GasPump pump = new GasPump(GasType.REGULAR, 10);
		gasStation.addGasPump(pump);
		Collection<GasPump> p = gasStation.getGasPumps();
		assertEquals(2, p.size());
	}
	
	@Test
	public void getGasPumps() {
		Collection<GasPump> p = gasStation.getGasPumps();
		assertEquals(1, p.size());
	}
	
	@Test
	public void buyGas() {
		
		try {
			Double price = gasStation.buyGas(GasType.DIESEL, 2, 5);
			assertEquals(4, price);
		} catch (NotEnoughGasException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GasTooExpensiveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
