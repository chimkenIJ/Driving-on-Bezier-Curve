// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.XRPDrivetrain;

public class DriveDistance extends Command {
  private final XRPDrivetrain m_xrpDrivetrain;
  private final double m_distance;
  private final double m_speed;
  boolean first = true;

  /**
   * Creates a new DriveDistance. This command will drive your your robot for a desired distance at
   * a desired speed.
   *
   * @param speed The speed at which the robot will drive
   * @param inches The number of inches the robot will drive
   * @param drive The drivetrain subsystem on which this command will run
   */
  public DriveDistance(double speed, double inches, XRPDrivetrain drivetrain) {
    m_distance = inches;
    m_speed = speed;
    m_xrpDrivetrain = drivetrain;
    addRequirements(drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_xrpDrivetrain.arcadeDrive(0, 0);
    m_xrpDrivetrain.resetEncoders();
    System.out.println("Drive " + m_distance + " inches");
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_xrpDrivetrain.arcadeDrive(m_speed, 0);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_xrpDrivetrain.arcadeDrive(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    // Compare distance travelled from start to desired distance
    if (Math.abs(m_xrpDrivetrain.getAverageDistanceInch()) >= m_distance) {}
    return false;
  }
}
