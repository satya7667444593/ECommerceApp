# 🛒 E-Commerce Android Application

A modern, full-featured e-commerce Android application built with Jetpack Compose, Firebase, and following Clean Architecture principles with MVVM pattern.

---

## 📱 Features

### ✅ Core Features
- **User Authentication**
  - Email/Password registration and login
  - Secure Firebase Authentication
  - Password validation and error handling
  - Session management

- **Product Management**
  - Upload products with multiple images (minimum 3) "due to some charges of firebase i cant use storage"
  - View all products in a scrollable list
  - Search products by title, description, or category
  - Filter products by category
  - Real-time product updates

- **Product Details**
  - View complete product information
  - Image gallery with swipe navigation
  - Seller contact information
  - Add/Remove from favorites
  - Back navigation

- **Favorites System**
  - Add products to favorites
  - Local storage using Room Database
  - Persistent across app sessions
  - Remove from favorites
  - View all saved favorites

- **Upload Products**
  - Multi-image picker (up to 5 images)
  - Image preview with remove option
  - Form validation
  - Category selection
  - Price and description fields
  - Upload progress indicator

### 🎁 Bonus Features
- **Recommended Products** - Integrated FakeStore API for product recommendations
- **Pull-to-Refresh** - Refresh product listings
- **Search Functionality** - Real-time product search
- **Category Filters** - Filter by product categories
- **Shimmer Loading** - Skeleton loading placeholders
- **Empty States** - User-friendly empty state designs
- **Error Handling** - Comprehensive error handling with retry options

---

## 🏗️ Architecture & Design Patterns

### Clean Architecture
```
Presentation Layer (UI)
    ↓
Domain Layer (Use Cases)
    ↓
Data Layer (Repositories)
    ↓
Data Sources (Remote/Local)
```

### MVVM (Model-View-ViewModel)
- **Model**: Data classes and business logic
- **View**: Composable UI components
- **ViewModel**: UI state management and business logic

### Dependency Injection
- **Hilt/Dagger**: Provides compile-time dependency injection
- Modular architecture with separate modules for different concerns

---

## 🛠️ Tech Stack

### Core Technologies
- **Language**: Kotlin 1.9.20
- **Minimum SDK**: API 24 (Android 7.0)
- **Target SDK**: API 34 (Android 14)
- **Build System**: Gradle (Kotlin DSL)

### UI Framework
- **Jetpack Compose**: Modern declarative UI toolkit
- **Material Design 3**: Latest Material Design components
- **Coil**: Image loading and caching
- **Navigation Compose**: Type-safe navigation

### Backend & Database
- **Firebase Authentication**: User authentication
- **Firebase Firestore**: Real-time NoSQL database
- **Firebase Storage**: Image and file storage
- **Room Database**: Local SQLite database for favorites

### Networking
- **Retrofit**: REST API client
- **OkHttp**: HTTP client with logging interceptor
- **Gson**: JSON serialization/deserialization

### Dependency Injection
- **Hilt**: Dependency injection framework
- **Dagger**: Compile-time dependency injection

### Coroutines & Flow
- **Kotlin Coroutines**: Asynchronous programming
- **Kotlin Flow**: Reactive data streams
- **StateFlow**: UI state management

---

## 📂 Project Structure

```
com.example.ecommerce/
│
├── data/
│   ├── local/
│   │   ├── dao/                    # Room DAOs
│   │   ├── database/               # Room Database
│   │   └── entity/                 # Room Entities
│   ├── remote/
│   │   ├── api/                    # Retrofit API interfaces
│   │   ├── firebase/               # Firebase managers
│   │   └── model/                  # Data models
│   └── repository/                 # Repository implementations
│
├── di/                             # Dependency Injection modules
│   ├── AppModule.kt
│   ├── DatabaseModule.kt
│   ├── NetworkModule.kt
│   └── RepositoryModule.kt
│
├── ui/
│   ├── auth/                       # Login & Registration
│   ├── home/                       # Home screen with products
│   ├── productdetail/              # Product details
│   ├── upload/                     # Upload products
│   ├── favorites/                  # Favorites screen
│   ├── components/                 # Reusable UI components
│   ├── navigation/                 # Navigation setup
│   └── theme/                      # App theming
│
├── util/                           # Utility classes
│   ├── Constants.kt
│   └── Resource.kt                 # Sealed class for API states
│
├── MainActivity.kt                 # Entry point
└── ECommerceApplication.kt         # Application class
```

