package top.tachibana.paperdemo;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerDiamondHeld extends Event implements Cancellable {
    private final static HandlerList HANDLER_LIST = new HandlerList();
    private final Player player;
    private boolean cancelled;
    // 构造器
    public PlayerDiamondHeld(Player who){
        this.player = who;
    }
    // 属性
    public Player getPlayer() {
        return player;
    }
    // 只有实现了方法getHandlerList()的事件才能被监听
    // 注意这个方法是静态的
    public static HandlerList getHandlerList(){
        return HANDLER_LIST;
    }
    // 注意这个方法是动态的
    @Override
    public HandlerList getHandlers(){
        return HANDLER_LIST;
    }
    // 为了实现Cancellable 需要定义下面的两个方法
    // cancelled的访问属性
    @Override
    public boolean isCancelled(){
        return this.cancelled;
    }
    // cancelled的修改属性
    @Override
    public void setCancelled(boolean cancelled){
        this.cancelled = cancelled;
    }
}
