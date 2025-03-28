package mcpagentspringai

import io.modelcontextprotocol.client.McpClient
import io.modelcontextprotocol.client.transport.HttpClientSseClientTransport
import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class Application {
    @Bean
    fun chatClient(@Value("\${mcp-service.url:http://localhost:8081}") url: String, builder: ChatClient.Builder): ChatClient {
        val mcpClient = McpClient
            .sync(HttpClientSseClientTransport(url))
            .build()
        mcpClient.initialize()

        return builder
            .defaultTools(SyncMcpToolCallbackProvider(mcpClient))
            .build()
    }
}

@RestController
class ConversationalController(val chatClient: ChatClient) {

    @PostMapping("/inquire")
    fun inquire(@RequestParam question: String): String =
        chatClient
                .prompt()
                .user(question)
                .call()
                .content() ?: "Please try again later."
}


fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
