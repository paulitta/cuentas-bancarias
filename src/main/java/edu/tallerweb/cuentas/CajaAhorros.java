package edu.tallerweb.cuentas;

/**
 * Similar a la CuentaSueldo, pero se pide que luego de la quinta extracción de
 * dinero se cobre un costo adicional por extracción de $ 6
 */
public class CajaAhorros extends AbstractCuenta {

	private Double saldo = 0.0;
	private int cantExtracciones = 0;
	private final static int EXTRACCION_TOPE = 5;
	private final static int ADICIONAL = 6;

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
	 * Se cobran $6 adicionales por cada extracción luego de la quinta.
	 * 
	 * @param monto
	 *            a extraer
	 */
	public void extraer(final Double monto) {

		this.cantExtracciones++;

		if (monto > 0) {
			if (monto <= this.saldo && cantExtracciones <= EXTRACCION_TOPE) {
				this.saldo -= monto;
			} else {
				if (monto + ADICIONAL <= this.saldo && cantExtracciones > EXTRACCION_TOPE) {
					this.saldo -= monto + ADICIONAL;
				} else {
					throw new CuentaBancariaException(
							"No tiene suficiente saldo para hacer la extracción");
				}
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
