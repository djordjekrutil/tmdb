//import com.djordjekrutil.tmdb.core.functional.Either
//import com.djordjekrutil.tmdb.core.interactor.UseCase
//import com.djordjekrutil.tmdb.feature.model.Movie
//import com.djordjekrutil.tmdb.feature.model.MoviesResponse
//import com.djordjekrutil.tmdb.feature.usecase.GetPopularMoviesUseCase
//import com.djordjekrutil.tmdb.feature.usecase.SearchMovieUseCase
//import com.djordjekrutil.tmdb.feature.viewmodel.HomeScreenState
//import com.djordjekrutil.tmdb.feature.viewmodel.HomeViewModel
//import kotlinx.coroutines.flow.first
//import kotlinx.coroutines.test.runTest
//import org.junit.Assert.assertEquals
//import org.junit.Before
//import org.junit.Test
//import org.mockito.kotlin.*
//
//class HomeViewModelTest {
//
//    private lateinit var viewModel: HomeViewModel
//    private val mockGetPopularMoviesUseCase: GetPopularMoviesUseCase = mock()
//    private val mockSearchMovieUseCase: SearchMovieUseCase = mock()
//
//    @Before
//    fun setup() {
//        viewModel = HomeViewModel(mockGetPopularMoviesUseCase, mockSearchMovieUseCase)
//    }
//
//    @Test
//    fun `fetchPopularMovies updates state to Content on success`() = runTest {
//        val dummyMovie = Movie(
//            adult = false,
//            backdropPath = "/dummyBackdrop.jpg",
//            genreIds = listOf(28, 12, 16),
//            id = 1,
//            originalLanguage = "en",
//            originalTitle = "Dummy Original Title",
//            overview = "This is a dummy movie overview used for testing purposes.",
//            popularity = 123.45,
//            posterPath = "/dummyPoster.jpg",
//            releaseDate = "2025-01-01",
//            title = "Dummy Movie",
//            video = false,
//            voteAverage = 8.5,
//            voteCount = 1000
//        )
//
//        val mockMovies = listOf(dummyMovie)
//        val mockResponse =
//            MoviesResponse(results = mockMovies, page = 1, totalPages = 100, totalResults = 2000)
//
//        whenever(mockGetPopularMoviesUseCase.invoke(eq(UseCase.None()), any()))
//            .thenAnswer { invocation ->
//                val callback = invocation.arguments[1] as (Either<Any, MoviesResponse>) -> Unit
//                callback(Either.Right(mockResponse))
//            }
//
//        viewModel.fetchPopularMovies()
//
//        val state = viewModel.screenState.first()
//        assert(state is HomeScreenState.Content)
//        assertEquals((state as HomeScreenState.Content).movies, mockMovies)
//    }
//
//    @Test
//    fun `fetchPopularMovies updates state to Error on failure`() = runTest {
//        // Arrange: Mock failure response
//        whenever(mockGetPopularMoviesUseCase.invoke(eq(UseCase.None()), any()))
//            .thenAnswer { invocation ->
//                val callback = invocation.arguments[1] as (Either<Any, MoviesResponse>) -> Unit
//                callback(Either.Left("Network Error"))
//            }
//
//        // Act: Call the method
//        viewModel.fetchPopularMovies()
//
//        // Assert: Check if state is Error
//        val state = viewModel.screenState.first()
//        assert(state is HomeScreenState.Error)
//        assertEquals(
//            "Failed to fetch popular movies: Network Error",
//            (state as HomeScreenState.Error).message
//        )
//    }
//
//    @Test
//    fun `onSearchQueryChanged updates searchQuery`() = runTest {
//        val query = "Inception"
//        viewModel.onSearchQueryChanged(query)
//
//        val currentQuery = viewModel.searchQuery.first()
//        assertEquals(query, currentQuery)
//    }
//
//    @Test
//    fun `searchMovies updates searchResults on success`() = runTest {
//        val dummyMovie = Movie(
//            adult = false,
//            backdropPath = "/dummyBackdrop.jpg",
//            genreIds = listOf(28, 12, 16),
//            id = 1,
//            originalLanguage = "en",
//            originalTitle = "Dummy Original Title",
//            overview = "This is a dummy movie overview used for testing purposes.",
//            popularity = 123.45,
//            posterPath = "/dummyPoster.jpg",
//            releaseDate = "2025-01-01",
//            title = "Dummy Movie",
//            video = false,
//            voteAverage = 8.5,
//            voteCount = 1000
//        )
//        val mockMovies = listOf(dummyMovie)
//        val mockResponse =
//            MoviesResponse(results = mockMovies, page = 1, totalPages = 100, totalResults = 2000)
//
//        whenever(mockSearchMovieUseCase.invoke(any(), any()))
//            .thenAnswer { invocation ->
//                val callback = invocation.arguments[1] as (Either<Any, MoviesResponse>) -> Unit
//                callback(Either.Right(mockResponse))
//            }
//
//        viewModel.searchMovies("Inception")
//
//        val results = viewModel.searchResults.first()
//        assertEquals(mockMovies, results)
//    }
//
//    @Test
//    fun `searchMovies clears searchResults on failure`() = runTest {
//        whenever(mockSearchMovieUseCase.invoke(any(), any()))
//            .thenAnswer { invocation ->
//                val callback = invocation.arguments[1] as (Either<Any, MoviesResponse>) -> Unit
//                callback(Either.Left("Search Error"))
//            }
//
//        viewModel.searchMovies("Nonexistent Movie")
//
//        val results = viewModel.searchResults.first()
//        assertEquals(emptyList<Movie>(), results)
//    }
//}
