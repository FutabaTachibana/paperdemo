package top.tachibana.paperdemo;

import net.kyori.adventure.text.Component;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PaperIsCoolEvent extends Event implements Cancellable {
    private final static HandlerList HANDLER_LIST = new HandlerList();
    private Component message;
    private boolean cancelled;

    //Constructor
    public PaperIsCoolEvent(Component message){
        this.message = message;
    }
    public Component getMessage(){
        return this.message;
    }
    public  void setMessage(Component message){
        this.message = message;
    }

    public static HandlerList getHandlerList(){
        return HANDLER_LIST;
    }

    @Override
    public HandlerList getHandlers(){
        return HANDLER_LIST;
    }
    @Override
    public boolean isCancelled(){
        return this.cancelled;
    }
    @Override
    public void setCancelled(boolean cancelled){
        this.cancelled = cancelled;
    }
}
