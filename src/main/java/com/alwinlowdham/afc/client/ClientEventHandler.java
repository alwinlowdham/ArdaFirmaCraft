package com.alwinlowdham.afc.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, value = {Dist.CLIENT})
public class ClientEventHandler {

    @SubscribeEvent(priority = EventPriority.HIGHEST, receiveCanceled = true)
    public static void onClickInput(InputEvent.ClickInputEvent event) {

        if (event.isAttack()) {
            Check.checkAttack(false, event);
        }
        else if (event.isUseItem()) {
            Check.checkAttack(true, event);
        }
        else if (event.isPickBlock()) {
            // check for block/parry else return
            // (if weapon or shield in offhand) or (both hands not holding weapons and offhand not crippled/severed):
                // block with offhand
                // blocking with offhand is useful because its cooldown is separate from mainhand
            // else:
                // block with mainhand
        }
    }
}
