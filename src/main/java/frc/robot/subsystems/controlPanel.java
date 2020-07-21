/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import frc.robot.Constants;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class controlPanel extends SubsystemBase {
  private WPI_VictorSPX m_control = null;
  private Servo m_conLift = null;

  public controlPanel() {
    m_control = new WPI_VictorSPX(Constants.controlPanel);
    m_conLift = new Servo(Constants.controlPanelLift);
  }
  
  // Sets servo angle
  public void controlLift(double angle){
    m_conLift.setAngle(angle);
  }

  public void controlSpin(double speed){
    m_control.set(speed);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
