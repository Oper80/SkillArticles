package ru.skillbranch.skillarticles.data.delegates

import ru.skillbranch.skillarticles.data.local.PrefManager
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class PrefDelegate<T>(private val defaultValue: T) {
    private var storedValue: T? = null

    operator fun provideDelegate(
        thisRef: PrefManager,
        prop: KProperty<*>
    ) : ReadWriteProperty<PrefManager, T?> {
        val key = prop.name
        return object : ReadWriteProperty<PrefManager, T?>{
            override fun getValue(thisRef: PrefManager, property: KProperty<*>): T? {
                if (storedValue == null){
                    storedValue = when (defaultValue) {
                        is Int -> thisRef.preferences.getInt(property.name, defaultValue) as T
                        is Boolean -> thisRef.preferences.getBoolean(property.name, defaultValue) as T
                        is String -> thisRef.preferences.getString(property.name, defaultValue) as T
                        is Float -> thisRef.preferences.getFloat(property.name, defaultValue) as T
                        is Long -> thisRef.preferences.getLong(property.name, defaultValue) as T
                        else -> throw IllegalArgumentException("Illegal type of property")
                    }
                }
                return storedValue
            }

            override fun setValue(thisRef: PrefManager, property: KProperty<*>, value: T?) {
                with(thisRef.preferences.edit()){
                    when (value) {
                        is Int -> putInt(property.name, value)
                        is Boolean -> putBoolean(property.name, value)
                        is String -> putString(property.name, value)
                        is Float -> putFloat(property.name, value)
                        is Long -> putLong(property.name, value)
                        else -> throw IllegalArgumentException("Illegal type of property")
                    }
                    apply()
                }
                storedValue = value
            }
        }
    }

}