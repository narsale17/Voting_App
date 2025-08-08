# Live Polling & Voting App

A modern Android application that allows users to create polls, vote on them, and view real-time results with beautiful bar charts.

## Features

### Login System
- Simple username-based authentication (no password required)
- Persistent login using SharedPreferences
- Automatic redirection to polls screen if already logged in

### Poll Management
- Create new polls with custom questions
- Support for 2-4 voting options per poll
- Local storage using Room database
- Beautiful card-based UI for poll display

### Voting System
- One vote per user per poll (enforced locally)
- Real-time vote counting and updates
- Dynamic bar charts showing live results
- Once voted, users see results instead of voting buttons

### Real-Time Results
- Live vote count updates
- Interactive bar charts using MPAndroidChart
- Beautiful Material Design UI
- Responsive layout for different screen sizes

### Logout Functionality
- Logout button in polls screen
- Clears stored username and redirects to login
- Clean session management

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
