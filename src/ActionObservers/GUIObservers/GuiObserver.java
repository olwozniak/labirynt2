package ActionObservers.GUIObservers;

import ActionObservers.ActionObserver;
import GUI.ControlPanelComposite;

abstract class GuiObserver implements ActionObserver {
    final ControlPanelComposite controlPanelComposite;
    public GuiObserver(ControlPanelComposite controlPanelComposite)
    {
        this.controlPanelComposite = controlPanelComposite;
    }
}
