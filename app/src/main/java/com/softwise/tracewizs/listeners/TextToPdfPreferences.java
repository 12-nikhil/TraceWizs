package com.softwise.tracewizs.listeners;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.annotation.NonNull;

import static com.softwise.tracewizs.utils.TraceWizConstant.DEFAULT_FONT_COLOR;
import static com.softwise.tracewizs.utils.TraceWizConstant.DEFAULT_FONT_COLOR_TEXT;
import static com.softwise.tracewizs.utils.TraceWizConstant.DEFAULT_FONT_FAMILY;
import static com.softwise.tracewizs.utils.TraceWizConstant.DEFAULT_FONT_FAMILY_TEXT;
import static com.softwise.tracewizs.utils.TraceWizConstant.DEFAULT_FONT_SIZE;
import static com.softwise.tracewizs.utils.TraceWizConstant.DEFAULT_FONT_SIZE_TEXT;
import static com.softwise.tracewizs.utils.TraceWizConstant.DEFAULT_PAGE_COLOR;
import static com.softwise.tracewizs.utils.TraceWizConstant.DEFAULT_PAGE_COLOR_TTP;
import static com.softwise.tracewizs.utils.TraceWizConstant.DEFAULT_PAGE_SIZE;
import static com.softwise.tracewizs.utils.TraceWizConstant.DEFAULT_PAGE_SIZE_TEXT;

/**
 * The {@link TextToPdfPreferences} is responsible for managing the default enhancement values
 * for the Text-to-PDF enhancements.
 */
public class TextToPdfPreferences {

    private final SharedPreferences mSharedPreferences;

    /**
     * Creates a new {@link TextToPdfPreferences}.
     *
     * @param context The {@link Context} used for the {@link SharedPreferences}.
     */
    public TextToPdfPreferences(@NonNull final Context context) {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**
     * @return the default font color.
     */
    public int getFontColor() {
        return mSharedPreferences.getInt(DEFAULT_FONT_COLOR_TEXT,
                DEFAULT_FONT_COLOR);
    }

    /**
     * Set the default font color.
     *
     * @param fontColor The font color.
     */
    public void setFontColor(final int fontColor) {
        final SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putInt(DEFAULT_FONT_COLOR_TEXT, fontColor);
        editor.apply();
    }

    /**
     * @return the default page color.
     */
    public int getPageColor() {
        return mSharedPreferences.getInt(DEFAULT_PAGE_COLOR_TTP,
                DEFAULT_PAGE_COLOR);
    }

    /**
     * Set the default page color.
     *
     * @param pageColor The page color.
     */
    public void setPageColor(final int pageColor) {
        final SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putInt(DEFAULT_PAGE_COLOR_TTP, pageColor);
        editor.apply();
    }

    /**
     * @return the default font family.
     */
    public String getFontFamily() {
        return mSharedPreferences.getString(DEFAULT_FONT_FAMILY_TEXT,
                DEFAULT_FONT_FAMILY);
    }

    /**
     * Set the default font family.
     *
     * @param fontFamily The font family.
     */
    public void setFontFamily(@NonNull final String fontFamily) {
        final SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(DEFAULT_FONT_FAMILY_TEXT, fontFamily);
        editor.apply();
    }

    /**
     * @return the default font size.
     */
    public int getFontSize() {
        return mSharedPreferences.getInt(DEFAULT_FONT_SIZE_TEXT, DEFAULT_FONT_SIZE);
    }

    /**
     * Sets the default font size.
     *
     * @param fontSize The font size.
     */
    public void setFontSize(final int fontSize) {
        final SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putInt(DEFAULT_FONT_SIZE_TEXT, fontSize);
        editor.apply();
    }

    /**
     * @return the default page size.
     */
    public String getPageSize() {
        return mSharedPreferences.getString(DEFAULT_PAGE_SIZE_TEXT, DEFAULT_PAGE_SIZE);
    }

    /**
     * Sets the default page size.
     *
     * @param pageSize The page size.
     */
    public void setPageSize(@NonNull final String pageSize) {
        final SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(DEFAULT_PAGE_SIZE_TEXT, pageSize);
        editor.apply();
    }
}
