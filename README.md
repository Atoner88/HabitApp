# HabitApp

A Kotlin-based Android habit tracking application that helps users build and maintain positive habits.

## Features

- **Create, Edit, and Delete Habits**: Manage your habits with ease
- **Habit Categorization**: Organize habits into custom categories with colors
- **Streak Tracking**: Track current streak and highest streak for each habit
- **Daily Check-offs**: Mark habits as completed each day
- **Multiple Views**: 
  - Tile view: See all habits in a card-based layout
  - Grid view: Compact view of habits
  - Calendar view support (ready for implementation)
- **Category Management**: Create and manage habit categories with custom colors

## Architecture

This app follows the MVVM (Model-View-ViewModel) architecture pattern with the following layers:

### Data Layer
- **Models**: `Habit`, `HabitCategory`, `HabitCompletion`
- **Room Database**: Local data persistence
- **DAOs**: Data Access Objects for database operations
- **Repositories**: Abstraction layer for data access

### ViewModel Layer
- `HabitViewModel`: Manages habit data and business logic
- `CategoryViewModel`: Manages category data

### UI Layer
- **MainActivity**: Main screen displaying habit list
- **HabitDetailActivity**: Create/edit habit details
- **CategoryManagementActivity**: Manage habit categories
- **Adapters**: RecyclerView adapters for lists

## Project Structure

```
app/
├── src/
│   ├── main/
│   │   ├── java/com/atoner/habitapp/
│   │   │   ├── data/
│   │   │   │   ├── model/         # Data models
│   │   │   │   ├── dao/           # Room DAOs
│   │   │   │   ├── database/      # Room database
│   │   │   │   └── repository/    # Repository layer
│   │   │   ├── ui/                # UI components
│   │   │   │   ├── habit/         # Habit-related UI
│   │   │   │   ├── category/      # Category management UI
│   │   │   │   └── calendar/      # Calendar view (future)
│   │   │   ├── viewmodel/         # ViewModels
│   │   │   ├── util/              # Utility classes
│   │   │   └── HabitApplication.kt
│   │   ├── res/                   # Resources (layouts, strings, etc.)
│   │   └── AndroidManifest.xml
│   ├── test/                      # Unit tests
│   └── androidTest/               # Instrumentation tests
└── build.gradle.kts
```

## Technologies Used

- **Kotlin**: Primary programming language
- **Android Jetpack**:
  - Room: Local database
  - LiveData & ViewModel: Reactive data management
  - Navigation: Fragment navigation (ready for use)
- **Material Design Components**: Modern UI components
- **Coroutines**: Asynchronous programming
- **RecyclerView**: Efficient list rendering

## Building the Project

1. Clone the repository
2. Open in Android Studio (Flamingo or later)
3. Sync Gradle files
4. Run on an emulator or physical device (API 24+)

## Future Enhancements

- Calendar view for visualizing habit completions
- Statistics and analytics
- Habit reminders and notifications
- Data export/import
- Widget support
- Dark theme support