package frc.robot.subsystems.endeffector;

//import edu.wpi.first.wpilibj.DriverStation;
//import frc.robot.RobotContainer;
//import org.littletonrobotics.junction.Logger;
//import frc.robot.RobotConstants;
//import frc.robot.subsystems.elevator.ElevatorSubsystem;
//import frc.robot.subsystems.beambreak.BeambreakIO;
//import frc.robot.subsystems.beambreak.BeambreakIOInputsAutoLogged;
//import frc.robot.subsystems.roller.RollerSubsystem;
//
//import static frc.robot.RobotConstants.EndEffectorConstants.*;
//import static frc.robot.RobotConstants.EndEffectorConstants.EndEffectorGainsClass.*;
//
//public class EndEffectorSubsystem extends RollerSubsystem {
//
//    public static final String NAME = "EndEffector";
//
//    private final EndEffectorIO endEffectorIO;
//    private final BeambreakIO middleBBIO, edgeBBIO;
//    private BeambreakIOInputsAutoLogged middleBBInputs = new BeambreakIOInputsAutoLogged();
//    private BeambreakIOInputsAutoLogged edgeBBInputs = new BeambreakIOInputsAutoLogged();
//
//    private WantedState wantedState = WantedState.IDLE;
//    private SystemState systemState = SystemState.IDLING;
//
//    private boolean hasTransitionedToHold = false;
//    private boolean hasTransitionedToPreShoot = false;
//
//    public double kp = ENDEFFECTOR_KP.get();
//    public double ki = ENDEFFECTOR_KI.get();
//    public double kd = ENDEFFECTOR_KD.get();
//    public double ka = ENDEFFECTOR_KA.get();
//    public double kv = ENDEFFECTOR_KV.get();
//    public double ks = ENDEFFECTOR_KS.get();
//
//    private static double idleRPS = IDLE_RPS.get();
//    private static double intakeRPS = INTAKE_RPS.get();
//    private static double preShootRPS = PRE_SHOOT_RPS.get();
//    private static double shootRPS = SHOOT_RPS.get();
//
//    public enum WantedState {
//        IDLE,
//        FUNNEL_INTAKE,
//        GROUND_INTAKE,
//        PRE_SHOOT,
//        SHOOT,
//        OFF
//    }
//
//    public enum SystemState {
//        IDLING,
//        FUNNEL_INTAKING,
//        GROUND_INTAKING,
//        PRE_SHOOTING,
//        SHOOTING,
//        OFF
//    }
//
//    public EndEffectorSubsystem(EndEffectorIO endEffectorIO , BeambreakIO middleBBIO, BeambreakIO edgeBBIO) {
//        super(endEffectorIO, NAME);
//        this.endEffectorIO = endEffectorIO;
//        this.middleBBIO = middleBBIO;
//        this.edgeBBIO = edgeBBIO;
//    }
//
//    @Override
//    public void periodic() {
//        super.periodic();
//
//        middleBBIO.updateInputs(middleBBInputs);
//        edgeBBIO.updateInputs(edgeBBInputs);
//
//        SystemState newState = handleStateTransition();
//
//        Logger.processInputs(NAME + "/Middle Beambreak", middleBBInputs);
//        Logger.processInputs(NAME + "/Edge Beambreak", edgeBBInputs);
//        Logger.recordOutput("EndEffector/SystemState", newState.toString());
//        Logger.recordOutput(NAME+"Velocity", inputs.velocityRotPerSec);
//
//        RobotContainer.preShootIsDanger = isPreShootReady();
//
//        Logger.recordOutput("Flags/preShootIsDanger", isPreShootReady());
//
//        if (newState != systemState) {
//            systemState = newState;
//        }
//
//        if (DriverStation.isDisabled()) {
//            systemState = SystemState.IDLING;
//        }
//
//        switch(systemState) {
//            case IDLING:
//                io.setVelocity(idleRPS);
//                break;
//            case FUNNEL_INTAKING:
//                io.setVelocity(-intakeRPS);
//                if (isIntakeFinished()) {
//                    io.setVelocity(0.0);
//                }
//                break;
//            case GROUND_INTAKING:
//                io.setVelocity(intakeRPS);
//                if (isIntakeFinished()) {
//                    io.setVelocity(0.0);
//                }
//                break;
//            case PRE_SHOOTING:
//                io.setVelocity(-preShootRPS);
//                if (isShootReady()) {
//                    io.setVelocity(0.0);
//                }
//                break;
//            case SHOOTING:
//                io.setVelocity(-shootRPS);
//                break;
//            case OFF:
//        }
//
//        if (RobotConstants.TUNING) {
//            intakeRPS = INTAKE_RPS.get();
//            preShootRPS = PRE_SHOOT_RPS.get();
//            shootRPS = SHOOT_RPS.get();
//
//            kp = ENDEFFECTOR_KP.get();
//            ki = ENDEFFECTOR_KI.get();
//            kd = ENDEFFECTOR_KD.get();
//            ka = ENDEFFECTOR_KA.get();
//            kv = ENDEFFECTOR_KV.get();
//            ks = ENDEFFECTOR_KS.get();
//
//            endEffectorIO.updateConfigs(kp, ki, kd, ka, kv, ks);
//        }
//    }
//
//    private SystemState handleStateTransition() {
//        return switch (wantedState) {
//            case IDLE -> SystemState.IDLING;
//            case FUNNEL_INTAKE -> {
//                if (!RobotContainer.elevatorIsDanger && (isIntakeFinished() || hasTransitionedToPreShoot)) {
//                    hasTransitionedToPreShoot = true;
//                    setWantedState(WantedState.PRE_SHOOT);
//                    yield SystemState.PRE_SHOOTING;
//                }
//                yield SystemState.FUNNEL_INTAKING;
//            }
//            case GROUND_INTAKE -> {
//                if (!RobotContainer.elevatorIsDanger && (isIntakeFinished() || hasTransitionedToPreShoot)) {
//                    hasTransitionedToPreShoot = true;
//                    setWantedState(WantedState.PRE_SHOOT);
//                    yield SystemState.PRE_SHOOTING;
//                }
//                yield SystemState.GROUND_INTAKING;
//            }
//            case PRE_SHOOT -> {
//                hasTransitionedToPreShoot = true;
//                yield SystemState.PRE_SHOOTING;
//            }
//            case SHOOT -> {
//                if (isShootFinished()) {
//                    hasTransitionedToPreShoot = false;
//                    hasTransitionedToHold = false;
//                    setWantedState(WantedState.IDLE);
//                    yield SystemState.IDLING;
//                }
//                yield SystemState.SHOOTING;
//            }
//            case OFF -> SystemState.OFF;
//        };
//    }
//
//    public boolean hasCoral () {return middleBBInputs.isBeambreakOn;}
//
//    public boolean isShootFinished () {return hasTransitionedToPreShoot && !edgeBBInputs.isBeambreakOn;}
//
//    public boolean isIntakeFinished () {return middleBBInputs.isBeambreakOn && !edgeBBInputs.isBeambreakOn;}
//
//    public boolean isShootReady () {return edgeBBInputs.isBeambreakOn && !middleBBInputs.isBeambreakOn;}
//
//    public boolean isPreShootReady () {return hasTransitionedToPreShoot;};
//
//    public void setWantedState(WantedState wantedState) {this.wantedState = wantedState;}
//}
