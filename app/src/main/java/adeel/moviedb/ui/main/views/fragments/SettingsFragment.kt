package adeel.moviedb.ui.main.views.fragments

import adeel.moviedb.R
import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.*

class SettingsFragment: PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.pref_settings)

        val sharedPreference: SharedPreferences = preferenceScreen.sharedPreferences
        val prefScreen: PreferenceScreen = preferenceScreen
        val count:Int = prefScreen.preferenceCount

        for (i in 0 until count){
            val preference: Preference = prefScreen.getPreference(i)
            if (!(preference is CheckBoxPreference)){
                val multiListPreference: MultiSelectListPreference = preference as MultiSelectListPreference
                val set: Set<String>? = sharedPreference.getStringSet(preference.key, HashSet())
                if (set != null) {
                    if (set.contains("all")){
                        multiListPreference.summary = "All"
                    } else{
                        multiListPreference.summary = set.toString().replace(" ","")
                            .replace("[","").replace("]","")
                            .replace(","," |")
                    }
                } else{
                    multiListPreference.summary = "All"
                }
            }
        }

    }

    override fun onSharedPreferenceChanged(p0: SharedPreferences?, p1: String?) {
        val preference: Preference? = findPreference(p1)
        if (preference!=null){
            if (!(preference is CheckBoxPreference)){
                val multiListPreference: MultiSelectListPreference = preference as MultiSelectListPreference
                val set: Set<String>? = p0!!.getStringSet(preference.key, HashSet())
                if (set != null) {
                    if (set.contains("all")){
                        multiListPreference.summary = "All"
                    } else{
                        multiListPreference.summary = set.toString().replace(" ","")
                            .replace("[","").replace("]","")
                            .replace(","," | ")
                    }
                } else{
                    multiListPreference.summary = "All"
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

}