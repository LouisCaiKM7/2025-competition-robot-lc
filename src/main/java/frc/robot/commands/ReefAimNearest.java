//package frc.robot.commands;
//
//import edu.wpi.first.math.MathUtil;
//import edu.wpi.first.math.geometry.Pose2d;
//import edu.wpi.first.math.geometry.Transform2d;
//import edu.wpi.first.wpilibj2.command.Command;
//import frc.robot.RobotConstants;
//import frc.robot.subsystems.apriltagvision.AprilTagVision;
//import frc.robot.subsystems.swerve.Swerve;
//
//public class ReefAimNearest extends Command {
//    private final AprilTagVision aprilTagVision; // AprilTagVision subsystem for detecting AprilTags
//    private final Swerve swerve = Swerve.getInstance(); // Swerve subsystem for robot movement
//    private final boolean rightReef; // true if shooting at the right reef, false if left reef
//    private Pose2d robotPose; // Current pose of the robot
//    private Pose2d tagPose; // Pose of the nearest detected AprilTag
//    private Pose2d destinationPose; // Target pose the robot should aim for
//    private Transform2d transform; // Transformation from robot pose to destination pose
//    private boolean isFinished = false; // Flag to indicate if the command is finished
//
//    /**
//     * Constructor for the ReefAimNearest command.
//     *
//     * @param aprilTagVision AprilTagVision subsystem for detecting AprilTags.
//     * @param rightReef      Boolean indicating if the target is the right reef.
//     */
//    public ReefAimNearest(AprilTagVision aprilTagVision, boolean rightReef) {
//        this.aprilTagVision = aprilTagVision;
//        // Register the subsystems used by this command with the scheduler
//        addRequirements(this.aprilTagVision, this.swerve);
//        this.rightReef = rightReef;
//    }
//
//    /**
//     * The initial subroutine of a command. Called once when the command is initially scheduled.
//     */
//    @Override
//    public void initialize() {
//        // Get the current pose of the robot from the AprilTagVision subsystem
//        this.robotPose = this.aprilTagVision.getRobotPose3d().toPose2d();
//        // Get the pose of the nearest detected AprilTag
//        this.tagPose = this.aprilTagVision.getClosestTagPose().toPose2d();
//        // Calculate the destination pose based on whether the right or left reef is the target
//        if (this.rightReef) {
//            this.destinationPose = this.tagPose.transformBy(RobotConstants.ReefAimConstants.tagRightToRobot);
//        } else {
//            this.destinationPose = this.tagPose.transformBy(RobotConstants.ReefAimConstants.tagLeftToRobot);
//        }
//        // Calculate the transformation from the robot's current pose to the destination pose
//        this.transform = this.destinationPose.minus(this.robotPose);
//    }
//
//    /**
//     * The main body of a command. Called repeatedly while the command is scheduled.
//     * (That is, it is called repeatedly until {@link #isFinished()}) returns true.)
//     */
//    @Override
//    public void execute() {
//        // Drive the robot towards the destination pose
//        this.swerve.drive(
//                this.transform.getTranslation(), // Translation component of the transform
//                MathUtil.angleModulus(this.transform.getRotation().getRadians()), // Rotation component of the transform, normalized to [0, 2π]
//                false, // Whether to lock the orientation of the robot
//                false  // Whether to use open loop control
//        );
//        // Set the command as finished after executing once
//        this.isFinished = true;
//    }
//
//    /**
//     * <p>
//     * Returns whether this command has finished. Once a command finishes -- indicated by
//     * this method returning true -- the scheduler will call its {@link #end(boolean)} method.
//     * </p><p>
//     * Returning false will result in the command never ending automatically. It may still be
//     * cancelled manually or interrupted by another command. Hard coding this command to always
//     * return true will result in the command executing once and finishing immediately. It is
//     * recommended to use * {@link edu.wpi.first.wpilibj2.command.InstantCommand InstantCommand}
//     * for such an operation.
//     * </p>
//     *
//     * @return whether this command has finished.
//     */
//    @Override
//    public boolean isFinished() {
//        // Return the flag indicating if the command is finished
//        return this.isFinished;
//    }
//
//    /**
//     * The action to take when the command ends. Called when either the command
//     * finishes normally -- that is it is called when {@link #isFinished()} returns
//     * true -- or when it is interrupted/canceled. This is where you may want to
//     * wrap up loose ends, like shutting off a motor that was being used in the command.
//     *
//     * @param interrupted whether the command was interrupted/canceled
//     */
//    @Override
//    public void end(boolean interrupted) {
//        // Brake the swerve subsystem when the command ends
//        this.swerve.brake();
//    }
//}
//
