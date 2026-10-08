# Learning Dashboard App

An Android learning dashboard application built using **Kotlin and XML**. The app demonstrates API integration, local caching, state management, offline support, and lesson progress tracking.

---

## Features

* Mock login with email/password validation and loading state
* Fetch courses from a MockAPI REST API
* Course dashboard with instructor, lesson count, and progress
* Course details with lesson completion tracking
* Automatic progress calculation
* Loading, success, empty, and error states
* Offline support using Room database caching
* Unit test for progress calculation

---

## Tech Stack

* **Language:** Kotlin
* **UI:** XML, ViewBinding, RecyclerView
* **Architecture:** MVVM + Repository
* **Networking:** Retrofit + Gson
* **Local Storage:** Room
* **Async/State:** Kotlin Coroutines + StateFlow
* **Navigation:** Android Navigation Component
* **Testing:** JUnit

---

## Architecture

```text
UI (XML)
    ↓
ViewModel / StateFlow
    ↓
Repository
    ↓
┌───────────────┐
│               │
│   Retrofit    │
│       +       │
│      Room     │
│               │
└───────┬───────┘
        ↓
    UI State
        ↓
       UI
```

The **Repository** acts as the single data access layer. Courses are fetched from the API and stored in Room. The UI observes state exposed by the ViewModel.

---

## Offline Support

When courses are successfully fetched from the API, they are stored locally in **Room**.

If the device is offline, the application can display the previously cached courses from the local database.

This provides a simple **offline fallback** without requiring a network connection for previously loaded courses.

---

## Authentication

The current login is a **mock authentication flow** created for the assignment. It validates the email and password locally and does not connect to a real authentication server.

In production, authentication would use a secure backend/API and access/refresh tokens. Sensitive credentials or tokens should not be stored in plain SharedPreferences. Secure storage such as **Android Keystore-backed solutions** would be used.

---

## Scaling Considerations

For **1M+ users and hundreds of courses**:

* Use a production backend with authentication and authorization.
* Add pagination and server-side filtering for large course lists.
* Use Room as the local cache.
* Use repository-based data access to keep API and local storage replaceable.
* Add dependency injection such as Hilt as the application grows.
* Add monitoring, logging, crash reporting, automated testing, and CI/CD.
* Use CDN/object storage for course media and optimize network/database queries.

---

## Testing

A local JVM unit test validates the course progress calculation independently from Android UI components.

**Example:**

```text
4 lessons
2 completed
2 pending
→ 50% progress
```

The test verifies that the progress calculation correctly returns **50%**.

---

## Future Platform

The same backend APIs and business requirements could support an iOS implementation using **Swift/SwiftUI**, with equivalent layers for networking, local persistence, state management, and UI.

---

## Build

* **compileSdk:** 36
* **targetSdk:** 36
* **minSdk:** 24
* **Language:** Kotlin
