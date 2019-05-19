package br.com.andre.processos.utils;

import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class PDFReport {

	
	public byte[] reportGenarator(String fileName, Map<String, Object> params, JRBeanCollectionDataSource dataSource) {
		
		try {
			
			JasperPrint print = JasperFillManager.fillReport(
					this.getClass().getClassLoader().getResource("/relatorios") + "/processos.jasper", params, dataSource);
			return JasperExportManager.exportReportToPdf(print);
		} catch (JRException e) {
			
			e.printStackTrace();
			return null;
		}
	}

}
