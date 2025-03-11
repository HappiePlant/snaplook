package nl.happieplant.snaplook;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.option.Perspective;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class SnaplookClient implements ClientModInitializer {
    private static KeyBinding keyBinding;

    @Override
    public void onInitializeClient() {
        // This entrypoint is suitable for setting up client-specific logic, such as rendering.
        keyBinding = KeyBindingHelper.registerKeyBinding(
                new KeyBinding(
                        "nl.happieplant.snaplook.hold_back",
                        InputUtil.Type.KEYSYM,
                        GLFW.GLFW_KEY_ESCAPE,
                        "nl.happieplant.snaplook.keybinds"
                ));

        ClientTickEvents.END_CLIENT_TICK.register(c -> {
            while (keyBinding.wasPressed()) {
                c.player.sendMessage(Text.literal("Key 1 was pressed!"), false);
                if (c.options.getPerspective() == Perspective.THIRD_PERSON_BACK) {
                    c.options.setPerspective(Perspective.FIRST_PERSON);
                } else {
                    c.options.setPerspective(Perspective.THIRD_PERSON_BACK);
                }
            }
        });
    }
}