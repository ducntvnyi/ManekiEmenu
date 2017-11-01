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

package com.qslib.viewanimation.easing.elastic;


import com.qslib.viewanimation.easing.BaseEasingMethod;

public class ElasticEaseOut extends BaseEasingMethod {

    public ElasticEaseOut(float duration) {
        super(duration);
    }

    @Override
    public Float calculate(float time, float start, float end, float duration) {
        if (time == 0) return start;
        if ((time /= duration) == 1) return start + end;
        float p = duration * .3f;
        float a = end;
        float s = p / 4;
        return (a * (float) Math.pow(2, -10 * time) * (float) Math.sin((time * duration - s) * (2 * (float) Math.PI) / p) + end + start);
    }
}
