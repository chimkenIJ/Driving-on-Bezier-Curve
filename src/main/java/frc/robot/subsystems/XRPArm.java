package frc.robot.subsystems;

import edu.wpi.first.wpilibj.xrp.XRPServo;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class XRPArm extends SubsystemBase {
  private final XRPServo m_armServo;

  public XRPArm() {
    m_armServo = new XRPServo(4);
  }

  public void periodic() {}

  public void setAngle(double angleDeg) {
    m_armServo.setAngle(angleDeg);
  }
}
