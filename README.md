# Live Polling & Voting App

A modern Android application that allows users to create polls, vote on them, and view real-time results with beautiful bar charts.

## Screenshots

<img src="https://github.com/narsale17/Voting_App/blob/main/Voting_App_Media/IMG-20250808-WA0005.jpg" width="200" />  <img src="https://github.com/narsale17/Voting_App/blob/main/Voting_App_Media/IMG-20250808-WA0011.jpg" width="200" /> <img src="https://github.com/narsale17/Voting_App/blob/main/Voting_App_Media/IMG-20250808-WA0007.jpg" width="200" /> <img src="https://github.com/narsale17/Voting_App/blob/main/Voting_App_Media/IMG-20250808-WA0009.jpg" width="200" />  <img src="https://github.com/narsale17/Voting_App/blob/main/Voting_App_Media/IMG-20250808-WA0012.jpg" width="200" /> <img src="https://github.com/narsale17/Voting_App/blob/main/Voting_App_Media/IMG-20250808-WA0010.jpg" width="200" /> <img src="https://github.com/narsale17/Voting_App/blob/main/Voting_App_Media/IMG-20250808-WA0008.jpg" width="200" /> <img src="https://github.com/narsale17/Voting_App/blob/main/Voting_App_Media/IMG-20250808-WA0006.jpg" width="200" /> 

## Demo

https://github.com/user-attachments/assets/2bc8752e-625e-463d-8cea-8bdfbb338fbb


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


## Installation

 - How to Download/Setup:

```bash
    git clone "https://github.com/narsale17/Voting_App.git"
