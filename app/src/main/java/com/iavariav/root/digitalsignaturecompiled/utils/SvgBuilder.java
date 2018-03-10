package com.iavariav.root.digitalsignaturecompiled.utils;

public class SvgBuilder  {

    private final StringBuilder mSvgPathsBuilder = new StringBuilder();
    private SvgPathBuilder mCurrentPathBuilder = null;

    public SvgBuilder() {
    }

    public void clear() {
        mSvgPathsBuilder.setLength(0);
        mCurrentPathBuilder = null;
    }

    public String build(final int width, final int height) {
        if (isPathStarted()) {
            appendCurrentPath();
        }
        return (new StringBuilder())
                .append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n")
                .append("<svg xmlns=\"http://www.w3.org/2000/svg\" version=\"1.2\" baseProfile=\"tiny\" ")
                .append("height=\"")
                .append(height)
                .append("\" ")
                .append("width=\"")
                .append(width)
                .append("\" ")
                .append("viewBox=\"")
                .append(0)
                .append(" ")
                .append(0)
                .append(" ")
                .append(width)
                .append(" ")
                .append(height)
                .append("\">")
                .append("<g ")
                .append("stroke-linejoin=\"round\" ")
                .append("stroke-linecap=\"round\" ")
                .append("fill=\"none\" ")
                .append("stroke=\"black\"")
                .append(">")
                .append(mSvgPathsBuilder)
                .append("</g>")
                .append("</svg>")
                .toString();
    }

    public com.iavariav.root.digitalsignaturecompiled.utils.SvgBuilder append(final Bezier curve, final float strokeWidth) {
        final Integer roundedStrokeWidth = Math.round(strokeWidth);
        final com.iavariav.root.digitalsignaturecompiled.utils.SvgPoint curveStartSvgPoint = new com.iavariav.root.digitalsignaturecompiled.utils.SvgPoint(curve.startPoint);
        final com.iavariav.root.digitalsignaturecompiled.utils.SvgPoint curveControlSvgPoint1 = new com.iavariav.root.digitalsignaturecompiled.utils.SvgPoint(curve.control1);
        final com.iavariav.root.digitalsignaturecompiled.utils.SvgPoint curveControlSvgPoint2 = new com.iavariav.root.digitalsignaturecompiled.utils.SvgPoint(curve.control2);
        final com.iavariav.root.digitalsignaturecompiled.utils.SvgPoint curveEndSvgPoint = new com.iavariav.root.digitalsignaturecompiled.utils.SvgPoint(curve.endPoint);

        if (!isPathStarted()) {
            startNewPath(roundedStrokeWidth, curveStartSvgPoint);
        }

        if (!curveStartSvgPoint.equals(mCurrentPathBuilder.getLastPoint())
                || !roundedStrokeWidth.equals(mCurrentPathBuilder.getStrokeWidth())) {
            appendCurrentPath();
            startNewPath(roundedStrokeWidth, curveStartSvgPoint);
        }

        mCurrentPathBuilder.append(curveControlSvgPoint1, curveControlSvgPoint2, curveEndSvgPoint);
        return this;
    }

    private void startNewPath(Integer roundedStrokeWidth, com.iavariav.root.digitalsignaturecompiled.utils.SvgPoint curveStartSvgPoint) {
        mCurrentPathBuilder = new SvgPathBuilder(curveStartSvgPoint, roundedStrokeWidth);
    }

    private void appendCurrentPath() {
        mSvgPathsBuilder.append(mCurrentPathBuilder);
    }

    private boolean isPathStarted() {
        return mCurrentPathBuilder != null;
    }

}
