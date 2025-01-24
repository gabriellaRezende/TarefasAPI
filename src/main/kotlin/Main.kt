package com.tarefasapi

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.database.FirebaseDatabase
import com.tarefasapi.com.tarefasapi.routes.projetosRoutes
import com.tarefasapi.com.tarefasapi.routes.tarefasRoutes
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json
import java.io.File

fun configureFirebase() {
    try {
        val serviceAccount = File("src/main/resources/tarefasapi-firebase-adminsdk-fbsvc-de473a0534.json").inputStream()

        val options = FirebaseOptions.builder()
        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
        .setDatabaseUrl("https://tarefasapi-default-rtdb.europe-west1.firebasedatabase.app/")
        .build()

        FirebaseApp.initializeApp(options)
        println("Firebase is ready")
    } catch (e: Exception) {
        println("Error: ${e.localizedMessage}")
    throw e
    }
}

fun testFirebaseConection() {
    try {
        val database = FirebaseDatabase.getInstance().reference
        val testRef = database.child("test")

        testRef.setValueAsync("Conexão funcionando").get()
        println("Conexão com firebase bem sucedida!!")
    } catch (e: Exception) {
        println("Error ao conectar o firebase: ${e.message}")
    }
}

fun main() {
    // Configurar Firebase
    configureFirebase()
    testFirebaseConection()


    // Iniciar o servidor Ktor
    embeddedServer(Netty, port = 8080) {
        install(ContentNegotiation) {
            json(
                Json{
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
                allowStructuredMapKeys = true
            })
        }
        routing {
            tarefasRoutes()
            projetosRoutes()
        }

    }.start(wait = true)

    println("Servidor Ktor rodando em http://localhost:8080")
}
