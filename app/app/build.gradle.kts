import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    kotlin("kapt")
}

val envProperties = Properties()
val localPropertiesFile = project.rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    envProperties.load(localPropertiesFile.inputStream())
}

android {
    namespace = "com.example.lasercraft"
    compileSdk = 34

    buildFeatures {
        compose = true
        buildConfig = true
    }

    defaultConfig {
        applicationId = "com.example.lasercraft"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        // set BuildConfig variables
        buildConfigField("String", "API_BASE_URL", "\"${envProperties.getProperty("api_base_url")}\"")
        buildConfigField("String", "MQTT_BROKER_URL", "\"${envProperties.getProperty("mqtt_broker_url")}\"")
        buildConfigField("String", "MQTT_CLIENT_ID_PREFIX", "\"${envProperties.getProperty("mqtt_client_id_prefix")}\"")
        buildConfigField("String", "MQTT_RECEIVE_IMAGE_TOPIC", "\"${envProperties.getProperty("mqtt_receive_image_topic")}\"")
        buildConfigField("String", "MQTT_USER_NAME", "\"${envProperties.getProperty("mqtt_user_name")}\"")
        buildConfigField("String", "MQTT_PASSWORD", "\"${envProperties.getProperty("mqtt_password")}\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // Camerax implementation
    val cameraxVersion = "1.3.3"
    implementation("androidx.camera:camera-core:${cameraxVersion}")
    implementation("androidx.camera:camera-camera2:${cameraxVersion}")
    implementation("androidx.camera:camera-view:${cameraxVersion}")
    implementation("androidx.camera:camera-lifecycle:$cameraxVersion")

    // Coil
    implementation("io.coil-kt:coil-compose:2.6.0")

    // Hilt
    implementation("com.google.dagger:hilt-android:2.51.1")
    kapt("com.google.dagger:hilt-compiler:2.51.1")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // Paho Mqtt
    implementation("io.github.mayzs:paho.mqtt.android:1.1.7")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")

    // Jetpack compose navigation
    val navVersion = "2.7.7"
    implementation("androidx.navigation:navigation-compose:$navVersion")

    // Mui icons
    implementation("androidx.compose.material:material-icons-extended:1.6.7")

    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.9.0")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}