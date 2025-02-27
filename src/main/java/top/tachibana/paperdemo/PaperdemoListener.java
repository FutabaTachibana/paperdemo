package top.tachibana.paperdemo;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;

public final class PaperdemoListener implements Listener {
    @EventHandler
    public void onItemUse(PlayerInteractEvent event){
        if(event.getItem() != null && event.getItem().getType() == Material.STICK){
            Player player = event.getPlayer();
            // 左键 传送至附近的随机一个实体
            if(event.getAction() == Action.LEFT_CLICK_AIR){
                Collection<Entity> entities = player.getLocation().getNearbyEntities(32, 32, 32);
                if(!entities.isEmpty()){
                    Entity entity = entities.stream().skip(ThreadLocalRandom.current().nextInt(entities.size())).findFirst().get();
                    player.teleport(entity);
                    player.sendMessage(Component.text("已传送至 " + entity.getName(), NamedTextColor.YELLOW));
                }
                else{
                    player.sendMessage(Component.text("你附近暂无实体", NamedTextColor.YELLOW));
                }
            }
            // 右键 传送至床的位置
            else if(event.getAction() == Action.RIGHT_CLICK_AIR){
                Location respawn = player.getRespawnLocation();
                if(respawn != null){
                    player.teleport(respawn);
                    player.sendMessage(Component.text("已传送至重生点", NamedTextColor.YELLOW));
                }
                else{
                    player.sendMessage(Component.text("你的床或重生锚被破坏", NamedTextColor.RED));
                }
            }
        }
    }
    @EventHandler
    public void onMove(PlayerMoveEvent event){
        BukkitRunnable damage = new BukkitRunnable(){
            public void run(){
                event.getPlayer().damage(0.5);
                if(event.getPlayer().getLocation().clone().add(0, -1, 0).getBlock().getType() != Material.BLUE_ICE){
                    this.cancel();
                }
            }
        };
        if(event.getTo().clone().add(0, -1, 0).getBlock().getType() == Material.BLUE_ICE){
            damage.runTaskTimer(Paperdemo.getInstance(), 0, 20);
        }
    }
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
            event.getPlayer().sendMessage(Component.text("如此宝贵，你竟敢随意丢弃！", NamedTextColor.YELLOW));
            event.getPlayer().sendMessage(Component.text("你将会获得惩罚", NamedTextColor.RED));
            scheduler.runTaskLater(Paperdemo.getInstance(), () -> {
                    event.getPlayer().setHealth(0);
            }, 40);
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
        //这段代码文档没有演示过
//        ItemStack newItem = event.getPlayer().getInventory().getItem(event.getNewSlot());
//        if(newItem != null && newItem.getType() == Material.PAPER){
//            Bukkit.broadcast(Component.text("Paper in " + event.getNewSlot()));
//            callCoolPaperEvent();
//        }
    }
//    public void callCoolPaperEvent(){
        //这段代码文档没有演示过
//        PaperIsCoolEvent event = new PaperIsCoolEvent(Component.text("Paper is cool!"));
//        //Event is called
//        if(event.callEvent()) {
//            Bukkit.broadcast(event.getMessage());
//        }
//        //equals to
//        event.callEvent();
//        if(!event.isCancelled()) {
//            Bukkit.broadcast(event.getMessage());
//        }
//    }
}
