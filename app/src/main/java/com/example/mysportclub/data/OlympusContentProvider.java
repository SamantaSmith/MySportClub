package com.example.mysportclub.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.example.mysportclub.data.ClubOlympusContract.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class OlympusContentProvider extends ContentProvider {

    OlympusDbOpenHelper dbOpenHelper;

    public static final int MEMBERS = 111;
    public static final int MEMBER_ID = 222;

    public static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {

        uriMatcher.addURI(ClubOlympusContract.AUTHORITY,
                ClubOlympusContract.PATH_MEMBERS, MEMBERS);
        uriMatcher.addURI(ClubOlympusContract.AUTHORITY,
                ClubOlympusContract.PATH_MEMBERS + "/#", MEMBER_ID);
    }

    @Override
    public boolean onCreate() {

        dbOpenHelper = new OlympusDbOpenHelper(getContext());
        return true;
    }


    @Override
    public Cursor query( Uri uri,  String[] projection,
                         String selection,  String[] selectionArgs,
                         String sortOrder) {

        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        Cursor cursor;

        int match = uriMatcher.match(uri);

        switch(match) {

            case MEMBERS:

                cursor = db.query(MemberEntry.TABLE_NAME,
                        projection, selection, selectionArgs, null,
                        null, sortOrder);
                break;
            case MEMBER_ID:

                selection = MemberEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                cursor = db.query(MemberEntry.TABLE_NAME, projection, selection,
                    selectionArgs, null, null, sortOrder);
                break;
            default:

                Toast.makeText(getContext(), "Invalid URI", Toast.LENGTH_SHORT).show();
                throw new IllegalArgumentException("FUUUUUUUCK");

        }
        return cursor;
    }


    @Override
    public String getType( Uri uri) {
        return null;
    }


    @Override
    public Uri insert( Uri uri,  ContentValues values) {

        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();

        int match = uriMatcher.match(uri);

        switch(match) {

            case MEMBERS:

                long id = db.insert(MemberEntry.TABLE_NAME, null, values);
                if (id == -1) {

                    Log.e("insertMethod", "Insertion has not been completed");
                    return null;
                }
                return ContentUris.withAppendedId(uri, id);


            default:

                throw new IllegalArgumentException("FUUUUUUUCK");
        }

    }

    @Override
    public int delete( Uri uri,  String selection,  String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update( Uri uri,  ContentValues values,  String selection,  String[] selectionArgs) {
        return 0;
    }
}
