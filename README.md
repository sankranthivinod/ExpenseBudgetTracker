# Expense & Budget Tracker 💰📱

**Expense & Budget Tracker** is a modern Android application built using **Kotlin** and **Jetpack Compose** with **Material 3** design principles. It is designed to help users effortlessly track daily expenses, manage budgets, monitor income, and gain insights into their financial habits.

---

## ✨ Features

- 🎨 **Modern Jetpack Compose UI**: Built from the ground up with Material 3 components and Edge-to-Edge display support.
- 💳 **Expense & Income Management**: Easily log daily expenses and incomes with detailed categorization.
- 📊 **Budget Tracking**: Set monthly budget limits per category and receive notifications when approaching bounds.
- 📈 **Visual Analytics**: Interactive graphs and charts to visualize spending patterns over time.
- 📱 **Adaptive & Responsive Layouts**: Seamless support for different Android screen sizes and orientations.

---

## 🛠 Tech Stack & Tools

- **Language**: [Kotlin](https://kotlinlang.org/)
- **UI Framework**: [Jetpack Compose](https://developer.android.com/jetpack/compose) (Material 3)
- **Architecture**: Modern Android Architecture (MVVM / Clean Architecture)
- **Asynchronous Processing**: Kotlin Coroutines & Flow
- **Build System**: Gradle (Kotlin DSL)
- **Min SDK**: 24 (Android 7.0)
- **Target SDK**: 37 (Android 15+)

---

## 🚀 Getting Started

### Prerequisites

- **Android Studio**: Android Studio Ladybug (2024.2.1) or newer recommended.
- **JDK**: Java Development Kit (JDK) 11 or higher.
- **Android SDK**: API level 37 installed.

### Installation

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/sankranthivinod/ExpenseBudgetTracker.git
   cd ExpenseBudgetTracker
   ```

2. **Open in Android Studio**:
   - Launch Android Studio and choose **Open an Existing Project**.
   - Select the `ExpenseBudgetTracker` root directory.

3. **Build & Run**:
   - Allow Gradle to sync dependencies.
   - Run the app on an Android Emulator or connected physical device using `./gradlew installDebug` or directly via Android Studio's **Run** button.

---

## 📁 Project Structure

```
ExpenseBudgetTracker/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/sankranthivinod/expensebudgettracker/
│   │   │   │   ├── MainActivity.kt
│   │   │   │   └── ui/theme/         # Color, Type, Theme definitions
│   │   │   └── AndroidManifest.xml
│   │   ├── test/                     # Unit tests
│   │   └── androidTest/              # Instrumented UI tests
│   └── build.gradle.kts
├── build.gradle.kts
├── settings.gradle.kts
├── gradle/
├── .gitignore
└── README.md
```

---

## 🤝 Contributing

Contributions are welcome! If you'd like to add features or report issues:
1. Fork the project repository.
2. Create your feature branch (`git checkout -b feature/AmazingFeature`).
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`).
4. Push to the branch (`git push origin feature/AmazingFeature`).
5. Open a Pull Request.

---

## 📄 License

This project is licensed under the [MIT License](LICENSE) - see the LICENSE file for details.
