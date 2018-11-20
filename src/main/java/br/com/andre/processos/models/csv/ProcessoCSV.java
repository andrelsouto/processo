package br.com.andre.processos.models.csv;

import com.opencsv.bean.CsvBindByPosition;

public class ProcessoCSV {
	
	@CsvBindByPosition(position = 0)
	private String col1;
	@CsvBindByPosition(position = 1)
	private String col2;
	
	public String getCol1() {
		return col1;
	}
	public void setCol1(String col1) {
		this.col1 = col1;
	}
	public String getCol2() {
		return col2;
	}
	public void setCol2(String col2) {
		this.col2 = col2;
	}

}
