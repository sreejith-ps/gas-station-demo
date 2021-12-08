package net.bigpoint.assessment.gasstation.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
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
	
	@BeforeEach
	public void initSetUp() {
		gasStation = new GasStationImpl();
		
		GasPump diesel = new GasPump(GasType.DIESEL, 500);
		gasStation.addGasPump(diesel);
		GasPump regular = new GasPump(GasType.REGULAR, 1000);
		gasStation.addGasPump(regular);
		GasPump superPump = new GasPump(GasType.SUPER, 400);
		gasStation.addGasPump(superPump);
		

		gasStation.setPrice(GasType.DIESEL, 2.0);
		gasStation.setPrice(GasType.REGULAR, 1.5);
		gasStation.setPrice(GasType.SUPER, 2.5);
	}
	
	@Test
	public void addGasPump() {
		GasPump pump = new GasPump(GasType.REGULAR, 10);
		gasStation.addGasPump(pump);
		Collection<GasPump> p = gasStation.getGasPumps();
		assertEquals(4, p.size());
	}
	
	@Test
	public void getGasPumps() {
		Collection<GasPump> p = gasStation.getGasPumps();
		assertEquals(3, p.size());
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
	public void cancellationsNotEnoughGas() {
		assertThrows(NotEnoughGasException.class, () -> gasStation.buyGas(GasType.DIESEL, 10000, 3));
	}
	
	@Test
	public void getNumberOfCancellationsNoGas() {
		try {
			gasStation.buyGas(GasType.DIESEL, 1000, 5);
		} catch (NotEnoughGasException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GasTooExpensiveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(1, gasStation.getNumberOfCancellationsNoGas());
	}

	
	@Test
	public void cancellationsTooExpensive() {
		assertThrows(GasTooExpensiveException.class, () -> gasStation.buyGas(GasType.DIESEL, 10, 1));
	}
	
	@Test
	public void getNumberOfCancellationsTooExpensive() {
		try {
			Double price = gasStation.buyGas(GasType.DIESEL, 10, 1);
			price += gasStation.buyGas(GasType.SUPER, 10, 2);
			assertEquals(45, gasStation.getRevenue());
		} catch (NotEnoughGasException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GasTooExpensiveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(1, gasStation.getNumberOfCancellationsTooExpensive());
	}
	
	@Test
	public void getRevenue() {
		try {
			Double price = gasStation.buyGas(GasType.DIESEL, 10, 5);
			price += gasStation.buyGas(GasType.SUPER, 10, 5);
			assertEquals(45, gasStation.getRevenue());
		} catch (NotEnoughGasException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GasTooExpensiveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void getPrice() {
		assertEquals(1.5, gasStation.getPrice(GasType.REGULAR));
		assertEquals(2.0, gasStation.getPrice(GasType.DIESEL));
		assertEquals(2.5, gasStation.getPrice(GasType.SUPER));
	}

}
