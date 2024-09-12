package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.subsystems.XRPDrivetrain;
import java.util.ArrayList;
import java.util.List;

public class BezierCurve extends Command {
  private final XRPDrivetrain m_xrpDrivetrain;
  private int orderNum;
  private double step = 0.05;
  List<double[]> points;
  List<double[]> curve;
  boolean ans = false;

  public BezierCurve(XRPDrivetrain drivetrain, List<double[]> points) {
    m_xrpDrivetrain = drivetrain;
    orderNum = points.size();
    this.points = points;
    addRequirements(drivetrain);
  }

  @Override
  public void initialize() {
    ans = false;
    System.out.println("Starting to calculate...");
    curve = calcCurve();
    System.out.println("Initalized!! Yeyyy =^.^=");
  }

  @Override
  public void execute() {
    double currX = 0;
    double currY = 0;
    double currAng = 0;

    for (int i = 0; i < curve.size(); i++) {
      double ang = (Math.tan((curve.get(i)[1] - currY) / (curve.get(i)[0] - currX)));
      if (Double.isNaN(ang)) {
        ang = 0;
      }
      currAng = currAng + ang;
      double dist =
          (curve.get(i)[0] - currX) * (curve.get(i)[0] - currX)
              + (curve.get(i)[1] - currY) * (curve.get(i)[1] - currY);
      /*System.out.println(curve.get(i)[0] + ", " + curve.get(i)[1]);
      System.out.println(currAng);
      System.out.println(dist);*/

      // CommandScheduler.getInstance().schedule(new TurnDegrees(1, currAng, m_xrpDrivetrain));
      // CommandScheduler.getInstance().schedule(new WaitCommand(0.2));
      // CommandScheduler.getInstance().schedule(new
      // DriveDistance(1,(curve.get(i)[0]-currX)*(curve.get(i)[0]-currX) +
      // (curve.get(i)[1]-currY)*(curve.get(i)[1]-currY),m_xrpDrivetrain));
      // CommandScheduler.getInstance().schedule(new WaitCommand(0.2));

      CommandScheduler.getInstance()
          .schedule(
              new TurnDegrees(1, currAng, m_xrpDrivetrain),
              (new DriveDistance(1, dist, m_xrpDrivetrain)));

      currX = curve.get(i)[0];
      currY = curve.get(i)[1];
    }
    System.out.println("done ^-^");
    ans = true;
    isFinished();
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
      x += 15 * b(time, i, points.get(i)[0]);
    }
    for (int i = 0; i < orderNum; i++) {
      y += 15 * b(time, i, points.get(i)[1]);
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

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return ans;
  }
}
