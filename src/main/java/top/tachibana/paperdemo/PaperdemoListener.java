package top.tachibana.paperdemo;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

public final class PaperdemoListener implements Listener {
    @EventHandler
    public void onDropItem(PlayerDropItemEvent event){
        // 当玩家丢出下界合金锭 输出消息并延迟两秒杀死该玩家 随后取消事件
        if(event.getItemDrop().getItemStack().getType() == Material.NETHERITE_INGOT){
//            new BukkitRunnable(){
//                public void run(){
//                    event.getPlayer().sendMessage(Component.text("如此宝贵，你竟敢随意丢弃！", NamedTextColor.YELLOW));
//                    event.getPlayer().sendMessage(Component.text("你将会获得惩罚", NamedTextColor.RED));
//                    try {
//                        Thread.sleep(2000);
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }
//                    event.getPlayer().setHealth(0);
//                }
//            }.runTask(Paperdemo.getInstance());

            BukkitScheduler scheduler = Paperdemo.getInstance().getServer().getScheduler();
            scheduler.runTaskAsynchronously(Paperdemo.getInstance(), () -> {
                    event.getPlayer().sendMessage(Component.text("如此宝贵，你竟敢随意丢弃！", NamedTextColor.YELLOW));
                    event.getPlayer().sendMessage(Component.text("你将会获得惩罚", NamedTextColor.RED));
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    event.getPlayer().setHealth(0);
            });
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onRightClick(PlayerInteractEntityEvent event){
        // 当玩家右键工具匠村民 添加配方
        if(event.getPlayer().getInventory().getItemInMainHand().getType() == Material.DIAMOND){
            if(event.getRightClicked() instanceof Villager villager){
                if(villager.getProfession() == Villager.Profession.TOOLSMITH){
                    villager.setRecipes(PaperdemoRecipe.getMerchantRecipe());
                }
            }
        }
    }
    @EventHandler
    public void onBucket(PlayerBucketFillEvent event){
        // cancel this event
        // playerBucketFillEvent.setCancelled(true);
        if(event.getBlock().getType() == Material.LAVA){
            ItemStack water_bucket = new ItemStack(Material.WATER_BUCKET);
            event.setItemStack(water_bucket);
        } else if (event.getBlock().getType() == Material.WATER) {
            ItemStack lava_bucket = new ItemStack(Material.LAVA_BUCKET);
            event.setItemStack(lava_bucket);
        }
    }
    @EventHandler
    public  void onHeld(PlayerItemHeldEvent event){
        ItemStack newItem = event.getPlayer().getInventory().getItem(event.getNewSlot());
        if(newItem != null && newItem.getType() == Material.PAPER){
            Bukkit.broadcast(Component.text("Paper in " + event.getNewSlot()));
            callCoolPaperEvent();
        }
    }
    public void callCoolPaperEvent(){
        PaperIsCoolEvent event = new PaperIsCoolEvent(Component.text("Paper is cool!"));
        //Event is called
        if(event.callEvent()) {
            Bukkit.broadcast(event.getMessage());
        }
        //equals to
//        event.callEvent();
//        if(!event.isCancelled()) {
//            Bukkit.broadcast(event.getMessage());
//        }
    }
}
