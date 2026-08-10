# Agentic Deep Search API

Sistema orquestado multi-agente construido con **Java 21**, **Spring Boot 4.x.x** y **Spring AI**. La aplicación realiza investigación profunda (*Deep Search*) en la web, evalúa la calidad de los prompts, sintetiza informes estructurados en JSON y maqueta/envía los hallazgos por correo electrónico de forma automatizada.

---

## 🏛️ Arquitectura del Sistema Multi-Agente

El flujo del sistema sigue un patrón de delegación jerárquico mediante **Tool Calling** y **Guardarraíles de Seguridad**:

```text
[ Cliente HTTP (POST /deep-search/search) ]
                    │
                    ▼
          QualityEvaluatorAgent
                    │
   ├──> [ InputGuardrailAdvisor ] ──> (Evalúa seguridad con GuardrailAgent)
   │
   └──> [ QualityEvaluatorTools ]
             ├──> ClarificationAgent (Clarifica consulta si es ambigua)
             └──> SearchingManagerAgent
                        │
                        └──> [ SearchingManagerTools ]
                                  ├──> QueryExpansionAgent (Expande términos de búsqueda)
                                  ├──> WebSearchAgent (OpenAI Java SDK + Web Search)
                                  ├──> ReportGeneratorAgent (Sintetiza DTO JSON)
                                  └──> EmailPreparationAgent
                                            │
                                            └──> [ EmailPreparationTools ]
                                                      ├──> SubjectWriterAgent (5 Asuntos)
                                                      ├──> HtmlConverterAgent (Texto a HTML)
                                                      └──> ResendService (Envío final de Email)
```

---

## 🛠️ Tecnologías Utilizadas

* **Java 21** & **Spring Boot 4.x.x**
* **Spring AI** (Orquestación de agentes, `ChatClient`, `CallAdvisor` y `@Tool`)
* **OpenAI Java SDK** (`com.openai:openai-java` para búsquedas web nativas)
* **Resend Java SDK** (Infraestructura de envío transaccional de emails)
* **Lombok** & **Jackson**

---

## 💡 Componentes Clave de Implementación

* **InputGuardrailAdvisor (`CallAdvisor`):** Interceptor de seguridad con prioridad `Ordered.HIGHEST_PRECEDENCE`. Delega la revisión del prompt a un `guardrailAgent` secundario y bloquea entradas maliciosas antes de llamar a las herramientas principales.
* **Integración Híbrida de OpenAI:** Combina la abstracción agnóstica de Spring AI para la orquestación general con el SDK nativo de OpenAI (`OpenAIOkHttpClient`) para habilitar la herramienta alojada `WebSearchTool`.
* **Mapeo DTO Transparente:** El `ReportGeneratorAgent` mapea directamente los hallazgos a una instancia de `ReportDataDTO` aprovechando anotaciones `@JsonPropertyDescription`, garantizando tipado fuerte entre agentes.

---

## ⚙️ Configuración e Instalación

### 1. Requisitos Previos

* JDK 21 o superior.
* Maven 4.0+.
* Cuenta activa en OpenAI y Resend.

### 2. Variables de Entorno

Asegúrate de exportar las siguientes variables en tu sistema o agregarlas en tu `application.properties`:

```properties
# Claves de API externas
OPENAI_API_KEY=tu_openai_api_key
RESEND_API_KEY=tu_resend_api_key

# Dominio verificado en Resend
DOMAIN_NAME=tu-dominio-verificado.com
```

### 3. Ejecución del Proyecto

```bash
# Clona el repositorio
git clone [https://github.com/tu-usuario/agentic-deep-search.git](https://github.com/tu-usuario/agentic-deep-search.git)
cd agentic-deep-search

# Compila y ejecuta la aplicación
./mvnw spring-boot:run
```

---

## 📬 Ejemplo de Uso (API REST)

**Endpoint:** `POST /deep-search/search`  
**Content-Type:** `text/plain` o `application/json`

### Petición
```bash
curl -X POST http://localhost:8080/deep-search/search \
  -H "Content-Type: text/plain" \
  -d "Realiza una investigación sobre los últimos avances de computación cuántica en 2026 y envíame un resumen por correo."
```

### Flujo de Ejecución Interno
1. **Seguridad:** `InputGuardrailAdvisor` valida que la consulta sea segura.
2. **Evaluación y Expansión:** `QualityEvaluatorAgent` y `SearchingManagerAgent` refinan los términos de búsqueda.
3. **Búsqueda Web:** `WebSearchAgent` ejecuta la consulta en la web en tiempo real.
4. **Sintetización:** `ReportGeneratorAgent` devuelve el informe formateado como `ReportDataDTO`.
5. **Maquetación y Envío:** `EmailPreparationAgent` genera el asunto, convierte el cuerpo a HTML y dispara el correo mediante `ResendService`.