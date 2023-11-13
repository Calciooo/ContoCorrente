package it.contocorrente.model;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

public class Movimento {
	
	private LocalDate data;
	private String tipo;
	private double importo, saldo_post;
	
	public Movimento(LocalDate data, String tipo, double importo, double saldo_post) {
		super();
		this.data = data;
		this.tipo = tipo;
		this.importo = importo;
		this.saldo_post = saldo_post;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public double getImporto() {
		return importo;
	}

	public void setImporto(double importo) {
		this.importo = importo;
	}

	public double getSaldo_post() {
		return saldo_post;
	}

	public void setSaldo_post(double saldo_post) {
		this.saldo_post = saldo_post;
	}

	@Override
	public String toString() {
		DecimalFormat df = new DecimalFormat("0.00");
		
		String toString =  (data+"   "+"|"+StringUtils.rightPad(tipo,18," ")+"|"+StringUtils.rightPad(df.format(importo),11," ")+"|"
				+StringUtils.rightPad(df.format(saldo_post),9," "));
		return toString;
	}

	public int compareTo(Movimento m) {
		LocalDate d1 = this.data;
		LocalDate d2 = m.getData();
		if (d1.isAfter(d2))
			return 1;
		else if (d1.isBefore(d2))
			return -1;
		else if (d1.isEqual(d2))
			return 0;
		return 0;
	}
	
}
