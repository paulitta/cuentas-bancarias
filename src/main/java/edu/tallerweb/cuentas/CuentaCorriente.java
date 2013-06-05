package edu.tallerweb.cuentas;

/**
 * La m�s compleja de las cuentas, �sta permite establecer una cantidad de
 * dinero a girar en descubierto. Es por ello que cada vez que se desee extraer
 * dinero, no s�lo se considera el que se posee, sino el l�mite adicional que el
 * banco estar� brindando. Por supuesto esto no es gratis, ya que el banco nos
 * cobrar� un 5% como comisi�n sobre todo el monto en descubierto consumido en
 * la operaci�n. Por ejemplo, si tuvi�ramos $ 100 en la cuenta, y quisi�ramos
 * retirar $ 200 (con un descubierto de $ 150), podremos hacerlo. Pasaremos a
 * deberle al banco $ 105 en total: los $ 100 que nos cubri�, m�s el 5%
 * adicional sobre el descubierto otorgado.
 */
public class CuentaCorriente extends AbstractCuenta {

	private Double descubiertoTotal = 0.0;
	private Double descubiertoParcial = 0.0;
	private Double descubiertoDisponible = 0.0;
	private Double saldo = 0.0;
	private final int ADICIONAL = 5;
	private final int PARA_PORCENTAJE = 100;

	/**
	 * Toda cuenta corriente se inicia con un l�mite total para el descubierto.
	 * 
	 * @param descubiertoTotal
	 */
	public CuentaCorriente(final Double descubiertoTotal) {
		this.descubiertoTotal = descubiertoTotal;
		this.descubiertoDisponible = descubiertoTotal;
	}

	/**
	 * Todo dep�sito deber� cubrir primero el descubierto, si lo hubiera, y
	 * luego contar para el saldo de la cuenta.
	 * 
	 * @param monto
	 *            a depositar
	 */
	public void depositar(final Double monto) {

		if (monto > 0) {
			if (this.descubiertoDisponible == this.descubiertoTotal) {
				this.saldo += monto;
			} else {
				if (monto >= (this.descubiertoTotal - this.descubiertoDisponible)) {
					this.saldo += (monto - (this.descubiertoTotal - this.descubiertoDisponible));
					this.descubiertoDisponible = this.descubiertoTotal;
				} else {

					this.descubiertoDisponible += monto;
				}
			}
		} else {
			throw new CuentaBancariaException(
					"No puede depositar valores negativos");
		}
	}

	/**
	 * Se cobrar� el 5% de comisi�n sobre el monto girado en descubierto. Por
	 * supuesto, no puede extraerse m�s que el total de la cuenta, m�s el
	 * descubierto (comisi�n inclu�da)
	 * 
	 * @param monto
	 *            a extraer
	 */
	public void extraer(final Double monto) {

		if (monto > 0) {

			Double porcentajeDescubierto = 0.0;

			if (this.saldo < monto) { // Si el monto es superior al saldo ya
										// calculo
										// el porcentaje del descubierto
				this.descubiertoParcial = monto - this.saldo;
				porcentajeDescubierto = (ADICIONAL * this.descubiertoParcial)
						/ PARA_PORCENTAJE;
			}

			if (monto + porcentajeDescubierto <= this.saldo
					+ this.descubiertoDisponible) {

				if (monto <= this.saldo) { // Saldo es mayor a monto, extraigo
											// de la
											// cuenta
					this.saldo -= monto;
				} else {
					this.descubiertoDisponible -= this.descubiertoParcial
							+ porcentajeDescubierto; // Resto al descubierto
														// total ya que lo estoy
														// utilizando
					this.saldo = 0.0;
				}
			} else {

				throw new CuentaBancariaException(
						"No puede extraer ese monto, supera el saldo y el descubierto");
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

	/**
	 * Permite saber el saldo en descubierto
	 * 
	 * @return el descubierto de la cuenta
	 */
	public Double getDescubierto() {
		return this.descubiertoDisponible;
	}

}
