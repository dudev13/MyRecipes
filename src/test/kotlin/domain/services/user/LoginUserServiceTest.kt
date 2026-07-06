package com.br.domain.services.user

import com.br.application.payloads.requests.AuthUserRequestFactory
import com.br.application.payloads.responses.TokenResponseFactory
import com.br.domain.model.UserFactory
import com.br.domain.model.UserFactory.UserFake
import com.br.domain.services.password.BCryptPasswordService
import com.br.domain.services.token.TokenService
import com.br.domain.validations.AuthUserRequestValidation
import com.br.fakeUtil.Constants
import com.br.infra.repository.user.UserReadOnlyRepository
import com.br.utils.ErrorCodes
import com.br.utils.SuccessCodes
import com.google.common.truth.Truth
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

class LoginUserServiceTest {


    private lateinit var tokenService: TokenService
    private lateinit var bCryptPasswordService: BCryptPasswordService
    private lateinit var authUserRequestValidation: AuthUserRequestValidation
    private lateinit var userReadOnlyRepository: UserReadOnlyRepository

    private lateinit var  loginUserService: LoginUserService


    //FACTORY
    private val tokenResponseFactory = TokenResponseFactory().create(
        successful = true,
        message = SuccessCodes.VALID_REGISTRATION.message,
        token = Constants.FAKE_TOKEN
    )

    private val UserEdu = UserFactory().create(UserFake.Edu)

    private val authUserRequestFactory = AuthUserRequestFactory().create(
        email = UserEdu.email,
        password = UserEdu.password
    )

    @BeforeTest
    fun setup(){
        tokenService = mockk()
        bCryptPasswordService = mockk()
        authUserRequestValidation = mockk()
        userReadOnlyRepository = mockk()

        loginUserService = LoginUserService(
            tokenService,
            bCryptPasswordService,
            authUserRequestValidation,
            userReadOnlyRepository
        )
    }

    @AfterTest
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `should return a successful tokenResponse with the generated token`() = runBlocking {

        //GIVEN
        val token = Constants.FAKE_TOKEN

        coEvery { authUserRequestValidation.validator(any()) } returns tokenResponseFactory
        coEvery { userReadOnlyRepository.checkIfUserExistsReturn(any()) } returns UserEdu

        every { bCryptPasswordService.verifyPassword(any(), any()) } returns true
        every { tokenService.generateToken(any()) } returns token

        //WHEN
        val result = loginUserService.loginUser(authUserRequestFactory)

        //THEN
        Truth.assertThat(result.token).isNotEmpty()
        Truth.assertThat(result.successful).isTrue()
    }

    @Test
    fun `should return user email not found message response`() = runBlocking {

        //GIVEN

        coEvery { authUserRequestValidation.validator(any()) } returns tokenResponseFactory
        coEvery { userReadOnlyRepository.checkIfUserExistsReturn(any()) } returns null


        //WHEN
        val result = loginUserService.loginUser(authUserRequestFactory)

        //THEN
        Truth.assertThat(result.token).isNull()
        Truth.assertThat(result.successful).isFalse()
        Truth.assertThat(result.message).isEqualTo(ErrorCodes.USER_EMAIL_NOT_FOUND.message)
    }

    @Test
    fun `should return incorrect passowrd response`() = runBlocking {

        //GIVEN

        coEvery { authUserRequestValidation.validator(any()) } returns tokenResponseFactory
        coEvery { userReadOnlyRepository.checkIfUserExistsReturn(any()) } returns UserEdu
        every { bCryptPasswordService.verifyPassword(any(), any()) } returns false


        //WHEN
        val result = loginUserService.loginUser(authUserRequestFactory)

        //THEN
        Truth.assertThat(result.token).isNull()
        Truth.assertThat(result.successful).isFalse()
        Truth.assertThat(result.message).isEqualTo(ErrorCodes.INCORRECT_PASSWORD.message)
    }
}