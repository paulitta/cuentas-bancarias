package edu.tallerweb.cuentas;

/**
 * Es el tipo de cuenta más simple, ya que se rige por la premisa de que en
 * tanto y en cuanto se tenga tanto o más dinero en cuenta del que se quiere
 * extraer, la operación se debe efectuar correctamente.
 */
public class CuentaSueldo extends AbstractCuenta {

	private Double saldo = 0.0;

	/**
	 * No hay reglas adicionales para el depósito
	 * 
	 * @param monto
	 *            a depositar
	 */
	public void depositar(final Double monto) {

		if (monto > 0) {
			this.saldo += monto;
		} else {
			throw new CuentaBancariaException(
					"No puede depositar valores negativos");
		}

	}

	/**
	 * No hay reglas adicionales para la extracción
	 * 
	 * @param monto
	 *            a extraer
	 */
	public void extraer(final Double monto) {

		if (monto > 0) {
			if (monto <= this.saldo) {
				this.saldo -= monto;
			} else {
				throw new CuentaBancariaException(
						"No tiene suficiente saldo para el monto seleccionado");
			}
		} else {
			throw new CuentaBancariaException(
					"No puede extraer valores negativos");
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
