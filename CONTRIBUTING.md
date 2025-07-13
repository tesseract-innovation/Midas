# Contributing to Midas Money

Thank you for your interest in contributing to **Midas Money**! We welcome contributions from the community to help make this financial management app even better. This document outlines the guidelines for contributing to the project. Please read it carefully to ensure a smooth collaboration process.

## Table of Contents

- [How to Contribute](#how-to-contribute)
- [Setting Up the Development Environment](#setting-up-the-development-environment)
- [Submitting Contributions](#submitting-contributions)
- [Coding Guidelines](#coding-guidelines)
- [Commit Message Guidelines](#commit-message-guidelines)
- [Pull Request Process](#pull-request-process)
- [Reporting Bugs](#reporting-bugs)
- [Suggesting Features](#suggesting-features)
- [Code of Conduct](#code-of-conduct)

## How to Contribute

We welcome contributions in the form of bug fixes, new features, documentation improvements, and more. Here’s how you can contribute:

1. **Check existing issues**: Browse the [GitHub Issues](https://github.com/userddssilva/Midas/issues) to see if your idea or bug is already being discussed.
2. **Fork the repository**: Create your own copy of the repository by forking it on GitHub.
3. **Create a branch**: Work on your changes in a new branch specific to your contribution.
4. **Submit a Pull Request (PR)**: Once your changes are ready, submit a PR to the main repository for review.

## Setting Up the Development Environment

To contribute to Midas Money, you’ll need to set up the development environment. Follow these steps:

### Prerequisites
- [Git](https://git-scm.com/)
- [Android Studio](https://developer.android.com/studio)
- [JDK](https://www.oracle.com/java/technologies/javase-downloads.html) (Java Development Kit)
- (Optional) Google Play Console account for testing deployment

### Steps
1. **Clone your forked repository**:
   ```bash
   git clone https://github.com/<your-username>/Midas.git && cd Midas
   ```

2. **Install dependencies**:
   ```bash
   ./gradlew build
   ```

3. **Configure the environment**:
   - Create a `.env` file in the project root and add necessary environment variables (e.g., API keys).
   - Ensure the package name is set to `com.midasmoney.app` in `app/build.gradle`.

4. **Run the project**:
   - Open the project in Android Studio.
   - Sync the project with Gradle.
   - Run the app on an emulator or physical Android device:
     ```bash
     ./gradlew installDebug
     ```

## Submitting Contributions

To contribute code or documentation, follow these steps:

1. **Create a new branch**:
   ```bash
   git checkout -b feature/<your-feature-name>
   ```
   Use a descriptive branch name, e.g., `feature/add-expense-tracking` or `fix/budget-calculation`.

2. **Make your changes**:
   - Ensure your code follows the [Coding Guidelines](#coding-guidelines).
   - Test your changes thoroughly.

3. **Commit your changes**:
   ```bash
   git commit -m "Add <description-of-your-changes>"
   ```
   Follow the [Commit Message Guidelines](#commit-message-guidelines).

4. **Push to your fork**:
   ```bash
   git push origin feature/<your-feature-name>
   ```

5. **Open a Pull Request**:
   - Go to the [Midas Money repository](https://github.com/userddssilva/Midas) on GitHub.
   - Create a Pull Request from your branch to the `main` branch.
   - Provide a clear description of your changes in the PR template.

## Coding Guidelines

To maintain consistency and quality in the codebase, please adhere to the following guidelines:

- **Language**: Use Java or Kotlin for Android development (to be finalized; check the project’s `build.gradle` for the current setup).
- **Code Style**:
  - Follow the [Android Kotlin Style Guide](https://developer.android.com/kotlin/style-guide) or [Java Coding Conventions](https://www.oracle.com/java/technologies/javase/codeconventions-contents.html) as applicable.
  - Use 4 spaces for indentation.
  - Keep lines under 120 characters.
- **Documentation**:
  - Add comments for complex logic.
  - Update relevant documentation (e.g., README, API docs) when adding new features.
- **Testing**:
  - Write unit tests for new functionality using JUnit or similar frameworks.
  - Ensure all tests pass before submitting a PR (`./gradlew test`).
- **Dependencies**:
  - Avoid adding unnecessary dependencies.
  - Discuss new dependencies with the maintainers via an issue or PR comment.

## Commit Message Guidelines

Clear and consistent commit messages help maintain a clean project history. Follow these guidelines:

- Use the imperative mood (e.g., "Add expense tracking" instead of "Added expense tracking").
- Keep the subject line concise (50 characters or less).
- Provide a detailed description in the body if necessary, explaining *what* changed and *why*.
- Reference related issues (e.g., "Fixes #123").

Example:
```
Add expense tracking feature

- Implemented expense logging UI
- Added database schema for expenses
- Updated README with new feature details
Fixes #45
```

## Pull Request Process

1. Ensure your PR addresses a specific issue or feature.
2. Fill out the PR template provided in the repository.
3. Link to any related issues (e.g., "Closes #123").
4. Ensure all checks (e.g., CI tests, linting) pass.
5. Respond to feedback from maintainers during the review process.
6. Once approved, your changes will be merged into the `main` branch.

## Reporting Bugs

If you find a bug, please report it by opening an issue on GitHub:

1. Go to the [Issues tab](https://github.com/userddssilva/Midas/issues).
2. Use the "Bug Report" template and provide:
   - A clear description of the bug.
   - Steps to reproduce the issue.
   - Expected and actual behavior.
   - Screenshots or logs, if applicable.

## Suggesting Features

We’re excited to hear your ideas for improving Midas Money! To suggest a feature:

1. Open an issue using the "Feature Request" template.
2. Describe the feature, its purpose, and how it benefits users.
3. Include any mockups, diagrams, or examples, if possible.

## Code of Conduct

All contributors are expected to follow the [Code of Conduct](CODE_OF_CONDUCT.md). Be respectful, inclusive, and collaborative in all interactions.

---

Thank you for contributing to Midas Money! Together, we can help users turn their finances into gold. 💰