package gonzadev.agenticdeepsearch.ai.agents;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.responses.Response;
import com.openai.models.responses.ResponseCreateParams;
import com.openai.models.responses.ResponseOutputText;
import com.openai.models.responses.WebSearchTool;
import gonzadev.agenticdeepsearch.ai.utils.PromptLoader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class WebSearchAgent {

    private final OpenAIClient client;

    public WebSearchAgent(@Value("${OPENAI_API_KEY}") String apiKey) {
        this.client = OpenAIOkHttpClient.builder()
                .apiKey(apiKey)
                .build();
    }

    public String searchWebStable(String userPrompt) {
        ResponseCreateParams params = ResponseCreateParams.builder()
                .model("gpt-4o-mini")
                .addTool(
                        WebSearchTool
                                .builder()
                                .type(WebSearchTool.Type.WEB_SEARCH)
                                .build()
                )
                .instructions(PromptLoader.loadPrompt("WebSearchAgent.txt"))
                .input(userPrompt)
                .build();

        Response response = client
                .responses()
                .create(params);

        return response.output()
                .stream()
                .flatMap(item -> item.message().stream())
                .flatMap(message -> message.content().stream())
                .flatMap(content -> content.outputText().stream())
                .map(ResponseOutputText::text)
                .collect(Collectors.joining("\n"));
    }
}
