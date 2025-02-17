// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.pathplanner.lib.util.FileVersionException;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.*;
import edu.wpi.first.wpilibj2.command.button.*;
import frc.robot.auto.basics.AutoActions;
//import frc.robot.commands.FunnelIntakeCommand;
//import frc.robot.commands.GroundIntakeCommand;
//import frc.robot.commands.PutCoralCommand;
import frc.robot.commands.RumbleCommand;
import frc.robot.display.Display;
//import frc.robot.subsystems.beambreak.BeambreakIOReal;
//import frc.robot.subsystems.elevator.ElevatorIOReal;
//import frc.robot.subsystems.elevator.ElevatorIOSim;
//import frc.robot.subsystems.endeffector.EndEffectorIOSim;
//import frc.robot.subsystems.beambreak.BeambreakIOSim;
//
//import frc.robot.subsystems.elevator.ElevatorSubsystem;
//import frc.robot.subsystems.endeffector.EndEffectorIOReal;
//import frc.robot.subsystems.endeffector.EndEffectorSubsystem;
//import frc.robot.subsystems.intake.IntakeRollerIOReal;
//import frc.robot.subsystems.intake.IntakeRollerIOSim;
//import frc.robot.subsystems.intake.IntakePivotIOReal;
//import frc.robot.subsystems.intake.IntakePivotIOSim;
//import frc.robot.subsystems.intake.IntakeSubsystem;
import frc.robot.subsystems.apriltagvision.AprilTagVision;
import frc.robot.subsystems.apriltagvision.AprilTagVisionIONorthstar;
import frc.robot.subsystems.swerve.Swerve;
import frc.robot.utils.AllianceFlipUtil;
import lombok.Getter;
import org.frcteam6941.looper.UpdateManager;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.function.BooleanSupplier;

import static edu.wpi.first.units.Units.Seconds;
import static frc.robot.RobotConstants.ElevatorConstants.*;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
    @Getter
    private final UpdateManager updateManager;
    public static final CommandXboxController driverController = new CommandXboxController(0);
    CommandXboxController operatorController = new CommandXboxController(1);
    CommandXboxController testerController = new CommandXboxController(2);
    CommandGenericHID streamDeckController = new CommandGenericHID(3);
    double lastResetTime = 0.0;

    // The robot's subsystems and commands are defined here...
    AprilTagVision aprilTagVision = new AprilTagVision(
            this::getAprilTagLayoutType,
            new AprilTagVisionIONorthstar(this::getAprilTagLayoutType, 0),
            new AprilTagVisionIONorthstar(this::getAprilTagLayoutType, 1));
    Swerve swerve = Swerve.getInstance();
    Display display = Display.getInstance();
//    ElevatorSubsystem elevatorSubsystem;
//    EndEffectorSubsystem endEffectorSubsystem;
//    IntakeSubsystem intakeSubsystem;

    //important flags for state logic
    public static boolean elevatorIsDanger;
    public static boolean intakeIsDanger;
    public static boolean preShootIsDanger;

    private double elevatorSetPoint = L3_EXTENSION_METERS.get();

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {
//        configureSubsystems();
        updateManager = new UpdateManager(swerve,
                display);
        updateManager.registerAll();

        configureDriverBindings(driverController);
        configureOperatorBindings(operatorController);
        configureTesterBindings(testerController);
        configureStreamDeckBindings(streamDeckController);
    }

    /**
     * Use this method to define your trigger->command mappings. Triggers can be created via the
     * {@link Trigger#Trigger(BooleanSupplier)} constructor with an arbitrary
     * predicate, or via the named factories in {@link
     * CommandGenericHID}'s subclasses for {@link
     * CommandXboxController Xbox}/{@link CommandPS4Controller
     * PS4} controllers or {@link CommandJoystick Flight
     * joysticks}.
     */

