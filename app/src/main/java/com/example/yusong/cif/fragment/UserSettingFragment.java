package com.example.yusong.cif.fragment;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.example.yusong.cif.MainActivity;
import com.example.yusong.cif.R;
import com.example.yusong.cif.service.InterviewCheckService;
import com.example.yusong.cif.service.ServiceAlarmReceiver;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Yusong on 2016-05-15.
 */
public class UserSettingFragment extends PreferenceFragment {
    private OnFragmentInteractionListener mListener;

    public UserSettingFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);

        SwitchPreference interviewReminder = (SwitchPreference) findPreference("pref_key_enable_interview_reminder");
        interviewReminder.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                Intent intent = new Intent(getActivity(), ServiceAlarmReceiver.class);

                final PendingIntent pIntent = PendingIntent.getBroadcast(getActivity(), ServiceAlarmReceiver.REQUEST_CODE,
                        intent, PendingIntent.FLAG_UPDATE_CURRENT);

                long firstMillis = System.currentTimeMillis(); // alarm is set right away
                AlarmManager alarm = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);

                if((boolean)newValue) {
                    Log.d("setting", "ture");

                    //set alarm on
                    alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, firstMillis,
                            AlarmManager.INTERVAL_HALF_DAY, pIntent);

                    // show snackBar
                    Snackbar.make(getView(), "Enabled reminder", Snackbar.LENGTH_SHORT)
                            .show();
                } else {
                    Log.d("setting", "false");

                    //cancel alarm
                    alarm.cancel(pIntent);

                    Snackbar.make(getView(), "Canceled reminder", Snackbar.LENGTH_SHORT)
                            .show();
                }
                return true;
            }
        });
        Preference clean_reminder_pref = (Preference) findPreference("pref_key_clean_reminder_db");
        clean_reminder_pref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {

                return true;
            }
        });

        Preference import_sample_pref = (Preference) findPreference("pref_log_out");
        import_sample_pref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                // log out user
                // delete all user infomation
                SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("User", 0);
                SharedPreferences.Editor editor = pref.edit();
                editor.putBoolean("isLoggedIn", false);
                editor.putString("userName", null);
                editor.putString("pass", null);
                editor.commit();

                //start log in intent
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();

                // show snackBar
                Snackbar.make(getView(), "Logging out", Snackbar.LENGTH_SHORT)
                        .show();
                return true;
            }
        });

        Preference clean_course_pref = (Preference) findPreference("pref_key_clean_course_db");
        clean_course_pref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {

                // show snackBar
                Snackbar.make(getView(), "All courses removed", Snackbar.LENGTH_SHORT)
                        .show();
                return true;
            }
        });

        Preference credit = findPreference("pref_key_Credit");
        credit.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder
                        .setTitle(R.string.credit_title)
                        .setMessage(R.string.credit_content)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {}
                        });
                builder.show();
                return true;
            }
        });

    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }

}