---

## 🚀 Getting Started

### Prerequisites
- Android Studio Hedgehog (2025.2.1) or newer
- JDK 17 or higher
- Android SDK (API 24+)
- Firebase account
- Internet connection

### Installation Steps

#### 1. Clone the Repository
```bash
git clone https://github.com/yourusername/ecommerce-android.git
cd ecommerce-android
```

#### 2. Setup Firebase

**a. Create Firebase Project**
1. Go to [Firebase Console](https://console.firebase.google.com/)
2. Click "Add Project"
3. Enter project name and follow setup wizard

**b. Add Android App**
1. Click Android icon in Firebase project
2. Register app with package name: `com.example.ecommerce`
3. Download `google-services.json`
4. Place file in `app/` directory

**c. Enable Firebase Services**

**Authentication:**
- Go to Authentication → Sign-in method
- Enable Email/Password
- Save

**Firestore:**
- Go to Firestore Database → Create database
- Start in test mode
- Update rules:
```javascript
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    match /users/{userId} {
      allow read: if request.auth != null;
      allow write: if request.auth != null && request.auth.uid == userId;
    }
    
    match /products/{productId} {
      allow read: if request.auth != null;
      allow create: if request.auth != null;
      allow update, delete: if request.auth != null && 
        request.auth.uid == resource.data.uploaderId;
    }
  }
}
```

**Storage:**
- Go to Storage → Get Started
- Update rules:
```javascript
rules_version = '2';
service firebase.storage {
  match /b/{bucket}/o {
    match /product_images/{allPaths=**} {
      allow read: if true;
      allow write: if request.auth != null;
    }
  }
}
```

#### 3. Open in Android Studio
1. Open Android Studio
2. File → Open → Select project folder
3. Wait for Gradle sync

#### 4. Build and Run
1. Connect device or start emulator
2. Click Run ▶️ button
3. Wait for app to install
4. App should launch successfully

---

## 🎨 Screenshots

### Authentication
| Login | Register |
|-------|----------|
| ![Login Screen](screenshots/login.png) | ![Register Screen](screenshots/register.png) |

### Main Features
| Home | Product Detail | Upload |
|------|----------------|--------|
| ![Home Screen](screenshots/home.png) | ![Product Detail](screenshots/detail.png) | ![Upload Screen](screenshots/upload.png) |

### Additional Features
| Favorites | Search | Filter |
|-----------|--------|--------|
| ![Favorites](screenshots/favorites.png) | ![Search](screenshots/search.png) | ![Filter](screenshots/filter.png) |

---

## 📦 Dependencies

### Build Configuration
```kotlin
dependencies {
    // Core Android
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.1")

    // Compose
    implementation(platform("androidx.compose:compose-bom:2023.10.01"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.navigation:navigation-compose:2.7.5")

    // Hilt
    implementation("com.google.dagger:hilt-android:2.48")
    kapt("com.google.dagger:hilt-android-compiler:2.48")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")

    // Room
    implementation("androidx.room:room-runtime:2.6.0")
    implementation("androidx.room:room-ktx:2.6.0")
    kapt("androidx.room:room-compiler:2.6.0")

    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:32.7.0"))
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation("com.google.firebase:firebase-storage-ktx")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // Coil
    implementation("io.coil-kt:coil-compose:2.5.0")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.7.3")
}
```

---

## 🧪 Testing

### Unit Tests
Located in `app/src/test/`

Run tests:
```bash
./gradlew test
```

### UI Tests
Located in `app/src/androidTest/`

Run UI tests:
```bash
./gradlew connectedAndroidTest
```

---

## 🐛 Troubleshooting

### Common Issues

**Issue 1: Build Fails**
```
Solution: 
- File → Invalidate Caches → Restart
- Check google-services.json is in app/ folder
- Verify internet connection
```

**Issue 2: App Crashes on Launch**
```
Solution:
- Check @HiltAndroidApp is on Application class
- Check @AndroidEntryPoint is on MainActivity
- Verify AndroidManifest references .ECommerceApplication
```

**Issue 3: Upload Fails**
```
Solution:
- Update Firebase Storage rules
- Check user is logged in
- Verify internet connection
```

**Issue 4: Can't Click Products**
```
Solution:
- Verify ProductCard has .clickable(onClick = onClick)
- Check NavGraph has ProductDetail route
- Rebuild project
```

---

## 📝 API Documentation

### FakeStore API
External API for recommended products: https://fakestoreapi.com/

**Endpoint:**
```
GET https://fakestoreapi.com/products
```

**Response:**
```json
[
  {
    "id": 1,
    "title": "Product Name",
    "price": 109.95,
    "description": "Product description",
    "category": "electronics",
    "image": "https://...",
    "rating": {
      "rate": 3.9,
      "count": 120
    }
  }
]
```

---

## 🔐 Security

### Best Practices Implemented
- ✅ Firebase Authentication for user management
- ✅ Firestore Security Rules for data protection
- ✅ Storage Rules for file access control
- ✅ Input validation on all forms
- ✅ Secure password handling (minimum 6 characters)
- ✅ No hardcoded API keys (using google-services.json)

### Recommended Production Changes
- Implement email verification
- Add password strength requirements
- Implement rate limiting
- Add user roles and permissions
- Enable App Check for Firebase
- Implement proper error logging (Crashlytics)

---

## 🚀 Future Enhancements

### Planned Features
- [ ] User profiles with avatars
- [ ] Product reviews and ratings
- [ ] Shopping cart functionality
- [ ] Order management system
- [ ] Payment integration (Stripe/PayPal)
- [ ] Push notifications
- [ ] Chat between buyers and sellers
- [ ] Product wishlist
- [ ] Order history
- [ ] Multiple images per product with zoom
- [ ] Social media sharing
- [ ] Dark mode support
- [ ] Multi-language support
- [ ] Offline mode with sync

---

## 👥 Contributing

Contributions are welcome! Please follow these steps:

1. Fork the project
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

### Code Style
- Follow [Kotlin Coding Conventions](https://kotlinlang.org/docs/coding-conventions.html)
- Use meaningful variable and function names
- Add comments for complex logic
- Write unit tests for new features

---

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

```
MIT License

Copyright (c) 2024 Your Name

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

---

## 👨‍💻 Author

**Satya prakash verma**
- GitHub: [@satya7667444593](https://github.com/satya7667444593)
- LinkedIn: [satya prakash verma](https://linkedin.com/in/satya-prakash-verma-)
- Email: satya766744@gmail.com

---

## 🙏 Acknowledgments

- [Firebase](https://firebase.google.com/) - Backend services
- [Jetpack Compose](https://developer.android.com/jetpack/compose) - UI Framework
- [Material Design 3](https://m3.material.io/) - Design system
- [FakeStore API](https://fakestoreapi.com/) - Recommended products
- [Coil](https://coil-kt.github.io/coil/) - Image loading
- [Hilt](https://dagger.dev/hilt/) - Dependency injection

---

## 📊 Project Stats

- **Total Files**: 50+
- **Lines of Code**: 5000+
- **Screens**: 6 main screens
- **Components**: 15+ reusable components
- **Development Time**: 40+ hours
- **Last Updated**: november 2025

---

## 📞 Support

If you have any questions or issues:

1. Check the [Troubleshooting](#-troubleshooting) section
2. Search [existing issues](https://github.com/satya7667444593/ecommerce-android/issues)
3. Create a [new issue](https://github.com/satya7667444593/ecommerce-android/issues/new)
4. Email:satya766744@gmail.com

---

## ⭐ Star History

If you find this project useful, please consider giving it a star! ⭐

[![Star History Chart](https://api.star-history.com/svg?repos=satya7667444593/ecommerce-android&type=Date)](https://star-history.com/#satya7667444593/ecommerce-android&Date)

---

## 🎓 Learning Resources

### Recommended Tutorials
- [Jetpack Compose Basics](https://developer.android.com/courses/pathways/compose)
- [Firebase for Android](https://firebase.google.com/docs/android/setup)
- [MVVM Architecture](https://developer.android.com/topic/architecture)
- [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)
- [Hilt Dependency Injection](https://developer.android.com/training/dependency-injection/hilt-android)

---

## 📱 Download

### APK Releases
Coming soon! Check the [Releases](https://github.com/satya7667444593/ecommerce-android/releases) page.

### Google Play Store
Coming soon!

---

<div align="center">

**Made with ❤️ using Kotlin and Jetpack Compose**

⭐ **If you like this project, please give it a star!** ⭐

</div>
