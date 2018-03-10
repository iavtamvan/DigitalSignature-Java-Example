package com.iavariav.root.digitalsignaturecompiled.utils;

import com.iavariav.root.digitalsignaturecompiled.utils.TimedPoint;

public class Bezier{

    public com.iavariav.root.digitalsignaturecompiled.utils.TimedPoint startPoint;
    public com.iavariav.root.digitalsignaturecompiled.utils.TimedPoint control1;
    public com.iavariav.root.digitalsignaturecompiled.utils.TimedPoint control2;
    public com.iavariav.root.digitalsignaturecompiled.utils.TimedPoint endPoint;

    public com.iavariav.root.digitalsignaturecompiled.utils.Bezier set(com.iavariav.root.digitalsignaturecompiled.utils.TimedPoint startPoint, com.iavariav.root.digitalsignaturecompiled.utils.TimedPoint control1,
                                                            com.iavariav.root.digitalsignaturecompiled.utils.TimedPoint control2, TimedPoint endPoint) {
        this.startPoint = startPoint;
        this.control1 = control1;
        this.control2 = control2;
        this.endPoint = endPoint;
        return this;
    }

    public float length() {
        int steps = 10;
        float length = 0;
        double cx, cy, px = 0, py = 0, xDiff, yDiff;

        for (int i = 0; i <= steps; i++) {
            float t = (float) i / steps;
            cx = point(t, this.startPoint.x, this.control1.x,
                    this.control2.x, this.endPoint.x);
            cy = point(t, this.startPoint.y, this.control1.y,
                    this.control2.y, this.endPoint.y);
            if (i > 0) {
                xDiff = cx - px;
                yDiff = cy - py;
                length += Math.sqrt(xDiff * xDiff + yDiff * yDiff);
            }
            px = cx;
            py = cy;
        }
        return length;

    }

    public double point(float t, float start, float c1, float c2, float end) {
        return start * (1.0 - t) * (1.0 - t) * (1.0 - t)
                + 3.0 * c1 * (1.0 - t) * (1.0 - t) * t
                + 3.0 * c2 * (1.0 - t) * t * t
                + end * t * t * t;
    }

}
