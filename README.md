# Live Polling & Voting App

A modern Android application that allows users to create polls, vote on them, and view real-time results with beautiful bar charts.

## Features

### 🔐 Login System
- Simple username-based authentication (no password required)
- Persistent login using SharedPreferences
- Automatic redirection to polls screen if already logged in

### 📊 Poll Management
- Create new polls with custom questions
- Support for 2-4 voting options per poll
- Local storage using Room database
- Beautiful card-based UI for poll display

### 🗳️ Voting System
- One vote per user per poll (enforced locally)
- Real-time vote counting and updates
- Dynamic bar charts showing live results
- Once voted, users see results instead of voting buttons

### 📈 Real-Time Results
- Live vote count updates
- Interactive bar charts using MPAndroidChart
- Beautiful Material Design UI
- Responsive layout for different screen sizes

### 🚪 Logout Functionality
- Logout button in polls screen
- Clears stored username and redirects to login
- Clean session management

## Technical Architecture

### Data Layer
- **Room Database**: Local storage for polls and votes
- **SharedPreferences**: User session management
- **Entities**: Poll and Vote models with proper relationships

### UI Layer
- **Activities**: Login, Polls, Create Poll, Voting
- **Material Design**: Modern UI components
- **RecyclerView**: Efficient poll list display
- **MPAndroidChart**: Beautiful bar charts for results

### Business Logic
- **ExecutorService**: Background database operations
- **Adapter Pattern**: Poll list management
- **Observer Pattern**: Real-time updates

## App Flow

```
[App Start]
    ↓
Check if username exists
    ↓
[YES] → [Polls Screen]
[NO]  → [Login Screen]
    ↓
[Login Screen]
    ↓
User enters username
    ↓
Redirect to [Polls Screen]
    ↓
[Polls Screen]
    ↓
Load existing polls
    ↓
User taps poll → [Voting Screen]
User taps "Create" → [Create Poll Screen]
User taps "Logout" → Clear username → [Login Screen]
    ↓
[Voting Screen]
    ↓
Check if user has voted
    ↓
[YES] → Show results with chart
[NO]  → Show voting options
    ↓
User votes → Update database → Show results
```

## Database Schema

### Poll Table
- `id` (Primary Key, Auto-increment)
- `question` (Text)
- `option1` (Text)
- `option2` (Text)
- `option3` (Text, Optional)
- `option4` (Text, Optional)
- `createdAt` (Long timestamp)

### Vote Table
- `id` (Primary Key, Auto-increment)
- `pollId` (Foreign Key to Poll)
- `username` (Text)
- `selectedOption` (Integer: 1-4)
- `votedAt` (Long timestamp)

## Dependencies

- **AndroidX**: Core Android components
- **Material Design**: Modern UI components
- **Room**: Local database
- **MPAndroidChart**: Chart visualization
- **RecyclerView**: Efficient list display

## Installation

1. Clone the repository
2. Open in Android Studio
3. Sync Gradle files
4. Build and run on device/emulator

## Usage

1. **Login**: Enter a username to start
2. **Create Polls**: Tap the + button to create new polls
3. **Vote**: Tap on any poll to vote
4. **View Results**: See real-time results with charts
5. **Logout**: Use the menu to logout

## Technical Highlights

- **Local Storage**: All data stored locally using Room
- **Real-time Updates**: Instant vote count updates
- **One Vote Per User**: Enforced at database level
- **Modern UI**: Material Design 3 components
- **Responsive**: Works on different screen sizes
- **Efficient**: Background operations using ExecutorService

## Screenshots

The app features:
- Clean login screen with Material Design input
- Poll list with card-based layout
- Create poll form with validation
- Voting screen with dynamic buttons
- Results screen with interactive bar charts

## Future Enhancements

- Cloud synchronization
- Multiple user support
- Advanced chart types
- Poll categories
- Export results
- Push notifications for new polls 