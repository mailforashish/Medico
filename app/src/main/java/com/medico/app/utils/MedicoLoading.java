package com.medico.app.utils;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

import androidx.annotation.Nullable;

import com.medico.app.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class MedicoLoading extends View {
    private float circleRadius;
    private float circleMargin;
    private float animDistance;
    private long animDuration;
    private long animDelay;
    private int animInterpolator;
    private List<Integer> colors = new ArrayList<>();
    private final List<Float> positions;
    private final AnimatorSet animatorSet;
    private final Paint paint;

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        colors.add(Color.parseColor("#4285F4"));//COLOR_BLUE
        colors.add(Color.parseColor("#DB4437"));//COLOR_RED
        colors.add(Color.parseColor("#F4B400"));//COLOR_YELLOW
        colors.add(Color.parseColor("#14BFFF"));//COLOR_THEME

        float startPoint = (float) (this.getWidth() / 2) - (float) 3 * (this.circleRadius + this.circleMargin / (float) 2);
        int i = 0;

        for (byte var4 = 4; i < var4; ++i) {
            this.paint.setColor(((Number) this.colors.get(i)).intValue());
            canvas.drawCircle(startPoint, (float) this.getHeight() / 2.0F + ((Number) this.positions.get(i)).floatValue(), this.circleRadius, this.paint);
            startPoint += this.circleRadius * (float) 2 + this.circleMargin;
        }


    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.animatorSet.start();
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.animatorSet.end();
    }

    public MedicoLoading(Context context) {
        this(context, (AttributeSet) null);

    }

    public MedicoLoading(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @SuppressWarnings("unchecked")
    public MedicoLoading(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.circleRadius = 20.0F;
        this.circleMargin = 20.0F;
        this.animDistance = 50.0F;
        this.animDuration = 500L;
        this.animDelay = 150L;
        this.colors = colors;
        this.positions = Arrays.asList(new Float[]{0.0F, 0.0F, 0.0F, 0.0F});
        this.animatorSet = new AnimatorSet();
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Style.FILL);
        paint.setColor(-65536);
        this.paint = paint;
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CirclesLoadingView, 0, 0);
        this.circleRadius = typedArray.getDimension(R.styleable.CirclesLoadingView_circleRadius, this.circleRadius);
        this.circleMargin = typedArray.getDimension(R.styleable.CirclesLoadingView_circleMargin, this.circleMargin);
        this.animDistance = typedArray.getDimension(R.styleable.CirclesLoadingView_animDistance, this.animDistance);
        this.animDuration = (long) typedArray.getInt(R.styleable.CirclesLoadingView_animDuration, (int) this.animDuration);
        this.animDelay = (long) typedArray.getInt(R.styleable.CirclesLoadingView_animDelay, (int) this.animDelay);
        this.animInterpolator = typedArray.getInt(R.styleable.CirclesLoadingView_animInterpolator, this.animInterpolator);
        typedArray.recycle();
        List<Animator> animators = new ArrayList();
        int i = 0;

        for (byte bytValue = 4; i < bytValue; ++i) {
            ValueAnimator valueAnimator = ObjectAnimator.ofFloat(new float[]{0.0F, this.animDistance});
            valueAnimator.setDuration(this.animDuration);
            valueAnimator.setStartDelay((long) i * this.animDelay);
            valueAnimator.setRepeatCount(-1);
            valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
            TimeInterpolator timeInterpolator;
            switch (this.animInterpolator) {
                case 0:
                    timeInterpolator = new AccelerateInterpolator();
                    break;
                case 1:
                    timeInterpolator = new DecelerateInterpolator();
                    break;
                case 2:
                    timeInterpolator = new AccelerateDecelerateInterpolator();
                    break;
                case 3:
                    timeInterpolator = new AnticipateInterpolator();
                    break;
                case 4:
                    timeInterpolator = new AnticipateOvershootInterpolator();
                    break;
                case 5:
                    timeInterpolator = new LinearInterpolator();
                    break;
                case 6:
                    timeInterpolator = new OvershootInterpolator();
                    break;
                default:
                    timeInterpolator = new AccelerateDecelerateInterpolator();
            }

            valueAnimator.setInterpolator(timeInterpolator);
            valueAnimator.addUpdateListener((new CirclesLoadingViewSpecial(this, i)));
            animators.add(valueAnimator);
        }
        this.animatorSet.playTogether(animators);
    }

    public static final List<Float> getPositions(MedicoLoading CirclesLoadingView) {
        return CirclesLoadingView.positions;
    }
}

final class CirclesLoadingViewSpecial implements AnimatorUpdateListener {
    final MedicoLoading circlesLoadingView;
    final int inlined;

    CirclesLoadingViewSpecial(MedicoLoading circlesLoadingView, int inlined) {
        this.circlesLoadingView = circlesLoadingView;
        this.inlined = inlined;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        List<Float> list = MedicoLoading.getPositions(this.circlesLoadingView);
        int arrangeLine = this.inlined;
        Object object = valueAnimator.getAnimatedValue();
        if (object == null) {
        } else {
            list.set(arrangeLine, (Float) object);
            this.circlesLoadingView.invalidate();
        }
    }
}

