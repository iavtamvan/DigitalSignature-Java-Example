package com.iavariav.root.digitalsignaturecompiled.utils;


/**
 * Build a SVG path as a string.
 *
 * https://www.w3.org/TR/SVGTiny12/paths.html
 */
public class SvgPathBuilder {

    public static final Character SVG_RELATIVE_CUBIC_BEZIER_CURVE = 'c';
    public static final Character SVG_MOVE = 'M';
    private final StringBuilder mStringBuilder;
    private final Integer mStrokeWidth;
    private final com.iavariav.root.digitalsignaturecompiled.utils.SvgPoint mStartPoint;
    private com.iavariav.root.digitalsignaturecompiled.utils.SvgPoint mLastPoint;

    public SvgPathBuilder(final com.iavariav.root.digitalsignaturecompiled.utils.SvgPoint startPoint, final Integer strokeWidth) {
        mStrokeWidth = strokeWidth;
        mStartPoint = startPoint;
        mLastPoint = startPoint;
        mStringBuilder = new StringBuilder();
        mStringBuilder.append(SVG_RELATIVE_CUBIC_BEZIER_CURVE);
    }

    public final Integer getStrokeWidth() {
        return mStrokeWidth;
    }

    public final com.iavariav.root.digitalsignaturecompiled.utils.SvgPoint getLastPoint() {
        return mLastPoint;
    }

    public com.iavariav.root.digitalsignaturecompiled.utils.SvgPathBuilder append(final com.iavariav.root.digitalsignaturecompiled.utils.SvgPoint controlPoint1, final com.iavariav.root.digitalsignaturecompiled.utils.SvgPoint controlPoint2, final com.iavariav.root.digitalsignaturecompiled.utils.SvgPoint endPoint) {
        mStringBuilder.append(makeRelativeCubicBezierCurve(controlPoint1, controlPoint2, endPoint));
        mLastPoint = endPoint;
        return this;
    }

    @Override
    public String toString() {
        return (new StringBuilder())
                .append("<path ")
                .append("stroke-width=\"")
                .append(mStrokeWidth)
                .append("\" ")
                .append("d=\"")
                .append(SVG_MOVE)
                .append(mStartPoint)
                .append(mStringBuilder)
                .append("\"/>")
                .toString();
    }

    private String makeRelativeCubicBezierCurve(final com.iavariav.root.digitalsignaturecompiled.utils.SvgPoint controlPoint1, final com.iavariav.root.digitalsignaturecompiled.utils.SvgPoint controlPoint2, final com.iavariav.root.digitalsignaturecompiled.utils.SvgPoint endPoint) {
        final String sControlPoint1 = controlPoint1.toRelativeCoordinates(mLastPoint);
        final String sControlPoint2 = controlPoint2.toRelativeCoordinates(mLastPoint);
        final String sEndPoint = endPoint.toRelativeCoordinates(mLastPoint);

        final StringBuilder sb = new StringBuilder();
        sb.append(sControlPoint1);
        sb.append(" ");
        sb.append(sControlPoint2);
        sb.append(" ");
        sb.append(sEndPoint);
        sb.append(" ");

        // discard zero curve
        final String svg = sb.toString();
        if ("c0 0 0 0 0 0".equals(svg)) {
            return "";
        } else {
            return svg;
        }
    }
}
