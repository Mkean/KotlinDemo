package com.mkean.network.permission.checker;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.provider.Telephony;

import androidx.annotation.RequiresApi;

/**
 * 测试读取权限权限
 */
public class SmsReadTest implements PermissionTest {
    @SuppressLint("NewApi")
    @Override
    public boolean test(Context context) {
        ContentResolver resolver = context.getContentResolver();

        String[] projection = new String[]{Telephony.Sms._ID, Telephony.Sms.ADDRESS, Telephony.Sms.PERSON,
                Telephony.Sms.BODY};
        Cursor cursor = resolver.query(Telephony.Sms.CONTENT_URI, projection, null, null, null);
        if (cursor != null) {
            try {
                CursorTest.read(cursor);
            } finally {
                cursor.close();
            }
            return true;
        } else {
            return false;
        }
    }
}
