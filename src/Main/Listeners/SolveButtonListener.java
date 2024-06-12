package Main.Listeners;

import Main.CustomEventManager;
import Main.EventType;
import Main.GUI.ControlPanelComposite;
import Main.MazeData.MazeDataSingleton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SolveButtonListener implements ActionListener {
    private final ControlPanelComposite controlPanelComposite;

    public SolveButtonListener(ControlPanelComposite controlPanelComposite)
    {
        this.controlPanelComposite = controlPanelComposite;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        MazeDataSingleton.getInstance().solve();
        CustomEventManager.getInstance().callEvent(EventType.solveBeginEvent);
    }
}
