
/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 daimajia
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.qslib.viewanimation;

import com.qslib.viewanimation.attention.BounceAnimator;
import com.qslib.viewanimation.attention.FlashAnimator;
import com.qslib.viewanimation.attention.PulseAnimator;
import com.qslib.viewanimation.attention.RubberBandAnimator;
import com.qslib.viewanimation.attention.ShakeAnimator;
import com.qslib.viewanimation.attention.StandUpAnimator;
import com.qslib.viewanimation.attention.SwingAnimator;
import com.qslib.viewanimation.attention.TadaAnimator;
import com.qslib.viewanimation.attention.WaveAnimator;
import com.qslib.viewanimation.attention.WobbleAnimator;
import com.qslib.viewanimation.bouncing_entrances.BounceInAnimator;
import com.qslib.viewanimation.bouncing_entrances.BounceInDownAnimator;
import com.qslib.viewanimation.bouncing_entrances.BounceInLeftAnimator;
import com.qslib.viewanimation.bouncing_entrances.BounceInRightAnimator;
import com.qslib.viewanimation.bouncing_entrances.BounceInUpAnimator;
import com.qslib.viewanimation.fading_entrances.FadeInAnimator;
import com.qslib.viewanimation.fading_entrances.FadeInDownAnimator;
import com.qslib.viewanimation.fading_entrances.FadeInLeftAnimator;
import com.qslib.viewanimation.fading_entrances.FadeInRightAnimator;
import com.qslib.viewanimation.fading_entrances.FadeInUpAnimator;
import com.qslib.viewanimation.fading_exits.FadeOutAnimator;
import com.qslib.viewanimation.fading_exits.FadeOutDownAnimator;
import com.qslib.viewanimation.fading_exits.FadeOutLeftAnimator;
import com.qslib.viewanimation.fading_exits.FadeOutRightAnimator;
import com.qslib.viewanimation.fading_exits.FadeOutUpAnimator;
import com.qslib.viewanimation.flippers.FlipInXAnimator;
import com.qslib.viewanimation.flippers.FlipInYAnimator;
import com.qslib.viewanimation.flippers.FlipOutXAnimator;
import com.qslib.viewanimation.flippers.FlipOutYAnimator;
import com.qslib.viewanimation.rotating_entrances.RotateInAnimator;
import com.qslib.viewanimation.rotating_entrances.RotateInDownLeftAnimator;
import com.qslib.viewanimation.rotating_entrances.RotateInDownRightAnimator;
import com.qslib.viewanimation.rotating_entrances.RotateInUpLeftAnimator;
import com.qslib.viewanimation.rotating_entrances.RotateInUpRightAnimator;
import com.qslib.viewanimation.rotating_exits.RotateOutAnimator;
import com.qslib.viewanimation.rotating_exits.RotateOutDownLeftAnimator;
import com.qslib.viewanimation.rotating_exits.RotateOutDownRightAnimator;
import com.qslib.viewanimation.rotating_exits.RotateOutUpLeftAnimator;
import com.qslib.viewanimation.rotating_exits.RotateOutUpRightAnimator;
import com.qslib.viewanimation.sliders.SlideInDownAnimator;
import com.qslib.viewanimation.sliders.SlideInLeftAnimator;
import com.qslib.viewanimation.sliders.SlideInRightAnimator;
import com.qslib.viewanimation.sliders.SlideInUpAnimator;
import com.qslib.viewanimation.sliders.SlideOutDownAnimator;
import com.qslib.viewanimation.sliders.SlideOutLeftAnimator;
import com.qslib.viewanimation.sliders.SlideOutRightAnimator;
import com.qslib.viewanimation.sliders.SlideOutUpAnimator;
import com.qslib.viewanimation.specials.HingeAnimator;
import com.qslib.viewanimation.specials.RollInAnimator;
import com.qslib.viewanimation.specials.RollOutAnimator;
import com.qslib.viewanimation.specials.in.DropOutAnimator;
import com.qslib.viewanimation.specials.in.LandingAnimator;
import com.qslib.viewanimation.specials.out.TakingOffAnimator;
import com.qslib.viewanimation.zooming_entrances.ZoomInAnimator;
import com.qslib.viewanimation.zooming_entrances.ZoomInDownAnimator;
import com.qslib.viewanimation.zooming_entrances.ZoomInLeftAnimator;
import com.qslib.viewanimation.zooming_entrances.ZoomInRightAnimator;
import com.qslib.viewanimation.zooming_entrances.ZoomInUpAnimator;
import com.qslib.viewanimation.zooming_exits.ZoomOutAnimator;
import com.qslib.viewanimation.zooming_exits.ZoomOutDownAnimator;
import com.qslib.viewanimation.zooming_exits.ZoomOutLeftAnimator;
import com.qslib.viewanimation.zooming_exits.ZoomOutRightAnimator;
import com.qslib.viewanimation.zooming_exits.ZoomOutUpAnimator;

