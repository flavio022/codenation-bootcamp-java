package br.com.codenation.calculadora;


public class CalculadoraSalario {

	public long calcularSalarioLiquido(double salarioBase) {
		//Use o Math.round apenas no final do método para arredondar o valor final.
		//Documentação do método: https://docs.oracle.com/javase/8/docs/api/java/lang/Math.html#round-double-
		if(salarioBase < 1039){
			return Math.round(0.0);
		}
		double salarioInss =  salarioBase - calcularInss(salarioBase);
		long salarioliquido = Math.round(salarioInss - calculoirrf(salarioInss));

		return salarioliquido;
	}
	
	
	//Exemplo de método que pode ser criado para separar melhor as responsábilidades de seu algorítmo
	public double calcularInss(double salarioBase) {
		double valorInss=0;

		if (salarioBase<=1500){
			double inss = salarioBase*0.08;
			return inss;
		} else if (1500 < salarioBase && salarioBase <= 4000){
			double inss =  salarioBase*0.09;
			return inss;
		} else {
			double inss = salarioBase*0.11;
			return inss;
		}

	}
	private double calculoirrf(double inss) {
		if(3000 <inss && inss <=6000){
			double irrf = inss*0.075;
			return irrf;
		} else if(6000 < inss){
			double irrf = inss*0.15;
			return irrf;
		} else {
			return 0.0;
		}
	}

}
/*Dúvidas ou Problemas?
Manda e-mail para o meajuda@codenation.dev que iremos te ajudar! 
*/