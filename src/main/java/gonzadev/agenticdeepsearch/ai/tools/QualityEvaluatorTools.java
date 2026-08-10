package gonzadev.agenticdeepsearch.ai.tools;

import gonzadev.agenticdeepsearch.ai.agents.SearchingManagerAgent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class QualityEvaluatorTools {

    private final SearchingManagerAgent searchingManagerAgent;
    private final ChatClient clarificationAgent;

    @Tool(
            name = "searchingManager",
            description = "Herramienta para realizar búsquedas en la web y obtener información relevante para evaluar la calidad de un prompt."
    )
    public String search(String query) {
        log.info("[QualityEvaluatorTools] - Realizando búsqueda para evaluar la calidad del prompt...");
        return searchingManagerAgent.search(query);
    }

    @Tool(
            name = "clarificationAgent",
            description = "Actúa como un asistente de investigación para clarificar y acotar consultas ambiguas o incompletas, ayudando a definir el alcance y las metas de la investigación antes de iniciar la búsqueda."
    )
    public String clarifyQuery(String query) {
        log.info("[QualityEvaluatorTools] - Clarificando la consulta de búsqueda...");
        return clarificationAgent.prompt()
                .user(query)
                .call()
                .content();
    }
}
