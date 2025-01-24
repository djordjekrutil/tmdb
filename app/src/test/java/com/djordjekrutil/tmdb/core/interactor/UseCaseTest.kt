//package com.djordjekrutil.tmdb.core.interactor
//
//import com.djordjekrutil.tmdb.core.exception.Failure
//import com.djordjekrutil.tmdb.core.functional.Either
//import com.djordjekrutil.tmdb.core.interactor.UseCase
//import io.mockk.coVerify
//import io.mockk.mockk
//import io.mockk.slot
//import kotlinx.coroutines.ExperimentalCoroutinesApi
//import kotlinx.coroutines.test.runTest
//import org.junit.Assert.assertEquals
//import org.junit.Before
//import org.junit.Test
//
//@OptIn(ExperimentalCoroutinesApi::class)
//class UseCaseTest {
//
//    private lateinit var useCase: TestUseCase
//
//    @Before
//    fun setup() {
//        useCase = TestUseCase()
//    }
//
//    @Test
//    fun `invoke calls run with correct parameters`() = runTest {
//        val params = "Test Params"
//        val onResult: (Either<Failure, String>) -> Unit = mockk(relaxed = true)
//
//        useCase(params, onResult)
//
//        coVerify { onResult(Either.Right("Result for $params")) }
//    }
//
//    @Test
//    fun `invoke handles failure correctly`() = runTest {
//        val params = "Fail Params"
//        val onResult: (Either<Failure, String>) -> Unit = mockk(relaxed = true)
//
//        useCase(params, onResult)
//
//        coVerify { onResult(Either.Left(Failure.ServerError)) }
//    }
//}
//
//private class TestUseCase : UseCase<String, String>() {
//    override suspend fun run(params: String): Either<Failure, String> {
//        return if (params == "Fail Params") {
//            Either.Left(Failure.ServerError)
//        } else {
//            Either.Right("Result for $params")
//        }
//    }
//}
