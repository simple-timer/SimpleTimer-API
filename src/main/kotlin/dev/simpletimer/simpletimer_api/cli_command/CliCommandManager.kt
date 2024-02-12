package dev.simpletimer.simpletimer_api.cli_command

import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import java.util.*

/**
 * 標準入力に応答を行います。
 *
 */
@Component
class CliCommandManager : CommandLineRunner {
    override fun run(vararg args: String?) {
        var input: String?
        val scanner = Scanner(System.`in`)
        while (scanner.hasNextLine()) {
            input = scanner.nextLine() ?: continue

            val parts = input.split(" ")
            if (parts.isEmpty()) continue

            when (parts[0]) {
                "token" -> {
                    TokenCommand.run(*parts.toTypedArray())
                }

                else -> {
                    println("Missing command.")
                }
            }
        }
    }
}