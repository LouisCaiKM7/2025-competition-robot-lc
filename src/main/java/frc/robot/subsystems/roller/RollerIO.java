//package frc.robot.subsystems.roller;
//
//import org.littletonrobotics.junction.AutoLog;
//
//public interface RollerIO {
//    @AutoLog
//    public static class RollerIOInputs {
//        public double velocityRotPerSec = 0.0;
//        public double appliedVolts = 0.0;
//        public double statorCurrentAmps = 0.0;
//        public double supplyCurrentAmps = 0.0;
//        public double tempCelsius = 0.0;
//    }
//
//    public default void updateInputs(RollerIOInputs inputs) {}
//
//    public default void updateConfigs(double kp, double ki, double kd, double ka, double kv, double ks) {}
//
//    public default void setVoltage(double voltage) {}
//
//    public default void stop() {
//        setVoltage(0.0);
//    }
//
//    public default void setVelocity(double velocityRPS) {}
//}
