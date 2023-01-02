package ru.ifmo.cs.util

import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.ExtensionContext
import ru.ifmo.cs.config.configureDatabase

class DatabaseExtension: BeforeAllCallback {
    override fun beforeAll(context: ExtensionContext?) {
        configureDatabase()
    }
}