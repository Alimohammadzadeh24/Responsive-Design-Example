package com.alimmz.responsivedesignjetpackcompose.data

data class Notification(
    val id: String,
    val title: String,
    val message: String,
    val timestamp: String,
    val isRead: Boolean = false
)

val sampleNotifications = listOf(
    Notification(
        id = "1",
        title = "New Message",
        message = "You have received a new message from John Doe",
        timestamp = "10:30 AM"
    ),
    Notification(
        id = "2",
        title = "System Update",
        message = "Your system has been updated to the latest version",
        timestamp = "Yesterday"
    ),
    Notification(
        id = "3",
        title = "Meeting Reminder",
        message = "Team meeting starts in 15 minutes",
        timestamp = "9:45 AM"
    ),
    Notification(
        id = "4",
        title = "New Feature",
        message = "Check out our new dashboard features",
        timestamp = "2 days ago"
    ),
    Notification(
        id = "5",
        title = "Security Alert",
        message = "Your account was accessed from a new device",
        timestamp = "1 week ago"
    )
) 