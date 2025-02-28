package pro.qyoga.clients

import io.restassured.http.Cookie
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.springframework.http.HttpStatus
import pro.qyoga.clients.api.TherapistClientsApi
import pro.qyoga.clients.api.TherapistExercisesApi
import pro.qyoga.fixture.THE_THERAPIST_ID
import pro.qyoga.fixture.THE_THERAPIST_LOGIN
import pro.qyoga.fixture.THE_THERAPIST_PASSWORD


class TherapistClient(val id: Long, val authCookie: Cookie) {

    val clients = TherapistClientsApi(authCookie)
    val exercises = TherapistExercisesApi(authCookie)

    fun getIndexPage(): Document {
        return Given {
            cookie(authCookie)
        } When {
            get()
        } Then {
            statusCode(HttpStatus.OK.value())
        } Extract {
            Jsoup.parse(body().asString())
        }
    }

    companion object {

        fun loginAsTheTherapist(): TherapistClient =
            TherapistClient(
                THE_THERAPIST_ID,
                PublicClient.authApi.login(THE_THERAPIST_LOGIN, THE_THERAPIST_PASSWORD)
            )

    }

}