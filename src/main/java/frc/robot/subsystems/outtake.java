/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;

public class outtake extends SubsystemBase {
  private WPI_TalonSRX m_outtake1 = null;
  private WPI_TalonSRX m_outtake2 = null;

  private double maxOut = 1;

  public outtake() {
    m_outtake1 = new WPI_TalonSRX(Constants.outtake1);
    m_outtake2 = new WPI_TalonSRX(Constants.outtake2);
  }

  public void driveOuttake(double speed){
    m_outtake1.set(speed*maxOut);
    m_outtake2.set(speed*maxOut);
  }

  @Override
  public void periodic() {
    maxOut = NetworkTableInstance.getDefault().getTable("Preferences").getEntry("maxShooter").getDouble(0.0);
  }
}
