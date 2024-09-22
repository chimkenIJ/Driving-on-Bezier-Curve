package frc.robot.commands;


import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.XRPDrivetrain;
import java.util.List;


public class DriveCurve extends SequentialCommandGroup {
 public DriveCurve(List<double[]> curve, XRPDrivetrain drivetrain) {
   double currX = 0;
   double currY = 0;
   double currAng = 0;
   for (int i = 0; i < curve.size() - 1; i++) {
     double ang = Math.toDegrees(Math.atan((curve.get(i + 1)[1]) / (curve.get(i + 1)[0])));
     if (Double.isNaN(ang)) {
       ang = 0;
     }
     currAng = ang - currAng + 90;
     double dist =
         (curve.get(i)[0] - currX) * (curve.get(i)[0] - currX)
             + (curve.get(i)[1] - currY) * (curve.get(i)[1] - currY);
     System.out.println(curve.get(i)[0] + ", " + curve.get(i)[1]);


     addCommands(
         new TurnDegrees(1, currAng, drivetrain), (new DriveDistance(1, dist, drivetrain)));


     currX = curve.get(i)[0];
     currY = curve.get(i)[1];
   }
   System.out.println("done ^-^");
 }
}


