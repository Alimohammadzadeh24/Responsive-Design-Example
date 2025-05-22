# Responsive Design Example with Jetpack Compose

## Overview

This project demonstrates how to implement responsive navigation and layouts in an Android app using Jetpack Compose. It adapts its UI based on screen size, providing a seamless experience on both phones and tablets.

## Features

- **Responsive Navigation**: Switches between bottom navigation (phones) and navigation rail (tablets).
- **Adaptive Layouts**: Adjusts UI components based on screen width.
- **Material 3 Design**: Uses Material 3 components for a modern look and feel.
- **Clean Architecture**: Follows best practices for maintainable and scalable code.

## Screenshots

_Insert screenshots of your app here (e.g., phone and tablet layouts)._

## Getting Started

### Prerequisites

- Android Studio Arctic Fox or later
- Kotlin 1.5.1 or later
- Android SDK 24 or later

### Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/Alimohammadzadeh24/Responsive-Design-Example.git
   ```

2. Open the project in Android Studio.

3. Build and run the app on your device or emulator.

## Project Structure

```
com.example.responsivedesign/
├── MainActivity.kt
├── navigation/
│   ├── Navigation.kt
│   ├── BottomNavigationBar.kt
│   └── NavigationRail.kt
├── screens/
│   ├── HomeScreen.kt
│   ├── TasksScreen.kt
│   └── NotificationsScreen.kt
└── ui/
    └── theme/
        ├── Theme.kt
        ├── Color.kt
        └── Type.kt
```

## Dependencies

- **Jetpack Compose**: For building native UI.
- **Material 3**: For design components.
- **Navigation Compose**: For handling navigation.
- **Window Size Class**: For responsive design.

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments

- Thanks to the Jetpack Compose team for their excellent documentation and examples.
