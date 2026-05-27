package consultorio;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.List;

public class CarnetVacunacionPDF {

    public static void generar(Paciente paciente, List<PacienteVacuna> vacunas, String rutaArchivo) {
        Document document = new Document(PageSize.A4.rotate());

        try {
            PdfWriter.getInstance(document, new FileOutputStream(rutaArchivo));
            document.open();

            Font tituloFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Font subtituloFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 10);

            Paragraph titulo = new Paragraph("Carnet de Vacunación", tituloFont);
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);

            document.add(new Paragraph(" "));

            document.add(new Paragraph("Paciente: " + paciente.getNombreCompleto(), normalFont));
            document.add(new Paragraph("DNI: " + valor(paciente.getDni()), normalFont));
            document.add(new Paragraph("Fecha de nacimiento: " + valor(paciente.getFechaNacimiento()), normalFont));
            document.add(new Paragraph("Fecha de emisión: " + LocalDate.now(), normalFont));

            document.add(new Paragraph(" "));

            PdfPTable tabla = new PdfPTable(7);
            tabla.setWidthPercentage(100);
            tabla.setWidths(new float[]{2, 4, 3, 2, 3, 3, 5});

            agregarCeldaHeader(tabla, "Edad", subtituloFont);
            agregarCeldaHeader(tabla, "Vacuna", subtituloFont);
            agregarCeldaHeader(tabla, "Dosis", subtituloFont);
            agregarCeldaHeader(tabla, "Aplicada", subtituloFont);
            agregarCeldaHeader(tabla, "Fecha", subtituloFont);
            agregarCeldaHeader(tabla, "Estado", subtituloFont);
            agregarCeldaHeader(tabla, "Observaciones", subtituloFont);

            for (PacienteVacuna v : vacunas) {
                agregarCelda(tabla, valor(v.getEdadTexto()), normalFont);
                agregarCelda(tabla, valor(v.getVacuna()), normalFont);
                agregarCelda(tabla, valor(v.getDosis()), normalFont);
                agregarCelda(tabla, v.isAplicada() ? "Sí" : "No", normalFont);
                agregarCelda(tabla, valor(v.getFechaAplicacion()), normalFont);
                agregarCelda(tabla, valor(v.getEstado()), normalFont);
                agregarCelda(tabla, valor(v.getObservaciones()), normalFont);
            }

            document.add(tabla);
            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void agregarCeldaHeader(PdfPTable tabla, String texto, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(texto, font));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setPadding(5);
        tabla.addCell(cell);
    }

    private static void agregarCelda(PdfPTable tabla, String texto, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(texto, font));
        cell.setPadding(4);
        tabla.addCell(cell);
    }

    private static String valor(String texto) {
        return texto != null ? texto : "";
    }
}