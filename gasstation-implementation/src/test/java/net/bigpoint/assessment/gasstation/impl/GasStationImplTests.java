package net.bigpoint.assessment.gasstation.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import net.bigpoint.assessment.gasstation.GasPump;
import net.bigpoint.assessment.gasstation.GasStation;
import net.bigpoint.assessment.gasstation.GasType;
import net.bigpoint.assessment.gasstation.exceptions.GasTooExpensiveException;
import net.bigpoint.assessment.gasstation.exceptions.NotEnoughGasException;

@SpringBootTest
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
	public void addGasPumpShouldAddPumpsAndReturnCountofPumps() {
		GasPump pump = new GasPump(GasType.REGULAR, 10);
		gasStation.addGasPump(pump);
		Collection<GasPump> p = gasStation.getGasPumps();
		assertEquals(4, p.size());
	}
	
	@Test
	public void shouldGivePumpSizeWhenGetGasPumpsInvoked() {
		Collection<GasPump> p = gasStation.getGasPumps();
		assertEquals(3, p.size());
	}
	
	@Test
	public void testBuyGasWithProperQuantityAndCost() {
		
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


	Double price = 0.0;
	
    @Test
	public void testBuyGasWithProperQuantityAndCostMultiThreaded() {
//    	GasPump diesel = new GasPump(GasType.DIESEL, 500);
//		gasStation.addGasPump(diesel);
//		GasPump regular = new GasPump(GasType.REGULAR, 1000);
//		gasStation.addGasPump(regular);
//		GasPump superPump = new GasPump(GasType.SUPER, 400);
//		gasStation.addGasPump(superPump);
//		
//
//		gasStation.setPrice(GasType.DIESEL, 2.0);
//		gasStation.setPrice(GasType.REGULAR, 1.5);
//		gasStation.setPrice(GasType.SUPER, 2.5);
		
		ExecutorService executors = Executors.newFixedThreadPool(3);
		
			for (int i = 0; i < 10; i++) {
				executors.submit(() -> {
					try {
						price += gasStation.buyGas(GasType.DIESEL, 60, 5);
					} catch (NotEnoughGasException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (GasTooExpensiveException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				});
			}

	}
	
	@Test
	public void shouldThrowNotEnoughGasExceptionWhenRequestedQtyOfGasNotAvailable() {
		assertThrows(NotEnoughGasException.class, () -> gasStation.buyGas(GasType.DIESEL, 10000, 3));
	}
	
	@Test
	public void shouldReturnNumberOfCancellationsDueToNoGasAvailability() {
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
	public void shouldThrowGasTooExpensiveExceptionWhenRequestedQtyOfGasNotAvailable() {
		assertThrows(GasTooExpensiveException.class, () -> gasStation.buyGas(GasType.DIESEL, 10, 1));
	}
	
	@Test
	public void shouldReturnNumberOfCancellationsDueToGasTooExpensive() {
		try {
			gasStation.buyGas(GasType.DIESEL, 10, 1);
			gasStation.buyGas(GasType.SUPER, 10, 2);
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
	public void shouldReturnTotalRevenueWhenBuyGasOpPerformed() {
		try {
			Double price = gasStation.buyGas(GasType.DIESEL, 10, 5);
			price += gasStation.buyGas(GasType.SUPER, 10, 5);
			assertEquals(price, gasStation.getRevenue());
		} catch (NotEnoughGasException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GasTooExpensiveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void shouldSetAndReturnPriceWhenTypeOfPumpAndPriceIsPassed() {

		gasStation.setPrice(GasType.DIESEL, 5.0);
		gasStation.setPrice(GasType.REGULAR, 4.0);
		gasStation.setPrice(GasType.SUPER, 6.5);
		
		assertEquals(4.0, gasStation.getPrice(GasType.REGULAR));
		assertEquals(5.0, gasStation.getPrice(GasType.DIESEL));
		assertEquals(6.5, gasStation.getPrice(GasType.SUPER));
	}

}
