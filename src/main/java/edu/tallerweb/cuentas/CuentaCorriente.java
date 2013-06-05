package edu.tallerweb.cuentas;

/**
 * La más compleja de las cuentas, Ésta permite establecer una cantidad de
 * dinero a girar en descubierto. Es por ello que cada vez que se desee extraer
 * dinero, no sólo se considera el que se posee, sino el límite adicional que el
 * banco estará brindando.
 * 
 * Por supuesto esto no es gratis, ya que el banco nos cobrará un 5% como
 * comisión sobre todo el monto en descubierto consumido en la operación.
 * 
 * Por ejemplo, si tuviéramos $ 100 en la cuenta, y quisiéramos retirar $ 200
 * (con un descubierto de $ 150), podremos hacerlo. Pasaremos a deberle al banco
 * $ 105 en total: los $ 100 que nos cubrió, más el 5% adicional sobre el
 * descubierto otorgado.
 */
public class CuentaCorriente extends AbstractCuenta {

	private Double descubiertoTotal = 0.0;
	private Double descubierto = 0.0;
	private Double saldo = 0.0;

	/**
	 * Toda cuenta corriente se inicia con un límite total para el descubierto.
	 * 
	 * @param descubiertoTotal
	 */
	public CuentaCorriente(final Double descubiertoTotal) {
		this.descubiertoTotal = descubiertoTotal;
	}

	/**
	 * Todo depósito deberá cubrir primero el descubierto, si lo hubiera, y
	 * luego contar para el saldo de la cuenta.
	 * 
	 * @param monto
	 *            a depositar
	 */
	public void depositar(final Double monto) {

		if (this.descubierto == 0) {
			this.saldo += monto;
		} else {
			if (monto >= this.descubierto) {
				this.saldo += (monto - this.descubierto);
				this.descubierto = 0.0;
			} else {

				this.descubierto -= monto;
			}
		}
	}

	/**
	 * Se cobrará el 5% de comisión sobre el monto girado en descubierto. Por
	 * supuesto, no puede extraerse más que el total de la cuenta, más el
	 * descubierto (comisión incluída)
	 * 
	 * @param monto
	 *            a extraer
	 */
	public void extraer(final Double monto) {

		// limite 500
		// saldo 100, desc 0, saco 50 => saldo 50, desc 0
		// saldo 100, desc 0, saco 200 => saldo 0, desc 105
		// saldo 0, desc 105, saco 200 =>

		Double porcentajeDescubierto = 0.0;

		if (this.saldo < monto) { // Si el monto es superior al saldo ya calculo
									// el porcentaje del descubierto
			porcentajeDescubierto = (5 * (monto - this.saldo)) / 100;
		}

		if (monto <= this.saldo + this.descubiertoTotal
				- (this.descubierto + porcentajeDescubierto)) {

			if (monto <= this.saldo) { // Saldo es mayor a monto, extraigo de la cuenta
				this.saldo -= monto;
			} else {
				this.descubierto += (monto - this.saldo)
						+ porcentajeDescubierto; // Sigo acumulando descubierto
													// utilizado
				this.saldo = 0.0;
			}

		} else {

			throw new CuentaBancariaException(
					"No puede extraer ese monto, supera el saldo y el descubierto");
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

	/**
	 * Permite saber el saldo en descubierto
	 * 
	 * @return el descubierto de la cuenta
	 */
	public Double getDescubierto() {
		return this.descubierto;
	}

}
