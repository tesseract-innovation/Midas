# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

**Midas Money** is an Android financial management application designed to help users track expenses, plan budgets, and achieve financial goals. The project uses modern Android architecture patterns with Compose for UI, Hilt for dependency injection, and Room for local data persistence.

- **Package Name**: `com.midasmoney.app`
- **Platform**: Android (minSdk 33, targetSdk/compileSdk 35 for app; library modules compile against SDK 36)
- **Language**: Kotlin
- **UI Framework**: Jetpack Compose + Material 3
- **Build System**: Gradle with custom convention plugins (Groovy DSL in `buildSrc/`)

## Build & Development Commands

```bash
# Build debug APK
./gradlew assembleDebug

# Install debug app on connected device/emulator
./gradlew installDebug

# Run all unit tests
./gradlew test

# Run tests for a specific module
./gradlew :feature:home:test
./gradlew :core:domain:model:test

# Run instrumented tests
./gradlew connectedAndroidTest

# Lint check
./gradlew lintDebug

# Clean build
./gradlew clean build
```

**APK naming**: Debug builds are named `Midas-<git-tag>-<commit-hash>-debug.apk`; release builds use `Midas-<git-tag>-release.apk`. The version is derived from the latest git tag (must start with `v`).

**Release signing** requires these environment variables: `KEYSTORE_PATH`, `KEYSTORE_PASSWORD`, `KEY_ALIAS`, `KEY_PASSWORD`.

## Architecture Overview

### Multi-Module Structure

```
app/                          # Main application entry point
├── MainActivity.kt           # @AndroidEntryPoint, sets up theme & navigation
├── NavigationGraph.kt        # Root NavHost wiring all feature nav graphs
├── BottomNavigation.kt       # Bottom bar with 5 main destinations
└── MidasApp.kt              # @HiltAndroidApp application class

core/
├── di/                       # Hilt AppModule: database, DAOs, repositories
├── ui/                       # Shared Compose components, theme, preview annotations
├── util/                     # Common utilities (UUID, DateTime, serializers)
├── resource/                 # App resources
├── data/
│   ├── local/                # Room database, DAOs, entities, repository implementations
│   ├── remote/               # Placeholder for future API integration
│   └── mock/                 # ~700-line mock data file for previews & dev
└── domain/
    ├── model/                # Core data models (Transaction, Account, Goal, Balance, Icon)
    ├── repository/           # Repository interfaces & entity mappers
    └── usercase/             # Empty — intended for business logic extraction

feature/                      # One Gradle module per screen domain
├── home/                     # Home dashboard
├── analytics/                # Charts & reports (YCharts)
├── history/                  # Transaction history with time filtering
├── goals/                    # Financial goals management
├── account/                  # Account CRUD + nested sub-screens (detail, form, transaction form)
└── profile/                  # User profile (WIP)
```

### Key Architectural Patterns

1. **Modular feature architecture**: Each screen domain is a separate Gradle module (`feature:*`) with its own `*NavGraph.kt`, ViewModels, and UI composables. The `account` feature has nested sub-screens organized in sub-packages (`accountdetail/`, `accountform/`, `transactionform/`).

2. **Clean architecture layers**:
   - **Domain** (`core:domain`): Models + repository interfaces
   - **Data** (`core:data:local`): Room database, DAOs, entity mappers, implementations
   - **Presentation** (`feature:*`): Compose UI + ViewModels
   - **DI** (`core:di`): Hilt bindings wiring data to domain

3. **Hilt DI**: Use `@HiltViewModel` on ViewModels and `@AndroidEntryPoint` on Activities. Bindings live in `AppModule`. All feature modules include Hilt via the `midas.library` convention plugin.

4. **Navigation**: Root `NavHost` in `NavigationGraph.kt` delegates to per-feature `*NavGraph` composables. Bottom nav visibility is toggled via a `MutableState<Boolean>` passed down from `MainActivity`.

5. **Compose**: Always generate with preview  

### Custom Gradle Convention Plugins (`buildSrc/`)

- **`midas.library`**: Applied to all feature/core library modules. Includes Kotlin, Hilt (kapt), Room (kapt), Compose, serialization, kotlinx.datetime, and standard test deps. `compileSdk 36`.
- **`midas.compose`**: Adds Compose BOM dependencies to any module.
- **`midas.kotlin-android`**: Base Kotlin/Android config. `compileSdk 36`.

All versions are centralized in `gradle/libs.versions.toml`.

## Data Layer

### Room Database

- **`MidasDatabase`**: Singleton via `getDatabase(context)`, registered in `AppModule`
- **Entities**: `AccountEntity`, `TransactionEntity`
- **DAOs**: `AccountDao`, `TransactionDao` — both extend `IDao`
- **Type converters**: `InstantConverter` (kotlinx.datetime.Instant), `UUIDConverter`, `TransactionConverter`

### Repository Pattern

Repository interfaces live in `core:domain:repository`; implementations in `core:data:local:repository`. `*EntityMapper` classes handle entity ↔ domain model conversion. `BaseRepository` provides common CRUD.

### ViewModels & State

ViewModels use `MutableLiveData` (not StateFlow). Compose observes via `runtime-livedata`. Each feature module has its own `*ViewModel` injected with `@HiltViewModel`.

## Core Domain Models (`core:domain:model/`)

- **`Transaction`**: type (`EXPENSE`, `INCOME`, `TRANSFER`, `WITHDRAWAL`, `DEPOSIT`, `FEES`, `INTEREST`, `REFUND`, `RECURRING`, `TAX`, `BONUS`, `DIVIDEND`, `LOAN_PAYMENT`), status (`PENDING`, `SCHEDULED`, `COMPLETED`, `CANCELED`, `FAILED`, `APPROVED`, `REJECTED`, `ON_HOLD`, `BATCH_PROCESSING`)
- **`Account`**: name, icon, color, balance, associated transactions
- **`Goal`**: financial goal with target amount and timeline
- **`Balance`**: account balance tracking
- **`Icon`**: composable icon with custom serialization converters

All models are `@Serializable` (kotlinx.serialization).

## UI & Theme

- Material 3 design system
- Custom components in `core:ui/component/`: `MidasCard`, `MidasGradientCard`, `MidasTransactionCard`, `MidasGoalCard`, `MidasPicker`, `MidasDatePicker`, `MidasTimePicker`, etc.
- Theme in `core:ui/theme/MidasTheme.kt`; colors in `MidasColors.kt` + `ColorShades.kt`
- True black OLED mode enabled via `isTrueBlack = true` in `MainActivity`
- Preview annotations in `core:ui/preview/`

## Key Dependency Versions

| Dependency | Version |
|---|---|
| Kotlin | 2.2.20 |
| AGP | 8.13.0 |
| Compose BOM | 2025.06.01 |
| Hilt | 2.57.2 |
| Room | 2.8.4 |
| Navigation Compose | 2.9.1 |
| Material 3 | 1.4.0 |
| YCharts | 2.1.0 |
| kotlinx.serialization | 1.9.0 |
| kotlinx.datetime | 0.7.1 |
