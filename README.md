# User Management App
A sample Android application demonstrating modern Android development practices. The app allows users to create, view, update, and delete user profiles, 
with all data saved locally on the device. It is built entirely with Jetpack Compose and follows the MVVM architecture pattern.

## ✨ Features
* **Create Users:** An input screen to enter user details including name, age, job title, and gender.
* **View All Users:** A clean, scrollable list that displays all saved users.
* **Edit User Details:** Click on any user in the list to navigate to a details screen where their information can be updated.
* **Delete Users:**
    * Delete a single user with a long-press gesture on the user card (with a confirmation dialog).
    * Delete all users at once with a dedicated button (also with a confirmation dialog).
* **Data Persistence:** User data is saved in a local Room database, so information is retained even after the app is closed.
* **Multi-Language Support:** The app is fully localized for both English and Arabic, and can switch languages dynamically.
* **Responsive & Modern UI:** The entire user interface is built with Jetpack Compose, using Material 3 design principles.

  This project follows modern Android architecture guidelines and uses a collection of popular libraries.

* **UI:** Jetpack Compose
* **Architecture:** Model-View-ViewModel (MVVM)
* **Asynchronous Programming:** Kotlin Coroutines
* **Database:** Room Persistence Library for local storage.
* **Navigation:** Jetpack Navigation for Compose to handle screen transitions.
* **State Management:** `ViewModel`, `StateFlow`, `collectAsState`, and `mutableStateOf` are used to manage UI state in a reactive way.
* **Dependency Injection:** Manual Dependency Injection is used for simplicity to provide the `ViewModel` with its dependencies (`UserDao`, `SettingsManager`).
