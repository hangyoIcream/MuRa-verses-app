// C:\data\build.gradle.kts

// 1. Apply the Android Library plugin and Kotlin Android plugin
plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
}

android {
    // 2. Define the namespace (essential for modern Android modules)
    namespace = "com.example.versesapp.data" 
    
    // 3. Set SDK versions to match the 'app' module
    compileSdk = 34 

    defaultConfig {
        minSdk = 24
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    // 4. Set Java/Kotlin compatibility versions
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    
    // The build system will now automatically include files from src/main/assets
    sourceSets["main"].resources.srcDirs("src/main/assets")
}

dependencies {
    // 5. Add required Android dependencies 
    implementation("androidx.core:core-ktx:1.12.0")
    
    // 6. Keep Gson dependency for JSON parsing
    implementation("com.google.code.gson:gson:2.10.1") 
    
    // Testing
    testImplementation(libs.junit)
}