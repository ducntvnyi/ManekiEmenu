package com.qslib.recycleview.swipe.implments;

import android.view.View;

import com.qslib.recycleview.swipe.SimpleSwipeListener;
import com.qslib.recycleview.swipe.SwipeLayout;
import com.qslib.recycleview.swipe.interfaces.SwipeAdapterInterface;
import com.qslib.recycleview.swipe.interfaces.SwipeItemMangerInterface;
import com.qslib.recycleview.swipe.util.Attributes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * SwipeItemMangerImpl is a helper class to help all the adapters to maintain open status.
 */
public class SwipeItemMangerImpl implements SwipeItemMangerInterface {
    private Attributes.Mode mode = Attributes.Mode.Single;

    public final int INVALID_POSITION = -1;
    protected int mOpenPosition = INVALID_POSITION;

    protected Set<Integer> mOpenPositions = new HashSet<Integer>();
    protected Set<SwipeLayout> mShownLayouts = new HashSet<SwipeLayout>();

    protected SwipeAdapterInterface swipeAdapterInterface;

    public SwipeItemMangerImpl(SwipeAdapterInterface swipeAdapterInterface) {
        if (swipeAdapterInterface == null)
            throw new IllegalArgumentException("SwipeAdapterInterface can not be null");
        this.swipeAdapterInterface = swipeAdapterInterface;
    }

    public Attributes.Mode getMode() {
        return mode;
    }

    public void setMode(Attributes.Mode mode) {
        try {
            this.mode = mode;
            mOpenPositions.clear();
            mShownLayouts.clear();
            mOpenPosition = INVALID_POSITION;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void bind(View view, int position) {
        try {
            int resId = swipeAdapterInterface.getSwipeLayoutResourceId(position);
            SwipeLayout swipeLayout = (SwipeLayout) view.findViewById(resId);
            if (swipeLayout == null)
                throw new IllegalStateException("can not find SwipeLayout in target view");

            if (swipeLayout.getTag(resId) == null) {
                OnLayoutListener onLayoutListener = new OnLayoutListener(position);
                SwipeMemory swipeMemory = new SwipeMemory(position);
                swipeLayout.addSwipeListener(swipeMemory);
                swipeLayout.addOnLayoutListener(onLayoutListener);
                swipeLayout.setTag(resId, new ValueBox(position, swipeMemory, onLayoutListener));
                mShownLayouts.add(swipeLayout);
            } else {
                ValueBox valueBox = (ValueBox) swipeLayout.getTag(resId);
                valueBox.swipeMemory.setPosition(position);
                valueBox.onLayoutListener.setPosition(position);
                valueBox.position = position;
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void openItem(int position) {
        try {
            if (mode == Attributes.Mode.Multiple) {
                if (!mOpenPositions.contains(position))
                    mOpenPositions.add(position);
            } else {
                mOpenPosition = position;
            }
            swipeAdapterInterface.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void closeItem(int position) {
        try {
            if (mode == Attributes.Mode.Multiple) {
                mOpenPositions.remove(position);
            } else {
                if (mOpenPosition == position)
                    mOpenPosition = INVALID_POSITION;
            }
            swipeAdapterInterface.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void closeAllExcept(SwipeLayout layout) {
        try {
            for (SwipeLayout swipeLayout : mShownLayouts) {
                if (swipeLayout != layout)
                    swipeLayout.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void closeAllItems() {
        if (mode == Attributes.Mode.Multiple) {
            mOpenPositions.clear();
        } else {
            mOpenPosition = INVALID_POSITION;
        }
        for (SwipeLayout swipeLayout : mShownLayouts) {
            swipeLayout.close();
        }
    }

    @Override
    public void removeShownLayouts(SwipeLayout layout) {
        try {
            mShownLayouts.remove(layout);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Integer> getOpenItems() {
        try {
            if (mode == Attributes.Mode.Multiple) {
                return new ArrayList<Integer>(mOpenPositions);
            } else {
                return Collections.singletonList(mOpenPosition);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<SwipeLayout> getOpenLayouts() {
        try {
            return new ArrayList<SwipeLayout>(mShownLayouts);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean isOpen(int position) {
        try {
            if (mode == Attributes.Mode.Multiple) {
                return mOpenPositions.contains(position);
            } else {
                return mOpenPosition == position;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    class ValueBox {
        OnLayoutListener onLayoutListener;
        SwipeMemory swipeMemory;
        int position;

        ValueBox(int position, SwipeMemory swipeMemory, OnLayoutListener onLayoutListener) {
            this.swipeMemory = swipeMemory;
            this.onLayoutListener = onLayoutListener;
            this.position = position;
        }
    }

    class OnLayoutListener implements SwipeLayout.OnLayout {
        private int position;

        OnLayoutListener(int position) {
            this.position = position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        @Override
        public void onLayout(SwipeLayout v) {
            try {
                if (isOpen(position)) {
                    v.open(false, false);
                } else {
                    v.close(false, false);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class SwipeMemory extends SimpleSwipeListener {
        private int position;

        SwipeMemory(int position) {
            this.position = position;
        }

        @Override
        public void onClose(SwipeLayout layout) {
            try {
                if (mode == Attributes.Mode.Multiple) {
                    mOpenPositions.remove(position);
                } else {
                    mOpenPosition = INVALID_POSITION;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onStartOpen(SwipeLayout layout) {
            try {
                if (mode == Attributes.Mode.Single) {
                    closeAllExcept(layout);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onOpen(SwipeLayout layout) {
            try {
                if (mode == Attributes.Mode.Multiple)
                    mOpenPositions.add(position);
                else {
                    closeAllExcept(layout);
                    mOpenPosition = position;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void setPosition(int position) {
            this.position = position;
        }
    }
}
