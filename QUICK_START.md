# HabitApp - Quick Start Guide

This guide will help you get started with the HabitApp quickly.

## Prerequisites

- **Android Studio**: Flamingo (2022.2.1) or later
- **JDK**: Java 17 or later
- **Android SDK**: API 24 (Android 7.0) or higher
- **Gradle**: 8.0 (included via wrapper)

## Setup Instructions

### 1. Clone the Repository

```bash
git clone https://github.com/Atoner88/HabitApp.git
cd HabitApp
```

### 2. Open in Android Studio

1. Launch Android Studio
2. Select "Open an Existing Project"
3. Navigate to the cloned HabitApp directory
4. Click "OK"

### 3. Sync Gradle

Android Studio will automatically sync Gradle. If not:
1. Click "File" → "Sync Project with Gradle Files"
2. Wait for the sync to complete

### 4. Run the App

1. Connect an Android device or start an emulator
2. Click the "Run" button (▶️) or press `Shift + F10`
3. Select your target device
4. Wait for the app to build and install

## First Use

### Creating Your First Habit

1. **Open the app** - You'll see an empty habit list
2. **Tap the (+) FAB button** in the bottom-right corner
3. **Enter habit details**:
   - Name: e.g., "Morning Exercise"
   - Description: e.g., "30 minutes of cardio" (optional)
   - Category: Select "No Category" for now
4. **Tap "SAVE"**
5. Your habit now appears in the list!

### Creating a Category

1. **Tap the menu icon** (three dots) in the top-right
2. **Select "Categories"**
3. **Tap the (+) FAB button**
4. **Enter category name**: e.g., "Health"
5. **Tap "ADD"**
6. The category is created with a random color

### Assigning a Category to a Habit

1. **Tap on a habit card** to open the details
2. **Select a category** from the dropdown
3. **Tap "SAVE"**
4. The habit card now shows the category color and name

### Checking Off a Habit

1. **Find the habit** in the main list
2. **Tap the checkbox** on the right side
3. ✓ Done! Your current streak will update

### Viewing Calendar

1. **Find the habit** in the main list
2. **Tap the calendar icon** (📅) next to the checkbox
3. **View your completion history** in calendar format
4. **Tap any date** to toggle completion
5. **Navigate months** using the arrows

### Switching Views

1. **Tap the menu icon** (three dots) in the top-right
2. **Select "Toggle View"**
3. Switch between:
   - **Tile View**: Full cards with all details
   - **Grid View**: Compact 2-column layout

## Key Features at a Glance

| Feature | How to Access |
|---------|---------------|
| Add Habit | (+) FAB on main screen |
| Edit Habit | Tap on habit card |
| Delete Habit | Open habit → "DELETE" button |
| Add Category | Menu → Categories → (+) FAB |
| Toggle View | Menu → Toggle View |
| View Calendar | Tap calendar icon on habit card |
| Check Today | Tap checkbox on habit card |

## Understanding the Interface

### Main Screen

```
┌─────────────────────────────────┐
│  HabitApp          [≡] [View]  │  ← Toolbar with menu
├─────────────────────────────────┤
│ ┌─────────────────────────┐    │
│ │ Morning Exercise    📅 ☐│    │  ← Habit card
│ │ Health                   │    │     - Name & Category
│ │ 🔥 5                     │    │     - Streak counter
│ └─────────────────────────┘    │     - Calendar & checkbox
│                                 │
│ ┌─────────────────────────┐    │
│ │ Read a Book        📅 ☐│    │  ← Another habit
│ │ Personal Growth          │    │
│ │ 🔥 12                    │    │
│ └─────────────────────────┘    │
│                           [+]   │  ← FAB to add habit
└─────────────────────────────────┘
```

### Calendar View

```
┌─────────────────────────────────┐
│  Morning Exercise - Calendar    │  ← Habit name
├─────────────────────────────────┤
│     <  November 2025  >         │  ← Month navigation
├─────────────────────────────────┤
│ Sun Mon Tue Wed Thu Fri Sat    │  ← Day headers
│                         1   2   │
│  3   4   5   6   7   8   9     │  ← Calendar grid
│ 10  11  12  13  14  15  16     │    Green = Completed
│ 17  18  19  20  21  22  23     │    Blue = Today
│ 24  25  26  27  28  29  30     │    White = Available
├─────────────────────────────────┤
│ ■ Completed  ■ Today            │  ← Legend
└─────────────────────────────────┘
```

## Tips and Best Practices

### Setting Up Habits

- **Start small**: Begin with 2-3 habits to avoid overwhelm
- **Be specific**: "Exercise 30 minutes" is better than "Exercise"
- **Use categories**: Organize habits by life area (Health, Work, Personal)
- **Check daily**: Build the habit of checking off your habits

### Maintaining Streaks

- **Daily review**: Check your habits at the same time each day
- **Use calendar view**: Visualize your progress
- **Celebrate milestones**: Notice when you hit 7, 30, 100 days!
- **Don't break the chain**: Your streak is motivation

### Organization

- **Color-code**: Use categories with different colors
- **Archive old habits**: Remove completed or abandoned habits
- **Review weekly**: Assess which habits are working

## Troubleshooting

### App won't build
- **Check Gradle sync**: File → Sync Project with Gradle Files
- **Clean build**: Build → Clean Project, then Build → Rebuild Project
- **Invalidate caches**: File → Invalidate Caches / Restart

### Database issues
- **Clear app data**: Settings → Apps → HabitApp → Storage → Clear Data
- **Reinstall**: Uninstall and reinstall the app

### UI issues
- **Restart app**: Force close and reopen
- **Check Android version**: Ensure device is API 24+ (Android 7.0+)

## Next Steps

1. ✓ Install and run the app
2. ✓ Create your first habit
3. ✓ Check it off daily
4. ✓ Build a streak
5. → Explore calendar view
6. → Add more habits
7. → Create categories
8. → Achieve your goals!

## Need Help?

- **README.md**: Overview and features
- **PROJECT_STRUCTURE.md**: Detailed file structure
- **FEATURES.md**: Complete feature documentation
- **GitHub Issues**: Report bugs or request features

---

Happy habit building! 🎯
