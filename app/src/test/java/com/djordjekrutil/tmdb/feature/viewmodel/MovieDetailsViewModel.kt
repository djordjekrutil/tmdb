//import androidx.lifecycle.SavedStateHandle
//import com.djordjekrutil.tmdb.core.functional.Either
//import com.djordjekrutil.tmdb.feature.model.Genre
//import com.djordjekrutil.tmdb.feature.model.MovieDetails
//import com.djordjekrutil.tmdb.feature.model.ProductionCompany
//import com.djordjekrutil.tmdb.feature.model.ProductionCountry
//import com.djordjekrutil.tmdb.feature.model.SpokenLanguage
//import com.djordjekrutil.tmdb.feature.usecase.GetMovieDetailsUseCase
//import com.djordjekrutil.tmdb.feature.viewmodel.MovieDetailsState
//import com.djordjekrutil.tmdb.feature.viewmodel.MovieDetailsViewModel
//import kotlinx.coroutines.flow.first
//import kotlinx.coroutines.test.runTest
//import org.junit.Assert.assertEquals
//import org.junit.Before
//import org.junit.Test
//import org.mockito.kotlin.mock
//import org.mockito.kotlin.whenever
//
//class MovieDetailsViewModelTest {
//
//    private lateinit var viewModel: MovieDetailsViewModel
//    private val mockGetMovieDetailsUseCase: GetMovieDetailsUseCase = mock()
//    private val savedStateHandle = SavedStateHandle(mapOf("movieId" to 1)) // Mock movieId
//
//    @Before
//    fun setup() {
//        viewModel = MovieDetailsViewModel(savedStateHandle, mockGetMovieDetailsUseCase)
//    }
//
//    @Test
//    fun `getMovieDetails updates state to Content on success`() = runTest {
//        val mockMovieDetails = MovieDetails(
//            id = 1,
//            title = "Inception",
//            overview = "A mind-bending thriller",
//            releaseDate = "2010-07-16",
//            voteAverage = 8.8,
//            runtime = 148,
//            genres = listOf(Genre(1, "Action"), Genre(2, "Sci-Fi")), // Mora biti lista Genre objekata
//            adult = false,
//            backdropPath = "/backdrop.jpg",
//            belongsToCollection = "Inception Collection",
//            budget = 160000000,
//            homepage = "https://www.inceptionmovie.com",
//            imdbId = "tt1375666",
//            originalLanguage = "en",
//            originalTitle = "Inception",
//            popularity = 95.5,
//            posterPath = "/poster.jpg",
//            productionCompanies = listOf(ProductionCompany(1, "/d8Ryb8AunYAuycVKDp5HpdWPKgC.jpg", "Warner Bros. Pictures", "USA")),
//            productionCountries = listOf(ProductionCountry("US", "United States")),
//            revenue = 829895144,
//            spokenLanguages = listOf(SpokenLanguage("English","en", "English")),
//            status = "Released",
//            tagline = "Your mind is the scene of the crime.",
//            video = false,
//            voteCount = 29149
//        )
//        whenever(mockGetMovieDetailsUseCase.invoke(1)).thenAnswer { invocation ->
//            val callback = invocation.arguments[1] as (Either<Any, MovieDetails>) -> Unit
//            callback(Either.Right(mockMovieDetails))
//        }
//
//        viewModel.getMovieDetails(1)
//
//        val state = viewModel.state.first()
//        assert(state is MovieDetailsState.Content)
//        assertEquals((state as MovieDetailsState.Content).movieDetails, mockMovieDetails)
//    }
//
//    @Test
//    fun `getMovieDetails updates state to Error on failure`() = runTest {
//        whenever(mockGetMovieDetailsUseCase.invoke(1)).thenAnswer { invocation ->
//            val callback = invocation.arguments[1] as (Either<Any, MovieDetails>) -> Unit
//            callback(Either.Left("Network Error"))
//        }
//
//        viewModel.getMovieDetails(1)
//
//        val state = viewModel.state.first()
//        assert(state is MovieDetailsState.Error)
//        assertEquals((state as MovieDetailsState.Error).message, "Failed to fetch movie details!!!")
//    }
//}
