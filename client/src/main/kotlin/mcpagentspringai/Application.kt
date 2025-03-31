package mcpagentspringai

import io.modelcontextprotocol.client.McpClient
import io.modelcontextprotocol.client.transport.HttpClientSseClientTransport
import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController


@SpringBootApplication
@RestController
class Application(val chatClientBuilder: ChatClient.Builder) {

    data class Prompt(val question: String)

    @PostMapping("/inquire")
    fun inquire(@Value("\${mcp-service.url:http://localhost:8081}") url: String, @RequestBody prompt: Prompt): String {

        val mcpClient = McpClient
            .sync(HttpClientSseClientTransport(url))
            .build()
        mcpClient.initialize()

        mcpClient.listTools().tools().forEach { println(it) }

        val chatClient = chatClientBuilder.build()

        return chatClient
            .prompt()
            .tools(SyncMcpToolCallbackProvider(mcpClient))
            .user(prompt.question)
            .call()
            .content() ?: "Please try again later."
    }
}


fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
