// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.pathplanner.lib.util.FileVersionException;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.simulation.ElevatorSim;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.auto.basics.AutoActions;
import frc.robot.commands.ElevatordownCommand;
import frc.robot.commands.ElevatorupCommand;
import frc.robot.commands.ElevatordownCommand;
import frc.robot.display.Display;
import frc.robot.subsystems.apriltagvision.AprilTagVision;
import frc.robot.subsystems.apriltagvision.AprilTagVisionIONorthstar;
import frc.robot.subsystems.elevator.ElevatorIOTalonFX;
import frc.robot.subsystems.elevator.ElevatorSubsystem;
import frc.robot.subsystems.swerve.Swerve;
import frc.robot.utils.AllianceFlipUtil;
import lombok.Getter;
import org.frcteam6941.looper.UpdateManager;
import org.json.simple.parser.ParseException;

import java.io.IOException;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
    @Getter
    private final UpdateManager updateManager;
    @Getter
    AprilTagVision aprilTagVision = new AprilTagVision(
            this::getAprilTagLayoutType,
            new AprilTagVisionIONorthstar(this::getAprilTagLayoutType, 0),
            new AprilTagVisionIONorthstar(this::getAprilTagLayoutType, 1));
    Swerve swerve = Swerve.getInstance();
    CommandXboxController driverController = new CommandXboxController(0);
    CommandXboxController operatorController = new CommandXboxController(1);
    Display display = Display.getInstance();
    double lastResetTime = 0.0;
      ElevatorSubsystem elevatorSubsystem = new ElevatorSubsystem(new ElevatorIOTalonFX());
    // The robot's subsystems and commands are defined here...

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {
        updateManager = new UpdateManager(swerve,
                display);
        updateManager.registerAll();

        configureBindings();

        // Configure the trigger bindings
        configureBindings();
    }

    /**
     * Use this method to define your trigger->command mappings. Triggers can be created via the
     * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
     * predicate, or via the named factories in {@link
     * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
     * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
     * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
     * joysticks}.
     */
    private void configureBindings() {
        swerve.setDefaultCommand(Commands
                .runOnce(() -> swerve.drive(
                                new Translation2d(
                                        -RobotConstants.driverController.getLeftY()
                                                * RobotConstants.SwerveConstants.maxSpeed.magnitude(),
                                        -RobotConstants.driverController.getLeftX()
                                                * RobotConstants.SwerveConstants.maxSpeed.magnitude()),
                                -RobotConstants.driverController.getRightX()
                                        * RobotConstants.SwerveConstants.maxAngularRate.magnitude(),
                                true,
                                false),
                        swerve));

        RobotConstants.driverController.start().onTrue(
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
                RobotConstants.driverController.a().whileTrue(elevatorupCommand());
                RobotConstants.driverController.b().whileTrue(elevatordownCommand());
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

    
    private Command elevatorupCommand(){
        return new ElevatorupCommand(elevatorSubsystem);
    }
    private Command elevatordownCommand(){
        return new ElevatordownCommand(elevatorSubsystem);
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
