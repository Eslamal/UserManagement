package com.example.madartask.view

import MainViewModel
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.madartask.R
import com.example.madartask.data.User

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DisplayScreen(viewModel: MainViewModel, navController: NavController) {
    val users by viewModel.allUsers.collectAsState()
    val showDeleteUserDialog = remember { mutableStateOf(false) }
    val showDeleteAllUsersDialog = remember { mutableStateOf(false) }
    val userToDelete = remember { mutableStateOf<User?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.users_list),
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 40.sp
            )
            Button(
                onClick = {
                    showDeleteAllUsersDialog.value = true
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Text(stringResource(R.string.delete_all))
            }
        }
        Spacer(Modifier.height(16.dp))
        LazyColumn {
            items(users) { user ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .combinedClickable(
                            onClick = { navController.navigate("userDetails/${user.id}")},
                            onLongClick = {
                                userToDelete.value = user
                                showDeleteUserDialog.value = true
                            }
                        )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("${stringResource(R.string.user_name)}: ${user.name}")
                        Text("${stringResource(R.string.age)}: ${user.age}")
                        Text("${stringResource(R.string.job_title)}: ${user.jobTitle}")
                        Text("${stringResource(R.string.gender)}: ${user.gender}")
                    }
                }
            }
        }
    }
    if (showDeleteUserDialog.value) {
        AlertDialog(
            onDismissRequest = {
                showDeleteUserDialog.value = false
                userToDelete.value = null
            },
            title = { Text(stringResource(R.string.delete_user_confirmation_title)) },
            text = { Text(stringResource(R.string.delete_user_confirmation_message, userToDelete.value?.name ?: "")) },
            confirmButton = {
                Button(
                    onClick = {
                        userToDelete.value?.let { viewModel.deleteUser(it) }
                        showDeleteUserDialog.value = false
                        userToDelete.value = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text(stringResource(R.string.confirm))
                }
            },
            dismissButton = {
                Button(onClick = {
                    showDeleteUserDialog.value = false
                    userToDelete.value = null
                }) {
                    Text(stringResource(R.string.cancel))
                }
            }
        )
    }
    if (showDeleteAllUsersDialog.value) {
        AlertDialog(
            onDismissRequest = { showDeleteAllUsersDialog.value = false },
            title = { Text(stringResource(R.string.delete_all_users_confirmation_title)) },
            text = { Text(stringResource(R.string.delete_all_users_confirmation_message)) },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.deleteAllUsers()
                        showDeleteAllUsersDialog.value = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text(stringResource(R.string.confirm))
                }
            },
            dismissButton = {
                Button(onClick = { showDeleteAllUsersDialog.value = false }) {
                    Text(stringResource(R.string.cancel))
                }
            }
        )
    }
}