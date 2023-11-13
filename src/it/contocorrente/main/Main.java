package it.contocorrente.main;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import com.itextpdf.text.DocumentException;

import it.contocorrente.model.*;

public class Main {

	public static void main(String[] args) {

			LocalDate dataApertura = LocalDate.of(2021, 1, 1);
			ContoCorrente contoCorrente = new ContoCorrente("Andrea",dataApertura);
			ContoDeposito contoDeposito = new ContoDeposito("Andrea",dataApertura);
			ContoInvestimento contoInvestimento = new ContoInvestimento("Andrea",dataApertura);
			
			//COONTO CORRENTE
			contoCorrente.preleva(LocalDate.of(2021, 3, 15), 800);
			contoCorrente.versa(LocalDate.of(2021, 5, 5), 5000);
			contoCorrente.versa(LocalDate.of(2021, 9, 28), 3000);
//			contoCorrente.preleva(LocalDate.now(), 800);;
			contoDeposito.printPDF(2021);
			
			
			//CONTO DEPOSITO
			contoDeposito.preleva(LocalDate.of(2021, 3, 15), 800);
			contoDeposito.versa(LocalDate.of(2021, 5, 5), 5000);
			contoDeposito.versa(LocalDate.of(2021, 9, 28), 3000);
			contoDeposito.preleva(LocalDate.of(2022, 2, 1), 100);
			contoDeposito.preleva(LocalDate.of(2023, 8, 10), 800);
//			System.out.println(contoDeposito.generaInteressi(2021));
			contoDeposito.printPDF(2021);
			
			
			//CONTO INVESTIMENTO
			contoInvestimento.preleva(LocalDate.of(2021, 3, 15), 800);
			contoInvestimento.versa(LocalDate.of(2021, 5, 5), 5000);
			contoInvestimento.versa(LocalDate.of(2021, 9, 28), 3000);
//			contoInvestimento.preleva(LocalDate.now(), 800);
			contoDeposito.printPDF(2021);


	}

}
