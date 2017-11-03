package com.qslib.customview.recycleview.swipe;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.HapticFeedbackConstants;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.FrameLayout;

import com.qslib.library.R;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SwipeLayout extends FrameLayout {
    @Deprecated
    public static final int EMPTY_LAYOUT = -1;
    public static final int DRAG_LEFT = 1;
    public static final int DRAG_RIGHT = 2;
    public static final int DRAG_TOP = 4;
    public static final int DRAG_BOTTOM = 8;
    public static final DragEdge DefaultDragEdge = DragEdge.Right;

    private int mTouchSlop = 0;
    private int mDragDistance = 0;
    private int mEventCounter = 0;
    private float sX = -1, sY = -1;

    private DragEdge mCurrentDragEdge = DefaultDragEdge;
    private ViewDragHelper mDragHelper;
    private ShowMode mShowMode;
    private DoubleClickListener mDoubleClickListener;
    private List<OnLayout> mOnLayoutListeners;
    private OnClickListener clickListener;
    private OnLongClickListener longClickListener;
    private Rect hitSurfaceRect;

    private float[] mEdgeSwipesOffset = new float[4];
    private float mWillOpenPercentAfterOpen = 0.75f;
    private float mWillOpenPercentAfterClose = 0.25f;

    private LinkedHashMap<DragEdge, View> mDragEdges = new LinkedHashMap<>();
    private List<SwipeListener> mSwipeListeners = new ArrayList<>();
    private List<SwipeDenier> mSwipeDeniers = new ArrayList<>();
    private Map<View, ArrayList<OnRevealListener>> mRevealListeners = new HashMap<>();
    private Map<View, Boolean> mShowEntirely = new HashMap<>();
    private Map<View, Rect> mViewBoundCache = new HashMap<>();//save all children's bound, restore in onLayout

    private boolean mIsBeingDragged;
    private boolean mSwipeEnabled = true;
    private boolean[] mSwipesEnabled = new boolean[]{true, true, true, true};
    private boolean mClickToClose = false;

    public enum DragEdge {
        Left,
        Top,
        Right,
        Bottom
    }

    public enum ShowMode {
        LayDown,
        PullOut
    }

    public SwipeLayout(Context context) {
        this(context, null);
    }

    public SwipeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        try {
            mDragHelper = ViewDragHelper.create(this, mDragHelperCallback);
            mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SwipeLayout);
            mEdgeSwipesOffset[DragEdge.Left.ordinal()] = typedArray.getDimension(R.styleable.SwipeLayout_leftEdgeSwipeOffset, 0);
            mEdgeSwipesOffset[DragEdge.Right.ordinal()] = typedArray.getDimension(R.styleable.SwipeLayout_rightEdgeSwipeOffset, 0);
            mEdgeSwipesOffset[DragEdge.Top.ordinal()] = typedArray.getDimension(R.styleable.SwipeLayout_topEdgeSwipeOffset, 0);
            mEdgeSwipesOffset[DragEdge.Bottom.ordinal()] = typedArray.getDimension(R.styleable.SwipeLayout_bottomEdgeSwipeOffset, 0);

            // click to close
            setClickToClose(typedArray.getBoolean(R.styleable.SwipeLayout_clickToClose, mClickToClose));

            // get drag
            int dragEdgeChoices = typedArray.getInt(R.styleable.SwipeLayout_dragEdge, DRAG_RIGHT);

            // left
            if ((dragEdgeChoices & DRAG_LEFT) == DRAG_LEFT) {
                mDragEdges.put(DragEdge.Left, null);
            }

            // top
            if ((dragEdgeChoices & DRAG_TOP) == DRAG_TOP) {
                mDragEdges.put(DragEdge.Top, null);
            }

            // right
            if ((dragEdgeChoices & DRAG_RIGHT) == DRAG_RIGHT) {
                mDragEdges.put(DragEdge.Right, null);
            }

            // bottom
            if ((dragEdgeChoices & DRAG_BOTTOM) == DRAG_BOTTOM) {
                mDragEdges.put(DragEdge.Bottom, null);
            }

            // get show mode
            int ordinal = typedArray.getInt(R.styleable.SwipeLayout_showMode, ShowMode.PullOut.ordinal());
            this.mShowMode = ShowMode.values()[ordinal];

            // recycle attribute
            typedArray.recycle();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface SwipeListener {
        void onStartOpen(SwipeLayout swipeLayout);

        void onOpen(SwipeLayout swipeLayout);

        void onStartClose(SwipeLayout swipeLayout);

        void onClose(SwipeLayout swipeLayout);

        void onUpdate(SwipeLayout swipeLayout, int leftOffset, int topOffset);

        void onHandRelease(SwipeLayout swipeLayout, float xvel, float yvel);
    }

    public SwipeLayout addSwipeListener(SwipeListener swipeListener) {
        mSwipeListeners.add(swipeListener);
        return this;
    }

    public SwipeLayout removeSwipeListener(SwipeListener swipeListener) {
        mSwipeListeners.remove(swipeListener);
        return this;
    }

    public SwipeLayout removeAllSwipeListener() {
        mSwipeListeners.clear();
        return this;
    }

    public interface SwipeDenier {
        /*
         * Called in onInterceptTouchEvent Determines if this swipe event should
         * be denied Implement this interface if you are using views with swipe
         * gestures As a child of SwipeLayout
         * 
         * @return true deny false allow
         */
        boolean shouldDenySwipe(MotionEvent motionEvent);
    }

    public SwipeLayout addSwipeDenier(SwipeDenier swipeDenier) {
        mSwipeDeniers.add(swipeDenier);
        return this;
    }

    public SwipeLayout removeSwipeDenier(SwipeDenier swipeDenier) {
        mSwipeDeniers.remove(swipeDenier);
        return this;
    }

    public SwipeLayout removeAllSwipeDeniers() {
        mSwipeDeniers.clear();
        return this;
    }

    public interface OnRevealListener {
        void onReveal(View view, DragEdge edge, float fraction, int distance);
    }

    /**
     * @param childId
     * @param onRevealListener
     * @return
     */
    public SwipeLayout addRevealListener(int childId, OnRevealListener onRevealListener) {
        try {
            View view = findViewById(childId);
            if (view == null) {
                throw new IllegalArgumentException("Child does not belong to SwipeListener.");
            }

            if (!mShowEntirely.containsKey(view)) {
                mShowEntirely.put(view, false);
            }

            if (mRevealListeners.get(view) == null)
                mRevealListeners.put(view, new ArrayList<OnRevealListener>());

            mRevealListeners.get(view).add(onRevealListener);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        return this;
    }

    /**
     * @param childIds
     * @param onRevealListener
     */
    public SwipeLayout addRevealListener(int[] childIds, OnRevealListener onRevealListener) {
        try {
            for (int childId : childIds) addRevealListener(childId, onRevealListener);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    public SwipeLayout removeRevealListener(int childId, OnRevealListener onRevealListener) {
        try {
            View view = findViewById(childId);
            if (view == null) return this;

            mShowEntirely.remove(view);
            if (mRevealListeners.containsKey(view))
                mRevealListeners.get(view).remove(onRevealListener);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    public SwipeLayout removeAllRevealListeners(int childId) {
        try {
            View view = findViewById(childId);
            if (view != null) {
                mRevealListeners.remove(view);
                mShowEntirely.remove(view);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    private ViewDragHelper.Callback mDragHelperCallback = new ViewDragHelper.Callback() {
        boolean isCloseBeforeDrag = true;

        @Override
        public int clampViewPositionHorizontal(View view, int left, int dx) {
            try {
                if (view == getSurfaceView()) {
                    switch (mCurrentDragEdge) {
                        case Top:
                        case Bottom:
                            return getPaddingLeft();
                        case Left:
                            if (left < getPaddingLeft()) return getPaddingLeft();
                            if (left > getPaddingLeft() + mDragDistance)
                                return getPaddingLeft() + mDragDistance;
                            break;
                        case Right:
                            if (left > getPaddingLeft()) return getPaddingLeft();
                            if (left < getPaddingLeft() - mDragDistance)
                                return getPaddingLeft() - mDragDistance;
                            break;
                    }
                } else if (getCurrentBottomView() == view) {
                    switch (mCurrentDragEdge) {
                        case Top:
                        case Bottom:
                            return getPaddingLeft();
                        case Left:
                            if (mShowMode == ShowMode.PullOut) {
                                if (left > getPaddingLeft()) return getPaddingLeft();
                            }
                            break;
                        case Right:
                            if (mShowMode == ShowMode.PullOut) {
                                if (left < getMeasuredWidth() - mDragDistance) {
                                    return getMeasuredWidth() - mDragDistance;
                                }
                            }
                            break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return left;
        }

        @Override
        public int clampViewPositionVertical(View view, int top, int dy) {
            try {
                if (view == getSurfaceView()) {
                    switch (mCurrentDragEdge) {
                        case Left:
                        case Right:
                            return getPaddingTop();
                        case Top:
                            if (top < getPaddingTop()) return getPaddingTop();
                            if (top > getPaddingTop() + mDragDistance)
                                return getPaddingTop() + mDragDistance;
                            break;
                        case Bottom:
                            if (top < getPaddingTop() - mDragDistance) {
                                return getPaddingTop() - mDragDistance;
                            }
                            if (top > getPaddingTop()) {
                                return getPaddingTop();
                            }
                    }
                } else {
                    View surfaceView = getSurfaceView();
                    int surfaceViewTop = surfaceView == null ? 0 : surfaceView.getTop();
                    switch (mCurrentDragEdge) {
                        case Left:
                        case Right:
                            return getPaddingTop();
                        case Top:
                            if (mShowMode == ShowMode.PullOut) {
                                if (top > getPaddingTop()) return getPaddingTop();
                            } else {
                                if (surfaceViewTop + dy < getPaddingTop())
                                    return getPaddingTop();
                                if (surfaceViewTop + dy > getPaddingTop() + mDragDistance)
                                    return getPaddingTop() + mDragDistance;
                            }
                            break;
                        case Bottom:
                            if (mShowMode == ShowMode.PullOut) {
                                if (top < getMeasuredHeight() - mDragDistance)
                                    return getMeasuredHeight() - mDragDistance;
                            } else {
                                if (surfaceViewTop + dy >= getPaddingTop())
                                    return getPaddingTop();
                                if (surfaceViewTop + dy <= getPaddingTop() - mDragDistance)
                                    return getPaddingTop() - mDragDistance;
                            }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return top;
        }

        @Override
        public boolean tryCaptureView(View view, int pointerId) {
            boolean result = false;
            try {
                result = view == getSurfaceView() || getBottomViews().contains(view);
                if (result) {
                    isCloseBeforeDrag = getOpenStatus() == Status.Close;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        public int getViewHorizontalDragRange(View view) {
            return mDragDistance;
        }

        @Override
        public int getViewVerticalDragRange(View view) {
            return mDragDistance;
        }

        @Override
        public void onViewReleased(View releasedView, float xvel, float yvel) {
            super.onViewReleased(releasedView, xvel, yvel);
            try {
                processHandRelease(xvel, yvel, isCloseBeforeDrag);
                for (SwipeListener swipeListener : mSwipeListeners) {
                    swipeListener.onHandRelease(SwipeLayout.this, xvel, yvel);
                }
                invalidate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            try {
                View surfaceView = getSurfaceView();
                if (surfaceView == null) return;

                View currentBottomView = getCurrentBottomView();
                int evLeft = surfaceView.getLeft(),
                        evRight = surfaceView.getRight(),
                        evTop = surfaceView.getTop(),
                        evBottom = surfaceView.getBottom();

                if (changedView == surfaceView) {
                    if (mShowMode == ShowMode.PullOut && currentBottomView != null) {
                        if (mCurrentDragEdge == DragEdge.Left || mCurrentDragEdge == DragEdge.Right) {
                            currentBottomView.offsetLeftAndRight(dx);
                        } else {
                            currentBottomView.offsetTopAndBottom(dy);
                        }
                    }
                } else if (getBottomViews().contains(changedView)) {
                    if (mShowMode == ShowMode.PullOut) {
                        surfaceView.offsetLeftAndRight(dx);
                        surfaceView.offsetTopAndBottom(dy);
                    } else {
                        Rect rect = computeBottomLayDown(mCurrentDragEdge);
                        if (currentBottomView != null) {
                            currentBottomView.layout(rect.left, rect.top, rect.right, rect.bottom);
                        }

                        int newLeft = surfaceView.getLeft() + dx, newTop = surfaceView.getTop() + dy;
                        if (mCurrentDragEdge == DragEdge.Left && newLeft < getPaddingLeft())
                            newLeft = getPaddingLeft();
                        else if (mCurrentDragEdge == DragEdge.Right && newLeft > getPaddingLeft())
                            newLeft = getPaddingLeft();
                        else if (mCurrentDragEdge == DragEdge.Top && newTop < getPaddingTop())
                            newTop = getPaddingTop();
                        else if (mCurrentDragEdge == DragEdge.Bottom && newTop > getPaddingTop())
                            newTop = getPaddingTop();

                        surfaceView.layout(newLeft, newTop, newLeft + getMeasuredWidth(), newTop + getMeasuredHeight());
                    }
                }

                dispatchRevealEvent(evLeft, evTop, evRight, evBottom);
                dispatchSwipeEvent(evLeft, evTop, dx, dy);
                invalidate();
                captureChildrenBound();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    /**
     * save children's bounds, so they can restore the bound in {@link #onLayout(boolean, int, int, int, int)}
     */
    private void captureChildrenBound() {
        try {
            View currentBottomView = getCurrentBottomView();
            if (getOpenStatus() == Status.Close) {
                mViewBoundCache.remove(currentBottomView);
                return;
            }

            View[] views = new View[]{getSurfaceView(), currentBottomView};
            for (View child : views) {
                Rect rect = mViewBoundCache.get(child);
                if (rect == null) {
                    rect = new Rect();
                    mViewBoundCache.put(child, rect);
                }
                rect.left = child.getLeft();
                rect.top = child.getTop();
                rect.right = child.getRight();
                rect.bottom = child.getBottom();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * the dispatchRevealEvent method may not always get accurate position, it
     * makes the view may not always get the event when the view is totally
     * show( fraction = 1), so , we need to calculate every time.
     */
    protected boolean isViewTotallyFirstShowed(View view, Rect relativePosition, DragEdge edge, int surfaceLeft,
                                               int surfaceTop, int surfaceRight, int surfaceBottom) {
        boolean r = false;

        try {
            if (mShowEntirely.get(view)) return r;

            int childLeft = relativePosition.left;
            int childRight = relativePosition.right;
            int childTop = relativePosition.top;
            int childBottom = relativePosition.bottom;


            if (getShowMode() == ShowMode.LayDown) {
                if ((edge == DragEdge.Right && surfaceRight <= childLeft)
                        || (edge == DragEdge.Left && surfaceLeft >= childRight)
                        || (edge == DragEdge.Top && surfaceTop >= childBottom)
                        || (edge == DragEdge.Bottom && surfaceBottom <= childTop)) r = true;
            } else if (getShowMode() == ShowMode.PullOut) {
                if ((edge == DragEdge.Right && childRight <= getWidth())
                        || (edge == DragEdge.Left && childLeft >= getPaddingLeft())
                        || (edge == DragEdge.Top && childTop >= getPaddingTop())
                        || (edge == DragEdge.Bottom && childBottom <= getHeight())) r = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return r;
    }

    protected boolean isViewShowing(View view, Rect relativePosition, DragEdge availableEdge, int surfaceLeft,
                                    int surfaceTop, int surfaceRight, int surfaceBottom) {
        try {
            int childLeft = relativePosition.left;
            int childRight = relativePosition.right;
            int childTop = relativePosition.top;
            int childBottom = relativePosition.bottom;

            if (getShowMode() == ShowMode.LayDown) {
                switch (availableEdge) {
                    case Right:
                        if (surfaceRight > childLeft && surfaceRight <= childRight) {
                            return true;
                        }
                        break;
                    case Left:
                        if (surfaceLeft < childRight && surfaceLeft >= childLeft) {
                            return true;
                        }
                        break;
                    case Top:
                        if (surfaceTop >= childTop && surfaceTop < childBottom) {
                            return true;
                        }
                        break;
                    case Bottom:
                        if (surfaceBottom > childTop && surfaceBottom <= childBottom) {
                            return true;
                        }
                        break;
                }
            } else if (getShowMode() == ShowMode.PullOut) {
                switch (availableEdge) {
                    case Right:
                        if (childLeft <= getWidth() && childRight > getWidth()) return true;
                        break;
                    case Left:
                        if (childRight >= getPaddingLeft() && childLeft < getPaddingLeft())
                            return true;
                        break;
                    case Top:
                        if (childTop < getPaddingTop() && childBottom >= getPaddingTop())
                            return true;
                        break;
                    case Bottom:
                        if (childTop < getHeight() && childTop >= getPaddingTop()) return true;
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    protected Rect getRelativePosition(View view) {
        try {
            View t = view;
            Rect r = new Rect(t.getLeft(), t.getTop(), 0, 0);
            while (t.getParent() != null && t != getRootView()) {
                t = (View) t.getParent();
                if (t == this) break;
                r.left += t.getLeft();
                r.top += t.getTop();
            }
            r.right = r.left + view.getMeasuredWidth();
            r.bottom = r.top + view.getMeasuredHeight();
            return r;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    protected void dispatchSwipeEvent(int surfaceLeft, int surfaceTop, int dx, int dy) {
        try {
            DragEdge edge = getDragEdge();
            boolean open = true;

            if (edge == DragEdge.Left) {
                if (dx < 0) open = false;
            } else if (edge == DragEdge.Right) {
                if (dx > 0) open = false;
            } else if (edge == DragEdge.Top) {
                if (dy < 0) open = false;
            } else if (edge == DragEdge.Bottom) {
                if (dy > 0) open = false;
            }

            dispatchSwipeEvent(surfaceLeft, surfaceTop, open);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void dispatchSwipeEvent(int surfaceLeft, int surfaceTop, boolean open) {
        try {
            safeBottomView();
            Status status = getOpenStatus();

            if (!mSwipeListeners.isEmpty()) {
                mEventCounter++;
                for (SwipeListener swipeListener : mSwipeListeners) {
                    if (mEventCounter == 1) {
                        if (open) {
                            swipeListener.onStartOpen(this);
                        } else {
                            swipeListener.onStartClose(this);
                        }
                    }
                    swipeListener.onUpdate(SwipeLayout.this, surfaceLeft - getPaddingLeft(), surfaceTop - getPaddingTop());
                }

                if (status == Status.Close) {
                    for (SwipeListener swipeListener : mSwipeListeners) {
                        swipeListener.onClose(SwipeLayout.this);
                    }
                    mEventCounter = 0;
                }

                if (status == Status.Open) {
                    View currentBottomView = getCurrentBottomView();
                    if (currentBottomView != null) {
                        currentBottomView.setEnabled(true);
                    }
                    for (SwipeListener swipeListener : mSwipeListeners) {
                        swipeListener.onOpen(SwipeLayout.this);
                    }
                    mEventCounter = 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * prevent bottom view get any touch event. Especially in LayDown mode.
     */
    private void safeBottomView() {
        try {
            Status status = getOpenStatus();
            List<View> bottoms = getBottomViews();

            if (status == Status.Close) {
                for (View bottom : bottoms) {
                    if (bottom != null && bottom.getVisibility() != INVISIBLE) {
                        bottom.setVisibility(INVISIBLE);
                    }
                }
            } else {
                View currentBottomView = getCurrentBottomView();
                if (currentBottomView != null && currentBottomView.getVisibility() != VISIBLE) {
                    currentBottomView.setVisibility(VISIBLE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void dispatchRevealEvent(final int surfaceLeft, final int surfaceTop, final int surfaceRight,
                                       final int surfaceBottom) {
        try {
            if (mRevealListeners.isEmpty()) return;

            for (Map.Entry<View, ArrayList<OnRevealListener>> entry : mRevealListeners.entrySet()) {
                View view = entry.getKey();
                Rect rect = getRelativePosition(view);

                if (isViewShowing(view, rect, mCurrentDragEdge, surfaceLeft, surfaceTop,
                        surfaceRight, surfaceBottom)) {
                    mShowEntirely.put(view, false);

                    int distance = 0;
                    float fraction = 0f;

                    if (getShowMode() == ShowMode.LayDown) {
                        switch (mCurrentDragEdge) {
                            case Left:
                                distance = rect.left - surfaceLeft;
                                fraction = distance / (float) view.getWidth();
                                break;
                            case Right:
                                distance = rect.right - surfaceRight;
                                fraction = distance / (float) view.getWidth();
                                break;
                            case Top:
                                distance = rect.top - surfaceTop;
                                fraction = distance / (float) view.getHeight();
                                break;
                            case Bottom:
                                distance = rect.bottom - surfaceBottom;
                                fraction = distance / (float) view.getHeight();
                                break;
                        }
                    } else if (getShowMode() == ShowMode.PullOut) {
                        switch (mCurrentDragEdge) {
                            case Left:
                                distance = rect.right - getPaddingLeft();
                                fraction = distance / (float) view.getWidth();
                                break;
                            case Right:
                                distance = rect.left - getWidth();
                                fraction = distance / (float) view.getWidth();
                                break;
                            case Top:
                                distance = rect.bottom - getPaddingTop();
                                fraction = distance / (float) view.getHeight();
                                break;
                            case Bottom:
                                distance = rect.top - getHeight();
                                fraction = distance / (float) view.getHeight();
                                break;
                        }
                    }

                    for (OnRevealListener onRevealListener : entry.getValue()) {
                        onRevealListener.onReveal(view, mCurrentDragEdge, Math.abs(fraction), distance);
                        if (Math.abs(fraction) == 1) {
                            mShowEntirely.put(view, true);
                        }
                    }
                }

                if (isViewTotallyFirstShowed(view, rect, mCurrentDragEdge, surfaceLeft, surfaceTop,
                        surfaceRight, surfaceBottom)) {
                    mShowEntirely.put(view, true);
                    for (OnRevealListener onRevealListener : entry.getValue()) {
                        if (mCurrentDragEdge == DragEdge.Left
                                || mCurrentDragEdge == DragEdge.Right)
                            onRevealListener.onReveal(view, mCurrentDragEdge, 1, view.getWidth());
                        else
                            onRevealListener.onReveal(view, mCurrentDragEdge, 1, view.getHeight());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        try {
            if (mDragHelper.continueSettling(true)) {
                ViewCompat.postInvalidateOnAnimation(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * {@link OnLayoutChangeListener} added in API 11. I need
     * to support it from API 8.
     */
    public interface OnLayout {
        void onLayout(SwipeLayout swipeLayout);
    }

    public SwipeLayout addOnLayoutListener(OnLayout onLayout) {
        try {
            if (mOnLayoutListeners == null) mOnLayoutListeners = new ArrayList<OnLayout>();
            mOnLayoutListeners.add(onLayout);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    public SwipeLayout removeOnLayoutListener(OnLayout onLayout) {
        try {
            if (mOnLayoutListeners != null) mOnLayoutListeners.remove(onLayout);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    public SwipeLayout clearDragEdge() {
        mDragEdges.clear();
        return this;
    }

    public SwipeLayout setDrag(DragEdge dragEdge, int childId) {
        try {
            clearDragEdge();
            addDrag(dragEdge, childId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    public SwipeLayout setDrag(DragEdge dragEdge, View view) {
        try {
            clearDragEdge();
            addDrag(dragEdge, view);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    public SwipeLayout addDrag(DragEdge dragEdge, int childId) {
        try {
            addDrag(dragEdge, findViewById(childId), null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    public SwipeLayout addDrag(DragEdge dragEdge, View view) {
        try {
            addDrag(dragEdge, view, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    public SwipeLayout addDrag(DragEdge dragEdge, View view, ViewGroup.LayoutParams params) {
        try {
            if (view == null) return this;

            if (params == null) {
                params = generateDefaultLayoutParams();
            }

            if (!checkLayoutParams(params)) {
                params = generateLayoutParams(params);
            }

            int gravity = -1;
            switch (dragEdge) {
                case Left:
                    gravity = Gravity.LEFT;
                    break;
                case Right:
                    gravity = Gravity.RIGHT;
                    break;
                case Top:
                    gravity = Gravity.TOP;
                    break;
                case Bottom:
                    gravity = Gravity.BOTTOM;
                    break;
            }

            if (params instanceof LayoutParams) {
                ((LayoutParams) params).gravity = gravity;
            }

            addView(view, 0, params);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    @Override
    public void addView(View view, int index, ViewGroup.LayoutParams params) {
        try {
            if (view == null) return;

            int gravity = Gravity.NO_GRAVITY;
            try {
                gravity = (Integer) params.getClass().getField("gravity").get(params);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (gravity > 0) {
                gravity = GravityCompat.getAbsoluteGravity(gravity, ViewCompat.getLayoutDirection(this));

                if ((gravity & Gravity.LEFT) == Gravity.LEFT) {
                    mDragEdges.put(DragEdge.Left, view);
                }

                if ((gravity & Gravity.RIGHT) == Gravity.RIGHT) {
                    mDragEdges.put(DragEdge.Right, view);
                }

                if ((gravity & Gravity.TOP) == Gravity.TOP) {
                    mDragEdges.put(DragEdge.Top, view);
                }

                if ((gravity & Gravity.BOTTOM) == Gravity.BOTTOM) {
                    mDragEdges.put(DragEdge.Bottom, view);
                }
            } else {
                for (Map.Entry<DragEdge, View> entry : mDragEdges.entrySet()) {
                    if (entry.getValue() == null) {
                        //means used the drag_edge attr, the no gravity child should be use set
                        mDragEdges.put(entry.getKey(), view);
                        break;
                    }
                }
            }

            if (view.getParent() == this) {
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        super.addView(view, index, params);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        try {
            updateBottomViews();
            if (mOnLayoutListeners != null) for (int i = 0; i < mOnLayoutListeners.size(); i++) {
                mOnLayoutListeners.get(i).onLayout(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void layoutPullOut() {
        try {
            View surfaceView = getSurfaceView();
            Rect surfaceRect = mViewBoundCache.get(surfaceView);

            if (surfaceRect == null) surfaceRect = computeSurfaceLayoutArea(false);
            if (surfaceView != null) {
                surfaceView.layout(surfaceRect.left, surfaceRect.top, surfaceRect.right, surfaceRect.bottom);
                bringChildToFront(surfaceView);
            }

            View currentBottomView = getCurrentBottomView();
            Rect bottomViewRect = mViewBoundCache.get(currentBottomView);
            if (bottomViewRect == null)
                bottomViewRect = computeBottomLayoutAreaViaSurface(ShowMode.PullOut, surfaceRect);
            if (currentBottomView != null) {
                currentBottomView.layout(bottomViewRect.left, bottomViewRect.top, bottomViewRect.right, bottomViewRect.bottom);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void layoutLayDown() {
        try {
            View surfaceView = getSurfaceView();
            Rect surfaceRect = mViewBoundCache.get(surfaceView);

            if (surfaceRect == null) surfaceRect = computeSurfaceLayoutArea(false);
            if (surfaceView != null) {
                surfaceView.layout(surfaceRect.left, surfaceRect.top, surfaceRect.right, surfaceRect.bottom);
                bringChildToFront(surfaceView);
            }

            View currentBottomView = getCurrentBottomView();
            Rect bottomViewRect = mViewBoundCache.get(currentBottomView);
            if (bottomViewRect == null)
                bottomViewRect = computeBottomLayoutAreaViaSurface(ShowMode.LayDown, surfaceRect);
            if (currentBottomView != null) {
                currentBottomView.layout(bottomViewRect.left, bottomViewRect.top, bottomViewRect.right, bottomViewRect.bottom);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkCanDrag(MotionEvent ev) {
        try {
            if (mIsBeingDragged) return;

            if (getOpenStatus() == Status.Middle) {
                mIsBeingDragged = true;
                return;
            }

            Status status = getOpenStatus();
            float distanceX = ev.getRawX() - sX;
            float distanceY = ev.getRawY() - sY;
            float angle = Math.abs(distanceY / distanceX);
            angle = (float) Math.toDegrees(Math.atan(angle));

            if (getOpenStatus() == Status.Close) {
                DragEdge dragEdge;
                if (angle < 45) {
                    if (distanceX > 0 && isLeftSwipeEnabled()) {
                        dragEdge = DragEdge.Left;
                    } else if (distanceX < 0 && isRightSwipeEnabled()) {
                        dragEdge = DragEdge.Right;
                    } else return;
                } else {
                    if (distanceY > 0 && isTopSwipeEnabled()) {
                        dragEdge = DragEdge.Top;
                    } else if (distanceY < 0 && isBottomSwipeEnabled()) {
                        dragEdge = DragEdge.Bottom;
                    } else return;
                }

                setCurrentDragEdge(dragEdge);
            }

            boolean doNothing = false;
            if (mCurrentDragEdge == DragEdge.Right) {
                boolean suitable = (status == Status.Open && distanceX > mTouchSlop)
                        || (status == Status.Close && distanceX < -mTouchSlop);
                suitable = suitable || (status == Status.Middle);

                if (angle > 30 || !suitable) {
                    doNothing = true;
                }
            }

            if (mCurrentDragEdge == DragEdge.Left) {
                boolean suitable = (status == Status.Open && distanceX < -mTouchSlop)
                        || (status == Status.Close && distanceX > mTouchSlop);
                suitable = suitable || status == Status.Middle;

                if (angle > 30 || !suitable) {
                    doNothing = true;
                }
            }

            if (mCurrentDragEdge == DragEdge.Top) {
                boolean suitable = (status == Status.Open && distanceY < -mTouchSlop)
                        || (status == Status.Close && distanceY > mTouchSlop);
                suitable = suitable || status == Status.Middle;

                if (angle < 60 || !suitable) {
                    doNothing = true;
                }
            }

            if (mCurrentDragEdge == DragEdge.Bottom) {
                boolean suitable = (status == Status.Open && distanceY > mTouchSlop)
                        || (status == Status.Close && distanceY < -mTouchSlop);
                suitable = suitable || status == Status.Middle;

                if (angle < 60 || !suitable) {
                    doNothing = true;
                }
            }

            mIsBeingDragged = !doNothing;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            if (!isSwipeEnabled()) {
                return false;
            }

            if (mClickToClose && getOpenStatus() == Status.Open && isTouchOnSurface(ev)) {
                return true;
            }

            for (SwipeDenier denier : mSwipeDeniers) {
                if (denier != null && denier.shouldDenySwipe(ev)) {
                    return false;
                }
            }

            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mDragHelper.processTouchEvent(ev);
                    mIsBeingDragged = false;
                    sX = ev.getRawX();
                    sY = ev.getRawY();
                    //if the swipe is in middle state(scrolling), should intercept the touch
                    if (getOpenStatus() == Status.Middle) {
                        mIsBeingDragged = true;
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    boolean beforeCheck = mIsBeingDragged;
                    checkCanDrag(ev);
                    if (mIsBeingDragged) {
                        ViewParent parent = getParent();
                        if (parent != null) {
                            parent.requestDisallowInterceptTouchEvent(true);
                        }
                    }
                    if (!beforeCheck && mIsBeingDragged) {
                        //let children has one chance to catch the touch, and request the swipe not intercept
                        //useful when swipeLayout wrap a swipeLayout or other gestural layout
                        return false;
                    }
                    break;
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    mIsBeingDragged = false;
                    mDragHelper.processTouchEvent(ev);
                    break;
                default://handle other action, such as ACTION_POINTER_DOWN/UP
                    mDragHelper.processTouchEvent(ev);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mIsBeingDragged;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = 0;
        try {
            if (!isSwipeEnabled()) return super.onTouchEvent(event);

            action = event.getActionMasked();
            gestureDetector.onTouchEvent(event);

            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    mDragHelper.processTouchEvent(event);
                    sX = event.getRawX();
                    sY = event.getRawY();
                case MotionEvent.ACTION_MOVE: {
                    //the drag state and the direction are already judged at onInterceptTouchEvent
                    checkCanDrag(event);
                    if (mIsBeingDragged) {
                        getParent().requestDisallowInterceptTouchEvent(true);
                        mDragHelper.processTouchEvent(event);
                    }
                    break;
                }
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    mIsBeingDragged = false;
                    mDragHelper.processTouchEvent(event);
                    break;
                default://handle other action, such as ACTION_POINTER_DOWN/UP
                    mDragHelper.processTouchEvent(event);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return super.onTouchEvent(event) || mIsBeingDragged || action == MotionEvent.ACTION_DOWN;
    }

    public boolean isClickToClose() {
        return mClickToClose;
    }

    public SwipeLayout setClickToClose(boolean mClickToClose) {
        this.mClickToClose = mClickToClose;
        return this;
    }

    public SwipeLayout setSwipeEnabled(boolean enabled) {
        mSwipeEnabled = enabled;
        return this;
    }

    public boolean isSwipeEnabled() {
        return mSwipeEnabled;
    }

    public boolean isLeftSwipeEnabled() {
        try {
            View bottomView = mDragEdges.get(DragEdge.Left);
            return bottomView != null && bottomView.getParent() == this
                    && bottomView != getSurfaceView() && mSwipesEnabled[DragEdge.Left.ordinal()];
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public SwipeLayout setLeftSwipeEnabled(boolean leftSwipeEnabled) {
        try {
            this.mSwipesEnabled[DragEdge.Left.ordinal()] = leftSwipeEnabled;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    public boolean isRightSwipeEnabled() {
        try {
            View bottomView = mDragEdges.get(DragEdge.Right);
            return bottomView != null && bottomView.getParent() == this
                    && bottomView != getSurfaceView() && mSwipesEnabled[DragEdge.Right.ordinal()];
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public SwipeLayout setRightSwipeEnabled(boolean rightSwipeEnabled) {
        try {
            this.mSwipesEnabled[DragEdge.Right.ordinal()] = rightSwipeEnabled;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    public boolean isTopSwipeEnabled() {
        try {
            View bottomView = mDragEdges.get(DragEdge.Top);
            return bottomView != null && bottomView.getParent() == this
                    && bottomView != getSurfaceView() && mSwipesEnabled[DragEdge.Top.ordinal()];
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public SwipeLayout setTopSwipeEnabled(boolean topSwipeEnabled) {
        try {
            this.mSwipesEnabled[DragEdge.Top.ordinal()] = topSwipeEnabled;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    public boolean isBottomSwipeEnabled() {
        try {
            View bottomView = mDragEdges.get(DragEdge.Bottom);
            return bottomView != null && bottomView.getParent() == this
                    && bottomView != getSurfaceView() && mSwipesEnabled[DragEdge.Bottom.ordinal()];
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public SwipeLayout setBottomSwipeEnabled(boolean bottomSwipeEnabled) {
        try {
            this.mSwipesEnabled[DragEdge.Bottom.ordinal()] = bottomSwipeEnabled;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    /***
     * Returns the percentage of revealing at which the view below should the view finish opening
     * if it was already open before dragging
     *
     * @returns The percentage of view revealed to trigger, default value is 0.25
     */
    public float getWillOpenPercentAfterOpen() {
        return mWillOpenPercentAfterOpen;
    }

    /***
     * Allows to stablish at what percentage of revealing the view below should the view finish opening
     * if it was already open before dragging
     *
     * @param willOpenPercentAfterOpen The percentage of view revealed to trigger, default value is 0.25
     */
    public SwipeLayout setWillOpenPercentAfterOpen(float willOpenPercentAfterOpen) {
        this.mWillOpenPercentAfterOpen = willOpenPercentAfterOpen;
        return this;
    }

    /***
     * Returns the percentage of revealing at which the view below should the view finish opening
     * if it was already closed before dragging
     *
     * @returns The percentage of view revealed to trigger, default value is 0.25
     */
    public float getWillOpenPercentAfterClose() {
        return mWillOpenPercentAfterClose;
    }

    /***
     * Allows to stablish at what percentage of revealing the view below should the view finish opening
     * if it was already closed before dragging
     *
     * @param willOpenPercentAfterClose The percentage of view revealed to trigger, default value is 0.75
     */
    public SwipeLayout setWillOpenPercentAfterClose(float willOpenPercentAfterClose) {
        this.mWillOpenPercentAfterClose = willOpenPercentAfterClose;
        return this;
    }

    private boolean insideAdapterView() {
        return getAdapterView() != null;
    }

    private AdapterView getAdapterView() {
        try {
            ViewParent t = getParent();
            if (t instanceof AdapterView) {
                return (AdapterView) t;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private void performAdapterViewItemClick() {
        try {
            if (getOpenStatus() != Status.Close) return;

            ViewParent t = getParent();
            if (t instanceof AdapterView) {
                AdapterView view = (AdapterView) t;
                int p = view.getPositionForView(SwipeLayout.this);
                if (p != AdapterView.INVALID_POSITION) {
                    view.performItemClick(view.getChildAt(p - view.getFirstVisiblePosition()), p, view
                            .getAdapter().getItemId(p));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean performAdapterViewItemLongClick() {
        try {
            if (getOpenStatus() != Status.Close) return false;

            ViewParent t = getParent();
            if (t instanceof AdapterView) {
                AdapterView view = (AdapterView) t;
                int p = view.getPositionForView(SwipeLayout.this);
                if (p == AdapterView.INVALID_POSITION) return false;
                long vId = view.getItemIdAtPosition(p);
                boolean handled = false;

                try {
                    Method m = AbsListView.class.getDeclaredMethod("performLongPress", View.class, int.class, long.class);
                    m.setAccessible(true);
                    handled = (boolean) m.invoke(view, SwipeLayout.this, p, vId);
                } catch (Exception e) {
                    e.printStackTrace();

                    if (view.getOnItemLongClickListener() != null) {
                        handled = view.getOnItemLongClickListener().onItemLongClick(view, SwipeLayout.this, p, vId);
                    }

                    if (handled) {
                        view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                    }
                }

                return handled;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        try {
            if (insideAdapterView()) {
                if (clickListener == null) {
                    setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            performAdapterViewItemClick();
                        }
                    });
                }

                if (longClickListener == null) {
                    setOnLongClickListener(new OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            performAdapterViewItemLongClick();
                            return true;
                        }
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void setOnClickListener(OnClickListener onClickListener) {
        super.setOnClickListener(onClickListener);
        this.clickListener = onClickListener;
    }

    @Override
    public void setOnLongClickListener(OnLongClickListener onLongClickListener) {
        super.setOnLongClickListener(onLongClickListener);
        this.longClickListener = onLongClickListener;
    }

    private boolean isTouchOnSurface(MotionEvent ev) {
        try {
            View surfaceView = getSurfaceView();
            if (surfaceView == null) {
                return false;
            }

            if (hitSurfaceRect == null) {
                hitSurfaceRect = new Rect();
            }

            surfaceView.getHitRect(hitSurfaceRect);

            return hitSurfaceRect.contains((int) ev.getX(), (int) ev.getY());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    private GestureDetector gestureDetector = new GestureDetector(getContext(), new SwipeDetector());

    class SwipeDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            if (mClickToClose && isTouchOnSurface(e)) {
                close();
            }

            return super.onSingleTapUp(e);
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            try {
                if (mDoubleClickListener != null) {
                    View target;
                    View bottom = getCurrentBottomView();
                    View surface = getSurfaceView();

                    if (bottom != null && e.getX() > bottom.getLeft() && e.getX() < bottom.getRight()
                            && e.getY() > bottom.getTop() && e.getY() < bottom.getBottom()) {
                        target = bottom;
                    } else {
                        target = surface;
                    }

                    mDoubleClickListener.onDoubleClick(SwipeLayout.this, target == surface);
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }

            return true;
        }
    }

    /**
     * set the drag distance, it will force set the bottom view's width or
     * height via this value.
     *
     * @param max max distance in dp unit
     */
    public SwipeLayout setDragDistance(int max) {
        try {
            if (max < 0) max = 0;
            mDragDistance = dp2px(max);
            requestLayout();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    public SwipeLayout setShowMode(ShowMode mode) {
        try {
            mShowMode = mode;
            requestLayout();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    public DragEdge getDragEdge() {
        return mCurrentDragEdge;
    }

    public int getDragDistance() {
        return mDragDistance;
    }

    public ShowMode getShowMode() {
        return mShowMode;
    }

    /**
     * return null if there is no surface view(no children)
     */
    public View getSurfaceView() {
        try {
            if (getChildCount() == 0) return null;
            return getChildAt(getChildCount() - 1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * return null if there is no bottom view
     */
    @Nullable
    public View getCurrentBottomView() {
        try {
            List<View> bottoms = getBottomViews();
            if (mCurrentDragEdge.ordinal() < bottoms.size()) {
                return bottoms.get(mCurrentDragEdge.ordinal());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * @return all bottomViews: left, top, right, bottom (may null if the edge is not set)
     */
    public List<View> getBottomViews() {
        try {
            ArrayList<View> bottoms = new ArrayList<View>();
            for (DragEdge dragEdge : DragEdge.values()) {
                bottoms.add(mDragEdges.get(dragEdge));
            }
            return bottoms;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public enum Status {
        Middle,
        Open,
        Close
    }

    /**
     * get the open status.
     * <p>
     * Middle.
     */
    public Status getOpenStatus() {
        try {
            View surfaceView = getSurfaceView();
            if (surfaceView == null) {
                return Status.Close;
            }

            int surfaceLeft = surfaceView.getLeft();
            int surfaceTop = surfaceView.getTop();
            if (surfaceLeft == getPaddingLeft() && surfaceTop == getPaddingTop())
                return Status.Close;

            if (surfaceLeft == (getPaddingLeft() - mDragDistance) || surfaceLeft == (getPaddingLeft() + mDragDistance)
                    || surfaceTop == (getPaddingTop() - mDragDistance) || surfaceTop == (getPaddingTop() + mDragDistance))
                return Status.Open;

            return Status.Middle;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * Process the surface release event.
     *
     * @param xvel                 xVelocity
     * @param yvel                 yVelocity
     * @param isCloseBeforeDragged the open state before drag
     */
    protected void processHandRelease(float xvel, float yvel, boolean isCloseBeforeDragged) {
        try {
            float minVelocity = mDragHelper.getMinVelocity();
            View surfaceView = getSurfaceView();
            DragEdge currentDragEdge = mCurrentDragEdge;
            if (currentDragEdge == null || surfaceView == null) {
                return;
            }

            float willOpenPercent = (isCloseBeforeDragged ? mWillOpenPercentAfterClose : mWillOpenPercentAfterOpen);

            if (currentDragEdge == DragEdge.Left) {
                if (xvel > minVelocity) open();
                else if (xvel < -minVelocity) close();
                else {
                    float openPercent = 1f * getSurfaceView().getLeft() / mDragDistance;
                    if (openPercent > willOpenPercent) open();
                    else close();
                }
            } else if (currentDragEdge == DragEdge.Right) {
                if (xvel > minVelocity) close();
                else if (xvel < -minVelocity) open();
                else {
                    float openPercent = 1f * (-getSurfaceView().getLeft()) / mDragDistance;
                    if (openPercent > willOpenPercent) open();
                    else close();
                }
            } else if (currentDragEdge == DragEdge.Top) {
                if (yvel > minVelocity) open();
                else if (yvel < -minVelocity) close();
                else {
                    float openPercent = 1f * getSurfaceView().getTop() / mDragDistance;
                    if (openPercent > willOpenPercent) open();
                    else close();
                }
            } else if (currentDragEdge == DragEdge.Bottom) {
                if (yvel > minVelocity) close();
                else if (yvel < -minVelocity) open();
                else {
                    float openPercent = 1f * (-getSurfaceView().getTop()) / mDragDistance;
                    if (openPercent > willOpenPercent) open();
                    else close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * smoothly open surface.
     */
    public SwipeLayout open() {
        return open(true, true);
    }

    public SwipeLayout open(boolean smooth) {
        return open(smooth, true);
    }

    public SwipeLayout open(boolean smooth, boolean notify) {
        try {
            View surface = getSurfaceView(), bottom = getCurrentBottomView();
            if (surface == null) {
                return this;
            }

            int dx, dy;
            Rect rect = computeSurfaceLayoutArea(true);
            if (smooth) {
                mDragHelper.smoothSlideViewTo(surface, rect.left, rect.top);
            } else {
                dx = rect.left - surface.getLeft();
                dy = rect.top - surface.getTop();
                surface.layout(rect.left, rect.top, rect.right, rect.bottom);
                if (getShowMode() == ShowMode.PullOut) {
                    Rect bRect = computeBottomLayoutAreaViaSurface(ShowMode.PullOut, rect);
                    if (bottom != null) {
                        bottom.layout(bRect.left, bRect.top, bRect.right, bRect.bottom);
                    }
                }
                if (notify) {
                    dispatchRevealEvent(rect.left, rect.top, rect.right, rect.bottom);
                    dispatchSwipeEvent(rect.left, rect.top, dx, dy);
                } else {
                    safeBottomView();
                }
            }
            invalidate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    public SwipeLayout open(DragEdge edge) {
        try {
            setCurrentDragEdge(edge);
            open(true, true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    public SwipeLayout open(boolean smooth, DragEdge edge) {
        try {
            setCurrentDragEdge(edge);
            open(smooth, true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    public SwipeLayout open(boolean smooth, boolean notify, DragEdge edge) {
        try {
            setCurrentDragEdge(edge);
            open(smooth, notify);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    /**
     * smoothly close surface.
     */
    public SwipeLayout close() {
        close(true, true);
        return this;
    }

    public SwipeLayout close(boolean smooth) {
        close(smooth, true);
        return this;
    }

    /**
     * close surface
     *
     * @param smooth smoothly or not.
     * @param notify if notify all the listeners.
     */
    public SwipeLayout close(boolean smooth, boolean notify) {
        try {
            View surface = getSurfaceView();
            if (surface == null) {
                return this;
            }
            int dx, dy;
            if (smooth)
                mDragHelper.smoothSlideViewTo(getSurfaceView(), getPaddingLeft(), getPaddingTop());
            else {
                Rect rect = computeSurfaceLayoutArea(false);
                dx = rect.left - surface.getLeft();
                dy = rect.top - surface.getTop();
                surface.layout(rect.left, rect.top, rect.right, rect.bottom);
                if (notify) {
                    dispatchRevealEvent(rect.left, rect.top, rect.right, rect.bottom);
                    dispatchSwipeEvent(rect.left, rect.top, dx, dy);
                } else {
                    safeBottomView();
                }
            }

            invalidate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    public SwipeLayout toggle() {
        return toggle(true);
    }

    public SwipeLayout toggle(boolean smooth) {
        try {
            if (getOpenStatus() == Status.Open)
                close(smooth);
            else if (getOpenStatus() == Status.Close) open(smooth);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }


    /**
     * a helper function to compute the Rect area that surface will hold in.
     *
     * @param open open status or close status.
     */
    private Rect computeSurfaceLayoutArea(boolean open) {
        try {
            int l = getPaddingLeft(), t = getPaddingTop();
            if (open) {
                if (mCurrentDragEdge == DragEdge.Left)
                    l = getPaddingLeft() + mDragDistance;
                else if (mCurrentDragEdge == DragEdge.Right)
                    l = getPaddingLeft() - mDragDistance;
                else if (mCurrentDragEdge == DragEdge.Top)
                    t = getPaddingTop() + mDragDistance;
                else t = getPaddingTop() - mDragDistance;
            }

            return new Rect(l, t, l + getMeasuredWidth(), t + getMeasuredHeight());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private Rect computeBottomLayoutAreaViaSurface(ShowMode mode, Rect surfaceArea) {
        try {
            Rect rect = surfaceArea;
            View bottomView = getCurrentBottomView();

            int bl = rect.left, bt = rect.top, br = rect.right, bb = rect.bottom;
            if (mode == ShowMode.PullOut) {
                if (mCurrentDragEdge == DragEdge.Left)
                    bl = rect.left - mDragDistance;
                else if (mCurrentDragEdge == DragEdge.Right)
                    bl = rect.right;
                else if (mCurrentDragEdge == DragEdge.Top)
                    bt = rect.top - mDragDistance;
                else bt = rect.bottom;

                if (mCurrentDragEdge == DragEdge.Left || mCurrentDragEdge == DragEdge.Right) {
                    bb = rect.bottom;
                    br = bl + (bottomView == null ? 0 : bottomView.getMeasuredWidth());
                } else {
                    bb = bt + (bottomView == null ? 0 : bottomView.getMeasuredHeight());
                    br = rect.right;
                }
            } else if (mode == ShowMode.LayDown) {
                if (mCurrentDragEdge == DragEdge.Left)
                    br = bl + mDragDistance;
                else if (mCurrentDragEdge == DragEdge.Right)
                    bl = br - mDragDistance;
                else if (mCurrentDragEdge == DragEdge.Top)
                    bb = bt + mDragDistance;
                else bt = bb - mDragDistance;

            }

            return new Rect(bl, bt, br, bb);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private Rect computeBottomLayDown(DragEdge dragEdge) {
        try {
            int bl = getPaddingLeft(), bt = getPaddingTop();
            int br, bb;
            if (dragEdge == DragEdge.Right) {
                bl = getMeasuredWidth() - mDragDistance;
            } else if (dragEdge == DragEdge.Bottom) {
                bt = getMeasuredHeight() - mDragDistance;
            }
            if (dragEdge == DragEdge.Left || dragEdge == DragEdge.Right) {
                br = bl + mDragDistance;
                bb = bt + getMeasuredHeight();
            } else {
                br = bl + getMeasuredWidth();
                bb = bt + mDragDistance;
            }

            return new Rect(bl, bt, br, bb);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public SwipeLayout setOnDoubleClickListener(DoubleClickListener doubleClickListener) {
        mDoubleClickListener = doubleClickListener;
        return this;
    }

    public interface DoubleClickListener {
        void onDoubleClick(SwipeLayout layout, boolean surface);
    }

    private int dp2px(float dp) {
        try {
            return (int) (dp * getContext().getResources().getDisplayMetrics().density + 0.5f);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }


    /**
     * Deprecated, use {@link #setDrag(DragEdge, View)}
     */
    @Deprecated
    public void setDragEdge(DragEdge dragEdge) {
        try {
            clearDragEdge();
            if (getChildCount() >= 2) {
                mDragEdges.put(dragEdge, getChildAt(getChildCount() - 2));
            }
            setCurrentDragEdge(dragEdge);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onViewRemoved(View view) {
        try {
            for (Map.Entry<DragEdge, View> entry : new HashMap<DragEdge, View>(mDragEdges).entrySet()) {
                if (entry.getValue() == view) {
                    mDragEdges.remove(entry.getKey());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map<DragEdge, View> getDragEdgeMap() {
        return mDragEdges;
    }

    /**
     * Deprecated, use {@link #getDragEdgeMap()}
     */
    @Deprecated
    public List<DragEdge> getDragEdges() {
        try {
            return new ArrayList<DragEdge>(mDragEdges.keySet());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Deprecated, use {@link #setDrag(DragEdge, View)}
     */
    @Deprecated
    public void setDragEdges(List<DragEdge> dragEdges) {
        try {
            clearDragEdge();
            for (int i = 0, size = Math.min(dragEdges.size(), getChildCount() - 1); i < size; i++) {
                DragEdge dragEdge = dragEdges.get(i);
                mDragEdges.put(dragEdge, getChildAt(i));
            }
            if (dragEdges.size() == 0 || dragEdges.contains(DefaultDragEdge)) {
                setCurrentDragEdge(DefaultDragEdge);
            } else {
                setCurrentDragEdge(dragEdges.get(0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Deprecated, use {@link #addDrag(DragEdge, View)}
     */
    @Deprecated
    public void setDragEdges(DragEdge... mDragEdges) {
        try {
            clearDragEdge();
            setDragEdges(Arrays.asList(mDragEdges));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Deprecated, use {@link #addDrag(DragEdge, View)}
     * When using multiple drag edges it's a good idea to pass the ids of the views that
     * you're using for the left, right, top bottom views (-1 if you're not using a particular view)
     */
    @Deprecated
    public void setBottomViewIds(int leftId, int rightId, int topId, int bottomId) {
        try {
            addDrag(DragEdge.Left, findViewById(leftId));
            addDrag(DragEdge.Right, findViewById(rightId));
            addDrag(DragEdge.Top, findViewById(topId));
            addDrag(DragEdge.Bottom, findViewById(bottomId));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private float getCurrentOffset() {
        try {
            if (mCurrentDragEdge == null) return 0;
            return mEdgeSwipesOffset[mCurrentDragEdge.ordinal()];
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    private void setCurrentDragEdge(DragEdge dragEdge) {
        try {
            mCurrentDragEdge = dragEdge;
            updateBottomViews();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateBottomViews() {
        try {
            View currentBottomView = getCurrentBottomView();
            if (currentBottomView != null) {
                if (mCurrentDragEdge == DragEdge.Left || mCurrentDragEdge == DragEdge.Right) {
                    mDragDistance = currentBottomView.getMeasuredWidth() - dp2px(getCurrentOffset());
                } else {
                    mDragDistance = currentBottomView.getMeasuredHeight() - dp2px(getCurrentOffset());
                }
            }

            if (mShowMode == ShowMode.PullOut) {
                layoutPullOut();
            } else if (mShowMode == ShowMode.LayDown) {
                layoutLayDown();
            }

            safeBottomView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
