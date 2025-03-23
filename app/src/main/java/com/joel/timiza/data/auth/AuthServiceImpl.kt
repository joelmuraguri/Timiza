package com.joel.timiza.data.auth

import android.accounts.AuthenticatorException
import android.content.Context
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.joel.timiza.R
import com.joel.timiza.domain.models.User
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.Google
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.auth.providers.builtin.IDToken
import io.github.jan.supabase.exceptions.RestException
import io.ktor.http.parsing.ParseException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AuthServiceImpl @Inject constructor(
    private val supabase : SupabaseClient,
    private val context: Context
) : AuthService {


    override val currentUserId: String
        get() = supabase.auth.currentSessionOrNull()?.user?.id ?: ""

    override val hasUser: Boolean
        get() = supabase.auth.currentSessionOrNull()?.user != null

    override val currentUser: Flow<User> = flow {
        val session = supabase.auth.currentSessionOrNull()
        val userData = session?.user

        if (userData != null) {
            val user = User(
                uid = userData.id,
                email = userData.email ?: "",
                name = userData.userMetadata ?.get("full_name") as? String ?: "Unknown"
            )
            emit(user)
        } else {
            emit(User(uid = "", email = "", name = ""))
        }
    }.flowOn(Dispatchers.IO)




    override suspend fun register(
        emailValue: String,
        passwordValue: String,
        name: String
    ): Flow<AuthResponse> = flow{
        try {
            supabase.auth.signUpWith(Email) {
                email = emailValue
                password = passwordValue
            }

            emit(AuthResponse.Success)
        } catch (e: ParseException) {
            emit(AuthResponse.Error(e.localizedMessage))
        }catch (e: RestException) {
            emit(AuthResponse.Error(e.localizedMessage))
        }catch (e: AuthenticatorException) {
            emit(AuthResponse.Error(e.localizedMessage))
        }catch (e: Exception) {
            emit(AuthResponse.Error(e.localizedMessage))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun googleSignIn(): Flow<AuthResponse> = flow{
        val googleIdOption : GetSignInWithGoogleOption = GetSignInWithGoogleOption
            .Builder(context.getString(R.string.web_client_id))
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        val credentialManager = CredentialManager.create(context)

        try {
            val result = credentialManager.getCredential(
                context = context,
                request = request
            )

            val googleIdTokenCredential = GoogleIdTokenCredential
                .createFrom(result.credential.data)

            val googleIdToken = googleIdTokenCredential.idToken

            supabase.auth.signInWith(IDToken) {
                idToken = googleIdToken
                provider = Google
            }

            emit(AuthResponse.Success)

        } catch (e: Exception) {
            Log.e("google", e.localizedMessage)
            emit(AuthResponse.Error(e.localizedMessage))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun signOut(): Flow<AuthResponse> = flow{
        try {
            supabase.auth.signOut()
            emit(AuthResponse.Success)
        } catch (e: ParseException) {
            emit(AuthResponse.Error(e.localizedMessage))
        }catch (e: RestException) {
            emit(AuthResponse.Error(e.localizedMessage))
        }catch (e: AuthenticatorException) {
            emit(AuthResponse.Error(e.localizedMessage))
        }catch (e: Exception) {
            emit(AuthResponse.Error(e.localizedMessage))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun signIn(emailValue: String, passwordValue: String): Flow<AuthResponse>  = flow{
        try {
            supabase.auth.signInWith(Email) {
                email = emailValue
                password = passwordValue
            }
            emit(AuthResponse.Success)
        } catch (e: ParseException) {
            emit(AuthResponse.Error(e.localizedMessage))
        }catch (e: RestException) {
            emit(AuthResponse.Error(e.localizedMessage))
        }catch (e: AuthenticatorException) {
            emit(AuthResponse.Error(e.localizedMessage))
        }catch (e: Exception) {
            emit(AuthResponse.Error(e.localizedMessage))
        }
    }.flowOn(Dispatchers.IO)
}