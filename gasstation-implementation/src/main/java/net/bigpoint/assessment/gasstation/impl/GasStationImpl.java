package net.bigpoint.assessment.gasstation.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import net.bigpoint.assessment.gasstation.GasPump;
import net.bigpoint.assessment.gasstation.GasStation;
import net.bigpoint.assessment.gasstation.GasType;
import net.bigpoint.assessment.gasstation.exceptions.GasTooExpensiveException;
import net.bigpoint.assessment.gasstation.exceptions.NotEnoughGasException;

public class GasStationImpl implements GasStation {
	
	private CopyOnWriteArrayList<GasPump> pumps = new CopyOnWriteArrayList<>();
	private ConcurrentHashMap<GasType, Double> priceMap = new ConcurrentHashMap<GasType, Double>();
	private double revenue;
	private AtomicInteger numberOfSales = new AtomicInteger(0);
	private AtomicInteger numberOfCancellationsNoGas = new AtomicInteger(0);
	private AtomicInteger numberOfCancellationsTooExpensive = new AtomicInteger(0);

	Lock lock = new ReentrantLock();
	
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
			numberOfCancellationsTooExpensive.getAndIncrement();
			throw new GasTooExpensiveException();
		}
		Boolean isFilled = false; // flag to check the gas is already filled. Not considered the case if gas can be filled from different pumps of same type as a whole having request quantity available
		double price = 0;
		
		for (int i = 0; i < pumps.size() && !isFilled; i++) {
			GasPump p = pumps.get(i);
			
			if (p.getGasType().equals(type)) {
				
				synchronized(p) { //locking to make sure no other threads is executing this block of code 
					
					
						if (!isFilled && p.getRemainingAmount() >= amountInLiters) {
							p.pumpGas(amountInLiters);
							
							try {
								lock.lock();
								price = amountInLiters * getPrice(type);
								revenue += price;
								numberOfSales.getAndIncrement();
								isFilled = true;
								return price;
							} finally {
								lock.unlock();
							}
						} 
					
				}
			}
			
			if (price == 0 && !isFilled && i == pumps.size() - 1) {
				numberOfCancellationsNoGas.getAndIncrement();
				throw new NotEnoughGasException();
			}
		}
		return price;
	}

	@Override
	public double getRevenue() {
		return revenue;
	}

	@Override
	public int getNumberOfSales() {
		return numberOfSales.get();
	}

	@Override
	public int getNumberOfCancellationsNoGas() {
		return numberOfCancellationsNoGas.get();
	}

	@Override
	public int getNumberOfCancellationsTooExpensive() {
		return numberOfCancellationsTooExpensive.get();
	}

	@Override
	public double getPrice(GasType type) {
		return priceMap.get(type) != null ? priceMap.get(type) : 0.0;
	}

	@Override
	public void setPrice(GasType type, double price) {
		priceMap.put(type, price);
	}

}
