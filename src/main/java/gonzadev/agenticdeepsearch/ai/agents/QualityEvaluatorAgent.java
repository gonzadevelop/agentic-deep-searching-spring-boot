package gonzadev.agenticdeepsearch.ai.agents;

import gonzadev.agenticdeepsearch.ai.tools.QualityEvaluatorTools;
import gonzadev.agenticdeepsearch.ai.utils.PromptLoader;
import gonzadev.agenticdeepsearch.ai.advisors.InputGuardrailAdvisor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class QualityEvaluatorAgent {

    private final InputGuardrailAdvisor inputGuardrailAdvisor;
    private final ChatClient chatClient;
    private final QualityEvaluatorTools qualityEvaluatorTools;


    public String evaluateQuality(String prompt) {
        log.info("[QualityEvaluatorAgent] - Evaluando la calidad del prompt recibido...");
        return chatClient
                .prompt()
                .system(PromptLoader.loadPrompt("QualityEvaluatorAgent.txt"))
                .user(prompt)
                .advisors(inputGuardrailAdvisor)
                .tools(qualityEvaluatorTools)
                .call()
                .content();
    }
}
