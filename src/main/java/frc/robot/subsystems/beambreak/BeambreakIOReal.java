//package frc.robot.subsystems.beambreak;
//
//import frc.robot.drivers.BeamBreak;
//
//public class BeambreakIOReal implements BeambreakIO {
//  final BeamBreak beambreak;
//
//  public BeambreakIOReal(int id) {
//    beambreak = new BeamBreak(id);
//  }
//
//  public void updateInputs(BeambreakIOInputs inputs) {
//    inputs.isBeambreakOn = beambreak.get();
//    inputs.voltage = beambreak.getVoltage();
//  }
//}
