package com.alimmz.responsivedesignjetpackcompose.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class Task(
    val id: String,
    val title: String,
    val description: String,
    val dueDate: LocalDate,
    val priority: Priority,
    val isCompleted: Boolean = false
)

enum class Priority {
    LOW, MEDIUM, HIGH
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TasksScreen(
    onAddTask: () -> Unit,
    isLargeScreen: Boolean = false
) {
    var tasks by remember { mutableStateOf(sampleTasks) }
    var showCreateTask by remember { mutableStateOf(false) }
    
    if (isLargeScreen) {
        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            // Tasks List
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    // App Bar
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.surface,
                        tonalElevation = 3.dp
                    ) {
                        Column {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 24.dp, vertical = 12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Tasks",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                
                                IconButton(
                                    onClick = { showCreateTask = true }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = "Add Task",
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                            
                            Divider(
                                modifier = Modifier.fillMaxWidth(),
                                color = MaterialTheme.colorScheme.outlineVariant
                            )
                        }
                    }
                    
                    // Task List
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(tasks) { task ->
                            TaskItem(
                                task = task,
                                onComplete = { updatedTask ->
                                    tasks = tasks.map { if (it.id == updatedTask.id) updatedTask else it }
                                },
                                onDelete = { taskToDelete ->
                                    tasks = tasks.filter { it.id != taskToDelete.id }
                                }
                            )
                        }
                    }
                }
            }
            
            // Create Task Screen
            AnimatedVisibility(
                visible = showCreateTask,
                enter = slideInHorizontally(
                    initialOffsetX = { it },
                    animationSpec = tween(durationMillis = 300)
                ),
                exit = slideOutHorizontally(
                    targetOffsetX = { it },
                    animationSpec = tween(durationMillis = 300)
                )
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                ) {
                    CreateTaskScreen(
                        onTaskCreated = { task ->
                            tasks = tasks + task
                            showCreateTask = false
                        },
                        onCancel = { showCreateTask = false }
                    )
                }
            }
        }
    } else {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // App Bar
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 3.dp
            ) {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Tasks",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        
                        IconButton(
                            onClick = onAddTask
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add Task",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                    
                    Divider(
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.outlineVariant
                    )
                }
            }
            
            // Task List
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(tasks) { task ->
                    TaskItem(
                        task = task,
                        onComplete = { updatedTask ->
                            tasks = tasks.map { if (it.id == updatedTask.id) updatedTask else it }
                        },
                        onDelete = { taskToDelete ->
                            tasks = tasks.filter { it.id != taskToDelete.id }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun TaskItem(
    task: Task,
    onComplete: (Task) -> Unit,
    onDelete: (Task) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        tonalElevation = 1.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Priority indicator
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(
                            when (task.priority) {
                                Priority.LOW -> MaterialTheme.colorScheme.primary
                                Priority.MEDIUM -> MaterialTheme.colorScheme.secondary
                                Priority.HIGH -> MaterialTheme.colorScheme.error
                            }
                        )
                )
                
                // Due date
                Text(
                    text = task.dueDate.format(DateTimeFormatter.ofPattern("MMM dd, yyyy")),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Title
            Text(
                text = task.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                textDecoration = if (task.isCompleted) TextDecoration.LineThrough else TextDecoration.None
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            // Description
            Text(
                text = task.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textDecoration = if (task.isCompleted) TextDecoration.LineThrough else TextDecoration.None
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Actions
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { onComplete(task.copy(isCompleted = !task.isCompleted)) },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (task.isCompleted) {
                            MaterialTheme.colorScheme.surfaceVariant
                        } else {
                            MaterialTheme.colorScheme.primaryContainer
                        },
                        contentColor = if (task.isCompleted) {
                            MaterialTheme.colorScheme.onSurfaceVariant
                        } else {
                            MaterialTheme.colorScheme.onPrimaryContainer
                        }
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = if (task.isCompleted) "Mark as incomplete" else "Mark as complete",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (task.isCompleted) "Completed" else "Complete",
                        style = MaterialTheme.typography.labelLarge
                    )
                }
                
                OutlinedButton(
                    onClick = { onDelete(task) },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.error
                    ),
                    border = BorderStroke(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.error
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete task",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Delete",
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        }
    }
}

// Sample tasks
@RequiresApi(Build.VERSION_CODES.O)
private val sampleTasks = listOf(
    Task(
        id = "1",
        title = "Complete project documentation",
        description = "Write detailed documentation for the responsive design project",
        dueDate = LocalDate.now().plusDays(2),
        priority = Priority.HIGH
    ),
    Task(
        id = "2",
        title = "Review pull requests",
        description = "Review and merge pending pull requests",
        dueDate = LocalDate.now().plusDays(1),
        priority = Priority.MEDIUM
    ),
    Task(
        id = "3",
        title = "Update dependencies",
        description = "Check and update project dependencies",
        dueDate = LocalDate.now().plusDays(3),
        priority = Priority.LOW
    ),
    Task(
        id = "4",
        title = "Fix UI bugs",
        description = "Address reported UI issues in the notification screen",
        dueDate = LocalDate.now(),
        priority = Priority.HIGH,
        isCompleted = true
    )
) 