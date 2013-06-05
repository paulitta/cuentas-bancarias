package edu.tallerweb.cuentas;

import org.junit.Assert;
import org.junit.Test;

public class CuentaTests {

	/******** TESTs DE CUENTA SUELDO ***********/

	@Test
	public void queVerifiqueLaConsigna() {
		CuentaSueldo cuenta = new CuentaSueldo();
		cuenta.depositar(4000.0);

		Assert.assertEquals(
				"al depositar $ 4000.0 en una cuenta vacía, tiene $ 4000.0",
				4000.0, cuenta.getSaldo(), 0.0);

		cuenta.extraer(500.0);

		Assert.assertEquals(
				"al extraer $ 500.0 de una cuenta con $ 4000.0 se obtienen $ 3500.0",
				3500.0, cuenta.getSaldo(), 0.0);
	}

	@Test(expected = CuentaBancariaException.class)
	public void queVerifiqueLaConsignaException() {
		CuentaSueldo cuenta = new CuentaSueldo();
		cuenta.depositar(3500.0);

		cuenta.extraer(4000.0);
	}

	@Test(expected = CuentaBancariaException.class)
	public void queNoSePuedaDepositarValoresNegativosEnCuentaSueldo() {
		CuentaSueldo cuenta = new CuentaSueldo();
		cuenta.depositar(-750.0);
	}

	@Test(expected = CuentaBancariaException.class)
	public void queNoSePuedaExtraerValoresNegativosEnCuentaSueldo() {
		CuentaSueldo cuenta = new CuentaSueldo();
		cuenta.extraer(-750.0);
	}

	/******** TESTs DE CAJA DE AHORROS ***********/

	@Test
	public void queCajaDeAhorrosHagaLaConsigna() {
		CajaAhorros cuenta = new CajaAhorros();
		cuenta.depositar(6000.0);

		Assert.assertEquals(
				"al depositar $ 6000.0 en una cuenta vacía, tiene $ 6000.0",
				6000.0, cuenta.getSaldo(), 0.0);

		cuenta.extraer(1000.0);

		Assert.assertEquals(
				"al extraer $ 1000.0 de una cuenta con $ 6000.0 se obtienen $ 5000.0",
				5000.0, cuenta.getSaldo(), 0.0);
	}

	@Test
	public void queCobreSeisPesosLuegoDeLaQuintaExtraccion() {
		CajaAhorros cuenta = new CajaAhorros();
		cuenta.depositar(10000.0);

		Assert.assertEquals(
				"al depositar $ 10000.0 en una cuenta vacía, tiene $ 10000.0",
				10000.0, cuenta.getSaldo(), 0.0);

		cuenta.extraer(250.0);
		cuenta.extraer(850.0);
		cuenta.extraer(320.0);
		cuenta.extraer(1110.0);
		cuenta.extraer(170.0);
		cuenta.extraer(2400.0);

		Assert.assertEquals(
				"al extraer $ 5100.0 de una cuenta con $ 10000.0 y cobrar $6 por exceder las 5 extracciones, se obtienen $ 5000.0",
				4894.0, cuenta.getSaldo(), 0.0);
	}

	@Test(expected = CuentaBancariaException.class)
	public void queCajaDeAhorrosHagaLaConsignaException() {
		CajaAhorros cuenta = new CajaAhorros();
		cuenta.depositar(3500.0);

		cuenta.extraer(2000.0);
		cuenta.extraer(2000.0);
	}

	@Test(expected = CuentaBancariaException.class)
	public void queNoSePuedaDepositarValoresNegativosEnCajaDeAhorros() {
		CajaAhorros cuenta = new CajaAhorros();
		cuenta.depositar(-750.0);
	}

	@Test(expected = CuentaBancariaException.class)
	public void queNoSePuedaExtraerValoresNegativosEnCajaDeAhorros() {
		CajaAhorros cuenta = new CajaAhorros();
		cuenta.extraer(-750.0);
	}

	/******** TESTs DE CUENTA CORRIENTE ***********/

