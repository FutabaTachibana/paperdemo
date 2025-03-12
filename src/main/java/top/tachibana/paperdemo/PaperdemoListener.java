package top.tachibana.paperdemo;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;

public final class PaperdemoListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        if(event.getClickedInventory() == Paperdemo.getMenu()){
            Inventory menu = Paperdemo.getMenu();
            HumanEntity player = event.getWhoClicked();
            event.setCancelled(true);
            ItemStack item = menu.getItem(event.getSlot());
            if(item == null){
                return;
            }
            switch (item.getType()){
                case GRASS_BLOCK -> player.setGameMode(GameMode.CREATIVE);
                case IRON_SWORD -> player.setGameMode(GameMode.SURVIVAL);
                case MAP -> player.setGameMode(GameMode.ADVENTURE);
                case ENDER_EYE -> player.setGameMode(GameMode.SPECTATOR);
                case BARRIER -> player.closeInventory();
            }
        }
    }
    @EventHandler
    public void onSwapHand(PlayerSwapHandItemsEvent event){
        HumanEntity player = event.getPlayer();
        if(player.isSneaking()){
            event.setCancelled(true);
            player.openInventory(player.getEnderChest());
        }
    }
    @EventHandler
    public void onGamemodeChange(PlayerGameModeChangeEvent event){
        if(event.getNewGameMode() == GameMode.ADVENTURE){
            event.setCancelled(true);
            event.getPlayer().setGameMode(GameMode.SPECTATOR);
        }
    }
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
        if(event.getPlayer().isSneaking()){
            event.setCancelled(true);
            event.getPlayer().openInventory(Paperdemo.getMenu());
        }


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
        // 用于call我们的自定义事件
        ItemStack newItem = event.getPlayer().getInventory().getItem(event.getNewSlot());
        if(newItem != null && newItem.getType() == Material.DIAMOND){
            callPlayerDiamondHeld(event.getPlayer());
        }
    }
    public void callPlayerDiamondHeld(Player who){
        PlayerDiamondHeld event = new PlayerDiamondHeld(who);
        // call事件
        if(event.callEvent()) {
            event.getPlayer().sendMessage(Component.text("PlayerDiamondHeld事件被触发", NamedTextColor.YELLOW));
        }
        // 与下面的写法等价
//        event.callEvent();
//        if(!event.isCancelled()) {
//            event.getPlayer().sendMessage(Component.text("PlayerDiamondHeld事件被触发", NamedTextColor.YELLOW));
//        }
    }
    // 我们的自定义事件可被监听 也可被取消
    @EventHandler
    public void onPlayerDiamondHeld(PlayerDiamondHeld event){
        ItemStack offHand = event.getPlayer().getInventory().getItemInOffHand();
        if(offHand.getType() == Material.NETHERITE_INGOT){
            Player player = event.getPlayer();
            player.sendMessage(Component.text("拿了钻石还拿下界合金锭？什么好事都让你占了。", NamedTextColor.RED));
            player.playSound(player, Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 1, 1);
            event.setCancelled(true);
        }
    }
}
