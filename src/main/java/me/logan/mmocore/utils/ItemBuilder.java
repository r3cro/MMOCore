package me.logan.mmocore.utils;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;

import java.util.*;

public class ItemBuilder {

    private final ItemStack item;
    private final ItemMeta itemM;

    public ItemBuilder(final Material itemType){
        item = new ItemStack(itemType);
        itemM = item.getItemMeta();
    }

    public ItemBuilder(final ItemStack itemStack){
        item = itemStack;
        itemM = item.getItemMeta();
    }

    public ItemBuilder(){
        item = new ItemStack(Material.AIR);
        itemM = item.getItemMeta();
    }

    public ItemBuilder type(final Material material){
        build().setType(material);
        return this;
    }

    public ItemBuilder amount(final Integer itemAmt){
        build().setAmount(itemAmt);
        return this;
    }

    public ItemBuilder name(final String name){
        meta().setDisplayName(name);
        build().setItemMeta(meta());
        return this;
    }

    public ItemBuilder lore(final String lore){
        List<String> lores = meta().getLore();
        if(lores == null){lores = new ArrayList<>();}
        lores.add(lore);
        meta().setLore(lores);
        build().setItemMeta(meta());
        return this;
    }

    public ItemBuilder lores(final String[] lores){
        List<String> loresList = meta().getLore();
        if(loresList == null){loresList = new ArrayList<>();}
        else{loresList.clear();}
        Collections.addAll(loresList, lores);
        meta().setLore(loresList);
        return this;
    }

    public ItemBuilder durability(final int durability){
        build().setDurability((short) durability);
        return this;
    }

    @SuppressWarnings("deprecation")
    public ItemBuilder data(final int data){
        build().setData(new MaterialData(build().getType(), (byte)data));
        return this;
    }

    public ItemBuilder enchantment(final Enchantment enchantment, final int level){
        build().addUnsafeEnchantment(enchantment, level);
        return this;
    }

    public ItemBuilder enchantment(final Enchantment enchantment){
        build().addUnsafeEnchantment(enchantment, 1);
        return this;
    }

    public ItemBuilder enchantments(final Enchantment[] enchantments, final int level){
        build().getEnchantments().clear();
        for(Enchantment enchantment : enchantments){
            build().addUnsafeEnchantment(enchantment, level);
        }
        return this;
    }

    public ItemBuilder enchantments(final Enchantment[] enchantments){
        build().getEnchantments().clear();
        for(Enchantment enchantment : enchantments){
            build().addUnsafeEnchantment(enchantment, 1);
        }
        return this;
    }

    public ItemBuilder clearEnchantment(final Enchantment enchantment){
        Map<Enchantment, Integer> itemEnchantments = build().getEnchantments();
        for(Enchantment enchantmentC : itemEnchantments.keySet()){
            if(enchantment == enchantmentC){
                itemEnchantments.remove(enchantmentC);
            }
        }
        return this;
    }

    public ItemBuilder clearEnchantments(){
        build().getEnchantments().clear();
        return this;
    }

    public ItemBuilder clearLore(final String lore){
        if(meta().getLore().contains(lore)){
            meta().getLore().remove(lore);
        }
        build().setItemMeta(meta());
        return this;
    }

    public ItemBuilder clearLores(){
        meta().getLore().clear();
        build().setItemMeta(meta());
        return this;
    }

    public ItemBuilder color(final Color color){
        if(build().getType() == Material.LEATHER_HELMET
                || build().getType() == Material.LEATHER_CHESTPLATE
                || build().getType() == Material.LEATHER_LEGGINGS
                || build().getType() == Material.LEATHER_BOOTS ){
            LeatherArmorMeta meta = (LeatherArmorMeta) meta();
            meta.setColor(color);
            build().setItemMeta(meta);
        }
        return this;
    }

    public ItemBuilder clearColor(){
        if(build().getType() == Material.LEATHER_HELMET
                || build().getType() == Material.LEATHER_CHESTPLATE
                || build().getType() == Material.LEATHER_LEGGINGS
                || build().getType() == Material.LEATHER_BOOTS ){
            LeatherArmorMeta meta = (LeatherArmorMeta) meta();
            meta.setColor(null);
            build().setItemMeta(meta);
        }
        return this;
    }

    public ItemBuilder skullOwner(final String name){
        if(build().getType() == Material.PLAYER_HEAD && build().getDurability() == (byte) 3){
            SkullMeta skullMeta = (SkullMeta) meta();
            skullMeta.setOwner(name);
            build().setItemMeta(meta());
        }
        return this;
    }

    public ItemMeta meta(){
        return itemM;
    }

    public ItemStack build(){
        return item;
    }

}