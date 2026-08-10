package gonzadev.agenticdeepsearch.ai.tools;

import com.resend.core.exception.ResendException;
import gonzadev.agenticdeepsearch.service.ResendService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailPreparationTools {

    private final ChatClient subjectWriterAgent;
    private final ChatClient htmlConverterAgent;
    private final ResendService resendService;

    @Tool(
            name = "subjectWriter",
            description = "Genera 5 opciones de asunto atractivos para un correo de ventas a partir del texto plano proporcionado."
    )
    public String generateSubjectOptions(String rawEmailBody) {
        log.info("[EmailPreparationTools] - Generando opciones de asunto para el correo...");
        return subjectWriterAgent.prompt()
                .user(rawEmailBody)
                .call()
                .content();
    }

    @Tool(
            name = "htmlConverter",
            description = "Convierte el texto plano del correo en un formato HTML atractivo y profesional, listo para ser enviado."
    )
    public String convertToHtml(String rawEmailBody) {
        log.info("[EmailPreparationTools] - Convirtiendo el contenido del correo a formato HTML...");
        return htmlConverterAgent.prompt()
                .user(rawEmailBody)
                .call()
                .content();
    }

    @Tool(
            name = "sendEmail",
            description = """
                        Herramienta de infraestructura encargada de realizar el envío técnico del correo electrónico a través del servidor SMTP.\n
                        Recibe como parámetros obligatorios el asunto seleccionado ('subject') y el contenido maquetado ('htmlBody'). Realiza el envío al destinatario correspondiente y devuelve la confirmación del estado de la transacción (éxito o error).
                    """
    )
    public String sendEmail(String subject, String htmlContent) throws ResendException {
        log.info("[EmailPreparationTools] - Enviando el correo electrónico...");
        // Cambia esto por el email real al que deseas enviar los correos
        String email = "exaple@gmail.com";
        resendService.sendEmail(email, subject, htmlContent);
        return "Email enviado con éxito con asunto: " + subject + " y contenido HTML: " + htmlContent;
    }
}
