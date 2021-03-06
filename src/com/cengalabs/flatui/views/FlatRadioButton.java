package com.cengalabs.flatui.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.PaintDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.widget.RadioButton;

import com.cengalabs.flatui.Attributes;
import com.cengalabs.flatui.FlatUI;
import com.cengalabs.flatui.R;

/**
 * User: eluleci
 * Date: 23.10.2013
 * Time: 22:18
 */
public class FlatRadioButton extends RadioButton implements Attributes.AttributeChangeListener {

    private Attributes attributes;

    private int dotMargin = 2;

    public FlatRadioButton(Context context) {
        super(context);
        init(null);
    }

    public FlatRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public FlatRadioButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    private void init(AttributeSet attrs) {

        if (attributes == null)
            attributes = new Attributes(this);

        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.FlatRadioButton);

            // getting common attributes
            int customTheme = a.getResourceId(R.styleable.FlatRadioButton_theme, Attributes.DEFAULT_THEME);
            attributes.setThemeSilent(customTheme, getResources());

            attributes.setFontFamily(a.getString(R.styleable.FlatRadioButton_fontFamily));
            attributes.setFontWeight(a.getString(R.styleable.FlatRadioButton_fontWeight));
            attributes.setFontExtension(a.getString(R.styleable.FlatRadioButton_fontExtension));

            attributes.setSize(a.getDimensionPixelSize(R.styleable.FlatRadioButton_size, Attributes.globalSize));
            attributes.setRadius(attributes.getSize() / 2);
            attributes.setBorderWidth(a.getDimensionPixelSize(R.styleable.FlatRadioButton_borderWidth, Attributes.globalBorderWidth));

            // getting view specific attributes
            dotMargin = a.getDimensionPixelSize(R.styleable.FlatRadioButton_dotMargin, dotMargin);

            a.recycle();
        }

        // creating unchecked-enabled state drawable
        GradientDrawable uncheckedEnabled = new GradientDrawable();
        uncheckedEnabled.setCornerRadius(attributes.getRadius());
        uncheckedEnabled.setSize(attributes.getSize(), attributes.getSize());
        uncheckedEnabled.setColor(Color.TRANSPARENT);
        uncheckedEnabled.setStroke(attributes.getBorderWidth(), attributes.getColor(2));

        // creating checked-enabled state drawable
        GradientDrawable checkedOutside = new GradientDrawable();
        checkedOutside.setCornerRadius(attributes.getSize());
        checkedOutside.setSize(attributes.getSize(), attributes.getSize());
        checkedOutside.setColor(Color.TRANSPARENT);
        checkedOutside.setStroke(attributes.getBorderWidth(), attributes.getColor(2));

        PaintDrawable checkedCore = new PaintDrawable(attributes.getColor(2));
        checkedCore.setCornerRadius(attributes.getSize());
        checkedCore.setIntrinsicHeight(attributes.getSize());
        checkedCore.setIntrinsicWidth(attributes.getSize());
        InsetDrawable checkedInside = new InsetDrawable(checkedCore,
                attributes.getBorderWidth() + dotMargin, attributes.getBorderWidth() + dotMargin,
                attributes.getBorderWidth() + dotMargin, attributes.getBorderWidth() + dotMargin);

        Drawable[] checkedEnabledDrawable = {checkedOutside, checkedInside};
        LayerDrawable checkedEnabled = new LayerDrawable(checkedEnabledDrawable);

        // creating unchecked-enabled state drawable
        GradientDrawable uncheckedDisabled = new GradientDrawable();
        uncheckedDisabled.setCornerRadius(attributes.getRadius());
        uncheckedDisabled.setSize(attributes.getSize(), attributes.getSize());
        uncheckedDisabled.setColor(Color.TRANSPARENT);
        uncheckedDisabled.setStroke(attributes.getBorderWidth(), attributes.getColor(3));

        // creating checked-disabled state drawable
        GradientDrawable checkedOutsideDisabled = new GradientDrawable();
        checkedOutsideDisabled.setCornerRadius(attributes.getRadius());
        checkedOutsideDisabled.setSize(attributes.getSize(), attributes.getSize());
        checkedOutsideDisabled.setColor(Color.TRANSPARENT);
        checkedOutsideDisabled.setStroke(attributes.getBorderWidth(), attributes.getColor(3));

        PaintDrawable checkedCoreDisabled = new PaintDrawable(attributes.getColor(3));
        checkedCoreDisabled.setCornerRadius(attributes.getRadius());
        checkedCoreDisabled.setIntrinsicHeight(attributes.getSize());
        checkedCoreDisabled.setIntrinsicWidth(attributes.getSize());
        InsetDrawable checkedInsideDisabled = new InsetDrawable(checkedCoreDisabled,
                attributes.getBorderWidth() + dotMargin, attributes.getBorderWidth() + dotMargin,
                attributes.getBorderWidth() + dotMargin, attributes.getBorderWidth() + dotMargin);

        Drawable[] checkedDisabledDrawable = {checkedOutsideDisabled, checkedInsideDisabled};
        LayerDrawable checkedDisabled = new LayerDrawable(checkedDisabledDrawable);


        StateListDrawable states = new StateListDrawable();
        states.addState(new int[]{-android.R.attr.state_checked, android.R.attr.state_enabled}, uncheckedEnabled);
        states.addState(new int[]{android.R.attr.state_checked, android.R.attr.state_enabled}, checkedEnabled);
        states.addState(new int[]{-android.R.attr.state_checked, -android.R.attr.state_enabled}, uncheckedDisabled);
        states.addState(new int[]{android.R.attr.state_checked, -android.R.attr.state_enabled}, checkedDisabled);
        setButtonDrawable(states);

        // setting padding for avoiding text to be appear on icon
        setPadding(attributes.getSize() / 4 * 5, 0, 0, 0);
        setTextColor(attributes.getColor(2));

        Typeface typeface = FlatUI.getFont(getContext(), attributes);
        if (typeface != null) setTypeface(typeface);
    }

    public Attributes getAttributes() {
        return attributes;
    }

    @Override
    public void onThemeChange() {
        init(null);
    }
}
