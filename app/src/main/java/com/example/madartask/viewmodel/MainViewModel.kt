import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.madartask.data.User
import com.example.madartask.data.UserDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val dao: UserDao,
    private val settingsManager: SettingsManager
) : ViewModel() {
    val name = mutableStateOf("")
    val age = mutableStateOf("")
    val jobTitle = mutableStateOf("")
    val gender = mutableStateOf("")

    val allUsers: StateFlow<List<User>> = dao.getAllUsers()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    private val _selectedUser = MutableStateFlow<User?>(null)
    val selectedUser: StateFlow<User?> = _selectedUser

    fun addUser() {
        if (name.value.isNotBlank() && age.value.isNotBlank()) {
            viewModelScope.launch {
                dao.insertUser(User(
                    name = name.value,
                    age = age.value.toIntOrNull() ?: 0,
                    jobTitle = jobTitle.value,
                    gender = gender.value
                ))
                name.value = ""
                age.value = ""
                jobTitle.value = ""
                gender.value = ""
            }
        }
    }

    fun deleteUser(user: User) {
        viewModelScope.launch {
            dao.deleteUser(user)
        }
    }

    fun deleteAllUsers() {
        viewModelScope.launch {
            dao.deleteAllUsers()
        }
    }
    fun updateUser(user: User) {
        viewModelScope.launch {
            dao.updateUser(user)
        }
    }

    fun getUserById(id: Int) {
        viewModelScope.launch {
            val user = withContext(Dispatchers.IO) {
                dao.getUserById(id)
            }
            _selectedUser.value = user
        }
    }


    val currentLanguage: StateFlow<String> = settingsManager.languageFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "en")

    fun changeLanguage(languageCode: String) {
        viewModelScope.launch {
            settingsManager.saveLanguage(languageCode)
        }
    }
}