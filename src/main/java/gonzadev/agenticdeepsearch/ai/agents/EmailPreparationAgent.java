package gonzadev.agenticdeepsearch.ai.agents;

import gonzadev.agenticdeepsearch.ai.utils.PromptLoader;
import gonzadev.agenticdeepsearch.ai.tools.EmailPreparationTools;
import gonzadev.agenticdeepsearch.dto.ReportDataDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailPreparationAgent {

    private final ChatClient chatClient;
    private final EmailPreparationTools emailPreparationTools;

    public String processAndSend(ReportDataDTO reportContent) {
        log.info("[EmailPreparationAgent] - Procesando el informe para preparar el correo electrónico...");
        return chatClient
                .prompt()
                .system(PromptLoader.loadPrompt("EmailPreparationAgent.txt"))
                .tools(emailPreparationTools)
                .user(promptUserSpec -> promptUserSpec
                        .text("Procesa el siguiente informe para preparar el correo: {report}")
                        .param("report", reportContent)
                )
                .call()
                .content();
    }
}