public enum Techniques {
    DropOut(DropOutAnimator.class),
    Landing(LandingAnimator.class),
    TakingOff(TakingOffAnimator.class),

    Flash(FlashAnimator.class),
    Pulse(PulseAnimator.class),
    RubberBand(RubberBandAnimator.class),
    Shake(ShakeAnimator.class),
    Swing(SwingAnimator.class),
    Wobble(WobbleAnimator.class),
    Bounce(BounceAnimator.class),
    Tada(TadaAnimator.class),
    StandUp(StandUpAnimator.class),
    Wave(WaveAnimator.class),

    Hinge(HingeAnimator.class),
    RollIn(RollInAnimator.class),
    RollOut(RollOutAnimator.class),

    BounceIn(BounceInAnimator.class),
    BounceInDown(BounceInDownAnimator.class),
    BounceInLeft(BounceInLeftAnimator.class),
    BounceInRight(BounceInRightAnimator.class),
    BounceInUp(BounceInUpAnimator.class),

    FadeIn(FadeInAnimator.class),
    FadeInUp(FadeInUpAnimator.class),
    FadeInDown(FadeInDownAnimator.class),
    FadeInLeft(FadeInLeftAnimator.class),
    FadeInRight(FadeInRightAnimator.class),

    FadeOut(FadeOutAnimator.class),
    FadeOutDown(FadeOutDownAnimator.class),
    FadeOutLeft(FadeOutLeftAnimator.class),
    FadeOutRight(FadeOutRightAnimator.class),
    FadeOutUp(FadeOutUpAnimator.class),

    FlipInX(FlipInXAnimator.class),
    FlipOutX(FlipOutXAnimator.class),
    FlipInY(FlipInYAnimator.class),
    FlipOutY(FlipOutYAnimator.class),
    RotateIn(RotateInAnimator.class),
    RotateInDownLeft(RotateInDownLeftAnimator.class),
    RotateInDownRight(RotateInDownRightAnimator.class),
    RotateInUpLeft(RotateInUpLeftAnimator.class),
    RotateInUpRight(RotateInUpRightAnimator.class),

    RotateOut(RotateOutAnimator.class),
    RotateOutDownLeft(RotateOutDownLeftAnimator.class),
    RotateOutDownRight(RotateOutDownRightAnimator.class),
    RotateOutUpLeft(RotateOutUpLeftAnimator.class),
    RotateOutUpRight(RotateOutUpRightAnimator.class),

    SlideInLeft(SlideInLeftAnimator.class),
    SlideInRight(SlideInRightAnimator.class),
    SlideInUp(SlideInUpAnimator.class),
    SlideInDown(SlideInDownAnimator.class),

    SlideOutLeft(SlideOutLeftAnimator.class),
    SlideOutRight(SlideOutRightAnimator.class),
    SlideOutUp(SlideOutUpAnimator.class),
    SlideOutDown(SlideOutDownAnimator.class),

    ZoomIn(ZoomInAnimator.class),
    ZoomInDown(ZoomInDownAnimator.class),
    ZoomInLeft(ZoomInLeftAnimator.class),
    ZoomInRight(ZoomInRightAnimator.class),
    ZoomInUp(ZoomInUpAnimator.class),

    ZoomOut(ZoomOutAnimator.class),
    ZoomOutDown(ZoomOutDownAnimator.class),
    ZoomOutLeft(ZoomOutLeftAnimator.class),
    ZoomOutRight(ZoomOutRightAnimator.class),
    ZoomOutUp(ZoomOutUpAnimator.class);

    private Class animatorClazz;

    Techniques(Class clazz) {
        animatorClazz = clazz;
    }

    public BaseViewAnimator getAnimator() {
        try {
            return (BaseViewAnimator) animatorClazz.newInstance();
        } catch (Exception e) {
            throw new Error("Can not init animatorClazz instance");
        }
    }
}
