# Room Database keep rules
-keep class * extends androidx.room.RoomDatabase { *; }
-keep class * extends androidx.room.Entity { *; }
-keep @androidx.room.Entity class * { *; }
-keep @androidx.room.Dao class * { *; }
-dontwarn androidx.room.paging.**

# Keep generated Room DB implementations
-keep class **_Impl { *; }

# Keep App Data Layer, Entities, DAOs, and Models
-keep class com.sankranthivinod.expensebudgettracker.data.** { *; }
-keep class com.sankranthivinod.expensebudgettracker.model.** { *; }
-keep class com.sankranthivinod.expensebudgettracker.ui.** { *; }

# Kotlin Coroutines
-keepclassmembers class * {
    @kotlinx.coroutines.InternalCoroutinesApi *;
}
