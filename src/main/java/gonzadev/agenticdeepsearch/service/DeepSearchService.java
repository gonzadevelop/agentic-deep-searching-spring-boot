package gonzadev.agenticdeepsearch.service;

import gonzadev.agenticdeepsearch.ai.agents.QualityEvaluatorAgent;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DeepSearchService {

    private final QualityEvaluatorAgent qualityEvaluatorAgent;

    public String evaluateSearchQuality(String prompt) {
        return qualityEvaluatorAgent.evaluateQuality(prompt);
    }
}
