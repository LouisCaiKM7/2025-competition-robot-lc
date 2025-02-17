//package frc.robot.subsystems.elevator;
//
//import edu.wpi.first.math.*;
//import edu.wpi.first.math.controller.ElevatorFeedforward;
//import edu.wpi.first.math.controller.ProfiledPIDController;
//import edu.wpi.first.math.numbers.N1;
//import edu.wpi.first.math.numbers.N2;
//import edu.wpi.first.math.system.NumericalIntegration;
//import edu.wpi.first.math.system.plant.DCMotor;
//import edu.wpi.first.math.trajectory.TrapezoidProfile.Constraints;
//import edu.wpi.first.math.util.Units;
//import edu.wpi.first.units.Measure;
//import edu.wpi.first.units.VoltageUnit;
//import edu.wpi.first.wpilibj.simulation.ElevatorSim;
//import frc.robot.RobotConstants.ElevatorGainsClass;
//
//import static edu.wpi.first.units.Units.*;
//import static frc.robot.RobotConstants.ElevatorConstants.ELEVATOR_GEAR_RATIO;
//import static frc.robot.RobotConstants.ElevatorConstants.ELEVATOR_SPOOL_DIAMETER;
//import static frc.robot.RobotConstants.ElevatorConstants.MAX_EXTENSION_METERS;
//
//public class ElevatorIOSim implements ElevatorIO {
//    private static final double LOOP_PERIOD_SECS = 0.02;
////    public final ElevatorSim elevatorTalonSim =
////    new ElevatorSim(
////            DCMotor.getKrakenX60Foc(2),
////            6.75,
////            // Add half of first stage mass bc its on a 2:1 ratio compared to carriage
////            Units.lbsToKilograms(7.0 + (3.25 / 2)),
////            ELEVATOR_SPOOL_DIAMETER/2,
////            0.0,
////            MAX_EXTENSION_METERS.get(),
////            true,
////            0.0);
//    private static final DCMotor elevatorTalonsim = DCMotor.getKrakenX60Foc(2).withReduction(6.75);
//
//    private final ProfiledPIDController controller =
//        new ProfiledPIDController(ElevatorGainsClass.ELEVATOR_KP.get(),ElevatorGainsClass.ELEVATOR_KI.get(),ElevatorGainsClass.ELEVATOR_KD.get(),
//        new Constraints(5.0, 10.0));
//    private double feedforward = 0.0;
////    private final ElevatorFeedforward ff =
////        new ElevatorFeedforward(
////            ElevatorGainsClass.ELEVATOR_KS.get(),
////            ElevatorGainsClass.ELEVATOR_KG.get(),
////            ElevatorGainsClass.ELEVATOR_KV.get(),
////            (DCMotor.getKrakenX60Foc(1).KvRadPerSecPerVolt * ELEVATOR_SPOOL_DIAMETER/2)
////            / ELEVATOR_GEAR_RATIO);
//
//    private Measure<VoltageUnit> appliedVolts = Volts.zero();
//    //private Measure<VoltageUnit> followerAppliedVoltage = Volts.zero();
//
//    public static final double carriaeMass =Units.lbsToKilograms(7.0 + (3.25 / 2));
//    public static final Matrix<N2, N2> A =
//            MatBuilder.fill(
//                    Nat.N2(),
//                    Nat.N2(),
//                    0,
//                    1,
//                    0,
//                    -elevatorTalonsim.KtNMPerAmp
//                            / (elevatorTalonsim.rOhms
//                            * Math.pow(ELEVATOR_SPOOL_DIAMETER, 2)
//                            * (carriaeMass)
//                            * elevatorTalonsim.KvRadPerSecPerVolt));
//
//    public static final Vector<N2> B =
//            VecBuilder.fill(
//                    0.0, elevatorTalonsim.KtNMPerAmp / (ELEVATOR_SPOOL_DIAMETER * carriaeMass));
//
//    // State given by elevator carriage position and velocity
//    // Input given by torque current to motor
//    private Vector<N2> simState;
//    private double inputTorqueCurrent = 0.0;
//
//    public ElevatorIOSim() {
//        simState = VecBuilder.fill(0.0, 0.0);
//    }
//
//    @Override
//    public void updateInputs(ElevatorIOInputs inputs) {
//        for (int i = 0; i < LOOP_PERIOD_SECS / (1.0 / 1000.0); i++) {
//            setInputTorqueCurrent(
//                    controller.calculate(simState.get(0) / ELEVATOR_SPOOL_DIAMETER ) + feedforward);
//            update(1.0 / 1000.0);
//        }
//
//        inputs.positionMeters = talonPosToHeight(simState.get(0));
//        inputs.velocityMetersPerSec =talonPosToHeight(simState.get(1));
//        inputs.statorCurrentAmps = new double[]{
//                Math.copySign(inputTorqueCurrent,appliedVolts.magnitude()),
//                Math.copySign(inputTorqueCurrent,appliedVolts.magnitude())
//        };
//
//    }
//
//    private void update(double dt) {
//        inputTorqueCurrent =
//                MathUtil.clamp(inputTorqueCurrent, -elevatorTalonsim.stallCurrentAmps, elevatorTalonsim.stallCurrentAmps);
//        Matrix<N2, N1> updatedState =
//                NumericalIntegration.rkdp(
//                        (Matrix<N2, N1> x, Matrix<N1, N1> u) ->
//                                A.times(x)
//                                        .plus(B.times(u))
//                                        .plus(
//                                                VecBuilder.fill(
//                                                        0.0,
//                                                        -9.8)),
//                        simState,
//                        MatBuilder.fill(Nat.N1(), Nat.N1(), inputTorqueCurrent),
//                        dt);
//        // Apply limits
//        simState = VecBuilder.fill(updatedState.get(0, 0), updatedState.get(1, 0));
//        if (simState.get(0) <= 0.0) {
//            simState.set(1, 0, 0.0);
//            simState.set(0, 0, 0.0);
//        }
//        if (simState.get(0) >= MAX_EXTENSION_METERS.get()) {
//            simState.set(1, 0, 0.0);
//            simState.set(0, 0, MAX_EXTENSION_METERS.get());
//        }
//    }
//
//    private void setInputTorqueCurrent(double torqueCurrent) {
//        inputTorqueCurrent = torqueCurrent;
//        appliedVolts =
//                Volts.of(elevatorTalonsim.getVoltage(
//                        elevatorTalonsim.getTorque(inputTorqueCurrent), simState.get(1, 0) / ELEVATOR_SPOOL_DIAMETER));
//        appliedVolts = Volts.of(MathUtil.clamp(appliedVolts.magnitude(), -12.0, 12.0));
//    }
//
//    @Override
//    public void setElevatorDirectVoltage(double volts) {
//
//    }
//
//    @Override
//    public void resetElevatorPosition() {
//        // physicsSim.setState(0,0);
//    }
//
//    @Override
//    public boolean isNearExtension(double expected) {
//        return true;
//    }
//
//    @Override
//    public void setElevatorTarget(double meters) {
//        controller.setGoal(heightToTalonPos(meters));
//        this.feedforward = feedforward;
//    }
//
//
//    @Override
//    public double getElevatorHeight() {
//        return talonPosToHeight(simState.get(0));
//    }
//
//    @Override
//    public boolean isNearZeroExtension() {
//        return true;
//    }
//
//    @Override
//    public double getElevatorVelocity() {
//        return talonPosToHeight(simState.get(1));
//    }
//
//    private double heightToTalonPos(double heightMeters) {
//        return (heightMeters / (Math.PI * ELEVATOR_SPOOL_DIAMETER)) ;
//    }
//
//    private double talonPosToHeight(double rotations) {
//        return rotations * (Math.PI * ELEVATOR_SPOOL_DIAMETER) ;
//    }
//}