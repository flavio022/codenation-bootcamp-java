package br.com.codenation.desafioexe;

import java.util.ArrayList;
import java.util.List;

public class DesafioApplication {

	public static List fibonacci() {
		int fib = 0;
		List<Integer> lista = new ArrayList<Integer>();

		while (fib <= 350) {
			if (lista.size() != 0) {
				lista.add(fib);
				fib += lista.get(lista.size() - 2);
			} else {
				lista.add(fib);
				fib = 1;
			}

		}

		lista.add(fib);

		return lista;
	}

	public static Boolean isFibonacci(Integer a) {
		return fibonacci().contains(a);
	}


}