package gonzadev.agenticdeepsearch.ai.agents;

import gonzadev.agenticdeepsearch.ai.utils.PromptLoader;
import gonzadev.agenticdeepsearch.ai.tools.SearchingManagerTools;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SearchingManagerAgent {

    private final ChatClient chatClient;
    private final SearchingManagerTools searchingManagerTools;

    public String search(String query) {
        log.info("[SearchingManagerAgent] - Realizando búsqueda en la web para obtener información relevante...");
        return chatClient
                .prompt()
                .system(PromptLoader.loadPrompt("SearchingManagerAgent.txt"))
                .tools(searchingManagerTools)
                .user(query)
                .call()
                .content();
    }
}