	@Test
	public void queCuentaCorrienteHagaLaConsigna() {
		CuentaCorriente cuenta = new CuentaCorriente(500.0);
		cuenta.depositar(2500.0);

		Assert.assertEquals(
				"al depositar $ 2500.0 en una cuenta vacía, tiene $ 2500.0",
				2500.0, cuenta.getSaldo(), 0.0);

		cuenta.extraer(750.0);

		Assert.assertEquals(
				"al extraer $ 750.0 de una cuenta con $ 2500.0 se obtienen $ 1750.0",
				1750.0, cuenta.getSaldo(), 0.0);

		Assert.assertEquals("al no utilizar descubierto debe estar en $0.0",
				0.0, cuenta.getDescubierto(), 0.0);
	}

	@Test(expected = CuentaBancariaException.class)
	public void queCuentaCorrienteHagaLaConsignaException() {
		CuentaCorriente cuenta = new CuentaCorriente(250.0);
		cuenta.depositar(300.0);

		cuenta.extraer(600.0);
	}

	@Test(expected = CuentaBancariaException.class)
	public void queNoSePuedaDepositarValoresNegativosEnCuentaCorriente() {
		CuentaCorriente cuenta = new CuentaCorriente(250.0);
		cuenta.depositar(-750.0);
	}
	
	@Test(expected = CuentaBancariaException.class)
	public void queNoSePuedaExtraerValoresNegativosEnCuentaCorriente() {
		CuentaCorriente cuenta = new CuentaCorriente(250.0);
		cuenta.extraer(-750.0);
	}

	@Test
	public void queAlUsarDescubiertoMeCobrenComision() {
		CuentaCorriente cuenta = new CuentaCorriente(500.0);
		cuenta.depositar(2500.0);

		Assert.assertEquals(
				"al depositar $ 2500.0 en una cuenta vacía, tiene $ 2500.0",
				2500.0, cuenta.getSaldo(), 0.0);

		cuenta.extraer(2700.0);

		Assert.assertEquals(
				"al extraer $ 2700.0 de una cuenta con $ 2500.0 el saldo queda en $ 0.0",
				0.0, cuenta.getSaldo(), 0.0);

		Assert.assertEquals(
				"al extraer $ 2700.0 de una cuenta con $ 2500.0 el descubierto es de $ 200.0 mas comision $ 10",
				210.0, cuenta.getDescubierto(), 0.0);

		cuenta.extraer(150.0);

		Assert.assertEquals(
				"al extraer $ 150.0 de la cuenta que posee un descubierto parcial de $ 210.0 ahora pasa a deber $ 367.50 con la comisión incluída",
				367.50, cuenta.getDescubierto(), 0.0);
	}

	/*
	 * @Test public void queAlDepositarPrimeroCubraElDescubierto() {
	 * CuentaCorriente cuenta = new CuentaCorriente(500.0);
	 * cuenta.depositar(1000.0);
	 * 
	 * Assert.assertEquals(
	 * "al depositar $ 1000.0 en una cuenta vacía, tiene $ 1000.0", 1000.0,
	 * cuenta.getSaldo(), 0.0);
	 * 
	 * cuenta.extraer(1100.0);
	 * 
	 * Assert.assertEquals(
	 * "al extraer $ 1100.0 de una cuenta con $ 1000.0 el saldo queda en $ 0.0",
	 * 0.0, cuenta.getSaldo(), 0.0);
	 * 
	 * Assert.assertEquals(
	 * "al extraer $ 1100.0 de una cuenta con $ 1000.0 el descubierto es de $ 100.0 mas comision $ 5"
	 * , 105.0, cuenta.getDescubierto(), 0.0);
	 * 
	 * cuenta.depositar(50.0);
	 * 
	 * Assert.assertEquals(
	 * "al depositar $ 50.0 el descubierto queda en $ 55.0", 55.0,
	 * cuenta.getDescubierto(), 0.0);
	 * 
	 * Assert.assertEquals("la cuenta sigue en $ 0.0", 0.0, cuenta.getSaldo(),
	 * 0.0);
	 * 
	 * cuenta.depositar(70.0);
	 * 
	 * Assert.assertEquals( "al depositar $ 70.0 el descubierto queda en $ 0.0",
	 * 0.0, cuenta.getDescubierto(), 0.0);
	 * 
	 * Assert.assertEquals("la cuenta tiene $ 15.0", 15.0, cuenta.getSaldo(),
	 * 0.0); }
	 */
}