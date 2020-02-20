package es.rfgr.cr4vegas.utileria.informes;

import java.awt.image.BufferedImage;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import es.rfgr.cr4vegas.modelo.postgresql.conexion.ConexionAlmacen;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.presupuesto.Presupuesto;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

public class InformePresupuesto {

	private final static String FICHERO_PRESUPUESTO_JRXML = "/es/rfgr/cr4vegas/utileria/informes/presupuesto.jrxml";

	public static void rellenar(Presupuesto presupuesto) throws DAOException {

		try {
			Connection conn = ConexionAlmacen.getConexion();

			JasperReport reporte = JasperCompileManager
					.compileReport(InformePresupuesto.class.getResourceAsStream(FICHERO_PRESUPUESTO_JRXML));

			// Cargar los parámetros necesarios para el reporte, en este caso el código del
			// presupuesto
			Map<String, Object> parametros = new HashMap<>();
			parametros.put("cod_presupuesto", presupuesto.getCodPresupuesto());

			BufferedImage logo = ImageIO.read(
					InformePresupuesto.class.getResourceAsStream("/es/rfgr/cr4vegas/utileria/informes/logo4vegas.png"));

			parametros.put("logo", logo);
			parametros.put("iva", presupuesto.getIVA());
			parametros.put("total_con_iva", presupuesto.getTotalConIVA());

			// Rellenar los datos del reporte (conexión y parámetros)
			JasperPrint reporteImpreso = JasperFillManager.fillReport(reporte, parametros, conn);

//			JasperExportManager.exportReportToPdf(reporteImpreso);
			JasperViewer.viewReport(reporteImpreso, false);

		} catch (Exception e) {
			throw new DAOException(e.getMessage(), e);
		}
	}
}
