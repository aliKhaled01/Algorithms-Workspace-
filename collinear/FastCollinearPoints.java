/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {

    private ArrayList<LineSegment> arrayListLS;


    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {

        arrayListLS = new ArrayList<LineSegment>();

        if (points == null)
            throw new IllegalArgumentException("the array connot be nulled");

        //no nulled points are allowed
        //no mutation is allowed to the original array
        Point[] pointsCopy = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null)
                throw new IllegalArgumentException("points connot be nulled");
            else
                pointsCopy[i] = points[i];
        }

        //sorting then making a copy for slopeOrder sorting
        Arrays.sort(pointsCopy);
        //checking for points duplication
        for (int i = 0; i < pointsCopy.length - 1; i++)
            if (pointsCopy[i].compareTo(pointsCopy[i + 1]) == 0)
                throw new IllegalArgumentException("duplicated points are not allowed");


        for (int i = 0; i < pointsCopy.length - 1; i++) {
            ArrayList<Point> startPionts = new ArrayList<Point>();
            ArrayList<Point> endPoints = new ArrayList<Point>();

            Arrays.sort(pointsCopy, pointsCopy[i].slopeOrder());

            int noSuccessiveEqualSlopes = 0;
            double prevSlope = pointsCopy[0].slopeTo(pointsCopy[1]);
            Point startPoint = pointsCopy[1];
            int j;
            for (j = 2; j < pointsCopy.length; j++) {

                if (pointsCopy[0].slopeTo(pointsCopy[j]) == prevSlope)
                    noSuccessiveEqualSlopes++;

                else if (noSuccessiveEqualSlopes >= 2) {
                    startPionts.add(startPoint);
                    endPoints.add(pointsCopy[j - 1]);

                    startPoint = pointsCopy[j];
                    noSuccessiveEqualSlopes = 0;
                    prevSlope = pointsCopy[0].slopeTo(pointsCopy[j]);
                }
                else {
                    startPoint = pointsCopy[j];
                    noSuccessiveEqualSlopes = 0;
                    prevSlope = pointsCopy[0].slopeTo(pointsCopy[j]);
                }
            }
            if (noSuccessiveEqualSlopes >= 2) {
                startPionts.add(startPoint);
                endPoints.add(pointsCopy[j - 1]);
            }

            for (int k = 0; k < startPionts.size(); k++) {
                if (pointsCopy[0].compareTo(startPionts.get(k)) < 0)
                    arrayListLS.add(new LineSegment(pointsCopy[0], endPoints.get(k)));
            }
            Arrays.sort(pointsCopy);
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return arrayListLS.size();
    }

    // the line segments
    public LineSegment[] segments() {
        LineSegment[] arrLS = new LineSegment[arrayListLS.size()];
        for (int i = 0; i < arrayListLS.size(); i++) {
            arrLS[i] = arrayListLS.get(i);
        }
        return arrLS;
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        StdOut.println(collinear.numberOfSegments());
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
