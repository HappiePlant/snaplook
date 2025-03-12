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

    private static KeyBinding registerKeybind(String id, int key) {
        return KeyBindingHelper.registerKeyBinding(
                new KeyBinding(
                        "key.nl.happieplant.snaplook." + id,
                        InputUtil.Type.KEYSYM,
                        key,
                        "category.nl.happieplant.snaplook"
                ));
    }

    private static boolean holdPerspective(MinecraftClient c, boolean holdingKey, KeyBinding key, Perspective perspective) {
        if (!holdingKey && key.isPressed()) {
            c.options.setPerspective(perspective);
            holdingKey = true;
        }
        if (holdingKey && !key.isPressed()) {
            c.options.setPerspective(Perspective.FIRST_PERSON);
            holdingKey = false;
        }
        return holdingKey;
    }

    @Override
    public void onInitializeClient() {
        holdBackKey = registerKeybind("hold_back", GLFW.GLFW_KEY_R);
        holdFrontKey = registerKeybind("hold_front", GLFW.GLFW_KEY_V);

        ClientTickEvents.END_CLIENT_TICK.register(c -> {
           holdingBack = holdPerspective(c, holdingBack, holdBackKey, Perspective.THIRD_PERSON_BACK);
           holdingFront = holdPerspective(c, holdingFront, holdFrontKey, Perspective.THIRD_PERSON_FRONT);
        });
    }
}