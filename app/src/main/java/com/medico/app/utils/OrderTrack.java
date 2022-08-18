package com.medico.app.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.Dimension;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.ViewCompat;

import com.medico.app.R;

import java.util.ArrayList;
import java.util.List;

public class OrderTrack extends View {
    public static final int DISPLAY_MODE_WITH_TEXT = 0;
    public static final int DISPLAY_MODE_NO_TEXT = 1;

    private static final int START_STEP = 0;
    private int displayMode = DISPLAY_MODE_WITH_TEXT;
    private List<String> steps = new ArrayList<>();
    private int stepsNumber = 0;
    private int currentStep = START_STEP;

    @ColorInt
    private int selectedCircleColor;
    @Dimension
    private int selectedCircleRadius;
    @ColorInt
    private int selectedTextColor;
    @ColorInt
    private int doneCircleColor;
    @Dimension
    private int doneCircleRadius;
    @ColorInt
    private int doneTextColor;
    @ColorInt
    private int nextTextColor;
    @Dimension
    private int stepPadding;
    @ColorInt
    private int nextStepLineColor;
    @ColorInt
    private int doneStepLineColor;
    @Dimension
    private int stepLineWidth;
    @Dimension(unit = Dimension.SP)
    private float textSize;
    @Dimension
    private int textPadding;
    private int selectedStepNumberColor;
    @Dimension(unit = Dimension.SP)
    private float stepNumberTextSize;
    @ColorInt
    private int doneStepMarkColor;

    private Paint paint;
    private TextPaint textPaint;

    private int[] circlesX;
    private int[] startLinesX;
    private int[] endLinesX;
    private float[] constraints;
    private int circlesY;
    private int textY;
    private boolean done;
    private StaticLayout[] textLayouts;

    private Rect bounds = new Rect();

    public OrderTrack(Context context) {
        this(context, null);
    }

