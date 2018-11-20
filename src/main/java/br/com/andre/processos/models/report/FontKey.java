package br.com.andre.processos.models.report;

public class FontKey {
	
	private String name;
	private boolean bold;
	private boolean italic;
	
	public FontKey(String name, boolean bold, boolean italic) {
		this.name = name;
		this.bold = bold;
		this.italic = italic;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isBold() {
		return bold;
	}
	public void setBold(boolean bold) {
		this.bold = bold;
	}
	public boolean isItalic() {
		return italic;
	}
	public void setItalic(boolean italic) {
		this.italic = italic;
	}

}
