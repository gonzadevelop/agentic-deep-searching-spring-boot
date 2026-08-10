package gonzadev.agenticdeepsearch.service;

import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;
import com.resend.services.emails.model.CreateEmailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResendService {

    // Dominio anteriormente configurado en Resend.
    @Value("${DOMAIN_NAME}")
    private String domainName;

    private final Resend resend;

    public void sendEmail(String to, String subject, String htmlContent) throws ResendException {

        String name = "Gonza";
        CreateEmailOptions params = CreateEmailOptions.builder()
                .from("%s <%s@%s>".formatted(name, name.toLowerCase(), domainName))
                .to(to)
                .subject(subject)
                .html(htmlContent)
                .build();

        CreateEmailResponse response = resend
            .emails()
            .send(params);
    }
}