//     public void configureSubsystems(){
//        if (RobotBase.isReal()) {
//                elevatorSubsystem = new ElevatorSubsystem(new ElevatorIOReal());
//                endEffectorSubsystem = new EndEffectorSubsystem(new EndEffectorIOReal(), new BeambreakIOReal(RobotConstants.BeamBreakConstants.ENDEFFECTOR_MIDDLE_BEAMBREAK_ID), new BeambreakIOReal(RobotConstants.BeamBreakConstants.ENDEFFECTOR_EDGE_BEAMBREAK_ID));
//                intakeSubsystem = new IntakeSubsystem(new IntakePivotIOReal(),new IntakeRollerIOReal());
//        }else{
//                elevatorSubsystem = new ElevatorSubsystem(new ElevatorIOSim());
//                endEffectorSubsystem = new EndEffectorSubsystem(new EndEffectorIOSim(), new BeambreakIOSim(RobotConstants.BeamBreakConstants.ENDEFFECTOR_MIDDLE_BEAMBREAK_ID), new BeambreakIOSim(RobotConstants.BeamBreakConstants.ENDEFFECTOR_EDGE_BEAMBREAK_ID));
//                intakeSubsystem = new IntakeSubsystem(new IntakePivotIOSim(), new IntakeRollerIOSim());
//        }
//     }

    //Define all commands here
    // private final Command groundIntakeCommand = new Command() {
    //     @Override
    //     public void execute() {
    //         intakeSubsystem.setWantedState(IntakeSubsystem.WantedState.DEPLOY_INTAKE);
    //         endEffectorSubsystem.setWantedState(EndEffectorSubsystem.WantedState.GROUND_INTAKE);
    //         elevatorSubsystem.setElevatorPosition(HOME_EXTENSION_METERS.get());
    //     }

    //     @Override
    //     public void end(boolean interrupted) {
    //         intakeSubsystem.setWantedState(IntakeSubsystem.WantedState.FUNNEL_AVOID);
    //         elevatorSubsystem.setElevatorPosition(IDLE_EXTENSION_METERS.get());
    //     }

    //     @Override
    //     public boolean isFinished() {
    //         return endEffectorSubsystem.hasCoral();
    //     }
    // };

    // private final Command preShootCommand = new Command() {
    //     @Override
    //     public void execute() {
    //         intakeSubsystem.setWantedState(IntakeSubsystem.WantedState.HOME);
    //         elevatorSubsystem.setElevatorPosition(elevatorSetPoint);
    //         endEffectorSubsystem.setWantedState(EndEffectorSubsystem.WantedState.PRE_SHOOT);
    //     }

    //     @Override
    //     public void end(boolean interrupted) {
    //         elevatorSubsystem.setElevatorPosition(IDLE_EXTENSION_METERS.get());
    //     }

    //     @Override
    //     public boolean isFinished() {
    //         return endEffectorSubsystem.isShootFinished();
    //     }
    // };

    // private final Command shootCommand = new Command() {
    //     @Override
    //     public void execute() {
    //         endEffectorSubsystem.setWantedState(EndEffectorSubsystem.WantedState.SHOOT);
    //     }

    //     @Override
    //     public boolean isFinished() {
    //         return endEffectorSubsystem.isShootFinished();
    //     }
    // };

    // private final Command putCoralCommand = new ParallelCommandGroup(
    //         preShootCommand,
    //         Commands.sequence(
    //                 new WaitUntilCommand(() -> driverController.a().getAsBoolean() && endEffectorSubsystem.isShootReady()),
    //                 shootCommand
    //         )
    // );



    //Configure all commands for driver
    private void configureDriverBindings(CommandXboxController driverController) {
        swerve.setDefaultCommand(Commands
                .runOnce(() -> swerve.drive(
                                new Translation2d(
                                        -driverController.getLeftY()
                                                * RobotConstants.SwerveConstants.maxSpeed.magnitude(),
                                        -driverController.getLeftX()
                                                * RobotConstants.SwerveConstants.maxSpeed.magnitude()),
                                -driverController.getRightX()
                                        * RobotConstants.SwerveConstants.maxAngularRate.magnitude(),
                                true,
                                false),
                        swerve));

        driverController.start().onTrue(
                Commands.runOnce(() -> {
                    /*
                        TODO: the reset command will be activated twice when the start button is pressed only once,
                        this is only a temporary solution to avoid execute the command twice within 0.01s,
                        please fix the bug
                    */
                    if (Timer.getFPGATimestamp() - lastResetTime > 0.01) {
                        swerve.resetHeadingController();
                        swerve.resetPose(
                                new Pose2d(
                                        AllianceFlipUtil.apply(
                                                new Translation2d(0, 0)),
                                        swerve.getLocalizer().getLatestPose().getRotation()));
                    }
                    lastResetTime = Timer.getFPGATimestamp();
                }).ignoringDisable(true));

                
//        driverController.rightBumper().whileTrue(new GroundIntakeCommand(intakeSubsystem, endEffectorSubsystem, elevatorSubsystem));
//        driverController.rightTrigger().whileTrue(new PutCoralCommand(endEffectorSubsystem, elevatorSubsystem, intakeSubsystem,L3_EXTENSION_METERS.get()));
//        driverController.leftBumper().whileTrue(new FunnelIntakeCommand(elevatorSubsystem, endEffectorSubsystem, intakeSubsystem));
//        driverController.povDown().onTrue(Commands.runOnce(() -> elevatorSubsystem.setElevatorState(ElevatorSubsystem.WantedState.ZERO)));
//        driverController.start().onTrue(Commands.runOnce(() -> intakeSubsystem.setWantedState(IntakeSubsystem.WantedState.GROUNDZERO)));
    }

    //Configure all commands for operator
    private void configureOperatorBindings(CommandXboxController operatorController) {

    }

    //Configure all commands for testing
    private void configureTesterBindings(CommandXboxController controller) {
//        //test of endeffector state machine
//        controller.povLeft().onTrue(Commands.runOnce(() -> endEffectorSubsystem.setWantedState(EndEffectorSubsystem.WantedState.FUNNEL_INTAKE)));
//        controller.povRight().onTrue(Commands.runOnce(() -> endEffectorSubsystem.setWantedState(EndEffectorSubsystem.WantedState.GROUND_INTAKE)));
//        controller.povUp().onTrue(Commands.runOnce(() -> endEffectorSubsystem.setWantedState(EndEffectorSubsystem.WantedState.SHOOT)));
//
//        //test of elevator state machine
//        controller.a().onTrue(Commands.runOnce(() -> elevatorSubsystem.setElevatorPosition(L1_EXTENSION_METERS.get())));
//        controller.b().onTrue(Commands.runOnce(() -> elevatorSubsystem.setElevatorPosition(L2_EXTENSION_METERS.get())));
//        controller.x().onTrue(Commands.runOnce(() -> elevatorSubsystem.setElevatorPosition(L3_EXTENSION_METERS.get())));
//        controller.y().onTrue(Commands.runOnce(() -> elevatorSubsystem.setElevatorPosition(L4_EXTENSION_METERS.get())));
//
//        //test of intake states
//
//        controller.rightBumper().onTrue((Commands.runOnce(() -> intakeSubsystem.setWantedState(IntakeSubsystem.WantedState.DEPLOY_INTAKE))));
//        controller.leftBumper().onTrue((Commands.runOnce(() -> intakeSubsystem.setWantedState(IntakeSubsystem.WantedState.GROUNDZERO))));
//        controller.rightTrigger().onTrue((Commands.runOnce(() -> intakeSubsystem.setWantedState(IntakeSubsystem.WantedState.FUNNEL_AVOID))));
//        controller.leftTrigger().onTrue((Commands.runOnce(() -> intakeSubsystem.setWantedState(IntakeSubsystem.WantedState.HOME))));
//        controller.povUp().onTrue((Commands.runOnce(() -> intakeSubsystem.setWantedState(IntakeSubsystem.WantedState.TREMBLE_INTAKE))));
//        controller.povDown().onTrue((Commands.runOnce(() -> intakeSubsystem.setWantedState(IntakeSubsystem.WantedState.OUTTAKE))));
//
    }

    //Configure all commands for Stream Deck
    private void configureStreamDeckBindings(CommandGenericHID controller) {
        controller.button(1).onTrue(Commands.runOnce(() -> System.out.println("Stream Deck Controller Test Successful!")));
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     * @throws ParseException
     * @throws IOException
     * @throws FileVersionException
     */
    public Command getAutonomousCommand() throws FileVersionException, IOException, ParseException {
        // An example command will be run in autonomous
        return new SequentialCommandGroup(
                AutoActions.waitFor(0.000001),
                AutoActions.followTrajectory(AutoActions.getTrajectory("T_4"), true, true)
        );
    }

    private Command rumbleDriver(double seconds) {
        return new RumbleCommand(Seconds.of(seconds), driverController.getHID());
    }

    public FieldConstants.AprilTagLayoutType getAprilTagLayoutType() {
//        if (aprilTagsSpeakerOnly.getAsBoolean()) {
//            return FieldConstants.AprilTagLayoutType.SPEAKERS_ONLY;
//        } else if (aprilTagsAmpOnly.getAsBoolean()) {
//            return FieldConstants.AprilTagLayoutType.AMPS_ONLY;
//        } else {
        return FieldConstants.defaultAprilTagType;
//        }
    }
}