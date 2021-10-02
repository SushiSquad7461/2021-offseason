package frc.robot.triggers;

import javax.swing.plaf.basic.BasicSplitPaneUI.KeyboardUpLeftHandler;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.buttons.Trigger;

public class ShiftButton extends Trigger{
    private boolean isShiftKeyActive;
    private GenericHID joystick;
    private int shiftKeyNumber;
    private int keyNumber;

    public ShiftButton(GenericHID joystick, int shiftKeyNumber, int keyNumber){
        this.joystick = joystick;
        this.shiftKeyNumber = shiftKeyNumber;
        this.keyNumber = keyNumber;
    }
    @Override
    public boolean get() {
        return isShiftKeyActive;
    }
}
 