package net.bigpoint.assessment.gasstation.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.bigpoint.assessment.gasstation.GasPump;
import net.bigpoint.assessment.gasstation.GasStation;
import net.bigpoint.assessment.gasstation.GasType;
import net.bigpoint.assessment.gasstation.exceptions.GasTooExpensiveException;
import net.bigpoint.assessment.gasstation.exceptions.NotEnoughGasException;

public class GasStationImpl implements GasStation {
	
	private List<GasPump> pumps = new ArrayList<>();
	private Map<GasType, Double> priceMap = new HashMap();
	private Map<GasType, Double> availabilityMap = new HashMap();
	private double price;
	private double revenue;
	private int numberOfSales;
	private int numberOfCancellationsNoGas;
	private int numberOfCancellationsTooExpensive;

	@Override
	public void addGasPump(GasPump pump) {
		pumps.add(pump);

	}

	@Override
	public Collection<GasPump> getGasPumps() {
		return pumps;
	}

	@Override
	public double buyGas(GasType type, double amountInLiters, double maxPricePerLiter)
			throws NotEnoughGasException, GasTooExpensiveException {
		
		if (amountInLiters <= 0)
			return 0.0;
		
		if (maxPricePerLiter < getPrice(type)) {
			numberOfCancellationsTooExpensive++;
			throw new GasTooExpensiveException();
		}
			
		pumps.stream().filter(p -> p.getGasType().equals(type)).forEach(p -> {
			
			if (p.getRemainingAmount() >= amountInLiters) {
				price = amountInLiters * getPrice(type);
				p.pumpGas(amountInLiters);
				revenue += price;
				numberOfSales++;
			} 
		});
		
		if (price == 0) {
			numberOfCancellationsNoGas++;
			throw new NotEnoughGasException();
		}
		return price;
	}

	@Override
	public double getRevenue() {
		return revenue;
	}

	@Override
	public int getNumberOfSales() {
		return numberOfSales;
	}

	@Override
	public int getNumberOfCancellationsNoGas() {
		return numberOfCancellationsNoGas;
	}

	@Override
	public int getNumberOfCancellationsTooExpensive() {
		return numberOfCancellationsTooExpensive;
	}

	@Override
	public double getPrice(GasType type) {
		return priceMap.get(type);
	}

	@Override
	public void setPrice(GasType type, double price) {
		priceMap.put(type, price);
	}

}
