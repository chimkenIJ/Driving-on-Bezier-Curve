package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.XRPDrivetrain;
import java.util.List;

public class DriveCurve extends SequentialCommandGroup {
  public DriveCurve(List<double[]> curve, XRPDrivetrain drivetrain) {
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
      System.out.println(curve.get(i)[0] + ", " + curve.get(i)[1]);
      System.out.println(currAng);
      System.out.println(dist);

      addCommands(
          new TurnDegrees(1, currAng, drivetrain), (new DriveDistance(1, dist, drivetrain)));

      currX = curve.get(i)[0];
      currY = curve.get(i)[1];
    }
    System.out.println("done ^-^");
  }
}
