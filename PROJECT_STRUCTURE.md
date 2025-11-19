# HabitApp - Project Structure

This document provides a detailed overview of the file structure for the HabitApp Kotlin Android application.

## Root Directory Structure

```
HabitApp/
├── app/                        # Main application module
├── gradle/                     # Gradle wrapper files
├── build.gradle.kts           # Root build configuration
├── settings.gradle.kts        # Project settings
├── gradle.properties          # Gradle properties
├── .gitignore                 # Git ignore rules
├── README.md                  # Project documentation
└── PROJECT_STRUCTURE.md       # This file
```

## Application Module Structure

```
app/
├── build.gradle.kts           # App-level build configuration
├── proguard-rules.pro         # ProGuard rules
└── src/
    ├── main/                  # Main source set
    ├── test/                  # Unit tests
    └── androidTest/           # Instrumented tests
```

## Main Source Structure

```
app/src/main/
├── AndroidManifest.xml        # App manifest configuration
├── java/com/atoner/habitapp/  # Kotlin source files
│   ├── HabitApplication.kt    # Application class
│   │
│   ├── data/                  # Data layer
│   │   ├── model/            # Data models (entities)
│   │   │   ├── Habit.kt                    # Habit entity
│   │   │   ├── HabitCategory.kt            # Category entity
│   │   │   ├── HabitCompletion.kt          # Completion entity
│   │   │   ├── HabitWithCategory.kt        # Relation model
│   │   │   └── HabitWithCompletions.kt     # Relation model
│   │   │
│   │   ├── dao/              # Data Access Objects
│   │   │   ├── HabitDao.kt               # Habit database operations
│   │   │   ├── HabitCategoryDao.kt       # Category database operations
│   │   │   └── HabitCompletionDao.kt     # Completion database operations
│   │   │
│   │   ├── database/         # Database configuration
│   │   │   └── HabitDatabase.kt          # Room database class
│   │   │
│   │   └── repository/       # Repository layer
│   │       ├── HabitRepository.kt        # Habit data repository
│   │       └── CategoryRepository.kt     # Category data repository
│   │
│   ├── ui/                   # UI layer
│   │   ├── MainActivity.kt   # Main activity (habit list)
│   │   │
│   │   ├── habit/           # Habit-related UI
│   │   │   ├── HabitAdapter.kt           # RecyclerView adapter
│   │   │   └── HabitDetailActivity.kt    # Create/Edit habit screen
│   │   │
│   │   ├── category/        # Category management UI
│   │   │   ├── CategoryAdapter.kt              # RecyclerView adapter
│   │   │   └── CategoryManagementActivity.kt   # Category management screen
│   │   │
│   │   └── calendar/        # Calendar view UI
│   │       ├── CalendarAdapter.kt              # Calendar grid adapter
│   │       └── CalendarViewActivity.kt         # Calendar view screen
│   │
│   ├── viewmodel/           # ViewModels (business logic)
│   │   ├── HabitViewModel.kt         # Habit operations ViewModel
│   │   └── CategoryViewModel.kt      # Category operations ViewModel
│   │
│   └── util/                # Utility classes
│       ├── DateUtils.kt              # Date formatting and manipulation
│       └── ColorUtils.kt             # Color utilities
│
└── res/                     # Resources
    ├── layout/              # XML layouts
    │   ├── activity_main.xml                 # Main screen layout
    │   ├── activity_habit_detail.xml         # Habit detail screen layout
    │   ├── activity_category_management.xml  # Category management layout
    │   ├── activity_calendar_view.xml        # Calendar view layout
    │   ├── item_habit.xml                    # Habit list item layout
    │   ├── item_category.xml                 # Category list item layout
    │   ├── item_calendar_day.xml             # Calendar day item layout
    │   └── dialog_category.xml               # Category dialog layout
    │
    ├── menu/               # Menu resources
    │   └── main_menu.xml                     # Main activity menu
    │
    ├── values/             # Value resources
    │   ├── strings.xml                       # String resources
    │   ├── colors.xml                        # Color resources
    │   └── themes.xml                        # Theme definitions
    │
    └── mipmap-*/           # App icons (various densities)
        └── ic_launcher*                      # App launcher icons
```

## Key Components Explanation

### Data Layer
- **Models**: Define the structure of data using Room entities
- **DAOs**: Provide methods to access the database
- **Database**: Room database singleton
- **Repositories**: Abstracts data sources and provides clean API to ViewModels

### ViewModel Layer
- **HabitViewModel**: Manages habit data, completions, and streaks
- **CategoryViewModel**: Manages category data

### UI Layer
- **MainActivity**: Displays habit list with tile/grid view toggle
- **HabitDetailActivity**: Create or edit habit with category selection
- **CategoryManagementActivity**: Manage habit categories
- **CalendarViewActivity**: View habit completions in calendar format
- **Adapters**: Bridge between data and RecyclerView

### Utilities
- **DateUtils**: Date formatting, parsing, and helper methods
- **ColorUtils**: Predefined colors and color manipulation

## Database Schema

### Tables
1. **habits**
   - id (PRIMARY KEY)
   - name
   - description
   - categoryId (FOREIGN KEY -> habit_categories.id)
   - createdAt
   - currentStreak
   - highestStreak
   - isArchived

2. **habit_categories**
   - id (PRIMARY KEY)
   - name
   - color
   - icon
   - createdAt

3. **habit_completions**
   - id (PRIMARY KEY)
   - habitId (FOREIGN KEY -> habits.id)
   - completionDate (UNIQUE with habitId)
   - timestamp
   - notes

## View Types

1. **Tile View** (Default): Cards showing habit name, category, streak, and today's checkbox
2. **Grid View**: Compact 2-column grid layout
3. **Calendar View**: Monthly calendar showing completion history for a specific habit

## Navigation Flow

```
MainActivity (Habit List)
    ├─> HabitDetailActivity (Create New Habit)
    ├─> HabitDetailActivity (Edit Habit)
    ├─> CalendarViewActivity (View Habit Calendar)
    └─> CategoryManagementActivity
            └─> Category Dialog (Add/Edit Category)
```

## Key Features by File

### Streak Tracking
- **HabitRepository.kt**: `updateStreaks()` calculates current and highest streaks
- **HabitCompletion.kt**: Stores individual completion records

### Multiple Views
- **MainActivity.kt**: Toggle between tile and grid layouts
- **CalendarViewActivity.kt**: Calendar view for individual habits

### Category Management
- **HabitCategory.kt**: Category model with color support
- **ColorUtils.kt**: Predefined color palette
- **CategoryManagementActivity.kt**: UI for managing categories

### Daily Check-offs
- **HabitRepository.kt**: `toggleHabitCompletion()` for marking/unmarking
- **HabitAdapter.kt**: Checkbox for today's completion
