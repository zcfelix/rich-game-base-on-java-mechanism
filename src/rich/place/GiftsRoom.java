package rich.place;

import rich.Player;
import rich.tool.Tool;

public class GiftsRoom implements Place {
    private Tool attachedTool;

    public GiftsRoom() {
        this.attachedTool = null;
    }

    @Override
    public Player.State actionTo(Player player) {
        return Player.State.WAITING_FOR_RESPONSE;
    }


    @Override
    public boolean attachedBy(Tool tool) {
        if (canToolBeAttached()) {
            this.attachedTool = tool;
            return true;
        }
        return false;
    }

    private boolean canToolBeAttached() {
        return attachedTool == null;
    }
}
