# HabitApp - Features Documentation

## Core Features

### 1. Habit Management

#### Create Habits
- Add new habits with name and description
- Assign habits to categories (optional)
- Set up habits quickly through the FAB button on the main screen

#### Edit Habits
- Modify habit name and description
- Change category assignment
- View current and highest streak statistics

#### Delete Habits
- Remove habits with confirmation dialog
- All associated completion data is deleted automatically (CASCADE)

#### Archive Habits (Soft Delete)
- Mark habits as archived instead of deleting
- Archived habits don't appear in the main list
- Preserves historical data

### 2. Habit Categories

#### Create Categories
- Add custom categories with unique names
- Automatic color assignment from predefined palette
- 12 predefined colors available

#### Edit Categories
- Rename categories
- Color remains assigned

#### Delete Categories
- Remove categories with confirmation
- Habits in deleted categories become uncategorized
- Uses SET_NULL foreign key constraint

#### Category Colors
Predefined color palette:
- Red (#FF6B6B)
- Teal (#4ECDC4)
- Blue (#45B7D1)
- Light Salmon (#FFA07A)
- Mint (#98D8C8)
- Yellow (#FFD93D)
- Light Teal (#95E1D3)
- Light Red (#F38181)
- Purple (#AA96DA)
- Pink (#FCBAD3)
- Light Green (#A8E6CF)
- Peach (#FFD6A5)

### 3. Streak Tracking

#### Current Streak
- Automatically calculated based on consecutive daily completions
- Counts backwards from today
- Resets to 0 if a day is missed

#### Highest Streak
- Records the maximum consecutive days achieved
- Never decreases (only increases when current streak exceeds it)
- Persists even if current streak resets

#### Automatic Updates
- Streaks update automatically when habits are completed/uncompleted
- Real-time calculation ensures accuracy

### 4. Daily Check-offs

#### Mark as Complete
- Tap checkbox to mark habit as done for today
- Visual feedback with checked state
- Creates HabitCompletion record

#### Unmark Completion
- Tap checkbox again to unmark
- Removes HabitCompletion record
- Updates streak accordingly

#### Date-based Completion
- Each completion is tied to a specific date (YYYY-MM-DD format)
- Prevents duplicate completions on the same day (UNIQUE constraint)
- Supports historical date completions in calendar view

### 5. Multiple View Modes

#### Tile View (Default)
- Card-based layout
- Shows habit name, category, and current streak
- Includes today's completion checkbox
- Calendar button for quick access to habit calendar
- Color-coded by category

**Display Information:**
- Habit name (bold)
- Category name (if assigned)
- Current streak with fire emoji (🔥)
- Today's checkbox
- Calendar button

#### Grid View
- Compact 2-column layout
- Shows same information as tile view
- Better for viewing many habits at once
- Toggle via menu button

#### Calendar View
- Monthly calendar display
- Shows completion history for a single habit
- Navigate between months with arrow buttons
- Color-coded days:
  - **Green (#4CAF50)**: Completed
  - **Blue (#2196F3)**: Today
  - **Gray (#EEEEEE)**: Future dates (not clickable)
  - **White**: Available but not completed

**Calendar Features:**
- Tap any past or today's date to toggle completion
- Cannot mark future dates
- Visual legend showing color meanings
- Month/year navigation

### 6. Data Persistence

#### Room Database
- Local SQLite database using Room
- Three main tables: habits, habit_categories, habit_completions
- Automatic database creation on first launch
- Supports database migrations

#### Relationships
- Habits linked to Categories (Many-to-One)
- Habits linked to Completions (One-to-Many)
- Proper foreign key constraints with CASCADE/SET_NULL

#### Data Integrity
- UNIQUE constraints prevent duplicate completions
- Foreign keys maintain referential integrity
- Indexed queries for performance

### 7. User Interface

#### Material Design
- Modern Material Design components
- Consistent color scheme (Purple primary, Teal accent)
- Smooth animations and transitions
- Responsive layouts

#### Navigation
- Back button support in all activities
- Clear navigation flow
- Contextual actions in appropriate locations

#### Floating Action Buttons (FAB)
- Main screen: Add new habit
- Category management: Add new category
- Consistent placement and behavior

#### Dialogs
- Category creation/editing
- Habit deletion confirmation
- Category deletion confirmation

### 8. Utility Features

#### Date Utilities
- Format dates for display
- Parse ISO date strings
- Get day/month names
- Check if date is today or in future
- Calculate month boundaries

#### Color Utilities
- Predefined color palette
- Random color selection
- Color contrast calculation
- Automatic text color adjustment (black/white based on background)

## Technical Features

### Architecture
- **MVVM Pattern**: Clean separation of concerns
- **Repository Pattern**: Abstraction of data sources
- **LiveData**: Reactive data updates
- **Coroutines**: Asynchronous operations
- **ViewModels**: Lifecycle-aware data management

### Performance
- **RecyclerView**: Efficient list rendering with view recycling
- **DiffUtil**: Optimized list updates
- **Database Indexing**: Fast queries on foreign keys
- **Flow/LiveData**: Reactive updates without polling

### Code Quality
- **Type Safety**: Kotlin null safety
- **Data Classes**: Immutable data structures
- **Extension Functions**: Utility methods
- **Proper Scoping**: ViewModelScope for coroutines

## Future Enhancement Ideas

### Notifications
- Daily reminders for habits
- Streak milestone notifications
- Configurable reminder times

### Statistics
- Weekly/monthly completion rates
- Trend analysis
- Habit comparison charts

### Data Management
- Export data to CSV/JSON
- Import data from backup
- Cloud sync support

### Widgets
- Home screen widget showing today's habits
- Quick completion from widget
- Streak display

### Customization
- Dark theme support
- Custom color picker for categories
- Habit icons
- Custom start day of week for calendar

### Social Features
- Share progress with friends
- Habit templates
- Community challenges

### Advanced Tracking
- Habit notes for each completion
- Habit goals (e.g., "Complete 5 times per week")
- Progress tracking towards goals
- Multiple daily completions support
