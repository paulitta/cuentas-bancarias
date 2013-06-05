package edu.tallerweb.cuentas;

/**
 * Similar a la CuentaSueldo, pero se pide que luego de la quinta extracci�n de
 * dinero se cobre un costo adicional por extracci�n de $ 6
 */
public class CajaAhorros extends AbstractCuenta {

	private Double saldo = 0.0;
	private int cantExtracciones = 0;

	/**
	 * No hay reglas adicionales para el dep�sito
	 * 
	 * @param monto
	 *            a depositar
	 */
	public void depositar(final Double monto) {
		this.saldo += monto;
	}

	/**
	 * Se cobran $6 adicionales por cada extracci�n luego de la quinta.
	 * 
	 * @param monto
	 *            a extraer
	 */
	public void extraer(final Double monto) {

		this.cantExtracciones++;

		/*
		 * if (monto <= this.saldo) { this.saldo -= monto;
		 * 
		 * if (this.cantExtracciones > 5) {
		 * 
		 * this.saldo -= 6; } } else {
		 * 
		 * throw new CuentaBancariaException(
		 * "No tiene suficiente saldo para hacer la extracci�n"); }
		 */

		if (monto <= this.saldo && cantExtracciones <= 5) {
			this.saldo -= monto;
		} else {
			if (monto + 6 <= this.saldo && cantExtracciones > 5) {
				this.saldo -= monto + 6;
			} else {
				throw new CuentaBancariaException(
						"No tiene suficiente saldo para hacer la extracci�n");
			}
		}
	}

	/**
	 * Permite saber el saldo de la cuenta
	 * 
	 * @return el saldo de la cuenta
	 */
	public Double getSaldo() {
		return this.saldo;
	}

}
