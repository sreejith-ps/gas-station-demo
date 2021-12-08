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
	private Double price;

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
		
		pumps.stream().filter(p -> p.getGasType().equals(type)).forEach(p -> {
			
			if (p.getRemainingAmount() >= amountInLiters) {
				price = amountInLiters * priceMap.get(type);
				p.pumpGas(amountInLiters);
			}
		});
		
		
		return price;
	}

	@Override
	public double getRevenue() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getNumberOfSales() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getNumberOfCancellationsNoGas() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getNumberOfCancellationsTooExpensive() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getPrice(GasType type) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setPrice(GasType type, double price) {
		priceMap.put(type, price);
	}

}
