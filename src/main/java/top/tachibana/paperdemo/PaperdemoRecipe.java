package top.tachibana.paperdemo;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.*;

import java.util.List;

public class PaperdemoRecipe {
    public static List<ShapedRecipe> getShapedRecipe(){
        // 附魔金苹果的合成配方
        // | B | B | B |
        // | B | A | B |
        // | B | B | B |
        // B: 金块
        // A: 苹果
        ShapedRecipe enchantedGoldenApple = new ShapedRecipe(
                new NamespacedKey(Paperdemo.getInstance(), "craft_enchanted_golden_apple"),
                new ItemStack(Material.ENCHANTED_GOLDEN_APPLE))
                .shape("BBB", "BAB", "BBB")
                .setIngredient('A', Material.APPLE)
                .setIngredient('B', Material.GOLD_BLOCK);
        return List.of(
                enchantedGoldenApple
        );
    }
    public static List<ShapelessRecipe> getShapelessRecipe() {
        // 烈焰棒 + 2 * 末影珍珠 合成 2 * 末影之眼
        ShapelessRecipe blazeRodToEnderEye = new ShapelessRecipe(
                new NamespacedKey(Paperdemo.getInstance(), "blaze_rod_to_ender_eye"),
                new ItemStack(Material.ENDER_EYE, 2))
                .addIngredient(Material.BLAZE_ROD)
                .addIngredient(2, Material.ENDER_PEARL);
        // 所有类型的铜块都能分解成9 * 铜锭
        RecipeChoice.MaterialChoice choice = new RecipeChoice.MaterialChoice(
                List.of(
                        //Material.COPPER_BLOCK, 原版存在，无需添加
                        Material.EXPOSED_COPPER,
                        Material.WEATHERED_COPPER,
                        Material.OXIDIZED_COPPER)
        );
        ShapelessRecipe copperBlockToIngot = new ShapelessRecipe(
                new NamespacedKey(Paperdemo.getInstance(), "copper_block_to_ingot"),
                new ItemStack(Material.COPPER_INGOT, 9)
        ).addIngredient(choice);
        return List.of(
                blazeRodToEnderEye,
                copperBlockToIngot
        );
    }
    public static List<BlastingRecipe> getBlastingRecipe(){
        // 铁链煅烧成 3 * 铁粒
        BlastingRecipe chainToIronNugget = new BlastingRecipe(
                new NamespacedKey(Paperdemo.getInstance(), "chain_to_iron_nugget"),
                new ItemStack(Material.IRON_NUGGET, 3),
                Material.CHAIN,
                0.2f,
                100
        );
        return List.of(
                chainToIronNugget
        );
    }
    public static List<MerchantRecipe> getMerchantRecipe(){
        // 2 * 绿宝石 交易钻石
        MerchantRecipe merchantDiamond = new MerchantRecipe(
                new ItemStack(Material.DIAMOND),
                10
        );
        merchantDiamond.setIngredients(List.of(new ItemStack(Material.EMERALD, 2)));
        return List.of(
                merchantDiamond
        );
    }
}
