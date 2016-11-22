package prosecutor.barrister.gui.managers;

import org.antlr.v4.codegen.model.decl.CodeBlock;
import org.antlr.v4.runtime.misc.NotNull;
import prosecutor.barrister.gui.components.FComponent;
import prosecutor.barrister.gui.components.buttons.FButton;

import java.awt.*;
import java.util.HashMap;
import java.util.Objects;

/**
 * Created by Fry on 22.11.2016.
 */
public class UIManager {

    static
    {
        HashMap<String, HashMap<String,Color>> colors=new HashMap<>();
        HashMap<String, HashMap<String,Font>> fonts=new HashMap<>();
        colors.put("default", new HashMap<>());
        fonts.put("default", new HashMap<>());
        colors.put("b/w",new HashMap<>());
        colors.put("green",new HashMap<>());
        Colors=colors;
        Fonts=fonts;
        Font f=new Font("SansSerif",Font.PLAIN,16);
        setFont("default","font.text.normal",f);
        setColor("b/w","color.hover",new Color(252,194,196));
        setColor("b/w","color.active",new Color(252,194,196));
        setColor("green","color.hover",new Color(128,199,125));
        setColor("green","color.active",new Color(15,115,59));
        setColor("green","color.text.normal",Color.black);
    }
    private static HashMap<String,HashMap<String,Color>> Colors;
    private static HashMap<String,HashMap<String,Font>> Fonts;

    public static Color getColor(@NotNull String key)
    {
        return getColor("default",key);
    }
    public static Color getColor(@NotNull FComponent component, @NotNull String key)
    {
        return getColor(component.getStyle(),key);
    }
    public static Color getColor(@NotNull String style, @NotNull String key)
    {
        return Colors.get(style).get(key);
    }

    public static Color setColor(@NotNull String key, @NotNull Color color)
    {
        return setColor("default",key,color);
    }
    public static Color setColor(@NotNull String style, @NotNull String key, @NotNull Color color)
    {
        return Colors.get(style).put(key,color);
    }

    public static Font getFont(FComponent component, String key) {
        return getFont(component.getStyle(),key);
    }
    public static Font getFont(String key)
    {
        return getFont("default",key);
    }
    public static Font getFont(String style,String key)
    {
        return Fonts.get(style).get(key);
    }
    public static Font setFont(@NotNull String key, @NotNull Font font)
    {
        return setFont("default",key,font);
    }
    public static Font setFont(@NotNull String style, @NotNull String key, @NotNull Font font)
    {
        return Fonts.get(style).put(key,font);
    }
}
