package frc.robot;


import java.util.ArrayList;
import java.util.List;


public class BezierCurveCalc {
 private int orderNum;
 private double step = 0.05;
 List<double[]> points;
 List<double[]> curve;


 public BezierCurveCalc(List<double[]> points) {
   orderNum = points.size();
   this.points = points;
 }


 public List<double[]> calcCurve() {
   List<double[]> newPoints = new ArrayList<>();
   double time = 0;
   do {
     newPoints.add(getPoint(time));
     time += step;


   } while (time < 1);


   return newPoints;
 }


 public double[] getPoint(double time) {
   double x = 0;
   double y = 0;
   for (int i = 0; i < orderNum; i++) {
     x += 24 * b(time, i, points.get(i)[0]);
   }
   for (int i = 0; i < orderNum; i++) {
     y += 24 * b(time, i, points.get(i)[1]);
   }


   double[] newPoint = {x, y};


   return newPoint;
 }


 public double b(double time, int K, double v) {
   return nChooseK(orderNum, K) * Math.pow(1 - time, orderNum - K) * Math.pow(time, K) * v;
 }


 public double nChooseK(int n, int k) {
   if (k > n) return 0;
   if (k == 0 || k == n) return 1;
   return nChooseK(n - 1, k - 1) + nChooseK(n - 1, k);
 }
}


