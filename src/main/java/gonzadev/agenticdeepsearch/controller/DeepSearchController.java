package gonzadev.agenticdeepsearch.controller;

import gonzadev.agenticdeepsearch.service.DeepSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/deep-search")
public class DeepSearchController {

    private final DeepSearchService deepSearchService;

    @PostMapping("/search")
    public ResponseEntity<String> evaluateSearchQuality(@RequestBody String prompt) {
        return ResponseEntity.ok().body(deepSearchService.evaluateSearchQuality(prompt));
    }
}
