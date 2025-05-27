# SakeShop

## About the App
SakeShop is an app that showcases a list of sake stores. By clicking on a store, you can view its details. The app provides options to access:
- The store's location (via Google Maps or a web browser).
- The store's website.

All data is stored in a local JSON file. To demonstrate different states (success and error), the app includes a floating action button that allows users to switch from the original JSON file to an error-prone JSON file, simulating an API call failure for testing purposes.

---

## Context
The SakeShop app was developed as an assessment for a hiring process within a timeframe of 2.5 days. 

---

## Technical Specifications
- **Platform**: Android, developed in Kotlin  
  *(Note: The app wasn't developed in Swift, my primary skill, or Kotlin Multiplatform, because I currently don't have access to an Apple device and therefore wouldn't be able to test it.)*
- **Architecture**: MVVM  
  The architecture was specifically chosen to enhance testability and ensure the app is scalable for future updates.
- **Repository Interface**:
  The repository was designed with an interface to enable seamless integration of future repositories (e.g., API-based repositories).
- **UI Framework**: Jetpack Compose  
  The app’s user interface was implemented using declarative UI development with Jetpack Compose.

---

## Next Steps
- **Increase Unit Test Coverage**:  
  Currently, only some unit tests for the ViewModel were implemented due to time constraints.
- **Improve Loading State Handling**:  
  Enhance the loading experience by adding better handling and shimmering effects during image loading.
- **Develop a Repository with API**:  
  Extend the repository to fetch data from an API instead of relying on local JSON files.
- **Add User Experience Features**:  
  Implement basic features like search and filtering to improve user experience.

---

## Debug APK Location

The generated debug APK is available at the following directory: 

``` ...\SakeShop\app\build\outputs\apk\debug```
