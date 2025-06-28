This project is a simple Android user management application built using Kotlin and Jetpack Compose, utilizing the Room Persistence Library for local data storage. The project demonstrates best practices in Android development, including MVVM architecture, UI state management, local data handling, and multi-language and dark mode support.

Features

•
Add New Users: Enter user details such as name, age, job title, and gender.

•
View User List: Display all stored users in a scrollable list.

•
Edit User Details: Update information for existing users.

•
Delete Users: Delete individual users or all users.

•
Multi-language Support: The application automatically adapts to the device's language (English and Arabic).

•
Automatic Dark/Light Mode: The application's appearance adapts to the system's dark or light mode.

•
Unit and UI Tests: Includes comprehensive tests to ensure code quality and correct behavior.

Technologies Used

•
Kotlin: The primary programming language.

•
Jetpack Compose: A modern toolkit for building native Android UI.

•
Room Persistence Library: An abstraction layer over SQLite to facilitate local database operations.

•
ViewModel: For managing UI-related data in a lifecycle-aware manner.

•
LiveData / StateFlow: For observing data changes and automatically updating the UI.

•
Kotlin Coroutines: For managing asynchronous operations.

•
DataStore Preferences: For storing application settings (e.g., language).

•
Mockito: For unit testing.

•
Jetpack Compose Testing: For UI testing.

Project Structure

The project follows the MVVM (Model-View-ViewModel) architecture to ensure separation of concerns, testability, and maintainability. The main structure consists of:

•
data: Contains data models (Entities), Data Access Objects (DAOs), and the database class.

•
view: Contains UI screens (Composables).

•
viewmodel: Contains the ViewModel that handles business logic and provides data to the UI.

•
util / manager: Contains helper classes for managing settings and language.

•
test / androidTest: Contains unit tests and UI tests.

