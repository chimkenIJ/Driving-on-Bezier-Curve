package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.XRPArm;

public class MarkerMove extends Command {
  private final XRPArm m_xrpServo;
  private double degAngle;

  public MarkerMove(XRPArm servo, double angle) {
    m_xrpServo = servo;
    degAngle = angle;
    addRequirements(servo);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    m_xrpServo.setAngle(degAngle);
    isFinished();
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}