    public OrderTrack(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.sv_stepViewStyle);
    }

    public OrderTrack(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextAlign(Paint.Align.CENTER);
        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextAlign(Paint.Align.CENTER);
        applyStyles(context, attrs, defStyleAttr);

    }

    private void applyStyles(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.StepView, defStyleAttr, R.style.StepView);
        selectedCircleColor = ta.getColor(R.styleable.StepView_sv_selectedCircleColor, 0);
        selectedCircleRadius = ta.getDimensionPixelSize(R.styleable.StepView_sv_selectedCircleRadius, 0);
        selectedTextColor = ta.getColor(R.styleable.StepView_sv_selectedTextColor, 0);
        selectedStepNumberColor = ta.getColor(R.styleable.StepView_sv_selectedStepNumberColor, 0);
        doneStepMarkColor = ta.getColor(R.styleable.StepView_sv_doneStepMarkColor, 0);
        doneCircleColor = ta.getColor(R.styleable.StepView_sv_doneCircleColor, 0);
        doneCircleRadius = ta.getDimensionPixelSize(R.styleable.StepView_sv_doneCircleRadius, 0);
        doneTextColor = ta.getColor(R.styleable.StepView_sv_doneTextColor, 0);
        nextTextColor = ta.getColor(R.styleable.StepView_sv_nextTextColor, 0);
        stepPadding = ta.getDimensionPixelSize(R.styleable.StepView_sv_stepPadding, 0);
        nextStepLineColor = ta.getColor(R.styleable.StepView_sv_nextStepLineColor, 0);
        doneStepLineColor = ta.getColor(R.styleable.StepView_sv_doneStepLineColor, 0);
        stepLineWidth = ta.getDimensionPixelSize(R.styleable.StepView_sv_stepLineWidth, 0);
        textPadding = ta.getDimensionPixelSize(R.styleable.StepView_sv_textPadding, 0);
        stepNumberTextSize = ta.getDimension(R.styleable.StepView_sv_stepNumberTextSize, 0);
        textSize = ta.getDimension(R.styleable.StepView_sv_textSize, 0);
        stepsNumber = ta.getInteger(R.styleable.StepView_sv_stepsNumber, 0);

        CharSequence[] descriptions = ta.getTextArray(R.styleable.StepView_sv_steps);
        if (descriptions != null) {
            for (CharSequence description : descriptions) {
                steps.add(description.toString());
            }
            displayMode = DISPLAY_MODE_WITH_TEXT;
        } else {
            displayMode = DISPLAY_MODE_NO_TEXT;
        }
        Drawable background = ta.getDrawable(R.styleable.StepView_sv_background);
        if (background != null) {
            setBackgroundDrawable(background);
        }
        int fontId = ta.getResourceId(R.styleable.StepView_sv_typeface, 0);
        if (fontId != 0) {
            Typeface typeface = ResourcesCompat.getFont(context, fontId);
            setTypeface(typeface);
        }
        textPaint.setTextSize(textSize);
        ta.recycle();
    }

    private void setTypeface(Typeface typeface) {
        if (typeface != null) {
            textPaint.setTypeface(typeface);
            paint.setTypeface(typeface);
        }
    }

    public void setSteps(List<String> steps) {
        stepsNumber = 0;
        displayMode = DISPLAY_MODE_WITH_TEXT;
        this.steps.clear();
        this.steps.addAll(steps);
        requestLayout();
        setTrackStatus(START_STEP);
    }


    public void setTrackStatus(int step) {
        if (step >= START_STEP && step < getStepCount()) {
            if (startLinesX != null) {
                if (Math.abs(step - currentStep) > 1) {
                    currentStep = step;
                    invalidate();
                }
            } else {
                currentStep = step;
                invalidate();
            }
        }
    }

    public void done(boolean isDone) {
        done = isDone;
        invalidate();
    }

    public int getStepCount() {
        return displayMode == DISPLAY_MODE_WITH_TEXT ? steps.size() : stepsNumber;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = measureWidth(widthMeasureSpec);
        if (getStepCount() == 0) {
            setMeasuredDimension(width, 0);
            return;
        }
        if (width == 0) {
            setMeasuredDimension(width, 0);
            return;
        }
        measureConstraints(width);
        int height = measureHeight(heightMeasureSpec);
        setMeasuredDimension(width, height);
        measureAttributes();
    }

    private int measureWidth(int widthMeasureSpec) {
        return MeasureSpec.getSize(widthMeasureSpec);
    }

    private void measureConstraints(int width) {
        constraints = new float[getStepCount()];
        constraints[0] = width / getStepCount();
        for (int i = 1; i < constraints.length; i++) {
            constraints[i] = constraints[0] * (i + 1);
        }
    }

    private int measureHeight(int heightMeasureSpec) {
        int specSize = MeasureSpec.getSize(heightMeasureSpec);
        int specMode = MeasureSpec.getMode(heightMeasureSpec);
        int desiredSize = getPaddingTop()
                + getPaddingBottom()
                + (Math.max(selectedCircleRadius, doneCircleRadius)) * 2
                + (displayMode == DISPLAY_MODE_WITH_TEXT ? textPadding : 0);
        if (!steps.isEmpty()) {
            desiredSize += measureStepsHeight();
        }
        int result = 0;

        switch (specMode) {
            case MeasureSpec.UNSPECIFIED:
                // Parent says we can be as big as we want.
                result = desiredSize;
                break;
            case MeasureSpec.AT_MOST:
                // Parent says we can be as big as we want, up to specSize.
                // Don't be larger than specSize
                result = Math.min(desiredSize, specSize);
                break;
            case MeasureSpec.EXACTLY:
                // No choice. Do what we are told.
                result = specSize;
                break;
        }

        return result;
    }

    private int measureStepsHeight() {
        textLayouts = new StaticLayout[steps.size()];
        textPaint.setTextSize(textSize);
        int max = 0;
        for (int i = 0; i < steps.size(); i++) {
            String text = steps.get(i);
            Layout.Alignment alignment =
                    isRtl() ? Layout.Alignment.ALIGN_OPPOSITE : Layout.Alignment.ALIGN_NORMAL;
            textLayouts[i] = new StaticLayout(
                    text,
                    textPaint,
                    getMeasuredWidth() / steps.size(),
                    alignment,
                    1,
                    0,
                    true
            );
            int height = textLayouts[i].getHeight();
            max = Math.max(height, max);
        }
        return max;
    }

    @SuppressLint("WrongConstant")
    private boolean isRtl() {
        return ViewCompat.getLayoutDirection(this) == View.LAYOUT_DIRECTION_RTL;
    }

    private void measureAttributes() {
        circlesY = getCircleY();
        if (displayMode == DISPLAY_MODE_NO_TEXT) {
            circlesY += getPaddingTop();
        }
        circlesX = getCirclePositions();
        if (displayMode == DISPLAY_MODE_NO_TEXT) {
            paint.setTextSize(stepNumberTextSize);
        } else {
            paint.setTextSize(stepNumberTextSize);
            paint.setTextSize(textSize);
            textY = circlesY + selectedCircleRadius + textPadding;
        }
        measureLines();
    }

    private int getCircleY() {
        int availableHeight = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();
        if (displayMode == DISPLAY_MODE_NO_TEXT) {
            return availableHeight / 2;
        }
        int maxItemHeight = getMaxTextHeight() + Math.max(selectedCircleRadius, doneCircleRadius) + textPadding;
        int additionalPadding = (availableHeight - maxItemHeight) / 2;
        return getPaddingTop() + additionalPadding + selectedCircleRadius;
    }

    private int getMaxTextHeight() {
        int max = 0;
        if (textLayouts == null || textLayouts.length == 0) {
            return max;
        }
        for (StaticLayout tl : textLayouts) {
            max = Math.max(tl.getHeight(), max);
        }
        return max;
    }

    private int[] getCirclePositions() {
        int stepsCount = getStepCount();
        int[] result = new int[stepsCount];

        if (result.length == 0) {
            return result;
        }

        result[0] = getStartCirclePosition();

        if (result.length == 1) {
            return result;
        }

        result[stepsCount - 1] = getEndCirclePosition();

        if (result.length < 3) {
            return result;
        }

        float spaceLeft = isRtl() ? result[0] - result[stepsCount - 1] : result[stepsCount - 1] - result[0];
        int margin = (int) (spaceLeft / (stepsCount - 1));

        if (isRtl()) {
            for (int i = 1; i < stepsCount - 1; i++) {
                result[i] = result[i - 1] - margin;
            }
        } else {
            for (int i = 1; i < stepsCount - 1; i++) {
                result[i] = result[i - 1] + margin;
            }
        }

        return result;
    }

    private int getStartCirclePosition() {
        int result;
        if (displayMode == DISPLAY_MODE_WITH_TEXT) {
            if (isRtl()) {
                result = getMeasuredWidth() - getPaddingRight() -
                        Math.max(getMaxLineWidth(textLayouts[0]) / 2, selectedCircleRadius);
            } else {
                result = getPaddingLeft() + Math.max(getMaxLineWidth(textLayouts[0]) / 2, selectedCircleRadius);
            }
        } else {
            if (isRtl()) {
                result = getMeasuredWidth() - getPaddingRight() - selectedCircleRadius;
            } else {
                result = getPaddingLeft() + selectedCircleRadius;
            }
        }
        return result;
    }

    private int getMaxLineWidth(StaticLayout layout) {
        int lineCount = layout.getLineCount();
        int max = 0;
        for (int i = 0; i < lineCount; i++) {
            max = (int) Math.max(layout.getLineWidth(i), max);
        }
        return max;
    }

    private int getEndCirclePosition() {
        int result;
        if (displayMode == DISPLAY_MODE_WITH_TEXT) {
            if (isRtl()) {
                result = getPaddingLeft() +
                        Math.max(getMaxLineWidth(last(textLayouts)) / 2, selectedCircleRadius);
            } else {
                result = getMeasuredWidth() - getPaddingRight() -
                        Math.max(getMaxLineWidth(last(textLayouts)) / 2, selectedCircleRadius);
            }
        } else {
            if (isRtl()) {
                result = getPaddingLeft() + selectedCircleRadius;
            } else {
                result = getMeasuredWidth() - getPaddingRight() - selectedCircleRadius;
            }
        }
        return result;
    }

    private <T> T last(T[] array) {
        return array[array.length - 1];
    }

    private void measureLines() {
        startLinesX = new int[getStepCount() - 1];
        endLinesX = new int[getStepCount() - 1];
        int padding = stepPadding + selectedCircleRadius;

        for (int i = 1; i < getStepCount(); i++) {
            if (isRtl()) {
                startLinesX[i - 1] = circlesX[i - 1] - padding;
                endLinesX[i - 1] = circlesX[i] + padding;
            } else {
                startLinesX[i - 1] = circlesX[i - 1] + padding;
                endLinesX[i - 1] = circlesX[i] - padding;
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (getHeight() == 0) return;

        final int stepSize = getStepCount();

        if (stepSize == 0) {
            return;
        }

        for (int i = 0; i < stepSize; i++) {
            drawStep(canvas, i, circlesX[i], circlesY);
        }

        for (int i = 0; i < startLinesX.length; i++) {
           if (i < currentStep) {
                drawLine(canvas, startLinesX[i], endLinesX[i], circlesY, true);
            } else {
                drawLine(canvas, startLinesX[i], endLinesX[i], circlesY, false);
            }
        }
    }

    private void drawStep(Canvas canvas, int step, int circleCenterX, int circleCenterY) {
        // todo: fix alpha for text when going back/forward
        // todo: don't scale up/down numbers if circles are not scaled
        final String text = displayMode == DISPLAY_MODE_WITH_TEXT ? steps.get(step) : "";
        final boolean isSelected = step == currentStep;
        final boolean isDone = done ? step <= currentStep : step < currentStep;
        final String number = String.valueOf(step + 1);

        if (isSelected && !isDone) {
            paint.setColor(selectedCircleColor);
            int radius;
            radius = selectedCircleRadius;
            canvas.drawCircle(circleCenterX, circleCenterY, radius, paint);
            paint.setColor(selectedStepNumberColor);
            paint.setTextSize(stepNumberTextSize);
            drawNumber(canvas, number, circleCenterX, paint);
            textPaint.setTextSize(textSize);
            textPaint.setColor(selectedTextColor);
            drawText(canvas, text, textY, step);
        } else if (isDone) {
            paint.setColor(doneCircleColor);
            canvas.drawCircle(circleCenterX, circleCenterY, doneCircleRadius, paint);
            drawCheckMark(canvas, circleCenterX, circleCenterY);
            textPaint.setTextSize(textSize);
            textPaint.setColor(doneTextColor);
            drawText(canvas, text, textY, step);
        } else {
            paint.setColor(nextTextColor);
            paint.setTextSize(stepNumberTextSize);
            drawNumber(canvas, number, circleCenterX, paint);
            textPaint.setTextSize(textSize);
            textPaint.setColor(nextTextColor);
            drawText(canvas, text, textY, step);
        }
    }

    private void drawNumber(Canvas canvas, String number, int circleCenterX, Paint paint) {
        paint.getTextBounds(number, 0, number.length(), bounds);
        float y = circlesY + bounds.height() / 2f - bounds.bottom;
        canvas.drawText(number, circleCenterX, y, paint);
    }

    private void drawText(Canvas canvas, String text, int y, int step) {
        if (text.isEmpty()) {
            return;
        }
        StaticLayout layout = textLayouts[step];
        canvas.save();
        canvas.translate(circlesX[step], y);
        layout.draw(canvas);
        canvas.restore();
    }

    private void drawCheckMark(Canvas canvas, int circleCenterX, int circleCenterY) {
        paint.setColor(doneStepMarkColor);
        float width = stepNumberTextSize * 0.1f;
        paint.setStrokeWidth(width);
        Rect bounds = new Rect(
                (int) (circleCenterX - width * 4.5),
                (int) (circleCenterY - width * 3.5),
                (int) (circleCenterX + width * 4.5),
                (int) (circleCenterY + width * 3.5));
        canvas.drawLine(
                bounds.left + 0.5f * width,
                bounds.bottom - 3.25f * width,
                bounds.left + 3.25f * width,
                bounds.bottom - 0.75f * width, paint);
        canvas.drawLine(
                bounds.left + 2.75f * width,
                bounds.bottom - 0.75f * width,
                bounds.right - 0.375f * width,
                bounds.top + 0.75f * width, paint);
    }

    private void drawLine(Canvas canvas, int startX, int endX, int centerY, boolean highlight) {
        if (highlight) {
            paint.setColor(doneStepLineColor);
            paint.setStrokeWidth(stepLineWidth);
            canvas.drawLine(startX, centerY, endX, centerY, paint);
        } else {
            paint.setColor(nextStepLineColor);
            paint.setStrokeWidth(stepLineWidth);
            canvas.drawLine(startX, centerY, endX, centerY, paint);
        }
    }

}
