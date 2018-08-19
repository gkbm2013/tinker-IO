package tinker_io.helper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class EasterEggsHelper {
    public static void displayMessage(World world, BlockPos pos, EntityPlayer player) {
        if(world.isBlockPowered(pos)){
            String name = player.getName();
            SoundEvent creeperPrimed = new SoundEvent(new ResourceLocation("entity.tnt.primed"));

            if(world.isRemote){
                if(name.equals("frankboy89722")){
                    player.sendMessage(new TextComponentString(TextFormatting.GOLD + "\u55ef?\u9019\u4e0d\u662f\u9999\u8349\u55ce? \u6211\u731c\u8eab\u5206\u8b49\u7684\u7b2c100\u4f4d\u6578\u5b57\u53ef\u4ee5\u8fa8\u8b58\u6027\u5225..."));
                    player.sendMessage(new TextComponentString(TextFormatting.GOLD + "\u5c0d\u4e86\uff0c\u6211\u7d55\u5c0d\u4e0d\u6703\u8aaa\u662f\u9999\u8349\u8981\u6211\u52a0\u7206\u70b8\u97f3\u6548\u4f86\u5687\u4eba\u7684..."));
                    player.playSound(creeperPrimed, 2f, 1f);
                }else if(name.equals("KwongFong")){
                    player.sendMessage(new TextComponentString(TextFormatting.GOLD + "\u98a8\u7684\u50b3\u4eba : \u9059\u9060\u7684\u6771\u65b9\u6709\u4e00\u9663\u98a8\uff0c\u4ed6\u7684\u540d\u5b57\u5c31\u53eb\u72c2\u98a8..."));
                    player.sendMessage(new TextComponentString(TextFormatting.GOLD + "\u5c0d\u4e86\uff0c\u6211\u7d55\u5c0d\u4e0d\u6703\u8aaa\u662f\u9999\u8349\u8981\u6211\u52a0\u7206\u70b8\u97f3\u6548\u4f86\u5687\u4eba\u7684..."));
                    player.playSound(creeperPrimed, 2f, 1f);
                }else if(name.equals("alan6716")){
                    player.sendMessage(new TextComponentString(TextFormatting.GOLD + "HI \u609f\u8a22~ \u5077\u5077\u544a\u8a34\u4f60\u4e00\u4ef6\u4e8b : Alan's fuel was stolen by Alien!"));
                    player.sendMessage(new TextComponentString(TextFormatting.GOLD + "\u5c0d\u4e86\uff0c\u6211\u7d55\u5c0d\u4e0d\u6703\u8aaa\u662f\u9999\u8349\u8981\u6211\u52a0\u7206\u70b8\u97f3\u6548\u4f86\u5687\u4eba\u7684..."));
                    player.playSound(creeperPrimed, 2f, 1f);
                }else if(name.equals("gkbXkinG")){
                    player.sendMessage(new TextComponentString(TextFormatting.GOLD + "\u4f60\u597d~"));
                    player.sendMessage(new TextComponentString(TextFormatting.GOLD + "\u5c0d\u4e86\uff0c\u6211\u7d55\u5c0d\u4e0d\u6703\u8aaa\u662f\u9999\u8349\u8981\u6211\u52a0\u7206\u70b8\u97f3\u6548\u4f86\u5687\u4eba\u7684..."));
                    player.playSound(creeperPrimed, 2f, 1f);
                }else if(name.equals("eating555")){
                    player.sendMessage(new TextComponentString(TextFormatting.GOLD + "HI eating555! \u6211\u662fGKB~"));
                    player.playSound(creeperPrimed, 2f, 1f);
                }else if(name.equals("codespawner")){
                    player.sendMessage(new TextComponentString(TextFormatting.GOLD + "\u54ce\u5440~\u9019\u4e0d\u662fcode\u55ce?\u8a71\u8aaa\u9019\u4e32\u6587\u5b57\u7de8\u78bc\u7a76\u7adf\u662fGBK\u9084\u662fGKB\u5462?"));
                    player.sendMessage(new TextComponentString(TextFormatting.GOLD + "\u5c0d\u4e86\uff0c\u6211\u7d55\u5c0d\u4e0d\u6703\u8aaa\u662f\u9999\u8349\u8981\u6211\u52a0\u7206\u70b8\u97f3\u6548\u4f86\u5687\u4eba\u7684..."));
                    player.playSound(creeperPrimed, 2f, 1f);
                }else if(name.equals("BigRice1018")){
                    player.sendMessage(new TextComponentString(TextFormatting.GOLD + "\u7c73\u5927!!!!"));
                    player.sendMessage(new TextComponentString(TextFormatting.GOLD + "\u5c0d\u4e86\uff0c\u6211\u7d55\u5c0d\u4e0d\u6703\u8aaa\u662f\u9999\u8349\u8981\u6211\u52a0\u7206\u70b8\u97f3\u6548\u4f86\u5687\u4eba\u7684..."));
                    player.playSound(creeperPrimed, 2f, 1f);
                }else if(name.equals("nightmare9913256")){
                    player.sendMessage(new TextComponentString(TextFormatting.GOLD + "\u597d\u9762\u719f\u554a...\u55ef?\u9019\u4e0d\u662fnight\u55ce?"));
                    player.sendMessage(new TextComponentString(TextFormatting.GOLD + "\u5c0d\u4e86\uff0c\u6211\u7d55\u5c0d\u4e0d\u6703\u8aaa\u662f\u9999\u8349\u8981\u6211\u52a0\u7206\u70b8\u97f3\u6548\u4f86\u5687\u4eba\u7684..."));
                    player.playSound(creeperPrimed, 2f, 1f);
                }else if(name.equals("con2000us")){
                    player.sendMessage(new TextComponentString(TextFormatting.GOLD + "\u4f60\u597d~ (\u4e0d\u8981\u61f7\u7591...\u5c31\u662f\u4f60\u597d...)"));
                    player.sendMessage(new TextComponentString(TextFormatting.GOLD + "\u5c0d\u4e86\uff0c\u6211\u7d55\u5c0d\u4e0d\u6703\u8aaa\u662f\u9999\u8349\u8981\u6211\u52a0\u7206\u70b8\u97f3\u6548\u4f86\u5687\u4eba\u7684..."));
                    player.playSound(creeperPrimed, 2f, 1f);
                }
            }
        }
    }
}
