package nl.happieplant.snaplook;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.option.Perspective;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class SnaplookClient implements ClientModInitializer {
    private static KeyBinding holdBackKey;
    private static KeyBinding holdFrontKey;
    private boolean holdingBack;
    private boolean holdingFront;

    private static KeyBinding registerKeybind(String id) {
        return KeyBindingHelper.registerKeyBinding(
                new KeyBinding(
                        "nl.happieplant.snaplook." + id,
                        InputUtil.Type.KEYSYM,
                        GLFW.GLFW_KEY_UNKNOWN,
                        "nl.happieplant.snaplook.keybinds"
                ));
    }

    private static boolean holdPerspective(MinecraftClient c, boolean holdingKey, KeyBinding key, Perspective perspective) {
        if (!holdingKey && key.isPressed()) {
            c.options.setPerspective(perspective);
            return true;
        }
        if (holdingKey && !key.isPressed()) {
            c.options.setPerspective(Perspective.FIRST_PERSON);
            return false;
        }
        return false;
    }

    @Override
    public void onInitializeClient() {
        holdBackKey = registerKeybind("hold_back");
        holdFrontKey = registerKeybind("hold_front");

        ClientTickEvents.END_CLIENT_TICK.register(c -> {
           holdingBack = holdPerspective(c, holdingBack, holdBackKey, Perspective.THIRD_PERSON_BACK);
           holdingFront = holdPerspective(c, holdingFront, holdFrontKey, Perspective.THIRD_PERSON_FRONT);
        });
    }
}