/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.VictorSP;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class conveyer extends SubsystemBase {
  
  private double maxConveyer = 0.7;
  private WPI_VictorSPX m_delivery = null;

  private double direction;

  public conveyer() {
    direction = 0;
    m_delivery = new WPI_VictorSPX(Constants.delivery);
  }

  public void deliveryDrive(double speed){
    direction = speed;
    m_delivery.set(-speed*maxConveyer);
  }

  public double getDirection(){
    return direction;
  }

  @Override
  public void periodic() {
    maxConveyer = NetworkTableInstance.getDefault().getTable("Preferences").getEntry("maxConveyer").getDouble(0.0);
  }
}
