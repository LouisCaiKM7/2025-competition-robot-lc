//package frc.robot.subsystems.beambreak;
//
//import edu.wpi.first.wpilibj.simulation.AnalogInputSim;
//import frc.robot.drivers.BeamBreak;
//
//public class BeambreakIOSim implements BeambreakIO {
//    private final BeamBreak beamBreak;
//
//    public BeambreakIOSim(int id) {
//        beamBreak = new BeamBreak(id);
//        AnalogInputSim beamBreakSim = new AnalogInputSim(id);
//        beamBreakSim.setInitialized(true);
//    }
//    @Override
//    public void updateInputs(BeambreakIOInputs inputs) {
//        inputs.isBeambreakOn = beamBreak.get();
//        inputs.voltage = beamBreak.getVoltage();
//    }
//}
//
