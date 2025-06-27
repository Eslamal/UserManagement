package com.example.madartask.view

import MainViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.madartask.R
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputScreen(viewModel: MainViewModel, navController: NavController) {
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
            text = stringResource(R.string.enter_user_data),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(Modifier.height(24.dp))
        OutlinedTextField(
            value = viewModel.name.value,
            onValueChange = { viewModel.name.value = it },
            label = { Text(stringResource(R.string.user_name)) },
            modifier = Modifier.fillMaxWidth(),
            shape = fieldAndButtonShape
        )
        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = viewModel.age.value,
            onValueChange = { viewModel.age.value = it },
            label = { Text(stringResource(R.string.age)) },
            modifier = Modifier.fillMaxWidth(),
            shape = fieldAndButtonShape
        )
        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = viewModel.jobTitle.value,
            onValueChange = { viewModel.jobTitle.value = it },
            label = { Text(stringResource(R.string.job_title)) },
            modifier = Modifier.fillMaxWidth(),
            shape = fieldAndButtonShape
        )
        Spacer(Modifier.height(16.dp))

        var expanded by remember { mutableStateOf(false) }
        val genderOptions = listOf(stringResource(R.string.gender_male), stringResource(R.string.gender_female))
        val selectedGender = viewModel.gender.value

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = selectedGender,
                onValueChange = {},
                readOnly = true,
                label = { Text(stringResource(R.string.gender)) },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
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
                            viewModel.gender.value = selectionOption
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(Modifier.height(24.dp))
        Button(
            onClick = { viewModel.addUser() },
            modifier = Modifier.fillMaxWidth(),
            shape = fieldAndButtonShape
        ) {
            Text(stringResource(R.string.save))
        }

        Spacer(Modifier.height(8.dp))
        OutlinedButton(
            onClick = { navController.navigate("display") },
            modifier = Modifier.fillMaxWidth(),
            shape = fieldAndButtonShape
        ) {
            Text(stringResource(R.string.view_users))
        }
    }
}



