import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.madartask.data.User
import com.example.madartask.data.UserDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlinx.coroutines.flow.flowOf

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var mockDao: UserDao
    private lateinit var mockSettingsManager: SettingsManager
    private lateinit var viewModel: MainViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        mockDao = mock()
        mockSettingsManager = mock()

        // When getAllUsers() is called, return an empty flow to avoid issues
        whenever(mockDao.getAllUsers()).thenReturn(flowOf(emptyList()))
        whenever(mockSettingsManager.languageFlow).thenReturn(flowOf("en"))

        viewModel = MainViewModel(mockDao, mockSettingsManager)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `addUser calls insertUser on DAO when name and age are not blank`() = runTest {
        // Arrange
        viewModel.name.value = "John Doe"
        viewModel.age.value = "30"
        viewModel.jobTitle.value = "Developer"
        viewModel.gender.value = "Male"
        val expectedUser = User(name = "John Doe", age = 30, jobTitle = "Developer", gender = "Male")

        // Act
        viewModel.addUser()

        // **THE FIX**: Advance the dispatcher to execute the coroutine
        advanceUntilIdle()

        // Assert
        verify(mockDao).insertUser(expectedUser)
    }

    @Test
    fun `deleteUser calls deleteUser on DAO`() = runTest {
        // Arrange
        val testUser = User(id = 1, name = "Test User", age = 25, jobTitle = "Tester", gender = "Female")

        // Act
        viewModel.deleteUser(testUser)

        // **THE FIX**: Advance the dispatcher to execute the coroutine
        advanceUntilIdle()

        // Assert
        verify(mockDao).deleteUser(testUser)
    }
}