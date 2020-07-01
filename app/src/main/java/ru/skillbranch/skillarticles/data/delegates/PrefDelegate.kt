package ru.skillbranch.skillarticles.data.delegates

import ru.skillbranch.skillarticles.data.local.PrefManager
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class PrefDelegate<T>(private val defaultValue: T) : ReadWriteProperty<PrefManager, T?> {

    override fun getValue(thisRef: PrefManager, property: KProperty<*>): T? {
        return when(defaultValue){
            is Int -> thisRef.preferences.getInt(property.name, defaultValue) as T
            is Boolean -> thisRef.preferences.getBoolean(property.name, defaultValue) as T
            is String -> thisRef.preferences.getString(property.name, defaultValue) as T
            is Float -> thisRef.preferences.getFloat(property.name, defaultValue) as T
            is Long -> thisRef.preferences.getLong(property.name, defaultValue) as T
            else -> throw IllegalArgumentException("Illegal type of property")
        }
    }

    override fun setValue(thisRef: PrefManager, property: KProperty<*>, value: T?) {
        when (value) {
            is Int -> thisRef.preferences.edit().putInt(property.name, value).apply()
            is Boolean -> thisRef.preferences.edit().putBoolean(property.name, value).apply()
            is String -> thisRef.preferences.edit().putString(property.name, value).apply()
            is Float -> thisRef.preferences.edit().putFloat(property.name, value).apply()
            is Long -> thisRef.preferences.edit().putLong(property.name, value).apply()
            else -> throw IllegalArgumentException("Illegal type of property")
        }
    }
}