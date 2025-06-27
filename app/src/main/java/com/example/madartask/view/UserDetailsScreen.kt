package com.example.madartask.view

import MainViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.madartask.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailsScreen(
    viewModel: MainViewModel,
    userId: Int,
    navController: NavController
) {
    LaunchedEffect(userId) {
        viewModel.getUserById(userId)
    }

    val user by viewModel.selectedUser.collectAsState()
    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var jobTitle by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }

    LaunchedEffect(user) {
        user?.let {
            name = it.name
            age = it.age.toString()
            jobTitle = it.jobTitle
            gender = it.gender
        }
    }

    val fieldAndButtonShape = RoundedCornerShape(16.dp)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.user_details),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(Modifier.height(24.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text(stringResource(R.string.user_name)) },
            modifier = Modifier.fillMaxWidth(),
            shape = fieldAndButtonShape
        )
        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = age,
            onValueChange = { age = it },
            label = { Text(stringResource(R.string.age)) },
            modifier = Modifier.fillMaxWidth(),
            shape = fieldAndButtonShape
        )
        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = jobTitle,
            onValueChange = { jobTitle = it },
            label = { Text(stringResource(R.string.job_title)) },
            modifier = Modifier.fillMaxWidth(),
            shape = fieldAndButtonShape
        )
        Spacer(Modifier.height(16.dp))
        var expanded by remember { mutableStateOf(false) }
        val genderOptions = listOf(stringResource(R.string.gender_male), stringResource(R.string.gender_female))

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = gender,
                onValueChange = {},
                readOnly = true,
                label = { Text(stringResource(R.string.gender)) },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor().fillMaxWidth(),
                shape = fieldAndButtonShape
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                genderOptions.forEach { selectionOption ->
                    DropdownMenuItem(
                        text = { Text(selectionOption) },
                        onClick = {
                            gender = selectionOption
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = {
                user?.let {
                    val updatedUser = it.copy(
                        name = name,
                        age = age.toIntOrNull() ?: it.age,
                        jobTitle = jobTitle,
                        gender = gender
                    )
                    viewModel.updateUser(updatedUser)
                    navController.popBackStack()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = fieldAndButtonShape
        ) {
            Text(stringResource(R.string.save_changes))
        }
    }
}