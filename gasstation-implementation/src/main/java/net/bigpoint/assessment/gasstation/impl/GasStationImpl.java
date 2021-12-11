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
	private double price;
	private double revenue;
	private AtomicInteger numberOfSales;
	private AtomicInteger numberOfCancellationsNoGas;
	private AtomicInteger numberOfCancellationsTooExpensive;
	private boolean isFilled = false;

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
		isFilled = false; //resets flag to check the gas is already filled. Not considered the case if gas can be filled from different pumps of same type as a whole having request quantity available
		price = 0;
		
		pumps.stream().filter(p -> p.getGasType().equals(type)).forEach(p -> {
			
			lock.lock(); //locking to make sure no other threads is executing this block of code 
			try {
				if (!isFilled && p.getRemainingAmount() >= amountInLiters) {
					price = amountInLiters * getPrice(type);
					p.pumpGas(amountInLiters);
					revenue += price;
					numberOfSales.getAndIncrement();
					isFilled = true;
				} 
			} finally {
				lock.unlock();
			}
			
		});
		
		if (price == 0) {
			numberOfCancellationsNoGas.getAndIncrement();
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
		return priceMap.get(type);
	}

	@Override
	public void setPrice(GasType type, double price) {
		priceMap.put(type, price);
	}

}
