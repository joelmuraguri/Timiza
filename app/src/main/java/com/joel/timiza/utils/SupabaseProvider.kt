package com.joel.timiza.utils

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.PropertyConversionMethod
import io.github.jan.supabase.serializer.KotlinXSerializer
import io.ktor.http.headers
import io.ktor.util.appendIfNameAndValueAbsent
import kotlinx.serialization.json.Json

object SupabaseProvider {
    val supabase: SupabaseClient by lazy {
        createSupabaseClient(
            supabaseUrl = "https://twrcjxtzyixblwzvcfyx.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFhcyIsInJlZiI6InR3cmNqeHR6eWl4Ymx3enZjZnl4Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDIyOTY1ODAsImV4cCI6MjA1Nzg3MjU4MH0.WcM0uSahZl6PvhogG_YW6GxwiRvrn6SHXO3T6KQ-isY"
        ) {
            defaultSerializer = KotlinXSerializer(Json)
            install(Auth) { // Explicitly specify AuthConfig
                alwaysAutoRefresh = false // default: true
                autoLoadFromStorage = false // default: true
                headers {
                    appendIfNameAndValueAbsent("apikey", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InR3cmNqeHR6eWl4Ymx3enZjZnl4Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDIyOTY1ODAsImV4cCI6MjA1Nzg3MjU4MH0.WcM0uSahZl6PvhogG_YW6GxwiRvrn6SHXO3T6KQ-isY")
                    appendIfNameAndValueAbsent("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InR3cmNqeHR6eWl4Ymx3enZjZnl4Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDIyOTY1ODAsImV4cCI6MjA1Nzg3MjU4MH0.WcM0uSahZl6PvhogG_YW6GxwiRvrn6SHXO3T6KQ-isY")
                    appendIfNameAndValueAbsent("Content-Type", "application/json")

                }
            }
            install(Postgrest) { // Explicitly specify PostgrestConfig
                defaultSchema = "public" // default: "public"
                propertyConversionMethod = PropertyConversionMethod.SERIAL_NAME // default: PropertyConversionMethod.CAMEL_CASE_TO_SNAKE_CASE
                headers {
                    appendIfNameAndValueAbsent("apikey", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InR3cmNqeHR6eWl4Ymx3enZjZnl4Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDIyOTY1ODAsImV4cCI6MjA1Nzg3MjU4MH0.WcM0uSahZl6PvhogG_YW6GxwiRvrn6SHXO3T6KQ-isY")
                    appendIfNameAndValueAbsent("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InR3cmNqeHR6eWl4Ymx3enZjZnl4Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDIyOTY1ODAsImV4cCI6MjA1Nzg3MjU4MH0.WcM0uSahZl6PvhogG_YW6GxwiRvrn6SHXO3T6KQ-isY")
                    appendIfNameAndValueAbsent("Content-Type", "application/json")
                }
            }
        }
    }
}




