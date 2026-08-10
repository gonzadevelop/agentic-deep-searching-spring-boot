package gonzadev.agenticdeepsearch.ai.config;

import gonzadev.agenticdeepsearch.ai.utils.PromptLoader;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ReportWorkflowConfig {

    private final ChatClient.Builder builder;

    private ChatClient createAgent(String promptFileName) {
        String systemPrompt = PromptLoader.loadPrompt(promptFileName);
        return builder.clone()
                .defaultSystem(systemPrompt)
                .build();
    }

    @Bean("guardrailAgent")
    public ChatClient guardrailAgent() {
        return createAgent("GuardrailAgent.txt");
    }

    @Bean("clarificationAgent")
    public ChatClient clarificationAgent() {
        return createAgent("ClarificationAgent.txt");
    }

    @Bean("queryExpansionAgent")
    public ChatClient queryExpansionAgent() {
        return createAgent("QueryExpansionAgent.txt");
    }

    @Bean("reportGeneratorAgent")
    public ChatClient reportGeneratorAgent() {
        return createAgent("ReportGeneratorAgent.txt");
    }

    @Bean("subjectWriterAgent")
    public ChatClient subjectWriterAgent() {
        return createAgent("SubjectWriterAgent.txt");
    }

    @Bean("htmlConverterAgent")
    public ChatClient htmlConverterAgent() {
        return createAgent("HtmlConverterAgent.txt");
    }
}
