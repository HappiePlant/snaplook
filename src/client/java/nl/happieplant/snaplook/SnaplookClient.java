package nl.happieplant.snaplook;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.option.Perspective;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class SnaplookClient implements ClientModInitializer {
    private static KeyBinding holdBackKey;
    private static KeyBinding holdFrontKey;
    private boolean holdingBack;
    private boolean holdingFront;

    @Override
    public void onInitializeClient() {
        // This entrypoint is suitable for setting up client-specific logic, such as rendering.
        holdBackKey = KeyBindingHelper.registerKeyBinding(
                new KeyBinding(
                        "nl.happieplant.snaplook.hold_back",
                        InputUtil.Type.KEYSYM,
                        GLFW.GLFW_KEY_R,
                        "nl.happieplant.snaplook.keybinds"
                ));
        holdFrontKey = KeyBindingHelper.registerKeyBinding(
                new KeyBinding(
                        "nl.happieplant.snaplook.hold_front",
                        InputUtil.Type.KEYSYM,
                        GLFW.GLFW_KEY_V,
                        "nl.happieplant.snaplook.keybinds"
                ));


        ClientTickEvents.END_CLIENT_TICK.register(c -> {
            if (!holdingBack && holdBackKey.isPressed()) {
                c.options.setPerspective(Perspective.THIRD_PERSON_BACK);
                holdingBack = true;
            }
            if (holdingBack && !holdBackKey.isPressed()) {
                c.options.setPerspective(Perspective.FIRST_PERSON);
                holdingBack = false;
            }
        });
    }
}