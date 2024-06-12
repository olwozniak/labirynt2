package ActionObservers.GUIObservers;

import AmazeingGui.GuiControlPanel.ButtonEnum;
import AmazeingGui.GuiControlPanel.ControlPanelComposite;

import javax.swing.*;

public class SolveBeginObserver extends GuiObserver{
    public SolveBeginObserver(ControlPanelComposite controlPanelComposite)
    {
        super(controlPanelComposite);
    }


    @Override
    public void call() {
        if(!SwingUtilities.isEventDispatchThread())
        {
            SwingUtilities.invokeLater(this::call);
            return;
        }
        controlPanelComposite.setButtonState(ButtonEnum.solveButton, false);
        controlPanelComposite.setButtonState(ButtonEnum.chooseExitButton, false);
        controlPanelComposite.setButtonState(ButtonEnum.chooseEntranceButton, false);
        controlPanelComposite.setButtonState(ButtonEnum.chooseFileButton, false);
        controlPanelComposite.setButtonState(ButtonEnum.writeFileButton, false);

        controlPanelComposite.setStatusLabel("Rozpoczęto rozwiązywanie labiryntu", false);
    }
}
