package net.bigpoint.assessment.gasstation.impl;

import java.util.Collection;

import net.bigpoint.assessment.gasstation.GasPump;
import net.bigpoint.assessment.gasstation.GasStation;
import net.bigpoint.assessment.gasstation.GasType;
import net.bigpoint.assessment.gasstation.exceptions.GasTooExpensiveException;
import net.bigpoint.assessment.gasstation.exceptions.NotEnoughGasException;

public class GasStationImpl implements GasStation {

	@Override
	public void addGasPump(GasPump pump) {
		// TODO Auto-generated method stub

	}

	@Override
	public Collection<GasPump> getGasPumps() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double buyGas(GasType type, double amountInLiters, double maxPricePerLiter)
			throws NotEnoughGasException, GasTooExpensiveException {
		// TODO Auto-generated method stub
		return 0;
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
		// TODO Auto-generated method stub

	}

}
