package gonzadev.agenticdeepsearch.ai.tools;

import gonzadev.agenticdeepsearch.ai.agents.EmailPreparationAgent;
import gonzadev.agenticdeepsearch.ai.agents.WebSearchAgent;
import gonzadev.agenticdeepsearch.dto.ReportDataDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SearchingManagerTools {

    private final ChatClient queryExpansionAgent;
    private final WebSearchAgent webSearchAgent;
    private final ChatClient reportGeneratorAgent;
    private final EmailPreparationAgent emailPreparationAgent;

    @Tool(
            name = "queryExpansionAgent",
            description = "Actúa como un analista estratégico de búsqueda. Toma un termino de búsqueda y genera una lista de 3 términos de búsqueda relacionados, que podrían ayudar a refinar o expandir la búsqueda original."
    )
    public String expandQuery(String searchTerm) {
        log.info("[SearchManagerTools] - Expandiendo la consulta de búsqueda...");
        return queryExpansionAgent.prompt()
                .user(searchTerm)
                .call()
                .content();
    }

    @Tool(
            name = "webSearchAgent",
            description = "Actúa como un motor de búsqueda web. Toma un término de búsqueda y devuelve los resultados de búsqueda más relevantes."
    )
    public String webSearching(String search) {
        log.info("[SearchingManagerTools] - Realizando búsqueda web...");
        return webSearchAgent
                .searchWebStable(search);
    }

    @Tool(
            name = "reportGeneratorAgent",
            description = "Actúa como un generador de informes. Toma los resultados de búsqueda y genera un informe detallado y bien estructurado. Devuelve el informe en formato JSON"
    )
    public ReportDataDTO generateReport(String searchResults) {
        log.info("[SearchingManagerTools] - Generando informe a partir de los resultados de búsqueda...");
        return reportGeneratorAgent.prompt()
                .user(searchResults)
                .call()
                .entity(ReportDataDTO.class);
    }

    @Tool(
            name = "emailPreparationAgent",
            description = "Actúa como un asistente de preparación de correos electrónicos. Toma el contenido del informe y genera un correo electrónico atractivo y profesional, listo para ser enviado."
    )
    public String prepareEmail(ReportDataDTO reportContent) {
        log.info("[SearchingManagerTools] - Preparando correo electrónico a partir del informe...");
        return emailPreparationAgent.processAndSend(reportContent);
    }

}
