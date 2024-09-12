// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.DriveCurve;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.TurnDegrees;
import frc.robot.subsystems.XRPArm;
import frc.robot.subsystems.XRPDrivetrain;
import java.util.Arrays;
import java.util.List;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final XRPDrivetrain m_xrpDrivetrain = new XRPDrivetrain();
  private final XRPArm m_xrpServo = new XRPArm();
  private final XboxController m_xbox = new XboxController(0);
  List<double[]> points =
      Arrays.asList(new double[] {0, 0}, new double[] {1, 1}, new double[] {2, 0});
  private final BezierCurveCalc curves = new BezierCurveCalc(points);
  boolean ans = false;
  private final ExampleCommand m_autoCommand = new ExampleCommand(m_xrpDrivetrain);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    m_xrpDrivetrain.setDefaultCommand(
        new RunCommand(
            () -> m_xrpDrivetrain.arcadeDrive(-m_xbox.getLeftY(), -m_xbox.getLeftX()),
            m_xrpDrivetrain));
    configureButtonBindings();

    /*List<double[]> curve = curves.calcCurve();
    double currX = 0;
    double currY = 0;
    double currAng = 0;
    for (int i = 0; i < curve.size(); i++) {
      double ang = (Math.tan((curve.get(i)[1]-currY)/(curve.get(i)[0]-currX)));
      if (Double.isNaN(ang)) {
        ang = 0;
      }
      currAng= currAng + ang;
      double dist = (curve.get(i)[0]-currX)*(curve.get(i)[0]-currX) + (curve.get(i)[1]-currY)*(curve.get(i)[1]-currY);
      //System.out.println(curve.get(i)[0] + ", " + curve.get(i)[1]);
      //System.out.println(currAng);
      //System.out.println(dist);
      //CommandScheduler.getInstance().schedule(new TurnDegrees(1, currAng, m_xrpDrivetrain));
      //CommandScheduler.getInstance().schedule(new WaitCommand(0.2));
      //CommandScheduler.getInstance().schedule(new DriveDistance(1,(curve.get(i)[0]-currX)*(curve.get(i)[0]-currX) + (curve.get(i)[1]-currY)*(curve.get(i)[1]-currY),m_xrpDrivetrain));
      //CommandScheduler.getInstance().schedule(new WaitCommand(0.2));

      //new TurnDegrees(1, currAng, m_xrpDrivetrain);
      //new DriveDistance(1, dist, m_xrpDrivetrain);
      CommandScheduler.getInstance().schedule(new TurnDegrees(1, currAng, m_xrpDrivetrain),(new DriveDistance(1,dist,m_xrpDrivetrain)));
      currX = curve.get(i)[0];
      currY = curve.get(i)[1];
    }
    System.out.println("done ^-^");*/

  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link edu.wpi.first.wpilibj.GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {

    /* Trigger userButton = new Trigger(m_xbox::getAButtonPressed);
    userButton
        .onTrue(new InstantCommand(() -> m_xrpServo.setAngle(45.0), m_xrpServo))
        .onFalse(new InstantCommand(() -> m_xrpServo.setAngle(0.0), m_xrpServo));
        */

    new JoystickButton(m_xbox, Button.kA.value).onTrue(new TurnDegrees(1, 45, m_xrpDrivetrain));
    // new JoystickButton(m_xbox, Button.kB.value)s
    //     .onTrue(new SequentialCommandGroup(new MarkerMove(m_xrpServo, 45.0), new
    // ParametricPath(m_xrpDrivetrain),new MarkerMove(m_xrpServo, 0.0)));

    List<double[]> curve = curves.calcCurve();

    new JoystickButton(m_xbox, Button.kB.value).onTrue(new DriveCurve(curve, m_xrpDrivetrain));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return m_autoCommand;
  }
}
