/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class limelight extends SubsystemBase {
  //initiates networktable variables

  private NetworkTable table = null;
  private NetworkTableEntry tx = null;
  private NetworkTableEntry ty = null;
  private NetworkTableEntry tv = null;

  //limelight constants
  double cam_angle = 40;
  double lime_center_angle = 40;

  double lime_center_distance = 8.5;
  double lime_shooter_distance = 7;

  double height_target = 98.25;
  double height_cam = 26.5;

  //shooter constant
  double center_shooter_distance = 12;

  public limelight() {

  }

  //functions to grab limelight metrics
  public double getX(){
    return NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0.0);
    
  }

  public double getY(){
    return NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0.0);
  }

  public double getWidth(){
    return NetworkTableInstance.getDefault().getTable("limelight").getEntry("thor").getDouble(0.0);
  }

  public boolean validTarget(){
    return ((double) NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getNumber(0)) != 0.0;
  }

  //changes visionpipeline between ball aiming and target aiming
  public void changePipe(){
    NetworkTableEntry pipeNum = NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline");
    if(pipeNum.getDouble(0) == 0){
      pipeNum.setNumber(1);
    }
    else{
      pipeNum.setNumber(0);
    }
  }

  //changes vision mode between processing and driver cam
  public void changeMode(){
    NetworkTableEntry camMode = NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode");
    if(camMode.getDouble(0) == 0){
      camMode.setNumber(1);
    }
    else{
      camMode.setNumber(0);
    }
  }
  
  // distance between the center of the robot and the target
  public double getCenterDistance(){
    double lime_target_distance = (height_target-height_cam)/Math.tan(Math.toRadians(getY()+cam_angle));
    double center_target_distance = Math.sqrt(Math.pow(lime_target_distance,2)+Math.pow(lime_center_distance,2)-2*(lime_target_distance*lime_center_distance)*Math.cos(Math.toRadians(90+lime_center_angle)));

    return center_target_distance;      
  }

  public double getShooterDistance(){
    return getCenterDistance()+center_shooter_distance;
  }


  // angle correction since limelight is not aligned with the shooter
  public double getCorrection(){
    double correction = Math.toDegrees(Math.asin(lime_shooter_distance/getCenterDistance()));
    return Math.abs(correction)*1;
  }

  public double getPerfectSpeed(){
    return 1;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("targetX", getX());
    SmartDashboard.putNumber("targetY", getY());
    SmartDashboard.putNumber("targetW", getWidth());
    SmartDashboard.putBoolean("targetValid", validTarget());
    SmartDashboard.putNumber("distance according to lime", getShooterDistance());
  }
}
