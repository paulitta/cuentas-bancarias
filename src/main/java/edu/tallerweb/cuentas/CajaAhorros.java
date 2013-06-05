package edu.tallerweb.cuentas;

/**
 * Similar a la CuentaSueldo, pero se pide que luego de la quinta extracci�n de
 * dinero se cobre un costo adicional por extracci�n de $ 6
 */
public class CajaAhorros extends AbstractCuenta {

	private Double saldo = 0.0;
	private int cantExtracciones = 0;
	private final int extraccionTope = 5;
	private final int adicional = 6;

	/**
	 * No hay reglas adicionales para el dep�sito
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
	 * Se cobran $6 adicionales por cada extracci�n luego de la quinta.
	 * @param monto
	 *            a extraer
	 */
	public void extraer(final Double monto) {

		this.cantExtracciones++;

		if (monto > 0) {
			if (monto <= this.saldo && cantExtracciones <= extraccionTope) {
				this.saldo -= monto;
			} else {
				if (monto + adicional <= this.saldo && cantExtracciones > extraccionTope) {
					this.saldo -= monto + adicional;
				} else {
					throw new CuentaBancariaException(
							"No tiene suficiente saldo para hacer la extracci�n");
				}
			}
		} else {
			throw new CuentaBancariaException(
					"No puede extraer valores negativos");

		}
	}

	/**
	 * Permite saber el saldo de la cuenta
	 * @return el saldo de la cuenta
	 */
	public Double getSaldo() {
		return this.saldo;
	}

}
