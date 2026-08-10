package gonzadev.agenticdeepsearch.ai.advisors;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InputGuardrailAdvisor implements CallAdvisor {

    private final ChatClient guardrailAgent;

    @Override
    public String getName() {
        return "InputGuardrailAdvisor";
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    @Override
    public ChatClientResponse adviseCall(ChatClientRequest request, CallAdvisorChain chain) {
        String messageToReview = request.prompt().getContents();

        Boolean isSafe = guardrailAgent.prompt()
                .user(messageToReview)
                .call()
                .entity(Boolean.class);

        if (Boolean.FALSE.equals(isSafe)) {
            throw new IllegalArgumentException("Input not safe according to guardrail agent.");
        }

        return chain.nextCall(request);
    }
}
