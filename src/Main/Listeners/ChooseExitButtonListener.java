package Main.Listeners;

import Main.GUI.ButtonEnum;
import Main.GUI.ControlPanelComposite;
import Main.MazeData.MazeDataSingleton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChooseExitButtonListener implements ActionListener {
    private final ControlPanelComposite controlPanelComposite;

    public ChooseExitButtonListener(ControlPanelComposite controlPanelComposite)
    {
        this.controlPanelComposite = controlPanelComposite;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean val = controlPanelComposite.isChoosingExit();

        if(MazeDataSingleton.getInstance().getExit() != null
                && MazeDataSingleton.getInstance().getEntry() != null)
            controlPanelComposite.setButtonState(ButtonEnum.solveButton, val);

        controlPanelComposite.setButtonState(ButtonEnum.chooseEntranceButton, val);
        controlPanelComposite.setButtonState(ButtonEnum.chooseFileButton, val);

        controlPanelComposite.changeChoosingExit();
    }
}
