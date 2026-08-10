package gonzadev.agenticdeepsearch.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportDataDTO {

    @JsonPropertyDescription("Un resumen de 2-3 oraciones de los hallazgos.")
    private String shortSummary;

    @JsonPropertyDescription("El informe final")
    private String markdownReport;

    @JsonPropertyDescription("Temas sugeridos para investigar más")
    private List<String> followUpQuestions;
}
